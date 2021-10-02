/*
 * Copyright (c) 2021 Leonard Sch�ngel
 * 
 * For licensing information see the included license (LICENSE.txt)
 */
package racecontrol.gui.app.trackdata;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import processing.core.PVector;
import racecontrol.client.data.RealtimeInfo;
import static racecontrol.client.data.enums.CarLocation.TRACK;
import static racecontrol.client.data.enums.LapType.REGULAR;
import racecontrol.client.events.RealtimeCarUpdateEvent;
import racecontrol.client.events.TrackInfoEvent;
import racecontrol.client.extension.trackdata.TrackData;
import racecontrol.client.extension.trackdata.TrackDataEvent;
import racecontrol.client.extension.trackdata.TrackDataExtension;
import racecontrol.eventbus.Event;
import racecontrol.eventbus.EventBus;
import racecontrol.eventbus.EventListener;
import racecontrol.gui.lpui.LPContainer;
import racecontrol.gui.lpui.LPTabPanel;

/**
 *
 * @author Leonard
 */
public class TrackDataController
        implements EventListener {

    private static final Logger LOG = Logger.getLogger(TrackDataController.class.getName());

    private final TrackDataPanel dataPanel;
    private final TrackMapPanel mapPanel;
    private final LPTabPanel tabPanel = new LPTabPanel();

    private final TrackDataExtension trackDataExtension;

    private TrackData trackData;

    private final int mapSize = 200;

    private final List<Float> vMap = new ArrayList<>();
    private final List<List<Float>> vMapTotal = new ArrayList<>();

    private final List<Float> dirMap = new ArrayList<>();
    private final List<List<Float>> dirMapTotal = new ArrayList<>();

    public TrackDataController() {
        EventBus.register(this);
        dataPanel = new TrackDataPanel();
        mapPanel = new TrackMapPanel();
        trackDataExtension = TrackDataExtension.getInstance();

        for (int i = 0; i < mapSize; i++) {
            vMap.add(0f);
            vMapTotal.add(new ArrayList<>());
            dirMap.add(0f);
            dirMapTotal.add(new ArrayList<>());
        }
        dataPanel.vMap = vMap;
        dataPanel.dirMap = dirMap;

        dataPanel.saveToFileButton.setAction(() -> saveAll());
        dataPanel.saveButton.setAction(() -> useData());

        tabPanel.addTab(dataPanel);
        tabPanel.addTab(mapPanel);
        tabPanel.setTabIndex(0);
    }

    public LPContainer getPanel() {
        return tabPanel;
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof TrackInfoEvent) {
            onTrackInfo();
        } else if (e instanceof RealtimeCarUpdateEvent) {
            updateVMap(((RealtimeCarUpdateEvent) e).getInfo());
            updateDirMap(((RealtimeCarUpdateEvent) e).getInfo());
            dataPanel.drawCarState(((RealtimeCarUpdateEvent) e).getInfo());
            dataPanel.invalidate();
            //mapPanel.invalidate();
        } else if (e instanceof TrackDataEvent) {
            mapPanel.trackData = ((TrackDataEvent) e).getTrackData();
            dataPanel.speedTrapLine = ((TrackDataEvent) e).getTrackData().getSpeedTrapLine();
        }
    }

    private void onTrackInfo() {
        trackData = trackDataExtension.getTrackData();

        dataPanel.trackNameLabel.setText(trackData.getTrackname());
        dataPanel.sectorOneTextField.setValue(String.format("%.4f", trackData.getSectorOneLine()).replace(",", "."));
        dataPanel.sectorTwoTextField.setValue(String.format("%.4f", trackData.getSectorTwoLine()).replace(",", "."));
        dataPanel.sectorThreeTextField.setValue(String.format("%.4f", trackData.getSectorThreeLine()).replace(",", "."));
        dataPanel.speedTrapTextField.setValue(String.format("%.4f", trackData.getSpeedTrapLine()).replace(",", "."));

        dataPanel.savedVMap = trackData.getGt3VelocityMap();
        dataPanel.savedDirMap = trackData.getDirectionMap();
    }

    private void updateVMap(RealtimeInfo info) {
        if (info.getCurrentLap().getType() == REGULAR
                && info.getLocation() == TRACK
                && info.getKMH() > 10
                && !info.getCurrentLap().isInvalid()
                && info.getLaps() > 0) {
            int index = (int) Math.floor(info.getSplinePosition() * mapSize);
            if (index == mapSize) {
                index = 0;
            }
            vMapTotal.get(index).add(info.getKMH() * 1f);
            vMapTotal.get(index).sort((a, b) -> a.compareTo(b));
            vMap.set(index, getMedian(vMapTotal.get(index)));
        }
    }

    private void updateDirMap(RealtimeInfo info) {
        if (info.getCurrentLap().getType() == REGULAR
                && info.getLocation() == TRACK
                && info.getKMH() > 10
                && !info.getCurrentLap().isInvalid()) {
            int index = (int) Math.floor(info.getSplinePosition() * mapSize);
            if (index == mapSize) {
                index = 0;
            }
            dirMapTotal.get(index).add(info.getYaw());
            dirMapTotal.get(index).sort((a, b) -> a.compareTo(b));
            dirMap.set(index, getAverageAngel(dirMapTotal.get(index)));
        }
    }

    private float getMedian(List<Float> l) {
        if (l.size() < 1) {
            return 0;
        }
        int middle = (int) Math.floor(l.size() / 2f);
        if (l.size() % 2 == 0) {
            return (l.get(middle) + l.get(middle - 1)) / 2f;
        } else {
            return l.get(middle);
        }
    }

    private float getAverageAngel(List<Float> l) {
        PVector sum = new PVector(0, 0);
        l.forEach(f -> sum.add(PVector.fromAngle(f)));
        return sum.div(l.size()).heading();
    }

    private void saveAll() {
        float s1, s2, s3, speedTrap;
        try {
            s1 = Float.parseFloat(dataPanel.sectorOneTextField.getValue());
            s2 = Float.parseFloat(dataPanel.sectorTwoTextField.getValue());
            s3 = Float.parseFloat(dataPanel.sectorThreeTextField.getValue());
            speedTrap = Float.parseFloat(dataPanel.speedTrapTextField.getValue());
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error parsing sectors", e);
            return;
        }

        TrackData oldData = trackDataExtension.getTrackData();

        List<Float> newVMap = oldData.getGt3VelocityMap();
        if (dataPanel.enableVMapCheckBox.isSelected()) {
            newVMap = vMap;
        }

        List<Float> newDMap = oldData.getDirectionMap();
        if (dataPanel.enableDMapCheckBox.isSelected()) {
            newDMap = dirMap;
        }

        TrackData newData = new TrackData(oldData.getTrackname(),
                oldData.getTrackMeters(),
                newVMap,
                s1,
                s2,
                s3,
                speedTrap,
                newDMap);
        trackDataExtension.saveTrackData(newData);
    }

    private void useData() {
        float s1, s2, s3, speedTrap;
        try {
            s1 = Float.parseFloat(dataPanel.sectorOneTextField.getValue());
            s2 = Float.parseFloat(dataPanel.sectorTwoTextField.getValue());
            s3 = Float.parseFloat(dataPanel.sectorThreeTextField.getValue());
            speedTrap = Float.parseFloat(dataPanel.speedTrapTextField.getValue());
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error parsing sectors", e);
            return;
        }

        TrackData oldData = trackDataExtension.getTrackData();

        List<Float> newVMap = oldData.getGt3VelocityMap();
        if (dataPanel.enableVMapCheckBox.isSelected()) {
            newVMap = vMap;
        }

        List<Float> newDMap = oldData.getDirectionMap();
        if (dataPanel.enableDMapCheckBox.isSelected()) {
            newDMap = dirMap;
        }

        TrackData newData = new TrackData(oldData.getTrackname(),
                oldData.getTrackMeters(),
                newVMap,
                s1,
                s2,
                s3,
                speedTrap,
                newDMap);
        trackDataExtension.useTrackData(newData);
    }

}

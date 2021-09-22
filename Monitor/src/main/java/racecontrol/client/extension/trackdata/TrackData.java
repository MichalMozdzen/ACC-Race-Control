/*
 * Copyright (c) 2021 Leonard Sch�ngel
 * 
 * For licensing information see the included license (LICENSE.txt)
 */
package racecontrol.client.extension.trackdata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Leonard
 */
public class TrackData
        implements Serializable {

    private final String trackname;

    private final int trackMeters;

    private final List<Float> gt3VelocityMap;

    private final float sectorOneLine;
    private final float sectorTwoLine;
    private final float sectorThreeLine;

    private final List<Float> directionMap;

    public TrackData(String trackname,
            int trackMeters,
            List<Float> gt3VelocityMap,
            float sectorOneLine,
            float sectorTwoLine,
            float sectorThreeLine,
            List<Float> directionMap) {
        this.trackname = trackname;
        this.trackMeters = trackMeters;
        this.gt3VelocityMap = gt3VelocityMap;
        this.sectorOneLine = sectorOneLine;
        this.sectorTwoLine = sectorTwoLine;
        this.sectorThreeLine = sectorThreeLine;
        this.directionMap = directionMap;
    }

    public TrackData(String trackName, int trackLength) {
        this(trackName, trackLength, new ArrayList<>(), 0.33f, 0.66f, 1f, new ArrayList<>());
    }

    public TrackData() {
        this("none", 0, new ArrayList<>(), 0, 0, 0, new ArrayList<>());
    }

    public String getTrackname() {
        return trackname;
    }

    public int getTrackMeters() {
        return trackMeters;
    }

    public List<Float> getGt3VelocityMap() {
        return gt3VelocityMap;
    }

    public float getSectorOneLine() {
        return sectorOneLine;
    }

    public float getSectorTwoLine() {
        return sectorTwoLine;
    }

    public float getSectorThreeLine() {
        return sectorThreeLine;
    }

    public List<Float> getDirectionMap() {
        return directionMap;
    }
}

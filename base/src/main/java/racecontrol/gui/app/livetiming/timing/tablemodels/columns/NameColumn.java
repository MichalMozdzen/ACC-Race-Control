/*
 * Copyright (c) 2021 Leonard Sch�ngel
 * 
 * For licensing information see the included license (LICENSE.txt)
 */
package racecontrol.gui.app.livetiming.timing.tablemodels.columns;

import processing.core.PApplet;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.LEFT;
import static racecontrol.client.extension.statistics.CarProperties.NAME;
import racecontrol.client.extension.statistics.CarStatistics;
import racecontrol.gui.LookAndFeel;
import static racecontrol.gui.LookAndFeel.COLOR_DARK_RED;
import static racecontrol.gui.LookAndFeel.COLOR_WHITE;
import static racecontrol.gui.LookAndFeel.LINE_HEIGHT;
import racecontrol.gui.lpui.table.LPTable;
import racecontrol.gui.lpui.table.LPTableColumn;

/**
 *
 * @author Leonard
 */
public class NameColumn
        extends LPTableColumn {

    public NameColumn() {
        super("Name");
        setMaxWidth(LINE_HEIGHT * 5f);
        setMinWidth(LINE_HEIGHT * 5f);
        setPriority(1000);
        setCellRenderer(this::nameRenderer);
    }

    protected void nameRenderer(PApplet applet, LPTable.RenderContext context) {
        if (!(context.object instanceof CarStatistics)) {
            return;
        }
        String name = ((CarStatistics) context.object).get(NAME);

        if (context.isMouseOverRow) {
            applet.fill(COLOR_DARK_RED);
            applet.rect(1, 1, context.width - 1, context.height - 2);
        }

        applet.fill(COLOR_WHITE);
        applet.textAlign(LEFT, CENTER);
        applet.textFont(LookAndFeel.fontMedium());
        applet.text(name, context.height / 4f, context.height / 2f);
    }

}
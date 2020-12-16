/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.screen.visualisation.components;

import base.screen.Main;
import base.screen.networking.AccBroadcastingClient;
import base.screen.visualisation.LookAndFeel;
import base.screen.visualisation.gui.LPComponent;
import base.screen.visualisation.gui.LPContainer;
import base.screen.visualisation.gui.LPTabPanel;

/**
 *
 * @author Leonard
 */
public class BasePanel extends LPContainer {

    private final LPComponent header;
    private final LPComponent body;

    public BasePanel(AccBroadcastingClient client) {
        header = new HeaderPanel(client);
        addComponent(header);

        LPTabPanel tabs = new LPTabPanel();
        for (LPContainer c : Main.getExtensionPanels()) {
            tabs.addTab(c);
        }
        tabs.setTabIndex(0);
        body = tabs;
        addComponent(body);
    }

    @Override
    public void onResize(int w, int h) {
        int headerSize = LookAndFeel.LINE_HEIGHT;
        header.setSize(w, headerSize);
        header.setPosition(0, 0);

        body.setSize(w, h - headerSize);
        body.setPosition(0, headerSize);
        invalidate();
    }

}

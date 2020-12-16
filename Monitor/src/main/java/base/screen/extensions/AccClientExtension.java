/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.screen.extensions;

import base.screen.networking.AccBroadcastingClient;
import base.screen.visualisation.gui.LPContainer;

/**
 *
 * @author Leonard
 */
public class AccClientExtension {
    /**
     * Base client for this extension.
     */
    protected AccBroadcastingClient client;
    /**
     * The panel that is attached to this extension.
     * If null then no panel is attached.
     */
    protected LPContainer panel = null;
    /**
     * Returns true if there is an extension Panel for this extension.
     * @return If there is an extension.
     */
    public boolean hasPanel(){
        return (panel != null);
    }
    /**
     * Returns the attached panel for this extension.
     * @return The attached panel.
     */
    public LPContainer getPanel(){
        return panel;
    }
    
    public void setClient(AccBroadcastingClient client){
        this.client = client;
    }
      
}

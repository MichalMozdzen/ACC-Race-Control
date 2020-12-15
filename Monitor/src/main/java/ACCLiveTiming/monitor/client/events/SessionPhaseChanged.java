/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acclivetiming.monitor.client.events;

import acclivetiming.monitor.eventbus.Event;
import acclivetiming.monitor.networking.data.SessionInfo;

/**
 *
 * @author Leonard
 */
public class SessionPhaseChanged extends Event {

    private SessionInfo sessionInfo;

    public SessionPhaseChanged(SessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
    }

    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

}

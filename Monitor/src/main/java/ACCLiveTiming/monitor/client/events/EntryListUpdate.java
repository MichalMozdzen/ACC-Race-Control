/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acclivetiming.monitor.client.events;

import acclivetiming.monitor.eventbus.Event;
import java.util.List;

/**
 *
 * @author Leonard
 */
public class EntryListUpdate extends Event {

    private List<Integer> carIds;

    public EntryListUpdate(List<Integer> carIds) {
        this.carIds = carIds;
    }

    public List<Integer> getCarIds() {
        return carIds;
    }

}

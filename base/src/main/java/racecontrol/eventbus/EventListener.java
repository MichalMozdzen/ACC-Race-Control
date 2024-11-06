/*
 * Copyright (c) 2021 Leonard Schüngel
 *
 * For licensing information see the included license (LICENSE.txt)
 */
package racecontrol.eventbus;

/**
 *
 * @author Leonard
 */
public interface EventListener {

    public void onEvent(Event e);
}

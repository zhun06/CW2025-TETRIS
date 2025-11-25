package com.comp2042.logic.data;

import com.comp2042.util.SfxEvent;

import java.util.ArrayDeque;
import java.util.Deque;

/**Stores active special effects events generated during gameplay.*/
public class SfxData {

    private final Deque<SfxEvent> activeSfxEvents = new ArrayDeque<>();

    /**
     * Adds a new special effects event to the queue.
     * @param event the SFX event
     */
    public void update(SfxEvent event) { activeSfxEvents.add(event); }

    /**@return the queue of active sound-effect events*/
    public Deque<SfxEvent> getActiveSfxEvents() { return activeSfxEvents; }
}

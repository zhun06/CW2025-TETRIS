package com.comp2042.logic.data;

import com.comp2042.util.SfxEvent;

import java.util.ArrayDeque;
import java.util.Deque;

public class SfxData {

    private final Deque<SfxEvent> activeSfxEvents = new ArrayDeque<>();

    public void update(SfxEvent event) {activeSfxEvents.add(event);}

    public Deque<SfxEvent> getActiveSfxEvents() {return activeSfxEvents;}

}

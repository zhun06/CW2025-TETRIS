package com.comp2042.logic.moves;

import com.comp2042.util.EventSource;
import com.comp2042.util.EventType;

/**
 * Represents a movement-related event in the game.
 * <p>
 * This immutable object describes what kind of movement occurred
 * and where the event originated from (keyboard, gravity, etc.).
 * </p>
 */
public final class MoveEvent {

    /** The type of movement performed. */
    private final EventType eventType;

    /** The source from which the event originated. */
    private final EventSource eventSource;

    /**
     * Constructs a new {@code MoveEvent}.
     *
     * @param eventType   the type of movement performed
     * @param eventSource the origin of the event
     */
    public MoveEvent(EventType eventType, EventSource eventSource) {
        this.eventType = eventType;
        this.eventSource = eventSource;
    }

    /**
     * Returns the type of event.
     *
     * @return the movement type
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Returns the source of the event. (thread/user)
     *
     * @return the event source
     */
    public EventSource getEventSource() {
        return eventSource;
    }
}

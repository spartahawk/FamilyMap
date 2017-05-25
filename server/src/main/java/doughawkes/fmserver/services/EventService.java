package doughawkes.fmserver.services;

import doughawkes.fmserver.model.Event;
import doughawkes.fmserver.services.request.EventRequest;

/**
 * defines the event service class which is created to return all events for a person on a single event
 */
public class EventService {
    /** creates a new EventService object to take action based on the event request
    public EventService() {

    }

    /**
     *
     * @param r event request object with the event to be looked up
     * @return a single event if an event is in the request object,
     * or all events related to the person in the request object
     */
    public Event[] getEventorEvents(EventRequest r) {
        return null;
    }
}

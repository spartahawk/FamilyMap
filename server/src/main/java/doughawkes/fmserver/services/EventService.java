package doughawkes.fmserver.services;

import java.util.ArrayList;

import doughawkes.fmserver.dataAccess.Database;
import doughawkes.fmserver.model.Event;
import doughawkes.fmserver.services.request.EventRequest;

/**
 * defines the event service class which is created to return all events for a person or a single event
 */
public class EventService {
    private boolean success;

    /** creates an eventService object to deal with the request
     *
     */
    public EventService() {

    }

    /**
     * Finds and returns the single event and their data
     * @param eventID a string with the eventID
     * @return one event
     */
    public Event getEvent(String eventID, String userName) {

        Database database = new Database();

        Event event = database.getEventDao().lookupEvent(eventID);

        if (!event.getDescendant().equals(userName)) {
            System.out.println("This event is of someone in the user's family.");
            database.setAllTransactionsSucceeded(false);
            event = null;
        }

        if (!database.getEventDao().isSuccess()) {
            database.setAllTransactionsSucceeded(false);
            System.out.println("Event lookup failed.");
        }
        else {
            success = true;
            System.out.println("Event lookup success");
        }

        database.endTransaction();
        return event;
    }

    public ArrayList<Event> getUserFamilyEvents(String authTokenString) {
        Database database = new Database();
        String userName = database.getAuthTokenDao().lookup(authTokenString);
        ArrayList<Event> userFamilyEvents = database.getEventDao().lookupAllFamilyEvents(userName);

        if (!database.getEventDao().isSuccess()) {
            database.setAllTransactionsSucceeded(false);
            System.out.println("Events lookup failed.");
        }
        else {
            success = true;
            System.out.println("Events lookup success.");
        }

        database.endTransaction();

        return userFamilyEvents;
    }

    public boolean isSuccess() {
        return success;
    }
}


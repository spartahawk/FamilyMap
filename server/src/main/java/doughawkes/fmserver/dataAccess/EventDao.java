package doughawkes.fmserver.dataAccess;

import java.sql.Connection;
import java.util.ArrayList;

import doughawkes.fmserver.model.Event;
import doughawkes.fmserver.model.User;

/**
 * class that deals with the database and makes changes and lookups
 * for event entries. Lookups can be for a single event or all of the user's ancestors events.
 */
public class EventDao extends Dao {
    Connection connection;

    /**
     * creates new eventDao object to interact with the database
     */
    public EventDao() {

    }

    /**
     * adds an event to the database
     * @param e event of interest
     * @return true or false for success
     */
    boolean addEvent(Event e) {
        return false;
    }

    /**
     * looks up a single event in the database based on the eventID in the event object
     * @param e event of interest
     * @return the event object successfully found
     */
    Event lookupSingleEvent(Event e) {
        return null;
    }

    /**
     * looks up all of the user's family's events in the database by
     * the authtoken provided of the user
     * @param u user of interest
     * @return the collection of all user's family's event objects successfully found
     */
    ArrayList<Event> lookupAllEvents(User u) { return null; }

    /**
     * deletes an event entry in the database
     * @param e event of interest
     * @return the event object successfully deleted
     */
    boolean delete(Event e) {
        return false;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}

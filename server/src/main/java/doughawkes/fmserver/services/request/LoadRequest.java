package doughawkes.fmserver.services.request;


import doughawkes.fmserver.model.Event;
import doughawkes.fmserver.model.Person;
import doughawkes.fmserver.model.User;

/**
 * request object containing data to make a load request for potentially multiple
 * users, persons, and events
 */
public class LoadRequest {
    /** array of User objects based on the request    */
    private User[] users;
    /**  array of Person objects based on the request   */
    private Person[] persons;
    /**  array of Event objects based on the request   */
    private Event[] events;

    /**
     * creates a new load request object will all needed fields
     */
    public LoadRequest() {

    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }

}

package doughawkes.fmserver.services.request;


import java.util.ArrayList;

import doughawkes.fmserver.model.Event;
import doughawkes.fmserver.model.Person;
import doughawkes.fmserver.model.User;

/**
 * request object containing data to make a load request for potentially multiple
 * users, persons, and events
 */
public class LoadRequest {
    /** array of User objects based on the request    */
    private ArrayList<User> users;
    /**  array of Person objects based on the request   */
    private ArrayList<Person> persons;
    /**  array of Event objects based on the request   */
    private ArrayList<Event> events;

    /**
     * creates a new load request object will all needed fields
     */
    public LoadRequest() {

    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
}

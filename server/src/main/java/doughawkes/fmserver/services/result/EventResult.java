package doughawkes.fmserver.services.result;


import java.lang.reflect.Array;
import java.util.ArrayList;

import doughawkes.fmserver.model.Event;

/**
 * This class can either return a single event as part of the successful event request,
 * or it can return all the events of the specified person
 *
 * There are two constructors, one for each scenario
 */
public class EventResult extends Result {
    private ArrayList<Event> events;

    /**  User to which this person belongs   */
    private String descendant;
    /**
     * a unique id for the event
     */
    private String eventID;
    /** the person this event is associated with
     *
     */
    private String personId;
    /** Latitude of event’s location   */
    private double latitude;
    /** Longitude of event’s location   */
    private double longitude;
    /**  Country in which event occurred   */
    private String country;
    /** City in which event occurred   */
    private String city;
    /**   Type of event (birth, baptism, christening, marriage, death)  */
    private String eventType;
    /**  Year in which event occurred   */
    private int year;

    /** creates the eventresult object for the single event as requested
     *
     * @param eventID the unique event identifier
     */
    public EventResult(String eventID) {

    }

    /**
     * creates the eventresult object for all events associated with the person
     * @param events the array of event objects
     */
    public EventResult(ArrayList<Event> events) {

    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getEventId() {
        return eventID;
    }

    public void setEventId(String eventId) {
        this.eventID = eventId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}

package doughawkes.fmserver.model;

/**
 * An event associated with a person, having a lat/long location,
 * country, city, year, and event type
 */
public class Event {
    /**
     * A unique ID for the event
     */
    private int eventID;
    /**  User to which this person belongs   */
    private String descendant;
    /** Person to which this event belongs   */
    private String personID;
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

    /**
     * Creates an event with all associated characteristics
     */
    public Event() {

    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getDescendant() { return descendant; }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
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

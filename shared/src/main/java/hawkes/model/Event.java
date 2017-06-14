package hawkes.model;

/**
 * An event associated with a person, having a lat/long location,
 * country, city, year, and event type
 */
public class Event {
    /**
     * A unique ID for the event
     */
    private String eventID;
    /**  User to which this person belongs   */
    private String descendant;
    /** Person to which this event belongs   */
    private String personID;
    /** Latitude of event’s location   */
    private String latitude;
    /** Longitude of event’s location   */
    private String longitude;
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

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
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

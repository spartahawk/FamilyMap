package hawkes.model.request;

/** a class that creates an event request object in response to a fill request
 */
public class EventRequest {
    /**
     * the unique eventId of the event in the request
     */
    private String eventId;

    /**
     * Creates a new event request object, which will either have a eventID set or not, depending
     * on if a lookup is going to get a single event (eventID provided) or
     * all of the user's family's events (no personID provided/set).
     */
    public EventRequest() {

    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}

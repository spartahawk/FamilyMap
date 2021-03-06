package hawkes.model.result;

import java.util.ArrayList;

import hawkes.model.Event;

/**
 * This class holds and returns the data structure holding an array of all the events of the family
 * members related to the user person who made the request
 *
 */
public class EventResult extends Result {
    /**
     * array that holds multiple (all) events of persons related to this person
     */
    private ArrayList<Event> data;

    public EventResult() {

    }

    public ArrayList<Event> getData() {
        return data;
    }

    public void setData(ArrayList<Event> data) {
        this.data = data;
    }
}
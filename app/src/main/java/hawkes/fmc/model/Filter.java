package hawkes.fmc.model;

import android.widget.Toast;

import java.util.ArrayList;

import hawkes.model.Event;

import static java.security.AccessController.getContext;

/**
 * Created by yo on 6/16/17.
 */

public class Filter {

    private String filterType;
    private boolean isOn;


    public Filter(String filterType) {
        this.filterType = filterType;
        isOn = true; // by default all filters are turned on (its event type will show on map)
    }

    public ArrayList<String> determineEventTypes() {
        Model model = Model.getModel();

        ArrayList<String> eventTypes = new ArrayList<>();

        for (Event event : model.getEvents().values()) {
            if (!eventTypes.contains(event.getEventType())) {
                eventTypes.add(event.getEventType());
            }
        }

        return eventTypes;

    }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean state) {
        isOn = state;
        System.out.println("NOW FILTERING BY " + filterType + isOn);
    }
}

package hawkes.fmc.model;

/**
 * Created by yo on 6/14/17.
 */

import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import hawkes.model.Event;
import hawkes.model.Person;
import hawkes.model.result.LoginResult;
import hawkes.model.result.RegisterResult;


/**
 * This class is a singleton container for the family data of the user, session filters, settings, etc.
 */
public class Model {
    private static Model model;
    private String authtoken;
    private String rootPersonID;
    private LoginResult loginResult;
    private RegisterResult registerResult;
    // map of persons. Key is personID, Value is Person Object
    private TreeMap<String, Person> persons;
    // map of events. Key is eventID, Value is Event object
    private TreeMap<String, Event> events;

    private HashMap<Marker, Event> markerToEventMap = new HashMap<>();

    private String serverHost;
    private String serverPort;

    private Settings settings = new Settings();
    private boolean settingsChanged = false;

    private ArrayList<String> eventTypes = new ArrayList<>();
    private TreeMap<String, Filter> filters = new TreeMap<>();

    private HashSet<Event> filteredEvents = new HashSet<>();

    private Event selectedEvent;

    private TreeMap<String, Person> fatherSide = new TreeMap<>();
    private TreeMap<String, Person> motherSide = new TreeMap<>();

    private TreeMap<String, String> eventTypeColors = new TreeMap<>();


    // constructor is private
    private Model() {

    }

    //returns the instance of the Model
    public static Model getModel() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public Person getPersonByEvent(String personID) {

        try {
            return persons.get(personID);
        } catch (Exception e) {
            //person not found by that personID
            e.printStackTrace();
        }
        return null;
    }

    public void makeFilters() {
        Filter filter = new Filter(""); // this particular filter won't be stored but its method is used.
        eventTypes = filter.determineEventTypes();

        filters.clear();

        //add dynamically created filters based on data provided
        for (String eventType : eventTypes) {
            filters.put(eventType + " Events", new Filter(eventType + " Events"));
        }

        // add default filters
        // the values could maybe be booleans if Filter doesn't need more.
        filters.put("Father's Side", new Filter("Father's Side"));
        filters.put("Mother's Side", new Filter("Mother's Side"));
        filters.put("Male Events", new Filter("Male Events"));
        filters.put("Female Events", new Filter("Female Events"));
    }

    public void applyUpdatedFilters() {
        filteredEvents.clear();

        //Get the paternal and maternal ancestry
        Person rootPerson = persons.get(rootPersonID);

        if (rootPerson.getFather() != null && persons.containsKey(rootPerson.getFather())) {
            Person father = persons.get(rootPerson.getFather());
            fatherSide.put(father.getPersonID(), father);
            findPersonParents(father, fatherSide);
        }
        if (rootPerson.getMother() != null && persons.containsKey(rootPerson.getMother())) {
            Person mother = persons.get(rootPerson.getMother());
            motherSide.put(mother.getPersonID(), mother);
            findPersonParents(mother, motherSide);
        }

        TreeMap<String, Event> parentFilteredEvents =  new TreeMap<>();
        // don't add if they're on the father's side and that filter is off
        if (filters.get("Father's Side").isOn() && !filters.get("Mother's Side").isOn()) {
            for (Event event : events.values()) {
                if (fatherSide.containsKey(event.getPersonID())) {
                    parentFilteredEvents.put(event.getEventID(), event);
                }
            }
        }
        else if (!filters.get("Father's Side").isOn() && filters.get("Mother's Side").isOn()) {
            for (Event event : events.values()) {
                if (motherSide.containsKey(event.getPersonID())) {
                    parentFilteredEvents.put(event.getEventID(), event);
                }
            }
        }
        else if (!filters.get("Father's Side").isOn() && !filters.get("Mother's Side").isOn()) {
            //no events remain
        }
        else {
            parentFilteredEvents = events;
        }

        //gonna have to change the range here to loop over what I get above
        for (Event event : parentFilteredEvents.values()) {
            //dynamically created filters
            // don't add if the event type filter isn't on
            String filterName = event.getEventType() + " Events";
            if (!filters.get(filterName).isOn()) continue;

            // don't add if their gender filter isn't on
            String personID = event.getPersonID();
            char personGender = findPersonGender(personID);
            if (!filters.get("Male Events").isOn() && personGender == 'm') continue;
            if (!filters.get("Female Events").isOn() && personGender == 'f') continue;

            // if the event wasn't filtered out by now it's good to add
            filteredEvents.add(event);

        }

        //todo: remove this an make the selected event based on one clicked in the PersonActivity
        //selectedEvent = filteredEvents.iterator().next();

    }

    private void findPersonParents(Person person, TreeMap familySide) {

        if (person.getFather() != null && persons.containsKey(person.getFather())) {
            Person father = persons.get(person.getFather());
            familySide.put(father.getPersonID(), father);
            findPersonParents(father, familySide);
        }
        if (person.getMother() != null && persons.containsKey(person.getMother())) {
            Person mother = persons.get(person.getMother());
            familySide.put(mother.getPersonID(), mother);
            findPersonParents(mother, familySide);
        }
    }

    public char findPersonGender(String personID) {

        return persons.get(personID).getGender();

    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getRootPersonID() {
        return rootPersonID;
    }

    public void setRootPersonID(String rootPersonID) {
        this.rootPersonID = rootPersonID;
    }

    public TreeMap<String, Person> getPersons() {
        return persons;
    }

    public void setPersons(TreeMap<String, Person> persons) {
        this.persons = persons;
    }

    public TreeMap<String, Event> getEvents() {
        return events;
    }

    public void setEvents(TreeMap<String, Event> events) {
        this.events = events;
        ArrayList<String> eventTypes = new ArrayList<>();

        for (Event event : model.getEvents().values()) {
            if (!eventTypes.contains(event.getEventType())) {
                eventTypes.add(event.getEventType());
            }
        }

        ArrayList<String> colors = new ArrayList<>();
        colors.add("Red");
        colors.add("Green");
        colors.add("Yellow");
        colors.add("Orange");
        colors.add("Blue");
        colors.add("Violet");
        colors.add("Azure");

        int i = 0;
        for (String eventType : eventTypes) {
            eventTypeColors.put(eventType, colors.get(i));
            i++;
            if (i > colors.size()) {
                i = 0;
            }
        }
    }

    public LoginResult getLoginResult() {
        return loginResult;
    }

    public void setLoginResult(LoginResult loginResult) {
        this.loginResult = loginResult;
    }

    public RegisterResult getRegisterResult() {
        return registerResult;
    }

    public void setRegisterResult(RegisterResult registerResult) {
        this.registerResult = registerResult;
    }

    public HashMap<Marker, Event> getMarkerToEventMap() {
        return markerToEventMap;
    }

    public void setMarkerToEventMap(HashMap<Marker, Event> markerToEventMap) {
        this.markerToEventMap = markerToEventMap;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public boolean isSettingsChanged() {
        return settingsChanged;
    }

    public void setSettingsChanged(boolean settingsChanged) {
        this.settingsChanged = settingsChanged;
    }

    public TreeMap<String, Filter> getFilters() {
        return filters;
    }

    public void setFilters(TreeMap<String, Filter> filters) {
        this.filters = filters;
    }

    public HashSet<Event> getFilteredEvents() {
        return filteredEvents;
    }

    public void setFilteredEvents(HashSet<Event> filteredEvents) {
        this.filteredEvents = filteredEvents;
    }

    public Event getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public TreeMap<String, String> getEventTypeColors() {
        return eventTypeColors;
    }

    public void setEventTypeColors(TreeMap<String, String> eventTypeColors) {
        this.eventTypeColors = eventTypeColors;
    }
}
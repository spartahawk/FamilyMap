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
    private ArrayList<Person> persons;
    private ArrayList<Event> events;

    private HashMap<Marker, Event> markerToEventMap = new HashMap<>();

    private String serverHost;
    private String serverPort;

    private Settings settings = new Settings();

    private ArrayList<String> eventTypes = new ArrayList<>();
    private TreeMap<String, Filter> filters = new TreeMap<>();

    private HashSet<Event> filteredEvents = new HashSet<>();




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

    public Person getPersonByEventID(String eventID) {
        for (Person p : persons) {
            if (p.getPersonID().equals(eventID)) {
                return p;
            }
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
        filters.put("Mothers's Side", new Filter("Mothers's Side"));
        filters.put("Male Events", new Filter("Male Events"));
        filters.put("Female Events", new Filter("Female Events"));
    }

    public void applyUpdatedFilters() {
        filteredEvents.clear();



        for (Event event : events) {
            //dynamicly created filters
            // don't add if the event type filter isn't on
            String filterName = event.getEventType() + " Events";
            if (!filters.get(filterName).isOn()) continue;

            // don't add if their gender filter isn't on
            String personID = event.getPersonID();
            char personGender = findPersonGender(personID);
            if (!filters.get("Male Events").isOn() && personGender == 'm') continue;
            if (!filters.get("Female Events").isOn() && personGender == 'f') continue;

            // don't add if they're on the father's side and that filter is off

            // don't add if they're on the mothers's side and that filter is off

            // if the event wasn't filtered out by now it's good to add
            filteredEvents.add(event);

        }
    }

    public char findPersonGender(String personID) {
        for (Person person : persons) {
            if (person.getPersonID().equals(personID)) {
                return person.getGender();
            }
        }
        // should never occur unless somehow the person has no gender in which case we have a problem
        return 'n';
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
}
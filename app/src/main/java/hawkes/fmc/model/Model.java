package hawkes.fmc.model;

/**
 * Created by yo on 6/14/17.
 */

import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;
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
    private LoginResult loginResult;
    private RegisterResult registerResult;
    private ArrayList<Person> persons;
    private ArrayList<Event> events;

    private HashMap<Marker, Event> markerToEventMap = new HashMap<>();

    private String serverHost;
    private String serverPort;

    private Settings settings = new Settings();




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

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
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
}
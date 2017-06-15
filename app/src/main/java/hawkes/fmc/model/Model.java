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
}
package hawkes.fmc.model;

/**
 * Created by yo on 6/14/17.
 */

import java.util.ArrayList;

import hawkes.model.Event;
import hawkes.model.Person;
import hawkes.model.result.LoginResult;

/**
 * This class is a singleton container for the family data of the user, session filters, settings, etc.
 */
public class Model {
    private static Model model;
    private LoginResult loginResult;
    private ArrayList<Person> persons;
    private ArrayList<Event> events;



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
}
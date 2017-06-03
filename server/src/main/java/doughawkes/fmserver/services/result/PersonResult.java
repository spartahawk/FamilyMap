package doughawkes.fmserver.services.result;

import java.util.ArrayList;

import doughawkes.fmserver.model.Person;

/**
 * This class is used to return the data structure with an array of persons for the Gson to
 * encode into JSON for the server to serve up.
 */
public class PersonResult {
    /**
     * array that holds multiple (all) persons related to this person and their info
     */
    private ArrayList<Person> data;

    public PersonResult() {

    }

    public ArrayList<Person> getAllFamily() {
        return data;
    }

    public void setData(ArrayList<Person> data) {
        this.data = data;
    }
}

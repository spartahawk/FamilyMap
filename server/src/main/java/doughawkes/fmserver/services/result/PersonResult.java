package doughawkes.fmserver.services.result;

import java.util.ArrayList;

import doughawkes.fmserver.model.Person;

/**
 * This class can either return a single person as part of the successfull personrequest,
 * or it can return all the family members of the current user, depending on the
 * nature of the request
 *
 * There are two constructors, one for each scenario
 */
public class PersonResult extends Result {
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

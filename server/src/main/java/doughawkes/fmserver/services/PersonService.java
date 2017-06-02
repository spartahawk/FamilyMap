package doughawkes.fmserver.services;

import java.util.ArrayList;

import doughawkes.fmserver.model.Person;

/** class for acting on a request for one person or all that person's family
 *
 */
public class PersonService {
    private boolean success;

    /** creates a personService object to deal with the request
     *
     */
    public PersonService() {

    }

    /**
     * Finds and returns the single person and their data, or all their family and their data
     * @param personID a string with the personID
     * @return one person
     */
    public Person getPerson(String personID) {
        return null;
    }

    public ArrayList<Person> getUserPersons() { return null; }

    public boolean isSuccess() {
        return success;
    }
}

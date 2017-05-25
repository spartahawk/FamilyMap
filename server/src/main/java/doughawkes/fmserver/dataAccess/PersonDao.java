package doughawkes.fmserver.dataAccess;

import java.util.ArrayList;

import doughawkes.fmserver.model.Person;
import doughawkes.fmserver.model.User;

/**
 * class that deals with the database and makes changes and lookups
 * for person entries. Lookups can be for the single person or for all of the user's ancestors.
 */
public class PersonDao extends Dao {
    /**
     * Creates new personDao object to interact with the database
     */
    public PersonDao() {

    }

    /**
     * adds a person to the database
     * @param p person of interest
     * @return true or false for success
     */
    boolean addPerson(Person p) {
        return false;
    }

    /**
     * looks up a SINGLE person in the database by the personID provided in the person object
     * @param p person of interest
     * @return the person object successfully found
     */
    Person lookupSinglePerson(Person p) { return null; }

    /**
     * looks up all of the user's family in the database by
     * the authtoken provided of the user
     * @param u user of interest
     * @return the collection of person objects successfully found for all the user's family
     */
    ArrayList<Person> lookupAllPeople(User u) { return null; }

    /**
     * deletes a person entry in the database
     * @param p person of interest
     * @return the person object successfully deleted
     */
    boolean delete(Person p) {
        return false;
    }

}

package doughawkes.fmserver.services;

import java.util.ArrayList;

import doughawkes.fmserver.dataAccess.Database;
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

        Database database = new Database();
        Person person = database.getPersonDao().lookupPerson(personID);

        if (!database.getPersonDao().isSuccess()) {
            database.setAllTransactionsSucceeded(false);
            System.out.println("Person lookup failed.");
        }
        else {
            success = true;
            System.out.println("Person lookup success");
        }

        database.endTransaction();
        return person;
    }

    public ArrayList<Person> getUserPersons(String authTokenString) {
        Database database = new Database();
        String userName = database.getAuthTokenDao().lookup(authTokenString);
        ArrayList<Person> userPersons = database.getPersonDao().lookupAllPeople(userName);

        if (!database.getPersonDao().isSuccess()) {
            database.setAllTransactionsSucceeded(false);
            System.out.println("Persons lookup failed.");
        }
        else {
            success = true;
            System.out.println("Persons lookup success.");
        }

        database.endTransaction();

        return userPersons;


    }

    public boolean isSuccess() {
        return success;
    }
}

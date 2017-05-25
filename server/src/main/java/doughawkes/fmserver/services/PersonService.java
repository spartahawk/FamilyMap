package doughawkes.fmserver.services;

import doughawkes.fmserver.model.Person;
import doughawkes.fmserver.services.request.PersonRequest;


/** class for acting on a request for one person or all that person's family
 *
 */
public class PersonService {
    /** creates a personService object to deal with the request
     *
     */
    public PersonService() {

    }

    /**
     * Finds and returns the single person and their data, or all their family and their data
     * @param r a person request object containing the person of interest
     * @return one or many persons
     */
    public Person[] person(PersonRequest r) {
        return null;
    }
}

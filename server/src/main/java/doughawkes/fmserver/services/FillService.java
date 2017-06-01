package doughawkes.fmserver.services;

import java.util.Random;
import java.util.UUID;

import doughawkes.fmserver.dataAccess.Database;
import doughawkes.fmserver.model.Person;
import doughawkes.fmserver.services.fromJSON.DataPool;
import doughawkes.fmserver.services.request.FillRequest;
import doughawkes.fmserver.services.result.FillResult;

/** details a class for acting on a fill request based on a particular user entered
 *
 */
public class FillService {
    /**
     * creates a new fillservice object to act on the request to fill the database for the user
     */
    public FillService() {

    }

    /** generates the full database or people and events for the user, who must be registered.
     * Any lingering data for the user is purged prior to filling with new data.     *
     *
     * @param r fillrequest object with the username and needed request info
     * @return a message of success or error with details on why the error
     */
    public FillResult fill(FillRequest r) {

        Database database = new Database();

        //remove all person and event data

        Person person = new Person();


        addParent(person, "male", r.getUserName(), r.getGenerations());
        addParent(person, "female", r.getUserName(), r.getGenerations());

        //Todo: finish this part

        FillResult fillResult = new FillResult();
        // PUT RESULTS IN

        return fillResult;
    }

    private void addParent(Person theirChild, String gender, String userName, int generations) {
        generations--;

        // This will have the personID for the parents, to be used in spouse IDs for them.
        Person currentPerson = generatePerson(gender, theirChild);

        // add this person to the database here, using the gender provided


        addThisPersonsEvents(currentPerson);

        if (generations > 0) {

            // add this person's parents to the database
            //If I wanted to make it random whether they have both parents or not,
            //that can be done by randomly calling only one of two of these.
            addParent(currentPerson, "male", userName, generations);
            addParent(currentPerson, "female", userName, generations);
        }
        return;
    }

    private Person generatePerson(String gender, Person theirChild) {
        //pull random names, etc and plug into a new person and return it
        DataPool dataPool = new DataPool();
        Person person = new Person();

        person.setPersonID(UUID.randomUUID().toString());
        //person.setDescendant();
        // ... Todo: finish all these

        return person;


    }

    private void addThisPersonsEvents(Person currentPerson) {
        //pull random stats and create event of each type

        // next event type

        // next event type


    }
}

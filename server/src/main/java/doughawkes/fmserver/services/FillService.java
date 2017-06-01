package doughawkes.fmserver.services;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import doughawkes.fmserver.dataAccess.Database;
import doughawkes.fmserver.model.Person;
import doughawkes.fmserver.model.User;
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

        //get the user (from the provided username) from the database
        User user = database.getUserDao().findUser(r.getUserName());

        //remove all person and event data associated with that user (descendant)
        // TODO do this.


        // generate this person and its events based on user,
        // and n generations of it's family and their events
        Generator generator = new Generator(user, r.getGenerations());
        ArrayList<Person> persons = generator.getPersons();

        //Todo: in the Generator class, do the events part too.

        // PUT RESULTS INTO THE DATABASE
        for (Person p : persons) {
            database.getPersonDao().addPerson(p);
        }

        FillResult fillResult = new FillResult();
        fillResult.setMessage("Successfully added " + persons.size() + "Persons " +
                              "and TBD Events to the database.");

        database.endTransaction();

        return fillResult;
    }



    private void addThisPersonsEvents(Person currentPerson) {
        //pull random stats and create event of each type

        // next event type

        // next event type


    }
}

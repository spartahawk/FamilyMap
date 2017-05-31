package doughawkes.fmserver.services;

import java.util.ArrayList;

import doughawkes.fmserver.dataAccess.Database;
import doughawkes.fmserver.model.Person;
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




    }

    private boolean addPerson(Person child, int generations) {
        generations--;
        if (generations > 0) {

            String[] personStats = generatePersonStats(child);

            Person currentPerson = new Person(stats);
            addPerson(currentPerson, generations);

            addThisPersonEvents(eventStats);
        }
    }

    private String[] generatePersonStats(Person child) {

    }

    private void addThisPersonEvents(Person currentPerson) {
        String[] eventStats = generateEventStats(currentPerson);

        
    }

    private String[] generateEventStats(Person currentPerson) {

    }
}

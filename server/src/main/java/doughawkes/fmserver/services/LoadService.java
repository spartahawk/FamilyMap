package doughawkes.fmserver.services;

import doughawkes.fmserver.dataAccess.Database;
import hawkes.model.Event;
import hawkes.model.Person;
import hawkes.model.User;
import hawkes.model.request.LoadRequest;
import hawkes.model.result.LoadResult;

/** class that takes action on the loadrequest object specificity
 *
 */
public class LoadService {
    private boolean success;
    /** creates a new load service object for the specific load request
     *
     */
    public LoadService() {

    }

    /**
     *
     * @param r loadrequest object containing a request for a single event of all of a person's events
     * @return either all the events for a person or a single event, and a success message, or error message
     */
    public LoadResult load(LoadRequest r) {
        success = false;
        LoadResult loadResult = new LoadResult();
        Database database = new Database();

        //clear the database first
        ClearService clearService = new ClearService();
        //Todo: maybe change it to date the connection reference so the clear can be rolled back...
        clearService.clear();

        int usersAdded = 0;
        int personsAdded = 0;
        int eventsAdded = 0;
        for (User user : r.getUsers()) {
            database.getUserDao().addUser(user);
            usersAdded++;
        }
        for (Person person : r.getPersons()) {
            database.getPersonDao().addPerson(person);
            personsAdded++;
        }
        for (Event event : r.getEvents()) {
            database.getEventDao().addEvent(event);
            eventsAdded++;
        }

        database.endTransaction();

        String message = "Successfully added "
                         + usersAdded + " users, "
                         + personsAdded + " persons, and "
                         + eventsAdded + " events to the database.";
        loadResult.setMessage(message);

        success = true;
        return loadResult;
    }

    public boolean isSuccess() {
        return success;
    }
}

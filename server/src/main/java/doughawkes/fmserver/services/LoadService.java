package doughawkes.fmserver.services;

import doughawkes.fmserver.dataAccess.Database;
import doughawkes.fmserver.model.Event;
import doughawkes.fmserver.model.Person;
import doughawkes.fmserver.model.User;
import doughawkes.fmserver.services.request.LoadRequest;
import doughawkes.fmserver.services.result.LoadResult;

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

        int usersAdded = 0;
        int personsAdded = 0;
        int eventsAdded = 0;
        for (User user : r.getUsers()) {
            System.out.println(user.getUserName());
            database.getUserDao().addUser(user);
            usersAdded++;
        }
        success = true;
//        for (Person person : r.getPersons()) {
//            success = (success && database.getPersonDao().addPerson(person));
//            personsAdded++;
//        }
//        for (Event event : r.getEvents()) {
//            success = (success && database.getEventDao().addEvent(event));
//            eventsAdded++;
//        }

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

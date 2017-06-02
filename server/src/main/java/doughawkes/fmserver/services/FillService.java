package doughawkes.fmserver.services;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import doughawkes.fmserver.dataAccess.Database;
import doughawkes.fmserver.model.Event;
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
        //Todo: see if I need to do "Fillresult fillresult = null" first, and do initialization in a try
        FillResult fillResult = new FillResult();

        try {
            //get the user (from the provided username) from the database
            User user = database.getUserDao().findUser(r.getUserName());
            if (user.getUserName() == null || user.getUserName().equals("")) {
                System.out.println("USER NOT REGISTERED IN DATABASE.");
                throw new NotFoundException();
            }

            //remove all person and event data associated with that user (descendant)
            database.getPersonDao().delete(user.getUserName());
            database.getEventDao().delete(user.getUserName());


            // generate this person and its events based on user,
            // and n generations of it's family and their events
            Generator generator = new Generator(user, r.getGenerations());
            ArrayList<Person> persons = generator.getPersons();
            ArrayList<Event> events = generator.getEvents();

            // PUT RESULTS INTO THE DATABASE
            for (Person p : persons) {
                database.getPersonDao().addPerson(p);
            }
            for (Event e : events) {
                database.getEventDao().addEvent(e);
            }

            fillResult.setMessage("Successfully added " + persons.size() + " Person(s) and "
                    + events.size() + " Events to the database.");
        } catch (NotFoundException e) {
            fillResult.setMessage("Fill failed because that user is not registered.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
            return fillResult;
        }

    }



    private void addThisPersonsEvents(Person currentPerson) {
        //pull random stats and create event of each type

        // next event type

        // next event type


    }
}

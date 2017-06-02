package doughawkes.fmserver.dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import doughawkes.fmserver.model.Event;
import doughawkes.fmserver.model.User;

/**
 * class that deals with the database and makes changes and lookups
 * for event entries. Lookups can be for a single event or all of the user's ancestors events.
 */
public class EventDao extends Dao {
    Connection connection;

    /**
     * creates new eventDao object to interact with the database
     */
    public EventDao() {

    }

    /**
     * adds an event to the database
     * @param event event of interest
     * @return true or false for success
     */
    public boolean addEvent(Event event) {
        PreparedStatement stmt = null;
        boolean success = false;

        try {

            String sql = "insert into event " +
                    "(eventID, descendant, personID, latitude, longitude," +
                    "country, city, eventType, year) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            stmt = connection.prepareStatement(sql);

            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getDescendant());
            stmt.setString(3, event.getPersonID());
            stmt.setString(4, event.getLatitude());
            stmt.setString(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            //System.out.println("About to execute update to add event to database.");
            if  (stmt.executeUpdate() == 1) {
                //System.out.println("Event entry added to database, pending transaction commit.");
                success = true;
            }
            else throw new SQLException();

        } catch (SQLException e) {
            System.out.println("Adding person failed.");
            e.printStackTrace();
        }
        finally {
            if (stmt != null) try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    /**
     * looks up a single event in the database based on the eventID in the event object
     * @param e event of interest
     * @return the event object successfully found
     */
    Event lookupSingleEvent(Event e) {
        return null;
    }

    /**
     * looks up all of the user's family's events in the database by
     * the authtoken provided of the user
     * @param u user of interest
     * @return the collection of all user's family's event objects successfully found
     */
    ArrayList<Event> lookupAllEvents(User u) { return null; }

    /**
     * deletes an event entry in the database
     * @param username name of user whose family events are to be deleted from database
     * @return true for success or false for failure
     */
    public boolean delete(String username) {
        //Todo: factor this method and the similar one in PersonDao out to another class they can share
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            String sql = "delete from event where descendant = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);

            int entriesDeleted = stmt.executeUpdate();
            if  (entriesDeleted >= 0) {
                success = true;
            }
            else {
                System.out.println("STMT.EXECUTEUPDATE = " + entriesDeleted);
                throw new SQLException();
            }

        } catch (SQLException e) {
            System.out.println("Deleting that username's family events failed.");
            e.printStackTrace();
        }
        finally {
            if (stmt != null) try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    public boolean clear() {
        PreparedStatement stmt = null;

        try {
            String sql = "delete from event";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}

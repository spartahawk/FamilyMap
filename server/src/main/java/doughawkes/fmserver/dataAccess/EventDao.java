package doughawkes.fmserver.dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import doughawkes.fmserver.model.Event;

/**
 * class that deals with the database and makes changes and lookups
 * for event entries. Lookups can be for a single event or all of the user's ancestors events.
 */
public class EventDao {
    Connection connection;
    boolean success;

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
     * looks up a SINGLE event in the database by the eventID provided in the event object
     * @param eventID string of eventID for the event to look up
     * * @return the event object successfully found
     */
    public Event lookupEvent(String eventID) {
        success = false;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Event event = new Event();

        String sql = "select * from event where eventid = ?";

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();

            while (rs.next()) {
                event.setEventID(rs.getString(2));
                event.setDescendant(rs.getString(3));
                event.setPersonID(rs.getString(4));
                event.setLatitude(rs.getString(5));
                event.setLongitude(rs.getString(6));
                event.setCountry(rs.getString(7));
                event.setCity(rs.getString(8));
                event.setEventType(rs.getString(9));
                event.setYear(rs.getInt(10));
            }

        } catch (SQLException e) {
            System.out.println("Event lookup failed within the eventDao. SQLException.");
            e.printStackTrace();
        }
        finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        success = true;
        return event;
    }

    /**
     * looks up all of the user's family's events in the database by
     * the authtoken provided of the user
     * @param userName username of descendant
     * @return the collection of event objects successfully found for all the user's family
     */
    public ArrayList<Event> lookupAllFamilyEvents(String userName) {
        success = false;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Event> events = new ArrayList<>();

        String sql = "select * from event where descendant = ?";

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, userName);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Event event = new Event();

                event.setEventID(rs.getString(2));
                event.setDescendant(rs.getString(3));
                event.setPersonID(rs.getString(4));
                event.setLatitude(rs.getString(5));
                event.setLongitude(rs.getString(6));
                event.setCountry(rs.getString(7));
                event.setCity(rs.getString(8));
                event.setEventType(rs.getString(9));
                event.setYear(rs.getInt(10));

                events.add(event);
            }

        } catch (SQLException e) {
            System.out.println("Event lookup failed within the eventDao. SQLException.");
            success = false;
            e.printStackTrace();
        }
        finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        success = true;
        return events;
    }

    /**
     * deletes an event entry in the database
     * @param username name of user whose family events are to be deleted from database
     * @return true for success or false for failure
     */
    public boolean delete(String username) {
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

    public boolean isSuccess() {
        return success;
    }
}

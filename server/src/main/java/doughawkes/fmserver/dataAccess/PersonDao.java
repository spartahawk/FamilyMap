package doughawkes.fmserver.dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import doughawkes.fmserver.model.Person;
import doughawkes.fmserver.model.User;

/**
 * class that deals with the database and makes changes and lookups
 * for person entries. Lookups can be for the single person or for all of the user's ancestors.
 */
public class PersonDao extends Dao {
    Connection connection;

    /**
     * Creates new personDao object to interact with the database
     */
    public PersonDao() {

    }

    /**
     * adds a person to the database
     * @param p person of interest
     * @return true or false for success
     */
    public boolean addPerson(Person p) {
        PreparedStatement stmt = null;
        boolean success = false;

        try {

            String sql = "insert into person " +
                    "(personid, descendant, firstname, lastname," +
                    "gender, father, mother, spouse) values (?, ?, ?, ?, ?, ?, ?, ?)";

            stmt = connection.prepareStatement(sql);

            stmt.setString(1, p.getPersonID());
            stmt.setString(2, p.getDescendant());
            stmt.setString(3, p.getFirstName());
            stmt.setString(4, p.getLastName());
            stmt.setString(5, Character.toString(p.getGender()));
            stmt.setString(6, p.getFather());
            stmt.setString(7, p.getMother());
            stmt.setString(8, p.getSpouse()); //Todo null might not work here, or does it become empty String?

            //System.out.println("About to execute update to add person to database.");
            if  (stmt.executeUpdate() == 1) {
                //System.out.println("Person entry added to database, pending transaction commit.");
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
     * looks up a SINGLE person in the database by the personID provided in the person object
     * @param p person of interest
     * @return the person object successfully found
     */
    Person lookupSinglePerson(Person p) { return null; }

    /**
     * looks up all of the user's family in the database by
     * the authtoken provided of the user
     * @param u user of interest
     * @return the collection of person objects successfully found for all the user's family
     */
    ArrayList<Person> lookupAllPeople(User u) { return null; }

    /**
     * deletes a person entry in the database
     * @param username the name of the user whose family is to be removed from the database
     * @return true for success or false for failure
     */
    public boolean delete(String username) {
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            String sql = "delete from person where descendant = ?";
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
            System.out.println("Deleting that username's family failed.");
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

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}

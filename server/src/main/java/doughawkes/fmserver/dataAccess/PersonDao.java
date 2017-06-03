package doughawkes.fmserver.dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import doughawkes.fmserver.model.Person;
import doughawkes.fmserver.model.User;

/**
 * class that deals with the database and makes changes and lookups
 * for person entries. Lookups can be for the single person or for all of the user's ancestors.
 */
public class PersonDao {
    Connection connection;
    boolean success;

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
            stmt.setString(8, p.getSpouse());
            // Todo ^ null might not work here on spouse if empty, or does it become empty String?

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
     * @param personID string of personID for the person to look up
     * * @return the person object successfully found
     */
    public Person lookupPerson(String personID) {
        success = false;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Person person = new Person();

        String sql = "select * from person where personid = ?";

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, personID);
            rs = stmt.executeQuery();

            while (rs.next()) {
                person.setPersonID(rs.getString(2));
                person.setDescendant(rs.getString(3));
                person.setFirstName(rs.getString(4));
                person.setLastName(rs.getString(5));
                person.setGender(rs.getString(6).charAt(0));
                person.setFather(rs.getString(7));
                person.setMother(rs.getString(8));
                person.setSpouse(rs.getString(9));
            }

        } catch (SQLException e) {
            System.out.println("Person lookup failed within the personDao. SQLexception.");
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
        return person;
    }

    /**
     * looks up all of the user's family in the database by
     * the authtoken provided of the user
     * @param userName username of descendant
     * @return the collection of person objects successfully found for all the user's family
     */
    public ArrayList<Person> lookupAllPeople(String userName) {
        success = false;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Person> persons = new ArrayList<>();

        String sql = "select * from person where descendant = ?";

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, userName);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Person person = new Person();

                person.setPersonID(rs.getString(2));
                person.setDescendant(rs.getString(3));
                person.setFirstName(rs.getString(4));
                person.setLastName(rs.getString(5));
                person.setGender(rs.getString(6).charAt(0));
                person.setFather(rs.getString(7));
                person.setMother(rs.getString(8));
                person.setSpouse(rs.getString(9));

                persons.add(person);
            }

        } catch (SQLException e) {
            System.out.println("Person lookup failed within the personDao. SQLexception.");
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
        return persons;
    }

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

    public boolean clear() {
        PreparedStatement stmt = null;

        try {
            String sql = "delete from person";
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

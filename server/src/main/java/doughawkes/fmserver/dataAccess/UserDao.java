package doughawkes.fmserver.dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import doughawkes.fmserver.model.User;

/**
 * This class interacts with the database in regard to user creation, user lookup,
 * and user deletion
 */
public class UserDao extends Dao {
    Connection connection;
    /**
     * the user object being translated into database entry
     */
    private User u;

    /**
     * This creates a new userDao object for interacting with the database
     * @param u the user of interest
     */
    public UserDao(User u) {
        this.u = u;
    }

    // for lookups from a login, no user is provided, so an empty constructor is needed.
    public UserDao() {}

    public User getU() {
        return u;
    }

    public void setU(User u) {
        this.u = u;
    }

    /**
     * adds a new user in the database
     * @param u the User object to be translated into the database
     * @return true or false based on database success
     */
    public boolean addUser(User u) {

        PreparedStatement stmt = null;
        boolean success = false;

        try {
            String sql = "insert into user " +
                         "(username, password, email, firstname, lastname," +
                         "gender, personid) values (?, ?, ?, ?, ?, ?, ?)";

            stmt = connection.prepareStatement(sql);

            System.out.println("this user's gender: " + Character.toString(u.getGender()));
            System.out.println("this user's personID: " + u.getPersonId());

            stmt.setString(1, u.getUserName());
            stmt.setString(2, u.getPassword());
            stmt.setString(3, u.getEmail());
            stmt.setString(4, u.getFirstName());
            stmt.setString(5, u.getLastName());
            stmt.setString(6, Character.toString(u.getGender()));
            stmt.setString(7, u.getPersonId());

            System.out.println("About to execute update to add user to database.");
            if  (stmt.executeUpdate() == 1) {
                System.out.println("User entry added to database, pending transaction commit.");
                success = true;
            }
            else throw new SQLException();

        } catch (SQLException e) {
            System.out.println("Adding a user failed.");
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
     * looks up the specified user based on username
     * @param userName the string representing the username
     * @return the User object
     */
    public User findUser(String userName) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User user = new User();

        try {
            String sql = "select * from user where username = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, userName);
            rs = stmt.executeQuery();

            while (rs.next()) {
                user.setUserName(rs.getString(1));
                user.setPassword(rs.getString(2));
                user.setEmail(rs.getString(3));
                user.setFirstName(rs.getString(4));
                user.setLastName(rs.getString(5));
                user.setGender(rs.getString(6).charAt(0));
                user.setPersonId(rs.getString(7));
            }

        } catch (SQLException e) {
            System.out.println("Query for user lookup by userName failed.");
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
        return user;
    }

    /**
     * deletes the specified user from the database
     * @param username the string representing the username
     * @return true or false based on database success
     */
    public boolean deleteUser(String username) {
        return false;
    }

    public boolean clear() {
        PreparedStatement stmt = null;

        try {
            String sql = "delete from user";
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

package doughawkes.fmserver.dataAccess;

import java.sql.Connection;

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

    /**
     * creates a new user in the database
     * @param u the User object to be translated into the database
     * @return true or false based on database success
     */
    public boolean createUser(User u) {
        return false;
    }

    /**
     * looks up the specified user based on username
     * @param userName the string representing the username
     * @return the User object
     */
    public User findUser(String userName) {
        return null;
    }

    /**
     * deletes the specified user from the database
     * @param username the string representing the username
     * @return true or false based on database success
     */
    public boolean deleteUser(String username) {
        return false;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}

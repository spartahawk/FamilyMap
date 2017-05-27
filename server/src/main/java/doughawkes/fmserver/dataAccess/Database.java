package doughawkes.fmserver.dataAccess;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class that contains potentially multiple specified Dao objects so that they can share a connection
 */
public class Database {
    private Connection connection;

    private AuthTokenDao authTokenDao;
    private EventDao eventDao;
    private PersonDao personDao;
    private UserDao userDao;

    /**
     * Constructor for the Database class as a container for instantiated specific Dao objects
     */
    public Database() {

    }

// this is being loaded in the Server main method.
/*    public boolean startDriver(String db) {
        return false;
    }*/

    public void startTransaction() {
        String dbName = "server" + File.separator + "db" + File.separator + "familyMap.sqlite";
        String connectionURL = "jdbc:sqlite:" + dbName;
        connection = null;
        try {
            // Open a database connection
            connection = DriverManager.getConnection(connectionURL);
            // Start a transaction
            //connection.setAutoCommit(false);
        }
        catch (SQLException e) {
            System.out.println("database driver issue");
            e.printStackTrace();
        }
    }

    public boolean endTransaction() {
        return false;  // THIS IS ONLY A FILLER, CHANGE IT, DEPENDING.
    }

    public AuthTokenDao getAuthTokenDao() {
        return authTokenDao;
    }

    public void setAuthTokenDao(AuthTokenDao authTokenDao) {
        this.authTokenDao = authTokenDao;
    }

    public EventDao getEventDao() {
        return eventDao;
    }

    public void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    public PersonDao getPersonDao() {
        return personDao;
    }

    public void setPersonDao(PersonDao personDao) {
        this.personDao = personDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}

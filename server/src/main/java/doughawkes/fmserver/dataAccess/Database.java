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

    private boolean allTransactionsSucceeded;

    /**
     * Constructor for the Database class as a container for instantiated specific Dao objects
     */
    public Database() {
        allTransactionsSucceeded = true;

    }

    public void startTransaction() {
        String dbName = "server/db/familyMap.sqlite";
        String connectionURL = "jdbc:sqlite:" + dbName;
        connection = null;
        try {
            // Open a database connection
            connection = DriverManager.getConnection(connectionURL);
            System.out.println("Database opened successfully");
            // Start a transaction (this is how it's done)
            // Not sure if I want the transaction to start here or in each dao.
            // I think I want to have each database either commit or rollback all changes.
            connection.setAutoCommit(false);
        }
        catch (SQLException e) {
            System.out.println("database driver issue");
            e.printStackTrace();
        }
    }

    public boolean endTransaction() {
        try {
            if (allTransactionsSucceeded) {
                connection.commit();
            }
            else {
                connection.rollback();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could neither commit nor rollback connection!");
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        connection = null;
        return allTransactionsSucceeded;  // THIS IS ONLY A FILLER, CHANGE IT, DEPENDING.
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

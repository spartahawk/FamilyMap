package doughawkes.fmserver.dataAccess;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

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
        startTransaction();

        authTokenDao = new AuthTokenDao();
        authTokenDao.setConnection(connection);

        eventDao = new EventDao();
        eventDao.setConnection(connection);

        personDao = new PersonDao();
        personDao.setConnection(connection);

        userDao = new UserDao();
        userDao.setConnection(connection);

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
            System.out.println("Transaction started.");
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
                System.out.println("Transaction committed.");
            }
            else {
                connection.rollback();
                System.out.println("Transaction rolled back.");
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
        System.out.println("Connection to database closed.");
        return allTransactionsSucceeded;  // THIS IS ONLY A FILLER, CHANGE IT, DEPENDING.
    }

    public AuthTokenDao getAuthTokenDao() {
        return authTokenDao;
    }

    public EventDao getEventDao() {
        return eventDao;
    }

    public PersonDao getPersonDao() {
        return personDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setAllTransactionsSucceeded(boolean allTransactionsSucceeded) {
        this.allTransactionsSucceeded = allTransactionsSucceeded;
    }

    public boolean clearAll() {
        PreparedStatement stmt = null;
        boolean success = false;
        String sql = "";

        try {
            File file = new File("server/db/schema.txt");
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            Scanner scanner = new Scanner(bis);
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine());
                sb.append("\n");
            }
            sql = sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            stmt = connection.prepareStatement(sql);

            System.out.println("About to execute update to rebuild database.");
            int execUpdate = stmt.executeUpdate();
            System.out.println(execUpdate);
//            if  (execUpdate > 0) {
//                System.out.println("Database rebuilt.");
//                success = true;
//            }
//            else throw new SQLException();

        } catch (SQLException e) {
            System.out.println("Database clearing failed.");
            success = false;
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
}

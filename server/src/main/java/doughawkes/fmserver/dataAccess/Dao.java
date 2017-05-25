package doughawkes.fmserver.dataAccess;

import java.sql.Connection;

/**
 * superclass for specific database access object classes
 */
public class Dao {
    /**
     * Creates a new Dao object (this may be abstract and only implemented
     * in specific Dao objects instead
     */
    public Dao() {

    }

    /**
     *
     * @param connection connection object for the database
     * @return true or false if connection (setAutoCommit false)
     * command successful
     */
    public boolean startTransaction(Connection connection) {
        return false;
    }

    public boolean commmit(Connection connection) {
        return false;
    }

    public boolean rollback(Connection connection) {
        return false;
    }




}

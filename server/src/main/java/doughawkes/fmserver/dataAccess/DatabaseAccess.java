package doughawkes.fmserver.dataAccess;

/**
 * Class that contains potentially multiple specified Dao objects so that they can share a connection
 */
public class DatabaseAccess {
    private UserDao uDao;
    private PersonDao pDao;
    private EventDao eDao;
    private AuthTokenDao atDao;

    /**
     * Constructor for the DatabaseAccess class as a container for instantiated specific Dao objects
     */
    public DatabaseAccess() {

    }

    /**
     * Loads the database driver
     * @param db the database driver to load
     * @return successful driver load or failure
     */
    public boolean startDriver(String db) {
        return false;
    }

}

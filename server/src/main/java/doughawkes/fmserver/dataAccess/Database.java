package doughawkes.fmserver.dataAccess;

/**
 * Class that contains potentially multiple specified Dao objects so that they can share a connection
 */
public class Database {
    private AuthTokenDao atDao;
    private EventDao eDao;
    private PersonDao pDao;
    private UserDao uDao;

    /**
     * Constructor for the Database class as a container for instantiated specific Dao objects
     */
    public Database() {

    }

    /**
     * Loads the database driver
     * @param db the database driver to load
     * @return successful driver load or failure
     */
    public boolean startDriver(String db) {
        return false;
    }

    public void startTransaction() {

    }

    public boolean endTransaction() {
        return false;  // THIS IS ONLY A FILLER, CHANGE IT, DEPENDING.
    }

    public AuthTokenDao getAtDao() {
        return atDao;
    }

    public void setAtDao(AuthTokenDao atDao) {
        this.atDao = atDao;
    }

    public EventDao geteDao() {
        return eDao;
    }

    public void seteDao(EventDao eDao) {
        this.eDao = eDao;
    }

    public PersonDao getpDao() {
        return pDao;
    }

    public void setpDao(PersonDao pDao) {
        this.pDao = pDao;
    }

    public UserDao getuDao() {
        return uDao;
    }

    public void setuDao(UserDao uDao) {
        this.uDao = uDao;
    }



}

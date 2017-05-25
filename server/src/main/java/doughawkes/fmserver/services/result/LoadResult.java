package doughawkes.fmserver.services.result;


public class LoadResult extends Result {
    /** The number of successfully added users   */
    private int users;
    /** The number of successfully added persons   */
    private int persons;
    /** The number of successfully added events   */
    private int events;
    /** Message to be given upon successful completion of the request   */
    private String successMessage;
    /** Message to be given upon failed completion of the request    */
    private String errorMessage;

    /**
     * Creates a new LoadResult object with all fields
     */
    public LoadResult() {

    }

    public int getUsers() {
        return users;
    }

    public void setUsers(int users) {
        this.users = users;
    }

    public int getPersons() {
        return persons;
    }

    public void setPersons(int persons) {
        this.persons = persons;
    }

    public int getEvents() {
        return events;
    }

    public void setEvents(int events) {
        this.events = events;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

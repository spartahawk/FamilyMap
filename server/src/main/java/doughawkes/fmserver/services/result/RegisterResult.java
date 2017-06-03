package doughawkes.fmserver.services.result;

/**
 * this class holds all information for the returned object after a register request
 * has been made and processed
 */
public class RegisterResult {
    /** Non-empty auth token string    */
    private String authToken;
    /** User name passed in with request    */
    private String userName;
    /** Non-empty string containing the Person ID of the user’s generated Person object    */
    private String personId;

    /**
     * Creates a new RegisterResult object without instantiating fields
     */
    public RegisterResult() {

    }

    /**
     * Constructor for initiating fields
     * @param authToken the unique token generated at login
     * @param userName name of the user who has logged in
     * @param personId the unique id of the person who represents the user
     */
    public RegisterResult(String authToken, String userName, String personId) {
        this.authToken = authToken;
        this.userName = userName;
        this.personId = personId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }
}

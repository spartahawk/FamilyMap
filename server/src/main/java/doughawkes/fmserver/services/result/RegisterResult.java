package doughawkes.fmserver.services.result;

/**
 * this class holds all information for the returned object after a register request
 * has been made and processed
 */
public class RegisterResult extends Result {
    /** Non-empty auth token string    */
    private String authToken;
    /** User name passed in with request    */
    private String userName;
    /** Non-empty string containing the Person ID of the user’s generated Person object    */
    private String personId;

    /**
     * Creates a new RegisterResult object with all needed fields
     */
    public RegisterResult() {

    }

    public RegisterResult(String authToken, String userName, String personId) {
        this.authToken = authToken;
        this.userName = userName;
        this.personId = personId;
    }

    /**
     *
     * @param authToken Non-empty auth token string
     * @param userName  User name passed in with request
     * @param personId Non-empty string containing the Person ID of the user’s generated Person object
     * @return the response body with the three listed fields
     */
    String successResponse(String authToken, String userName, int personId) {
        return null;
    }

    /**
     * contains the RegisterResult error cause to be returned upon error
     * @param errorCause what data of the request caused failure
     * @return the error message
     */
    public String errorResponse(String errorCause) {
        return null;
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

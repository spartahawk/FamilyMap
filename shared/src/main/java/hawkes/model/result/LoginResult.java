package hawkes.model.result;

/**
 * this class holds all information for the returned object after a
 * login request has been made and processed successfully or not
 */
public class LoginResult extends Result {
    /** Non-empty auth token string    */
    private String authToken;
    /** User name passed in with request    */
    private String userName;
    /** Non-empty string containing the Person ID of the user’s generated Person object    */
    private String personId;
    /** Error message will exist if login failed   */
    private String message;

    /**
     * creates loginresult object to be returned after login request fulfilled or error
     */
    public LoginResult() {

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

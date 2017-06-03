package doughawkes.fmserver.services.result;

/**
 * this class holds all information for the returned object after a
 * login request has been made and processed successfully or not
 */
public class LoginResult {
    /** Non-empty auth token string    */
    private String authToken;
    /** User name passed in with request    */
    private String userName;
    /** Non-empty string containing the Person ID of the user’s generated Person object    */
    private String personId;

    /**
     * creates loginresult object to be returned after login request fulfilled or error
     */
    public LoginResult() {

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

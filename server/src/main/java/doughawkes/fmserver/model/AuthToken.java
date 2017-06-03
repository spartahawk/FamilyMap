package doughawkes.fmserver.model;

/**
 * A unique authorization token generation when the user successfully logs in.
 * Includes an associated timestamp which will expire after an appropriate time.
 * Each authToken is associated with a person, and there may be multiple authtokens per user.
 */
public class AuthToken {
    /**The unique authorization token  */
    private String token;
    /** The unique username (do usernames need to be unique? for the user
     * associated with this authtoken */
    private String userName;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }
}

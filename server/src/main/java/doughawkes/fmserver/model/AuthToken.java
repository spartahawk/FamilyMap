package doughawkes.fmserver.model;

/**
 * A unique authorization token generation when the user successfully logs in.
 * Includes an associated timestamp which will expire after an appropriate time.
 * Each authToken is associated with a person, and there may be multiple authtokens per user.
 */
public class AuthToken {
    /**The unique authorization token  */
    private String token;
    /**date and time of token generation at login  */
    // SQL TABLE IS HANDLING THIS COMPLETELY
    //private Timestamp timeStamp;
    /** The unique username (do usernames need to be unique? for the user
     * associated with this authtoken */
    private String userName;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

/*    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }*/

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

//    public int getPersonId() {
//        return personId;
//    }
//
//    public void setPersonId(int personId) {
//        this.personId = personId;
//    }

    public AuthToken() {
        // TBD
    }
}

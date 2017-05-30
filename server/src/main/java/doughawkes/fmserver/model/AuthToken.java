package doughawkes.fmserver.model;

import java.sql.Timestamp;

/**
 * A unique authorization token generation when the user successfully logs in.
 * Includes an associated timestamp which will expire after an appropriate time.
 * Each authToken is associated with a person, and there may be multiple authtokens per user.
 */
public class AuthToken {
    /**unique id for the token (used in database table)  */
    private int id;
    /**The unique authorization token  */
    private String token;
    /**date and time of token generation at login  */
    private Timestamp timeStamp;
//    /**The unique person associated with this user and authtoken  */
//    private int personId;
    /** The unique username (do usernames need to be unique? for the user
     * associated with this authtoken */
    private String userName;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

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

package doughawkes.fmserver.services.request;

/**
 * this class contains all information submitted as part of a login request
 */
public class LoginRequest {
    /** Non-empty string for username   */
    private String userName;
    /**  Non-empty string for user password   */
    private String password;

    /**
     * creates a new login request object will all required fields
     */
    public LoginRequest() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

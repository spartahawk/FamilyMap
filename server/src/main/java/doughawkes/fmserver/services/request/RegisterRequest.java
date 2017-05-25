package doughawkes.fmserver.services.request;

/**
 * this class contains all information submitted as part of a register request
 */
public class RegisterRequest {
    /** user name for request    */
    private String userName;
    /** user's password    */
    private String password;
    /** user's email    */
    private String email;
    /**  user's first name   */
    private String firstName;
    /** person's last name    */
    private String lastName;
    /** person's gender    */
    private String gender;

    /** creates a new register request objet will all required fields */
    public RegisterRequest() {

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

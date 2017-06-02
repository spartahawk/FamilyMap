package doughawkes.fmserver.model;

/**
 * A unique user able to login and make requests to the family map server
 */
public class User {
    /**Unique user name (non-empty string)  */
    private String userName;
    /** User’s password (non-empty string)  */
    private String password;
    /** User’s email address (non-empty string)  */
    private String email;
    /** User’s first name (non-empty string)  */
    private String firstName;
    /** User’s last name (non-empty string)  */
    private String lastName;
    /** User’s gender (Male or Female)  */
    private char gender;
    /** Unique Person ID assigned to this user’s generated Person object - see Family
     History Information section in spec for details (non-empty string)  */
    private String personID;

    /**
     * creates a user with no initialized fields
     */
    public User() {

    }
    /**
     * creates a user with all required fields
     */
    public User(String userName, String password, String email,
                String firstName, String lastName, char gender, String personID) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

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

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getPersonId() {
        return personID;
    }

    public void setPersonId(String personID) {
        this.personID = personID;
    }
}

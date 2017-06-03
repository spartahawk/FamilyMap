package doughawkes.fmserver.model;

/**
 * A unique person with basic details as well as potential family
 */
public class Person {
    /** Unique identifier for this person     */
    private String personID;
    /** User to which this person belongs     */
    private String descendant;
    /** User’s first name (non-empty string)  */
    private String firstName;
    /** User’s last name (non-empty string)  */
    private String lastName;
    /** User’s gender (Male or Female)  */
    private char gender;
    /**  Person’s father (possibly null)    */
    private String father;
    /**  Person’s mother (possibly null)    */
    private String mother;
    /** Person’s spouse (possibly null)    */
    private String spouse;

    /** creates a new person object for storage of person details */
    public Person() {

    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) { this.personID = personID; }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
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

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }
}

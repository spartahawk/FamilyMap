package doughawkes.fmserver.services.result;

import doughawkes.fmserver.model.Person;

/**
 * This class can either return a single person as part of the successfull personrequest,
 * or it can return all the family members of the current user, depending on the
 * nature of the request
 *
 * There are two constructors, one for each scenario
 */
public class PersonResult extends Result {
    /**
     * array that holds multiple (all) persons related to this person and their info
     */
    private Person[] allFamily;


    /** User to which this person belongs     */
    private int descendant;
    /** unique person ID
     *
     */
    private int personId;
    /** User’s first name (non-empty string)  */
    private String firstName;
    /** User’s last name (non-empty string)  */
    private String lastName;
    /** User’s gender (Male or Female)  */
    private char gender;
    /** ID of Person’s father (possibly null)    */
    private int father;
    /**  ID of Person’s mother (possibly null)    */
    private int mother;
    /** ID of Person’s spouse (possibly null)    */
    private int spouse;
    /** Message to be given upon successful completion of the request   */
    private String successMessage;
    /** Message to be given upon failed completion of the request    */
    private String errorMessage;

    /**
     * constructor for PersonResult object to be returned when all of person's family is requested
     * @param familyData array that holds multiple (all) persons related to this person and their info
     */
    public PersonResult(Person[] familyData) {
    // TBD
    }

    /**
     *
     * @param id the unique personId of the person
     */
    public PersonResult(int id) {
        //TBD
    }

    public Person[] getAllFamily() {
        return allFamily;
    }

    public void setAllFamily(Person[] allFamily) {
        this.allFamily = allFamily;
    }

    public int getDescendant() {
        return descendant;
    }

    public void setDescendant(int descendant) {
        this.descendant = descendant;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
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

    public int getFather() {
        return father;
    }

    public void setFather(int father) {
        this.father = father;
    }

    public int getMother() {
        return mother;
    }

    public void setMother(int mother) {
        this.mother = mother;
    }

    public int getSpouse() {
        return spouse;
    }

    public void setSpouse(int spouse) {
        this.spouse = spouse;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

package doughawkes.fmserver.model;

/**
 * A unique person with first and last names as well as potential family
 */
public class Person {
    /** Unique identifier for this person     */
    private int id;
    /** User to which this person belongs     */
    private int descendant;
    /** User’s first name (non-empty string)  */
    private String firstName;
    /** User’s last name (non-empty string)  */
    private String lastName;
    /** User’s gender (Male or Female)  */
    private char gender;
    /**  Person’s father (possibly null)    */
    private int father;
    /**  Person’s mother (possibly null)    */
    private int mother;
    /** Person’s spouse (possibly null)    */
    private int spouse;

    /** creates a new person with all required fields */
    public Person() {
        // TBD
    }

    public Person(String[] personStats) {
        this.descendant = personStats[0];
        this.firstName = personStats[]
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDescendant() {
        return descendant;
    }

    public void setDescendant(int descendant) {
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
}

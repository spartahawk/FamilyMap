package doughawkes.fmserver.services.request;

/**
 * This class defines an object created when a fill request is made, and
 *  Populates the server's database with generated data for the specified user name.
 */
public class FillRequest {
    /**
     * The username whose family data will be generated
     * No getter method for userName since it is required and set by the constructor.
     */
    private String userName;
    /**
     * The number of generations of family member data to generate for the user
     */
    private int generations;

    public FillRequest(String userName) {
        this.userName = userName;
    }

    public FillRequest(String userName, int generations) {
        this.userName = userName;
        this.generations = generations;
    }

    public String getUserName() {
        return userName;
    }

    public int getGenerations() {
        return generations;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }
}

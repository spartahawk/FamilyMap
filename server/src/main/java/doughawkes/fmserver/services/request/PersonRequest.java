package doughawkes.fmserver.services.request;


public class PersonRequest {
    /** the unique person Id associated with the person in the request */
    private String personId;

    /**
     * creates a new person request object, which will either have a personID set or not, depending
     * on if a lookup is going to get a single person (personID provided) or
     * all of the user's family (no personID provided/set).
     */
    public PersonRequest() {

    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }
}

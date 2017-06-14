package hawkes.model.result;


public class LoadResult extends Result {
    /** “message”: “Successfully added X users, Y persons, and Z events to the database.”
     *  or “message”: “Description of the error” */
    private String message;

    /**
     * Creates a new LoadResult object with all fields
     */
    public LoadResult() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

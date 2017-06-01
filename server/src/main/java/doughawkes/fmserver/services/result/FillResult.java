package doughawkes.fmserver.services.result;

/**
 *  The FillResult class provides the error or success message of a fill request.
 *  A success message will have the number of persons and events added to the database.
 */
public class FillResult extends Result {
    /** Message to be given upon completion of the request   */
    private String message;

    /**
     * creates a new fillResult object to be returned upon completion of a fill request.
      */
    public FillResult() {

    }

    public void setMessage(String message) {
        this.message = message;
    }
}

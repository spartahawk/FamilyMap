package hawkes.model.result;

/**
 * this class holds all information for the returned object after a
 * clear request has been made and processed sucessfully or not
 * with the corresponding message
 */
public class ClearResult extends Result {
    /** Message to be given, stating clear success or failure   */
    private String message;

    /** creates a new clear result based on the request success or failure
     *
     */
    public ClearResult() {

    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package doughawkes.fmserver.services.result;

/**
 * this class holds all information for the returned object after a
 * clear request has been made and processed sucessfully or not
 * with the corresponding message
 */
public class ClearResult extends Result {
    /** Message to be given upon successful completion of the request   */
    private String successMessage;
    /** Message to be given upon failed completion of the request    */
    private String errorMessage;

    /** creates a new clear result based on the request success or failure
     *
     */
    public ClearResult() {

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

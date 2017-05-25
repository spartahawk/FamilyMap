package doughawkes.fmserver.services.result;

/**
 *  The FillResult class provides the error or success message of a fill request.
 *  A success message will have the number of persons and events added to the database.
 */
public class FillResult extends Result {
    /** Message to be given upon successful completion of the request   */
    private String successMessage;
    /** Message to be given upon failed completion of the request    */
    private String errorMessage;

    /**
     * creates a new fillResult object to be returned upon completion of a fill request.
      */
    public FillResult() {

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

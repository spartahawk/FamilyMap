package doughawkes.fmserver.services.result;

/**
 * Created by yo on 5/30/17.
 */

public class ErrorMessage {
    private String message;

    public ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String errorMessage) {
        this.message = message;
    }
}

package doughawkes.fmserver.services;

import doughawkes.fmserver.dataAccess.Database;
import doughawkes.fmserver.services.result.ClearResult;

/** defines a service class for clearing the database
 *
 */
public class ClearService {
    private boolean success;
    /**
     * creates new service object for clearing the database
     */
    public ClearService() {


    }

    /**
     * clears the database
     * @return a ClearResult object for reporting result which will indicate a success message
     * or an error message with error detail
     */
    public ClearResult clear() {
        System.out.println("clearRequest recieved by clearService");

        ClearResult clearResult = new ClearResult();
        Database database = new Database();

        boolean clearSuccess = database.clearAll();
        if (!clearSuccess) {
            database.setAllTransactionsSucceeded(false);
            String message = "Clear failed due to an internal error.";
            clearResult.setMessage(message);
        }

        //this can return a redundant boolean (true for successful transaction, false for fail.
        database.endTransaction();

        return clearResult;
    }

    public boolean isSuccess() {
        return success;
    }
}

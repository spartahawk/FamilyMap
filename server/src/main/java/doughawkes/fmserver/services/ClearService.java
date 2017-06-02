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

        boolean userClear = database.getUserDao().clear();
        boolean authClear = database.getAuthTokenDao().clear();
        boolean personClear = database.getPersonDao().clear();
        boolean eventClear = database.getEventDao().clear();
        boolean clearSuccess = (userClear && authClear && personClear && eventClear);

        System.out.println("clearSuccess: " + clearSuccess);

        if (!clearSuccess) {
            database.setAllTransactionsSucceeded(false);
            String message = "Clear failed due to an internal error.";
            clearResult.setMessage(message);
        }
        else {
            clearResult.setMessage("Clear succeeded.");
        }



        //this can return a redundant boolean (true for successful transaction, false for fail.
        success = database.endTransaction();
        return clearResult;
    }

    public boolean isSuccess() {
        return success;
    }
}

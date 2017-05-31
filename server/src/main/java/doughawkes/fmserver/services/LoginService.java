package doughawkes.fmserver.services;

import doughawkes.fmserver.dataAccess.AuthTokenDao;
import doughawkes.fmserver.dataAccess.Database;
import doughawkes.fmserver.dataAccess.PersonDao;
import doughawkes.fmserver.dataAccess.UserDao;
import doughawkes.fmserver.model.AuthToken;
import doughawkes.fmserver.model.User;
import doughawkes.fmserver.services.request.LoginRequest;
import doughawkes.fmserver.services.result.LoginResult;

/**
 * class that takes action on the login request sent
 */
public class LoginService {

    /** creates a new LoginService object for acting on a login request
     *
     */
    public LoginService() {

    }

    /**
     * Look up the username in the Dao and retrieve its data.
     * @param r a login request object containing the login data (username, password...)
     * @return Username, auth token, person data
     */
    public LoginResult login(LoginRequest r) {
        System.out.println("LoginRequest recieved by LoginService");

        LoginResult loginResult = new LoginResult();
        Database database = new Database();

        // personID will be set to zero if username and password don't match in findUser()
        User user = database.getUserDao().findUser(r.getUserName());
        // TODO: don't allow empty passwords when creating a user -- do that elsewhere (in registerService)
        // check for correct password associated with the username provided. Nonexistant username
        // will give empty password

        //System.out.println("username: " + user.getUserName() + " password: " + user.getPassword()
        //                    + " personID: " + user.getPersonId());
        if (       user.getUserName() == null
                || user.getUserName().equals("")
                || user.getPassword() == null
                || !user.getPassword().equals(r.getPassword())
                || user.getPassword().equals("")
                || user.getPersonId().equals("")) {
            //send back empty values in the loginResult so the handler knows there was a login error
            loginResult.setAuthToken("");
            loginResult.setUserName("");
            loginResult.setPersonId("");
            database.setAllTransactionsSucceeded(false);
            System.out.println("password was empty or did not match");
        }
        // username and password match
        else {
            loginResult.setUserName(user.getUserName());
            loginResult.setPersonId(user.getPersonId());
            //Get just the authtoken itself (not the full authToken object)
            String authTokenString = database.getAuthTokenDao().generateAuthToken(r.getUserName());
            loginResult.setAuthToken(authTokenString);
        }


        // TODO: test this and look for needs for exceptions and special cases

        //this can return a redundant boolean (true for successful transaction, false for fail.
        database.endTransaction();

//        loginResult.setUserName(r.getUserName());
//        loginResult.setAuthToken("fancyRandomAuthToken");
//        loginResult.setPersonId(123456);

        return loginResult;
    }
}

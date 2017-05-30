package doughawkes.fmserver.services;

import doughawkes.fmserver.dataAccess.AuthTokenDao;
import doughawkes.fmserver.dataAccess.Database;
import doughawkes.fmserver.dataAccess.PersonDao;
import doughawkes.fmserver.dataAccess.UserDao;
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

        // LoginResult should have an authtoken, username, and personID
        LoginResult loginResult = new LoginResult();

        Database database = new Database();
        database.startTransaction();

        // the Database object automatically sets
        // its new AuthTokenDao's connection to that of the Database
        database.setUserDao(new UserDao());
        database.setAuthTokenDao(new AuthTokenDao());

        loginResult.setAuthToken(database.getAuthTokenDao().lookupByUserName(r.getUserName()));
        // personID will be set to zero if username and password don't match in findUser()
        User user = database.getUserDao().findUser(r.getUserName());
        // TODO: don't allow empty passwords when creating a user -- do that elsewhere (in registerService)
        // check for correct password associated with the username provided
        if (user.getPassword() != r.getPassword() || user.getPassword() == "") {
            //send back empty values in the loginResult so the handler knows there was a login error
            loginResult.setUserName("");
            loginResult.setPersonId(0);
        }
        // username and password match
        else {
            loginResult.setUserName(user.getUserName());
            loginResult.setPersonId(user.getPersonId());
        }


        // TODO: test this and look for needs for exceptions and special cases

        database.endTransaction();

//        loginResult.setUserName(r.getUserName());
//        loginResult.setAuthToken("fancyRandomAuthToken");
//        loginResult.setPersonId(123456);

        return loginResult;
    }
}

package doughawkes.fmserver.services;

import doughawkes.fmserver.dataAccess.AuthTokenDao;
import doughawkes.fmserver.dataAccess.Database;
import doughawkes.fmserver.dataAccess.PersonDao;
import doughawkes.fmserver.dataAccess.UserDao;
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

        // LoginResult has an authtoken, username, and personID
        LoginResult loginResult = new LoginResult();

        Database database = new Database();
        database.startTransaction();

        // the Database object automatically sets
        // its new AuthTokenDao's connection to that of the Database
        database.setUserDao(new UserDao());
        database.setAuthTokenDao(new AuthTokenDao());

        loginResult.setAuthToken(database.getAuthTokenDao().lookupByUserName(r.getUserName()));
        loginResult.setUserName(r.getUserName());
        loginResult.setPersonId(database.getUserDao().findUser(r.getUserName()).getPersonId());






        database.endTransaction();

//        loginResult.setUserName(r.getUserName());
//        loginResult.setAuthToken("fancyRandomAuthToken");
//        loginResult.setPersonId(123456);

        return loginResult;
    }
}

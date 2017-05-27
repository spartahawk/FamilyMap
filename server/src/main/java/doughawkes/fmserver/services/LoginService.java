package doughawkes.fmserver.services;

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



/*        loginResult.setUserName(r.getUserName());
        loginResult.setAuthToken("fancyRandomAuthToken");
        loginResult.setPersonId(123456);*/

        return loginResult;
    }
}

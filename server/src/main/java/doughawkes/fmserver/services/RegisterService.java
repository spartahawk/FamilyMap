package doughawkes.fmserver.services;

import doughawkes.fmserver.dataAccess.AuthTokenDao;
import doughawkes.fmserver.dataAccess.Database;
import doughawkes.fmserver.dataAccess.EventDao;
import doughawkes.fmserver.dataAccess.PersonDao;
import doughawkes.fmserver.dataAccess.UserDao;
import doughawkes.fmserver.services.request.RegisterRequest;
import doughawkes.fmserver.services.result.RegisterResult;

/**
 * This class takes RegisterRequest objects in order to create a new user and its database
 * person representation including the generated family. Creates a new user account,
 * generates 4 generations of ancestor data for the new user, logs the user in,
 * and returns an auth token with the username and personID
 */
public class RegisterService {
    /**
     * RegisterService constructor
    */
    public RegisterService() {}

    /**
     * Use info in request to make a User with generated data,
     * verify its existence in the user dao object and populate
     * with data if doesn't yet exist and create a new user entry in
     * the database using the user dao object. Result will be called
     * to send the result back.
     * @param r RegisterRequest object containing new user data for registration
     * @return the registerResult object containing the authtoken, username, and password
     */
    public RegisterResult register(RegisterRequest r) {

        RegisterResult registerResult = new RegisterResult();
        Database database = new Database();

        //create a new user account


        //generate 4 generations of ancestor data for the new user


        //log in the user


        // return an auth token (with user name and personID)






        registerResult.setAuthToken("blahblah789");
        registerResult.setUserName("theusername");
        registerResult.setPersonId("personID3983");
        return registerResult;
    }

}



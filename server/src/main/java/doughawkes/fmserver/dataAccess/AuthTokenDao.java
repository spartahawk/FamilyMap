package doughawkes.fmserver.dataAccess;

import doughawkes.fmserver.model.AuthToken;

/**
 * class that deals with the database and makes changes and lookups
 * for AuthToken entries
 */
public class AuthTokenDao extends Dao {

    /**
     * creates new AuthTokenDao object to interact with the database
     */
    public AuthTokenDao() {

    }

    /**
     * adds an AuthToken to the database
     * @param a AuthToken of interest
     * @return true or false for success
     */
    boolean addAuthToken(AuthToken a) {
        return false;
    }

    /**
     * looks up a AuthToken in the database
     * @param a AuthToken of interest
     * @return the AuthToken object successfully found
     */
    AuthToken lookup(AuthToken a) {
        return null;
    }
    /**
     * deletes a AuthToken entry in the database
     * @param a AuthToken of interest
     * @return the AuthToken object successfully deleted
     */
    boolean delete(AuthToken a) {
        return false;
    }
}

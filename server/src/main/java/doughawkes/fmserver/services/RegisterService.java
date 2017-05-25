package doughawkes.fmserver.services;

import doughawkes.fmserver.services.request.RegisterRequest;
import doughawkes.fmserver.services.result.RegisterResult;

/** this class has two constructors, one for processing a register request
 * and the other for processing a register result
 */
public class RegisterService {
    /**
     * creates a new register service object from a requst
     * @param r register request object passed in
     */
    public RegisterService(RegisterRequest r) {
        request(r);
    }

    /**
     * creates a new registerService object from a result
     * @param r register result object passed in
     */
    public RegisterService(RegisterResult r) {
        result(r);
    }

    /**
     * Use info in request to make a User with generated data,
     * verify its existence in the user dao object and populate
     * with data if doesn't yet exist and create a new user entry in
     * the database using the user dao object. Result will be called
     * to send the result back.
     * @param r RegisterRequest object
     *
     */
    private void request(RegisterRequest r) {

    }

    /**
     *  Called to send a register result back
     * @param r RegisterResult object
     *
     */
    private void result(RegisterResult r) {

    }

}



package doughawkes.fmserver.services;

import doughawkes.fmserver.services.request.LoadRequest;
import doughawkes.fmserver.services.result.LoadResult;

/** class that takes action on the loadrequest object specificity
 *
 */
public class LoadService {
    /** creates a new load service object for the specific load request
     *
     */
    public LoadService() {

    }

    /**
     *
     * @param r loadrequest object containing a request for a single event of all of a person's events
     * @return either all the events for a person or a single event, and a success message, or error message
     */
    public LoadResult load(LoadRequest r) {
        return null;
    }
}

package hawkes.fmc.model;

import java.util.HashSet;

import hawkes.model.Event;
import hawkes.model.Person;

/**
 * Created by yo on 6/19/17.
 */

public class SearchResult {

    private String query;
    private HashSet<Person> matchingPeople;
    private HashSet<Event> matchingEvents;
    Model model;

    public SearchResult(String query) {
        model = Model.getModel();
        this.query = query;
        searchPeople();
        searchEvents();
    }

    private void searchPeople() {
        for (Person person : model.getPersons().values()) {
            if (person.getFirstName().contains(query)) {
                matchingPeople.add(person);
            }
            else if (person.getLastName().contains(query)) {
                matchingPeople.add(person);
            }
        }
    }

    private void searchEvents() {
        for (Event event : model.getFilteredEvents()) {
            searchEvent(event);
        }
    }

    private void searchEvent(Event event) {
        if (event.getEventType().contains(query)) {
            matchingEvents.add(event);
            return;
        }
        if (event.getCity().contains(query)) {
            matchingEvents.add(event);
            return;
        }
        if (event.getCountry().contains(query)) {
            matchingEvents.add(event);
            return;
        }
        if ((Integer.toString(event.getYear()).contains(query))) {
            matchingEvents.add(event);
            return;
        }

        Person eventOwner = model.getPersons().get(event.getEventID());
        String fullName = eventOwner.getFirstName() + eventOwner.getLastName();
        if (fullName.contains(query)) {
            matchingEvents.add(event);
            return;
        }
    }

}

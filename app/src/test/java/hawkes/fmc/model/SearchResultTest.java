package hawkes.fmc.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.TreeMap;

import hawkes.model.Event;
import hawkes.model.Person;

import static org.junit.Assert.*;

/**
 * Created by yo on 6/19/17.
 */
public class SearchResultTest {

    private String query;

    private Model model;

    Person person1;
    Person person2;

    private TreeMap<String, Person> persons;

    Event event1;
    Event event2;

    private TreeMap<String, Event> events;

    @Before
    public void setUp() throws Exception {
        model = Model.getModel();
        persons = new TreeMap<>();
        person1 = new Person();
        person2 = new Person();
        event1 = new Event();
        event2 = new Event();
        events = new TreeMap<>();
    }

    @After
    public void tearDown() throws Exception {
        persons.clear();
    }

    @Test
    public void testAllViaSearchResultConstructor() throws Exception {

        //persons
        person1.setPersonID("ID_1");
        person1.setFirstName("Bob");
        person1.setLastName("Jones");

        person2.setPersonID("ID_2");
        person2.setFirstName("Kim");
        person2.setLastName("Smith");

        persons.put(person1.getPersonID(), person1);
        persons.put(person2.getPersonID(), person2);

        model.setPersons(persons);

        //events
        event1.setEventType("explosion");
        event1.setPersonID("ID_1");
        event2.setEventType("eclipse");
        event2.setPersonID("ID_2");

        events.put(event1.getEventType(), event1);
        events.put(event2.getEventType(), event2);

        model.setEvents(events);

        query = "b";

        SearchResult searchResult = new SearchResult(query);

        assertEquals("Bob", searchResult.getMatchingPeople().get(0).getFirstName());

        int resultSize = 1;
        assertEquals(resultSize, searchResult.getMatchingEvents().size()
                                +searchResult.getMatchingPeople().size());

    }

}
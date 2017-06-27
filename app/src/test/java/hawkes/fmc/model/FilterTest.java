package hawkes.fmc.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.TreeMap;

import hawkes.model.Event;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by yo on 6/19/17.
 */
public class FilterTest {

    private Model model;
    private TreeMap<String, Event> events;

    Event event1;
    Event event2;
    Event event3;
    Event event4;

    // Just using its method, not its constructor other than to create the object
    Filter filter;

    private ArrayList<String> expected;

    @Before
    public void setUp() throws Exception {
        model = Model.getModel();
        TreeMap<String, String> eventTypeColors = new TreeMap<>();
        model.setEventTypeColors(eventTypeColors);

        events = new TreeMap<>();

        event1 = new Event();
        event2 = new Event();
        event3 = new Event();
        event4 = new Event();

        expected = new ArrayList<String>();

        filter = new Filter("dummyFilter");

    }

    @After
    public void tearDown() throws Exception {
        expected.clear();
        events.clear();

        return;
    }

    @Test
    public void duplicatedEventTypes() throws Exception {

        expected.add("cat");
        expected.add("dog");
        expected.add("monkey");

        event1.setEventType("cat");
        event2.setEventType("cat");
        event3.setEventType("dog");
        event4.setEventType("monkey");

        events.put(event1.getEventType(), event1);
        events.put(event2.getEventType(), event2);
        events.put(event3.getEventType(), event3);
        events.put(event4.getEventType(), event4);

        model.setEvents(events);

        // Just using its method, not its constructor other than to create the object
        Filter filter = new Filter("dummyFilter");

        ArrayList<String> actual = filter.determineEventTypes();

        assertEquals(actual, expected);
    }

    @Test
    public void badEventTypeCase() throws Exception {
        // This should fail because the events should already have their types
        // set to lowercase before being passed to the method.

        expected.add("cat");
        expected.add("monkey");

        event1.setEventType("cat");
        event2.setEventType("CAT");
        event3.setEventType("Monkey");
        event4.setEventType("monKEY");

        events.put(event1.getEventType(), event1);
        events.put(event2.getEventType(), event2);
        events.put(event3.getEventType(), event3);
        events.put(event4.getEventType(), event4);

        model.setEvents(events);

        ArrayList<String> actual = filter.determineEventTypes();

        assertFalse(expected.size() == actual.size());
    }

}
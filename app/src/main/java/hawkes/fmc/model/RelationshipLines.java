package hawkes.fmc.model;

import java.util.SortedSet;
import java.util.TreeSet;

import hawkes.model.Event;
import hawkes.model.Person;

/**
 * Created by yo on 6/17/17.
 */

public class RelationshipLines {

    private TreeSet<Event> lifeStoryEvents;
    private Event spouseEvent;

    public RelationshipLines(Event event) {
        lifeStoryEvents = new TreeSet<>();
        generateLines(event);
    }

    //generate the lines for the selected person
    private void generateLines(Event event) {
        Model model = Model.getModel();

        // get the person whose selected event this is
        Person thisPerson = model.getPersons().get(event.getPersonID());

        for (Event otherEvent : model.getFilteredEvents()) {

//            //skip it if it's the same event
//            if (otherEvent.getEventID().equals(event.getEventID())) continue;

            // life Story Events
            if (otherEvent.getPersonID().equals(event.getPersonID())) {
                System.out.println("*** IT'S EQUAL ***" + otherEvent.getEventType() + otherEvent.getEventID());
                lifeStoryEvents.add(otherEvent);
                System.out.println("lifeStoryEvents size " + lifeStoryEvents.size());
            }

            // Spouse line
            if (thisPerson.getSpouse() != null) {
                String spousePersonID = thisPerson.getSpouse();
                // todo: finish this and do the family relationship lines too
            }
        }
    }

    public SortedSet<Event> getLifeStoryEvents() {
        return lifeStoryEvents;
    }

    public void setLifeStoryEvents(TreeSet<Event> lifeStoryEvents) {
        this.lifeStoryEvents = lifeStoryEvents;
    }

}

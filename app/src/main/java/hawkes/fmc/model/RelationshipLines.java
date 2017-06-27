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
    private TreeSet<Event> spouseEvents;

    public RelationshipLines(Event event) {
        lifeStoryEvents = new TreeSet<>();
        spouseEvents = new TreeSet<>();
        generateLines(event);
    }

    //generate the lines for the selected person
    private void generateLines(Event event) {
        Model model = Model.getModel();
        Person thisPerson = new Person();

        try {
            // get the person whose selected event this is
            thisPerson = model.getPersons().get(event.getPersonID());
        } catch (Exception e) {
            //null person somehow.
            e.printStackTrace();
            return;
        }

        for (Event otherEvent : model.getFilteredEvents()) {

            // life Story Events
            if (otherEvent.getPersonID().equals(event.getPersonID())) {
                //System.out.println("*** IT'S EQUAL ***" + otherEvent.getEventType() + otherEvent.getEventID());
                lifeStoryEvents.add(otherEvent);
                //System.out.println("lifeStoryEvents size " + lifeStoryEvents.size());
            }
        }

        // Spouse line
        try {
            if (thisPerson == null) return;
            if (thisPerson.getSpouse() == null) return;
            String spousePersonID = thisPerson.getSpouse();
            if (spousePersonID == null) return;
            for (Event spEvent : model.getFilteredEvents()) {
                if (spEvent.getPersonID().equals(spousePersonID)) {
                    spouseEvents.add(spEvent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SortedSet<Event> getLifeStoryEvents() {
        return lifeStoryEvents;
    }

    public void setLifeStoryEvents(TreeSet<Event> lifeStoryEvents) {
        this.lifeStoryEvents = lifeStoryEvents;
    }

    public TreeSet<Event> getSpouseEvents() {
        return spouseEvents;
    }

    public void setSpouseEvents(TreeSet<Event> spouseEvents) {
        this.spouseEvents = spouseEvents;
    }
}

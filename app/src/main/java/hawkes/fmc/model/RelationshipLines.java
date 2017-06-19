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
    private TreeSet<Event> familyLineEvents;

    public RelationshipLines(Event event) {
        lifeStoryEvents = new TreeSet<>();
        generateLines(event);
    }

    //generate the lines for the selected person
    private void generateLines(Event event) {
        Model model = Model.getModel();
        Person thisPerson = null;

        try {
            // get the person whose selected event this is
            thisPerson = model.getPersons().get(event.getPersonID());
        } catch (Exception e) {
            //null person somehow.
            e.printStackTrace();
            return;
        }

        for (Event otherEvent : model.getFilteredEvents()) {

//            //skip it if it's the same event
//            if (otherEvent.getEventID().equals(event.getEventID())) continue;

            // life Story Events
            if (otherEvent.getPersonID().equals(event.getPersonID())) {
                //System.out.println("*** IT'S EQUAL ***" + otherEvent.getEventType() + otherEvent.getEventID());
                lifeStoryEvents.add(otherEvent);
                //System.out.println("lifeStoryEvents size " + lifeStoryEvents.size());
            }
        }

        spouseEvents = new TreeSet<>();
        // Spouse line
        if (thisPerson.getSpouse() != null) {
            String spousePersonID = thisPerson.getSpouse();
            for (Event spEvent : model.getFilteredEvents()) {
                if (spEvent.getPersonID().equals(spousePersonID)) {
                    spouseEvents.add(spEvent);
                }
            }
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

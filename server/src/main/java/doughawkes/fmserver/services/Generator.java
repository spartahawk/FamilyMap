package doughawkes.fmserver.services;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import doughawkes.fmserver.model.Event;
import doughawkes.fmserver.model.Person;
import doughawkes.fmserver.model.User;
import doughawkes.fmserver.services.fromJSON.DataPool;
import doughawkes.fmserver.services.fromJSON.Location;

/**
 * Created by yo on 6/1/17.
 */

public class Generator {

    private User user;
    private ArrayList<Person> persons;
    private ArrayList<Event> events;
    DataPool dataPool;

    public Generator(User user, int generations) {
        this.user = user;

        // Make user person
        Person person = new Person();
        dataPool = new DataPool();

        person.setPersonID(UUID.randomUUID().toString());
        person.setDescendant(user.getUserName());
        person.setFirstName(user.getFirstName());
        person.setLastName(user.getLastName());
        person.setGender(user.getGender());
        person.setFather(UUID.randomUUID().toString());
        person.setMother(UUID.randomUUID().toString());
        person.setSpouse(null);

        persons = new ArrayList<>();
        persons.add(person);

        //Make user personal events
        events = new ArrayList<>();

        generateUserEvents(person);

        events.add(event);


        addParent(person, 'm', generations);
        addParent(person, 'f', generations);

    }

    private void addParent(Person theirChild, char gender, int generations) {
        generations--;

        // This will have the personID for the parents, to be used in spouse IDs for them.
        Person currentPerson = generatePerson(theirChild, gender);

        // add this person to the database here, using the gender provided
        // OR JUST ADD THE PERSON TO THE ARRAY OF PERSONS and add all to database elsewhere.
        persons.add(currentPerson);


//        Event ID: Unique identifier for this event (non-empty string)
//        Descendant: User (Username) to which this person belongs
//        Person: ID of person to which this event belongs
//        Latitude: Latitude of event’s location
//        Longitude: Longitude of event’s location
//        Country: Country in which event occurred
//        City: City in which event occurred
//        EventType: Type of event (birth, baptism, christening, marriage, death, etc.)
//        Year: Year in which event occurred

        //Todo: make this method and uncomment
        generateTheirEvents(currentPerson, marriageYear);

        if (generations > 0) {

            // add this person's parents to the database
            //If I wanted to make it random whether they have both parents or not,
            //that can be done by randomly calling only one of two of these.
            addParent(currentPerson, 'm', generations);
            addParent(currentPerson, 'f', generations);
        }
        return;
    }

    private Person generatePerson(Person theirChild, char gender) {
        //pull random names, etc and plug into a new person and return it
        Person person = new Person();

        if (gender == 'm') {
            person.setPersonID(theirChild.getFather());
            person.setFirstName(dataPool.getRandomMaleName());
            // todo: see if this is okay to have the father always have child's surname
            person.setLastName(theirChild.getLastName());
            person.setSpouse(theirChild.getMother());
        }
        if (gender == 'f') {
            person.setPersonID(theirChild.getMother());
            person.setFirstName(dataPool.getRandomFemaleName());
            // this surname is random since we'll assume the child only gets surname from father
            person.setLastName(dataPool.getRandomSurname());
            person.setSpouse(theirChild.getFather());
        }

        person.setDescendant(theirChild.getDescendant());
        person.setGender(gender);
        person.setFather(UUID.randomUUID().toString());
        person.setMother(UUID.randomUUID().toString());

        return person;
    }

    private void generateTheirEvents(Person person, int marriageYear) {
        Random random = new Random();

        int typicalMarriageAge = 30;
        int variation = 10;
        int decrement = 5;
        int birthYear =  marriageYear - typicalMarriageAge + random.nextInt(variation) - decrement;
        generateEvent(person, "Birth", birthYear);

        int minimumBaptismAge = 8;
        int baptismYear = birthYear + minimumBaptismAge + random.nextInt(5);
        generateEvent(person, "Baptism", baptismYear);
        generateEvent(person, "Marriage", marriageYear);

        int typicalGradYear = 30;
        int graduationYear = birthYear + typicalGradYear + random.nextInt(variation) - decrement;
        generateEvent(person, "Graduation", graduationYear);

        int typicalDeathAge = 80;
        variation = 15;
        decrement = 8;
        int deathYear = birthYear + typicalDeathAge + random.nextInt(variation) - decrement;
        while (deathYear <= marriageYear || deathYear <= graduationYear) deathYear += 1;
        generateEvent(person, "Death", deathYear);
    }

    private void generateEvent(Person person, String eventType, int year) {
        Event event = new Event();
        event.setEventID(UUID.randomUUID().toString());
        event.setDescendant(user.getUserName());
        event.setPersonID(person.getPersonID());

        Location location = dataPool.getRandomLocation();
        event.setLatitude(location.getLatitude());
        event.setLongitude(location.getLongitude());
        event.setCountry(location.getCountry());
        event.setCity(location.getCity());
        event.setEventType(eventType);
        event.setYear(year);

        events.add(event);
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }
}

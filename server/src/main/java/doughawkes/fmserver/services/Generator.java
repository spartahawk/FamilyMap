package doughawkes.fmserver.services;

import java.util.ArrayList;
import java.util.UUID;

import doughawkes.fmserver.model.Event;
import doughawkes.fmserver.model.Person;
import doughawkes.fmserver.model.User;
import doughawkes.fmserver.services.fromJSON.DataPool;

/**
 * Created by yo on 6/1/17.
 */

public class Generator {

    private User user;
    private ArrayList<Person> persons;
    private ArrayList<Event> events;

    public Generator(User user, int generations) {
        this.user = user;

        // Make user person
        Person person = new Person();

        person.setPersonID(UUID.randomUUID().toString());
        person.setDescendant(user.getUserName());
        person.setFirstName(user.getFirstName());
        person.setLastName(user.getLastName());
        person.setGender(user.getGender());

        DataPool dataPool = new DataPool();
        String fatherPersonID = UUID.randomUUID().toString();
        person.setFather(fatherPersonID);
        String motherPersonID = UUID.randomUUID().toString();
        person.setMother(motherPersonID);
        person.setSpouse(null);

        persons.add(person);

        //Make user personal events
        //...

        addParent(person, 'm', generations);
        addParent(person, 'f', generations);

    }

    private void addParent(Person theirChild, char gender, int generations) {
        generations--;

        // This will have the personID for the parents, to be used in spouse IDs for them.
        Person currentPerson = generatePerson(theirChild, gender);

        // add this person to the database here, using the gender provided

        addThisPersonsEvents(currentPerson);

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
        DataPool dataPool = new DataPool();

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

    private void generateEvents() {

    }
}

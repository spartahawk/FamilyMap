package hawkes.fmc.model;

import hawkes.model.Person;

/**
 * Created by yo on 6/19/17.
 */

public class FamilyMember extends Person {

    private String relationship;

    public FamilyMember(Person person) {
        setPersonID(person.getPersonID());
        setDescendant(person.getDescendant());
        setFirstName(person.getFirstName());
        setLastName(person.getLastName());
        if (person.getFather() != null) setFather(person.getFather());
        if (person.getMother() != null) setMother(person.getMother());
        if (person.getSpouse() != null) setSpouse(person.getSpouse());
        setGender(person.getGender());
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

}

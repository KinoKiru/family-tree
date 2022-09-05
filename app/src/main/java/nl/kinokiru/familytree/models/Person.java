package nl.kinokiru.familytree.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public abstract class Person {
    private String name;
    private int age;
    private final String ssn;
    // usage of set because you can't be friends twice with one person
    private Set<Person> members;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSsn() {
        return ssn;
    }

    public Set<Person> getMembers() {
        return members;
    }

    public void setMembers(Set<Person> members) {
        this.members = members;
    }

    public void addFriend(Person member) {
        if (!this.hasFriend(member) && !member.equals(this)) {
            this.members.add(member);
        }

        if (!member.hasFriend(this) && !member.equals(this)) {
            member.members.add(this);
        }
    }

    public boolean removeFriend(Person member) {
        return this.members.remove(member);
    }

    public boolean hasFriend(Person member) {
        return this.members.contains(member);
    }

    /**
     * @implement print persons greeting
     */
    public abstract void greet();

    @Override
    public boolean equals(Object person) {
        return person instanceof Person && ((Person) person).ssn == this.ssn;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("[ name: ").append(this.name).append(", Age: ").append(this.age).append(" ]");

        return str.toString();
    }

    /**
     * Constructor
     * 
     * @param name
     * @param age
     * @param ssn
     */
    public Person(String name, int age, String ssn) {
        this.name = name;
        this.age = age;
        this.ssn = ssn;
        this.members = new HashSet<Person>();
    }

    public String getFamilyTree(Person target) {
        ArrayList<Person> path = dfs(target, new HashSet<>());

        if (path.isEmpty()) {
            return "No correlation found between " + target.name + " and " + this.name;
        }

        StringBuilder str = new StringBuilder();
        for (Person person : path) {
            str.append(person.name).append(" -> ");
        }

        return str.toString().substring(0, str.length() - 4);
    }

    public ArrayList<Person> dfs(Person target, HashSet<Person> visited) {
        // adds the current person to visited so it won't be visited agian
        visited.add(this);
        // not the best for speed but makes a new list
        ArrayList<Person> path = new ArrayList<>();

        // if this (current person in loop) is equal to target add to path and return
        // it.
        if (this.equals(target)) {
            path.add(0, this);
            return path;
        }

        // (this) isnt the target, so loop over it's members
        for (Person person : this.members) {
            // only go for the members who aren't already in visited
            if (!visited.contains(person)) {
                // dfs with member
                path = person.dfs(target, visited);
                // target is found so add current to path and return it
                if (!path.isEmpty()) {
                    path.add(0, this);
                    return path;
                }
            }
        }
        return path;
    }
}

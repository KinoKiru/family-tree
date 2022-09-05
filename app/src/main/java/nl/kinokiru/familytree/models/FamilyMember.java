package nl.kinokiru.familytree.models;

import java.util.Random;

public class FamilyMember extends Person {
    public final Roles role;
    private Roles[] roles = Roles.values();
    private static final Random rndm = new Random();

    public FamilyMember(String name, int age, String ssn) {
        super(name, age, ssn);
        role = roles[rndm.nextInt(roles.length)];
    }

    @Override
    public void greet() {
        System.out.println("Hey nice to see you again");
    }
}

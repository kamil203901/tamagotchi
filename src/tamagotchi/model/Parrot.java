package tamagotchi.model;

public class Parrot implements IPet {
    private String name;
    private int happiness;
    private int health;
    private int hunger;
    private double weight = 0.2;
    private static final double MINIMAL_WEIGHT = 0.2;
    private static final double MAXIMAL_WEIGHT = 2.0;
}

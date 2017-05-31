package tamagotchi.model;

public class Pet {
    private String name;
    private Genre genre;
    private int age = 0;
    private int health = 100;
    private int hunger = 0;
    private int happiness = 100;
    private double weight;

    public Pet(String name, Genre genre) {
        this.name = name;
        this.genre = genre;
    }

    public Pet(String name, Genre genre, int age, double weight) {
        this.name = name;
        this.genre = genre;
        this.age = age;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public Genre getGenre() {
        return genre;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHunger() {
        return hunger;
    }

    public void increaseHunger() {
        if (hunger < 100) {
            this.hunger += 1;
        }
    }

    public void decreaseHunger(int points) {
        if (hunger > 0) {
            this.hunger -= 1;
        }
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public int getAge() {
        return age;
    }

    public void increaseAge() {
        this.age += 1;
    }
}

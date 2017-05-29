package tamagotchi.model;

public class Dog implements IPet {
    private String name;
    private int happiness;
    private int health;
    private int hunger;
    private int weight = 50;
    private static final int MINIMAL_WEIGHT = 10;
    private static final int MAXIMAL_WEIGHT = 100;
    private static final int DEWORMING_POINTS = 10;
    private static final int SHOWER_POINTS = 5;
    private static final int GO_FOR_A_WALK_POINTS = 5;
    private static final int THROW_A_STIK_POINTS = 10;

    public void goForAWalk() {
        happiness += GO_FOR_A_WALK_POINTS;
    }

    public void wash() {
        health += SHOWER_POINTS;
    }

    public void throwAStick() {
        happiness += THROW_A_STIK_POINTS;
    }

    public void subtractHappiness(int points) {
        if (points >= happiness) {
            happiness = 0;
        } else {
            happiness -= points;
        }
    }

    public void deworming() {
        health += DEWORMING_POINTS;
    }

    public void subtractHealth(int points) {
        if (points >= health) {
            health = 0;
        } else {
            health -= points;
        }
    }

    public void feedWithDryFood(Food dogFood) {
        if (dogFood.dryDogFood >= hunger) {
            hunger = 0;
        } else {
            hunger -= dogFood.dryDogFood;
        }
    }

    public void feedWithWetFood(Food dogFood) {
        if (dogFood.wetDogFood >= hunger) {
            hunger = 0;
        } else {
            hunger -= dogFood.wetDogFood;
        }
    }

    public void increaseWeight(int kg) {
        if ( (weight + kg) >= MAXIMAL_WEIGHT) {
            weight = MAXIMAL_WEIGHT;
        } else {
            weight += kg;
        }
    }

    public void decreaseWeight(int kg) {
        if ( (weight - kg) <= MINIMAL_WEIGHT ) {
            weight = MINIMAL_WEIGHT;
        } else {
            weight -= kg;
        }
    }
}

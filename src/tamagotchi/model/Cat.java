package tamagotchi.model;


public class Cat implements IPet {
    private String name;
    private int happiness;
    private int health;
    private int hunger;
    private int weight = 5;
    private static final int DEWORMING_POINTS = 3;
    private static final int STROKE_POINTS = 10;
    private static final int MAXIMAL_WEIGHT = 12;
    private static final int MINIMAL_WEIGHT = 1;


    public void stroke() {
        health += STROKE_POINTS;
    } // glaskac

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

    public void feedWithDryFood(Food catFood) {
        if (catFood.dryCatFood >= hunger) {
            hunger = 0;
        } else {
            hunger -= catFood.dryCatFood;
        }
    }

    public void feedWithWetFood(Food catFood) {
        if (catFood.wetCatFood >= hunger) {
            hunger = 0;
        } else {
            hunger -= catFood.wetCatFood;
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

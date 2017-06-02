package tamagotchi.model;

public class Action {
    private String name;
    private ActionGenre actionGenre;
    private int points;

    public Action(String name, ActionGenre actionGenre, int points) {
        this.name = name;
        this.actionGenre = actionGenre;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public ActionGenre getActionGenre() {
        return actionGenre;
    }

    public int getActionPoints() {
        return points;
    }
}

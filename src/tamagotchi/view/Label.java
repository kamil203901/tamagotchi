package tamagotchi.view;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by kamil on 03.06.17.
 */
public class Label extends JLabel {
    private int petId;
    private int genreId;
    private JPopupMenu menu;
    private JMenu health;
    private JMenu happiness;
    private JMenu feed;
    private ArrayList<ActionItem> actions;

    public Label(int petId, Icon image) {
        super(null, image, CENTER);
        this.petId = petId;

        menu = new JPopupMenu();
        health = new JMenu();
        happiness = new JMenu();
        feed = new JMenu();
        menu.add(health);
        menu.add(happiness);
        menu.add(feed);
        this.setComponentPopupMenu(menu);
    }

    public Label() {
        super();

        menu = new JPopupMenu();
        health = new JMenu("Health");
        happiness = new JMenu("Happiness");
        feed = new JMenu("Hunger");
        actions = new ArrayList<>();

        menu.add(health);
        menu.add(happiness);
        menu.add(feed);
        this.setComponentPopupMenu(menu);
    }

    public void setActions(ArrayList<ActionItem> actionsToCopy) {
        for (ActionItem item : actionsToCopy) {
            this.actions.add(new ActionItem(item));
        }

        for (ActionItem action : this.actions) {
            switch (action.getType()) {
                case "leczenie":
                    if (this.getGenreId() == Integer.parseInt(action.getGenreId())) {
                        health.add(action);
                    }
                    break;
                case "zabawa":
                    if (this.getGenreId() == Integer.parseInt(action.getGenreId())) {
                        happiness.add(action);
                    }
                    break;
                case "karmienie":
                    if (this.getGenreId() == Integer.parseInt(action.getGenreId())) {
                        feed.add(action);
                    }
                    break;
                default:
                    System.out.println("Cannot find correct category");
                    break;
            }
        }
    }

    public Label(ArrayList<ActionItem> actionsToCopy) {
        super();
        actions = new ArrayList<>();
        menu = new JPopupMenu();
        health = new JMenu("Health");
        happiness = new JMenu("Happiness");
        feed = new JMenu("Hunger");

        for (ActionItem item : actionsToCopy) {
            this.actions.add(new ActionItem(item));
        }

        for (ActionItem action : this.actions) {
            switch (action.getType()) {
                case "leczenie":
                    if (this.getGenreId() == Integer.parseInt(action.getGenreId())) {
                        health.add(action);
                    }
                    break;
                case "zabawa":
                    if (this.getGenreId() == Integer.parseInt(action.getGenreId())) {
                        happiness.add(action);
                    }
                    break;
                case "karmienie":
                    if (this.getGenreId() == Integer.parseInt(action.getGenreId())) {
                        feed.add(action);
                    }
                    break;
                default:
                    System.out.println("Cannot find correct category");
                    break;
            }
        }

        menu.add(health);
        menu.add(happiness);
        menu.add(feed);

        this.setComponentPopupMenu(menu);
    }

    public ArrayList<ActionItem> getActions() {
        return actions;
    }

    public JPopupMenu getMenu() {
        return menu;
    }

    public JMenu getHealth() {
        return health;
    }

    public JMenu getHappiness() {
        return happiness;
    }

    public JMenu getFeed() {
        return feed;
    }



    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }
}

package tamagotchi.view;

import javax.swing.*;

public class ActionItem extends JMenuItem {
    private String genreId;
    private String type;
    private int points;

    public ActionItem(String text, String genreId, String type, int points) {
        super(text);
        this.genreId = genreId;
        this.type = type;
        this.points = points;
    }

    public ActionItem(ActionItem item) {
        this(item.getText(), item.getGenreId(), item.getType(), item.getPoints());
    }

    public String getGenreId() {
        return genreId;
    }

    public String getType() {
        return type;
    }

    public int getPoints() {
        return points;
    }
}

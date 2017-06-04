package tamagotchi.view;

import tamagotchi.model.*;

import javax.swing.*;

public class ActionItem extends JMenuItem {
    private String genreId;
    private String type;

    public ActionItem(String text, String genreId, String type) {
        super(text);
        this.genreId = genreId;
        this.type = type;
    }

    public ActionItem(ActionItem item) {
        this(item.getText(), item.getGenreId(), item.getType());
    }

    public String getGenreId() {
        return genreId;
    }

    public String getType() {
        return type;
    }
}

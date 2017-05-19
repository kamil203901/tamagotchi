package tamagotchi.view;

import tamagotchi.controller.IController;
import javax.swing.*;

public class Panel extends JPanel {
    private IController controller;

    public Panel(IController controller) {
        this.controller = controller;
    }
}

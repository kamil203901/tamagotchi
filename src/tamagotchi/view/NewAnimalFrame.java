package tamagotchi.view;

import tamagotchi.controller.IController;

import javax.swing.*;
import java.awt.*;

public class NewAnimalFrame extends JFrame {
    private Panel newAnimalPanel;
    private JButton addButton;
    private JTextField nameAnimalTextField;

    public NewAnimalFrame(IController newAnimalController) {
        newAnimalPanel = new Panel(newAnimalController);
        setupNewAnimalFrame();

    }

    private void setupNewAnimalFrame() {
        this.setContentPane(newAnimalPanel);
        this.setTitle("Add animal...");
        this.setResizable(false);
        this.setAlwaysOnTop(true);
        this.setLayout(new BorderLayout());

        // create name text field
        nameAnimalTextField = new JTextField(20);

        // create combo box for type of animal


        // label for a text field name
        JLabel nameAnimalFieldLabel = new JLabel("Name:");
        nameAnimalFieldLabel.setLabelFor(nameAnimalTextField);

        // label for a type of animal combo box
        JLabel typeAnimalFieldLabel = new JLabel("Genre:");

        this.newAnimalPanel.setLayout(new BoxLayout(newAnimalPanel, BoxLayout.Y_AXIS));

        JPanel fields = new JPanel(new FlowLayout(FlowLayout.CENTER));
        fields.add(nameAnimalFieldLabel);
        fields.add(typeAnimalFieldLabel);

        addButton = new JButton("Add animal");

        JPanel button = new JPanel(new FlowLayout(FlowLayout.CENTER));
        button.add(addButton);

        this.newAnimalPanel.add(fields);
        this.newAnimalPanel.add(button);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public JTextField getNameAnimalTextField() {
        return nameAnimalTextField;
    }
}

package tamagotchi.view;

import tamagotchi.controller.IController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class NewAnimalFrame extends JFrame {
    private Panel newAnimalPanel;
    private JButton addButton;
    private JTextField nameAnimalTextField;
    private JComboBox genreOfAnimal;
    private String[] genries = { "Dog", "Cat", "Fish" };

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
        nameAnimalTextField = new JTextField(10);

        // create combo box for type of animal
        genreOfAnimal = new JComboBox(genries);

        // label for a text field name
        JLabel nameAnimalFieldLabel = new JLabel("Name:");
        nameAnimalFieldLabel.setLabelFor(nameAnimalTextField);

        // label for a type of animal combo box
        JLabel typeAnimalFieldLabel = new JLabel("Genre:");
        typeAnimalFieldLabel.setLabelFor(genreOfAnimal);

        this.newAnimalPanel.setLayout(new BoxLayout(newAnimalPanel, BoxLayout.Y_AXIS));

        JPanel fields = new JPanel(new GridLayout(0,2));
        fields.add(nameAnimalFieldLabel);
        fields.add(nameAnimalTextField);
        fields.add(typeAnimalFieldLabel);
        fields.add(genreOfAnimal);

        addButton = new JButton("Add");

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

    public JComboBox getGenreOfAnimal() {
        return genreOfAnimal;
    }

    public void addAddButtonListener(ActionListener addButtonListener) {
        addButton.addActionListener(addButtonListener);
    }
}

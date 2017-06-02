package tamagotchi.view;

import tamagotchi.controller.IController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Vector;

public class NewAnimalFrame extends JFrame {
    private Panel newAnimalPanel;
    private JButton addButton;
    private JTextField nameAnimalTextField;
    private JComboBox genreOfAnimal;
    private String[] genries = { "Dog", "Cat", "Parrot" };
    private Vector<String> petGenriesNames;
    private static final int MAX_ANIMAL_NAME_LENGTH = 10;

    public NewAnimalFrame(IController newAnimalController,  Vector<String> petGenriesNames) {
        newAnimalPanel = new Panel(newAnimalController);
        this.petGenriesNames = new Vector<>(petGenriesNames);

        setupNewAnimalFrame();
    }

    private void setupNewAnimalFrame() {
        this.setContentPane(newAnimalPanel);
        this.setTitle("Add animal...");
        this.setResizable(false);
        this.setAlwaysOnTop(true);
        this.setLayout(new BorderLayout());

        // create name text field
        nameAnimalTextField = new JTextField(MAX_ANIMAL_NAME_LENGTH);

        // create combo box for type of animal
        genreOfAnimal = new JComboBox(petGenriesNames);

        // label for a text field name
        JLabel nameAnimalFieldLabel = new JLabel("Name:");
        nameAnimalFieldLabel.setLabelFor(nameAnimalTextField);

        // label for a type of animal combo box
        JLabel typeAnimalFieldLabel = new JLabel("PetGenre:");
        typeAnimalFieldLabel.setLabelFor(genreOfAnimal);

        this.newAnimalPanel.setLayout(new BoxLayout(newAnimalPanel, BoxLayout.Y_AXIS));

        JPanel fields = new JPanel(new GridLayout(0,2));
        fields.add(nameAnimalFieldLabel);
        fields.add(nameAnimalTextField);
        fields.add(typeAnimalFieldLabel);
        fields.add(genreOfAnimal);

        addButton = new JButton("Add");
        this.getRootPane().setDefaultButton(addButton);

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

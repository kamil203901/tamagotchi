package tamagotchi.view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Vector;

import tamagotchi.controller.IController;

public class BaseFrame extends JFrame {
    private Panel basePanel;
    private JButton addAnimalButton;
    private Panel welcomePanel;
    private ImagePanel animalPanel;
    private JComboBox genreOfPet;
    private JComboBox action;
    private JButton makeAction;
    private Panel healthHappinessHungerPanel;
    private JPanel usernamePanel;
    private Label firstAnimalLabel;
    private Label secondAnimalLabel;
    private Label thirdAnimalLabel;
    private Label forthAnimalLabel;
    private JMenuItem loginButton;
    private JMenuItem registerButton;
    private JMenuItem closeButton;
    private JMenuItem logoutItem;
    private JPopupMenu logout;

    private ArrayList<ActionItem> actionsItems;

    private JProgressBar healthBar;
    private JProgressBar happinessBar;
    private JProgressBar hungerBar;
    private GridBagConstraints constraints;
    private JPanel[][] panelHolder;
    private Vector<String> genries;
   // private ArrayList<Vector<String>> actions;
    private Vector<String> actions;


    public BaseFrame(IController baseController) {
        makeAction = new JButton("Make action");
        actionsItems = new ArrayList<>();
        initializeLabels();
        basePanel = new Panel(baseController);
        healthHappinessHungerPanel = new Panel(baseController);
        healthBar = new JProgressBar(0, 100);
        happinessBar = new JProgressBar(0, 100);
        hungerBar = new JProgressBar(0, 100);
        animalPanel = new ImagePanel("img/bg.jpg");
        welcomePanel = new Panel(baseController);
        usernamePanel = new JPanel();
        logout = new JPopupMenu();
        logoutItem = new JMenuItem("Logout");
        logout.add(logoutItem);

        addAnimalButton = new JButton("Add new animal");
        welcomePanel.setOpaque(true);
        welcomePanel.setLayout(new GridLayout(1,1));
        welcomePanel.setBackground(new Color(223, 223, 88));
        panelHolder = new JPanel[4][4];
        for(int m = 0; m < 3; m++) {
            for(int n = 0; n < 4; n++) {
                panelHolder[m][n] = new JPanel();
                panelHolder[m][n].setOpaque(false);
                animalPanel.add(panelHolder[m][n]);
            }
        }
        setupFrame();
    }

    public void setWelcomePanelAsContenePane() {
        this.setContentPane(welcomePanel);
    }

    public void initializeLabels() {
        firstAnimalLabel = new Label();
        secondAnimalLabel = new Label();
        thirdAnimalLabel = new Label();
        forthAnimalLabel = new Label();
    }

    public void clearLabels() {
        firstAnimalLabel = null;
        secondAnimalLabel = null;
        thirdAnimalLabel = null;
        forthAnimalLabel = null;
    }


    public void setBasePanelAsContentPane() {
        this.setContentPane(basePanel);
        basePanel.setOpaque(true);
        basePanel.setBackground(new Color(223, 223, 88));
        basePanel.setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();
    }

    private void setupFrame() {
        this.setContentPane(welcomePanel);
        this.setTitle("Tamagotchi");
        this.setSize(new Dimension(800,600));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // create menus in base frame
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");

        loginButton = new JMenuItem("Login");
        menu.add(loginButton);

        registerButton = new JMenuItem("Register");
        menu.add(registerButton);
        menu.addSeparator();

        closeButton = new JMenuItem("Close");
        menu.add(closeButton);

        // set menu bar
        menuBar.add(menu);
        this.setJMenuBar(menuBar);
        this.setResizable(false);

        JLabel welcomeLabel = new JLabel("<html> <center> Welcome in Tamagotchi! </center> <br>" +
                "<center> Log in or register </center> </html>");
        welcomeLabel.setSize(300,300);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 30));
        welcomePanel.add(welcomeLabel);

        // need to be always on bottom
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void showLoginLabel(String login) {
        JLabel loginLabel = new JLabel(login);

        usernamePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        usernamePanel.setSize(new Dimension(200,20));
        usernamePanel.setBorder(new LineBorder(Color.BLACK));
        usernamePanel.add(loginLabel);
        usernamePanel.setComponentPopupMenu(logout);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.insets = new Insets(10,10,10,10);
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.FIRST_LINE_END;

        this.getContentPane().add(usernamePanel, constraints);
        this.setVisible(true);
    }


    public void removeLoginLabel() {
        if (usernamePanel != null) {
            usernamePanel.removeAll();
            this.remove(usernamePanel);
        }
    }

    public void showAddAnimalComboBox() {
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.ipadx = 40;
        constraints.ipady = 30;
        constraints.insets = new Insets(10,10,10,10);
        constraints.gridx = 2;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.LAST_LINE_END;


        this.getContentPane().add(addAnimalButton, constraints);
        this.setVisible(true);
    }

    public void removeComboBoxes() {
        if (genreOfPet != null && action != null) {
            this.remove(genreOfPet);
            this.remove(action);
        }
    }

    public void showBoxesAndButtonToMakeActionOnAllPets() {
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.insets = new Insets(10,10,1,10);
        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        genreOfPet = new JComboBox(genries);
        this.getContentPane().add(genreOfPet, constraints);

        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.insets = new Insets(0,10,1,10);
        constraints.gridx = 2;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        action = new JComboBox(actions);
        this.getContentPane().add(action, constraints);

        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.insets = new Insets(0,10,10,10);
        constraints.gridx = 2;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        this.getContentPane().add(makeAction, constraints);

        this.setVisible(true);
    }

    public void addAnimalToPanel(String path, int position, int petId, int genreId) {
        ImageIcon animal = new ImageIcon(path);
        switch (position) {
            case 0:
                firstAnimalLabel.setIcon(animal);
                firstAnimalLabel.setPetId(petId);
                firstAnimalLabel.setGenreId(genreId);
                firstAnimalLabel.setActions(actionsItems);
                panelHolder[2][0].add(firstAnimalLabel);
                System.out.println(firstAnimalLabel.getPetId());
                break;
            case 1:
                secondAnimalLabel.setIcon(animal);
                secondAnimalLabel.setPetId(petId);
                secondAnimalLabel.setGenreId(genreId);
                secondAnimalLabel.setActions(actionsItems);
                panelHolder[2][1].add(secondAnimalLabel);
                System.out.println(secondAnimalLabel.getPetId());
                break;
            case 2:
                thirdAnimalLabel.setIcon(animal);
                thirdAnimalLabel.setPetId(petId);
                thirdAnimalLabel.setGenreId(genreId);
                thirdAnimalLabel.setActions(actionsItems);
                panelHolder[2][2].add(thirdAnimalLabel);
                System.out.println(thirdAnimalLabel.getPetId());
                break;
            case 3:
                forthAnimalLabel.setIcon(animal);
                forthAnimalLabel.setPetId(petId);
                forthAnimalLabel.setGenreId(genreId);
                forthAnimalLabel.setActions(actionsItems);
                panelHolder[2][3].add(forthAnimalLabel);
                System.out.println(forthAnimalLabel.getPetId());
                break;
        }
        this.setVisible(true);
    }

    public void setActionsItems(ArrayList<String> actionName, ArrayList<String> type, ArrayList<String> genreId) {
        actionsItems = new ArrayList<>();
        if (actionName.size() == type.size() && type.size() == genreId.size()) {
            for (int i = 0; i < actionName.size(); i++) {
                this.actionsItems.add(new ActionItem(actionName.get(i), genreId.get(i), type.get(i)));
            }
        } else {
            System.out.println("ERROR");
        }
    }

    public void removeAnimalsFromPanel() {
        //animalPanel.removeAll();

        for(int m = 0; m < 3; m++) {
            for(int n = 0; n < 4; n++) {
                panelHolder[m][n].removeAll();
            }
        }

        this.getContentPane().setVisible(true);
    }



    public void showAnimalPanel() {
        animalPanel.setBounds(0,0,600,400);
        animalPanel.setBorder(new LineBorder(Color.BLACK));
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.insets = new Insets(10,10,10,10);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.gridheight = 8;
        constraints.anchor = GridBagConstraints.NORTHWEST;

        animalPanel.setLayout(new GridLayout(3,4));

        this.getContentPane().add(animalPanel, constraints);
        this.setVisible(true);

    }

    public void setHealth(int health) {
        healthBar.setValue(health);
    }

    public void setHappiness(int happiness) {
        happinessBar.setValue(happiness);
    }

    public void sethunger(int hunger) {
        hungerBar.setValue(hunger);
    }

    public void showHealthHappinessHungerPanel() {
        Border health = BorderFactory.createTitledBorder("Health");
        Border happiness = BorderFactory.createTitledBorder("Happiness");
        Border hunger = BorderFactory.createTitledBorder("Hunger");

        healthBar.setBorder(health);
        happinessBar.setBorder(happiness);
        hungerBar.setBorder(hunger);

        healthBar.setStringPainted(true);
        happinessBar.setStringPainted(true);
        hungerBar.setStringPainted(true);


        healthHappinessHungerPanel.setBorder(new LineBorder(Color.BLACK));
        healthHappinessHungerPanel.setLayout(new GridLayout(3,1));
        healthHappinessHungerPanel.add(healthBar);
        healthHappinessHungerPanel.add(happinessBar);
        healthHappinessHungerPanel.add(hungerBar);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.ipadx = 0;
        constraints.ipady = 0;
        constraints.insets = new Insets(10,10,10,10);
        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;

        this.getContentPane().add(healthHappinessHungerPanel, constraints);
        this.setVisible(true);

    }

    public void setGenries(Vector<String> genries) {
        this.genries = genries;
    }

    //public void setActions(ArrayList<Vector<String>> actions) {
       // this.actions = actions;
   // }

    public void setActions(Vector<String> actions) {
        this.actions = actions;
    }

    public JPanel getUsernamePanel() {
        return usernamePanel;
    }

    public void addLoginListener(ActionListener loginListener) {

        loginButton.addActionListener(loginListener);
    }

    public void addRegisterListener(ActionListener registerListener) {

        registerButton.addActionListener(registerListener);
    }

    public void addCloseListener(ActionListener closeListener) {

        closeButton.addActionListener(closeListener);
    }

    public void addNewAnimalListener(ActionListener newAnimalListener) {

        addAnimalButton.addActionListener(newAnimalListener);
    }

    public void addLogoutListener(ActionListener logoutListener) {

        logoutItem.addActionListener(logoutListener);
    }

    public Label getFirstAnimalLabel() {
        return firstAnimalLabel;
    }

    public Label getSecondAnimalLabel() {
        return secondAnimalLabel;
    }

    public Label getThirdAnimalLabel() {
        return thirdAnimalLabel;
    }

    public Label getForthAnimalLabel() {
        return forthAnimalLabel;
    }

    }







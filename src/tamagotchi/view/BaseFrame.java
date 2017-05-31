package tamagotchi.view;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;

import tamagotchi.controller.IController;
import tamagotchi.model.IPet;

public class BaseFrame extends JFrame {
    private Panel basePanel;
    private JButton addAnimalButton;
    private JButton feedDogs;
    private JButton feedCats;
    private JButton feedFish;
    private Panel welcomePanel;
    private ImagePanel animalPanel;
    private Panel healthHappinessHungerPanel;
    private JPanel usernamePanel;
    private JLabel firstAnimalLabel;
    private JLabel secondAnimalLabel;
    private JLabel thirdAnimalLabel;
    private JLabel forthAnimalLabel;
    private ImageIcon dog;
    private ImageIcon cat;
    private ImageIcon parrot;
    private JMenuItem loginButton;
    private JMenuItem registerButton;
    private JMenuItem closeButton;
    private JMenuItem logoutItem;
    private JPopupMenu logout;
    private JProgressBar healthBar;
    private JProgressBar happinessBar;
    private JProgressBar hungerBar;
    private GridBagConstraints constraints;
    private JPanel[][] panelHolder;


    public BaseFrame(IController baseController) {
        feedDogs = new JButton("Feed dogs");
        feedCats = new JButton("Feed cats");
        feedFish = new JButton("Feed fish");
        firstAnimalLabel = new JLabel();
        secondAnimalLabel = new JLabel();
        thirdAnimalLabel = new JLabel();
        forthAnimalLabel =  new JLabel();
        dog = new ImageIcon("img/dog.png");
        cat = new ImageIcon("img/dog.png");
        parrot = new ImageIcon("img/dog.png");

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
        setupFrame();
    }

    public void setWelcomePanelAsContenePane() {
        this.setContentPane(welcomePanel);
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

    public void showButtonToFeedAnimals() {
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.insets = new Insets(10,10,5,10);
        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        this.getContentPane().add(feedDogs, constraints);

        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.insets = new Insets(5,10,5,10);
        constraints.gridx = 2;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        this.getContentPane().add(feedCats, constraints);

        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.insets = new Insets(5,10,10,10);
        constraints.gridx = 2;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        this.getContentPane().add(feedFish, constraints);

        this.setVisible(true);
    }

    public void addAnimalToPanel(String animalType, int position) {
        ImageIcon animal;
        if (animalType.equals("dog")) {
            animal = dog;
        } else if (animalType.equals("cat")) {
            animal = cat;
        } else {
            animal = parrot;
        }
        switch (position) {
            case 0:
                firstAnimalLabel.setIcon(animal);
                panelHolder[3][0].add(firstAnimalLabel);
                break;
            case 1:
                secondAnimalLabel.setIcon(animal);
                panelHolder[3][1].add(secondAnimalLabel);
                break;
            case 2:
                thirdAnimalLabel.setIcon(animal);
                panelHolder[3][2].add(thirdAnimalLabel);
                break;
            case 3:
                forthAnimalLabel.setIcon(animal);
                panelHolder[3][3].add(forthAnimalLabel);
                break;
        }
        this.setVisible(true);
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

        animalPanel.setLayout(new GridLayout(4,4));

        for(int m = 0; m < 4; m++) {
            for(int n = 0; n < 4; n++) {
                panelHolder[m][n] = new JPanel();
                panelHolder[m][n].setOpaque(false);
                animalPanel.add(panelHolder[m][n]);
            }
        }



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

}

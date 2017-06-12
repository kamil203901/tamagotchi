package tamagotchi.view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Vector;

import tamagotchi.controller.IController;
import tamagotchi.controller.UpdateAnimalController;
import tamagotchi.model.DBConnect;

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
    private Label[] animalLabels;
    private JMenuItem loginButton;
    private JMenuItem registerButton;
    private JMenuItem adminPanelButton;
    private JMenuItem closeButton;
    private JMenuItem logoutItem;
    private JPopupMenu logout;

    private ArrayList<ActionItem> actionsItems;

    private static JProgressBar healthBar;
    private static JProgressBar happinessBar;
    private static JProgressBar hungerBar;
    private GridBagConstraints constraints;
    private JPanel[][] panelHolder;
    private Vector<String> genries;
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

    public JComboBox getGenreOfPet() {
        return genreOfPet;
    }

    public JComboBox getAction() {
        return action;
    }

    public JButton getMakeAction() {
        return makeAction;
    }

    public Vector<String> getGenries() {
        return genries;
    }

    public Vector<String> getActions() {
        return actions;
    }

    public void initializeLabels() {
        animalLabels = new Label[4];

        for (int i = 0; i < animalLabels.length; i++) {
            animalLabels[i] = new Label();
            int finalI = i;
            animalLabels[i].getDelete().addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    int petId = animalLabels[finalI].getPetId();
                    new DBConnect().deletePet(String.valueOf(petId));
                    updateFrame();
                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
        }
    }

    public void updateFrame() {
        DBConnect connection = new DBConnect();
        String login = connection.getLoggedUser().getLogin();
        this.setBasePanelAsContentPane();
        this.removeAnimalsFromPanel();
        this.removeLoginLabel();
        this.removeComboBoxes();
        this.clearLabels();
        this.initializeLabels();
        connection.login(connection.getUserByLogin(login));
        ArrayList<String> petsId = new ArrayList<>(connection.getLoggedUserPetsId());
        ArrayList<String> genriesNames = new ArrayList<>(connection.getLoggedUserPetGenriesNames(petsId));
        for (int i = 0; i < genriesNames.size(); i++) {
            this.addAnimalToPanel(connection.getGenrePath(genriesNames.get(i)), i,
                    Integer.parseInt(petsId.get(i)),
                    Integer.parseInt(connection.getGenreIdByPetId(petsId.get(i))),
                    Integer.parseInt(connection.getHealth(petsId.get(i))),
                    Integer.parseInt(connection.getHappiness(petsId.get(i))),
                    Integer.parseInt(connection.getHunger(petsId.get(i))));
        }
        UpdateAnimalController update = new UpdateAnimalController(this, connection);
        update.start();
        this.addUpdateAnimalPropertiesListener(update);
        this.setGenries(connection.getLoggedUser().getPetGenries());
        this.setActions(connection.getVectorOfActions());
        this.showAnimalPanel();
        this.showLoginLabel(login);
        this.showAddAnimalComboBox();
        this.showHealthHappinessHungerPanel();
        this.showBoxesAndButtonToMakeActionOnAllPets();
    }

    public void clearLabels() {
        for (int i = 0; i < animalLabels.length; i++) {
            animalLabels[i] = null;
        }
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

        adminPanelButton = new JMenuItem("Admin Panel");
        menu.add(adminPanelButton);
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

    public void addAnimalToPanel(String path, int position, int petId, int genreId, int health, int happiness, int hunger) {
        ImageIcon animal = new ImageIcon(path);
        switch (position) {
            case 0:
                animalLabels[0].setIcon(animal);
                animalLabels[0].setPetId(petId);
                animalLabels[0].setAnimalProperties(health, happiness, hunger);
                animalLabels[0].setGenreId(genreId);
                animalLabels[0].setActions(actionsItems);
                panelHolder[2][0].add(animalLabels[0]);
                System.out.println(animalLabels[0].getPetId());
                break;
            case 1:
                animalLabels[1].setIcon(animal);
                animalLabels[1].setPetId(petId);
                animalLabels[1].setAnimalProperties(health, happiness, hunger);
                animalLabels[1].setGenreId(genreId);
                animalLabels[1].setActions(actionsItems);
                panelHolder[2][1].add(animalLabels[1]);
                System.out.println(animalLabels[1].getPetId());
                break;
            case 2:
                animalLabels[2].setIcon(animal);
                animalLabels[2].setPetId(petId);
                animalLabels[2].setAnimalProperties(health, happiness, hunger);
                animalLabels[2].setGenreId(genreId);
                animalLabels[2].setActions(actionsItems);
                panelHolder[2][2].add(animalLabels[2]);
                System.out.println(animalLabels[2].getPetId());
                break;
            case 3:
                animalLabels[3].setIcon(animal);
                animalLabels[3].setPetId(petId);
                animalLabels[3].setAnimalProperties(health, happiness, hunger);
                animalLabels[3].setGenreId(genreId);
                animalLabels[3].setActions(actionsItems);
                panelHolder[2][3].add(animalLabels[3]);
                System.out.println(animalLabels[3].getPetId());
                break;
        }
        this.setVisible(true);
    }

    public void setActionsItems(ArrayList<String> actionName, ArrayList<String> type, ArrayList<String> genreId, ArrayList<Integer> points) {
        actionsItems = new ArrayList<>();
        if (actionName.size() == type.size() && type.size() == genreId.size() && genreId.size() == points.size()) {
            for (int i = 0; i < actionName.size(); i++) {
                this.actionsItems.add(new ActionItem(actionName.get(i), genreId.get(i), type.get(i), points.get(i)));
            }
        } else {
            System.out.println("ERROR");
        }
    }

    public void removeAnimalsFromPanel() {
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

    public Label[] getAnimalLabels() {
        return animalLabels;
    }

    public static JProgressBar getHealthBar() {
        return healthBar;
    }

    public static JProgressBar getHappinessBar() {
        return happinessBar;
    }

    public static JProgressBar getHungerBar() {
        return hungerBar;
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

    public void addAdminPanelListener(ActionListener adminPanelListener) {

        adminPanelButton.addActionListener(adminPanelListener);
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

    public void addUpdateAnimalPropertiesListener(MouseListener updateProperties) {
        for (int i = 0; i < animalLabels.length; i++) {
            animalLabels[i].addMouseListener(updateProperties);
        }
    }

    public void addMakeActionListener(ActionListener actionListener) {
        makeAction.addActionListener(actionListener);
    }

}







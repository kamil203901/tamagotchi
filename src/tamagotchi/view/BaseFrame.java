package tamagotchi.view;


import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import tamagotchi.controller.IController;

public class BaseFrame extends JFrame {
    private Panel basePanel;
    private Panel welcomePanel;
    private JPanel panel;
    private JMenuItem loginButton;
    private JMenuItem registerButton;
    private JMenuItem closeButton;
    private GridBagConstraints constraints;

    public BaseFrame(IController baseController) {
        basePanel = new Panel(baseController);
        welcomePanel = new Panel(baseController);
        welcomePanel.setOpaque(true);
        welcomePanel.setLayout(new GridLayout(1,1));
        welcomePanel.setBackground(new Color(194, 223, 160));
        setupFrame();
    }

    public void setBasePanelAsContentPane() {
        this.setContentPane(basePanel);
        basePanel.setOpaque(true);
        basePanel.setBackground(new Color(194, 223, 160));
        basePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        basePanel.setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();
    }

    private void setupFrame() {
        this.setContentPane(welcomePanel);
        this.setTitle("Tamagotchi");
        this.setSize(800, 600);
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
        this.setResizable(true);

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
        JLabel loginLabel = new JLabel("Login: " + login);

        panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.setSize(new Dimension(200,20));
        panel.setBorder(new LineBorder(Color.BLACK));
        panel.add(loginLabel);

        /*
         * making some constraints to use with gridbaglayout

        JButton button;
        this.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        if (true) {
            //natural height, maximum width
            c.fill = GridBagConstraints.HORIZONTAL;
        }

        button = new JButton("Button 1");
        if (true) {
            c.weightx = 0.5;
        }
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        this.getContentPane().add(button, c);

        button = new JButton("Button 2");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        this.getContentPane().add(button, c);

        button = new JButton("Button 3");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 0;
        this.getContentPane().add(button, c);

        button = new JButton("Long-Named Button 4");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;      //make this component tall
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 1;
        this.getContentPane().add(button, c);

        button = new JButton("5");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;       //reset to default
        c.weighty = 1.0;     //request any extra vertical space
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.insets = new Insets(0,0,0,0);  //top padding
        c.gridx = 2;       //aligned with button 2
        c.gridwidth = 1;   //2 columns wide
        c.gridy = 2;       //third row
        this.getContentPane().add(button, c);
        */

        //constraints.fill = GridBagConstraints.VERTICAL;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.insets = new Insets(10,10,10,10);
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.FIRST_LINE_END;

        this.getContentPane().add(panel, constraints);
        this.setVisible(true);
    }

    public void removeLoginLabel() {
        if (panel != null) {
            this.remove(panel);
        }
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

}

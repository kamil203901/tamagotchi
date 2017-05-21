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

        this.getContentPane().add(panel);
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

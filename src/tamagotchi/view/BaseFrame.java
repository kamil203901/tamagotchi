package tamagotchi.view;


import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import tamagotchi.controller.IController;

public class BaseFrame extends JFrame {
    private Panel basePanel;
    private JPanel panel;
    private JLabel loginLabel;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem loginButton;
    private JMenuItem registerButton;
    private JMenuItem closeButton;

    public BaseFrame(IController baseController) {
        basePanel = new Panel(baseController);
        basePanel.setOpaque(true);
        basePanel.setBackground(Color.lightGray);
        basePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        setupFrame();
    }

    private void setupFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        this.setContentPane(basePanel);
        this.setTitle("Tamagotchi");
        this.setSize(800, 600);
        this.setLocation(width / 2 - this.getWidth() / 2, height / 2 - this.getHeight() / 2);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // create menus in base frame
        menuBar = new JMenuBar();
        menu = new JMenu("Menu");

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

        // need to be always on bottom
        this.setVisible(true);



    }

    public void showLoginLabel(String login) {
        loginLabel = new JLabel("Login: " + login);

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

//lgon frame
package tamagotchi.view;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


import java.awt.*;
import tamagotchi.controller.AppController;

public class LoginFrame extends JFrame {
    private Panel loginPanel;

    public LoginFrame(AppController baseController) {
        loginPanel = new Panel(baseController);
        setupLoginFrame();
    }

    private void setupLoginFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        this.setContentPane(loginPanel);
        this.setTitle("Login");
        this.setSize(300, 200);
        this.setResizable(false);
        this.setLocation(width / 2 - this.getWidth() / 2, height / 2 - this.getHeight() / 2);
        this.setAlwaysOnTop(true);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // create login field
        JTextField loginTextField = new JTextField(15);
        loginTextField.setActionCommand("Login");

        // create password field
        JPasswordField passwordField = new JPasswordField(10);
        passwordField.setActionCommand("Password");

        // create some labels
        JLabel textFieldLabel = new JLabel("Login:");
        textFieldLabel.setLabelFor(loginTextField);
        JLabel passwordFieldLabel = new JLabel(" Password:");
        passwordFieldLabel.setLabelFor(passwordField);


        this.loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));

        JPanel fields = new JPanel(new FlowLayout(FlowLayout.CENTER));
        fields.add(textFieldLabel);
        fields.add(loginTextField);
        fields.add(passwordFieldLabel);
        fields.add(passwordField);

        JButton login = new JButton("login");
        JPanel button = new JPanel(new FlowLayout(FlowLayout.CENTER));
        button.add(login);

        this.loginPanel.add(fields);
        this.loginPanel.add(button);

        this.pack();
        this.setVisible(true);
    }



}

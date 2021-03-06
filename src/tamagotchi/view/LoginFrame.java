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
import java.awt.event.ActionListener;

import tamagotchi.controller.AppController;
import tamagotchi.controller.LoginController;

public class LoginFrame extends JFrame {
    private Panel loginPanel;
    private JButton loginButton;
    private JPasswordField passwordField;
    private JTextField loginTextField;
    private static final int LOGIN_MAX_LENGTH = 15;
    private static final int PASSWORD_MAX_LENGTH = 15;

    public LoginFrame(LoginController loginController) {
        loginPanel = new Panel(loginController);
        setupLoginFrame();
    }

    private void setupLoginFrame() {
        this.setContentPane(loginPanel);
        this.setTitle("Login");
        this.setResizable(false);
        this.setAlwaysOnTop(true);
        this.setLayout(new BorderLayout());

        // create login field
        loginTextField = new JTextField(LOGIN_MAX_LENGTH);
        loginTextField.setActionCommand("Login");

        // create password field
        passwordField = new JPasswordField(PASSWORD_MAX_LENGTH);
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

        loginButton = new JButton("Login");
        JPanel button = new JPanel(new FlowLayout(FlowLayout.CENTER));
        button.add(loginButton);

        this.loginPanel.add(fields);
        this.loginPanel.add(button);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JTextField getLoginTextField() {
        return loginTextField;
    }

    public void addLoginListener(ActionListener loginListener) {
        loginButton.addActionListener(loginListener);
        passwordField.addActionListener(loginListener);
        loginTextField.addActionListener(loginListener);
    }

}

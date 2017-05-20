package tamagotchi.view;

import tamagotchi.controller.IController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RegisterFrame extends JFrame {

    private Panel registerPanel;
    private JButton registerButton;
    private JPasswordField passwordField;
    private JTextField loginTextField;

    public RegisterFrame(IController registerController) {
        registerPanel = new Panel(registerController);
        setupRegisterFrame();
    }

    private void setupRegisterFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        this.setContentPane(registerPanel);
        this.setTitle("Register");
        this.setSize(300, 200);
        this.setResizable(false);
        this.setLocation(width / 2 - this.getWidth() / 2, height / 2 - this.getHeight() / 2);
        this.setAlwaysOnTop(true);
        this.setLayout(new BorderLayout());

        // create login field
        loginTextField = new JTextField(15);
        loginTextField.setActionCommand("Login");

        // create password field
        passwordField = new JPasswordField(10);
        passwordField.setActionCommand("Password");

        // create some labels
        JLabel textFieldLabel = new JLabel("Login:");
        textFieldLabel.setLabelFor(loginTextField);
        JLabel passwordFieldLabel = new JLabel(" Password:");
        passwordFieldLabel.setLabelFor(passwordField);


        this.registerPanel.setLayout(new BoxLayout(registerPanel, BoxLayout.Y_AXIS));

        JPanel fields = new JPanel(new FlowLayout(FlowLayout.CENTER));
        fields.add(textFieldLabel);
        fields.add(loginTextField);
        fields.add(passwordFieldLabel);
        fields.add(passwordField);

        registerButton = new JButton("register");
        JPanel button = new JPanel(new FlowLayout(FlowLayout.CENTER));
        button.add(registerButton);

        this.registerPanel.add(fields);
        this.registerPanel.add(button);

        this.pack();
        this.setVisible(true);
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JTextField getLoginTextField() {
        return loginTextField;
    }

    public void addRegisterListener(ActionListener registerListener) {
        registerButton.addActionListener(registerListener);
        loginTextField.addActionListener(registerListener);
        passwordField.addActionListener(registerListener);
    }





}

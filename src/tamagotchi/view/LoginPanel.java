package tamagotchi.view;

import javax.swing.*;

public class LoginPanel extends JPanel {
    private JLabel loginLabel;

    public LoginPanel(String login) {
        loginLabel = new JLabel("Login: " + login);
        loginLabel.setHorizontalAlignment(JLabel.LEFT);
    }
}

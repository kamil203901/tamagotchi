package tamagotchi.controller;

import tamagotchi.model.DBConnect;
import tamagotchi.view.RegisterFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterController implements IController {
    private RegisterFrame registerFrame;
    private DBConnect connection;

    public void start() {
        connection = new DBConnect();
        registerFrame = new RegisterFrame(this);
        registerFrame.addRegisterListener(new RegisterListener(registerFrame));
    }

    class RegisterListener implements ActionListener {
        RegisterFrame registerFrame;

        RegisterListener(RegisterFrame registerFrame) {
            this.registerFrame = registerFrame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String login = registerFrame.getLoginTextField().getText();
            String password = new String(registerFrame.getPasswordField().getPassword());
            connection.addUser(login, password);
            //JOptionPane.showMessageDialog(registerFrame, "Dodano uzytkownika: " + login);
            JOptionPane.showConfirmDialog(registerFrame, "User " + login + " registered successfully.",
                    "Register", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
            registerFrame.dispose();
        }
    }

}

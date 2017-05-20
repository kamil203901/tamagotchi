package tamagotchi.controller;

import tamagotchi.model.DBConnect;
import tamagotchi.view.BaseFrame;
import tamagotchi.view.LoginFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController implements IController {
    private LoginFrame loginFrame;
    private DBConnect connection;

    public void start() {
        connection = new DBConnect();
        loginFrame = new LoginFrame(this);
        loginFrame.addLoginListener(new LoginListener(loginFrame));
    }


    class LoginListener implements ActionListener {
        LoginFrame loginFrame;

        LoginListener(LoginFrame loginFrame) {
            this.loginFrame = loginFrame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String login = loginFrame.getLoginTextField().getText();
            String password = new String(loginFrame.getPasswordField().getPassword());
            boolean correctLogin = connection.isCorrectLoggingData(login, password);

            if (correctLogin) {
                JOptionPane.showConfirmDialog(loginFrame, "User " + login + " logged in correctly.",
                        "Login", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);

            } else {
                JOptionPane.showConfirmDialog(loginFrame, "Uncorrect login or password.",
                        "Login", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            }
            loginFrame.dispose();
        }
    }



}

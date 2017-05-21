package tamagotchi.controller;

import tamagotchi.model.DBConnect;
import tamagotchi.view.LoginFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController implements IController {
    private AppController appController;
    private LoginFrame loginFrame;
    private DBConnect connection;

    public LoginController(AppController appController) {
        this.appController = appController;
    }


    public void start() {
        connection = new DBConnect();
        loginFrame = new LoginFrame(this);
        loginFrame.addLoginListener(new LoginListener(loginFrame, appController));
    }


    class LoginListener implements ActionListener {
        LoginFrame loginFrame;
        AppController appController;

        LoginListener(LoginFrame loginFrame, AppController appController) {
            this.loginFrame = loginFrame;
            this.appController = appController;

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String login = loginFrame.getLoginTextField().getText();
            String password = new String(loginFrame.getPasswordField().getPassword());
            boolean correctLogin = connection.isCorrectLoggingData(login, password);

            if (correctLogin) {

                JOptionPane.showConfirmDialog(loginFrame, "User " + login + " logged in correctly.",
                        "Login", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                appController.getAppFrame().setBasePanelAsContentPane();
                appController.getAppFrame().removeLoginLabel();
                appController.getAppFrame().showLoginLabel(login);

            } else {
                JOptionPane.showConfirmDialog(loginFrame, "Uncorrect login or password.",
                        "Login", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            }
            loginFrame.dispose();
        }
    }



}

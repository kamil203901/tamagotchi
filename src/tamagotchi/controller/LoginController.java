package tamagotchi.controller;

import tamagotchi.model.DBConnect;
import tamagotchi.model.User;
import tamagotchi.view.LoginFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController implements IController {
    private AppController appController;
    private LoginFrame loginFrame;
    private DBConnect connection;

    LoginController(AppController appController) {
        this.appController = appController;
    }


    public void start() {
        connection = new DBConnect();
        loginFrame = new LoginFrame(this);
        loginFrame.addLoginListener(new LoginListener(loginFrame, appController, this));
    }


    class LoginListener implements ActionListener {
        LoginFrame loginFrame;
        AppController appController;
        LoginController loginController;

        LoginListener(LoginFrame loginFrame, AppController appController, LoginController loginController) {
            this.loginFrame = loginFrame;
            this.appController = appController;
            this.loginController = loginController;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            User oldUser = loginController.connection.getLoggedUser();

            if (oldUser != null) {
                loginController.connection.logout();
                System.out.println(oldUser.getLogin() + " is logout");
            }

            String login = loginFrame.getLoginTextField().getText();
            String password = new String(loginFrame.getPasswordField().getPassword());
            boolean correctLogin = connection.isCorrectLoggingData(login, password);

            if (correctLogin) {
                JOptionPane.showConfirmDialog(loginFrame, "User " + login + " logged in correctly.",
                        "Login", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                appController.getAppFrame().setBasePanelAsContentPane();
                loginController.connection.login(loginController.connection.getUserByLogin(login));
                appController.getAppFrame().removeLoginLabel();
                appController.getAppFrame().showLoginLabel(login);
                appController.getAppFrame().showAnimalPanel();
                appController.getAppFrame().showAddAnimalComboBox();
                appController.getAppFrame().showHealthHappinessHungerPanel();
                appController.getAppFrame().showButtonToFeedAnimals();

                if (loginController.connection.getLoggedUser() != null) {
                    System.out.println(loginController.connection.getLoggedUser().getLogin() + " is logged in.");
                }

            } else {
                JOptionPane.showConfirmDialog(loginFrame, "Uncorrect login or password.",
                        "Login", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            }
            loginFrame.dispose();
        }
    }



}

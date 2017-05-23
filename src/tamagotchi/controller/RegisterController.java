package tamagotchi.controller;

import tamagotchi.model.DBConnect;
import tamagotchi.view.RegisterFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterController implements IController {
    private RegisterFrame registerFrame;
    private AppController appController;
    private DBConnect connection;

    public RegisterController(AppController appController) {
        this.appController = appController;
    }


    public void start() {
        connection = new DBConnect();
        registerFrame = new RegisterFrame(this);
        registerFrame.addRegisterListener(new RegisterListener(registerFrame, appController));
    }

    class RegisterListener implements ActionListener {
        RegisterFrame registerFrame;
        AppController appController;

        RegisterListener(RegisterFrame registerFrame, AppController appController) {
            this.appController = appController;
            this.registerFrame = registerFrame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String login = registerFrame.getLoginTextField().getText();
            String name = registerFrame.getNameTextField().getText();
            String surname = registerFrame.getSurnameTextField().getText();
            String password = new String(registerFrame.getPasswordField().getPassword());
            String confirmPassword = new String(registerFrame.getConfirmPasswordField().getPassword());
            if (!password.equals(confirmPassword)) {
                JOptionPane.showConfirmDialog(registerFrame, "Uncorrect password.",
                        "Register", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                registerFrame.getPasswordField().setText("");
                registerFrame.getConfirmPasswordField().setText("");
                return;
            } else if (connection.ifUserExists(login)) {
                JOptionPane.showConfirmDialog(registerFrame, "User already exists.",
                        "Register", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                registerFrame.getLoginTextField().setText("");
                return;
            }
            connection.addUser(login, name, surname, password);
            JOptionPane.showConfirmDialog(registerFrame, "User " + login + " registered successfully.",
                    "Register", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
            appController.getAppFrame().setBasePanelAsContentPane();
            appController.getAppFrame().removeLoginLabel();
            appController.getAppFrame().showLoginLabel(login);
            registerFrame.dispose();
        }
    }

}

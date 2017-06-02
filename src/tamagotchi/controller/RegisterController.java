package tamagotchi.controller;

import tamagotchi.model.DBConnect;
import tamagotchi.model.User;
import tamagotchi.view.RegisterFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class RegisterController implements IController {
    private RegisterFrame registerFrame;
    private AppController appController;
    private DBConnect connection;

    RegisterController(AppController appController) {
        this.appController = appController;
    }

    @Override
    public void start() {
        connection = new DBConnect();
        registerFrame = new RegisterFrame(this);
        registerFrame.addRegisterListener(new RegisterListener(registerFrame, appController, this));
    }

    class RegisterListener implements ActionListener {
        RegisterFrame registerFrame;
        AppController appController;
        RegisterController registerController;

        RegisterListener(RegisterFrame registerFrame, AppController appController, RegisterController registerController) {
            this.appController = appController;
            this.registerFrame = registerFrame;
            this.registerController = registerController;
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
            appController.getAppFrame().setGenries(new Vector<>());
            appController.getAppFrame().setActions(new Vector<>());
            appController.getAppFrame().removeLoginLabel();
            appController.getAppFrame().removeComboBoxes();
            appController.getAppFrame().showLoginLabel(login);
            appController.getAppFrame().showAnimalPanel();
            appController.getAppFrame().showAddAnimalComboBox();
            appController.getAppFrame().showHealthHappinessHungerPanel();
            appController.getAppFrame().showBoxesAndButtonToMakeActionOnAllPets();
            registerFrame.dispose();
        }
    }
}

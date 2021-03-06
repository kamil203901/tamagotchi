package tamagotchi.controller;

import tamagotchi.model.DBConnect;
import tamagotchi.model.User;
import tamagotchi.view.LoginFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

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
            String login = loginFrame.getLoginTextField().getText();
            String password = new String(loginFrame.getPasswordField().getPassword());
            boolean correctLogin = connection.isCorrectLoggingData(login, password);

            if (correctLogin) {
                JOptionPane.showConfirmDialog(loginFrame, "User " + login + " logged in correctly.",
                        "Login", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                appController.getAppFrame().setBasePanelAsContentPane();
                appController.getAppFrame().removeAnimalsFromPanel();
                appController.getAppFrame().removeLoginLabel();
                appController.getAppFrame().removeComboBoxes();
                appController.getAppFrame().clearLabels();
                appController.getAppFrame().initializeLabels();
                loginController.connection.login(loginController.connection.getUserByLogin(login));
                ArrayList<String> petsId = new ArrayList<>(connection.getLoggedUserPetsId());
                ArrayList<String> genriesNames = new ArrayList<>(connection.getLoggedUserPetGenriesNames(petsId));
                for (int i = 0; i < genriesNames.size(); i++) {
                    appController.getAppFrame().addAnimalToPanel( connection.getGenrePath(genriesNames.get(i)), i,
                            Integer.parseInt(petsId.get(i)),
                            Integer.parseInt(connection.getGenreIdByPetId(petsId.get(i))),
                            Integer.parseInt(connection.getHealth(petsId.get(i))),
                            Integer.parseInt(connection.getHappiness(petsId.get(i))),
                            Integer.parseInt(connection.getHunger(petsId.get(i))));
                }
                UpdateAnimalController update = new UpdateAnimalController(appController.getAppFrame(), new DBConnect());
                update.start();
                update.addActionListenersToAnimalActions();
                appController.getAppFrame().addUpdateAnimalPropertiesListener(update);
                appController.getAppFrame().setGenries(loginController.connection.getLoggedUser().getPetGenries());
                appController.getAppFrame().setActions(connection.getVectorOfActions());/////////////
                appController.getAppFrame().showAnimalPanel();
                appController.getAppFrame().showLoginLabel(login);
                appController.getAppFrame().showAddAnimalComboBox();
                appController.getAppFrame().showHealthHappinessHungerPanel();
                appController.getAppFrame().showBoxesAndButtonToMakeActionOnAllPets();
                appController.getAppFrame().addMakeActionListener(new MakeActionController(connection, appController, loginController));
            } else {
                JOptionPane.showConfirmDialog(loginFrame, "Uncorrect login or password.",
                        "Login", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            }
            loginFrame.dispose();
        }
    }



}



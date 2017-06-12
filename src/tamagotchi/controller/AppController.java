package tamagotchi.controller;

import tamagotchi.model.DBConnect;
import tamagotchi.view.ActionItem;
import tamagotchi.view.BaseFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


public class AppController implements IController {
    private BaseFrame appFrame;
    private DBConnect connection = new DBConnect();

    public void start() {
        appFrame = new BaseFrame(this);
        ArrayList<String> ids = new ArrayList<>(connection.getActionsId());
        appFrame.setActionsItems(connection.getActionName(ids),
                connection.getTypeOfAction(ids),
                connection.getGenreFromIdAction(ids),
                connection.getPointsFromIdAction(ids));
        appFrame.addLoginListener(new LoginListener(this));
        appFrame.addRegisterListener(new RegisterListener(this));
        appFrame.addAdminPanelListener(new AdminPanelListener());
        appFrame.addCloseListener(new CloseListener(appFrame));
        appFrame.addNewAnimalListener(new AddAnimalListener(this));
        appFrame.addLogoutListener(new LogoutListener(this, connection));
    }

    BaseFrame getAppFrame() {
        return appFrame;
    }

    class LoginListener implements ActionListener {
        AppController appController;

        LoginListener(AppController appController) {
            this.appController = appController;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            LoginController lc = new LoginController(appController);
            lc.start();
        }
    }

    class RegisterListener implements ActionListener {
        AppController appController;

        RegisterListener(AppController appController) {
            this.appController = appController;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            RegisterController rc = new RegisterController(appController);
            rc.start();
        }
    }

    class AdminPanelListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            AdminPanelController apc = new AdminPanelController();
            apc.start();
        }
    }

    class CloseListener implements ActionListener {
        private BaseFrame baseFrame;

        CloseListener(BaseFrame appFrame) {
            this.baseFrame = appFrame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            baseFrame.dispose();
        }
    }

    class AddAnimalListener implements ActionListener {
        private AppController appController;

        AddAnimalListener(AppController appController) {
            this.appController = appController;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            NewAnimalController animalController = new NewAnimalController(appController);
            animalController.start();
        }
    }

    class LogoutListener implements ActionListener {
        private AppController appController;
        private DBConnect connection;

        LogoutListener(AppController appController, DBConnect connection) {
            this.appController = appController;
            this.connection = connection;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            appController.getAppFrame().removeLoginLabel();
            connection.logout();
            appController.getAppFrame().setWelcomePanelAsContenePane();
        }
    }

}

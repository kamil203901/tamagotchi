package tamagotchi.controller;

import tamagotchi.model.DBConnect;
import tamagotchi.view.BaseFrame;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AppController implements IController {
    private BaseFrame appFrame;
    private DBConnect connection;

    public void start() {
        appFrame = new BaseFrame(this);
        appFrame.addLoginListener(new LoginListener(this));
        appFrame.addRegisterListener(new RegisterListener(this));
        appFrame.addCloseListener(new CloseListener(appFrame));


    }

    public BaseFrame getAppFrame() {
        return appFrame;
    }

    public DBConnect getConnection() {
        return connection;
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


}

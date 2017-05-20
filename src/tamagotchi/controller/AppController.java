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
        appFrame.addLoginListener(new LoginListener());
        appFrame.addRegisterListener(new RegisterListener());
        appFrame.addCloseListener(new CloseListener(appFrame));


    }

    class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LoginController lc = new LoginController();
            lc.start();
        }
    }

    class RegisterListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            RegisterController rc = new RegisterController();
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

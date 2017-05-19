package tamagotchi.controller;

import tamagotchi.view.LoginFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController implements IController {
    private LoginFrame loginFrame;

    public void startLogin() {

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
            loginFrame.dispose();
        }
    }



}

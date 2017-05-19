package tamagotchi.controller;

import tamagotchi.view.BaseFrame;
import tamagotchi.view.LoginFrame;

public class AppController {
    private BaseFrame appFrame;
    private LoginFrame loginFrame;

    public void start() {
        appFrame = new BaseFrame(this);
    }

    public void login() {
        loginFrame = new LoginFrame(this);
    }
}

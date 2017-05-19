package tamagotchi.controller;

import tamagotchi.view.RegisterFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterController implements IController {
    private RegisterFrame registerFrame;

    public void startRegister() {
        registerFrame = new RegisterFrame(this);
        registerFrame.addRegisterListener(new RegisterListener(registerFrame));
    }

    class RegisterListener implements ActionListener {
        RegisterFrame registerFrame;

        RegisterListener(RegisterFrame registerFrame) {
            this.registerFrame = registerFrame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            registerFrame.dispose();
        }
    }

}

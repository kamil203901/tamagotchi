package tamagotchi.controller;

import tamagotchi.model.DBConnect;
import tamagotchi.view.NewAnimalFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewAnimalController implements IController {
    private AppController appController;
    private NewAnimalFrame newAnimalFrame;
    private DBConnect connection;


    NewAnimalController(AppController appController) {
        this.appController = appController;

    }

    @Override
    public void start() {
        connection = new DBConnect();
        newAnimalFrame = new NewAnimalFrame(appController);
        newAnimalFrame.addAddButtonListener(new AddButtonListener(appController, newAnimalFrame, connection));
    }

    class AddButtonListener implements ActionListener {
        private AppController appController;
        private NewAnimalFrame newAnimalFrame;
        private DBConnect connection;

        AddButtonListener(AppController appController, NewAnimalFrame newAnimalFrame, DBConnect connection) {
            this.appController = appController;
            this.newAnimalFrame = newAnimalFrame;
            this.connection = connection;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String genreOfAnimal = (String) newAnimalFrame.getGenreOfAnimal().getSelectedItem();
            String name = newAnimalFrame.getNameAnimalTextField().getText();
            JLabel usernameLabel = (JLabel) appController.getAppFrame().getUsernamePanel().getComponent(0);
            String username = usernameLabel.getText();
            if (name.equals("")) {
                JOptionPane.showMessageDialog(newAnimalFrame, "You must enter name");
                return;
            }
            connection.addAnimal(genreOfAnimal, name, username);
            appController.getAppFrame().addAnimalToPanel(genreOfAnimal.toLowerCase(), 3);


            System.out.println(genreOfAnimal);
            System.out.println(name);
            newAnimalFrame.dispose();
        }
    }
}

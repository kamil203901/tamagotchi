package tamagotchi.controller;

import tamagotchi.model.DBConnect;
import tamagotchi.view.NewAnimalFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

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

            if (name.equals("")) {
                JOptionPane.showMessageDialog(newAnimalFrame, "You must enter name");
                return;
            }

            connection.addAnimalToCurrentUser(genreOfAnimal, name, null);
            appController.getAppFrame().addAnimalToPanel(genreOfAnimal.toLowerCase(), 3);
            appController.getAppFrame().setGenries(connection.getLoggedUser().getPetGenries());
            appController.getAppFrame().setActions(new Vector<>());
            appController.getAppFrame().removeComboBoxes();
            appController.getAppFrame().showBoxesAndButtonToMakeActionOnAllPets();

            System.out.println(genreOfAnimal);
            System.out.println(name);
            newAnimalFrame.dispose();
        }
    }
}

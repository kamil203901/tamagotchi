package tamagotchi.controller;

import tamagotchi.model.DBConnect;
import tamagotchi.model.PetGenre;
import tamagotchi.model.User;
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

        if (connection.getLoggedUser().getPets().size() < User.MAX_AMOUNT_OF_PETS) {
            newAnimalFrame = new NewAnimalFrame(appController, connection.getGenriesNames());
            newAnimalFrame.addAddButtonListener(new AddButtonListener(appController, newAnimalFrame, connection));
        } else {
            System.out.println("You can't add more pets");
            JOptionPane.showMessageDialog(appController.getAppFrame() ,"You can't add more pets");
        }
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
            String idPet = null;
            String idGenre = null;
            int amount_of_pets = connection.getLoggedUser().getPets().size();

            if (name.equals("")) {
                JOptionPane.showMessageDialog(newAnimalFrame, "You must enter name");
                return;
            }

            if (!connection.isPetNameUnique(name)) {
                JOptionPane.showMessageDialog(newAnimalFrame, "Name already exists");
                return;
            }

            connection.addAnimalToCurrentUser(genreOfAnimal, name);
            idPet = connection.getPetId(genreOfAnimal, name);
            idGenre = connection.getGenreIdByPetId(idPet);
            appController.getAppFrame().addAnimalToPanel(connection.getGenrePath(genreOfAnimal), amount_of_pets,
                    Integer.parseInt(idPet),
                    Integer.parseInt(idGenre),
                    Integer.parseInt(connection.getHealth(idPet)),
                    Integer.parseInt(connection.getHappiness(idPet)),
                    Integer.parseInt(connection.getHunger(idPet)));
            UpdateAnimalController update = new UpdateAnimalController(appController.getAppFrame(), new DBConnect());
            update.start();
            update.deleteActionListenersToAnimalAcions();
            update.addActionListenersToAnimalActions();
            appController.getAppFrame().addUpdateAnimalPropertiesListener(update);
            appController.getAppFrame().setGenries(connection.getLoggedUser().getPetGenries());
            appController.getAppFrame().setActions(connection.getVectorOfActions());
            appController.getAppFrame().removeComboBoxes();
            appController.getAppFrame().showAnimalPanel();
            appController.getAppFrame().showBoxesAndButtonToMakeActionOnAllPets();


            System.out.println("New animal: " + genreOfAnimal.toLowerCase() + " " + name);
            newAnimalFrame.dispose();
        }
    }
}

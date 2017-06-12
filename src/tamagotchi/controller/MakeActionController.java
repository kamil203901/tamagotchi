package tamagotchi.controller;

import tamagotchi.model.DBConnect;
import tamagotchi.view.ActionItem;
import tamagotchi.view.Label;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;


public class MakeActionController implements ActionListener {
    DBConnect connect;
    AppController appController;
    LoginController loginController;

    public MakeActionController(DBConnect connect, AppController appController, LoginController loginController) {
        this.connect = connect;
        this.appController = appController;
        this.loginController = loginController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame baseFrame = appController.getAppFrame();
        JButton makeAction = appController.getAppFrame().getMakeAction();
        JComboBox genresOfPets = appController.getAppFrame().getGenreOfPet();
        JComboBox actions = appController.getAppFrame().getAction();
        Label[] labels = appController.getAppFrame().getAnimalLabels();
        Vector<String> genresNames = appController.getAppFrame().getGenries();
        Vector<String> actionsNames = appController.getAppFrame().getActions();

        String selectedPetGenre = genresOfPets.getSelectedItem().toString();
        String selectedActionName = actions.getSelectedItem().toString();

        int points = 0;
        boolean health = false;
        boolean happiness = false;
        boolean hunger = false;

        for (Label label : labels) {
            String genreId = connect.getGenreIdByPetId(String.valueOf(label.getPetId()));
            String genreName = connect.getGenreName(genreId);
            if (selectedPetGenre.equals(genreName)) {
                for (ActionItem action : label.getHealthActions()) {
                    if (points != 0)
                        break;
                    if (action.getText().equals(selectedActionName)) {
                        points = action.getPoints();
                        health = true;
                    }
                }

                for (ActionItem action : label.getHappinessActions()) {
                    if (points != 0)
                        break;
                    if (action.getText().equals(selectedActionName)) {
                        points = action.getPoints();
                        happiness = true;
                    }
                }

                for (ActionItem action : label.getHungerActions()) {
                    if (points != 0)
                        break;
                    if (action.getText().equals(selectedActionName)) {
                        points = action.getPoints();
                        hunger = true;
                    }
                }
                break;
            }
        }


        for (Label label : labels) {
            String petId = String.valueOf(label.getPetId());
            if (petId.equals("0"))
                break;
            String genreId = connect.getGenreIdByPetId(petId);
            String genreName = connect.getGenreName(genreId);
            if (!genreName.equals(selectedPetGenre))
                continue;

            if (health) {
                int healthValue = Integer.parseInt(connect.getHealth(petId));
                healthValue += points;
                if (healthValue >= 100)
                    healthValue = 100;
                connect.setHealth(petId, healthValue);
            } else if (happiness) {
                int happinessValue = Integer.parseInt(connect.getHappiness(petId));
                happinessValue += points;
                if (happinessValue >= 100)
                    happinessValue = 100;
                connect.setHappiness(petId, happinessValue);
            } else if (hunger) {
                int hungerValue = Integer.parseInt(connect.getHunger(petId));
                hungerValue -= points;
                if (hungerValue <= 0)
                    hungerValue = 0;
                connect.setHunger(petId, hungerValue);
            } else {
                JOptionPane.showMessageDialog(appController.getAppFrame(), "Incorrect operation for this kind of pet");
                break;
            }
        }
    }

}

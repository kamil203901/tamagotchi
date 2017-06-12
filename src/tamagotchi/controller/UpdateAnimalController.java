package tamagotchi.controller;

import tamagotchi.model.DBConnect;
import tamagotchi.view.ActionItem;
import tamagotchi.view.BaseFrame;
import tamagotchi.view.Label;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class UpdateAnimalController implements IController, MouseListener {
    private BaseFrame appFrame;
    private DBConnect connect;
    private String petID;
    private Label label;
    private Label[] labels;
    private static Timer timer;
    private static TimerTask timerTask;

    public UpdateAnimalController(BaseFrame appFrame, DBConnect connect) {
        this.appFrame = appFrame;
        this.connect = connect;
        this.labels = this.appFrame.getAnimalLabels();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        label = (Label)e.getSource();
        petID = String.valueOf(label.getPetId());
        int healthValue     = Integer.parseInt(connect.getHealth(petID));
        int happinessValue  = Integer.parseInt(connect.getHappiness(petID));
        int hungerValue     = Integer.parseInt(connect.getHunger(petID));

        appFrame.setHealth(healthValue);
        appFrame.setHappiness(happinessValue);
        appFrame.sethunger(hungerValue);
    }


    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void start() {
        if (timerTask != null) {
            timerTask.cancel();
        }
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                labels = appFrame.getAnimalLabels();
                for (int i = 0; i < labels.length ; i++) {
                    if (labels[i].getPetId() == 0) {
                        break;
                    }

                    String petId = String.valueOf(labels[i].getPetId());
                    int health = Integer.parseInt(connect.getHealth(petId));
                    int happiness = Integer.parseInt(connect.getHappiness(petId));
                    int hunger = Integer.parseInt(connect.getHunger(petId));

                    health -= 5;
                    happiness -= 3;
                    hunger += 2;

                    if (health <= 0) {
                        health = 0;
                    }

                    if (happiness <= 0) {
                        happiness = 0;
                    }

                    if (hunger >= 100) {
                        hunger = 100;
                    }

                    connect.setHealth(petId, health);
                    connect.setHappiness(petId, happiness);
                    connect.setHunger(petId, hunger);

                    if (petId.equals(petID)) {
                        appFrame.setHealth(health);
                        appFrame.setHappiness(happiness);
                        appFrame.sethunger(hunger);

                    }

                }
            }
        };

        timer.schedule(timerTask, 0, 10000);
    }

    public void deleteActionListenersToAnimalAcions() {
        for (int i = 0; i < labels.length; i++) {
            ArrayList<ActionItem> actions = labels[i].getActions();
            for (int j = 0; j < actions.size(); j++) {
                for (ActionListener al : actions.get(j).getActionListeners()) {
                    actions.get(j).removeActionListener(al);
                }
            }
        }


    }

    public void addActionListenersToAnimalActions() {
        DBConnect connect = this.connect;
        for (int i = 0; i < labels.length; i++) {
            ArrayList<ActionItem> actions = labels[i].getActions();
            int finalI = i;
            for (int j = 0; j < actions.size(); j++) {
                String petId = String.valueOf(labels[finalI].getPetId());
                int finalJ = j;
                switch (actions.get(j).getType()) {
                    case "karmienie":
                        actions.get(j).addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int hunger = Integer.parseInt(connect.getHunger(petId));
                                int points = actions.get(finalJ).getPoints();
                                int hungerSubtractPoints = hunger - points;
                                int last = hungerSubtractPoints >= 0 ? hungerSubtractPoints : 0;
                                connect.setHunger(petId, last);
                            }
                        });
                        break;
                    case "leczenie":
                        actions.get(j).addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int health = Integer.parseInt(connect.getHealth(petId));
                                int points = actions.get(finalJ).getPoints();
                                int healthAddPoints = health + points;
                                int last = healthAddPoints <= 100 ? healthAddPoints : 100;
                                connect.setHealth(petId, last);
                            }
                        });
                        break;
                    case "zabawa":
                        actions.get(j).addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int happiness = Integer.parseInt(connect.getHappiness(petId));
                                int points = actions.get(finalJ).getPoints();
                                int happinessAddPoints = happiness + points;
                                int last = happinessAddPoints <= 100 ? happinessAddPoints : 100;
                                connect.setHappiness(petId, last);
                            }
                        });
                        break;
                }
            }
        }
    }

}

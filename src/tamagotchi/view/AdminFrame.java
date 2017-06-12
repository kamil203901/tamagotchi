package tamagotchi.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;

public class AdminFrame extends JFrame {
    private JPanel adminPanel;
    private JTabbedPane tabbedPane;
    private JTable pets;
    private JTable users;
    private JTable actions;
    private JTable petsGenres;
    private JTable actionsGenres;
    private DefaultTableModel petsModel;
    private DefaultTableModel usersModel;
    private DefaultTableModel actionsModel;
    private DefaultTableModel petsGenresModel;
    private DefaultTableModel actionsGenresModel;
    private JButton deleteButton;
    private JButton addButton;

    public AdminFrame()  {
        adminPanel = new JPanel();
        tabbedPane = new JTabbedPane();
        deleteButton = new JButton("Delete");
        addButton = new JButton("Add");

        setupAdminFrame();
    }

    private void setupAdminFrame() {
        this.setContentPane(adminPanel);
        this.setTitle("Admin Panel");
        this.setResizable(true);
        this.setAlwaysOnTop(true);
        adminPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        setTables();

        tabbedPane.addTab("Pets", new JScrollPane(pets));
        tabbedPane.addTab("Users", new JScrollPane(users));
        tabbedPane.addTab("Actions", new JScrollPane(actions));
        tabbedPane.addTab("PetsGenres", new JScrollPane(petsGenres));
        tabbedPane.addTab("ActionsGenres", new JScrollPane(actionsGenres));

        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.ipadx = 40;
        constraints.ipady = 30;
        constraints.insets = new Insets(10,10,10,10);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.gridheight = 4;
        constraints.anchor = GridBagConstraints.CENTER;
        adminPanel.add(tabbedPane, constraints);

        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.ipadx = 40;
        constraints.ipady = 30;
        constraints.insets = new Insets(10,10,10,10);
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        adminPanel.add(addButton, constraints);

        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.ipadx = 40;
        constraints.ipady = 30;
        constraints.insets = new Insets(10,10,10,10);
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.FIRST_LINE_END;
        adminPanel.add(deleteButton, constraints);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void setTables() {
        petsModel = new DefaultTableModel() {
            String[] pets = {"idPet", "idPetGenre", "name", "age", "weight", "dateLastFeeding", "idUser"};

            @Override
            public int getColumnCount() {
                return pets.length;
            }

            @Override
            public String getColumnName(int index) {
                return pets[index];
            }
        };

        usersModel = new DefaultTableModel() {
            String[] users = {"id", "login", "name", "surname", "password"};

            @Override
            public int getColumnCount() {
                return users.length;
            }

            @Override
            public String getColumnName(int index) {
                return users[index];
            }
        };

        petsGenresModel = new DefaultTableModel() {
            String[] petsGenres = {"idPetsGenre", "name", "path"};

            @Override
            public int getColumnCount() {
                return petsGenres.length;
            }

            @Override
            public String getColumnName(int index) {
                return petsGenres[index];
            }
        };

        actionsModel = new DefaultTableModel() {
            String[] actions = {"id", "idActionGenre", "idPetGenre", "points"};

            @Override
            public int getColumnCount() {
                return actions.length;
            }

            @Override
            public String getColumnName(int index) {
                return actions[index];
            }
        };

        actionsGenresModel = new DefaultTableModel() {
            String[] actionsGenre = {"id", "actionGenreName", "actionName"};

            @Override
            public int getColumnCount() {
                return actionsGenre.length;
            }

            @Override
            public String getColumnName(int index) {
                return actionsGenre[index];
            }
        };

        pets = new JTable(petsModel);
        users = new JTable(usersModel);
        petsGenres = new JTable(petsGenresModel);
        actions = new JTable(actionsModel);
        actionsGenres = new JTable(actionsGenresModel);

    }

    public void addPetsRows(ArrayList<String> idPet, ArrayList<String> idPetGenre,
                            ArrayList<String> name, ArrayList<String> age,
                            ArrayList<String> weight, ArrayList<String> dateLastFeed,
                            ArrayList<String> idUser) {

        for (int i = 0; i < idPet.size(); i++) {
            petsModel.addRow(new Object[] {idPet.get(i), idPetGenre.get(i), name.get(i), age.get(i),
                    weight.get(i), dateLastFeed.get(i), idUser.get(i)});
        }

        for (int i = 0; i < 3; i++) {
            petsModel.addRow(new Object[] {null, null, null, null, null, null, null});
        }
    }

    public void addUsersRows(ArrayList<String> idUser, ArrayList<String> login,
                             ArrayList<String> name, ArrayList<String> surname,
                             ArrayList<String> password) {

        for (int i = 0; i < idUser.size(); i++) {
            usersModel.addRow(new Object[] {idUser.get(i), login.get(i), name.get(i),
            surname.get(i), password.get(i)});
        }

        for (int i = 0; i < 3; i++) {
            usersModel.addRow(new Object[] {null, null, null, null, null});
        }
    }

    public void addPetGenresRows(ArrayList<String> idPetGenre, ArrayList<String> genreName, ArrayList<String> path) {

        for (int i = 0; i < idPetGenre.size(); i++) {
            petsGenresModel.addRow(new Object[] {idPetGenre.get(i), genreName.get(i), path.get(i)});
        }

        for (int i = 0; i < 3; i++) {
            petsGenresModel.addRow(new Object[] {null, null, null});
        }
    }

    public void addActionsRows(ArrayList<String> idAction, ArrayList<String> idGenreAction, ArrayList<String> idPetGenre, ArrayList<String> points) {

        for (int i = 0; i < idAction.size(); i++) {
            actionsModel.addRow(new Object[] {idAction.get(i), idGenreAction.get(i), idPetGenre.get(i), points.get(i)});
        }

        for (int i = 0; i < 3; i++) {
            actionsModel.addRow(new Object[] {null, null, null, null});
        }
    }

    public void addActionGenresRows(ArrayList<String> idActionGenre, ArrayList<String> actionGenreName, ArrayList<String> actionName) {

        for (int i = 0; i < idActionGenre.size(); i++) {
            actionsGenresModel.addRow(new Object[] {idActionGenre.get(i), actionGenreName.get(i), actionName.get(i)});
        }

        for (int i = 0; i < 3; i++) {
            actionsGenresModel.addRow(new Object[] {null, null, null});
        }
    }

    public void addDeleteRowListener(ActionListener delete) {
        deleteButton.addActionListener(delete);
    }

    public void addAddButtonListener(ActionListener add) {
        addButton.addActionListener(add);
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public JTable getPets() {
        return pets;
    }

    public JTable getUsers() {
        return users;
    }

    public JTable getActions() {
        return actions;
    }

    public JTable getPetsGenres() {
        return petsGenres;
    }

    public JTable getActionsGenres() {
        return actionsGenres;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public DefaultTableModel getPetsModel() {
        return petsModel;
    }

    public DefaultTableModel getUsersModel() {
        return usersModel;
    }

    public DefaultTableModel getActionsModel() {
        return actionsModel;
    }

    public DefaultTableModel getPetsGenresModel() {
        return petsGenresModel;
    }

    public DefaultTableModel getActionsGenresModel() {
        return actionsGenresModel;
    }
}

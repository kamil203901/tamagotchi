package tamagotchi.controller;

import tamagotchi.model.DBConnect;
import tamagotchi.view.AdminFrame;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Vector;


public class AdminPanelController implements IController {
    private DBConnect connection;
    private AdminFrame adminFrame;



    @Override
    public void start() {
        connection = new DBConnect();
        adminFrame = new AdminFrame();
        adminFrame.addAddButtonListener(new AddRowListener());
        adminFrame.addDeleteRowListener(new DeleteRowListener());


        ArrayList<String> idPet             = new ArrayList<>(connection.getPetTableIdPet());
        ArrayList<String> idPetGenre        = new ArrayList<>(connection.getPetTableidPetGenre());
        ArrayList<String> name              = new ArrayList<>(connection.getPetTableName());
        ArrayList<String> age               = new ArrayList<>(connection.getPetTableAge());
        ArrayList<String> weight            = new ArrayList<>(connection.getPetTableWeight());
        ArrayList<String> dateLastFeed      = new ArrayList<>(connection.getPetTableDateLastFeed());
        ArrayList<String> idUser            = new ArrayList<>(connection.getPetTableIdUser());
        adminFrame.addPetsRows(idPet, idPetGenre, name, age, weight, dateLastFeed, idUser);

        idUser                              = new ArrayList<>(connection.getUserTableIdUser());
        ArrayList<String> login             = new ArrayList<>(connection.getUserTableLogin());
        name                                = new ArrayList<>(connection.getUserTableName());
        ArrayList<String> surname           = new ArrayList<>(connection.getUserTableSurname());
        ArrayList<String> password          = new ArrayList<>(connection.getUserTablePassword());
        adminFrame.addUsersRows(idUser, login, name, surname, password);

        idPetGenre                          = new ArrayList<>(connection.getPetGenreTableIdPetGenre());
        ArrayList<String> petGenreName      = new ArrayList<>(connection.getPetGenreTableGenreName());
        ArrayList<String> path              = new ArrayList<>(connection.getPetGenreTablePath());
        adminFrame.addPetGenresRows(idPetGenre, petGenreName, path);

        ArrayList<String> idAction          = new ArrayList<>(connection.getActionTableIdAction());
        ArrayList<String> idActionGenre     = new ArrayList<>(connection.getActionTableIdActionGenre());
        idPetGenre                          = new ArrayList<>(connection.getActionTableIdPetGenre());
        ArrayList<String> points            = new ArrayList<>(connection.getActionTablePoints());
        adminFrame.addActionsRows(idAction, idActionGenre, idPetGenre, points);

        idActionGenre                       = new ArrayList<>(connection.getActionGenreTableIdActionGenre());
        ArrayList<String> idActionGenreName = new ArrayList<>(connection.getActionGenreTableActionGenreName());
        ArrayList<String> actionName        = new ArrayList<>(connection.getActionGenreTableActionName());
        adminFrame.addActionGenresRows(idActionGenre, idActionGenreName, actionName);
        adminFrame.getPetsModel().addTableModelListener(new PetsTableChangeListener());


    }

    class AddRowListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    class DeleteRowListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedTab = adminFrame.getTabbedPane().getSelectedIndex();
            switch (selectedTab) {
                case 0: {
                    int selectedRow = adminFrame.getPets().getSelectedRow();
                    System.out.println(selectedRow);
                    String petId = adminFrame.getPetsModel().getValueAt(selectedRow, 0).toString();
                    connection.deletePet(petId);
                    adminFrame.getPetsModel().removeRow(selectedRow);
                }
                    break;
                case 1: {
                    int selectedRow = adminFrame.getUsers().getSelectedRow();
                    System.out.println(selectedRow);
                    String userId = adminFrame.getUsersModel().getValueAt(selectedRow, 0).toString();
                    connection.deleteUser(userId);
                    adminFrame.getUsersModel().removeRow(selectedRow);
                }
                    break;
                case 2: {
                    int selectedRow = adminFrame.getActions().getSelectedRow();
                    System.out.println(selectedRow);
                    String actionId = adminFrame.getActionsModel().getValueAt(selectedRow, 0).toString();
                    connection.deleteAction(actionId);
                    adminFrame.getActionsModel().removeRow(selectedRow);
                }
                    break;
                case 3: {
                    int selectedRow = adminFrame.getPetsGenres().getSelectedRow();
                    System.out.println(selectedRow);
                    String petGenreId = adminFrame.getPetsGenresModel().getValueAt(selectedRow, 0).toString();
                    connection.deletePetGenre(petGenreId);
                    adminFrame.getPetsGenresModel().removeRow(selectedRow);
                    Vector<String> columnSecond = new Vector<>();
                    for (int i = 0, c = adminFrame.getPetsModel().getDataVector().size()-3; i < c; i++) {
                        columnSecond.add(((Vector)adminFrame.getPetsModel().getDataVector().elementAt(i)).elementAt(1).toString());
                    }

                    int counter = 0;
                    for (int i = 0; i < columnSecond.size(); i++) {
                        String value = columnSecond.elementAt(i);
                        if (value.equals(petGenreId)) {
                            adminFrame.getPetsModel().removeRow(i-counter);
                            counter++;
                        }
                    }

                    Vector<String> columnThird = new Vector<>();
                    for (int i = 0, c = adminFrame.getActionsModel().getDataVector().size()-3; i < c; i++) {
                        columnThird.add(((Vector)adminFrame.getActionsModel().getDataVector().elementAt(i)).elementAt(2).toString());
                    }

                    counter = 0;
                    for (int i = 0; i < columnThird.size(); i++) {
                        String value = columnThird.elementAt(i);
                        if (value.equals(petGenreId)) {
                            adminFrame.getActionsModel().removeRow(i-counter);
                            counter++;
                        }
                    }


                }
                    break;
                case 4: {
                    int selectedRow = adminFrame.getActionsGenres().getSelectedRow();
                    System.out.println(selectedRow);
                    String actionGenreId = adminFrame.getActionsGenresModel().getValueAt(selectedRow, 0).toString();
                    connection.deleteActionGenre(actionGenreId);
                    adminFrame.getActionsGenresModel().removeRow(selectedRow);
                    Vector<String> columnSecond = new Vector<>();
                    for (int i = 0, c = adminFrame.getActionsModel().getDataVector().size()-3; i < c; i++) {
                        columnSecond.add(((Vector)adminFrame.getActionsModel().getDataVector().elementAt(i)).elementAt(1).toString());
                    }

                    int counter = 0;
                    for (int i = 0; i < columnSecond.size(); i++) {
                        String value = columnSecond.elementAt(i);
                        if (value.equals(actionGenreId)) {
                            adminFrame.getActionsModel().removeRow(i-counter);
                            counter++;
                        }
                    }
                }
                    break;
            }
        }
    }

    class PetsTableChangeListener implements TableModelListener {

        @Override
        public void tableChanged(TableModelEvent e) {
            int row = e.getFirstRow();
            int column = e.getColumn();
            if (column == -1)
                return;
            DefaultTableModel model = (DefaultTableModel)e.getSource();
            String columnName = model.getColumnName(column);
            Object data = model.getValueAt(row, column);
            System.out.println(columnName);

            int selectedIndex = adminFrame.getTabbedPane().getSelectedIndex();
            switch (selectedIndex) {
                case 0:
                    int rows0 = adminFrame.getPetsModel().getRowCount();
                    //Object obj = adminFrame.getPetsModel().ge
                    //System.out.println(obj);
                    break;
                case 1:
                    int rows1 = adminFrame.getUsersModel().getRowCount();
                    break;
                case 2:
                    int rows2 = adminFrame.getActionsModel().getRowCount();
                    break;
                case 3:
                    int rows3 = adminFrame.getPetsGenresModel().getRowCount();
                    break;
                case 4:
                    int rows4 = adminFrame.getActionsGenresModel().getRowCount();
                    break;
            }

        }
    }


}

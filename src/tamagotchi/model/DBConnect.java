package tamagotchi.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DBConnect {
    private Connection con;
    private Statement st;
    private ResultSet rs;
    private static ArrayList<User> users = new ArrayList<>();
    private static User currentUser;

    public DBConnect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tamagotchi", "root", "");
            st = con.createStatement();
            DBConnect.users = readUsersFromDatabase();
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

    }

    public void getData() {
        try {
            String query = "select * from uzytkownik";
            rs = st.executeQuery(query);
            System.out.println("Records from database");
            while (rs.next()) {
                String id = rs.getString("id");
                String login = rs.getString("login");
                String password = rs.getString("haslo");
                System.out.println("Id: " + id + " login: " + login + " password: " + password);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void login(User currentUser) {
        DBConnect.currentUser = currentUser;
    }

    public User getLoggedUser() {
        return currentUser;
    }

    public void logout() {
        DBConnect.currentUser = null;
    }

    public User getUserByLogin(String login) {
        User tmp;
        int i = 0;
        do {
            tmp = users.get(i);
            i++;
        } while (!tmp.getLogin().equals(login));
        return tmp;
    }

    private ArrayList<User> readUsersFromDatabase() {
        ArrayList<User> listOfUsers = new ArrayList<>();
        String login, name, surname, password;
        try {
            String query = "select * from uzytkownik";
            rs = st.executeQuery(query);
            while (rs.next()) {
                login = rs.getString("login");
                name = rs.getString("imie");
                surname = rs.getString("nazwisko");
                password = rs.getString("haslo");
                listOfUsers.add(new User(login, name, surname, password));
            }
            System.out.println("List of users loaded from database");
        } catch (Exception e) {
            System.out.println(e);
        }

        return listOfUsers;
    }

    private String getUserId(String username) {
        String id = null;
        try {
            String query = "select * from uzytkownik where login = '" + username + "'";
            rs = st.executeQuery(query);
            rs.next();
            id = rs.getString("id");
            System.out.println("User id:" + id);
        } catch (Exception e) {
            System.out.println(e);
        }

        return id;
    }

    public void addUser(String login, String imie, String nazwisko, String haslo) {
        try {
            users.add(new User(login, imie, nazwisko, haslo));
            System.out.println(users.size());
            String query = "insert into uzytkownik (login, imie, nazwisko, haslo) " +
                    "values ('" + login + "','" + imie + "','" + nazwisko + "','" + haslo + "')";
            st.executeUpdate(query);
            System.out.println("User " + login + " added to database.");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addAnimal(String genre, String name, String username) {
        try {
            String query = "select id_rodzaj_podopiecznego from rodzaj_podopiecznego where nazwa = '" + genre + "'";
            rs = st.executeQuery(query);
            rs.next();
            String id_genre = rs.getString("id_rodzaj_podopiecznego");
            String id_user = getUserId(username);

            query = "insert into podopieczny (id_rodzaj, imie, id_uzytkownik) " +
                    "values ('" + id_genre + "','" + name + "','" + id_user + "')";
            st.executeUpdate(query);
            System.out.println(genre + " " + name + " added to user " + username);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean isCorrectLoggingData(String login, String password) {
        try {
            String correctLogin, correctPassword;
            String query = "select * from uzytkownik where login = '" + login + "'";
            rs = st.executeQuery(query);
            while (rs.next()) {
                correctLogin = rs.getString("login");
                correctPassword = rs.getString("haslo");
                if (login.equals(correctLogin) && password.equals(correctPassword)) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return false;
    }

    public boolean ifUserExists(String login) {
        try {
            String tempLogin = null;
            String query = "select * from uzytkownik where login = '" + login + "'";
            rs = st.executeQuery(query);
            while (rs.next()) {
                tempLogin = rs.getString("login");
                if (tempLogin != null) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return false;
    }

    public void deleteUser(String login) {
        try {
            String query = "delete from uzytkownik where login = '" + login + "'";
            st.executeUpdate(query);
            System.out.println("User " + login + " deleted from database.");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
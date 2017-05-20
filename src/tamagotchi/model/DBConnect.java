package tamagotchi.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnect {
    private Connection con;
    private Statement st;
    private ResultSet rs;

    public DBConnect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tamagotchi", "root", "");
            st = con.createStatement();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

    }

    public void getData() {
        try {
            String query = "select * from Users";
            rs = st.executeQuery(query);
            System.out.println("Records from database");
            while (rs.next()) {
                String id = rs.getString("id");
                String login = rs.getString("login");
                String password = rs.getString("password");
                System.out.println("Id: " + id + " login: " + login + " password: " + password);

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addUser(String login, String password) {
        try {
            String query = "insert into Users (login, password) values ('" + login + "','" + password + "')";
            st.executeUpdate(query);
            System.out.println("User " + login + " added to database.");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean isCorrectLoggingData(String login, String password) {
        try {
            String correctLogin, correctPassword;
            String query = "select * from Users where login = '" + login + "'";
            rs = st.executeQuery(query);
            while (rs.next()) {
                correctLogin = rs.getString("login");
                correctPassword = rs.getString("password");
                if (login.equals(correctLogin) && password.equals(correctPassword)) {
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
            String query = "delete from Users where login = '" + login + "'";
            st.executeUpdate(query);
            System.out.println("User " + login + " deleted from database.");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

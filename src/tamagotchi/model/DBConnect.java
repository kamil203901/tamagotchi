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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Tamagotchi", "root", "");
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
                String id = rs.getString("ID");
                String login = rs.getString("Login");
                String password = rs.getString("Password");
                System.out.println("Id: " + id + " login: " + login + " password: " + password);

            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}

package tamagotchi.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

public class DBConnect {
    private Connection con;
    private Statement st;
    private ResultSet rs;
    private static ArrayList<User> users = new ArrayList<>();
    private static Vector<PetGenre> genries = new Vector<>();
    private static Vector<String> genriesNames = new Vector<>();
    private static User currentUser;

    public DBConnect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tamagotchi", "root", "");
            st = con.createStatement();
            DBConnect.users = getUsersFromDatabase();
            DBConnect.genries = getPetGenriesFromDatabase();
            DBConnect.genriesNames = getGenriesNames();
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
        User oldUser = DBConnect.currentUser;
        DBConnect.currentUser = currentUser;
        if (oldUser == null) {
            System.out.println(currentUser.getLogin() + " is logged in");
        } else if (oldUser.equals(currentUser)) {
            System.out.println(currentUser.getLogin() + " is already logged in");
        } else {
            System.out.println(oldUser.getLogin() + " is logout");
            System.out.println(currentUser.getLogin() + " is logged in");
        }

        this.setCurrentUsersPets();


    }

    public User getLoggedUser() {
        return currentUser;
    }

    public void logout() {
        if (currentUser == null) {
            return;
        }
        String loggedUser = currentUser.getLogin();
        DBConnect.currentUser = null;
        System.out.println(loggedUser + " is logout");
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


    private ArrayList<User> getUsersFromDatabase() {
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
            System.out.println("List of users read from database");
        } catch (Exception e) {
            System.out.println(e);
        }

        return listOfUsers;
    }

    // return genries
    private Vector<PetGenre> getPetGenriesFromDatabase() {
        Vector<PetGenre> vectorOfPetGenries = new Vector<>();
        String name, path;
        try {
            String query = "select * from rodzaj_podopiecznego";
            rs = st.executeQuery(query);
            while (rs.next()) {
                name = rs.getString("nazwa");
                path = rs.getString("sciezka");
                vectorOfPetGenries.add(new PetGenre(name, path));
            }
            System.out.println("Genries read from database");
        } catch (Exception e) {
            System.out.println(e);
        }

        return vectorOfPetGenries;
    }

    public Vector<String> getGenriesNames() {
        Vector<String> genriesNames = new Vector<>();

        for (PetGenre petGenre : DBConnect.genries) {
            genriesNames.add(petGenre.getName());
        }

        return genriesNames;
    }

    private void setCurrentUsersPets() {
        class TempPetInfo {
            private String petName;
            private String idPetGenre;
            private String weight;
            private String age;
            private TempPetInfo(String petName, String idPetGenre, String weight, String age) {
                this.petName = petName;
                this.idPetGenre = idPetGenre;
                this.weight = weight;
                this.age = age;
            }
            private String getPetName() {
                return petName;
            }
            private String getIdPetGenre() {
                return idPetGenre;
            }
            private String getWeight() {
                return weight;
            }
            private String getAge() {
                return age;
            }
        }

        ArrayList<Pet> listOfPets = new ArrayList<>();
        ArrayList<TempPetInfo> listOfTempPetInfo = new ArrayList<>();
        String currentUserId = getUserId(currentUser.getLogin());
        String petName, path, petGenre, idPetGenre, weight, age;

        try {
            String query = "select * from podopieczny where id_uzytkownik = '" + currentUserId + "'";
            rs = st.executeQuery(query);
            while (rs.next()) {
                petName = rs.getString("imie");
                idPetGenre = rs.getString("id_rodzaj");
                weight = rs.getString("waga");
                age = rs.getString("wiek");
                listOfTempPetInfo.add(new TempPetInfo(petName, idPetGenre, weight, age));
            }

            for (TempPetInfo info : listOfTempPetInfo) {
                query = "select * from rodzaj_podopiecznego where id_rodzaj_podopiecznego = '" + info.getIdPetGenre() + "'";
                rs = st.executeQuery(query);
                rs.next();
                petGenre = rs.getString("nazwa");
                path = rs.getString("sciezka");
                listOfPets.add(new Pet(info.getPetName(), new PetGenre(petGenre, path), Integer.parseInt(info.getAge()), Double.parseDouble(info.getWeight())));
            }

            System.out.println("List of users loaded from database");
        } catch (Exception e) {
            System.out.println(e);
        }

        currentUser.setPets(listOfPets);
        currentUser.setPetGenries();

        listOfPets = currentUser.getPets();
        for (Pet pet : listOfPets) {
            System.out.println(pet.getName() + " " + pet.getAge() + " " + pet.getPetGenre().getName() + " "
            + pet.getPetGenre().getPath() + " " + pet.getWeight() + " " + pet.getAge());
        }

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
            User user = new User(login, imie, nazwisko, haslo);
            users.add(user);
            this.login(user);
            System.out.println(users.size());
            String query = "insert into uzytkownik (login, imie, nazwisko, haslo) " +
                    "values ('" + login + "','" + imie + "','" + nazwisko + "','" + haslo + "')";
            st.executeUpdate(query);
            System.out.println("User " + login + " added to database.");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addAnimalToCurrentUser(String genre, String name, String path) {
        try {
            currentUser.addPet(new Pet(name, new PetGenre(genre, path)));
            String query = "select id_rodzaj_podopiecznego from rodzaj_podopiecznego where nazwa = '" + genre + "'";
            rs = st.executeQuery(query);
            rs.next();
            String id_genre = rs.getString("id_rodzaj_podopiecznego");
            String id_user = getUserId(currentUser.getLogin());

            query = "insert into podopieczny (id_rodzaj, imie, id_uzytkownik) " +
                    "values ('" + id_genre + "','" + name + "','" + id_user + "')";
            st.executeUpdate(query);
            System.out.println(genre + " " + name + " added to user " + currentUser.getLogin());

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
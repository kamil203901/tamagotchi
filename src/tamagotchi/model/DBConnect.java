package tamagotchi.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class DBConnect {
    private Connection con;
    private Statement st;
    private ResultSet rs;
    private static ArrayList<User> users = new ArrayList<>();
    private static Vector<PetGenre> genries = new Vector<>();
    private static Vector<String> genriesNames = new Vector<>();
    private static ArrayList<String> actions = new ArrayList<>();
    private static User currentUser;

    public DBConnect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tamagotchi", "root", "");
            st = con.createStatement();
            DBConnect.users = getUsersFromDatabase();
            DBConnect.genries = getPetGenriesFromDatabase();
            DBConnect.genriesNames = getGenriesNames();
            DBConnect.actions = getActionsFromDatabase();
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

    public String getGenrePath(String genreName) {
        String path = null;

        for (PetGenre petGenre : this.genries) {
            if (petGenre.getName().equals(genreName)) {
                path = petGenre.getPath();
                break;
            }
        }

        return path;
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

    // zwraca id rodzajow akcji (nie typow) dla danego id zwierzecia
    public ArrayList<String> getActionsId(String genreId) {
        ArrayList<String> actionsId = new ArrayList<>();

        try {
            String query = "select * from akcja where id_rodzaj_podopieczny = '" + genreId + "'";
            rs = st.executeQuery(query);
            while (rs.next()) {
                actionsId.add(rs.getString("id_rodzaj_akcji"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return actionsId;
    }

    // zwraca wektor nazw akcji dla danego gatunku zwierzecia
    public Vector<String> getVectorOfActions(String genre) {
        Vector<String> actions = new Vector<>();
        Vector<String> actionsId = new Vector<>();
        String genreId = getGenreId(genre);

        try {
            String query = "select * from akcja where id_rodzaj_podopieczny = '" + genreId + "'";
            rs = st.executeQuery(query);
            while (rs.next()) {
                actionsId.add(rs.getString("id_rodzaj_akcji"));
            }

            for (String id : actionsId) {
                query = "select * from rodzaj_akcji where id_rodzaj_akcji = '" + id + "'";
                rs = st.executeQuery(query);
                rs.next();
                actions.add(rs.getString("nazwa_akcji"));
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return actions;

    }

    // zwraca wektor nazw akcji dla wszystkich zwierzat
    public Vector<String> getVectorOfActions() {
        Vector<String> actions = new Vector<>();

        try {
            String query = "select * from rodzaj_akcji";
            rs = st.executeQuery(query);
            while (rs.next()) {
               actions.add(rs.getString("nazwa_akcji"));
            }


        } catch (Exception e) {
            System.out.println(e);
        }

        return actions;

    }

    // zwraca wektor wszystkich id akcji
    public ArrayList<String> getActionsId() {
        ArrayList<String> actionsId = new ArrayList<>();

        try {
            String query = "select * from akcja";
            rs = st.executeQuery(query);
            while (rs.next()) {
                actionsId.add(rs.getString("id_akcji"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return actionsId;
    }

    public ArrayList<String> getLoggedUserPetsId() {
        ArrayList<String> petsId = new ArrayList<>();
        String userId = getUserId(currentUser.getLogin());

        try {
            String query = "select * from podopieczny where id_uzytkownik = '" + getUserId(currentUser.getLogin()) + "'";
            rs = st.executeQuery(query);
            while (rs.next()) {
                petsId.add(rs.getString("id_podopieczny"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return petsId;
    }

    public ArrayList<String> getHealthActions(String genre) {
        String genreId = getGenreId(genre);
        ArrayList<String> actionsId = new ArrayList<>(getActionsId(genreId));
        ArrayList<String> actions = new ArrayList<>();

        for (String id : actionsId) {
            try {
                String query = "select * from rodzaj_akcji where id_rodzaj_akcji = '" + id +"' and nazwa_rodzaju_akcji = leczenie";
                rs = st.executeQuery(query);
                while (rs.next()) {
                    actions.add(rs.getString("nazwa_akcji"));
                }
                System.out.println("List of users read from database");
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return actions;
    }

    public ArrayList<String> getHappinessActions(String genre) {
        String genreId = getGenreId(genre);
        ArrayList<String> actionsId = new ArrayList<>(getActionsId(genreId));
        ArrayList<String> actions = new ArrayList<>();

        for (String id : actionsId) {
            try {
                String query = "select * from rodzaj_akcji where id_rodzaj_akcji = '" + id +"' and nazwa_rodzaju_akcji = zabawa";
                rs = st.executeQuery(query);
                while (rs.next()) {
                    actions.add(rs.getString("nazwa_akcji"));
                }
                System.out.println("List of users read from database");
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return actions;
    }

    public ArrayList<String> getFeedActions(String genre) {
        String genreId = getGenreId(genre);
        ArrayList<String> actionsId = new ArrayList<>(getActionsId(genreId));
        ArrayList<String> actions = new ArrayList<>();

        for (String id : actionsId) {
            try {
                String query = "select * from rodzaj_akcji where id_rodzaj_akcji = '" + id +"' and nazwa_rodzaju_akcji = karmienie";
                rs = st.executeQuery(query);
                while (rs.next()) {
                    actions.add(rs.getString("nazwa_akcji"));
                }
                System.out.println("List of users read from database");
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return actions;
    }

    public String getPetId(String genre, String name) {
        String petId = null;
        String genreId = getGenreId(genre);
        try {
            String query = "select * from podopieczny where id_rodzaj = '" + genreId + "' and " +
                    "imie = '" + name +"'";
            rs = st.executeQuery(query);
            rs.next();
            petId = rs.getString("id_podopieczny");
        } catch (Exception e) {
            System.out.println(e);
        }

        return petId;
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

    public ArrayList<String> getActionsFromDatabase() {
        ArrayList<String> actions = new ArrayList<>();
        String action;
        try {
            String query = "select * from rodzaj_akcji";
            rs = st.executeQuery(query);
            while (rs.next()) {
                action = rs.getString("nazwa_akcji");
                actions.add(action);
            }
            System.out.println("Actions read from database");
        } catch (Exception e) {
            System.out.println(e);
        }

        return actions;
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

    public ArrayList<String> getLoggedUserPetGenriesNames() {
        ArrayList<String> genriesId = new ArrayList<>();
        ArrayList<String> genriesNames = new ArrayList<>();
        String loggedUserId = getUserId(DBConnect.currentUser.getLogin());

        try {
            String query = "select id_rodzaj from podopieczny where id_uzytkownik = '" + loggedUserId + "'";
            rs = st.executeQuery(query);
            while (rs.next()) {
                genriesId.add(rs.getString("id_rodzaj"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        for (String id : genriesId) {
            try {
                String query = "select nazwa from rodzaj_podopiecznego where id_rodzaj_podopiecznego = '" + id + "'";
                rs = st.executeQuery(query);
                while (rs.next()) {
                    genriesNames.add(rs.getString("nazwa"));
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }


        return genriesNames;

    }

    public ArrayList<String> getLoggedUserPetGenriesNames(ArrayList<String> petsId) {
        ArrayList<String> genriesId = new ArrayList<>();
        ArrayList<String> genriesNames = new ArrayList<>();
        String loggedUserId = getUserId(DBConnect.currentUser.getLogin());

        for (String petId : petsId) {
            try {
                String query = "select id_rodzaj from podopieczny where id_uzytkownik = '" + loggedUserId + "' and " +
                        "id_podopieczny = '" + petId +"'";
                rs = st.executeQuery(query);
                rs.next();
                genriesId.add(rs.getString("id_rodzaj"));
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        for (String id : genriesId) {
            try {
                String query = "select nazwa from rodzaj_podopiecznego where id_rodzaj_podopiecznego = '" + id + "'";
                rs = st.executeQuery(query);
                while (rs.next()) {
                    genriesNames.add(rs.getString("nazwa"));
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }


        return genriesNames;

    }

    public Vector<String> getGenriesNames() {
        Vector<String> genriesNames = new Vector<>();

        for (PetGenre petGenre : DBConnect.genries) {
            genriesNames.add(petGenre.getName());
        }

        return genriesNames;
    }

    public boolean isPetNameUnique(String petName) {
        boolean isUnique = true;

        try {
            String query = "select * from podopieczny";
            rs = st.executeQuery(query);
            while (rs.next()) {
                if (petName.equals(rs.getString("imie"))) {
                    isUnique = false;
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return isUnique;
    }

    // zwraca liste wektorow nazw akcji dla wszystkich typow zwierzat
    public ArrayList<Vector<String>> getActions() {
        ArrayList<Vector<String>> actions = new ArrayList<>();
        ArrayList<String> genriesId = new ArrayList<>();

        try {
            String query = "select * from rodzaj_podopiecznego";
            rs = st.executeQuery(query);
            while (rs.next()) {
                genriesId.add(rs.getString("id_rodzaj_podopiecznego"));
            }

            for (String id : genriesId) {
                actions.add(getActionsNames(id));
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return actions;
    }

    public ArrayList<Vector<String>> getAllActionsId() {
        ArrayList<Vector<String>> actionsNames = new ArrayList<>(getActions());
        ArrayList<Vector<String>> actionsId = new ArrayList<>(actionsNames.size());

        for (int i = 0; i < actionsId.size(); i++) {
            for (int j = 0; j < actionsNames.get(i).size(); j++) {
                actionsId.get(i).add(getActionGenreId(actionsNames.get(i).get(j)));
            }
        }

        return actionsId;
    }

    // zwraca wektor nazw akcji dla danego typu zwierzecia
    public Vector<String> getActionsNames(String genreId) {
        Vector<String> actions = new Vector<>();
        Vector<String> actionsGenreId = new Vector<>();

        try {
            String query = "select * from akcja where id_rodzaj_podopieczny = '" + genreId + "'";
            rs = st.executeQuery(query);
            while (rs.next()) {
                actionsGenreId.add(rs.getString("id_rodzaj_akcji"));
            }

            for (String actionGenreId : actionsGenreId) {
                query = "select * from rodzaj_akcji where id_rodzaj_akcji = '" + actionGenreId + "'";
                rs = st.executeQuery(query);
                rs.next();
                actions.add(rs.getString("nazwa_akcji"));
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return actions;
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

        if (currentUserId == null) {
            return;
        }

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

    private String getGenreId(String genre) {
        String id = null;
        try {
            String query = "select * from rodzaj_podopiecznego where nazwa = '" + genre + "'";
            rs = st.executeQuery(query);
            rs.next();
            id = rs.getString("id_rodzaj_podopiecznego");
        } catch (Exception e) {
            System.out.println(e);
        }

        return id;
    }

    public String getGenreName(String genreId) {
        String genreName = null;
        try {
            String query = "select * from rodzaj_podopiecznego where id_rodzaj_podopiecznego = '" + genreId + "'";
            rs = st.executeQuery(query);
            rs.next();
            genreName = rs.getString("nazwa");
        } catch (Exception e) {
            System.out.println(e);
        }

        return genreName;
    }



    public String getGenreIdByPetId(String petId) {
        String genreId = null;
        try {
            String query = "select * from podopieczny where id_podopieczny = '" + petId + "'";
            rs = st.executeQuery(query);
            rs.next();
            genreId = rs.getString("id_rodzaj");

        } catch (Exception e) {
            System.out.println(e);
        }

        return genreId;
    }

    public String getActionGenreId(String actionName) {
        String actionGenreId = null;

        try {
            String query = "select * from rodzaj_akcji where nazwa_akcji = '" + actionName + "'";
            rs = st.executeQuery(query);
            rs.next();
            actionGenreId = rs.getString("id_rodzaj_akcji");
        } catch (Exception e) {
            System.out.println(e);
        }

        return actionGenreId;
    }

    public String getPetGenre(Vector<String> actionsGenries) {
        Vector<String> petGenriesId = new Vector<>();
        ArrayList<Vector<String>> allActionsNames = new ArrayList<>(getActions());
        try {
            String query = "select * from rodzaj_podopiecznego";
            rs = st.executeQuery(query);
            while (rs.next()) {
                petGenriesId.add(rs.getString("id_rodzaj_podopiecznego"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        boolean isEqual = true;
        String correctPetGenreId = null;

        for (int i = 0; i < petGenriesId.size(); i++) {
            Vector<String> actionsNames = new Vector<>(getActionsNames(petGenriesId.get(i)));
            correctPetGenreId = petGenriesId.get(i);

            for (int j = 0; j < actionsNames.size(); j++) {
                if (!actionsNames.get(j).equals(actionsGenries.get(j))) {
                    correctPetGenreId = null;
                    isEqual = false;
                    break;
                }
            }

            if (isEqual) {
                break;
            }
        }

        if (correctPetGenreId != null) {
            System.out.println("Znaleziono typ");
            return getGenreName(correctPetGenreId);
        }

        return null;
    }


    //zwraca typ akcji na podstawie id rodzaju akcji
    public String getTypeOfAction(String idGenreAction) {
        String typeOfAction = null;
        try {
            String query = "select * from rodzaj_akcji where id_rodzaj_akcji = '" + idGenreAction + "'";
            rs = st.executeQuery(query);
            rs.next();
            typeOfAction = rs.getString("nazwa_rodzaju_akcji");
        } catch (Exception e) {
            System.out.println(e);
        }

        return typeOfAction;
    }



    // zwraca typ akcji na podstawie id akcji
    public String getTypeOfActionByIdAction(String idAction) {
        String typeOfAction = null;
        String idGenreAction = null;
        try {
            String query = "select * from akcja where id_akcji = '" + idAction + "'";
            rs = st.executeQuery(query);
            rs.next();
            idGenreAction = rs.getString("id_rodzaj_akcji");
            typeOfAction = getTypeOfAction(idGenreAction);
        } catch (Exception e) {
            System.out.println(e);
        }



        return typeOfAction;
    }

    // zwraca nazwe akcji na podstawie id rodzaju akcji
    public String getActionName(String idAGenreAction) {
        String actionName = null;
        try {
            String query = "select * from rodzaj_akcji where id_rodzaj_akcji = '" + idAGenreAction + "'";
            rs = st.executeQuery(query);
            rs.next();
            actionName = rs.getString("nazwa_akcji");
        } catch (Exception e) {
            System.out.println(e);
        }

        return actionName;
    }

    // zwraca nazwe akcji na podstawie id akcji
    public String getActionNameByIdAction(String idAction) {
        String actionName = null;
        String idActionGenre = null;

        try {
            String query = "select * from akcja where id_akcji = '" + idAction + "'";
            rs = st.executeQuery(query);
            rs.next();
            idActionGenre = rs.getString("id_rodzaj_akcji");
        } catch (Exception e) {
            System.out.println(e);
        }

        actionName = getActionName(idActionGenre);

        return actionName;
    }


    // zwraca
    public ArrayList<String> getGenreFromIdAction(ArrayList<String> idAction) {
        ArrayList<String> genres = new ArrayList<>();
        ArrayList<String> genresId = new ArrayList<>();

        for (String id : idAction) {
            genres.add(getGenreFromIdAction(id));
        }

        genresId = getGenreId(genres);

        return genresId;
    }

    public int getPointsFromIdAction(String idAction) {
        int points = 0;

        try {
            String query = "select * from akcja where id_akcji = '" + idAction + "'";
            rs = st.executeQuery(query);
            rs.next();
            points = Integer.parseInt(rs.getString("points"));
        } catch (Exception e) {
            System.out.println(e);
        }

        return points;
    }

    public ArrayList<Integer> getPointsFromIdAction(ArrayList<String> idAction) {
        ArrayList<Integer> points = new ArrayList<Integer>();

        for (String id : idAction) {
            points.add(getPointsFromIdAction(id));
        }

        return points;
    }

    public ArrayList<String> getGenreId(ArrayList<String> genres) {
        ArrayList<String> genre = new ArrayList<>();
        for (String value : genres) {
            genre.add(getGenreId(value));
        }

        return genre;
    }

    public ArrayList<String> getActionName(ArrayList<String> idAction) {
        ArrayList<String> actionName = new ArrayList<>();
        for (int i = 0; i < idAction.size(); i++) {
            actionName.add(getActionNameByIdAction(idAction.get(i)));
        }

        return actionName;
    }

    public ArrayList<String> getTypeOfAction(ArrayList<String> idAction) {
        ArrayList<String> actionType = new ArrayList<>();
        for (String id : idAction) {
            actionType.add(getTypeOfActionByIdAction(id));
        }
        return actionType;
    }



    public String getGenreFromIdAction(String idAction) {
        String genre = null;
        String genreId = null;

        try {
            String query = "select * from akcja where id_akcji = '" + idAction + "'";
            rs = st.executeQuery(query);
            rs.next();
            genreId = rs.getString("id_rodzaj_podopieczny");

            query = "select * from rodzaj_podopiecznego where id_rodzaj_podopiecznego = '" + genreId + "'";
            rs = st.executeQuery(query);
            rs.next();
            genre = rs.getString("nazwa");

        } catch (Exception e) {
            System.out.println(e);
        }

        return genre;
    }


    public void addUser(String login, String imie, String nazwisko, String haslo) {
        try {
            User user = new User(login, imie, nazwisko, haslo);
            users.add(user);
            System.out.println(users.size());
            String query = "insert into uzytkownik (login, imie, nazwisko, haslo) " +
                    "values ('" + login + "','" + imie + "','" + nazwisko + "','" + haslo + "')";
            st.executeUpdate(query);
            System.out.println("User " + login + " added to database.");

            this.login(user);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ArrayList<String> getPetTableIdPet() {
        ArrayList<String> idPet = new ArrayList<>();
        try {
            String query = "select * from podopieczny";
            rs = st.executeQuery(query);
            while (rs.next()) {
                idPet.add(rs.getString("id_podopieczny"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return idPet;
    }

    public ArrayList<String> getPetTableidPetGenre() {
        ArrayList<String> idPetGenre = new ArrayList<>();
        try {
            String query = "select * from podopieczny";
            rs = st.executeQuery(query);
            while (rs.next()) {
                idPetGenre.add(rs.getString("id_rodzaj"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return idPetGenre;
    }

    public ArrayList<String> getPetTableName() {
        ArrayList<String> name = new ArrayList<>();
        try {
            String query = "select * from podopieczny";
            rs = st.executeQuery(query);
            while (rs.next()) {
                name.add(rs.getString("imie"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return name;
    }

    public ArrayList<String> getPetTableAge() {
        ArrayList<String> age = new ArrayList<>();
        try {
            String query = "select * from podopieczny";
            rs = st.executeQuery(query);
            while (rs.next()) {
                age.add(rs.getString("wiek"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return age;
    }

    public ArrayList<String> getPetTableWeight() {
        ArrayList<String> weight = new ArrayList<>();
        try {
            String query = "select * from podopieczny";
            rs = st.executeQuery(query);
            while (rs.next()) {
                weight.add(rs.getString("waga"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return weight;
    }

    public ArrayList<String> getPetTableDateLastFeed() {
        ArrayList<String> dateLastFeed = new ArrayList<>();
        try {
            String query = "select * from podopieczny";
            rs = st.executeQuery(query);
            while (rs.next()) {
                dateLastFeed.add(rs.getString("data_ostatniego_karmienia"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return dateLastFeed;
    }

    public ArrayList<String> getPetTableIdUser() {
        ArrayList<String> idUser = new ArrayList<>();
        try {
            String query = "select * from podopieczny";
            rs = st.executeQuery(query);
            while (rs.next()) {
                idUser.add(rs.getString("id_uzytkownik"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return idUser;
    }

    public ArrayList<String> getUserTableIdUser() {
        ArrayList<String> idUser = new ArrayList<>();
        try {
            String query = "select * from uzytkownik";
            rs = st.executeQuery(query);
            while (rs.next()) {
                idUser.add(rs.getString("id"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return idUser;
    }

    public ArrayList<String> getUserTableLogin() {
        ArrayList<String> login = new ArrayList<>();
        try {
            String query = "select * from uzytkownik";
            rs = st.executeQuery(query);
            while (rs.next()) {
                login.add(rs.getString("login"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return login;
    }

    public ArrayList<String> getUserTableName() {
        ArrayList<String> name = new ArrayList<>();
        try {
            String query = "select * from uzytkownik";
            rs = st.executeQuery(query);
            while (rs.next()) {
                name.add(rs.getString("imie"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return name;
    }

    public ArrayList<String> getUserTableSurname() {
        ArrayList<String> surname = new ArrayList<>();
        try {
            String query = "select * from uzytkownik";
            rs = st.executeQuery(query);
            while (rs.next()) {
                surname.add(rs.getString("nazwisko"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return surname;
    }

    public ArrayList<String> getUserTablePassword() {
        ArrayList<String> password = new ArrayList<>();
        try {
            String query = "select * from uzytkownik";
            rs = st.executeQuery(query);
            while (rs.next()) {
                password.add(rs.getString("haslo"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return password;
    }

    public ArrayList<String> getPetGenreTableIdPetGenre() {
        ArrayList<String> idPetGenre = new ArrayList<>();
        try {
            String query = "select * from rodzaj_podopiecznego";
            rs = st.executeQuery(query);
            while (rs.next()) {
                idPetGenre.add(rs.getString("id_rodzaj_podopiecznego"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return idPetGenre;
    }

    public ArrayList<String> getPetGenreTableGenreName() {
        ArrayList<String> genreName = new ArrayList<>();
        try {
            String query = "select * from rodzaj_podopiecznego";
            rs = st.executeQuery(query);
            while (rs.next()) {
                genreName.add(rs.getString("nazwa"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return genreName;
    }

    public ArrayList<String> getPetGenreTablePath() {
        ArrayList<String> path = new ArrayList<>();
        try {
            String query = "select * from rodzaj_podopiecznego";
            rs = st.executeQuery(query);
            while (rs.next()) {
                path.add(rs.getString("sciezka"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return path;
    }

    public ArrayList<String> getActionTableIdAction() {
        ArrayList<String> idAction = new ArrayList<>();
        try {
            String query = "select * from akcja";
            rs = st.executeQuery(query);
            while (rs.next()) {
                idAction.add(rs.getString("id_akcji"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return idAction;
    }

    public ArrayList<String> getActionTableIdActionGenre() {
        ArrayList<String> idActionGenre = new ArrayList<>();
        try {
            String query = "select * from akcja";
            rs = st.executeQuery(query);
            while (rs.next()) {
                idActionGenre.add(rs.getString("id_rodzaj_akcji"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return idActionGenre;
    }

    public ArrayList<String> getActionTableIdPetGenre() {
        ArrayList<String> idPetGenre = new ArrayList<>();
        try {
            String query = "select * from akcja";
            rs = st.executeQuery(query);
            while (rs.next()) {
                idPetGenre.add(rs.getString("id_rodzaj_podopieczny"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return idPetGenre;
    }

    public ArrayList<String> getActionTablePoints() {
        ArrayList<String> points = new ArrayList<>();
        try {
            String query = "select * from akcja";
            rs = st.executeQuery(query);
            while (rs.next()) {
                points.add(rs.getString("points"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return points;
    }

    public ArrayList<String> getActionGenreTableIdActionGenre() {
        ArrayList<String> idActionGenre = new ArrayList<>();
        try {
            String query = "select * from rodzaj_akcji";
            rs = st.executeQuery(query);
            while (rs.next()) {
                idActionGenre.add(rs.getString("id_rodzaj_akcji"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return idActionGenre;
    }

    public ArrayList<String> getActionGenreTableActionGenreName() {
        ArrayList<String> actionGenreName = new ArrayList<>();
        try {
            String query = "select * from rodzaj_akcji";
            rs = st.executeQuery(query);
            while (rs.next()) {
                actionGenreName.add(rs.getString("nazwa_rodzaju_akcji"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return actionGenreName;
    }

    public ArrayList<String> getActionGenreTableActionName() {
        ArrayList<String> actionName = new ArrayList<>();
        try {
            String query = "select * from rodzaj_akcji";
            rs = st.executeQuery(query);
            while (rs.next()) {
                actionName.add(rs.getString("nazwa_akcji"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return actionName;
    }











    public void addAnimalToCurrentUser(String genre, String name) {
        try {

            String query = "select * from rodzaj_podopiecznego where nazwa = '" + genre + "'";
            rs = st.executeQuery(query);
            rs.next();
            String id_genre = rs.getString("id_rodzaj_podopiecznego");
            String path = rs.getString("sciezka");
            String id_user = getUserId(currentUser.getLogin());

            currentUser.addPet(new Pet(name, new PetGenre(genre, path)));
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

    public void deleteUser(String userId) {
        try {
            String query = "delete from uzytkownik where id = '" + userId + "'";
            st.executeUpdate(query);
            System.out.println("User " + userId + " deleted from database.");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deleteAction(String actionId) {
        try {
            String query = "delete from akcja where id_akcji = '" + actionId + "'";
            st.executeUpdate(query);
            System.out.println("Action " + actionId + " deleted from database.");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deletePetGenre(String petGenreId) {
        try {
            String query = "delete from podopieczny where id_rodzaj = '" + petGenreId + "'";
            st.executeUpdate(query);
            query = "delete from akcja where id_rodzaj_podopieczny = '" + petGenreId + "'";
            st.executeUpdate(query);
            query = "delete from rodzaj_podopiecznego where id_rodzaj_podopiecznego = '" + petGenreId + "'";
            st.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deleteActionGenre(String actionGenreId) {
        try {
            String query = "delete from akcja where id_rodzaj_akcji = '" + actionGenreId + "'";
            st.executeUpdate(query);
            query = "delete from rodzaj_akcji where id_rodzaj_akcji = '" + actionGenreId + "'";
            st.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String getHealth(String petId) {
        String health = null;

        try {
            String query = "select * from podopieczny where id_podopieczny = '" + petId + "'";
            rs = st.executeQuery(query);
            rs.next();
            health = rs.getString("health");
        } catch (Exception e) {
            System.out.println(e);
        }

        return health;
    }

    public String getHappiness(String petId) {
        String happiness = null;

        try {
            String query = "select * from podopieczny where id_podopieczny = '" + petId + "'";
            rs = st.executeQuery(query);
            rs.next();
            happiness = rs.getString("happiness");
        } catch (Exception e) {
            System.out.println(e);
        }

        return happiness;
    }

    public String getHunger(String petId) {
        String hunger = null;

        try {
            String query = "select * from podopieczny where id_podopieczny = '" + petId + "'";
            rs = st.executeQuery(query);
            rs.next();
            hunger = rs.getString("hunger");
        } catch (Exception e) {
            System.out.println(e);
        }

        return hunger;
    }

    public void setHealth(String petId, int value) {
        String healthValue = String.valueOf(value);

        try {
            String query = "update podopieczny set health = '" + healthValue + "' where id_podopieczny = '" + petId + "'";
            st.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setHappiness(String petId, int value) {
        String happinessValue = String.valueOf(value);

        try {
            String query = "update podopieczny set happiness = '" + happinessValue + "' where id_podopieczny = '" + petId + "'";
            st.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setHunger(String petId, int value) {
        String hungerValue = String.valueOf(value);

        try {
            String query = "update podopieczny set hunger = '" + hungerValue + "' where id_podopieczny = '" + petId + "'";
            st.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deletePet(String petId) {
        try {
            String query = "delete from podopieczny where id_podopieczny = '" + petId + "'";
            st.executeUpdate(query);
            System.out.println("Pet " + petId + " deleteed from database.");
        } catch (Exception e) {
            System.out.print(e);
        }
    }
}
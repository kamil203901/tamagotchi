package tamagotchi.model;

import java.util.ArrayList;

public class User {
    private String login;
    private String name;
    private String surname;
    private String password;
    private ArrayList<IPet> listOfPets;
    private static final int MAX_AMOUNT_OF_PETS = 4;

    public User(String login, String name, String surname, String password) {
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.listOfPets = new ArrayList<>();
    }

    public void addPet(IPet pet) {
        if (listOfPets.size() < MAX_AMOUNT_OF_PETS) {
            this.listOfPets.add(pet);
        } else {
            System.out.println("You cannot have more pets");
        }
    }

    public void removePet(IPet pet) {
        if (listOfPets.contains(pet)) {
            listOfPets.remove(pet);
        }
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<IPet> getListOfPets() {
        return listOfPets;
    }
}

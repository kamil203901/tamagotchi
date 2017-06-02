package tamagotchi.model;

import java.util.ArrayList;
import java.util.Vector;

public class User {
    private String login;
    private String name;
    private String surname;
    private String password;
    private ArrayList<Pet> pets;
    private Vector<String> petGenries;
    public static final int MAX_AMOUNT_OF_PETS = 4;

    public User(String login, String name, String surname, String password) {
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.pets = new ArrayList<>();
        this.petGenries = new Vector<>();
    }

    public void addPet(Pet pet) {
        if (pets.size() < MAX_AMOUNT_OF_PETS) {
            this.pets.add(pet);
            if (!petGenries.contains(pet.getPetGenre().getName())) {
                petGenries.add(pet.getPetGenre().getName());
            }
        } else {
            System.out.println("You cannot have more pets");
        }
    }

    public void removePet(Pet pet) {
        if (pets.contains(pet)) {
            pets.remove(pet);
        }
    }

    public void setPetGenries() {
        String genre;
        if (pets != null) {
            for (Pet pet : pets) {
                genre = pet.getPetGenre().getName();
                if (!petGenries.contains(genre)) {
                    petGenries.add(genre);
                }
            }
        } else {
            System.out.println("Pets are not initialized yet");
        }
    }

    public Vector<String> getPetGenries() {
        return petGenries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (surname != null ? !surname.equals(user.surname) : user.surname != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (pets != null ? !pets.equals(user.pets) : user.pets != null) return false;
        return petGenries != null ? petGenries.equals(user.petGenries) : user.petGenries == null;
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (pets != null ? pets.hashCode() : 0);
        result = 31 * result + (petGenries != null ? petGenries.hashCode() : 0);
        return result;
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

    public ArrayList<Pet> getPets() {
        return pets;
    }

    public void setPets(ArrayList<Pet> pets) {
        this.pets = pets;
    }
}

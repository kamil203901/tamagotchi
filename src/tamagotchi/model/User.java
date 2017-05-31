package tamagotchi.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class User {
    private String login;
    private String name;
    private String surname;
    private String password;
    private ArrayList<IPet> listOfPets;
    private ArrayList<Pet> pets;
    private Vector<String> petGenries;
    private static final int MAX_AMOUNT_OF_PETS = 4;

    public User(String login, String name, String surname, String password) {
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.listOfPets = new ArrayList<>();
        this.pets = new ArrayList<>();
        this.petGenries = new Vector<>();
    }

    public void addPet(IPet pet) {
        if (listOfPets.size() < MAX_AMOUNT_OF_PETS) {
            this.listOfPets.add(pet);
        } else {
            System.out.println("You cannot have more pets");
        }
    }

    public void addPet(Pet pet) {
        if (pets.size() < MAX_AMOUNT_OF_PETS) {
            this.pets.add(pet);
            if (!petGenries.contains(pet.getGenre().getName())) {
                petGenries.add(pet.getGenre().getName());
            }
        } else {
            System.out.println("You cannot have more pets");
        }
    }

    public void removePet(IPet pet) {
        if (listOfPets.contains(pet)) {
            listOfPets.remove(pet);
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
                genre = pet.getGenre().getName();
                if (!petGenries.contains(genre)) {
                    petGenries.add(genre);
                }
            }
        } else {
            System.out.println("Pets are empty");
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

        if (getLogin() != null ? !getLogin().equals(user.getLogin()) : user.getLogin() != null) return false;
        if (getName() != null ? !getName().equals(user.getName()) : user.getName() != null) return false;
        if (getSurname() != null ? !getSurname().equals(user.getSurname()) : user.getSurname() != null) return false;
        if (getPassword() != null ? !getPassword().equals(user.getPassword()) : user.getPassword() != null)
            return false;
        if (getListOfPets() != null ? !getListOfPets().equals(user.getListOfPets()) : user.getListOfPets() != null)
            return false;
        return getPets() != null ? getPets().equals(user.getPets()) : user.getPets() == null;
    }

    @Override
    public int hashCode() {
        int result = getLogin() != null ? getLogin().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getSurname() != null ? getSurname().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getListOfPets() != null ? getListOfPets().hashCode() : 0);
        result = 31 * result + (getPets() != null ? getPets().hashCode() : 0);
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

    public ArrayList<IPet> getListOfPets() {
        return listOfPets;
    }

    public ArrayList<Pet> getPets() {
        return pets;
    }

    public void setPets(ArrayList<Pet> pets) {
        this.pets = pets;
        this.setPetGenries();
    }
}

package tamagotchi.model;

public class PetGenre {
    private String name;
    private String path;

    public PetGenre(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
}

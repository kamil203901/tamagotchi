package tamagotchi.model;

public class Genre {
    private String name;
    private String path;

    public Genre(String name, String path) {
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

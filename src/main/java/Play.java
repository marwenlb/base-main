public class Play {
    public enum PlayType {
        TRAGEDY, COMEDY
    }

    String name;
    PlayType type;

    public Play(String name, PlayType type) {
        this.name = name;
        this.type = type;
    }

    public PlayType getType() {
        return type;
    }
}

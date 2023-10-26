public class Play {
    public static final String TRAGEDY = "tragedy";
    public static final String COMEDY = "comedy";

    String name;
    String type;

    public Play(String name, String type) {
        if (!type.equals(TRAGEDY) && !type.equals(COMEDY)) {
            throw new IllegalArgumentException("Unknown type: " + type);
        }
        this.name = name;
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
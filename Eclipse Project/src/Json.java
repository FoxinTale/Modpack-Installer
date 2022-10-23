import java.util.ArrayList;

public class Json {
    static ArrayList<String> modlist = new ArrayList<>();
    static ArrayList<String> toRemove = new ArrayList<>();
    static ArrayList<String> checksums = new ArrayList<>();

    public static ArrayList<String> getModlist() {
        return modlist;
    }

    public static ArrayList<String> getToRemove() {
        return toRemove;
    }

    public static ArrayList<String> getChecksums() {
        return checksums;
    }

}

import SetUtils.Sets;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class resourceCheck {
    static String q = File.separator;
    static String dir = System.getProperty("user.dir") + q + "Modpack-Installer_lib";
    static ArrayList<String> sigarFiles = new ArrayList<>();

    public static boolean masterCheck() {
        if (dirCheck()) {
            if (jsonCheck()) {
                if (sigarCheck()) {
                    return true;
                }
            }
        }
        if (!dirCheck()) {
            // Halt right here, nothing can continue without the libraries and needed data.
            if (!jsonCheck()) {
                // Json files are important for the function.
            }
        }
        return false;
    }

    public static boolean dirCheck() {
        File dataDir = new File(dir);
        if (dataDir.exists()) {
            return true;
        }
        return false;
    }

    public static boolean sigarCheck() {
        jsonCheck();
        ArrayList<String> folderContent = new ArrayList<>();
        boolean emptySet = false;
        try {
            Files.list(new File(dir).toPath()).forEach(path -> {
                folderContent.add(path.getFileName().toString());
            });

            Set<String> folderSet = new HashSet<String>();
            Set<String> jsonSet = new HashSet<String>();
            Set<String> remainingSet = new HashSet<String>();
            for (String x : folderContent)
                folderSet.add(x);
            for (String x : sigarFiles)
                jsonSet.add(x);

            remainingSet = Sets.difference(folderSet, jsonSet);
            if (remainingSet.contains("json")) {
                emptySet = true;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return emptySet;
    }

    public static boolean jsonCheck() {
        String jsonDir = dir + q + "json" + q;
        File errors = new File(jsonDir + "errors.json");
        File modData = new File(jsonDir + "modData.json");
        File libraries = new File(jsonDir + "libraries.json");
        if (fileCheck(errors) && fileCheck(modData) && fileCheck(libraries)) {
            Json.libRead(libraries, sigarFiles);
            return true;
        }
        return false;
    }

    public static boolean fileCheck(File file) {
        if (file.exists()) {
            return true;
        }
        return false;
    }

    public static ArrayList<String> getSigarFiles() {
        return sigarFiles;
    }

    public static void setSigarFiles(ArrayList<String> sigarFiles) {
        resourceCheck.sigarFiles = sigarFiles;
    }
}

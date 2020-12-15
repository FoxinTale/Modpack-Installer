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

    public static boolean sigarCheck() {
        jsonCheck();
        ArrayList<String> folderContent = new ArrayList<>();
        boolean emptySet = false;
        try {
            Files.list(new File(dir).toPath()).forEach(path -> {
                folderContent.add(path.getFileName().toString());
            });
            Set<String> remainingSet;
            Set<String> folderSet = new HashSet<>(folderContent);
            Set<String> jsonSet = new HashSet<>(sigarFiles);

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

    public static void jsonCheck() {
        String jsonDir = dir + q + "json" + q;
        File errors = new File(jsonDir + "errors.json");
        File modData = new File(jsonDir + "modData.json");
        File libraries = new File(jsonDir + "libraries.json");
        if (fileCheck(errors) && fileCheck(modData) && fileCheck(libraries)) {
            Json.libRead(libraries, sigarFiles);
        }
    }

    public static boolean fileCheck(File file) {
        return file.exists();
    }

    public static ArrayList<String> getSigarFiles() {
        return sigarFiles;
    }

    public static void setSigarFiles(ArrayList<String> sigarFiles) {
        resourceCheck.sigarFiles = sigarFiles;
    }
}

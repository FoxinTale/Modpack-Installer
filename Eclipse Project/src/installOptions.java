import FileUtils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class installOptions extends Install {
    static Boolean packGood = false;

    public static void verifyInstall() {
        String downloadedMods = Common.getDownloadsLocation() + Common.q + "Modpack" + Common.q + "mods" + Common.q;
        String minecraftMods = Common.getMinecraftInstallLocation() + Common.q + "mods" + Common.q;
        ArrayList<String> modList = Json.getModlist();
        ArrayList<String> minecraftModsList = new ArrayList<>();
        ArrayList<String> downloadedModsList = new ArrayList<>();

        try {
            Files.list(new File(downloadedMods).toPath()).forEach(path -> {
                downloadedModsList.add(path.getFileName().toString());
            });

            Files.list(new File(minecraftMods).toPath()).forEach(item -> {
                minecraftModsList.add(item.getFileName().toString());
            });

            Set<Object> listOne = new HashSet<>(minecraftModsList);
            Set<Object> listTwo = new HashSet<>(modList);

            Set<Object> fileCheck = new HashSet<>(listTwo);
            fileCheck.removeAll(listOne);
            ArrayList<Object> missing = new ArrayList<Object>(fileCheck);

            if (fileCheck.isEmpty()) {
                packGood = true;
            }
            if (fileCheck.size() != 0) {
                for (int i = fileCheck.size(); i > 0; i--) {
                    fixMods(Common.q + missing.remove(i - 1));
                }
                verifyInstall();
            }
        } catch (IOException e) {
            // Mod file could not be found to copy over.
            GUI.errorOccured("Mantyke");
            Errors.mantyke();
        }
    }

    public static void fixMods(String missingMod) {
        File modsDirectory = new File(Common.getMinecraftInstall() + Common.q + "mods");
        File packDirectory = new File(Common.getDownloadsLocation() + Common.q + "Modpack" + Common.q + "mods");
        File missingModFile;
        try {
            missingModFile = new File(packDirectory + missingMod);
            FileUtils.copyFileToDirectory(missingModFile, modsDirectory, false);
        } catch (IOException e) {
            // Generic IO exception while copying mods.
   //         GUI.errors.setText("Lapras");
        }
    }
}

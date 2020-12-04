import FileUtils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class installOptions extends Install {
    static int ramSizeChosen = 0;
    static long memSize;
    static int ramSizeMb;
    static int ramSizeGb;
    static Font pretty;
    static Boolean packGood = false;
    Common co = new Common();

    static String modpackOptions = Common.getDownloadsLocation() + Common.q + "Modpack" + Common.q + "options" + Common.q;

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
            fileCheck.remove("gammabright");
            fileCheck.remove("1.7.10");
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
            GUI.errors.setText("Mantyke");
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
            GUI.errors.setText("Lapras");
        }
    }

    public static void backup() {
        File profileBackup = new File(Common.getMinecraftInstall() + Common.q + "profilebackup");
        File launcherProfiles = new File(Common.getMinecraftInstall() + Common.q + "launcher_profiles.json");
        if (!profileBackup.exists()) {
            profileBackup.mkdir();
            try {
                FileUtils.copyFileToDirectory(launcherProfiles, profileBackup);
                System.out.println(Strings.installerBackupProfile);
            } catch (IOException e) {
                // Eh, do nothing here.
            }
        }
    }

    public static void restore() {
        File profileBackup = new File(Common.getMinecraftInstall() + Common.q + "profilebackup");
        File launcherProfiles = new File(Common.getMinecraftInstall() + Common.q + "launcher_profiles.json");
        File oldProfile = new File(profileBackup + File.separator + "launcher_profiles.json");
        try {
            FileUtils.forceDelete(launcherProfiles);
            FileUtils.copyFileToDirectory(oldProfile, Common.getMinecraftInstallLocation());
            System.out.println(Strings.installerRestoreProfile);
            if (featuresUsed) {
                again();
            }
        } catch (IOException e) {
            // If the backed up profile could not be found.
            e.printStackTrace();
        }
    }

    public static void again() {
        int o = JOptionPane.showConfirmDialog(new JFrame(), Strings.installerAgainMessage, Strings.installerAgainTitle, JOptionPane.YES_NO_OPTION);
        if (o == JOptionPane.YES_OPTION) {
            optionsGUI.otherOptionsGUI();
        }
        if (o == JOptionPane.NO_OPTION) {
            end();
        }
    }
}

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
    static String q = File.separator;
    static Boolean packGood = false;
    static String modpackOptions = Driver.getDownloadsLocation() + q + "Modpack" + q + "options" + q;

    public static void verifyInstall() {
        String downloadedMods = Driver.getDownloadsLocation() + q + "Modpack" + q + "mods" + q;
        String minecraftMods = Driver.getMinecraftInstallLocation() + q + "mods" + q;
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

            Set<Object> listOne = new HashSet<Object>(minecraftModsList);
            Set<Object> listTwo = new HashSet<Object>(modList);

            Set<Object> fileCheck = new HashSet<Object>(listTwo);
            fileCheck.removeAll(listOne);
            fileCheck.remove("gammabright");
            fileCheck.remove("1.7.10");
            ArrayList<Object> missing = new ArrayList<Object>(fileCheck);

            if (fileCheck.isEmpty()) {
                packGood = true;
            }
            if (fileCheck.size() != 0) {
                for (int i = fileCheck.size(); i > 0; i--) {
                    fixMods(q + missing.remove(i - 1));
                }
                verifyInstall();
            }
        } catch (IOException e) {
            // Mod file could not be found to copy over.
            GUI.errors.setText("Mantyke");
        }
    }

    public static void fixMods(String missingMod) {
        File modsDirectory = new File(Driver.getMinecraftInstall() + q + "mods");
        File packDirectory = new File(Driver.getDownloadsLocation() + q + "Modpack" + q + "mods");
        File missingModFile;
        try {
            missingModFile = new File(packDirectory + missingMod);
            FileUtils.copyFileToDirectory(missingModFile, modsDirectory, false);

        } catch (IOException e) {
            // Generic IO exception while copying mods.
            GUI.errors.setText("Lapras");
        }
    }

/*	public static Boolean resourceCheck() {
		String home = System.getProperty("user.dir");
		File libsDir = new File(home + q + "Modpack-Installer_lib");
		return libsDir.exists();
	}*/

    public static void backup() {
        File profileBackup = new File(Driver.getMinecraftInstall() + q + "profilebackup");
        File launcherProfiles = new File(Driver.getMinecraftInstall() + q + "launcher_profiles.json");
        if (!profileBackup.exists()) {
            profileBackup.mkdir();
            try {
                FileUtils.copyFileToDirectory(launcherProfiles, profileBackup);
                System.out.println("Launcher Profiles backed up.");
            } catch (IOException e) {
                // Eh, do nothing here.
            }
        }
    }

    public static void restore() {
        File profileBackup = new File(Driver.getMinecraftInstall() + q + "profilebackup");
        File launcherProfiles = new File(Driver.getMinecraftInstall() + q + "launcher_profiles.json");
        File oldProfile = new File(profileBackup + File.separator + "launcher_profiles.json");
        try {
            FileUtils.forceDelete(launcherProfiles);
            FileUtils.copyFileToDirectory(oldProfile, Driver.getMinecraftInstallLocation());
            System.out.println(" Launcher Settings restored.");
            if (featuresUsed) {
                again();
            }
        } catch (IOException e) {
            // If the backed up profile could not be found.
            e.printStackTrace();
        }
    }

    public static void again() {
        String t = "Would you like to use another option? Selecting no exits the program.";
        int o = JOptionPane.showConfirmDialog(new JFrame(), t, "Another option?", JOptionPane.YES_NO_OPTION);
        if (o == JOptionPane.YES_OPTION) {
            optionsGUI.otherOptionsGUI();
        }
        if (o == JOptionPane.NO_OPTION) {
            end();
        }
    }
}

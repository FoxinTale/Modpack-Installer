import FileUtils.FileUtils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Install {
    static String q = File.separator;
    static int selectedOption = 0;
    static Boolean featuresUsed = false;
    static File modpackLocation = new File(Driver.getDownloadsLocation() + q + "Modpack");

    public static void install() {

        System.out.println(Strings.installerInstallNotice);
        modpackLocation.deleteOnExit();

        String minecraftPath = Driver.getMinecraftInstall();

        File minecraftMods = new File(minecraftPath + q + "mods");
        File minecraftConfig = new File(minecraftPath + q + "config");
       // File minecraftFlans = new File(minecraftPath + q + "Flan");

        File backups = new File(Driver.getDesktopLocation() + q + "Minecraft Stuff");
        String backupsLocation = backups.getAbsolutePath();
        File backupMods = new File(backupsLocation + q + "Mods");
        File backupConfig = new File(backupsLocation + q + "Config");
       // File backupFlans = new File(backupsLocation + q + "Flans");

        File modpackMods = new File(modpackLocation + q + "mods");
        File modpackConfig = new File(modpackLocation + q + "config");
        //File modpackFlans = new File(modpackLocation + q + "Flan");

        Driver.folderCreate(backups);
        Driver.folderCreate(backupMods);
        Driver.folderCreate(backupConfig);
        //Driver.folderCreate(backupFlans);

        moveFiles(minecraftMods, backupMods, Strings.installerBackupMods);
        moveFiles(minecraftConfig, backupConfig, Strings.installerBackupConfig);
        //moveFiles(minecraftFlans, backupFlans, " Backing up any Flans related items");

      /*  if (minecraftFlans.exists()) {
            minecraftFlans.delete();
        }*/

        installOptions.backup();
        //Driver.folderCreate(minecraftFlans);
        Driver.folderCreate(minecraftConfig);
        Driver.folderCreate(minecraftMods);

        System.out.println(Strings.installerInstalling);

        copyFiles(modpackMods, minecraftMods);
        copyFiles(modpackConfig, minecraftConfig);
        //copyFiles(modpackFlans, minecraftFlans);

        installOptions.verifyInstall();
        System.out.println(" Verifying install.");
        if (installOptions.packGood) {

            String t = "Would you like the installer to adjust your Java arguments in the launcher? This will also allow you to configure the amount of ram youallocate to Minecraft.";
            int o = JOptionPane.showConfirmDialog(new JFrame(), Strings.installerExtractResourceMessage, "Java Arguments", JOptionPane.YES_NO_OPTION);
            if (o == JOptionPane.YES_OPTION) {
                installOptions.backup();
                Memory.sliderGUI();
            }
            if (o == JOptionPane.NO_OPTION) {
                installFinalize();
            }
        }
        if (!installOptions.packGood) {
            GUI.errors.setText("Chikorita");
            //Installation failed to verify somehow.
            installOptions.verifyInstall();
        }
    }

    public static void installFinalize() {
        String message = "Your pre-existing mods and configs have been moved to a folder on the desktop named 'Minecraft Stuff'.";
        JOptionPane.showMessageDialog(new JFrame(), message, "Info", JOptionPane.INFORMATION_MESSAGE);

        String t = "Would you like the installer to check if the server is up?";
        int o = JOptionPane.showConfirmDialog(new JFrame(), t, "Server Test", JOptionPane.YES_NO_OPTION);
        if (o == JOptionPane.YES_OPTION) {
            serverPing();
        }
        if (o == JOptionPane.NO_OPTION) {
            resourcePack();
        }
    }

    public static void serverPing() {
        try {
            Socket server = new Socket();
            server.connect(new InetSocketAddress("IP ADDRESS", 25525), 10000);
            // Not putting my IP address out there for all to see.
            // If ran as is, it will throw the UnknownHostException below, but if changed to
            // a proper IP that error should never be thrown.
            String notification = "The server is up, Yay!";
            JOptionPane.showMessageDialog(new JFrame(), notification, "Server Up!", JOptionPane.INFORMATION_MESSAGE);
            server.close();
            if (!featuresUsed) {
                resourcePack();
            }
            if (featuresUsed) {
                installOptions.again();
            }
        } catch (UnknownHostException h) {
            // This should never happen.
            // EVER
            GUI.errors.setText("Marill");
        } catch (IOException i) {
            String notification = "It isn't up, please let me know, and I'll get on it as soon as I can.";
            JOptionPane.showMessageDialog(new JFrame(), notification, "Server Down", JOptionPane.ERROR_MESSAGE);
            // This is an error that must be caught, as the server sometimes crashes without
            // my knowing.
            if (!featuresUsed) {
                resourcePack();
            }
            if (featuresUsed) {
                installOptions.again();
            }
        }
    }

    public static void checkForMinecraftandForge() {
        String minecraftVersions = Driver.getMinecraftInstall() + q + "versions" + q;
        String moddedJson = minecraftVersions + "1.7.10-Forge10.13.4.1614-1.7.10" + q + "1.7.10-Forge10.13.4.1614-1.7.10.json";
        File vanillaMinecraft = new File(minecraftVersions + "1.7.10" + q + "1.7.10.jar");
        File vanillaMinecraftConfig = new File(minecraftVersions + "1.7.10" + q + "1.7.10.json");

        File moddedMinecraftConfig = new File(moddedJson);

        boolean modConfig = moddedMinecraftConfig.exists();
        boolean vanilla = vanillaMinecraft.exists();
        boolean vanillaConfig = vanillaMinecraftConfig.exists();
        if (!vanilla || !vanillaConfig) {
            JOptionPane.showMessageDialog(new JFrame(), Strings.noVanillaMessage, Strings.noVanillaTitle, JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        if (!modConfig) {
            JOptionPane.showMessageDialog(new JFrame(), Strings.noModsMessage, Strings.noModsTitle, JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    public static void resourcePack() {
        String t = "While you're at it, would you like to download and install the resource pack? ";
        int o = JOptionPane.showConfirmDialog(new JFrame(), t, "Resource Pack Install", JOptionPane.YES_NO_OPTION);
        if (o == JOptionPane.YES_OPTION) {
            resourcePacks.packGUI();
        }
        if (o == JOptionPane.NO_OPTION) {
            optionalMods();
        }
    }

    public static void optionalMods() {
        String question = "Final question. Would you like to install controller support and/or additional ambient mods?";
        int modsAsk = JOptionPane.showConfirmDialog(new JFrame(), question, "Optional Mods Install",
                JOptionPane.YES_NO_OPTION);
        if (modsAsk == JOptionPane.YES_OPTION) {
            modOptions.modOptionsGui();
        }
        if (modsAsk == JOptionPane.NO_OPTION) {
            end();
        }
    }

    public static void end() {
        String endMessage = Strings.installerThanksMessage;
        JOptionPane.showMessageDialog(new JFrame(), endMessage, Strings.installerThanksTitle, JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    public static void moveFiles(File dirOne, File dirTwo, String s) {
        if (dirOne.exists()) {
            if (dirTwo.exists()) {
                System.out.println(s);
                try {
                    Files.move(dirOne.toPath(), dirTwo.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException a) {
                    // Do nothing. Java complained if I didn't put this here.
                }
            }
        }
    }

    public static void copyFiles(File dirOne, File dirTwo) {
        if (dirOne.exists()) {
            if (dirTwo.exists()) {
                try {
                    FileUtils.copyDirectory(dirOne, dirTwo);
                } catch (IOException a) {
                    // Do nothing. Java complained if I didn't put this here.
                }
            }
        }
    }
}

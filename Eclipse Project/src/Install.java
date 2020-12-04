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
    static int selectedOption = 0;
    static Boolean featuresUsed = false;
    static File modpackLocation = new File(Common.getDownloadsLocation() + Common.q + "Modpack");

    public static void install() {

        System.out.println(Strings.installerInstallNotice);
        modpackLocation.deleteOnExit();

        String minecraftPath = Common.getMinecraftInstall();
        File minecraftMods = new File(minecraftPath + Common.q + "mods");
        File minecraftConfig = new File(minecraftPath + Common.q + "config");
        File backups = new File(Common.getDesktopLocation() + Common.q + "Minecraft Stuff");
        String backupsLocation = backups.getAbsolutePath();

        File backupMods = new File(backupsLocation + Common.q + "Mods");
        File backupConfig = new File(backupsLocation + Common.q + "Config");
        File modpackMods = new File(modpackLocation + Common.q + "mods");
        File modpackConfig = new File(modpackLocation + Common.q + "config");

        Common.folderCreate(backups);
        Common.folderCreate(backupMods);
        Common.folderCreate(backupConfig);

        moveFiles(minecraftMods, backupMods, Strings.installerBackupMods);
        moveFiles(minecraftConfig, backupConfig, Strings.installerBackupConfig);

        installOptions.backup();
        Common.folderCreate(minecraftConfig);
        Common.folderCreate(minecraftMods);

        System.out.println(Strings.installerInstalling);

        copyFiles(modpackMods, minecraftMods);
        copyFiles(modpackConfig, minecraftConfig);

        installOptions.verifyInstall();
        System.out.println(Strings.installerModsVerification);

        if (installOptions.packGood) {
            int o = JOptionPane.showConfirmDialog(new JFrame(), Strings.installerMemoryAdjustMessage, Strings.installerMemoryAdjustTitle, JOptionPane.YES_NO_OPTION);
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
        JOptionPane.showMessageDialog(new JFrame(), Strings.installerStuffBackupMessage, Strings.installerStuffBackupTitle, JOptionPane.INFORMATION_MESSAGE);

        int o = JOptionPane.showConfirmDialog(new JFrame(), Strings.installerPingServerMessage, Strings.installerPingServerTitle, JOptionPane.YES_NO_OPTION);
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
            JOptionPane.showMessageDialog(new JFrame(), Strings.serverUpMessage, Strings.serverUpTitle, JOptionPane.INFORMATION_MESSAGE);
            server.close();
            if (!featuresUsed) {
                resourcePack();
            }
            if (featuresUsed) {
                installOptions.again();
            }
        } catch (UnknownHostException h) {
            // This should never happen EVER.
            GUI.errors.setText("Marill");
        } catch (IOException i) {
            JOptionPane.showMessageDialog(new JFrame(), Strings.serverNotReachableMessage, Strings.serverNotReachableTitle, JOptionPane.ERROR_MESSAGE);
            // This is an error that must be caught, as the server sometimes crashes without my knowing.
            if (!featuresUsed) {
                resourcePack();
            }
            if (featuresUsed) {
                installOptions.again();
            }
        }
    }

    public static void checkForMinecraftandForge() {
        String minecraftVersions = Common.getMinecraftInstall() + Common.q + "versions" + Common.q;
        String moddedJson = minecraftVersions + "1.7.10-Forge10.13.4.1614-1.7.10" + Common.q + "1.7.10-Forge10.13.4.1614-1.7.10.json";
        File vanillaMinecraft = new File(minecraftVersions + "1.7.10" + Common.q + "1.7.10.jar");
        File vanillaMinecraftConfig = new File(minecraftVersions + "1.7.10" + Common.q + "1.7.10.json");

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
        int o = JOptionPane.showConfirmDialog(new JFrame(), Strings.installerResourceMessage, Strings.installerResourceTitle, JOptionPane.YES_NO_OPTION);
        if (o == JOptionPane.YES_OPTION) {
            resourcePacks.packGUI();
        }
        if (o == JOptionPane.NO_OPTION) {
            optionalMods();
        }
    }

    public static void optionalMods() {
        int modsAsk = JOptionPane.showConfirmDialog(new JFrame(), Strings.installerOptionalModsMessage, Strings.installerOptionalModsTitle, JOptionPane.YES_NO_OPTION);
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

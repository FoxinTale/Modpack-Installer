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
    static File modpackLocation = new File(Common.getDownloadsLocation() + Common.q + "modpack");

    public static void install() {
        System.out.println(Strings.installerInstallNotice);
        modpackLocation.deleteOnExit();
        File modsLocation = new File(Common.getMinecraftInstall() + Common.q);

      //  installOptions.verifyInstall();
        System.out.println(Strings.installerModsVerification);
        backupMinecraftContent();
        copyFiles(modpackLocation, modsLocation);

        if (installOptions.packGood) {
            installFinalize();
        }
        if (!installOptions.packGood) {
            GUI.errorOccured("Chickorita");
            Errors.chickorita();
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
            end();
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
            end();
        } catch (UnknownHostException h) {
            // This should never happen EVER.
            GUI.errorOccured("Marill");
            Errors.marill();
        } catch (IOException i) {
            JOptionPane.showMessageDialog(new JFrame(), Strings.serverNotReachableMessage, Strings.serverNotReachableTitle, JOptionPane.ERROR_MESSAGE);
            // This is an error that must be caught, as the server sometimes crashes without my knowing.
        }
    }

    public static void end() {
        String endMessage = Strings.installerThanksMessage;
        JOptionPane.showMessageDialog(new JFrame(), endMessage, Strings.installerThanksTitle, JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    public static void backupMinecraftContent(){
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

        //installOptions.backup();
        Common.folderCreate(minecraftConfig);
        Common.folderCreate(minecraftMods);

        System.out.println(Strings.installerInstalling);

        copyFiles(modpackMods, minecraftMods);
        copyFiles(modpackConfig, minecraftConfig);
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

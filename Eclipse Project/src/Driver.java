import GUI.CustomOutputStream;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
/*
 * This program is free software. It comes without any warranty, to the extent
 * permitted by applicable law. You can redistribute it and/or modify it under
 * the terms of the Do What The Fuck You Want To Public License, Version 2, as
 * published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 *
 * You're welcome to improve upon it. If you do, please let me know, and I'll
 * implement your fix / improvement, and give you credit for it. I know my code
 * isn't the best, I'm only a beginner.
 *
 * For the try/catch blocks, it outputs a Pokemon name for a bit of humor. If
 * someone tells me it said a Pokemon, I can look at where it is supposed to be
 * outputting that, and understand what's going wrong.
 *
 */

public class Driver {

    static String zipFile = "Modpack.zip";
    static int installProgress = 0;
    static int selectedOption = 0;
    static Boolean updateTime = false;
    static Boolean musicPack = false;

    private static File minecraftInstallLocation = null;
    private static String minecraftInstall = null;
    private static String downloadsLocation = null;
    private static String desktopLocation = null;
    private static String minecraftDefaultInstall = null;

    private static PrintStream standardOut; // This sets the outputs.

    public static void main(String[] args) throws IOException {
        // Creating the custom output stream.
        PrintStream printStream = new PrintStream(new CustomOutputStream(GUI.consoleOutput));
        standardOut = System.out;
        System.setOut(printStream);
        System.setErr(printStream);
        Boolean validOS = false;
        String OS = System.getProperty("os.name"); // This gets the name of the current operating system.
        if (OS.equals("Windows 10") || OS.equals("Windows 8.1") || OS.equals("Windows 7")) {
            // For modern Windows systems
            validOS = true;
            osDetect.isWindows();
            //Install.checkForMinecraftandForge();
            //Json.installerUpdateCheck();
            //Updater.installerUpdateCheck();
            GUI.launchGUI();
        }

        if (OS.equals("Windows Vista") || OS.equals("Windows XP")) {
            String message = "Why are you still using this computer?";
            JOptionPane.showMessageDialog(new JFrame(), message, "Outdated OS", JOptionPane.ERROR_MESSAGE);
            // For older Windows systems. Which, frankly, why are you still using?
            System.exit(0);
        }

        if (OS.equals("Linux") || OS.equals("Unix")) {
            // Good on you for using Linux!
            validOS = true;
            osDetect.isLinux();
            //Install.checkForMinecraftandForge();
            GUI.launchGUI();
        }

        if (OS.equals("Mac")) {
            // Big oof.
            validOS = true;
            osDetect.isMac();
            //Install.checkForMinecraftandForge();
            GUI.launchGUI();
        }

        if (!validOS) {
            // If you see this, well... why are you trying to run this on an unsupported OS?
            // Except Solaris..Which is really uncommon. If you're running that, I'd like to
            // know why.
            String message = "Your OS is not supported by this installer.";
            JOptionPane.showMessageDialog(new JFrame(), message, "Unknown Operating System", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    // This explains itself. I decided on a function because otherwise this would
    // have been repeated many times, taking up space in the code.
    public static void folderCreate(File folder) {
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    // Just your average getters and setters... Nothing interesting to see.
    public static File getMinecraftInstallLocation() {
        return minecraftInstallLocation;
    }

    public static void setMinecraftInstallLocation(File minecraftInstallLocation) {
        Driver.minecraftInstallLocation = minecraftInstallLocation;
    }

    public static String getMinecraftInstall() {
        return minecraftInstall;
    }

    public static void setMinecraftInstall(String minecraftInstall) {
        Driver.minecraftInstall = minecraftInstall;
    }

    public static String getDownloadsLocation() {
        return downloadsLocation;
    }

    public static void setDownloadsLocation(String downloadsLocation) {
        Driver.downloadsLocation = downloadsLocation;
    }

    public static String getDesktopLocation() {
        return desktopLocation;
    }

    public static void setDesktopLocation(String desktopLocation) {
        Driver.desktopLocation = desktopLocation;
    }

    public static String getMinecraftDefaultInstall() {
        return minecraftDefaultInstall;
    }

    public static void setMinecraftDefaultInstall(String minecraftDefaultInstall) {
        Driver.minecraftDefaultInstall = minecraftDefaultInstall;
    }

    public static int getSelectedOption() {
        return selectedOption;
    }

    public static void setSelectedOption(int selectedOption) {
        Driver.selectedOption = selectedOption;
    }
}

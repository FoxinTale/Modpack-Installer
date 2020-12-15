import GUI.CustomOutputStream;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
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
 *
 * All strings can be found in the Strings file.
 *
 */

public class Driver {
    static int selectedOption = 0;
    static Boolean updateTime = false;
    public static URL installerUpdateLink;
    private static PrintStream standardOut; // This sets the outputs.


static Boolean validOS = false;
    public static void main(String[] args) throws IOException {
         //Creating the custom output stream.
        PrintStream printStream = new PrintStream(new CustomOutputStream(GUI.consoleOutput));
        standardOut = System.out;
        System.setOut(printStream);
        System.setErr(printStream);

        installerUpdateLink = new URL(Common.installerLatestLink);
        String OS = System.getProperty("os.name"); // This gets the name of the current operating system.
        if (OS.equals("Windows 10") || OS.equals("Windows 8.1") || OS.equals("Windows 7")) {
            // For modern Windows systems
            validOS = true;
            osDetect.isWindows();
            sharedActions();
        }

        if (OS.equals("Windows Vista") || OS.equals("Windows XP")) {
            JOptionPane.showMessageDialog(new JFrame(), Strings.oldOSMessage, Strings.oldOSTitle, JOptionPane.ERROR_MESSAGE);
            // For older Windows systems. Which, frankly, why are you still using?
            System.exit(0);
        }

        if (OS.equals("Linux") || OS.equals("Unix")) {
            // Good on you for using Linux!
            validOS = true;
            osDetect.isLinux();
            sharedActions();
        }

        if (OS.equals("Mac")) {
            // Big oof.
            validOS = true;
            osDetect.isMac();
            sharedActions();
        }

        if (!validOS) {
            // If you see this, well... why are you trying to run this on an unsupported OS?
            // Except Solaris..Which is really uncommon. If you're running that, I'd like to
            // know why.
            JOptionPane.showMessageDialog(new JFrame(), Strings.unknownOSMessage, Strings.unknownOSTitle, JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    public static void sharedActions(){
        Preinstall.checkForMinecraftandForge();
        guiChoice();
        Updater.modpackUpdateTags();
        Preinstall.fileCheck();
    }

    public static void guiChoice(){
        int o = JOptionPane.showOptionDialog(new JFrame(), Strings.guiChoiceMessage, Strings.guiChoiceTitle, JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, Strings.guiOptionsArray, Strings.guiOptionsArray[0]);
        if (o == JOptionPane.YES_OPTION) {
            Common.bePretty = true;
            Common.getFont();
            GUI.launchGUI();
        }
        if (o == JOptionPane.NO_OPTION) {
            Common.bePretty = false;
            GUI.launchGUI();
        }
    }

    public static int getSelectedOption() {
        return selectedOption;
    }
    public static void setSelectedOption(int selectedOption) {
        Driver.selectedOption = selectedOption;
    }
}

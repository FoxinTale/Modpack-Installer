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

    /*
    * Code comment todo list for the whole installer. This will be removed before it is released:
    *
    * Update checking:
    * - Figure out how to check if an update is available for each modpack part.
    * - Check if part one is or is not installed.
    * - Check if an update is available for part one if it is, otherwise download and install.
    * - Check if part two is or is not installed.
    * - Check if an update is available for part two, if it is then download and install it.
    *
    * - The checking for each part can probably be accomplished by checking if one or more mods/files from each part exist.
    * - Part update checking can probably be accomplished via creating a file named "modpackInfo.json" storing the current version of part one and two.
    *     At launch the installer would check for the file, get the values and compare it to the versions pulled from the Github API.
    *
    *
    * Testing things:
    * - Test file extraction with the newly merged libraries.
    * - Test json file creation and writing.
    * - Ensure that when part one is finished the installer does not quit and fires back for part two.
    *    After part two, it then goes into its previous routine of doing install-y things.
    * - Do a full test runthrough to make sure it all works.
    *
    *
    * Thing(s) to fix:
    * - Fix the custom font not showing up.
    * - Change the old AWS S3 links over to whichever file host I choose.
    *
    *
    * Thing(s) to add:
    * - Installer "remembering" installation paths.
    *     This can probably utilize the previously created Json file and store the paths for downloads and desktop, as apparently this had issues.
    * - Add the presence footsteps resources to modpack part one.
    * - Use the bundled FileUtils stuff instead of the homebrew solution for operating system checking.
    *
    *
    * Minecraft launcher memory adjustment:
    * - Verify that changing the memory in the launcher still works and Microsoft didn't rework it over the past year.
    * - Remove all usage of Sigar due to it not working without being used externally.
    * - Replace it with a slider with 1 Gb memory increments, from 2 Gb up to 14 Gb.
    *
    *
    * Other:
    * - Change the license from WTFPL V2 to Apache 2, or...GPL V3.
    * - Maybe remove the checksums part as I don't really see much of a use for it anymore.
    * - Figure out what more resource packs that were used and double check the usage permissions.
    *    After that, create a new repository with a released version of the texture pack.
    */

static Boolean validOS = false;
    public static void main(String[] args) throws IOException {
        // Creating the custom output stream.
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
        //Install.checkForMinecraftandForge();
        guiChoice();
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

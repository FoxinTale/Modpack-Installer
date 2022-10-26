

public class Strings {
    // Purpose of putting all the strings here is to make it easier for translations to be made.
    // If you're doing a translation, the space in front of the word must be there. The text looks squished otherwise.
    // Ideally, this should be in a bundled language file, but I haven't figured out how to bundle assets within the jar file yet.
    // If someone knows how to, please either let me know or somehow send me documentation/ tutorials.


    // Text for the GUI elements, aside from the options.
    static String downloadText = "Download.";
    static String installerWindowTitle = "Modpack Installer";
    static String installerErrorsLabelText = "Errors: ";
    static String installerVersionText = "Version ";


    // Outputs directly to the text window, this is listing the progress of the installer.
    static String installerWelcome = " Welcome to the installer!";
    static String installerBugReport = " Please report any issues or bugs to Aubrey Jane #7563.";
    static String installerOptions = " Select an option from below to continue.\n";
    static String installerVerifyingFile = " Verifying integrity of file";
    static String installerVerificationPassed = " File verification passed!";
    static String installerDownloadComplete = " Download Complete!";
    static String installerVerificationRedownlaoding1 = " Verification failed. Redownloading.";
    static String installerVerificationRedownlaoding2 = " If this happens more than three times,";
    static String installerVerificationRedownlaoding3 = " tell me. It means I forgot to update things.";
    static String installerExtractNotice = " Extracting modpack. Expect system lag.";
    static String installerInstallNotice = " Installing the Modpack now.";
    static String installerModsVerification = " Verifying mods installed correctly.";
    static String installerBackupMods = " Backing up any Mods";
    static String installerBackupConfig = " Backing up any existing configs";
    static String installerInstalling = " Installing...";


    // Installer Options
    static String installerOptionOne = "Do it for me.";
    static String installerOptionTwo = "Just download the zip file.";
    static String installerErrorsDefault = " Nothing to report.";


    // Checking the existence of Vanilla Minecraft and Modded.
    static String noVanillaMessage = "Please run Vanilla Minecraft 1.7.10 at least once before continuing.";
    static String noVanillaTitle = "Vanilla not Found";
    static String noModsMessage = "Please install the latest version of Forge for 1.7.10 before continuing!";
    static String noModsTitle = "Forge not Found";


    // Unsupported Operating System.
    static String oldOSMessage = "Why are you still using this computer?";
    static String oldOSTitle = "Outdated OS";
    static String unknownOSMessage = "Your OS is not supported by this installer.";
    static String unknownOSTitle = "Unknown Operating System";

    static String serverNotReachableMessage = "It isn't up, please let me know, and I'll get on it as soon as I can.";
    static String serverNotReachableTitle = "Server Down";
    static String serverUpMessage = "The server is up, Yay!";
    static String serverUpTitle = "Server up.";
    static String installerStuffBackupMessage = "Your pre-existing mods and configs have been moved to a folder on the desktop named 'Minecraft Stuff'.";
    static String installerStuffBackupTitle = "Your stuff has bee backed up.";
    static String installerPingServerMessage = "Would you like to check if the server is up?";
    static String installerPingServerTitle = "Ping server?";


    // Finalizing the installation.
    static String installerThanksMessage = "Thanks for using the installer! Now, go have fun.";
    static String installerThanksTitle = "All Done!";
}

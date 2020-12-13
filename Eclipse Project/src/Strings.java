

public class Strings {
    // Purpose of putting all the strings here is to make it easier for translations to be made.
    // If you're doing a translation, the space in front of the word must be there. The text looks squished otherwise.
    // Ideally, this should be in a bundled language file, but I haven't figured out how to bundle assets within the jar file yet.
    // If someone knows how to, please either let me know or somehow send me documentation/ tutorials.


    // Text for the GUI elements, aside from the options.
    static String downloadText = "Download.";
    static String installerWindowTitle = "Modpack Installer by Aubrey";
    static String installerErrorsLabelText = "Errors: ";
    static String installerVersionText = "Version ";


    // Outputs directly to the text window, this is listing the progress of the installer.
    static String installerWelcome = " Welcome to the installer!";
    static String installerBugReport = " Please report any issues or bugs to Aubrey Jane #7563.";
    static String installerOptions = " Select an option from below to continue.\n";
    static String installerVerifyingFile = " Verifying integrity of file";
    static String installerVerificationPassed = " File verification passed!";
    static String installerDownloadingPartOne = " Downloading part one of the modpack.";
    static String installerFinishedPartOne = " Finished downloading part one.";
    static String installerDownloadingPartTwo = " Downloading part two of the modpack.";
    static String installerFinishedPartTwo = " Finished downloading part two.";
    static String installerVerificationFailed = " Verification Failed...Redownloading.";
    static String installerDownloadComplete = " Download Complete!";
    static String installerVerificationRedownlaoding1 = " Verification failed. Redownloading.";
    static String installerVerificationRedownlaoding2 = " If this happens more than three times,";
    static String installerVerificationRedownlaoding3 = " tell me. It means I forgot to update things.";
    static String installerExtractNotice = " Extracting modpack. Expect system lag.";
    static String installerInstallNotice = " Installing the Modpack now.";
    static String installerBackupProfile = " Launcher Profiles backed up.";
    static String installerRestoreProfile = " Launcher Settings restored.";
    static String installerModsVerification = " Verifying mods installed correctly.";
    static String installerBackupMods = " Backing up any Mods";
    static String installerBackupConfig = " Backing up any existing configs";
    static String installerInstalling = " Installing...";


    // Installer Options
    static String installerOptionOne = "Do it for me.";
    static String installerOptionTwo = "Just download the zip file.";
    static String installerOptionThree = "Other Features.";
    static String installerOptionFour = "Get Resource Pack.";
    static String installerErrorsDefault = " Nothing to report.";


    // Text for an update available for the installer.
    static String installerUpdateTitle = "Installer Update Available";
    static String installerUpdateMessage = "There's an update available for the installer. Would you like to download it?";
    static String installerUpToDate = " You have the latest version of the installer.";


    // Modpack update related things.
    static String modpackPartOneGood = " Part one is up to date. Yay!";
    static String modpackPartTwoGood = " Part two is up to date. Yay!";
    static String modpackPartOneOutdated = "Part one is out of date. Downloading the latest version.";
    static String modpackPartTwoOutdated = "Part two is out of date. Downloading the latest version.";
    static String modpackPartOutdated = " Part of the pack is outdated.";


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


    // Installer options messages and titles.
    static String installerExtractResourceMessage = "Would you like to extract the resource pack in order to improve load times on Minecraft? ";
    static String installerExtractResourceTitle = "Extract resource pack?";
    static String installerDownloadLocationMessage = "The download has finished. You can find the file in your downloads folder. Have fun!";
    static String installerDownloadLocationTitle = "Download finished.";
    static String serverNotReachableMessage = "It isn't up, please let me know, and I'll get on it as soon as I can.";
    static String serverNotReachableTitle = "Server Down";
    static String serverUpMessage = "The server is up, Yay!";
    static String serverUpTitle = "Server up.";
    static String installerMemoryAdjustMessage = "Would you like the installer to adjust your Java arguments in the launcher? This will also allow you to configure the amount of ram you allocate to Minecraft.";
    static String installerMemoryAdjustTitle = "Adjust or change Java arguments";
    static String installerStuffBackupMessage = "Your pre-existing mods and configs have been moved to a folder on the desktop named 'Minecraft Stuff'.";
    static String installerStuffBackupTitle = "Your stuff has bee backed up.";
    static String installerPingServerMessage = "Would you like to check if the server is up?";
    static String installerPingServerTitle = "Ping server?";
    static String installerResourceMessage = "While you're at it, would you like to download and install the optional resource pack? ";
    static String installerResourceTitle = "Resource Pack Install";
    static String installerOptionalModsMessage = "Final question. Would you like to install controller support and/or additional ambience mods?";
    static String installerOptionalModsTitle = "Install optional mods?";
    static String installerAgainMessage = "Would you like to use another option? Selecting no exits the program.";
    static String installerAgainTitle = "Use another option?";
    static String guiChoiceMessage = "Would you like a colourful, pretty version or would you like to keep it plain?";
    static String guiChoiceTitle = "User interface theme choice";
    static Object[] guiOptionsArray = {"Make it pretty", "Keep it plain"};


    // Options Text
    static String optionsTitle = "Options.";
    static String optionsOne = "Extract.";
    static String optionsTwo = "Set Memory.";
    static String optionsThree = "Ping Server.";
    static String optionsFour = "Restore Settings.";
    static String optionsFive = "Optional Mods.";


    //  Resource pack options GUI Text.
    static String assetCreditsLabel = "Asset Credits";
    static String resourcePackTitle = "Resource Pack Options";
    static String resourceDownloadSize = "Download Size: ";
    static String resourceNoneSelected = "None selected ";
    static String resourceContinue = "Continue";


    // Resource Pack Options
    static String resourceOptionOne = "One";
    static String resourceOptionTwo = "Two";
    static String resourceOptionThree = "Three";
    static String resourceOptionFour = "Four";
    static String resourceOptionFive = "Five";
    static String resourceOptionSix = "Six";


    // Text for the descriptions of the resource packs.
    static String resourcePackOneDesc = " This pack is textures and \n" + " sounds. \n" + " As a note, this is a high \n"
            + " resolution texture pack.  \n" + " At least 128x128 quality. \n" + " This does not include any \n"
            + " music replacements, \n" + " sounds or ambiance music. ";

    static String resourcePackTwoDesc = " This pack is game music \n" + " and sounds. No textures. \n"
            + " No ambiance music. Only \n" + " replacing the games' music\n" + "  and realistic sounds.";

    static String resourcePackThreeDesc = " This pack is game music \n" + " sounds and textures. \n" + " No ambiance music. Only \n"
            + " replacing the games' music, \n" + " realistic sounds \n" + " and textures.";

    static String resourcePackFourDesc = " This pack is ambiance \n" + " music, music disks and \n" + " sounds. No textures.";

    static String resourcePackFiveDesc = " This one has it all. \n" + " High resolution textures, \n"
            + " Ambiance Music, realistic \n" + " sounds, and custom music \n"
            + " disks. Note: This will \n" + " also extract the pack, due \n"
            + " its size, to lessen load \n" + " times.";

    static String resourcePackSixDesc = " This one is like the \n" + " previous one, except with \n"
            + " a crazy amount of ambiance \n" + " added. This one is so large, \n"
            + " I am not entrusting the \n" + " installer to download it.\n\n"
            + " If you choose this, you \n" + " will be directed to a link \n"
            + " to download from within \n" + " your browser. ";


    //The big resource pack things
    static String resourceBigPackSize = "Huge";
    static String resourceBigTitle = "Large pack option";
    static String resourceBigLinkLabel = "Go here for more information";
    static String resourceBigPackInfo = " Seriously, this is a massive pack \n" + " and is not required. For further \n"
            + " information, go to the below link. \n" + " It will direct you to my site \n"
            + " which contains more information.\n" + " The link may take time to load.\n" + "\n"
            + " Closing this will exit the program.";


    // Optional mods labels.
    static String optionalModsControllerLabel = "Controller Support.";
    static String optionalModsAmbianceLabel = "Custom Ambiance Music.";
    static String optionalModsDynamicLabel = "Dynamic Surroundings.";
    static String optionalModsFootstepsLabel = "Presence Footsteps.";
    static String optionalModsFiltersLabel = "Sound Filters.";
    static String optionalModsFishingLabel = "Auto Fishing";
    static String optionalModsNoteblockLabel = "Noteblock Display";
    static String optionalModsGammabrightLabel = "Gammabright";


    //Optional mods info text
    static String optionalModsInfoDefault = " These are optional. \n" +
            " You'll be able to connect\n" + " without problems if you \n"
            + " select none of these.";

    static String optionalModsControllerInfo = " Adds support for Xbox \n"
            + " controllers. I suppose other \n" + " controllers could be used too.";

    static String optionalModsAmbianceInfo = " Environmentally aware music. \n"
            + " Required for the non-texture \n" + " resource packs.";

    static String optionalModsFootstepInfo = " Dynamic sounds for every \n"
            + " block the player walks on. \n" + " Every block has sounds to \n"
            + " better match what they're \n" + " made of.";

    static String optionalModsFiltersInfo = " Adds reverb to sounds in \n"
            + " caves and mutes sounds while " + "\n underwater. Mutes sounds \n"
            + " from behind a wall too.";

    static String optionalModsGammabrightInfo = " Adds an option to  \n " + " increase the in game \n"
            + " brightness eliminating the \n" + " need for torches";

    static String optionalModsDynamicInfo = " Realistic storms, Auroras, \n" + " Specific biome sounds, and more!";
    static String optionalModsFishingInfo = " The name describes all. \n" + " It pulls in fishing rods \n" + " as soon as a bite happens";
    static String optionalModsNoteblockInfo = " Shows a graphical config \n" + " menu for noteblocks.";


    // Optional mods GUI elements.
    static String optionalModsInstall = "Install!";
    static String optionalModsCancel = "Nah, I'm good";
    static String optionalModsTitle = "Optional mods installation";


    // Finalizing the installation.
    static String installerThanksMessage = "Thanks for using the installer! Now, go have fun.";
    static String installerThanksTitle = "All Done!";
}

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class InstallerSanityChecks {

    static String q = File.separator;
	/*
	To test:
		Mod deletion, if an existing 1.7.10 install is found.
		Checksum verification.
		Memory getting and setting.
		Memory getting, setting and then re-writing the installer json.
		Profile restoration.
		Test internet loss while downloading.
		Test removing different files
		Test if a singular and multiple sigar files are missing.
		test the various Json files..What happens if they are missing.
		Test the updater, for updates.
		Maybe rework the update to have a json file within it, so it knows what to remove specifically.
	*/

    public static void check(int option) throws IOException {
        switch (option) {
            case 0:
                optionsGUI.otherOptionsGUI();
                break;
            case 1:
                modOptions.modOptionsGui();
                break;
            case 2:
                resourcePacks.bigPack();
                break;
            case 3:
                resourceCheck.sigarCheck();
                break;
            case 4:
                Json.readProfileData(6144);
                break;
            case 5:
                installOptions.verifyInstall();
                break;
            case 6:
                System.out.println(" Running checksum verification.");
                String zipName = "Modpack.zip";
                //File zipFile = new File(q + Driver.getDownloadsLocation() + q + zipName);
                //Checksums.checksum(zipFile, zipName);
                break;
            case 7:
                Install.install();
                break;
            case 8:
                GUI.launchGUI();
                GUI.errors.setText("Sylveon");
                break;
            case 9:
                String dir = System.getProperty("user.dir") + q + "Modpack-Installer_lib";
                ArrayList<String> list = new ArrayList<>();
                String jsonDir = dir + q + "json" + q;
                File libData = new File(jsonDir + "libraries.json");
                Json.libRead(libData, list);
            case 10:
                Json.readLists();
            default:
                break;
        }
    }
}

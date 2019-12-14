import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Updater {
	static ArrayList<String> removal = Json.getToRemove();
	static File versionFile = new File(Driver.getMinecraftInstallLocation() + File.separator + "Modpack_Version.txt");
	static String installedVersion = "";
	static String currentVersion = "";
	static String baseLink = "https://aubreys-storage.s3.us-east-2.amazonaws.com/1.7.10/Updates/";
	static String q = File.separator;

	public static void updater() {
		currentVersion = Json.getCurrentVersion();

		if (!versionFile.exists()) {
			versionFile();
		}
		if (versionFile.exists()) {
			versionRead();
		}
		if (versionCompare()) {
			System.out.println("Yay, you are running the latest version. No need to continue.");
		}
		if (!versionCompare()) {
			System.out.println("Seems as if a new version has been released.");
			System.out.println("Downloading update file");
			try {
				Downloader.Download(new URL(baseLink + currentVersion + ".zip"), currentVersion + ".zip", 1);
			} catch (MalformedURLException e) {
				GUI.errors.setText("Bastiodon");
			}
		}
	}

	public static void versionFile() {
		try {
			PrintWriter writer = new PrintWriter(versionFile);
			writer.println(currentVersion);
			writer.println("Please do not delete this file. this is how the installer knows if there is an update.");
			writer.close();
			versionFile.setReadOnly();
		} catch (IOException e) {
			// Do nothing.
		}
	}

	public static void versionRead() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(versionFile));
			String line;
			ArrayList<String> versionsContent = new ArrayList<>();
			while ((line = reader.readLine()) != null) {
				versionsContent.add(line);
			}
			reader.close();
			installedVersion = versionsContent.get(0);
			versionCompare();
		} catch (IOException e) {
			GUI.errors.setText("Clefable");
		}
	}

	public static Boolean versionCompare() {
		Boolean versionsMatch = false;
		if (currentVersion.equals(installedVersion)) {
			versionsMatch = true;
		}
		if (!currentVersion.equals(installedVersion)) {
			versionsMatch = false;
		}
		return versionsMatch;
	}

	public static void removeStuff() {
		File modsDirectory = new File(Driver.getMinecraftInstall() + q + "mods");
		if (removal.isEmpty() || removal.size() == 0) {
			// Do nothing. Literally.
		}
		for (int i = removal.size(); i > 0; i--) {
			File begone;
			begone = new File(modsDirectory.toString() + q + removal.get(i - 1));
			begone.delete();
		}
	}

	public static void installUpdate() {
		removeStuff();
		File modsDirectory = new File(Driver.getMinecraftInstall() + q + "mods");
		File configDirectory = new File(Driver.getMinecraftInstall() + q + "config");

		File updateMods = new File(Driver.getDownloadsLocation() + q + currentVersion + q + "mods");
		File updateConfig = new File(Driver.getDownloadsLocation() + q + currentVersion + q + "config");

		Install.copyFiles(updateMods, modsDirectory);
		Install.copyFiles(updateConfig, configDirectory);

		String updateMessage = "Update installed!";
		JOptionPane.showMessageDialog(new JFrame(), updateMessage, "Update done.", JOptionPane.INFORMATION_MESSAGE);
		Install.end();
	}
}

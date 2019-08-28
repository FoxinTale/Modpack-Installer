package packUpdate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class OOF {
	static ArrayList<String> versions = new ArrayList<>();
	static ArrayList<String> version;
	static String baseLink = "https://aubreys-storage.s3.us-east-2.amazonaws.com/1.7.10/Updates";
	static File versionFile = new File("C:\\Users\\Alyan\\AppData\\Roaming\\.minecraft\\Modpack_Version.txt");
	static String installedVersion = "";
	static String currentVersion = "";

	public static void main(String[] args) {
	
	}

	public static void updater() {
		
		websiteReader.siteReader("https://sites.google.com/view/aubreys-modpack-info/home/latest-version", false, 2,
				versions);
		String versionPreTrim = (Arrays.toString(versions.toArray()).replace('[', ' ').replace(']', ' '));
		currentVersion = versionPreTrim.trim();

		if (!versionFile.exists()) {
			versionFile();
		}
		if (versionFile.exists()) {
			versionRead();
		}
		if(versionCompare()) {
			System.out.println("Yay, you are running the latest version. No need to continue.");
		}
		if(!versionCompare()) {
			System.out.println("Seems as if a new version has been released.");
			System.out.println("Downloading update file");
			// Downloader.downloader(baseLink + currentVersion + ".zip");
		}
	}

	public static void versionFile() {
		try {
			PrintWriter writer = new PrintWriter(versionFile);
			String versionInfo = Arrays.toString(versions.toArray()).replace('[', ' ').replace(']', ' ');
			writer.println(versionInfo.trim());
			writer.println("Please do not delete this file. this is how the installer knows if there is an update.");
			writer.close();
			versionFile.setReadOnly();
		} catch (IOException e) {
			e.printStackTrace();
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

	public static void installUpdate() {
		// This needs a lot of work, but I've no idea what exactly to do.
		// File updateFolder = new File(Driver.getDownloadsLocation() +
		// UpdateGUI.getFolderLoc());
	}
}
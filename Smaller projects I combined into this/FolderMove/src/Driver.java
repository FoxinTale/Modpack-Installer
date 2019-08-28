import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FileUtils;

public class Driver {
	public static void main(String[] args) {
		System.out.println("Installing the Modpack now.");

		String username = System.getProperty("user.name");
		// String downloadsLocation = Driver.downloadsLocation.getAbsolutePath();
		// String desktopLocation = desktopPath.getAbsolutePath();
		String minecraftPath = "C:\\Users\\" + username + "\\AppData\\Roaming\\.minecraft";

		File minecraftMods = new File(minecraftPath + "\\mods");
		File minecraftConfig = new File(minecraftPath + "\\config");
		File minecraftFlans = new File(minecraftPath + "\\Flan");

		File backups = new File("C:\\Users\\" + username + "\\Desktop\\Minecraft Stuff");

		String backupsLocation = backups.getAbsolutePath();
		File backupMods = new File(backupsLocation + "\\Mods");
		File backupConfig = new File(backupsLocation + "\\Config");
		File backupFlans = new File(backupsLocation + File.separator + "Flans");

		File modpackLocation = new File("C:\\Users\\" + username + "\\Downloads\\Modpack");

		File modpackMods = new File(modpackLocation.getAbsolutePath() + "\\mods");
		File modpackConfig = new File(modpackLocation.getAbsolutePath() + "\\config");
		File modpackFlans = new File(modpackLocation.getAbsolutePath() + "\\Flan");

		if (backups.exists()) {
			backups.delete();
			// Put a prompt alerting the user that a folder of the same name exists,
			// Then ask to overwrite, or put it elsewhere.
			// If overwrite is chosen, delete the folder and carry on.
			// If not, allow the user to set where the alternate backup goes.
			// Also have a boolean that changes the end message if this is chosen.

		}
		folderCreate(backups);
		folderCreate(backupMods);
		folderCreate(backupConfig);
		folderCreate(backupFlans);
		// folderCreate(modpackLocation);

		if (minecraftMods.exists()) {
			if (backupMods.exists()) {
				System.out.println("Backing up any mods.");
				try {
					Files.move(minecraftMods.toPath(), backupMods.toPath(), StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException a) {
					// Do nothing
				}
			}
		}

		if (minecraftConfig.exists()) {
			if (backupConfig.exists()) {
				System.out.println("Backing up any configs.");
				try {
					Files.move(minecraftConfig.toPath(), backupConfig.toPath(), StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException b) {
					// Do nothing, again.
				}
			}
		}
		
		if (minecraftFlans.exists()) {
			if (backupFlans.exists()) {
				System.out.println("Backing up any configs.");
				try {
					Files.move(minecraftFlans.toPath(), backupFlans.toPath(), StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException b) {
					// Do nothing, again.
				}
			}
		}

		// folderCreate(modpackMods);

		// folderCreate(minecraftConfig);

		if (minecraftFlans.exists()) {
			minecraftFlans.delete();
		}

		folderCreate(minecraftFlans);
		folderCreate(minecraftConfig);
		folderCreate(minecraftMods);
		try {
			System.out.println("\nMoving Stuff");
			if (modpackMods.exists()) {
				if (minecraftMods.exists()) {
					// FileUtils.copyDirectory(modpackMods, minecraftMods);
					Files.move(modpackMods.toPath(), minecraftMods.toPath(), StandardCopyOption.REPLACE_EXISTING);
					System.out.println("Mods Moved");
				}
			}

			if (modpackConfig.exists() && minecraftConfig.exists()) {
				Files.move(modpackConfig.toPath(), minecraftConfig.toPath(), StandardCopyOption.REPLACE_EXISTING);
				System.out.println("Config Moved");
			}
		} catch (IOException i) {
			System.out.println("Charizard");
		}

		try {
		if (modpackFlans.exists() && minecraftFlans.exists()) {
			Files.move(modpackFlans.toPath(), minecraftFlans.toPath(), StandardCopyOption.REPLACE_EXISTING);
			System.out.println("Flans Moved");
		}
		}
		catch(IOException i) {
			System.out.println("Motherfucker");
		}


		// Driver.folderCreate(modpackFlans);

		// Moving the information downloaded in the modpack directory from Github. The
		// final folder structure is yet to be determined.
		// moveStuff(modpackMods, minecraftMods);
		// moveStuff(modpackConfig, minecraftConfig);
		// moveStuff(modpackFlans, minecraftFlans);

	}



	public static void folderCreate(File folder) {
		if (!folder.exists()) {
			folder.mkdir();
		}
	}
}

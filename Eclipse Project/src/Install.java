import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;


import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Install {

	public static void install() {

		System.out.println("Installing the Modpack now.");

		String minecraftPath = Driver.getMinecraftInstall();

		File minecraftMods = new File(minecraftPath + File.separator + "mods");
		File minecraftConfig = new File(minecraftPath + File.separator + "config");
		File minecraftFlans = new File(minecraftPath + File.separator + "Flan");

		File backups = new File(Driver.getDesktopLocation() + File.separator + "Minecraft Stuff");
		String backupsLocation = backups.getAbsolutePath();
		File backupMods = new File(backupsLocation + File.separator + "Mods");
		File backupConfig = new File(backupsLocation + File.separator + "Config");
		File backupFlans = new File(backupsLocation + File.separator + "Flans");

		File modpackLocation = new File(Driver.getDownloadsLocation() + File.separator + "Modpack");

		File modpackMods = new File(modpackLocation + File.separator + "mods");
		File modpackConfig = new File(modpackLocation + File.separator + "config");
		File modpackFlans = new File(modpackLocation + File.separator + "Flan");

		if (backups.exists()) {
			backups.delete();
			// Put a prompt alerting the user that a folder of the same name exists,
			// Then ask to overwrite, or put it elsewhere.
			// If overwrite is chosen, delete the folder and carry on.
			// If not, allow the user to set where the alternate backup goes.
			// Also have a boolean that changes the end message if this is chosen.

		}
		Driver.folderCreate(backups);
		Driver.folderCreate(backupMods);
		Driver.folderCreate(backupConfig);
		Driver.folderCreate(backupFlans);
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

		Driver.folderCreate(minecraftFlans);
		Driver.folderCreate(minecraftConfig);
		Driver.folderCreate(minecraftMods);
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
		} catch (IOException i) {
			System.out.println("Motherfucker");
		}

		// Driver.folderCreate(modpackFlans);

		// Moving the information downloaded in the modpack directory from Github. The
		// final folder structure is yet to be determined.
		// moveStuff(modpackMods, minecraftMods);
		// moveStuff(modpackConfig, minecraftConfig);
		// moveStuff(modpackFlans, minecraftFlans);
		installDone();
	}

	public static void installDone() {
		String message = "Your pre-existing mods and configs have been moved to a folder on the desktop named 'Minecraft Stuff'.";
		JOptionPane.showMessageDialog(new JFrame(), message, "Info", JOptionPane.INFORMATION_MESSAGE);
		String completeMessage = "The modpack has been installed! Please read the read me file in the modpack folder in your downloads for more information. Have fun!";
		JOptionPane.showMessageDialog(new JFrame(), completeMessage, "Install Complete!",
				JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}
}

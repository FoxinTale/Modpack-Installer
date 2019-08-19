import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.hyperic.sigar.SigarException;

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

		moveFiles(minecraftMods, backupMods, "Backing up any Mods");
		moveFiles(minecraftConfig, backupConfig, "Backing up any configs.");
		moveFiles(minecraftFlans, backupFlans, "Backing up any Flans related items");

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
			System.out.println("Giritina");

		}
		String t = "Would you like the installer to adjust your Java aruments in the launcher? This will also allow you to configure the amount of ram you allocate to Minecraft.";
		int o = JOptionPane.showConfirmDialog(new JFrame(), t, "Server Test", JOptionPane.YES_NO_OPTION);
		if (o == JOptionPane.YES_OPTION) {
			try {
				installOptions.sliderGUI();
			} catch (SigarException e) {
				e.printStackTrace();
			}
		}
		if (o == JOptionPane.NO_OPTION) {
			installFinalize();
		}

	}

	public static void installFinalize() {
		String message = "Your pre-existing mods and configs have been moved to a folder on the desktop named 'Minecraft Stuff'.";
		JOptionPane.showMessageDialog(new JFrame(), message, "Info", JOptionPane.INFORMATION_MESSAGE);
		
		String t = "Would you like the installer to check if the server is up?";
		int o = JOptionPane.showConfirmDialog(new JFrame(), t, "Server Test", JOptionPane.YES_NO_OPTION);
		if (o == JOptionPane.YES_OPTION) {
			serverPing();
		}
		if (o == JOptionPane.NO_OPTION) {
			allDone();
		}
	}

	public static void serverPing() {
		try {
			Socket server = new Socket();
			server.connect(new InetSocketAddress("IP ADDRESS", 25525), 60000);
			// Not putting my IP address out there for all to see.
			// If ran as it, it will throw the UnknownHostException below, but if changed to
			// a proper IP that error should never be thrown.
			String notification = "The server is up! Get on it and have fun!";
			JOptionPane.showMessageDialog(new JFrame(), notification, "Server Up!", JOptionPane.INFORMATION_MESSAGE);
			server.close();
			allDone();

		} catch (UnknownHostException h) {
			String notification = "It seems that either the computer isn't turned on, or my internet is down. Check the minecraft channel for more info.";
			JOptionPane.showMessageDialog(new JFrame(), notification, "No connection", JOptionPane.ERROR_MESSAGE);
		} catch (IOException i) {
			String notification = "It isnt up, please let me know, and I'll get on it as soon as I can.";
			JOptionPane.showMessageDialog(new JFrame(), notification, "Server Down", JOptionPane.ERROR_MESSAGE);
			// This is an error that must be caught, as the server sometimes crashes without
			// my knowing.
			allDone();
		}
	}

	public static void allDone() {
		String messageTwo = "Thanks for using this installer!";
		JOptionPane.showMessageDialog(new JFrame(), messageTwo, "Finished!", JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}

	public static void moveFiles(File dirOne, File dirTwo, String s) {
		if (dirOne.exists()) {
			if (dirTwo.exists()) {
				System.out.println(s);
				try {
					Files.move(dirOne.toPath(), dirTwo.toPath(), StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException a) {
					// Do nothing. Java complained if I didn't put this here.
				}
			}
		}
	}
}

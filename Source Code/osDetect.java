import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class osDetect {

	static String username = System.getProperty("user.name");

	public static void isWindows() {
		File downloads = new File("C:\\Users\\" + username + "\\Downloads");
		File minecraftDefaultPath = new File("C:\\Users\\" + username + "\\AppData\\Roaming\\.minecraft");
		File desktop = new File("C:\\Users\\" + username + "\\Desktop");
		if (downloads.exists()) {
			Driver.setDownloadsLocation(downloads.getAbsolutePath());
		}
		if (!downloads.exists()) {

			String message = "Unable to locate downloads folder.";
			JOptionPane.showMessageDialog(new JFrame(), message, "Downloads not Found.", JOptionPane.ERROR_MESSAGE);
		}
		if (minecraftDefaultPath.exists()) {
			Driver.setMinecraftInstall(minecraftDefaultPath.getAbsolutePath());
		}
		if (!minecraftDefaultPath.exists()) {
			String message = "Unable to locate Minecraft install directory. Click OK to manually locate it";
			JOptionPane.showMessageDialog(new JFrame(), message, "Minecraft Not Found.", JOptionPane.ERROR_MESSAGE);
			JFileChooser findMinecraft = new JFileChooser("C:\\");
			findMinecraft.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int r = findMinecraft.showSaveDialog(null);
			if (r == JFileChooser.APPROVE_OPTION) {

				Driver.setMinecraftInstall(Driver.getMinecraftInstallLocation().getAbsolutePath());
			}
			// if the user cancelled the operation

			else {
				String messageTwo = "Cannot continue without Minecraft install!";
				JOptionPane.showMessageDialog(new JFrame(), messageTwo, "Install Search Cancelled",
						JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		}

		if (desktop.exists()) {
			Driver.setDesktopLocation(desktop.getAbsolutePath());
		}

		if (!desktop.exists()) {
			// Oh, you're fucked if you see this.
			String message = "You...seem to not have a desktop. Please fix that before continuing";
			JOptionPane.showMessageDialog(new JFrame(), message, "No Desktop Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}

	}

	public static void isLinux() {
		// Hello Linux user! You have made a good choice.
		// Yes, I know I need to also fix these.
		File downloads = new File("/home/" + username + "/Downloads/");
		File minecraftDefaultPath = new File("/home/" + username + "/.minecraft/");
		File desktop = new File("/home/" + username + "/Desktop/");
		if (downloads.exists()) {
			Driver.setMinecraftInstall(minecraftDefaultPath.getAbsolutePath());
		}
		if (!downloads.exists()) {
			// Prompt the user towards their downloads folder
			// A GUI prompt would appear, allowing the user to navigate to their downloads.
		}
		if (minecraftDefaultPath.exists()) {
			Driver.setMinecraftInstall(minecraftDefaultPath.getAbsolutePath());
		}
		if (!minecraftDefaultPath.exists()) {
			String message = "Unable to locate Minecraft install directory. Click OK to manually locate it";
			JOptionPane.showMessageDialog(new JFrame(), message, "Minecraft Not Found.", JOptionPane.ERROR_MESSAGE);
			JFileChooser findMinecraft = new JFileChooser("/");
			findMinecraft.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int r = findMinecraft.showSaveDialog(null);
			if (r == JFileChooser.APPROVE_OPTION) {

			}
			// if the user cancelled the operation
			else {

			}
		}

		if (desktop.exists()) {
			Driver.setDesktopLocation(desktop.getAbsolutePath());
		}

		if (!desktop.exists()) {
			String message = "You...seem to not have a desktop. Please fix that before continuing";
			JOptionPane.showMessageDialog(new JFrame(), message, "No Desktop Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void isMac() {
		// Yes, I know the locations are incorrect. I do not have access to, or own a Mac, so I am unable to do this.
		File downloads = new File("/Users/" + username + "/Downloads/");
		File minecraftDefaultPath = new File("/Users/" + username + "/Library/Application Support/minecraft"); // is.
		File desktop = new File("/Users/" + username + "/Desktop/");
		if (downloads.exists()) {
			Driver.setMinecraftInstall(minecraftDefaultPath.getAbsolutePath());
		}
		if (!downloads.exists()) {
			// Prompt the user towards their downloads folder
			// A GUI prompt would appear, allowing the user to navigate to their downloads.
		}
		if (minecraftDefaultPath.exists()) {
			Driver.setMinecraftInstall(minecraftDefaultPath.getAbsolutePath());
		}
		if (!minecraftDefaultPath.exists()) {
			String message = "Unable to locate Minecraft install directory. Click OK to manually locate it";
			JOptionPane.showMessageDialog(new JFrame(), message, "Minecraft Not Found.", JOptionPane.ERROR_MESSAGE);
			JFileChooser findMinecraft = new JFileChooser("/");
			findMinecraft.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int r = findMinecraft.showSaveDialog(null);
			if (r == JFileChooser.APPROVE_OPTION) {

			} else {
			}
		}
		if (desktop.exists()) {
			Driver.setDesktopLocation(desktop.getAbsolutePath());
		}

		if (!desktop.exists()) {
			// You thought you were fucked with Windows? You might as well bend over with
			// this one.
			String message = "You...seem to not have a desktop. Please fix that before continuing";
			JOptionPane.showMessageDialog(new JFrame(), message, "No Desktop Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}

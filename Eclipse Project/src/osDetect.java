import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class osDetect {

	static String username = System.getProperty("user.name");
	static File loc;

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

			Driver.setDownloadsLocation(
					findFolder(downloads.getPath(), "Where is the pack going to go?", "Pack home search cancelled"));
		}
		if (minecraftDefaultPath.exists()) {
			Driver.setMinecraftInstall(minecraftDefaultPath.getAbsolutePath());
			Driver.setMinecraftInstallLocation(minecraftDefaultPath);
		}
		if (!minecraftDefaultPath.exists()) {
			String message = "Unable to locate Minecraft install directory. Click OK to manually locate it";
			JOptionPane.showMessageDialog(new JFrame(), message, "Minecraft Not Found.", JOptionPane.ERROR_MESSAGE);
			Driver.setMinecraftInstall(findFolder(minecraftDefaultPath.getPath(),
					"Cannot continue without Minecraft install!", "Install Search Cancelled"));
			Driver.setMinecraftInstallLocation(loc);
		}

		if (desktop.exists()) {
			Driver.setDesktopLocation(desktop.getAbsolutePath());
		}

		if (!desktop.exists()) {
			String message = "For some reason, your desktop could not be found. Click ok to point this to it.";
			JOptionPane.showMessageDialog(new JFrame(), message, "Desktop 404", JOptionPane.ERROR_MESSAGE);
			Driver.setDesktopLocation(findFolder(desktop.getPath(), "This is needed for the installation...",
					"Desktop Search Cancelled"));
		}

	}

	public static void isLinux() {
		// Hello Linux user! You have made a good choice.
		File downloads = new File("/home/" + username + "/Downloads/");
		File minecraftDefaultPath = new File("/home/" + username + "/.minecraft/");
		File desktop = new File("/home/" + username + "/Desktop");
		if (downloads.exists()) {
			Driver.setDownloadsLocation(downloads.getAbsolutePath());
		}
		if (!downloads.exists()) {
			String message = "Unable to locate downloads folder.";
			JOptionPane.showMessageDialog(new JFrame(), message, "Downloads not Found.", JOptionPane.ERROR_MESSAGE);
			Driver.setDownloadsLocation(
					findFolder(downloads.getPath(), "Where is the pack going to go?", "Pack home search cancelled"));
		}
		if (minecraftDefaultPath.exists()) {
			Driver.setMinecraftInstall(minecraftDefaultPath.getAbsolutePath());
			Driver.setMinecraftInstallLocation(minecraftDefaultPath);
		}
		if (!minecraftDefaultPath.exists()) {
			String message = "Unable to locate Minecraft install directory. Click OK to manually locate it";
			JOptionPane.showMessageDialog(new JFrame(), message, "Minecraft Not Found.", JOptionPane.ERROR_MESSAGE);
			Driver.setMinecraftInstall(findFolder(minecraftDefaultPath.getPath(),
					"Cannot continue without Minecraft install!", "Install Search Cancelled"));
			Driver.setMinecraftInstallLocation(loc);
		}

		if (desktop.exists()) {
			Driver.setDesktopLocation(desktop.getAbsolutePath());
		}

		if (!desktop.exists()) {
			String message = "For some reason, your desktop could not be found. Click ok to point this to it.";
			JOptionPane.showMessageDialog(new JFrame(), message, "Desktop 404", JOptionPane.ERROR_MESSAGE);
			Driver.setDesktopLocation(findFolder(desktop.getPath(), "This is needed for the installation...",
					"Desktop Search Cancelled"));
		}
	}

	public static void isMac() {
		// The locations may be incorrect. I do not have access to, or own a Mac, so I
		// am unable to do this properly.
		File downloads = new File("/Users/" + username + "/Downloads/");
		File minecraftDefaultPath = new File("/Users/" + username + "/Library/Application Support/minecraft");
		File desktop = new File("/Users/" + username + "/Desktop/");
		if (downloads.exists()) {
			Driver.setDownloadsLocation(downloads.getAbsolutePath());
		}
		if (!downloads.exists()) {
			String message = "Unable to locate downloads folder.";
			JOptionPane.showMessageDialog(new JFrame(), message, "Downloads not Found.", JOptionPane.ERROR_MESSAGE);
			Driver.setDownloadsLocation(
					findFolder(downloads.getPath(), "Where is the pack going to go?", "Pack home search cancelled"));
		}
		if (minecraftDefaultPath.exists()) {
			Driver.setMinecraftInstall(minecraftDefaultPath.getAbsolutePath());
			Driver.setMinecraftInstallLocation(minecraftDefaultPath);
		}
		if (!minecraftDefaultPath.exists()) {
			String message = "Unable to locate Minecraft install directory. Click OK to manually locate it";
			JOptionPane.showMessageDialog(new JFrame(), message, "Minecraft Not Found.", JOptionPane.ERROR_MESSAGE);
			Driver.setMinecraftInstall(findFolder(minecraftDefaultPath.getPath(),
					"Cannot continue without Minecraft install!", "Install Search Cancelled"));
			Driver.setMinecraftInstallLocation(loc);

		}
		if (desktop.exists()) {
			Driver.setDesktopLocation(desktop.getAbsolutePath());
		}

		if (!desktop.exists()) {
			String message = "For some reason, your desktop could not be found. Click ok to point this to it.";
			JOptionPane.showMessageDialog(new JFrame(), message, "Desktop 404", JOptionPane.ERROR_MESSAGE);
			Driver.setDesktopLocation(findFolder(desktop.getPath(), "This is needed for the installation...",
					"Desktop Search Cancelled"));
		}
	}

	@SuppressWarnings("unused")
	public static String findFolder(String folder, String message, String title) {
		int count;
		String home = System.getProperty("user.home");
		JFileChooser whereFolder = new JFileChooser(home);
		whereFolder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int op = whereFolder.showOpenDialog(null);
		if (op == JFileChooser.APPROVE_OPTION) {
			loc = whereFolder.getSelectedFile();
			folder = whereFolder.getSelectedFile().getPath();
		} else {
			JOptionPane.showMessageDialog(new JFrame(), message, title, JOptionPane.INFORMATION_MESSAGE);
			findFolder(folder, message, title);
			count = +1;
		}
		return folder;
	}
}

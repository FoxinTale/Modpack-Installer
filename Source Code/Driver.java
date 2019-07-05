
import java.io.File;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Driver {
	static String zipFile = "Modpack.zip";
	static int installProgress = 0;
	static int selectedOption = 0;
	/*
	 * This is licensed under the WTFPL 2.0.
	 * 
	 * Basically, you are permitted to do whatever the hell you want with this code.
	 * You're welcome to improve upon it. If you do, please let me know, and I'll
	 * implement your fix / improvement, and give you credit for it. I know my code
	 * isn't the best, I'm only a beginner.
	 * 
	 * Despite the GUI saying "Copyright" That's more my way of saying that I made it.
	 * And for the try/catch, it outputs a Pokemon name for a bit of humor. If someone tells me it said a Pokemon,
	 * I can look at where it is supposed to be outputting that, and understand what's going wrong.
	 */
	private static File minecraftInstallLocation = null;
	private static String minecraftInstall = null;
	private static String downloadsLocation = null;
	private static String desktopLocation = null;
	private static String minecraftDefaultInstall = null;

	public static File getMinecraftInstallLocation() {
		return minecraftInstallLocation;
	}

	public static void setMinecraftInstallLocation(File minecraftInstallLocation) {
		Driver.minecraftInstallLocation = minecraftInstallLocation;
	}

	public static String getMinecraftInstall() {
		return minecraftInstall;
	}

	public static void setMinecraftInstall(String minecraftInstall) {
		Driver.minecraftInstall = minecraftInstall;
	}

	public static String getDownloadsLocation() {
		return downloadsLocation;
	}

	public static void setDownloadsLocation(String downloadsLocation) {
		Driver.downloadsLocation = downloadsLocation;
	}

	public static String getDesktopLocation() {
		return desktopLocation;
	}

	public static void setDesktopLocation(String desktopLocation) {
		Driver.desktopLocation = desktopLocation;
	}

	public static String getMinecraftDefaultInstall() {
		return minecraftDefaultInstall;
	}

	public static void setMinecraftDefaultInstall(String minecraftDefaultInstall) {
		Driver.minecraftDefaultInstall = minecraftDefaultInstall;
	}

	@SuppressWarnings("unused")
	private static PrintStream standardOut;

	public static void main(String[] args) {

		PrintStream printStream = new PrintStream(new CustomOutputStream(GUI.consoleOutput));
		standardOut = System.out;
		System.setOut(printStream);
		System.setErr(printStream);
		Boolean validOS = false;
		String OS = System.getProperty("os.name");
		if (OS.equals("Windows 10") || OS.equals("Windows 8.1") || OS.equals("Windows 7")) {
			validOS = true;
			osDetect.isWindows();
			GUI.launchGUI();
		}
		if (OS.equals("Windows Vista") || OS.equals("Windows XP")) {
			String message = "Why are you still using this computer?";
			JOptionPane.showMessageDialog(new JFrame(), message, "Outdated OS", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}

		if (OS.equals("Linux") || OS.equals("Unix")) {
			validOS = true;
			osDetect.isLinux();
			GUI.launchGUI();
		}

		if (OS.equals("Mac")) {
			validOS = true;
			osDetect.isMac();
			GUI.launchGUI();
		}

		if (validOS == false) {
			String message = "Your OS is not supported by this installer.";
			JOptionPane.showMessageDialog(new JFrame(), message, "Unknown Operating System", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}

	}

	public static boolean validURL(String url) {
		Boolean isValid = false;
		try {
			new URL(url).toURI();
			GUI.errors.setText("");
			isValid = true;
		} catch (MalformedURLException e) {
			isValid = false;
			GUI.errors.setText("Invalid link.");
		} catch (URISyntaxException f) {
			GUI.errors.setText("I got no idea. It's an error.");
		}
		return isValid;
	}

	public static void folderCreate(File folder) {
		if (!folder.exists()) {
			folder.mkdir();
		}
	}

}

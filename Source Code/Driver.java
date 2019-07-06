
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
	 * This program is free software. It comes without any warranty, to
     * the extent permitted by applicable law. You can redistribute it
     * and/or modify it under the terms of the Do What The Fuck You Want
     * To Public License, Version 2, as published by Sam Hocevar. See
     * http://www.wtfpl.net/ for more details.
	 *
	 * You're welcome to improve upon it. If you do, please let me know, and I'll
	 * implement your fix / improvement, and give you credit for it. I know my code
	 * isn't the best, I'm only a beginner.
	 * 
	 * Despite the GUI saying "Copyright" That's more my way of saying that I made it.
	 * And for the try/catch, it outputs a Pokemon name for a bit of humor. If someone tells me it said a Pokemon,
	 * I can look at where it is supposed to be outputting that, and understand what's going wrong.
	 *
	 */
	 
	private static File minecraftInstallLocation = null;
	private static String minecraftInstall = null;
	private static String downloadsLocation = null;
	private static String desktopLocation = null;
	private static String minecraftDefaultInstall = null;

   //Just your average getters and setters... Nothing interesting to see.
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
//This the main. This starts everything.
	public static void main(String[] args) {

		// Creating the custom output stream.
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
			//If you see this, well... why are you trying to run this on an unsupported OS?
			String message = "Your OS is not supported by this installer.";
			JOptionPane.showMessageDialog(new JFrame(), message, "Unknown Operating System", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}

	}

//I'm not too sure if this is needed or used, but I'll keep it for right now.
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

 //This explains itself.
	public static void folderCreate(File folder) {
		if (!folder.exists()) {
			folder.mkdir();
		}
	}

}

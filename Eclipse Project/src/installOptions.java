
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;

public class installOptions extends Install {
	static int ramSizeChosen = 0;
	static long memSize;
	static int ramSizeMb;
	static int ramSizeGb;
	static Font pretty;
	static String q = File.separator;
	static Boolean packGood = false;

	public static void launcherSettings() {
		File launcherSettings = new File(Driver.getMinecraftInstall() + q + "launcher_profiles.json");
		if (launcherSettings.exists()) {
			ArrayList<String> launcherData = new ArrayList<>();
			try {

				BufferedReader reader = new BufferedReader(new FileReader(launcherSettings));
				String s = "";
				String line = "";
				StringBuffer content = new StringBuffer();
				while ((s = reader.readLine()) != null) {
					content.append(s);
					content.trimToSize();
					line = content.toString().trim();
					line.trim();
					launcherData.add(line);
					content.delete(0, content.length());
				}
				reader.close();

				String chosenRamSize = "-Xmx" + Integer.toString(ramSizeChosen) + "M";
				int versionPos = launcherData.indexOf("\"lastVersionId\" : \"1.7.10-Forge10.13.4.1614-1.7.10\",");
				int argsPos = versionPos - 2;
				String newArgs = "\"javaArgs\" :\"-XX:HeapDumpPath=MojangTricksIntelDriversForPerformance_javaw.exe_minecraft.exe.heapdump -XX:+UseG1GC -XX:+UseConcMarkSweepGC"
						+ chosenRamSize
						+ " -XX:+UnlockExperimentalVMOptions -XX:G1NewSizePercent=20 -XX:G1ReservePercent=20 -XX:MaxGCPauseMillis=50 -XX:G1HeapRegionSize=32M\",";
				launcherData.set(argsPos, newArgs);
				Object[] settingsArray = launcherData.toArray();

				launcherSettings.delete();
				FileWriter newSettings = new FileWriter(Driver.getMinecraftInstall() + q + "launcher_profiles.json");
				for (int i = 0; i < settingsArray.length; i++) {
					newSettings.write(settingsArray[i] + "\n");
				}
				newSettings.close();
				System.out.println(" Launcher settings changed and memory set.");
				if (!featuresUsed) {
					installFinalize();
				}
				if (featuresUsed) {
					again();
				}

			} catch (IOException e1) {
				GUI.errors.setText("Chansey");

			} catch (ArrayIndexOutOfBoundsException a) {
				GUI.errors.setText("Skitty");
			}
		}
		if (!launcherSettings.exists()) {
			installFinalize();
		}
	}

	public static void verifyInstall() {
		String dirName = Driver.getDownloadsLocation() + q + "Modpack" + q + "mods" + q;
		String dirTwo = Driver.getMinecraftInstallLocation() + q + "mods" + q;
		ArrayList<String> contents = new ArrayList<>();
		ArrayList<String> contentsTwo = new ArrayList<>();

		Set<Object> listOne = new HashSet<Object>();
		Set<Object> listTwo = new HashSet<Object>();

		try {
			Files.list(new File(dirName).toPath()).forEach(path -> {
				contents.add(path.getFileName().toString());
			});

			Files.list(new File(dirTwo).toPath()).forEach(item -> {
				contentsTwo.add(item.getFileName().toString());
			});

			listOne.addAll(contents);
			listTwo.addAll(contentsTwo);

			Set<Object> fileCheck = new HashSet<Object>(listOne);
			fileCheck.removeAll(listTwo);
			ArrayList<Object> missing = new ArrayList<Object>(fileCheck);

			if (fileCheck.isEmpty() || fileCheck.size() == 0) {
				packGood = true;
				System.out.println(" All's good.");
			}
			if (!fileCheck.isEmpty()|| fileCheck.size() != 0) {
				System.out.println(" All's not good.");
				for (int i = fileCheck.size(); i > 0; i--) {
					fixMods(q + missing.remove(i - 1));
				}
				verifyInstall();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void fixMods(String missingMod) {
		File modsDirectory = new File(Driver.getMinecraftInstall() + q + "mods");
		File packDirectory = new File(Driver.getDownloadsLocation() + q + "Modpack" + q + "mods");
		File missingModFile;
		try {
			missingModFile = new File(packDirectory + missingMod);
			FileUtils.copyFileToDirectory(missingModFile, modsDirectory);
		} catch (IOException e) {
			GUI.errors.setText("Lapras");
		}
	}

	public static Boolean resourceCheck() {
		String home = System.getProperty("user.dir");
		File libsDir = new File(home + q + "Modpack-Installer_lib");
		File resourceDir = new File(home + q + "resources");
		if (libsDir.exists() && resourceDir.exists()) {
			return true;
		}
		return false;
	}

	public static void backup() {
		File profileBackup = new File(Driver.getMinecraftInstall() + q + "profilebackup");
		File launcherProfiles = new File(Driver.getMinecraftInstall() + q + "launcher_profiles.json");
		if (!profileBackup.exists()) {
			profileBackup.mkdir();
			try {
				FileUtils.copyFileToDirectory(launcherProfiles, profileBackup);
				System.out.println("Launcher Profiles backed up.");
			} catch (IOException e) {
				// Eh, do nothing here.
			}
		}
	}

	public static void restore() {
		File profileBackup = new File(Driver.getMinecraftInstall() + q + "profilebackup");
		File launcherProfiles = new File(Driver.getMinecraftInstall() + q + "launcher_profiles.json");
		File oldProfile = new File(profileBackup + File.separator + "launcher_profiles.json");
		try {
			FileUtils.forceDelete(launcherProfiles);
			FileUtils.copyFileToDirectory(oldProfile, Driver.getMinecraftInstallLocation());
			System.out.println(" Launcher Settings restored.");
			if (featuresUsed) {
				again();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void again() {
		String t = "Would you like yo use another option? Selecting no exits the program.";
		int o = JOptionPane.showConfirmDialog(new JFrame(), t, "Another option?", JOptionPane.YES_NO_OPTION);
		if (o == JOptionPane.YES_OPTION) {
			optionsGUI.otherOptionsGUI();
		}
		if (o == JOptionPane.NO_OPTION) {
			end();
		}
	}
}

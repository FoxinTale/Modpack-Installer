
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

	public static Boolean verifyInstall() {
		String fullLink = "https://sites.google.com/view/aubreys-modpack-info/home/modlist-full";
		ArrayList<String> fullMods = new ArrayList<>();
		ArrayList<String> modsDirList = new ArrayList<>();

		File modsDir = new File(Driver.getMinecraftInstall() + q + "mods");
		StringBuffer modName = new StringBuffer();

		List<File> files = (List<File>) FileUtils.listFiles(modsDir, null, true);

		websiteReader.siteReader(fullLink, false, 1, fullMods);

		String name = "";

		if (files.size() == 0) {
			// Do nothing, just catching stuff, really.
		}
		if (files.size() != 0) {
			for (int i = files.size() - 1; i > 0; i--) {
				String folders = modsDir.toString();
				name = files.get(i).getAbsolutePath();
				modName.append(name);
				modName.delete(0, folders.length() + 1);
				modsDirList.add(modName.toString());
				modName.delete(0, modName.length());
				name = "";
			}
			Collections.sort(modsDirList);
			Collections.sort(fullMods);

			Set<String> modsSet = new HashSet<String>(modsDirList);
			Set<String> siteSet = new HashSet<String>(fullMods);

			List<String> modsSorted = modsSet.stream().collect(Collectors.toList());
			List<String> siteModsSorted = siteSet.stream().collect(Collectors.toList());

			Set<String> mods = new HashSet<>(siteModsSorted);
			mods.removeAll(modsSorted);

			mods.remove("mcheli");
			mods.remove("Gammabrightv3.3[MC1.7.10].litemod");
			ArrayList<String> missing = new ArrayList<>(mods);
			if (!mods.isEmpty() || mods.size() != 0) {
				for (int i = mods.size(); i > 0; i--) {
					System.out.println("Missing mod(s): " + missing.remove(i - 1));
					// fixMods(q + missing.remove(i - 1));
				}
			}

			if (mods.isEmpty() || mods.size() == 0) {
				return true;
			}
		}
		return false;
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
			if(featuresUsed) {
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

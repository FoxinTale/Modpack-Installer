import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;

import org.apache.commons.io.FileUtils;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class installOptions extends Install {
	static int ramSizeChosen = 0;
	static long memSize;
	static int ramSizeMb;
	static int ramSizeGb;
	static Font pretty;
	static String q = File.separator;

	public static void launcherSettings() {
		File launcherSettings = new File(Driver.getMinecraftInstall() + q + "launcher_profiles.json");
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
			if (featuresUsed == false) {
				installFinalize();
			}

		} catch (IOException e1) {
			GUI.errors.setText("Chansey");

		} catch (ArrayIndexOutOfBoundsException a) {
			GUI.errors.setText("Skitty");
		}
	}

	public static void sliderGUI() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("Ram adjustment slider");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(480, 210);
		JPanel sliderPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		GridLayout format = new GridLayout(2, 1);

		try {
			Sigar memInfo = new Sigar();
			Mem memory = new Mem();
			memory.gather(memInfo);
			memSize = memory.getRam();
			int ram = (int) memSize;
			ramSizeMb = setRamSize(ram);
			ramSizeGb = ramSizeMb / 1024;
		} catch (SigarException s) {

		}

		JButton next = new JButton("Continue");
		JSlider allocatedRam = new JSlider();
		allocatedRam.setMaximum(ramSizeMb - 2048);
		allocatedRam.setMinimum(2048);
		allocatedRam.setValue(4096);
		allocatedRam.setMajorTickSpacing(1024);
		allocatedRam.setMinorTickSpacing(512);
		allocatedRam.setPaintTicks(true);
		allocatedRam.setPaintLabels(true);

		Hashtable<Integer, JLabel> position = new Hashtable<Integer, JLabel>();
		for (int i = 2; i < ramSizeGb; i++) {
			switch (i) {
			case 2:
				position.put(2048, new JLabel("2"));
				break;
			case 3:
				position.put(3072, new JLabel("3"));
				break;
			case 4:
				position.put(4096, new JLabel("4"));
				break;
			case 5:
				position.put(5120, new JLabel("5"));
				break;
			case 6:
				position.put(6144, new JLabel("6"));
				break;
			case 7:
				position.put(7168, new JLabel("7"));
				break;
			case 8:
				position.put(8192, new JLabel("8"));
				break;
			case 9:
				position.put(9216, new JLabel("9"));
				break;
			case 10:
				position.put(10240, new JLabel("10"));
				break;
			case 11:
				position.put(11264, new JLabel("11"));
				break;
			case 12:
				position.put(12288, new JLabel("12"));
				break;
			case 13:
				position.put(13312, new JLabel("13"));
				break;
			case 14:
				position.put(14336, new JLabel("14"));
				break;
			default:

			}
		}
		ActionListener buttonEvent = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selection = allocatedRam.getValue();
				ramSizeChosen = selection;
				setRamSize(selection);
				frame.setVisible(false);
				launcherSettings();
			}
		};
		next.addActionListener(buttonEvent);
		position.put(ramSizeMb, new JLabel(Integer.toString(ramSizeGb)));

		allocatedRam.setLabelTable(position);// Set the label to be drawn

		allocatedRam.setSnapToTicks(true);

		sliderPanel.add(allocatedRam);
		buttonPanel.add(next);
		frame.add(sliderPanel);
		frame.add(buttonPanel);
		allocatedRam.setPreferredSize(new Dimension(400, 105));
		next.setPreferredSize(new Dimension(150, 20));
		frame.setLayout(format);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	public static int getRamSizeChosen() {
		return ramSizeChosen;
	}

	public static void setRamSizeChosen(int ramSizeChosen) {
		installOptions.ramSizeChosen = ramSizeChosen;
	}

	public static int setRamSize(int ram) {
		int totalRam = 0;
		if (ram >= 16384) {
			totalRam = 16384;
			// Higher than 16 Gb. No need to allocate more than this to Minecraft on its
			// own.
		}
		if (ram <= 16384 && ram > 14366) {
			totalRam = 16384;
			// 16 Gb.
		}
		if (ram <= 14336 && ram > 12288) {
			totalRam = 14366;
			// 14 Gb.
		}
		if (ram <= 12288 && ram > 10240) {
			totalRam = 12288;
			// 12 Gb.
		}
		if (ram <= 10240 && ram > 9216) {
			totalRam = 10240;
			// 10 Gb.
		}
		if (ram <= 9216 && ram > 8192) {
			totalRam = 9216;
			// 9 Gb.
		}
		if (ram <= 8192 && ram > 6144) {
			totalRam = 8192;
			// 8 Gb.
		}
		if (ram <= 6144 && ram > 5120) {
			totalRam = 6144;
			// 6 Gb.
		}
		if (ram <= 5120 && ram > 4096) {
			totalRam = 5120;
			// 5 Gb.
		}
		if (ram <= 4096 && ram > 3072) {
			totalRam = 4096;
			// 4 Gb.
		}
		if (ram <= 3072) {
			notSupported();
			// 4 gb minimum. 2 Gb for pack, and 2 Gb for the OS. This won't work.
		}
		if (totalRam == 0) {
			// Error catching stuff, essentially.
		}
		return totalRam;
	}

	public static void notSupported() {
		String message = "Why are you trying to run this pack on a machine with this little ram?";
		JOptionPane.showMessageDialog(new JFrame(), message, "Low Ram", JOptionPane.ERROR_MESSAGE);
		System.exit(0);
	}

	public static void otherOptionsGUI() {
		JFrame frame = new JFrame("Options");

		JRadioButton extract = new JRadioButton("Extract.");
		JRadioButton launcher = new JRadioButton("Set Memory.");
		JRadioButton ping = new JRadioButton("Ping Server.");
		JRadioButton downloadUpdate = new JRadioButton("Download Update File.");
		JButton go = new JButton("Continue");
		ButtonGroup options = new ButtonGroup();

		options.add(ping);
		options.add(launcher);
		options.add(extract);
		options.add(downloadUpdate);

		Color rbc = new Color(220, 255, 255); // Hex value: dcffff

		JPanel pingPanel = new RoundedPanel(10, rbc);
		JPanel launcherPanel = new RoundedPanel(10, rbc);
		JPanel extractPanel = new RoundedPanel(10, rbc);
		JPanel updatePanel = new RoundedPanel(10, rbc);

		Container c = frame.getContentPane();

		c.setBackground(new Color(255, 220, 220));// Hex value: ffdcdc

		ping.setBackground(rbc);
		launcher.setBackground(rbc);
		extract.setBackground(rbc);
		downloadUpdate.setBackground(rbc);

		ActionListener radioButtonEvent = new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				AbstractButton absButton = (AbstractButton) ae.getSource();
				String selection = absButton.getText();

				if (selection.equals("Extract.")) {
					extract.setEnabled(false);
					launcher.setEnabled(false);
					ping.setEnabled(false);
					downloadUpdate.setEnabled(false);
					selectedOption = 1;
				}
				if (selection.equals("Set Memory.")) {
					extract.setEnabled(false);
					launcher.setEnabled(false);
					ping.setEnabled(false);
					downloadUpdate.setEnabled(false);
					selectedOption = 2;
				}
				if (selection.equals("Ping Server.")) {
					extract.setEnabled(false);
					launcher.setEnabled(false);
					ping.setEnabled(false);
					downloadUpdate.setEnabled(false);
					selectedOption = 3;
				}
				if (selection.equals("Download Update File.")) {
					extract.setEnabled(false);
					launcher.setEnabled(false);
					ping.setEnabled(false);
					downloadUpdate.setEnabled(false);
					selectedOption = 4;

				}
			}
		};

		ActionListener buttonEvent = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (selectedOption == 1) {
					System.out.println(" Extracting File...");
					Extractor.Extract(q + Driver.getDownloadsLocation() + q + Driver.zipFile, "Modpack");
				}
				if (selectedOption == 2) {
					installOptions.sliderGUI();
				}
				if (selectedOption == 3) {
					System.out.println(" Pinging Server...");
					serverPing();
				}
				if (selectedOption == 4) {
					Driver.updateTime = true;
					ArrayList<String> versions = new ArrayList<>();
					String baseLink = "https://aubreys-storage.s3.us-east-2.amazonaws.com/1.7.10/Updates/";
					websiteReader.siteReader("https://sites.google.com/view/aubreys-modpack-info/home/latest-version",
							false, 2, versions);
					String versionPreTrim = (Arrays.toString(versions.toArray()).replace('[', ' ').replace(']', ' '));
					String currentVersion = versionPreTrim.trim();

					try {
						Downloader.Download(new URL(baseLink + currentVersion + ".zip"), currentVersion + ".zip");
					} catch (MalformedURLException u) {
						GUI.errors.setText("Bastiodon");
					}
				}
			}
		};

		try {
			pretty = Font.createFont(Font.TRUETYPE_FONT, new File("resources" + q + "Font.ttf")).deriveFont(16f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("resources" + q + "Font.ttf")));
		} catch (IOException e) {

		} catch (FontFormatException e) {
			GUI.errors.setText("Screwy font");
		}

		ping.addActionListener(radioButtonEvent);
		launcher.addActionListener(radioButtonEvent);
		extract.addActionListener(radioButtonEvent);
		downloadUpdate.addActionListener(radioButtonEvent);

		go.addActionListener(buttonEvent);

		extractPanel.setBounds(73, 50, 175, 25);
		extract.setBounds(78, 55, 150, 15);

		pingPanel.setBounds(73, 85, 175, 25);
		ping.setBounds(78, 90, 150, 15);

		launcher.setBounds(78, 125, 150, 15);
		launcherPanel.setBounds(73, 120, 175, 25);

		downloadUpdate.setBounds(78, 160, 150, 15);
		updatePanel.setBounds(73, 155, 175, 25);

		go.setBounds(100, 210, 100, 20);

		ping.setFont(pretty);
		launcher.setFont(pretty);
		extract.setFont(pretty);
		go.setFont(pretty);
		downloadUpdate.setFont(pretty);

		frame.add(ping);
		frame.add(pingPanel);

		frame.add(launcher);
		frame.add(launcherPanel);

		frame.add(extract);
		frame.add(extractPanel);

		frame.add(downloadUpdate);
		frame.add(updatePanel);

		frame.add(go);
		frame.setSize(320, 320);
		frame.setResizable(false);

		frame.setLayout(null);// using no layout managers
		frame.setVisible(true);

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
			ArrayList<String> missing = new ArrayList<>(mods);

			if (!mods.isEmpty()) {
				for (int i = mods.size(); i > 0; i--) {
					// System.out.println("Missing mod(s): " + missing.remove(i - 1));
					fixMods(q + missing.remove(i - 1));
				}
			}

			if (mods.isEmpty()) {
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
}

package memoryAdjust;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class Main {
	static int ramSizeChosen = 0;

	public static void main(String[] args) {
		try {
			sliderGUI();
		} catch (SigarException e) {
			e.printStackTrace();
		}
	}

	public static void launcherSettings() {
		File launcherSettings = new File("C:\\Users\\Alyan\\Desktop\\launcher_profiles.json");
		ArrayList<String> launcherData = new ArrayList<>();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(launcherSettings));
			String s = "";
			String line = "";
			StringBuilder content = new StringBuilder();
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
			String newArgs = "\"javaArgs\" :\"-XX:HeapDumpPath=MojangTricksIntelDriversForPerformance_javaw.exe_minecraft.exe.heapdump -XX:+UseG1GC "
					+ chosenRamSize
					+ " -XX:+UnlockExperimentalVMOptions -XX:G1NewSizePercent=20 -XX:G1ReservePercent=20 -XX:MaxGCPauseMillis=50 -XX:G1HeapRegionSize=32M\",";
			launcherData.set(argsPos, newArgs);

			Object[] settingsArray = launcherData.toArray();

			launcherSettings.delete();
			FileWriter newSettings = new FileWriter("C:\\Users\\Alyan\\Desktop\\launcher_profiles.json");
			for (int i = 0; i < settingsArray.length; i++) {
				newSettings.write(settingsArray[i] + "\n");
			}
			newSettings.close();
			System.out.println("Launcher settings changed.");
			System.exit(0);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static void sliderGUI() throws SigarException {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("Ram adjustment slider");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(480, 210);
		JPanel sliderPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		GridLayout format = new GridLayout(2, 1);

		Sigar memInfo = new Sigar();
		Mem memory = new Mem();

		memory.gather(memInfo);
		long memSize = memory.getRam();
		int ram = (int) memSize;
		int ramSizeMb = setRamSize(ram);
		int ramSizeGb = ramSizeMb / 1024;

		JButton next = new JButton("Continue");
		JSlider allocatedRam = new JSlider();
		allocatedRam.setMaximum(ramSizeMb - 2048);
		allocatedRam.setMinimum(2048);
		allocatedRam.setValue(4096);

		allocatedRam.setMajorTickSpacing(1024);
		allocatedRam.setMinorTickSpacing(512);
		allocatedRam.setPaintTicks(true);
		allocatedRam.setPaintLabels(true);

		System.out.println(Integer.toString(ramSizeGb) + " Gb");
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
		Main.ramSizeChosen = ramSizeChosen;
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
			// 4 gb minimum. 2 Gb for pack, and 2 Gb for the OS.
		}
		if (totalRam == 0) {
			
			// Error catching stuff, essentially.
		}

		return totalRam;
	}

	public static void notSupported() {

	}
}

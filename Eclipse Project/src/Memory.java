import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class Memory extends installOptions {
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
			// Usually, this would be a pretty bad error.
			// Currently, since this whole thing is unused, who cares!
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
				Json.readProfileData(ramSizeChosen);
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

	public static int setRamSize(int ram) {
		int totalRam = 0;
		if (ram >= 16384) {
			totalRam = 16384;
			// Higher than 16 Gb. No need to allocate more than this to Minecraft on its
			// own.
		}
		if (ram <= 16384 && ram > 14366) { // 16 Gb.
			totalRam = 16384;
		}
		if (ram <= 14336 && ram > 12288) { // 14 Gb.
			totalRam = 14366;
		}
		if (ram <= 12288 && ram > 10240) { // 12 Gb.
			totalRam = 12288;
		}
		if (ram <= 10240 && ram > 9216) { // 10 Gb.
			totalRam = 10240;
		}
		if (ram <= 9216 && ram > 8192) { // 9 Gb.
			totalRam = 9216;
		}
		if (ram <= 8192 && ram > 6144) { // 8 Gb.
			totalRam = 8192;
		}
		if (ram <= 6144 && ram > 5120) { // 6 Gb.
			totalRam = 6144;
		}
		if (ram <= 5120 && ram > 4096) { // 5 Gb.
			totalRam = 5120;
		}
		if (ram <= 4096 && ram > 3072) { // 4 Gb.
			totalRam = 4096;

		}
		if (ram <= 3072) {
			totalRam = 2048;
			// 4 gb minimum. 2 Gb for pack, and 2 Gb for the OS. This won't work.
		}
		if (totalRam == 0) {
			// Error catching stuff, essentially.
		}
		return totalRam;
	}

	public static int getRamSizeChosen() {
		return ramSizeChosen;
	}

	public static void setRamSizeChosen(int ramSizeChosen) {
		installOptions.ramSizeChosen = ramSizeChosen;
	}
}

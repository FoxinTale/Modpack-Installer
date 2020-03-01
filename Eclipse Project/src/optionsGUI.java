import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class optionsGUI extends installOptions {
	public static void otherOptionsGUI() {
		JFrame frame = new JFrame("Options");

		JFrame.setDefaultLookAndFeelDecorated(true);
		JRadioButton extract = new JRadioButton("Extract.");
		JRadioButton launcher = new JRadioButton("Set Memory.");
		JRadioButton ping = new JRadioButton("Ping Server.");
		JRadioButton downloadUpdate = new JRadioButton("Download Update File.");
		JRadioButton restoreSettings = new JRadioButton("Restore Settings.");
		JRadioButton optionalMods = new JRadioButton("Optional Mods.");
		JButton go = new JButton("Continue");
		ButtonGroup options = new ButtonGroup();

		// JLabel info1 = new JLabel("There was something here.");
		// JLabel info2 = new JLabel("But it is really broken.");

		options.add(ping);
		options.add(launcher);
		options.add(extract);
		options.add(downloadUpdate);
		options.add(optionalMods);

		Color rbc = new Color(220, 255, 255); // Hex value: dcffff

		JPanel pingPanel = new RoundedPanel(10, rbc);
		JPanel launcherPanel = new RoundedPanel(10, rbc);
		JPanel extractPanel = new RoundedPanel(10, rbc);
		JPanel updatePanel = new RoundedPanel(10, rbc);
		JPanel restorePanel = new RoundedPanel(10, rbc);
		JPanel modPanel = new RoundedPanel(10, rbc);

		Container c = frame.getContentPane();

		c.setBackground(new Color(255, 220, 220));// Hex value: ffdcdc

		ping.setBackground(rbc);
		launcher.setBackground(rbc);
		extract.setBackground(rbc);
		downloadUpdate.setBackground(rbc);
		restoreSettings.setBackground(rbc);
		optionalMods.setBackground(rbc);

		ActionListener radioButtonEvent = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				AbstractButton absButton = (AbstractButton) ae.getSource();
				String selection = absButton.getText();
				switch (selection) {
				case "Extract.":
					selectedOption = 1;
					break;
				case "Set Memory.":
					selectedOption = 2;
					break;
				case "Ping Server.":
					selectedOption = 3;
					break;
				case "Download Update File.":
					selectedOption = 4;
					break;
				case "Restore Settings.":
					selectedOption = 5;
					break;
				case "Optional Mods.":
					selectedOption = 6;
					break;
				default:
					break;
				}
			}
		};

		ActionListener buttonEvent = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch (selectedOption) {
				case 1:
					radioSet(extract, launcher, ping, downloadUpdate, restoreSettings, optionalMods);
					System.out.println(" Extracting File...");
					Extractor.Extract(q + Driver.getDownloadsLocation() + q + Driver.zipFile, "Modpack", 0);
					break;
				case 2:
					radioSet(extract, launcher, ping, downloadUpdate, restoreSettings, optionalMods);
					Memory.sliderGUI();
					break;
				case 3:
					radioSet(extract, launcher, ping, downloadUpdate, restoreSettings, optionalMods);
					System.out.println(" Pinging Server...");
					serverPing();
					break;
				case 4:
					radioSet(extract, launcher, ping, downloadUpdate, restoreSettings, optionalMods);
					Driver.updateTime = true;
					String baseLink = "https://aubreys-storage.s3.us-east-2.amazonaws.com/1.7.10/Updates/";
					String currentVersion = Json.getCurrentVersion();
					try {
						Downloader.Download(new URL(baseLink + currentVersion + ".zip"), currentVersion + ".zip", 1);
					} catch (MalformedURLException u) {
						GUI.errors.setText("Bastiodon");
					}
					break;
				case 5:
					radioSet(extract, launcher, ping, downloadUpdate, restoreSettings, optionalMods);
					restore();
					break;

				case 6:
					radioSet(extract, launcher, ping, downloadUpdate, restoreSettings, optionalMods);;
					modOptions.modOptionsGui();
					break;
				default:
					break;
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
		restoreSettings.addActionListener(radioButtonEvent);
		optionalMods.addActionListener(radioButtonEvent);

		go.addActionListener(buttonEvent);

		extractPanel.setBounds(63, 30, 200, 25);
		extract.setBounds(78, 35, 175, 15);

		pingPanel.setBounds(63, 65, 200, 25);
		ping.setBounds(68, 70, 175, 15);

		launcherPanel.setBounds(63, 100, 200, 25);
		launcher.setBounds(68, 105, 175, 15); // Launcher (Memory Set)

		updatePanel.setBounds(63, 135, 200, 25);
		downloadUpdate.setBounds(68, 140, 175, 15);

		restorePanel.setBounds(63, 170, 200, 25);
		restoreSettings.setBounds(68, 175, 175, 15); // Restore settings, Width 150.

		modPanel.setBounds(63, 205, 200, 25);
		optionalMods.setBounds(68, 210, 175, 15);

		go.setBounds(90, 300, 100, 30);

		ping.setFont(pretty);
		launcher.setFont(pretty);
		extract.setFont(pretty);
		go.setFont(pretty);
		downloadUpdate.setFont(pretty);
		restoreSettings.setFont(pretty);
		// info1.setFont(pretty);
		// info2.setFont(pretty);
		optionalMods.setFont(pretty);

		frame.add(ping);
		frame.add(pingPanel);

		frame.add(launcher);
		frame.add(launcherPanel);

		// frame.add(extract);
		// frame.add(extractPanel);

		frame.add(downloadUpdate);
		frame.add(updatePanel);

		frame.add(restoreSettings);
		frame.add(restorePanel);

		// frame.add(info1);
		// frame.add(info2);
		frame.add(optionalMods);
		frame.add(modPanel);

		frame.add(go);
		frame.setSize(320, 400);
		frame.setResizable(false);

		frame.setLayout(null);// using no layout managers
		frame.setVisible(true);
	}

	public static void radioSet(JRadioButton a, JRadioButton b, JRadioButton c, JRadioButton d, JRadioButton e,
			JRadioButton f) {
		a.setEnabled(false);
		b.setEnabled(false);
		c.setEnabled(false);
		d.setEnabled(false);
		e.setEnabled(false);
		f.setEnabled(false);
	}
}

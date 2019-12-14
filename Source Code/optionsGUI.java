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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class optionsGUI extends installOptions {
	public static void otherOptionsGUI() {
		JFrame frame = new JFrame("Options");

		JRadioButton extract = new JRadioButton("Extract.");
		JRadioButton launcher = new JRadioButton("Set Memory.");
		JRadioButton ping = new JRadioButton("Ping Server.");
		JRadioButton downloadUpdate = new JRadioButton("Download Update File.");
		JRadioButton restoreSettings = new JRadioButton("Restore Settings.");
		JButton go = new JButton("Continue");
		ButtonGroup options = new ButtonGroup();
		
		JLabel info1 = new JLabel("There was something here.");
		JLabel info2 = new JLabel("But it is really broken.");

		options.add(ping);
		options.add(launcher);
		options.add(extract);
		options.add(downloadUpdate);

		Color rbc = new Color(220, 255, 255); // Hex value: dcffff

		JPanel pingPanel = new RoundedPanel(10, rbc);
		// JPanel launcherPanel = new RoundedPanel(10, rbc);
		JPanel extractPanel = new RoundedPanel(10, rbc);
		JPanel updatePanel = new RoundedPanel(10, rbc);
		// JPanel restorePanel = new RoundedPanel(10, rbc);

		Container c = frame.getContentPane();

		c.setBackground(new Color(255, 220, 220));// Hex value: ffdcdc

		ping.setBackground(rbc);
		launcher.setBackground(rbc);
		extract.setBackground(rbc);
		downloadUpdate.setBackground(rbc);
		restoreSettings.setBackground(rbc);

		ActionListener radioButtonEvent = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				AbstractButton absButton = (AbstractButton) ae.getSource();
				String selection = absButton.getText();

				if (selection.equals("Extract.")) {
					selectedOption = 1;
				}
				if (selection.equals("Set Memory.")) {
					selectedOption = 2;
				}
				if (selection.equals("Ping Server.")) {
					selectedOption = 3;
				}
				if (selection.equals("Download Update File.")) {
					selectedOption = 4;
				}

				if (selection.equals("Restore Settings.")) {
					selectedOption = 5;
				}
			}
		};

		ActionListener buttonEvent = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedOption == 1) {
					extract.setEnabled(false);
					launcher.setEnabled(false);
					ping.setEnabled(false);
					downloadUpdate.setEnabled(false);
					restoreSettings.setEnabled(false);
					System.out.println(" Extracting File...");
					Extractor.Extract(q + Driver.getDownloadsLocation() + q + Driver.zipFile, "Modpack", 0);
				}
				if (selectedOption == 2) {
					extract.setEnabled(false);
					launcher.setEnabled(false);
					ping.setEnabled(false);
					downloadUpdate.setEnabled(false);
					restoreSettings.setEnabled(false);
					Memory.sliderGUI();
				}
				if (selectedOption == 3) {
					extract.setEnabled(false);
					launcher.setEnabled(false);
					ping.setEnabled(false);
					downloadUpdate.setEnabled(false);
					restoreSettings.setEnabled(false);
					System.out.println(" Pinging Server...");
					serverPing();
				}
				if (selectedOption == 4) {
					extract.setEnabled(false);
					launcher.setEnabled(false);
					ping.setEnabled(false);
					downloadUpdate.setEnabled(false);
					restoreSettings.setEnabled(false);
					Driver.updateTime = true;
					String baseLink = "https://aubreys-storage.s3.us-east-2.amazonaws.com/1.7.10/Updates/";
					String currentVersion = Json.getCurrentVersion();

					try {
						Downloader.Download(new URL(baseLink + currentVersion + ".zip"), currentVersion + ".zip", 1);
					} catch (MalformedURLException u) {
						GUI.errors.setText("Bastiodon");
					}
				}

				if (selectedOption == 5) {
					extract.setEnabled(false);
					launcher.setEnabled(false);
					ping.setEnabled(false);
					downloadUpdate.setEnabled(false);
					restoreSettings.setEnabled(false);
					restore();
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

		go.addActionListener(buttonEvent);

		extractPanel.setBounds(73, 30, 175, 25);
		extract.setBounds(78, 35, 150, 15);

		pingPanel.setBounds(73, 65, 175, 25);
		ping.setBounds(78, 70, 150, 15);

		// launcherPanel.setBounds(73, 100, 175, 25);
		info1.setBounds(78, 105, 200, 15); // Launcher (Memory Set) 

		updatePanel.setBounds(73, 135, 175, 25);
		downloadUpdate.setBounds(78, 140, 150, 15);

		// restorePanel.setBounds(73, 170, 175, 25);
		info2.setBounds(78, 175, 200, 15); // Restore settings, Width 150.

		go.setBounds(100, 220, 100, 20);

		ping.setFont(pretty);
		launcher.setFont(pretty);
		extract.setFont(pretty);
		go.setFont(pretty);
		downloadUpdate.setFont(pretty);
		restoreSettings.setFont(pretty);
		info1.setFont(pretty);
		info2.setFont(pretty);

		frame.add(ping);
		frame.add(pingPanel);

		// frame.add(launcher);
		// frame.add(launcherPanel);

		// frame.add(extract);
		// frame.add(extractPanel);

		frame.add(downloadUpdate);
		frame.add(updatePanel);

		// frame.add(restoreSettings);
		// frame.add(restorePanel);
		
		frame.add(info1);
		frame.add(info2);

		frame.add(go);
		frame.setSize(320, 320);
		frame.setResizable(false);

		frame.setLayout(null);// using no layout managers
		frame.setVisible(true);
	}
}

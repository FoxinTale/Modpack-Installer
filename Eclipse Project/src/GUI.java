import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

/*
 * Let's be honest, this class does document itself, for the most part.
 * 
 */
public class GUI {
	static JProgressBar progress = new JProgressBar();
	static JEditorPane pane = new JEditorPane();
	static JTextArea consoleOutput = new JTextArea();
	static JScrollPane scroll = new JScrollPane(consoleOutput);
	static JTextField errors = new JTextField(" Nothing to report.");
	static Boolean packDownloadOnly = false;
	static Boolean updateOnly = false;
	static Font pretty;
	static String q = File.separator;

	public static void launchGUI() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		JRadioButton modpackOne = new JRadioButton("Do it for me.");
		JRadioButton downloadOption = new JRadioButton("Just download the zip file.");
		JRadioButton updateOption = new JRadioButton("Update.");
		JRadioButton otherOptions = new JRadioButton("Other Features.");
		JRadioButton extractOption = new JRadioButton("");

		JButton button = new JButton("Download");
		JLabel errorsLabel = new JLabel("Errors: ");
		ButtonGroup options = new ButtonGroup();
		
		JLabel installerVersion = new JLabel("Version 2.0.1");

		consoleOutput.setLineWrap(true);

		frame.getContentPane().add(scroll);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		progress.setMaximum(100000);

		scroll.setBorder(new LineBorder(Color.black, 1, true));

		Color rbc = new Color(220, 255, 255); // Hex value: dcffff

		options.add(modpackOne);
		options.add(downloadOption);
		options.add(updateOption);
		options.add(extractOption);
		options.add(otherOptions);

		JPanel modpackPanel = new RoundedPanel(10, rbc);
		JPanel downloadPanel = new RoundedPanel(10, rbc);
		JPanel updatePanel = new RoundedPanel(10, rbc);
		JPanel optionsPanel = new RoundedPanel(10, rbc);

		ActionListener radioButtonEvent = new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				AbstractButton absButton = (AbstractButton) ae.getSource();
				String selection = absButton.getText();
				if (Driver.installProgress == 0) {
					Boolean validPack = false;
					if (selection.equals("Do it for me.")) {
						System.out.println(" You have selected the 1.7.10 Modpack.");
						System.out.println(" Please click next to continue.");
						modpackOne.setEnabled(false);
						downloadOption.setEnabled(false);
						updateOption.setEnabled(false);
						otherOptions.setEnabled(false);
						Driver.selectedOption = 1;
						Driver.installProgress = 1;
						validPack = true;
					}
					if (selection.equals("Just download the zip file.")) {
						modpackOne.setEnabled(false);
						downloadOption.setEnabled(false);
						otherOptions.setEnabled(false);
						updateOption.setEnabled(false);
						Driver.installProgress = 1;
						Driver.selectedOption = 2;
						validPack = true;
					}
					if (selection.equals("Update.")) {
						modpackOne.setEnabled(false);
						downloadOption.setEnabled(false);
						otherOptions.setEnabled(false);
						updateOption.setEnabled(false);
						extractOption.setEnabled(false);
						Driver.updateTime = true;
						Driver.selectedOption = 3;
						Driver.installProgress = 1;
						validPack = true;
					}
					if (selection.equals("Install.")) {

						modpackOne.setEnabled(false);
						downloadOption.setEnabled(false);
						updateOption.setEnabled(false);
						otherOptions.setEnabled(false);
						extractOption.setEnabled(false);
						Driver.selectedOption = 4;
						Driver.installProgress = 1;
						validPack = true;
						Install.install();
					}
					if (selection.equals("Other Features.")) {
						Driver.selectedOption = 5;
						Driver.installProgress = 1;
						modpackOne.setEnabled(false);
						downloadOption.setEnabled(false);
						updateOption.setEnabled(false);
						extractOption.setEnabled(false);
						otherOptions.setEnabled(false);
						button.setText("Next");
						validPack = true;
					}
					if (validPack == false) {
						System.out.println("\n Something went terribly wrong.\n");
						errors.setText("Uh...Notify the developer.");
					}
				}
			}
		};

		ActionListener buttonEvent = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (Driver.installProgress == 1 & Driver.selectedOption == 1) {
					try {
						URL modpackOneLink = new URL(
								"https://aubreys-storage.s3.us-east-2.amazonaws.com/1.7.10/Modpack.zip");
						Downloader.Download(modpackOneLink, "Modpack.zip");
					} catch (MalformedURLException f) {
						errors.setText("Fuck you Java.");
						System.out.println("Shaymin");
					}
				}

				if (Driver.installProgress == 1 && Driver.selectedOption == 2) {
					try {
						URL modpackTwoLink = new URL(
								"https://aubreys-storage.s3.us-east-2.amazonaws.com/1.7.10/Modpack.zip");
						packDownloadOnly = true;
						Downloader.Download(modpackTwoLink, "Modpack.zip");
					} catch (MalformedURLException g) {
						errors.setText("Fuck you Java.");
						System.out.println("Glameow");
					}
				}

				if (Driver.installProgress == 1 && Driver.selectedOption == 3) {
					Updater.updater();
				}
				if (Driver.installProgress == 1 && Driver.selectedOption == 4) {
					installOptions.sliderGUI();
				}
				if (Driver.installProgress == 1 && Driver.selectedOption == 5) {
					installOptions.otherOptionsGUI();
					Install.featuresUsed = true;
				}
			}
		};
		
		try {
			pretty = Font.createFont(Font.TRUETYPE_FONT, new File("resources" + q + "Font.ttf")).deriveFont(16f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("resources" + q + "Font.ttf")));
		} catch (IOException e) {

		} catch (FontFormatException e) {
			System.out.println("Screwy font");
		}

		frame.setFont(pretty);
		consoleOutput.setFont(pretty);
		modpackOne.setFont(pretty);
		downloadOption.setFont(pretty);
		otherOptions.setFont(pretty);
		updateOption.setFont(pretty);
		errors.setFont(pretty);
		errorsLabel.setFont(pretty);
		button.setFont(pretty);
		installerVersion.setFont(pretty);

		frame.setTitle("Modpack Installer by Aubrey");
		
		button.setBounds(325, 450, 100, 25);// x axis, y axis, width, height
		scroll.setBounds(25, 25, 400, 200);
		errors.setBounds(75, 405, 350, 20);
		errorsLabel.setBounds(25, 405, 50, 20);

		modpackOne.setBounds(120, 255, 200, 15);
		modpackPanel.setBounds(115, 250, 250, 25);

		downloadOption.setBounds(120, 290, 200, 15);
		downloadPanel.setBounds(115, 285, 250, 25);

		otherOptions.setBounds(120, 325, 200, 15);
		optionsPanel.setBounds(115, 320, 250, 25);

		updateOption.setBounds(120, 360, 200, 15);
		updatePanel.setBounds(115, 355, 250, 25);
		
		progress.setBounds(25, 450, 275, 25); // X, Y, Width, Height
		installerVersion.setBounds(190, 485, 100, 20);

		// Radio Button Colour
		modpackOne.setBackground(rbc);
		downloadOption.setBackground(rbc);
		otherOptions.setBackground(rbc);
		updateOption.setBackground(rbc);

		modpackPanel.add(modpackOne);
		downloadPanel.add(downloadOption);
		optionsPanel.add(otherOptions);
		updatePanel.add(updateOption);

		consoleOutput.setEditable(false);
		errors.setEditable(false);

		System.out.println(" Welcome to the installer!");
		System.out.println(" Please select an option from below.\n");

		modpackOne.addActionListener(radioButtonEvent);
		downloadOption.addActionListener(radioButtonEvent);
		updateOption.addActionListener(radioButtonEvent);
		extractOption.addActionListener(radioButtonEvent);
		otherOptions.addActionListener(radioButtonEvent);
		button.addActionListener(buttonEvent);
		
		ImageIcon background = new ImageIcon("resources" + q + "Background.png");
		Image bg = background.getImage();
		Image bgImg = bg.getScaledInstance(480, 550, Image.SCALE_SMOOTH);
		background = new ImageIcon(bgImg);
		JLabel backgroundImage = new JLabel(background);
		backgroundImage.setBounds(0, 0, 480, 550);

		frame.add(button);// adding button in JFrame
		frame.add(scroll);
		frame.add(progress);
		frame.add(errors);
		frame.add(errorsLabel);
		frame.add(modpackOne);

		frame.add(downloadOption);

		frame.add(updateOption);
		frame.add(otherOptions);

		frame.add(modpackPanel);
		frame.add(downloadPanel);
		
		frame.add(optionsPanel);
		frame.add(updatePanel);
		
		frame.add(backgroundImage);
		frame.add(installerVersion);
		
		frame.setSize(480, 550);
		frame.setResizable(false);

		frame.setLayout(null);// using no layout managers
		frame.setVisible(true);// making the frame visible
	}
}

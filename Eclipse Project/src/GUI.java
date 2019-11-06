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
		JRadioButton resourceOption = new JRadioButton("Get Resource Pack.");
		JRadioButton extractOption = new JRadioButton("");

		JButton button = new JButton("...");
		JLabel errorsLabel = new JLabel("Errors: ");
		ButtonGroup options = new ButtonGroup();

		JLabel installerVersion = new JLabel("Version 3.0.0");

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
		options.add(resourceOption);

		JPanel modpackPanel = new RoundedPanel(10, rbc);
		JPanel downloadPanel = new RoundedPanel(10, rbc);
		JPanel updatePanel = new RoundedPanel(10, rbc);
		JPanel optionsPanel = new RoundedPanel(10, rbc);
		JPanel resourcePanel = new RoundedPanel(10, rbc);

		ActionListener radioButtonEvent = new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				AbstractButton absButton = (AbstractButton) ae.getSource();
				String selection = absButton.getText();
				Boolean validPack = false;
				if (selection.equals("Do it for me.")) {
					Driver.setSelectedOption(1);
					button.setText("Download");
					validPack = true;
				}
				if (selection.equals("Just download the zip file.")) {
					Driver.setSelectedOption(2);
					button.setText("Download");
					validPack = true;
				}
				if (selection.equals("Update.")) {
					Driver.updateTime = true;
					Driver.setSelectedOption(3);
					button.setText("Download");
					validPack = true;
				}

				if (selection.equals("Other Features.")) {
					Driver.setSelectedOption(4);
					button.setText("Next");
					validPack = true;
				}
				if (selection.equals("Get Resource Pack.")) {
					Driver.setSelectedOption(5);
					button.setText("Next");
					validPack = true;
				}
				if (validPack == false) {
					System.out.println("\n Something went terribly wrong.\n");
					errors.setText("Uh...Notify the developer.");
				}
			}
		};

		ActionListener buttonEvent = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int op = Driver.getSelectedOption();
				if (op == 1) {
					System.out.println(" Downloading modpack. Please wait.");
					modpackOne.setEnabled(false);
					downloadOption.setEnabled(false);
					updateOption.setEnabled(false);
					otherOptions.setEnabled(false);
					resourceOption.setEnabled(false);

					try {
						URL modpackOneLink = new URL(
								"https://aubreys-storage.s3.us-east-2.amazonaws.com/1.7.10/Modpack.zip");
						Downloader.Download(modpackOneLink, "Modpack.zip", 0);
					} catch (MalformedURLException f) {
						errors.setText("Fuck you Java.");
						System.out.println("Shaymin");
					}
					
					// installOptions.verifyInstall();
				}

				if (op == 2) {
					modpackOne.setEnabled(false);
					downloadOption.setEnabled(false);
					updateOption.setEnabled(false);
					otherOptions.setEnabled(false);
					resourceOption.setEnabled(false);
					try {
						URL modpackTwoLink = new URL(
								"https://aubreys-storage.s3.us-east-2.amazonaws.com/1.7.10/Modpack.zip");
						packDownloadOnly = true;
						Downloader.Download(modpackTwoLink, "Modpack.zip", 0);
					} catch (MalformedURLException g) {
						errors.setText("Fuck you Java.");
						System.out.println("Glameow");
					}
				}

				if (op == 3) {
					modpackOne.setEnabled(false);
					downloadOption.setEnabled(false);
					updateOption.setEnabled(false);
					otherOptions.setEnabled(false);
					resourceOption.setEnabled(false);
					Updater.updater();
				}
				if (op == 4) {
					modpackOne.setEnabled(false);
					downloadOption.setEnabled(false);
					updateOption.setEnabled(false);
					otherOptions.setEnabled(false);
					resourceOption.setEnabled(false);
					optionsGUI.otherOptionsGUI();
					Install.featuresUsed = true;
				}
				if (op == 5) {
					modpackOne.setEnabled(false);
					downloadOption.setEnabled(false);
					updateOption.setEnabled(false);
					otherOptions.setEnabled(false);
					resourceOption.setEnabled(false);
					resourcePacks.packGUI();
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
		resourceOption.setFont(pretty);

		frame.setTitle("Modpack Installer by Aubrey");

		// x axis, y axis, width, height
		scroll.setBounds(30, 20, 400, 200);

		modpackOne.setBounds(120, 235, 200, 15);
		modpackPanel.setBounds(115, 230, 250, 25);

		downloadOption.setBounds(120, 270, 200, 15);
		downloadPanel.setBounds(115, 265, 250, 25);

		updateOption.setBounds(120, 305, 200, 15);
		updatePanel.setBounds(115, 300, 250, 25);

		resourceOption.setBounds(120, 340, 200, 15);
		resourcePanel.setBounds(115, 335, 250, 25);

		otherOptions.setBounds(120, 375, 200, 15);
		optionsPanel.setBounds(115, 370, 250, 25);

		progress.setBounds(25, 475, 275, 25); // X, Y, Width, Height
		installerVersion.setBounds(195, 510, 100, 20); // 190
		button.setBounds(325, 475, 100, 25);

		errors.setBounds(75, 440, 350, 20);
		errorsLabel.setBounds(25, 440, 50, 20);

		// Radio Button Colour
		modpackOne.setBackground(rbc);
		downloadOption.setBackground(rbc);
		otherOptions.setBackground(rbc);
		updateOption.setBackground(rbc);
		resourceOption.setBackground(rbc);

		modpackPanel.add(modpackOne);
		downloadPanel.add(downloadOption);
		optionsPanel.add(otherOptions);
		updatePanel.add(updateOption);

		consoleOutput.setEditable(false);
		errors.setEditable(false);

		System.out.println(" Welcome to the installer!");
		System.out.println(" Please report any issues or bugs to Aubrey #2376. ");
		System.out.println(" Select an option from below to continue.\n");

		modpackOne.addActionListener(radioButtonEvent);
		downloadOption.addActionListener(radioButtonEvent);
		updateOption.addActionListener(radioButtonEvent);
		extractOption.addActionListener(radioButtonEvent);
		otherOptions.addActionListener(radioButtonEvent);
		resourceOption.addActionListener(radioButtonEvent);
		button.addActionListener(buttonEvent);

		ImageIcon background = new ImageIcon("resources" + q + "Background.png");
		Image bg = background.getImage();
		Image bgImg = bg.getScaledInstance(480, 575, Image.SCALE_SMOOTH);
		background = new ImageIcon(bgImg);
		JLabel backgroundImage = new JLabel(background);
		backgroundImage.setBounds(0, 0, 480, 575);

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

		frame.add(installerVersion);

		frame.add(resourceOption);
		frame.add(resourcePanel);

		frame.add(backgroundImage);

		frame.setSize(480, 575);
		frame.setResizable(false);

		frame.setLayout(null);// using no layout managers
		frame.setVisible(true);// making the frame visible
	}
}

import GUI.RoundedPanel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/*
 * Let's be honest, this class does document itself, for the most part.
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
	static JButton errorLookup = new JButton("!!!");
	static String installerVersionValue = "1.0";

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

		JLabel installerVersion = new JLabel("Version " + installerVersionValue);

		consoleOutput.setLineWrap(true);

		frame.getContentPane().add(scroll);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		progress.setMaximum(100000);

		scroll.setBorder(new LineBorder(Color.black, 1, true));

		Color rbc = new Color(220, 255, 255); // Hex value: dcffff
		Color lightRed = new Color(255, 129, 127);

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
				Boolean validPack = null;
				switch (selection) {
				case "Do it for me.":
					validPack = true;
					Driver.setSelectedOption(1);
					button.setText("Download");
					break;
				case "Just download the zip file.":
					Driver.setSelectedOption(2);
					button.setText("Download");
					validPack = true;
					break;
				case "Update.":
					Driver.updateTime = true;
					Driver.setSelectedOption(3);
					button.setText("Download");
					validPack = true;
					break;
				case "Other Features.":
					Driver.setSelectedOption(4);
					button.setText("Next");
					validPack = true;
					break;
				case "Get Resource Pack.":
					Driver.setSelectedOption(5);
					button.setText("Next");
					validPack = true;
					break;
				default:
					break;
				}
				if (!validPack) {
					errors.setText(" Garchomp");
					Errors.init();
				}
			}
		};
		ActionListener buttonEvent = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int op = Driver.getSelectedOption();
				switch (op) {
				case 1:
					case 2:
						radioSet(modpackOne, downloadOption, updateOption, otherOptions, resourceOption);
					Json.readLists();
					//beginDownload(button);
					break;
					case 3:
					radioSet(modpackOne, downloadOption, updateOption, otherOptions, resourceOption);
					Json.readLists();
					Updater.updater();
					break;
				case 4:
					radioSet(modpackOne, downloadOption, updateOption, otherOptions, resourceOption);
					Json.readLists();
					optionsGUI.otherOptionsGUI();
					Install.featuresUsed = true;
					break;
				case 5:
					radioSet(modpackOne, downloadOption, updateOption, otherOptions, resourceOption);
					Json.readLists();
					resourcePacks.packGUI();
					break;
				default:
					break;
				}
			}
		};

		ActionListener errorsEvent = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(" Opening errors dilogue...");
				Errors.makeGUI();
			}
		};

		try {
			pretty = Font.createFont(Font.TRUETYPE_FONT, new File("resources" + q + "Font.ttf")).deriveFont(16f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("resources" + q + "Font.ttf")));
		} catch (IOException ioe) {
			// Oh look. A wild error in its habitat.
		} catch (FontFormatException ffe) {
			GUI.errors.setText("Uxie");
			Errors.init();
		}

/*		frame.setFont(pretty);
		consoleOutput.setFont(pretty);
		modpackOne.setFont(pretty);
		downloadOption.setFont(pretty);
		otherOptions.setFont(pretty);
		updateOption.setFont(pretty);
		errors.setFont(pretty);
		errorsLabel.setFont(pretty);
		button.setFont(pretty);
		installerVersion.setFont(pretty);
		resourceOption.setFont(pretty);*/

		errorLookup.setBackground(lightRed);
		frame.setTitle("Modpack Installer by Aubrey");

		errorLookup.setBounds(415, 300, 50, 50); // x axis, y axis, width, height
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
		try {
			Updater.installerUpdateCheck();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(" Please report any issues or bugs to Aubrey Jane #7563.");
		System.out.println(" Select an option from below to continue.\n");

		modpackOne.addActionListener(radioButtonEvent);
		downloadOption.addActionListener(radioButtonEvent);
		updateOption.addActionListener(radioButtonEvent);
		extractOption.addActionListener(radioButtonEvent);
		otherOptions.addActionListener(radioButtonEvent);
		resourceOption.addActionListener(radioButtonEvent);
		button.addActionListener(buttonEvent);
		errorLookup.addActionListener(errorsEvent);

/*		ImageIcon background = new ImageIcon("resources" + q + "Background.png");
		Image bg = background.getImage();
		Image bgImg = bg.getScaledInstance(480, 575, Image.SCALE_SMOOTH);
		background = new ImageIcon(bgImg);
		JLabel backgroundImage = new JLabel(background);
		backgroundImage.setBounds(0, 0, 480, 575);*/

		frame.add(button);
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

		frame.add(errorLookup);
		errorLookup.setVisible(false);

		//frame.add(backgroundImage);

		frame.setSize(480, 575);
		frame.setResizable(false);

		frame.setLayout(null);// using no layout managers
		frame.setVisible(true);// making the frame visible
	}

	public static void radioSet(JRadioButton a, JRadioButton b, JRadioButton c, JRadioButton d, JRadioButton e) {
		a.setEnabled(false);
		b.setEnabled(false);
		c.setEnabled(false);
		d.setEnabled(false);
		e.setEnabled(false);
	}

/*	public static void beginDownload(JButton button) {
		try {
			URL modpackOneLink = new URL("https://aubreys-storage.s3.us-east-2.amazonaws.com/1.7.10/Modpack.zip");
			Downloader.Download(modpackOneLink, "Modpack.zip", 0);
			button.setEnabled(false);
		} catch (MalformedURLException mue) {
			errors.setText("Shaymin");
			System.out.println("Fuck you Java.");
			Errors.init();
		}
	}*/
}

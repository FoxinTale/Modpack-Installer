import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.hyperic.sigar.SigarException;

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

	public static void launchGUI() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("Modpack Installer by Aubrey");// creating instance of JFrame, and setting the title.
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		JRadioButton modpackOne = new JRadioButton("1.7.10 Modpack.");
		JRadioButton downloadOption = new JRadioButton("Download pack zip file.");
		// JRadioButton updateOption = new JRadioButton("Update.");
		JRadioButton updateOption = new JRadioButton("Update.");
		JRadioButton extractOption = new JRadioButton("");

		JButton button = new JButton("Next");// creating instance of JButton
		JLabel errorsLabel = new JLabel("Errors: ");
		ButtonGroup options = new ButtonGroup();

		consoleOutput.setLineWrap(true);

		frame.getContentPane().add(scroll);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		progress.setMaximum(100000);

		options.add(modpackOne);
		options.add(downloadOption);
		options.add(updateOption);
		options.add(extractOption);

		ActionListener radioButtonEvent = new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				AbstractButton absButton = (AbstractButton) ae.getSource();
				String selection = absButton.getText();
				if (Driver.installProgress == 0) {

					Boolean validPack = false;
					if (selection.equals("1.7.10 Modpack.")) {
						System.out.println("\n You have selected the 1.7.10 Modpack.");
						System.out.println("\n Please click next to continue.");
						modpackOne.setEnabled(false);
						downloadOption.setEnabled(false);
						updateOption.setEnabled(false);
						Driver.selectedOption = 1;
						Driver.installProgress = 1;
						validPack = true;
					}

					if (selection.equals("Download pack zip file.")) {
						modpackOne.setEnabled(false);
						downloadOption.setEnabled(false);
						updateOption.setEnabled(false);
						Driver.installProgress = 1;
						Driver.selectedOption = 2;
						validPack = true;
					}

					if (selection.equals("Update.")) {
						modpackOne.setEnabled(false);
						downloadOption.setEnabled(false);
						updateOption.setEnabled(false);
						extractOption.setEnabled(false);
						Driver.selectedOption = 3;
						Driver.installProgress = 1;
						validPack = true;
					}
					if (selection.equals("Install.")) {

						modpackOne.setEnabled(false);
						downloadOption.setEnabled(false);
						updateOption.setEnabled(false);
						extractOption.setEnabled(false);
						Driver.selectedOption = 4;
						Driver.installProgress = 1;
						validPack = true;
						Install.install();

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
						Downloader.Downloader(modpackOneLink);
					} catch (MalformedURLException f) {
						errors.setText("Fuck you Java.");
					}
				}

				if (Driver.installProgress == 1 && Driver.selectedOption == 2) {
					try {
						URL modpackTwoLink = new URL(
								"https://aubreys-storage.s3.us-east-2.amazonaws.com/1.7.10/Modpack.zip");
						packDownloadOnly = true;
						Downloader.Downloader(modpackTwoLink);
					} catch (MalformedURLException g) {
						errors.setText("Fuck you Java.");
					}
				}

				if (Driver.installProgress == 1 && Driver.selectedOption == 3) {
				
					try {
						installOptions.sliderGUI();
					} catch (SigarException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
				}
				if (Driver.installProgress == 1 && Driver.selectedOption == 4) {
					// System.out.println("\n Beginning Extraction...");
					// Extractor.Extractor();
					try {
						installOptions.sliderGUI();
					} catch (SigarException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		};

		button.setBounds(325, 400, 100, 25);// x axis, y axis, width, height
		scroll.setBounds(25, 25, 400, 200);
		errors.setBounds(75, 375, 350, 20);
		errorsLabel.setBounds(25, 375, 50, 20);
		modpackOne.setBounds(150, 250, 250, 15);
		downloadOption.setBounds(150, 275, 250, 15);
		updateOption.setBounds(150, 300, 250, 15);
		extractOption.setBounds(150, 325, 250, 15);
		progress.setBounds(25, 400, 275, 25);

		consoleOutput.setEditable(false);
		errors.setEditable(false);

		System.out.println(" Welcome to the installer!\n");
		System.out.println("\n Please select an option from below.\n");

		modpackOne.addActionListener(radioButtonEvent);
		downloadOption.addActionListener(radioButtonEvent);
		updateOption.addActionListener(radioButtonEvent);
		extractOption.addActionListener(radioButtonEvent);
		button.addActionListener(buttonEvent);

		Container c = frame.getContentPane();

		c.setBackground(new Color(255, 220, 220));

		frame.add(button);// adding button in JFrame
		frame.add(scroll);
		frame.add(progress);
		frame.add(errors);
		frame.add(errorsLabel);
		frame.add(modpackOne);
		frame.add(downloadOption);
		// frame.add(updateOption);
		// frame.add(extractOption);

		frame.setSize(480, 480);
		frame.setResizable(false);

		frame.setLayout(null);// using no layout managers
		frame.setVisible(true);// making the frame visible
	}
}

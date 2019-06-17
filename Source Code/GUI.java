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

public class GUI {
	static JProgressBar progress = new JProgressBar();
	static JEditorPane pane = new JEditorPane();
	static JTextArea consoleOutput = new JTextArea();
	static JScrollPane scroll = new JScrollPane(consoleOutput);
	static JTextField errors = new JTextField(" Nothing to report.");

	public static void launchGUI() {
		JFrame frame = new JFrame("Modpack Installer by Aubrey");// creating instance of JFrame
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		JTextField packLink = new JTextField("Custom pack link goes here. :)");
		JRadioButton modpackOne = new JRadioButton("1.7.10 Modpack.");
		JRadioButton modpackTwo = new JRadioButton("Another Modpack. :)");
		// JRadioButton modpackThree = new JRadioButton("Custom Modpack.");
		JRadioButton extractOption = new JRadioButton("Install.");

		JButton button = new JButton("Next");// creating instance of JButton
		JLabel errorsLabel = new JLabel("Errors: ");
		// JProgressBar progress = new JProgressBar();
		ButtonGroup modpacks = new ButtonGroup();

		consoleOutput.setLineWrap(true);
		// consoleOutput.setEditable(false);

		frame.getContentPane().add(scroll);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		progress.setMaximum(100000);

		packLink.setEditable(false);
		packLink.setVisible(false);
		modpacks.add(modpackOne);
		modpacks.add(modpackTwo);
		// modpacks.add(modpackThree);
		modpacks.add(extractOption);
		
		ActionListener radioButtonEvent = new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				AbstractButton absButton = (AbstractButton) ae.getSource();
				String selection = absButton.getText();
				if (Driver.installProgress == 0) {

					Boolean validPack = false;
					if (selection.equals("1.7.10 Modpack.")) {
						// updateConsole("\nYou have selected the 1.7.10 Modpack");
						// updateConsole("\nPlease click next.");
						// consoleOutput.append("\nYou have selected the 1.7.10 Modpack.");
						// consoleOutput.append("\nPlease click next to continue.");
						System.out.println("\n You have selected the 1.7.10 Modpack.");
						System.out.println("\n Please click next to continue.");
						modpackOne.setEnabled(false);
						modpackTwo.setEnabled(false);
						// modpackThree.setEnabled(false);
						Driver.selectedOption = 1;
						Driver.installProgress = 1;
						validPack = true;
					}

					if (selection.equals("Another Modpack. :)")) {
						// updateConsole("\nYou have selected a placeholder button.\n");
						// updateConsole("\nPlease choose another option.");
						// consoleOutput.append("\nYou have selected a placeholder button.\n");
						// consoleOutput.append("\nPlease choose another option.");
						System.out.println("\n You have selected a placeholder button.\n");
						System.out.println("\n Please choose another option.");
						// modpackOne.setEnabled(false);
						modpackTwo.setEnabled(false);
						// modpackThree.setEnabled(false);
						Driver.installProgress = 0;
						Driver.selectedOption = 0;
						validPack = true;
					}

					if (selection.equals("Custom Modpack.")) {
						// updateConsole("\nYou have selected a custom modpack.\n");
						// updateConsole("\nPlease put the link below, then click Next. \n");
						// consoleOutput.append("\nYou have selected a custom modpack.\n");
						// consoleOutput.append("\nPlease put the link below, then click Next. \n");
						System.out.println("\n You have selected a custom modpack.\n");
						System.out.println("\n Please put the link below, then click Next. \n");
						packLink.setText("");
						packLink.setEditable(true);
						packLink.setVisible(true);
						modpackOne.setEnabled(false);
						// modpackTwo.setEnabled(false);
						// modpackThree.setEnabled(false);
						Driver.selectedOption = 3;
						Driver.installProgress = 1;
						validPack = true;
					}
					if (selection.equals("Install.")) {

						modpackOne.setEnabled(false);
						modpackTwo.setEnabled(false);
						// modpackThree.setEnabled(false);
						extractOption.setEnabled(false);
						Driver.selectedOption = 4;
						Driver.installProgress = 1;
						validPack = true;
						Install.install();

					}
					if (validPack == false) {
						// updateConsole("\n\tSomething went terribly wrong.\n");
						// consoleOutput.append("\n\tSomething went terribly wrong.\n");
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

				if (Driver.installProgress == 1 && Driver.selectedOption == 0) {
					try {
						URL modpackTwoLink = new URL(
								"https://aubreys-storage.s3.us-east-2.amazonaws.com/1.7.10/Modpack.zip");
						Downloader.Downloader(modpackTwoLink);
					} catch (MalformedURLException g) {
						errors.setText("Fuck you Java.");
					}
				}

				if (Driver.installProgress == 1 && Driver.selectedOption == 2) {
					String link = packLink.getText();
					if (Driver.validURL(link)) {
						try {
							errors.setText("Nothing to report.");
							URL modpackThreeLink = new URL(link);
							Downloader.Downloader(modpackThreeLink);
						} catch (MalformedURLException m) {
							errors.setText("That isn't a valid link");
						}
					}
					if (!Driver.validURL(link)) {
						errors.setText("That isn't a valid link");
					}
				}
				if (Driver.installProgress == 1 && Driver.selectedOption == 4) {
					System.out.println("\n Beginning Extraction...");
					// Extractor.Extractor();
				}
			}
		};

		button.setBounds(325, 400, 100, 25);// x axis, y axis, width, height
		// consoleOutput.setBounds(25, 25, 400, 200);
		scroll.setBounds(25, 25, 400, 200);
		errors.setBounds(75, 375, 350, 20);
		errorsLabel.setBounds(25, 375, 50, 20);
		modpackOne.setBounds(150, 250, 150, 15);
		modpackTwo.setBounds(150, 275, 150, 15);
		// modpackThree.setBounds(150, 300, 250, 15);
		// extractOption.setBounds(150, 325, 250, 15);
		extractOption.setBounds(150, 300, 250, 15);
		progress.setBounds(25, 400, 275, 25);
		packLink.setBounds(25, 350, 400, 20);

		consoleOutput.setEditable(false);
		errors.setEditable(false);

		// consoleOutput.append("Welcome to the installer!\n\n");
		// consoleOutput.append("Please select an option from below.\n");
		System.out.println(" Welcome to the installer!\n");
		System.out.println("\n Please select an option from below.\n");

		modpackOne.addActionListener(radioButtonEvent);
		modpackTwo.addActionListener(radioButtonEvent);
		// modpackThree.addActionListener(radioButtonEvent);
		extractOption.addActionListener(radioButtonEvent);
		button.addActionListener(buttonEvent);
		
		
		Container c = frame.getContentPane();
		
		c.setBackground(new Color(255, 220, 220));

		frame.add(button);// adding button in JFrame
		// frame.add(consoleOutput);
		frame.add(scroll);
		frame.add(packLink);
		frame.add(progress);
		frame.add(errors);
		frame.add(errorsLabel);
		frame.add(modpackOne);
		frame.add(modpackTwo);
		// frame.add(modpackThree);
		// frame.add(extractOption);
		// frame.setBackground(pastelPink);

		frame.setSize(480, 480);
		frame.setResizable(false);

		frame.setLayout(null);// using no layout managers
		frame.setVisible(true);// making the frame visible

	}

}

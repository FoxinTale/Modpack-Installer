import GUI.RoundedPanel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Let's be honest, this class does document itself, for the most part.
 */
public class GUI {
    static JProgressBar progress = new JProgressBar();
    static JTextArea consoleOutput = new JTextArea();
    static JScrollPane scroll = new JScrollPane(consoleOutput);
    static JTextField errors = new JTextField(Strings.installerErrorsDefault);
    static JButton errorLookup = new JButton("!!!");
    static String installerVersionValue = "1.0";

    public static void launchGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JRadioButton modpackOne = new JRadioButton(Strings.installerOptionOne);
        JRadioButton downloadOption = new JRadioButton(Strings.installerOptionTwo);
        JRadioButton otherOptions = new JRadioButton(Strings.installerOptionThree);
        JRadioButton resourceOption = new JRadioButton(Strings.installerOptionFour);
        JButton button = new JButton("...");
        JLabel errorsLabel = new JLabel(Strings.installerErrorsLabelText);
        ButtonGroup options = new ButtonGroup();

        JPanel lightRedPanel = new JPanel();
        JPanel lightOrangePanel = new JPanel();
        JPanel lightYellowPanel = new JPanel();
        JPanel lightGreenPanel = new JPanel();
        JPanel lightBluePanel = new JPanel();
        JPanel lightPurplePanel = new JPanel();
        JPanel lightVioletPanel = new JPanel();

        JLabel installerVersion = new JLabel(Strings.installerVersionText+ installerVersionValue);
        consoleOutput.setLineWrap(true);
        frame.getContentPane().add(scroll);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        progress.setMaximum(100000);
        scroll.setBorder(new LineBorder(Color.black, 1, true));

        Color rbc = new Color(220, 255, 255); // Hex value: dcffff

        options.add(modpackOne);
        options.add(downloadOption);
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
                        button.setText(Strings.downloadText);
                        break;
                    case "Just download the zip file.":
                        Driver.setSelectedOption(2);
                        button.setText(Strings.downloadText);
                        validPack = true;
                        break;
                    case "Update.":
                        Driver.updateTime = true;
                        Driver.setSelectedOption(3);
                        button.setText(Strings.downloadText);
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
                        radioSet(modpackOne, downloadOption, otherOptions, resourceOption);
                        Json.readLists();
                        //beginDownload(button);
                        break;
                    case 3:
                        radioSet(modpackOne, downloadOption, otherOptions, resourceOption);
                        Json.readLists();
                        Updater.updater();
                        break;
                    case 4:
                        radioSet(modpackOne, downloadOption, otherOptions, resourceOption);
                        Json.readLists();
                        optionsGUI.otherOptionsGUI();
                        Install.featuresUsed = true;
                        break;
                    case 5:
                        radioSet(modpackOne, downloadOption, otherOptions, resourceOption);
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
        Color lightRed = new Color(255, 188, 188);
        Color lightOrange = new Color(255, 216, 158, 255);
        Color lightYellow = new Color(255, 255, 200);
        Color lightGreen = new Color(203, 234, 203);
        Color lightBlue = new Color(198, 215, 222);
        Color lightPurple = new Color(220, 189, 218);
        Color lightViolet = new Color(192, 148, 189);

        frame.setTitle(Strings.installerWindowTitle);

        //Setting background colours.
        modpackOne.setBackground(rbc);
        downloadOption.setBackground(rbc);
        otherOptions.setBackground(rbc);
        resourceOption.setBackground(rbc);
        lightRedPanel.setBackground(lightRed);
        lightOrangePanel.setBackground(lightOrange);
        lightYellowPanel.setBackground(lightYellow);
        lightGreenPanel.setBackground(lightGreen);
        lightBluePanel.setBackground(lightBlue);
        lightPurplePanel.setBackground(lightPurple);
        lightVioletPanel.setBackground(lightViolet);

        //Setting the location of each element.
        lightRedPanel.setBounds(0,0,70,575);
        lightOrangePanel.setBounds(70,0,70,575);
        lightYellowPanel.setBounds(140,0,70,575);
        lightGreenPanel.setBounds(210, 0, 70, 575);
        lightBluePanel.setBounds(280, 0, 70, 575);
        lightPurplePanel.setBounds(350, 0, 70, 575);
        lightVioletPanel.setBounds(420, 0, 70,575);
        errorLookup.setBounds(415, 300, 50, 50); // x axis, y axis, width, height
        scroll.setBounds(30, 20, 400, 200);
        modpackOne.setBounds(120, 235, 200, 15);
        modpackPanel.setBounds(115, 230, 250, 25);
        downloadOption.setBounds(120, 270, 200, 15);
        downloadPanel.setBounds(115, 265, 250, 25);
        updatePanel.setBounds(115, 300, 250, 25);
        resourceOption.setBounds(120, 305, 200, 15);
        resourcePanel.setBounds(115, 335, 250, 25);
        otherOptions.setBounds(120, 340, 200, 15);
        progress.setBounds(25, 475, 275, 25); // X, Y, Width, Height
        installerVersion.setBounds(195, 510, 100, 20); // 190
        button.setBounds(325, 475, 100, 25);
        errors.setBounds(75, 440, 350, 20);
        errorsLabel.setBounds(25, 440, 50, 20);

        modpackPanel.add(modpackOne);
        downloadPanel.add(downloadOption);
        optionsPanel.add(otherOptions);

        consoleOutput.setEditable(false);
        errors.setEditable(false);

        System.out.println(Strings.installerWelcome);
        /*Updater.checkAPIForUpdate(Driver.installerUpdateLink, installerVersionValue, Strings.installerUpdateMessage,
                Strings.installerUpdateTitle, Strings.installerUpToDate, 0);*/
        System.out.println(Strings.installerBugReport);
        System.out.println(Strings.installerOptions);

        modpackOne.addActionListener(radioButtonEvent);
        downloadOption.addActionListener(radioButtonEvent);
        otherOptions.addActionListener(radioButtonEvent);
        resourceOption.addActionListener(radioButtonEvent);
        button.addActionListener(buttonEvent);
        errorLookup.addActionListener(errorsEvent);

        // Adding all the elements to the frame.
        frame.add(button);
        frame.add(scroll);
        frame.add(progress);
        frame.add(errors);
        frame.add(errorsLabel);
        frame.add(modpackOne);
        frame.add(downloadOption);
        frame.add(otherOptions);
        frame.add(modpackPanel);
        frame.add(downloadPanel);
        frame.add(optionsPanel);
        frame.add(updatePanel);
        frame.add(installerVersion);
        frame.add(resourceOption);
        frame.add(resourcePanel);
        frame.add(errorLookup);
        frame.add(lightRedPanel);
        frame.add(lightOrangePanel);
        frame.add(lightYellowPanel);
        frame.add(lightGreenPanel);
        frame.add(lightBluePanel);
        frame.add(lightPurplePanel);
        frame.add(lightVioletPanel);

        errorLookup.setVisible(false);
        frame.setSize(480, 575);
        frame.setResizable(false);

        frame.setLayout(null);// using no layout managers
        frame.setVisible(true);// making the frame visible
    }


    public static void radioSet(JRadioButton a, JRadioButton b, JRadioButton c, JRadioButton d) {
        a.setEnabled(false);
        b.setEnabled(false);
        c.setEnabled(false);
        d.setEnabled(false);
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

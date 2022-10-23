import GUI.RoundedPanel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

// Let's be honest, this class does document itself, for the most part.

public class GUI {
    static JProgressBar progress = new JProgressBar();
    static JTextArea consoleOutput = new JTextArea();
    static JScrollPane scroll = new JScrollPane(consoleOutput);
    static JTextField errorsBox = new JTextField(Strings.installerErrorsDefault);
    static String installerVersionValue = "0.0";

    public static void launchGUI() {

        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JRadioButton modpackOne = new JRadioButton(Strings.installerOptionOne);
        JRadioButton downloadOption = new JRadioButton(Strings.installerOptionTwo);
        JButton button = new JButton("...");
        JLabel errorsLabel = new JLabel(Strings.installerErrorsLabelText);
        ButtonGroup options = new ButtonGroup();


        JLabel installerVersion = new JLabel(Strings.installerVersionText+ installerVersionValue);
        consoleOutput.setLineWrap(true);
        frame.getContentPane().add(scroll);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        progress.setMaximum(100000);
        scroll.setBorder(new LineBorder(Color.black, 1, true));

        Color rbc = new Color(220, 255, 255); // Hex value: dcffff

        options.add(modpackOne);
        options.add(downloadOption);


        JPanel modpackPanel = new RoundedPanel(10, rbc);
        JPanel downloadPanel = new RoundedPanel(10, rbc);
        JPanel updatePanel = new RoundedPanel(10, rbc);

        ActionListener radioButtonEvent = ae -> {
            AbstractButton absButton = (AbstractButton) ae.getSource();
            String selection = absButton.getText();
            Boolean validPack = false;
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
                default:
                    break;
            }
            if (!validPack) {
                errorsBox.setText(" Garchomp");
                Errors.garchomp();
            }
        };

        ActionListener buttonEvent = e -> {
            int op = Driver.getSelectedOption();
            switch (op) {
                case 1: // Download, extract and install the pack.
                    beginDownload(button);
                    radioSet(modpackOne, downloadOption);
                    break;
                case 2: //Just download the zip files.
                    radioSet(modpackOne, downloadOption);
                    break;
                default:
                    break;
            }
        };

        frame.setTitle(Strings.installerWindowTitle);

        //Setting background colours.
        modpackOne.setBackground(rbc);
        downloadOption.setBackground(rbc);

        //Setting the location of each element.

        scroll.setBounds(30, 20, 400, 200);
        modpackOne.setBounds(120, 235, 200, 15);
        modpackPanel.setBounds(115, 230, 250, 25);
        downloadOption.setBounds(120, 270, 200, 15);
        downloadPanel.setBounds(115, 265, 250, 25);
        updatePanel.setBounds(115, 300, 250, 25);
        progress.setBounds(25, 475, 275, 25); // X, Y, Width, Height
        installerVersion.setBounds(195, 510, 100, 20); // 190
        button.setBounds(325, 475, 100, 25);
        errorsBox.setBounds(75, 440, 350, 20);
        errorsLabel.setBounds(25, 440, 50, 20);

        modpackPanel.add(modpackOne);
        downloadPanel.add(downloadOption);
        consoleOutput.setEditable(false);
        errorsBox.setEditable(false);

        System.out.println(Strings.installerWelcome);
       // Updater.getFileUpdate(Driver.installerUpdateLink);
        //, installerVersionValue, Strings.installerUpdateMessage,
        //                Strings.installerUpdateTitle, Strings.installerUpToDate, 0
        System.out.println(Strings.installerBugReport);
        System.out.println(Strings.installerOptions);

        modpackOne.addActionListener(radioButtonEvent);
        downloadOption.addActionListener(radioButtonEvent);
        button.addActionListener(buttonEvent);


        // Adding all the elements to the frame.
        frame.add(button);
        frame.add(scroll);
        frame.add(progress);
        frame.add(errorsBox);
        frame.add(errorsLabel);
        frame.add(modpackOne);
        frame.add(downloadOption);
        frame.add(modpackPanel);
        frame.add(downloadPanel);
        frame.add(updatePanel);
        frame.add(installerVersion);

        frame.setSize(480, 575);
        frame.setResizable(false);
        frame.setLayout(null);// using no layout managers
        frame.setVisible(true);// making the frame visible
    }


    public static void radioSet(JRadioButton a, JRadioButton b) {
        a.setEnabled(false);
        b.setEnabled(false);
    }

public static void beginDownload(JButton button) {
		try {
            Updater.getFileUpdate(new URL(Common.modpackPartOneLink), 0);
			//Downloader.Download(fileLink, "Modpack.zip", 0);
			button.setEnabled(false);
		} catch (MalformedURLException mue) {
			errorOccured("Shaymin");
            Errors.shaymin();
			System.out.println("Fuck you Java.");
		}
	}



    public static void errorOccured(String errorType){
        errorsBox.setText(errorType);
    }
}

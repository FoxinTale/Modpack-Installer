import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.WindowConstants;

/*
 * This whole section is bad code, but it works. 
 * You know it, I know it. But again, it works.
 * There are very likely better ways to do this.
 *  
 */
public class UpdateGUI extends Updater {
	static int number = 1;
	static String endTag;
	static int updateOption = 0;
	static boolean validUpdate = false;
	static String category;
	static String folderLoc;

	public static void partOne() {

		JFrame panelOne = new JFrame("Updates!");
		panelOne.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		JRadioButton event = new JRadioButton("Event Update");
		JRadioButton versionOne = new JRadioButton("Version 1.1.x");
		JRadioButton versionTwo = new JRadioButton("Version 1.2.x");
		JRadioButton versionThree = new JRadioButton("Version 1.3.x");
		JRadioButton versionFour = new JRadioButton("Version 1.4.x");
		JRadioButton versionFive = new JRadioButton("Version 1.5.x");
		JRadioButton versionSix = new JRadioButton("Version 1.6.x");
		JRadioButton versionSeven = new JRadioButton("Version 1.7.x");
		JRadioButton versionEight = new JRadioButton("Version 1.8.x");
		JRadioButton versionNine = new JRadioButton("Version 1.9.x");
		JRadioButton versionTen = new JRadioButton("Version 2.0.x");

		ButtonGroup options = new ButtonGroup();

		options.add(event);
		options.add(versionOne);
		options.add(versionTwo);
		options.add(versionThree);
		options.add(versionFour);
		options.add(versionFive);
		options.add(versionSix);
		options.add(versionSeven);
		options.add(versionEight);
		options.add(versionNine);
		options.add(versionTen);
		int width = 100;
		int height = 20;
		int x = 25;
		int x2 = 130;

		ActionListener updateSelectionEvent = new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				AbstractButton absButton = (AbstractButton) ae.getSource();
				String selection = absButton.getText();
				if (selection.equals("Event Update")) {
					validUpdate = true;
					updateOption = 1;
					disableAll(event, versionOne, versionTwo, versionThree, versionFour, versionFive, versionSix,
							versionSeven, versionEight);
				}

				if (selection.equals("Version 1.1.x")) {
					validUpdate = true;
					updateOption = 2;
					number = 1;
					category = "1.1.x";
					disableAll(event, versionOne, versionTwo, versionThree, versionFour, versionFive, versionSix,
							versionSeven, versionEight);
				}

				if (selection.equals("Version 1.2.x")) {
					validUpdate = true;
					updateOption = 3;
					number = 2;
					category = "1.2.x";
					disableAll(event, versionOne, versionTwo, versionThree, versionFour, versionFive, versionSix,
							versionSeven, versionEight);
				}
				if (selection.equals("Version 1.3.x")) {
					validUpdate = true;
					updateOption = 4;
					number = 3;
					category = "1.3.x";
					disableAll(event, versionOne, versionTwo, versionThree, versionFour, versionFive, versionSix,
							versionSeven, versionEight);
				}
				if (selection.equals("Version 1.4.x")) {
					validUpdate = true;
					updateOption = 5;
					number = 4;
					category = "1.4.x";
					disableAll(event, versionOne, versionTwo, versionThree, versionFour, versionFive, versionSix,
							versionSeven, versionEight);
				}
				if (selection.equals("Version 1.5.x")) {
					validUpdate = true;
					updateOption = 6;
					number = 5;
					category = "1.5.x";
					disableAll(event, versionOne, versionTwo, versionThree, versionFour, versionFive, versionSix,
							versionSeven, versionEight);
				}
				if (selection.equals("Version 1.6.x")) {
					validUpdate = true;
					updateOption = 7;
					number = 6;
					category = "1.6.x";
					disableAll(event, versionOne, versionTwo, versionThree, versionFour, versionFive, versionSix,
							versionSeven, versionEight);
				}
				if (selection.equals("Version 1.7.x")) {
					validUpdate = true;
					updateOption = 8;
					number = 7;
					category = "1.7.x";
					disableAll(event, versionOne, versionTwo, versionThree, versionFour, versionFive, versionSix,
							versionSeven, versionEight);
				}
				if (selection.equals("Version 1.8.x")) {
					validUpdate = true;
					updateOption = 9;
					number = 8;
					category = "1.8.x";
					disableAll(event, versionOne, versionTwo, versionThree, versionFour, versionFive, versionSix,
							versionSeven, versionEight);
				}
				if (selection.equals("Version 1.9.x")) {
					validUpdate = true;
					updateOption = 10;
					number = 9;
					category = "1.9.x";
					disableAll(event, versionOne, versionTwo, versionThree, versionFour, versionFive, versionSix,
							versionSeven, versionEight);
				}
				if (selection.equals("Version 2.0.x")) {
					validUpdate = true;
					System.out.println("This isn't implemented yet...");
				}
				if (validUpdate == false) {
					System.out.println("\n Something went terribly wrong.");

				}
			}
		};

		ActionListener buttonEvent = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// String versionName = ;
				if (updateOption == 1) {

				}

				if (updateOption == 2) {
					whichUpdate(one);
				}

				if (updateOption == 3) {
					whichUpdate(two);
				}

				if (updateOption == 4) {
					whichUpdate(three);
				}

				if (updateOption == 5) {
					whichUpdate(four);
				}

				if (updateOption == 6) {
					whichUpdate(five);
				}

				if (updateOption == 7) {
					whichUpdate(six);
				}

				if (updateOption == 8) {
					whichUpdate(seven);
				}

				if (updateOption == 9) {
					whichUpdate(eight);
				}

				if (updateOption == 10) {
					whichUpdate(nine);
				}

			}

		};

		JButton button = new JButton("Next");
		button.addActionListener(buttonEvent);

		event.addActionListener(updateSelectionEvent);
		versionOne.addActionListener(updateSelectionEvent);
		versionTwo.addActionListener(updateSelectionEvent);
		versionThree.addActionListener(updateSelectionEvent);
		versionFour.addActionListener(updateSelectionEvent);
		versionFive.addActionListener(updateSelectionEvent);
		versionSix.addActionListener(updateSelectionEvent);
		versionSeven.addActionListener(updateSelectionEvent);
		versionEight.addActionListener(updateSelectionEvent);
		versionNine.addActionListener(updateSelectionEvent);
		versionTen.addActionListener(updateSelectionEvent);

		event.setBounds(x, 25, width, height);

		versionOne.setBounds(x, 50, width, height);
		versionTwo.setBounds(x, 75, width, height);
		versionThree.setBounds(x, 100, width, height);
		versionFour.setBounds(x, 125, width, height);
		versionFive.setBounds(x, 150, width, height);

		versionSix.setBounds(x2, 50, width, height);
		versionSeven.setBounds(x2, 75, width, height);
		versionEight.setBounds(x2, 100, width, height);
		versionNine.setBounds(x2, 125, width, height);
		versionTen.setBounds(x2, 150, width, height);

		button.setBounds(x, 200, 150, 30);

		optionVisibility(event, versions, "Event");
		optionVisibility(versionOne, versions, "1.1.x");
		optionVisibility(versionTwo, versions, "1.2.x");
		optionVisibility(versionThree, versions, "1.3.x");
		optionVisibility(versionFour, versions, "1.4.x");
		optionVisibility(versionFive, versions, "1.5.x");
		optionVisibility(versionSix, versions, "1.6.x");
		optionVisibility(versionSeven, versions, "1.7.x");
		optionVisibility(versionEight, versions, "1.8.x");
		optionVisibility(versionNine, versions, "1.9.x");
		optionVisibility(versionTen, versions, "2.0.x");

		panelOne.add(event);
		panelOne.add(versionOne);
		panelOne.add(versionTwo);
		panelOne.add(versionThree);
		panelOne.add(versionFour);
		panelOne.add(versionFive);
		panelOne.add(versionSix);
		panelOne.add(versionSeven);
		panelOne.add(versionEight);
		panelOne.add(versionNine);
		panelOne.add(versionTen);
		panelOne.add(button);

		panelOne.setSize(320, 320);
		panelOne.setLayout(null);
		panelOne.setResizable(false);
		panelOne.setVisible(true);

	}

	static Boolean validOption = false;

	public static void whichUpdate(ArrayList<String> list) {
		String versionName = "Version 1." + number;
		JRadioButton baseUpdate = new JRadioButton(versionName + ".0");
		JRadioButton oneUpdate = new JRadioButton(versionName + ".1");
		JRadioButton twoUpdate = new JRadioButton(versionName + ".2");
		JRadioButton threeUpdate = new JRadioButton(versionName + ".3");
		JRadioButton fourUpdate = new JRadioButton(versionName + ".4");
		JRadioButton fiveUpdate = new JRadioButton(versionName + ".5");
		JRadioButton sixUpdate = new JRadioButton(versionName + ".6");
		JRadioButton sevenUpdate = new JRadioButton(versionName + ".7");
		JRadioButton eightUpdate = new JRadioButton(versionName + ".8");
		JRadioButton nineUpdate = new JRadioButton(versionName + ".9");

		JFrame updatePanel = new JFrame("Versions");
		updatePanel.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		String versionCheck = "1." + number;

		ButtonGroup options = new ButtonGroup();

		options.add(baseUpdate);
		options.add(oneUpdate);
		options.add(twoUpdate);
		options.add(threeUpdate);
		options.add(fourUpdate);
		options.add(fiveUpdate);
		options.add(sixUpdate);
		options.add(sevenUpdate);
		options.add(eightUpdate);
		options.add(nineUpdate);

		int width = 100;
		int height = 20;
		int x = 25;
		int x2 = 130;

		// String optionPicked;
		ActionListener versionSelectionEvent = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				AbstractButton absButton = (AbstractButton) ae.getSource();
				String selection = absButton.getText();
				if (selection.equals(versionName + ".0")) {
					validOption = true;
					endTag = ".0";
					disableAllTwo(baseUpdate, oneUpdate, twoUpdate, threeUpdate, fourUpdate, fiveUpdate, sixUpdate,
							sevenUpdate, eightUpdate);
				}

				if (selection.equals(versionName + ".1")) {
					validOption = true;
					endTag = ".1";
					disableAllTwo(baseUpdate, oneUpdate, twoUpdate, threeUpdate, fourUpdate, fiveUpdate, sixUpdate,
							sevenUpdate, eightUpdate);
				}

				if (selection.equals(versionName + ".2")) {
					validOption = true;
					endTag = ".2";
					disableAllTwo(baseUpdate, oneUpdate, twoUpdate, threeUpdate, fourUpdate, fiveUpdate, sixUpdate,
							sevenUpdate, eightUpdate);
				}

				if (selection.equals(versionName + ".3")) {
					validOption = true;
					endTag = ".3";
					disableAllTwo(baseUpdate, oneUpdate, twoUpdate, threeUpdate, fourUpdate, fiveUpdate, sixUpdate,
							sevenUpdate, eightUpdate);
				}

				if (selection.equals(versionName + ".4")) {
					validOption = true;
					endTag = ".4";
					disableAllTwo(baseUpdate, oneUpdate, twoUpdate, threeUpdate, fourUpdate, fiveUpdate, sixUpdate,
							sevenUpdate, eightUpdate);
				}

				if (selection.equals(versionName + ".5")) {
					validOption = true;
					endTag = ".5";
					disableAllTwo(baseUpdate, oneUpdate, twoUpdate, threeUpdate, fourUpdate, fiveUpdate, sixUpdate,
							sevenUpdate, eightUpdate);
				}

				if (selection.equals(versionName + ".6")) {
					validOption = true;
					endTag = ".6";
					disableAllTwo(baseUpdate, oneUpdate, twoUpdate, threeUpdate, fourUpdate, fiveUpdate, sixUpdate,
							sevenUpdate, eightUpdate);
				}

				if (selection.equals(versionName + ".7")) {
					validOption = true;
					endTag = ".7";
					disableAllTwo(baseUpdate, oneUpdate, twoUpdate, threeUpdate, fourUpdate, fiveUpdate, sixUpdate,
							sevenUpdate, eightUpdate);
				}

				if (selection.equals(versionName + ".8")) {
					validOption = true;
					endTag = ".8";
					disableAllTwo(baseUpdate, oneUpdate, twoUpdate, threeUpdate, fourUpdate, fiveUpdate, sixUpdate,
							sevenUpdate, eightUpdate);
				}

				if (selection.equals(versionName + ".9")) {
					validOption = true;
					endTag = ".9";
					disableAllTwo(baseUpdate, oneUpdate, twoUpdate, threeUpdate, fourUpdate, fiveUpdate, sixUpdate,
							sevenUpdate, eightUpdate);
				}
			}
		};

		ActionListener buttonEvent = new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				URL updateFile;
				try {
					setFolderLoc(versionCheck + endTag);
					setBaseLink(baseLink + "Updates/" + category + "/" + versionCheck + endTag + ".zip");
					updateFile = new URL(getBaseLink());
					Downloader.Downloader(updateFile);
				} catch (MalformedURLException e1) {
					GUI.errors.setText("Update link invalid.");
				}

			}

		};

		JButton button = new JButton("Next");

		baseUpdate.addActionListener(versionSelectionEvent);
		oneUpdate.addActionListener(versionSelectionEvent);
		twoUpdate.addActionListener(versionSelectionEvent);
		threeUpdate.addActionListener(versionSelectionEvent);
		fourUpdate.addActionListener(versionSelectionEvent);
		fiveUpdate.addActionListener(versionSelectionEvent);
		sixUpdate.addActionListener(versionSelectionEvent);
		sevenUpdate.addActionListener(versionSelectionEvent);
		eightUpdate.addActionListener(versionSelectionEvent);
		nineUpdate.addActionListener(versionSelectionEvent);

		button.addActionListener(buttonEvent);

		baseUpdate.setBounds(x, 50, width, height);
		oneUpdate.setBounds(x, 75, width, height);
		twoUpdate.setBounds(x, 100, width, height);
		threeUpdate.setBounds(x, 125, width, height);
		fourUpdate.setBounds(x, 150, width, height);

		fiveUpdate.setBounds(x2, 50, width, height);
		sixUpdate.setBounds(x2, 75, width, height);
		sevenUpdate.setBounds(x2, 100, width, height);
		eightUpdate.setBounds(x2, 125, width, height);
		nineUpdate.setBounds(x2, 150, width, height);

		button.setBounds(x, 200, 150, 30);

		optionVisibility(baseUpdate, list, versionCheck + ".0");
		optionVisibility(oneUpdate, list, versionCheck + ".1");
		optionVisibility(twoUpdate, list, versionCheck + ".2");
		optionVisibility(threeUpdate, list, versionCheck + ".3");
		optionVisibility(fourUpdate, list, versionCheck + ".4");
		optionVisibility(fiveUpdate, list, versionCheck + ".5");
		optionVisibility(sixUpdate, list, versionCheck + ".6");
		optionVisibility(sevenUpdate, list, versionCheck + ".7");
		optionVisibility(eightUpdate, list, versionCheck + ".8");
		optionVisibility(nineUpdate, list, versionCheck + ".9");

		updatePanel.add(baseUpdate);
		updatePanel.add(oneUpdate);
		updatePanel.add(twoUpdate);
		updatePanel.add(threeUpdate);
		updatePanel.add(fourUpdate);
		updatePanel.add(fiveUpdate);
		updatePanel.add(sixUpdate);
		updatePanel.add(sevenUpdate);
		updatePanel.add(eightUpdate);
		updatePanel.add(nineUpdate);
		updatePanel.add(button);

		updatePanel.setSize(320, 320);
		updatePanel.setResizable(false);

		updatePanel.setLayout(null);// using no layout managers
		updatePanel.setVisible(true);// making the frame visible
	}

	public static String getFolderLoc() {
		return folderLoc;
	}

	public static void setFolderLoc(String folderLoc) {
		UpdateGUI.folderLoc = folderLoc;
	}

	public static void disableAll(JRadioButton event, JRadioButton versionOne, JRadioButton versionTwo,
			JRadioButton versionThree, JRadioButton versionFour, JRadioButton versionFive, JRadioButton versionSix,
			JRadioButton versionSeven, JRadioButton versionEight) {
		event.setEnabled(false);
		versionOne.setEnabled(false);
		versionTwo.setEnabled(false);
		versionThree.setEnabled(false);
		versionFour.setEnabled(false);
		versionFive.setEnabled(false);
		versionSix.setEnabled(false);
		versionSeven.setEnabled(false);
		versionEight.setEnabled(false);

	}

	public static void disableAllTwo(JRadioButton baseUpdate, JRadioButton oneUpdate, JRadioButton twoUpdate,
			JRadioButton threeUpdate, JRadioButton fourUpdate, JRadioButton fiveUpdate, JRadioButton sixUpdate,
			JRadioButton sevenUpdate, JRadioButton eightUpdate) {
		baseUpdate.setEnabled(false);
		oneUpdate.setEnabled(false);
		twoUpdate.setEnabled(false);
		threeUpdate.setEnabled(false);
		fourUpdate.setEnabled(false);
		fiveUpdate.setEnabled(false);
		sixUpdate.setEnabled(false);
		sevenUpdate.setEnabled(false);
		eightUpdate.setEnabled(false);
	}

}

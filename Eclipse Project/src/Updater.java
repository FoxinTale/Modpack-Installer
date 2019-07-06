import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JRadioButton;

@SuppressWarnings("resource")
public class Updater {
	// A bunch of ArrayLists.
	static ArrayList<String> versions = new ArrayList<>();
	static ArrayList<String> one = new ArrayList<>();
	static ArrayList<String> two = new ArrayList<>();
	static ArrayList<String> three = new ArrayList<>();
	static ArrayList<String> four = new ArrayList<>();
	static ArrayList<String> five = new ArrayList<>();
	static ArrayList<String> six = new ArrayList<>();
	static ArrayList<String> seven = new ArrayList<>();
	static ArrayList<String> eight = new ArrayList<>();
	static ArrayList<String> nine = new ArrayList<>();
	static ArrayList<String> ten = new ArrayList<>();
	
	static String updateFile = Driver.getDownloadsLocation();
	static File updates = new File(updateFile + File.separator + "Updates.txt");
	static String baseLink = "https://aubreys-storage.s3.us-east-2.amazonaws.com/1.7.10/";

	//Oh look, more getters and setters.
	public static String getBaseLink() {
		return baseLink;
	}

	public static void setBaseLink(String baseLink) {
		Updater.baseLink = baseLink;
	}

	public static void printList(ArrayList<String> list) {
		//This prints an arrayList.. What else did you think it did?
		// I think this is not needed anymore and is left over from debugging and testing.
		// But it will stay here until I know for sure I will not need it.
		System.out.println(Arrays.toString(list.toArray()));
	}

	public static void readFile() {
		try {
			Scanner lines = new Scanner(updates);
			int count = 0;
			while (lines.hasNext() && count < 11) {
				String current = lines.nextLine();
				if (current.equals("-")) {
					count++;
					continue;
				}
				switch (count) {
				case 0:
					versions.add(current);
					break;
				case 1:
					one.add(current);
					break;
				case 2:
					two.add(current);
					break;
				case 3:
					three.add(current);
					break;
				case 4:
					four.add(current);
					break;
				case 5:
					five.add(current);
					break;
				case 6:
					six.add(current);
					break;
				case 7:
					seven.add(current);
					break;
				case 8:
					eight.add(current);
					break;
				case 9:
					nine.add(current);
					break;
				case 10:
					ten.add(current);
					break;

				}
			}
			UpdateGUI.partOne();
		} catch (IOException i) {
			GUI.errors.setText("Update info not found.");
			// The file wasn't found.
		}
	}

	public static void optionVisibility(JRadioButton button, ArrayList<String> list, String s) {
		if (list.contains(s)) {
			button.setVisible(true);
		}
		if (!list.contains(s)) {
			button.setVisible(false);
		}

	}

	public static void download() {
		//A simple download function for the text file that lists the updates.
		try {
			String url = "https://aubreys-storage.s3.us-east-2.amazonaws.com/1.7.10/Updates/Updates.txt";
			BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
			FileOutputStream fileOut = new FileOutputStream(updates);
			byte dataBuffer[] = new byte[1024];
			int bytesRead;
			while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
				fileOut.write(dataBuffer, 0, bytesRead);
			}
			readFile();

		} catch (IOException e) {
			System.out.println("Update File not found");
		}
	}

	public static void installUpdate() {
		//This needs a lot of work, but I've no idea what exactly to do.
		File updateFolder =new File(Driver.getDownloadsLocation() + UpdateGUI.getFolderLoc());
	}
}

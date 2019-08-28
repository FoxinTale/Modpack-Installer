package siteFileDeletion;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Driver {

	public static void main(String[] args) {
		// removeStuff();

	}

	public static void removeStuff() {
		File modsDirectory = new File("C:\\Users\\Alyan\\AppData\\Roaming\\.minecraft\\mods\\");
		ArrayList<String> removal = new ArrayList<>();
		String removalLink = "https://sites.google.com/view/aubreys-modpack-info/home/to-delete";
		websiteReader.siteReader(removalLink, false, 4, removal);
		File begone;
		if (removal.contains("None")) {
			System.out.println("Nothing to remove.");
		}

		for (int i = removal.size(); i > 0; i--) {
			begone = new File(modsDirectory.toString() + File.separator + removal.get(i - 1));
			begone.delete();
		}

	}

}

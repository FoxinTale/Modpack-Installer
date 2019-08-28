package siteFileDeletion;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class websiteReader {
	static ArrayList<String> modlist = new ArrayList<>();
	static ArrayList<String> fullList = new ArrayList<>();
	static ArrayList<String> toUpdate = new ArrayList<>();
	static ArrayList<String> toRemove = new ArrayList<>();
	static ArrayList<String> latest = new ArrayList<>();
	static ArrayList<String> checksums = new ArrayList<>();

	public static ArrayList<String> getChecksums() {
		return checksums;
	}

	public static void setChecksums(ArrayList<String> checksums) {
		websiteReader.checksums = checksums;
	}

	public static ArrayList<String> getModlist() {
		return modlist;
	}

	public static void setModlist(ArrayList<String> modlist) {
		websiteReader.modlist = modlist;
	}

	public static ArrayList<String> getFullList() {
		return fullList;
	}

	public static void setFullList(ArrayList<String> fullList) {
		websiteReader.fullList = fullList;
	}

	public static ArrayList<String> getToUpdate() {
		return toUpdate;
	}

	public static void setToUpdate(ArrayList<String> toUpdate) {
		websiteReader.toUpdate = toUpdate;
	}

	public static ArrayList<String> getToRemove() {
		return toRemove;
	}

	public static void setToRemove(ArrayList<String> toRemove) {
		websiteReader.toRemove = toRemove;
	}

	public static ArrayList<String> getLatest() {
		return latest;
	}

	public static void setLatest(ArrayList<String> latest) {
		websiteReader.latest = latest;
	}

	public static void siteReader(String siteLink, Boolean toPrint, int ops, ArrayList<String> list) { // or not to
																										// print

		try {
			Document doc = Jsoup.connect(siteLink).get();
			String title = doc.title();
			Jsoup.parse(title);

			StringBuilder fullText = new StringBuilder(doc.body().text());

			int listStart = fullText.indexOf("-----");

			fullText.delete(0, listStart + 5);
			int listEnd = fullText.indexOf("-----");
			int length;
			length = fullText.length();
			fullText.delete(listEnd, length);
// Total amount of arrays would be the Normal modlist, any updated mods, any mods to remove
			length = fullText.length();
			char[] data = new char[length];
			fullText.getChars(0, length, data, 0);
			String obj;
			StringBuilder s = new StringBuilder();

			for (int i = 0; i < data.length; i++) {

				if (data[i] == '/') {
					s.append(File.separator);
					data[i] = ' ';
				}
				s.append(data[i]);

				if (data[i] == '*') {
					data[i] = '\n';
					s.deleteCharAt(s.length() - 1);
					obj = s.toString();
					obj = obj.replaceAll("\\s+", "");
					list.add(obj);
					s.delete(0, s.length());
				}
			}
			if (toPrint == true) {
				Object[] array = list.toArray();
				for (int i = 0; i < array.length; i++) {
					System.out.println(array[i] + "\n");
				}
			}

			if (ops == 0) {
				// For getting the modlist
				setModlist(list);
			}
			if (ops == 1) {
				// For getting the file names
				setFullList(list);
			}
			if (ops == 2) {
				// For getting the version
				setLatest(list);
			}
			if (ops == 3) {
				setChecksums(list);
			}
			if (ops == 4) {
				setToRemove(list);
			}

		} catch (IOException e) {

		}
	}
}
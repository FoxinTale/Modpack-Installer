package siteRead;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Driver {

	public static void main(String[] args) {
		ArrayList<String> modlist = new ArrayList<>();
		ArrayList<String> fullList = new ArrayList<>();
		// ArrayList<String> toUpdate = new ArrayList<>();
		// ArrayList<String> toRemove = new ArrayList<>();
		ArrayList<String> latest = new ArrayList<>();

		// siteReader("https://sites.google.com/view/aubreys-modpack-info/home/modlist",
		// false, 0, modlist);
		// System.out.println();
		// System.out.println();
		// siteReader("https://sites.google.com/view/aubreys-modpack-info/home/modlist-full",
		// false, 0, fullList);

		siteReader("https://sites.google.com/view/aubreys-modpack-info/home/latest-version", true, 0, latest);

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
				s.append(data[i]);
				if (data[i] == '*') {
					data[i] = '\n';
					s.deleteCharAt(s.length() - 1);
					obj = s.toString();
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

		} catch (IOException e) {
			System.out.println("Fuckity");
		}
	}

}

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Json {
	static ArrayList<String> modlist = new ArrayList<>();
	static ArrayList<String> toRemove = new ArrayList<>();
	static ArrayList<String> checksums = new ArrayList<>();
	static String currentVersion;

	public static ArrayList<String> getModlist() {
		return modlist;
	}

	public static void setModlist(ArrayList<String> modlist) {
		Json.modlist = modlist;
	}

	public static ArrayList<String> getToRemove() {
		return toRemove;
	}

	public static void setToRemove(ArrayList<String> toRemove) {
		Json.toRemove = toRemove;
	}

	public static ArrayList<String> getChecksums() {
		return checksums;
	}

	public static void setChecksums(ArrayList<String> checksums) {
		Json.checksums = checksums;
	}

	public static String getCurrentVersion() {
		return currentVersion;
	}

	public static void setCurrentVersion(String currentVersion) {
		Json.currentVersion = currentVersion;
	}

	public static void readLists() {
		try {
			URL fileLink = new URL("https://raw.githubusercontent.com/FoxinTale/Modpack-Installer/master/data.json");
			JSONObject data = (JSONObject) new JSONTokener(fileLink.openStream()).nextValue();

			currentVersion = (String) data.get("currentVersion");
			setCurrentVersion(currentVersion);

			JSONArray checksumArray = (JSONArray) data.get("checksums");
			JSONArray modArray = (JSONArray) data.get("modList");

			jsonArrayRead(checksumArray, checksums, false, 0);
			jsonArrayRead(modArray, modlist, false, 1);

		} catch (FileNotFoundException e) {
			GUI.errors.setText("Zebstrika");
			Errors.init();
		} catch (IOException e) {
			GUI.errors.setText("Litwick");
			Errors.init();
		} catch (JSONException e) {
			GUI.errors.setText("Sylveon");
			Errors.init();
		}
	}

	public static void jsonArrayRead(JSONArray arr, ArrayList<String> list, Boolean toPrint, int op) {

		for (int i = 0; i < arr.length(); i++) {
			JSONObject item = null;
			StringBuilder s = new StringBuilder();
			try {
				item = arr.getJSONObject(i);
				int place = item.toString().indexOf(":");
				s.append(item);
				s.delete(0, place + 2);
				list.add(s.toString().replace('{', ' ').replace('}', ' ').replace('"', ' ').trim());
				s.delete(0, s.length() - 1);
			} catch (JSONException e) {
				GUI.errors.setText("Sylveon");
				Errors.init();
			}
		}

		if (toPrint) {
			Object[] array = list.toArray();
			for (int i = 0; i < array.length; i++) {
				System.out.println(array[i] + "\n");
			}
		}

		if (op == 0) {
			setChecksums(list);
		}
		if (op == 1) {
			setModlist(list);
		}
		if (op == 2) {
			setToRemove(list);
		}

	}
}

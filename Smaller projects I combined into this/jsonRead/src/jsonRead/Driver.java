package jsonRead;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Driver {

	static ArrayList<String> checksums = new ArrayList<>();
	static ArrayList<String> toRemove = new ArrayList<>();
	static String currentVersion;

	public static void main(String[] args) {
		System.setProperty("http.agent", "Netscape 1.0");
		readProfileData();
	}

	public static void readJsonFromURL() {
		try {
			URL fileLink = new URL("https://raw.githubusercontent.com/FoxinTale/Modpack-Installer/master/data.json");
			JSONObject data = (JSONObject) new JSONTokener(fileLink.openStream()).nextValue();
			currentVersion = (String) data.get("currentVersion");
			JSONArray checksumArray = (JSONArray) data.get("checksums");
			jsonArrayRead(checksumArray, checksums, true);
		} catch (FileNotFoundException e) {
			// GUI.errors.setText("Zebstrika");
		} catch (IOException e) {
			// GUI.errors.setText("Litwick");
		} catch (JSONException e) {
			// GUI.errors.setText("Sylveon");
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void readProfileData() {
		File launcherSettings = new File("/home/aubrey/Shared/launcher_profiles.json");
		if (launcherSettings.exists()) {
			try {
				Object fileData = new JSONParser().parse(new FileReader(launcherSettings));
				JSONObject launcherData = (JSONObject) fileData;
				Map profileData = ((Map) launcherData.get("profiles"));

				int mem = 6144; // This will be fed into the function once complete.

				for (Map.Entry pair : (Iterable<Map.Entry>) profileData.entrySet()) {
					if (pair.getKey().equals("Forge")) {
						Map forgeData = ((Map) profileData.get("Forge"));
						// Key = lastVersionId
						// Value = 1.7.10-Forge10.13.4.1614-1.7.10
						for (Map.Entry forgePair : (Iterable<Map.Entry>) forgeData.entrySet()) {
							Object versionId = forgePair.getKey();
							Object versionValue = forgePair.getValue();
							if (versionId.equals("lastVersionId")) {
								if (versionValue.equals("1.7.10-Forge10.13.4.1614-1.7.10")) {
									memAdjust(profileData, forgeData, mem);
								}
							}
						}
					}
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void jsonArrayRead(JSONArray arr, ArrayList<String> list, Boolean toPrint) {
		for (int i = 0; i < arr.length(); i++) {
			org.json.JSONObject item = null;
			StringBuilder s = new StringBuilder();
			try {
				item = arr.getJSONObject(i);
				int place = item.toString().indexOf(":");
				s.append(item);
				s.delete(0, place + 2);
				list.add(s.toString().replace('{', ' ').replace('}', ' ').replace('"', ' ').trim());
				s.delete(0, s.length() - 1);
			} catch (JSONException e) {
				// GUI.errors.setText("Sylveon");
			}
		}
		if (toPrint) {
			Object[] array = list.toArray();
			for (Object o : array) {
				System.out.println(o + "\n");
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void memAdjust(Map profileData, Map map, int mem) {
		Iterator<Map.Entry> argsItr = map.entrySet().iterator();
		while (argsItr.hasNext()) {
			Map.Entry argsPair = argsItr.next();
			Object argsId = argsPair.getKey();
			Object argsValue = argsPair.getValue();

			if (argsId.equals("javaArgs")) {
				String args = argsValue.toString();
				int posArg = args.indexOf("-Xmx");
				if (posArg == -1) {
					System.out.println("Not Found");
				}
				if (posArg > 0) {
					StringBuilder sb = new StringBuilder();
					sb.append(args);
					String values;
					values = sb.substring(posArg, posArg + 10);

					if (values.charAt(values.length() - 1) == 'M') {
						values = sb.substring(posArg, posArg + 11);
					}
					StringBuilder valueMod = new StringBuilder(values);
					int posOfX = values.indexOf('x');
					int posOfM = values.indexOf('M') + 1;
					valueMod.replace(posOfX + 1, posOfM, Integer.toString(mem) + 'M');
					int argsPos = posArg + posOfM;
					sb.replace(posArg, argsPos, valueMod.toString().trim());
					argsValue = sb.toString();
					launcherJsonWrite(argsValue.toString());
				}
			}
		}
	}

	// This is a very messy way of writing the new Json file, but the library I used
	// wrote things out of order and all over the place.

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void launcherJsonWrite(String newArgs) {
		try {
			File launcherInfo = new File("/home/aubrey/Shared/launcher_profiles.json");
			Object fileData = new JSONParser().parse(new FileReader(launcherInfo));
			JSONObject launcherData = (JSONObject) fileData;
			JSONObject newLauncher = new JSONObject();

			JSONObject settings = new JSONObject();
			JSONObject version = new JSONObject();
			Map profileData = ((Map) launcherData.get("profiles"));
			Map settingsData = ((Map) launcherData.get("settings"));
			settings.put("settings", settingsData);
			Long failCount = (Long) launcherData.get("analyticsFailCount");
			String token = (String) launcherData.get("analyticsToken");
			String clientToken = (String) launcherData.get("clientToken");

			Map launcherVersionMap = ((Map) launcherData.get("launcherVersion"));
			Map launcherVersion = new LinkedHashMap(3);
			Iterator<Map.Entry> lvmItr = launcherVersionMap.entrySet().iterator();
			while (lvmItr.hasNext()) {
				Map.Entry versionPair = lvmItr.next();
				launcherVersion.put(versionPair.getKey(), versionPair.getValue());
			}
			version.put("launcherVersion", launcherVersion);

			for (Map.Entry pair : (Iterable<Map.Entry>) profileData.entrySet()) {
				if (pair.getKey().equals("Forge")) {
					Map currentForgeData = ((Map) profileData.get("Forge"));
					Map forgeData = ((Map) profileData.get("Forge"));
					for (Map.Entry forgePair : (Iterable<Map.Entry>) forgeData.entrySet()) {
						Object versionId = forgePair.getKey();
						Object versionValue = forgePair.getValue();

						if (versionId.equals("lastVersionId")) {
							if (versionValue.equals("1.7.10-Forge10.13.4.1614-1.7.10")) {
								for (Map.Entry forgePair2 : (Iterable<Map.Entry>) forgeData.entrySet()) {
									Object modId = forgePair2.getKey();

									if (modId.equals("javaArgs")) {
										forgePair2.setValue(newArgs);
										forgeData.replace("javaArgs", forgePair2.getValue(), newArgs);
									}
								}
							}
						}
					}
					profileData.replace("Forge", currentForgeData, forgeData);
					newLauncher.put("profile", profileData);
				}
			}

			String json = "test.json";
			PrintWriter launcherJson = new PrintWriter(json);

			launcherJson.write("{" + "\n");
			launcherJson.write(" \t\"analyticsFailCount\"" + ": " + failCount + ",\n");
			launcherJson.write(" \t\"analyticsToken\"" + ": " + "\"" + token + "\",\n");
			launcherJson.write(" \t\"clientToken\"" + ": " + "\"" + clientToken + "\",\n");
			launcherJson.write(restructure(version, true, false));
			launcherJson.write(restructure(newLauncher, true, true));
			launcherJson.write(restructure(settings, false, false));
			launcherJson.write("\n}");

			launcherJson.flush();
			launcherJson.close();

			// Then delete the original, and replace it with this version.
			// Back up the original, of course.
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String restructure(JSONObject jo, Boolean addComma, boolean isProfile) {
		StringBuilder sb = new StringBuilder();
		sb.append(jo);
		sb.deleteCharAt(0);
		if (isProfile) {
			return profileRestructure(sb);
		}
		if (!isProfile) {
			int startBracket = sb.indexOf("{");

			sb.insert(startBracket + 1, "\n\t\t");
			for (int i = 1; i < sb.length(); i++) {
				if (sb.charAt(i - 1) == ',') {
					sb.insert(i, "\n\t\t");
				}
			}

			int endBracket = sb.indexOf("}");
			sb.deleteCharAt(endBracket);
			sb.insert(endBracket, "\n\t");

			if (addComma) {
				sb.insert(sb.length(), ",\n");
				sb.deleteCharAt(sb.length() - 1);
			}

			sb.insert(0, '\t');
		}
		return sb.toString();
	}

	public static String profileRestructure(StringBuilder sb) {

		int startBracket;
		startBracket = sb.indexOf("{");

		sb.insert(startBracket + 1, "\n\t");
		for (int i = 1; i < sb.length(); i++) {
			if (sb.charAt(i - 1) == ',') {
				sb.insert(i, "\n\t");
			}
		}
		int newStartBracket = sb.indexOf("{", startBracket + 2);
		sb.insert(newStartBracket + 1, "\n\t");
		for (int i = 1; i < sb.length(); i++) {
			if (sb.charAt(i - 1) == ',') {
				sb.insert(i, "\t\t");
			}
		}

		int endBracket = sb.length() - 1;
		sb.deleteCharAt(endBracket);
		sb.insert(endBracket - 2, "\n\t");
		sb.insert(endBracket + 1, "\n");
		sb.insert(endBracket + 3, ',');
		return sb.toString();
	}
}

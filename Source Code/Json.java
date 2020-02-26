import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Json {
	static ArrayList<String> modlist = new ArrayList<>();
	static ArrayList<String> toRemove = new ArrayList<>();
	static ArrayList<String> checksums = new ArrayList<>();

	static String currentVersion;
	static String q = File.separator;

	static int forgePoint, argsPoint;

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
		switch (op) {
		case 0:
			setChecksums(list);
			break;
		case 1:
			setModlist(list);
			break;
		case 2:
			setToRemove(list);
			break;
		default:
			// Error. Shouldn't reach here.
			break;
		}
	}

	public static void readProfileData(int mem) {
		File launcherSettings = new File(Driver.getMinecraftInstall() + q + "launcher_profiles.json");
		// installOptions.backup();
		if (launcherSettings.exists()) {
			try {
				JSONTokener fileData = new JSONTokener(new FileReader(launcherSettings));
				JSONObject launcherData = new JSONObject(fileData);
				JSONObject profilesObject = launcherData.getJSONObject("profiles");

				JSONArray keys = profilesObject.names();
				boolean forgeFound = false;
				boolean versionIdFound = false;
				for (int j = 0; j < keys.length(); j++) {
					if (keys.get(j).equals("Forge")) {
						forgeFound = true;
						forgePoint = j;
					}
				}

				if (forgeFound) {
					JSONObject forgeObject = profilesObject.getJSONObject("Forge");
					JSONArray forgeKeys = forgeObject.names();
					for (int k = 0; k < forgeKeys.length(); k++) {
						if (forgeKeys.get(k).equals("lastVersionId")) {
							versionIdFound = true;
						}

						else if (forgeKeys.get(k).equals("javaArgs")) {
							argsPoint = k;
						}
					}

					if (versionIdFound) {
						Object versionValue = forgeObject.get("lastVersionId");
						if (versionValue.equals("1.7.10-Forge10.13.4.1614-1.7.10")) {
							memAdjust(forgeObject, mem);
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void memAdjust(JSONObject forgeData, int mem) { // Map map was in there.
		Object argsValue = null;
		int posArg = 0;
		try {
			JSONArray keys = forgeData.names();
			for (int i = 0; i < keys.length(); i++) {
				if (keys.get(i).equals("javaArgs")) {
					argsValue = forgeData.get("javaArgs");
				}
			}
			String args = argsValue.toString();
			posArg = args.indexOf("-Xmx");
			if (posArg == -1) {
				System.out.println("Not Found");
				// Actually handle this, as this would mean that there were no arguments found.
				// Likely would just insert its own.
			}
			if (posArg >= 0) {
				StringBuilder sb = new StringBuilder();
				sb.append(args);
				String values = sb.substring(posArg, posArg + 10);

				if (values.charAt(values.length() - 1) == 'M') {
					values = sb.substring(posArg, posArg + 11);
				}

				StringBuilder valueMod = new StringBuilder(values);
				int posOfX = values.indexOf('x');
				int posOfM = values.indexOf('M') + 1;
				int posOfG = values.indexOf('G') + 1;
				int argsPos = 0;
				if (posOfM == 0) {
					valueMod.replace(posOfX + 1, posOfG, Integer.toString(mem / 1024) + 'G');
					argsPos = posArg + posOfG;
				}
				if (posOfG == 0) {
					valueMod.replace(posOfX + 1, posOfM, Integer.toString(mem) + 'M');
					argsPos = posArg + posOfM;
				}
				sb.replace(posArg, argsPos, valueMod.toString().trim());
				argsValue = sb.toString();
				launcherJsonWrite(argsValue.toString());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void launcherJsonWrite(String newArgs) {
		try {
			File launcherInfo = new File(Driver.getMinecraftInstall() + q + "launcher_profiles.json");

			JSONTokener fileData = new JSONTokener(new FileReader(launcherInfo));
			JSONObject launcherData = new JSONObject(fileData);
			JSONObject newLauncher = launcherData.getJSONObject("profiles");
			Object clientToken = launcherData.get("clientToken");
			JSONObject authData = launcherData.getJSONObject("authenticationDatabase");
			JSONObject settings = launcherData.getJSONObject("settings");
			JSONObject version = launcherData.getJSONObject("launcherVersion");
			JSONObject userData = launcherData.getJSONObject("selectedUser");

			String pwd = System.getProperty("user.dir");
			// String json = Driver.getMinecraftInstall() + q + "launcher_profiles.json";
			String json = pwd + q + "test.json";
			PrintWriter launcherJson = new PrintWriter(json);
			// launcherInfo.delete();
			JSONObject newForge = newLauncher.getJSONObject("Forge");
			newForge.put("javaArgs", newArgs);
			newLauncher.put("Forge", newForge);

			launcherJson.write("{");
			launcherJson.write(restructure("authenticationDatabase", authData, true, 0));
			launcherJson.write(" \t\"clientToken\"" + ": " + "\"" + clientToken + "\",\n");
			launcherJson.write(restructure("launcherVersion", version, true, 0));
			launcherJson.write(restructure("profiles", newLauncher, true, 0));
			launcherJson.write(restructure("selectedUser", userData, true, 0));
			launcherJson.write(restructure("settings", settings, false, 0));
			launcherJson.write("\n}");
			launcherJson.flush();
			launcherJson.close();

			// Back up the original, of course.
		} catch (FileNotFoundException fnfe) {
			// Launcher profile could not be found.
			fnfe.printStackTrace();
		} catch (JSONException je) {
			// Incorrectly formatted Json in the launcher. Shouldn't happen.
			je.printStackTrace();
		}
	}

	public static String restructure(String header, JSONObject jo, boolean addComma, int op) {
		StringBuilder sb = new StringBuilder();
		sb.append(jo);
		sb.deleteCharAt(0); // This deletes the opening bracket from the object.
		for (int i = 1; i < sb.length(); i++) {
			if (sb.charAt(i - 1) == ',') {
				sb.insert(i, "\n");
			} else if (sb.charAt(i) == '}') {
				sb.insert(i + 1, "\n");
			} else if (sb.charAt(i - 1) == '{') {
				sb.insert(i, "\n");
			}
		}

		if (addComma) {
			sb.insert(sb.length(), ",\n");
		}
		
		sb.insert(0, '\t');
		String newHeader = "\t\n" + "\"" + header + "\"" + ": " + "{";
		sb.insert(0, newHeader);
		return sb.toString();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void errorRead(Object errorCode) {
		String pwd = System.getProperty("user.dir");
		File errorsData = new File(pwd + q + "Modpack-Installer_lib" + q + "errors.json");
		try {
			Object fileData = new JSONTokener(new FileReader(errorsData));
			JSONObject data = (JSONObject) fileData;
			Object keyVal;
			Iterator<Map.Entry> itr1 = ((Map) data).entrySet().iterator();
			while (itr1.hasNext()) {
				Map.Entry pair = itr1.next();
				keyVal = pair.getKey();
				if (keyVal.equals(errorCode)) {
					Map errorData = ((Map) data.get("errorCode"));
					Iterator<Map.Entry> errorItr = errorData.entrySet().iterator();
					while (errorItr.hasNext()) {
						Map.Entry errorPair = errorItr.next();
						if (errorPair.getKey().equals("severity")) {
							Errors.setSeverity(errorPair.getValue());
						}
						if (errorPair.getKey().equals("cause")) {
							Errors.setCause(errorPair.getValue());
						}
						if (errorPair.getKey().equals("fix")) {
							Errors.setFix(errorPair.getValue());
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			// If the Json file itself could not be found. This shouldn't happen.
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Errors.makeGUI();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void libRead(File libData, ArrayList<String> list) {
		try {
			Object dataObj = new JSONTokener(new FileReader(libData));
			JSONObject dataJson = (JSONObject) dataObj;
			Map sigarData = ((Map) dataJson.get("sigar"));
			Iterator<Map.Entry> itr1 = ((Map) sigarData).entrySet().iterator();
			while (itr1.hasNext()) {
				Map.Entry pair = itr1.next();
				list.add((String) pair.getValue());
			}
			resourceCheck.setSigarFiles(list);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
}

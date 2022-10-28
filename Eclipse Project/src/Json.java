import JsonUtils.JSONArray;
import JsonUtils.JSONObject;
import JsonUtils.JSONTokener;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

public class Json {
    static ArrayList<String> modlist = new ArrayList<>();
    static ArrayList<String> toRemove = new ArrayList<>();
    static ArrayList<String> checksums = new ArrayList<>();


    public static void modpackLatestInfo() {
        try {
            URL modpackLink = new URL(Common.modpackLatestLink);
            JSONObject data = (JSONObject) new JSONTokener(modpackLink.openStream()).nextValue();

            // I could not do this the normal way as for whatever reason, the length is 1.
            // Meaning a for loop with assets.size() is pointless.
            JSONArray assets = (JSONArray) data.get("assets");
            String assetsString = assets.toString();
            String stuff = assetsString.substring(assetsString.indexOf("{"), assetsString.indexOf("}"));
            String browserURL = null;

            String[] splitData = stuff.split(",");


            for (int i = 0; i < splitData.length; i++) {
                if (splitData[i].contains("browser_download_url")) {
                    browserURL = splitData[i];
                    i = splitData.length;
                }
            }

            String[] splitURL = browserURL.split("\":\"");
            String modpackURL = splitURL[1].replace("\"", "\n").strip();
            String modpackFileName = modpackURL.substring(modpackURL.lastIndexOf("/") + 1);
            System.out.println(modpackURL);
            //    Downloader.Download(new URL(modpackURL), modpackFileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void modpackData() {
        try {
            URL modpackDataLink = new URL(Common.modpackDataJsonLink);
            JSONObject data = (JSONObject) new JSONTokener(modpackDataLink.openStream()).nextValue();

            String currentVersion = (String) data.get("1_16-currentVersion");
            JSONArray checksumArray = (JSONArray) data.get("checksums");
            JSONArray modArray = (JSONArray) data.get("modList");
            JSONArray removal = (JSONArray) data.get("toRemove");

            readJSONArray(modArray, modlist, "mod");
            readJSONArray(removal, toRemove, "remove");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void readJSONArray(JSONArray array, ArrayList<String> list, String key) {
        JSONObject obj;
        for (int i = 0; i < array.length(); i++) {
            obj = (JSONObject) array.get(i);
            list.add((String) obj.get(key));
        }
    }

    public static void readLauncherFile(String versionId, int newMemAmt) throws IOException {
        File launcherFile = new File("C:\\Users\\Aubrey\\Appdata\\Roaming\\.minecraft\\launcher_profiles.json");
        Scanner scan = new Scanner(launcherFile);
        StringBuilder sb = new StringBuilder();

        while(scan.hasNext()){
            sb.append(scan.next());
        }
        scan.close();

        JSONObject obj = new JSONObject(sb.toString().strip());
        JSONObject profiles = obj.getJSONObject("profiles");
        Set<String> keys = profiles.keySet();
        Object[] keysArr = keys.toArray();
        ArrayList<String> stringKeys = new ArrayList<>();
        for (Object o : keysArr) {
            stringKeys.add(o.toString());
        }

        JSONObject array, chosenObject = null;
        String key, lastVersionId;

        for(int i = 0; i < stringKeys.size(); i++){
            key = stringKeys.get(i);
            array = (JSONObject) profiles.get(key);
            lastVersionId = (String) array.get("lastVersionId");
            if(lastVersionId.equals(versionId)){
                chosenObject = (JSONObject) profiles.get(stringKeys.get(i));
                break;
            }
        }

        String argsString = (String) chosenObject.get("javaArgs");
        String[] argsArr = argsString.split(":");

        for(int i = 0; i < argsArr.length; i++){
            if(argsArr[i].contains("-Xmx")){
                argsArr[i] = "-Xmx" + newMemAmt + "G-XX";
                break;
           }
       }

 //       System.out.println(Arrays.toString(argsArr));

    }


    public static ArrayList<String> getModlist() {
        return modlist;
    }

    public static ArrayList<String> getToRemove() {
        return toRemove;
    }

    public static ArrayList<String> getChecksums() {
        return checksums;
    }

}

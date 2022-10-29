import json_simple.JsonArray;
import json_simple.JsonException;
import json_simple.JsonObject;
import json_simple.Jsoner;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Json {
    static ArrayList<String> modlist = new ArrayList<>();
    static ArrayList<String> toRemove = new ArrayList<>();
    static ArrayList<String> checksums = new ArrayList<>();


    public static void modpackLatestInfo() {
        try {
            URL modpackLink = new URL(Common.modpackLatestLink);

            InputStream linkStream = modpackLink.openStream();
            BufferedReader linkReader = new BufferedReader(new InputStreamReader(linkStream, StandardCharsets.UTF_8));

            JsonObject linkParser = (JsonObject) Jsoner.deserialize(linkReader);
            JsonArray assetsArray = (JsonArray) linkParser.get("assets");
            JsonObject assets = (JsonObject) assetsArray.get(0);

            String downloadURL, fileName;

            downloadURL = assets.get("browser_download_url").toString();

            fileName = downloadURL.substring(downloadURL.lastIndexOf("/") + 1);
            // Feed to download function.
            Downloader.Download(new URL(downloadURL), fileName);
        } catch (IOException | JsonException e) {
            throw new RuntimeException(e);
        }
    }

    public static void modpackData() {
        try {
            URL modpackDataLink = new URL(Common.modpackDataJsonLink);
            InputStream dataStream = modpackDataLink.openStream();
            BufferedReader dataReader = new BufferedReader(new InputStreamReader(dataStream, StandardCharsets.UTF_8));

            JsonObject dataParser = (JsonObject) Jsoner.deserialize(dataReader);

            JsonArray checksumArray = (JsonArray) dataParser.get("checksums");
            JsonArray modArray = (JsonArray) dataParser.get("modList");
            JsonArray removal = (JsonArray) dataParser.get("toRemove");

            readJSONArray(modArray, modlist, "mod");
            readJSONArray(removal, toRemove, "remove");

            JsonObject checksumObj = (JsonObject) checksumArray.get(0);

            System.out.println();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JsonException e) {
            throw new RuntimeException(e);
        }
    }

    public static void readJSONArray(JsonArray array, ArrayList<String> list, String key) {
        JsonObject obj;
        for (int i = 0; i < array.size(); i++) {
            obj = (JsonObject) array.get(i);
            list.add((String) obj.get(key));
        }
    }

    public static void adjustLauncherMemory(String versionId, int newMemAmt) throws IOException, JsonException {
        File launcherFile = new File(Common.getMinecraftInstall() + Common.q + "launcher_profiles.json");

        Reader profilesReader = Files.newBufferedReader(Paths.get(launcherFile.toURI()));

        JsonObject profileParser = (JsonObject) Jsoner.deserialize(profilesReader);
        JsonObject profiles = (JsonObject) profileParser.get("profiles");

        Object[] keysArray = profiles.keySet().toArray();
        ArrayList<String> stringKeys = new ArrayList<>();

        for (Object o : keysArray) {
            stringKeys.add(o.toString());
        }

        JsonObject obj;
        String versionKey;
        int foundSpot = 0;

        for (int i = 0; i < stringKeys.size(); i++) {
            obj = (JsonObject) profiles.get(stringKeys.get(i));
            versionKey = obj.get("lastVersionId").toString();
            if (versionKey.equals(versionId)) {
                foundSpot = i;
                break;
            }
        }

        JsonObject chosenOne = (JsonObject) profiles.get(stringKeys.get(foundSpot));
        String javaArgs = chosenOne.get("javaArgs").toString();
        String newArgs = "-Xmx" + newMemAmt + "G ";
        String currentArgs = javaArgs.substring(javaArgs.indexOf("-Xmx"), javaArgs.indexOf("-Xmx") + 7);
        javaArgs = javaArgs.replace(currentArgs, newArgs);
        chosenOne.put("javaArgs", javaArgs);

        profiles.put(stringKeys.get(foundSpot), chosenOne);

        BufferedWriter profileWriter = Files.newBufferedWriter(Paths.get("profiles.json"));

        Jsoner.serialize(profileParser, profileWriter);
        profileWriter.close();
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

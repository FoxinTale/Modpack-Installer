import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Json {
    static ArrayList<String> modlist = new ArrayList<>();
    static ArrayList<String> toRemove = new ArrayList<>();
    static ArrayList<String> checksums = new ArrayList<>();

    /*
    Right. Why am I using two separate JSON libraries here. Well,for some reason simple JSON failed to parse the JSOn from Github all of a sudden.
    Even though it was tested and verified toi work, suddenly it stopped working and I could not be bothered to figure out why. It was saying invalid token (commas)
    and well, there was no way for me to really even verify this. I couldn't debug it, I couldn't get it to print out what it was reading, to I reverted back to what I knew worked.
    I may work on figuring that out later, or just rework this to work with good ol' org.json again. Probably not.

    As the main reason I switched to simple JSON is because I couldn't seem to get org.json to be able to write a clean file with the launcher memory setting thing.
    and if I could get it to work, it was really, really messy. Look back at the commit history of this file, it took like three or four functions just to handle that.
    With simple JSON, I do it in one, clean function.
     */

    public static void modpackLatestInfo() {
        try {
            URL modpackLink = new URL(Common.modpackLatestLink);
            JSONObject data = (JSONObject) new JSONTokener(modpackLink.openStream()).nextValue();
            JSONArray assetsArray = (JSONArray) data.get("assets");
            JSONObject assets = (JSONObject) assetsArray.get(0);

            String downloadURL = (String) assets.get("browser_download_url");
            String modpackFileName = downloadURL.substring(downloadURL.lastIndexOf("/") + 1);
            Downloader.Download(new URL(downloadURL), modpackFileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void modpackData() {
        try {
            URL modpackDataLink = new URL(Common.modpackDataJsonLink);
            JSONObject data = (JSONObject) new JSONTokener(modpackDataLink.openStream()).nextValue();
            // Checksums layout: First one is main, second is testing and so on.

            JSONArray checksumArray = data.getJSONArray("checksums");
            JSONArray modsArray = data.getJSONArray("modList");
            JSONArray removal = data.getJSONArray("toRemove");

            readJSONArray(checksumArray, checksums, "modpack");
            readJSONArray(modsArray, modlist, "mod");
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

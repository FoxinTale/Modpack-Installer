import JsonUtils.JSONArray;
import JsonUtils.JSONObject;
import JsonUtils.JSONTokener;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Json {
    static ArrayList<String> modlist = new ArrayList<>();
    static ArrayList<String> toRemove = new ArrayList<>();
    static ArrayList<String> checksums = new ArrayList<>();

    public static void modpackLatestInfo(){
        try{
            URL modpackLink = new URL(Common.modpackLatestLink);
            JSONObject data = (JSONObject) new JSONTokener(modpackLink.openStream()).nextValue();
            JSONArray assets = (JSONArray) data.get("assets");
            String assetsString = assets.toString();
            String stuff = assetsString.substring(assetsString.indexOf("{"), assetsString.indexOf("}"));
            String browserURL = null;

            String[] splitData = stuff.split(",");

            for(int i = 0; i < splitData.length; i++){
                if(splitData[i].contains("browser_download_url")){
                    browserURL = splitData[i];
                    i = splitData.length;
                }
            }

            String[] splitURL= browserURL.split("\":\"");
            String modpackURL = splitURL[1].replace("\"", "\n").strip();
            String modpackFileName = modpackURL.substring(modpackURL.lastIndexOf("/") + 1);

            Downloader.Download(new URL(modpackURL), modpackFileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
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

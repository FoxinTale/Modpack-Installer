import JsonUtils.JSONArray;
import JsonUtils.JSONObject;
import JsonUtils.JSONTokener;

import javax.swing.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Updater {
    static ArrayList<String> removal = Json.getToRemove();
    static File versionFile = new File(Driver.getMinecraftInstallLocation() + File.separator + "Modpack_Version.txt");
    static String currentVersion = "";
    static String q = File.separator;

    public static void updater() {
        //Empty constructor.
    }

    public static void checkAPIForUpdate(URL updateLink, String version, String message, String title,  String toPrint, int op){
        try{
            JSONObject latestPage = (JSONObject) new JSONTokener(updateLink.openStream()).nextValue();
            String fileVersion = (String) latestPage.get("tag_name");
            if(!version.equals(fileVersion)){
                int o = JOptionPane.showConfirmDialog(new JFrame(), message, title, JOptionPane.YES_NO_OPTION);
                if (o == JOptionPane.YES_OPTION) {
                    JSONArray assetsArray = (JSONArray) latestPage.get("assets");
                    String assetString = assetsArray.toString();
                    String[] assetStringArray = assetString.split(",");
                    String newDownloadLink, tempLink = "";
                    for (String s : assetStringArray) {
                        if (s.contains("browser_download_url")) {
                            tempLink = s;
                        }
                    }
                    String[] tempArr = tempLink.split("\"");
                    newDownloadLink = tempArr[tempArr.length - 1];
                    // Fire off that link to the downloader to do its thingy, then close self after downloader is completed.
                }
            }
            else{
                System.out.println(toPrint);
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void modpackUpdateCheck() {
        try {
            URL modpackLatest = new URL("");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void removeStuff() {
        File modsDirectory = new File(Driver.getMinecraftInstall() + q + "mods");
        if (removal.isEmpty() || removal.size() == 0) {
            // Do nothing. Literally.
        }
        for (int i = removal.size(); i > 0; i--) {
            File begone;
            begone = new File(modsDirectory.toString() + q + removal.get(i - 1));
            begone.delete();
        }
    }

    public static void installUpdate() {
        removeStuff();
        File modsDirectory = new File(Driver.getMinecraftInstall() + q + "mods");
        File configDirectory = new File(Driver.getMinecraftInstall() + q + "config");
        File updateMods = new File(Driver.getDownloadsLocation() + q + currentVersion + q + "mods");
        File updateConfig = new File(Driver.getDownloadsLocation() + q + currentVersion + q + "config");

        Install.copyFiles(updateMods, modsDirectory);
        Install.copyFiles(updateConfig, configDirectory);

        String updateMessage = "Update installed!";
        JOptionPane.showMessageDialog(new JFrame(), updateMessage, "Update done.", JOptionPane.INFORMATION_MESSAGE);
        Install.end();
    }
}

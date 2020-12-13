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
    static String currentVersion = "";

    public static void checkAPIForUpdate(URL updateLink, String version, String message, String title,  String toPrint, int op){
        try{
            JSONObject latestPage = (JSONObject) new JSONTokener(updateLink.openStream()).nextValue();
            String fileVersion = (String) latestPage.get("tag_name");
            String fileName = "";
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

                    for (String s : assetStringArray) {
                        if (s.contains("name")) {
                            fileName = s;
                        }
                    }

                    String[] linkArr = tempLink.split("\"");
                    String[] nameArr = fileName.split("\"");
                    fileName = nameArr[nameArr.length - 1];

                    newDownloadLink = linkArr[linkArr.length - 1];
                    System.out.println("File name: " + fileName);
                    // Following if it remains two values it could likely be a Boolean value passed in.
                    switch (op) {
                        case 0: // It's pretty much every other file
                            URL fileDownloadLink = new URL (newDownloadLink);
                            Downloader.Download(fileDownloadLink, fileName, 0);
                            break;
                        case 1: // Installer update.
                            File thingFile = new File(fileName);
                            Downloader.downloadNoProgress(newDownloadLink, thingFile);
                            break;
                        default:
                            break;
                    }
                }
            }
            else{
                System.out.println(toPrint);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void modpackUpdateTags() {
        try {
            Common.modpackPartOneCheckedVersion = Preinstall.getTag(new URL(Common.modpackPartOneLink));
            Common.modpackPartTwoCheckedVersion = Preinstall.getTag(new URL(Common.modpackPartTwoLink));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void removeStuff() {
        File modsDirectory = new File(Common.getMinecraftInstall() + Common.q + "mods");
        if (removal.isEmpty()) {
            // Do nothing. Literally.
        }
        for (int i = removal.size(); i > 0; i--) {
            File begone;
            begone = new File(modsDirectory.toString() + Common.q + removal.get(i - 1));
            begone.delete();
        }
    }

    public static void installUpdate() {
        removeStuff();
        File modsDirectory = new File(Common.getMinecraftInstall() + Common.q + "mods");
        File configDirectory = new File(Common.getMinecraftInstall() + Common.q + "config");
        File updateMods = new File(Common.getDownloadsLocation() + Common.q + currentVersion + Common.q + "mods");
        File updateConfig = new File(Common.getDownloadsLocation() + Common.q + currentVersion + Common.q + "config");

        Install.copyFiles(updateMods, modsDirectory);
        Install.copyFiles(updateConfig, configDirectory);

        String updateMessage = "Update installed!";
        JOptionPane.showMessageDialog(new JFrame(), updateMessage, "Update done.", JOptionPane.INFORMATION_MESSAGE);
        Install.end();
    }
}

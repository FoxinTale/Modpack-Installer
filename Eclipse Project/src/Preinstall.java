import FileUtils.FileUtils;
import JsonUtils.JSONObject;
import JsonUtils.JSONTokener;

import javax.swing.*;
import java.io.*;
import java.net.URL;

// Used for checking and doing things before the installation process begins.
public class Preinstall {

    String tmpDir = FileUtils.getTempDirectoryPath();
    public static void checkForMinecraftandForge() {
        String minecraftVersions = Common.getMinecraftInstall() + Common.q + "versions" + Common.q;
        String moddedJson = minecraftVersions + "1.7.10-Forge10.13.4.1614-1.7.10" + Common.q + "1.7.10-Forge10.13.4.1614-1.7.10.json";
        File vanillaMinecraft = new File(minecraftVersions + "1.7.10" + Common.q + "1.7.10.jar");
        File vanillaMinecraftConfig = new File(minecraftVersions + "1.7.10" + Common.q + "1.7.10.json");
        File moddedMinecraftConfig = new File(moddedJson);

        boolean modConfig = moddedMinecraftConfig.exists();
        boolean vanilla = vanillaMinecraft.exists();
        boolean vanillaConfig = vanillaMinecraftConfig.exists();
        if (!vanilla || !vanillaConfig) {
            JOptionPane.showMessageDialog(new JFrame(), Strings.noVanillaMessage, Strings.noVanillaTitle, JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        if (!modConfig) {
            JOptionPane.showMessageDialog(new JFrame(), Strings.noModsMessage, Strings.noModsTitle, JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    public static String getTag(URL updateLink){
        try{
            JSONObject latestPage = (JSONObject) new JSONTokener(updateLink.openStream()).nextValue();
            return (String) latestPage.get("tag_name");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void createInfoFile(){
        try {
            FileWriter infoWriter = new FileWriter(Common.infoFile);
            infoWriter.write("{\n");
            infoWriter.write("\t\"PartOneVersion\": " + "\"" + Common.modpackPartOneCheckedVersion + "\",\n");
            infoWriter.write("\t\"PartTwoVersion\": " + "\"" + Common.modpackPartTwoCheckedVersion + "\"");
            infoWriter.write("\n}");
            infoWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void readInfoFile(){
        if(Common.infoFile.exists()){
            try {
               JSONTokener infoData  = new JSONTokener(new FileReader(Common.infoFile));
                JSONObject infoObject = new JSONObject(infoData);
                Common.modpackPartOneInstalledVersion = (String) infoObject.get("PartOneVersion");
                Common.modpackPartTwoInstalledVersion = (String) infoObject.get("PartTwoVersion");
                modpackPartOneCheck();
                modpackPartTwoCheck();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void fileCheck(){
        if(Common.infoFile.exists()){
            readInfoFile();
        }
        else{
            createInfoFile();
        }
    }

    public static void modpackPartOneCheck(){
        if(Common.modpackPartOneInstalledVersion.equals(Common.modpackPartOneCheckedVersion)){
            System.out.println(Strings.modpackPartOneGood);
            // Would it then do something afterwards?
        }
        else{
            JOptionPane.showMessageDialog(new JFrame(), Strings.modpackPartOneOutdated, Strings.modpackPartOutdated, JOptionPane.WARNING_MESSAGE);
            // Summon the downloader by firing a link off. May need to have special copying. Force copy overwriting anything there.
        }
    }

    public static void modpackPartTwoCheck(){
        if(Common.modpackPartTwoInstalledVersion.equals(Common.modpackPartTwoCheckedVersion)){
            System.out.println(Strings.modpackPartTwoGood);
            // Would it then do something afterwards?
        }
        else{
            JOptionPane.showMessageDialog(new JFrame(), Strings.modpackPartTwoOutdated, Strings.modpackPartOutdated, JOptionPane.WARNING_MESSAGE);
            // Summon the downloader by firing a link off. May need to have special copying. Force copy overwriting anything there.
        }
    }


}

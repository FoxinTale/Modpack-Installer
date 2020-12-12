import FileUtils.FileUtils;
import JsonUtils.JSONObject;
import JsonUtils.JSONTokener;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
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

    public static void createLocationFile(){

    }

    public static void readLocationFile(){

    }

    public static void createVersionFile(){

    }

    public static void readVersionFile(){

    }

    public static void versionCheck(){

    }
}

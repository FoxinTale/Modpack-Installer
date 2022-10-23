import JsonUtils.JSONObject;
import JsonUtils.JSONTokener;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

// Used for checking and doing things before the installation process begins.
public class Preinstall {

    public static void checkForMinecraftandForge() {
        String minecraftVersions = Common.getMinecraftInstall() + Common.q + "versions" + Common.q;
        String moddedJson = minecraftVersions + "1.16.5-forge-36.2.39" + Common.q + "1.16.5-forge-36.2.39.json";
        File vanillaMinecraft = new File(minecraftVersions + "1.16.5" + Common.q + "1.16.5.jar");
        File vanillaMinecraftConfig = new File(minecraftVersions + "1.16.5" + Common.q + "1.16.5.json");
        File moddedMinecraftConfig = new File(moddedJson);

        boolean modConfig = moddedMinecraftConfig.exists();
        boolean vanilla = vanillaMinecraft.exists();
        boolean vanillaConfig = vanillaMinecraftConfig.exists();
        boolean notUsingDefault = false;
        if (!vanilla || !vanillaConfig) {

            int noMinecraft = JOptionPane.showOptionDialog(new JFrame(), Strings.noVanillaMessage, Strings.noVanillaTitle, JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Okay", "I'm not using the default launcher"}, "default");
            if (noMinecraft == 0) {
                System.exit(0);
            } else {
                notUsingDefault = true;
            }
        } else {
            System.out.println(" [log] Vanilla game found.");
        }

        if (!notUsingDefault) {
            if (!modConfig) {
                JOptionPane.showMessageDialog(new JFrame(), Strings.noModsMessage, Strings.noModsTitle, JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            } else {
                System.out.println(" [log] Modded game found.");
            }
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


}

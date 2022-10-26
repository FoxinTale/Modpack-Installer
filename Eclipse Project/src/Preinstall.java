import javax.swing.*;
import java.io.File;

// Used for checking and doing things before the installation process begins.
public class Preinstall {
    public static boolean notUsingDefault = false;
    public static void checkForMinecraftandForge() {
        String minecraftVersions = Common.getMinecraftInstall() + Common.q + "versions" + Common.q;
        String moddedJson = minecraftVersions + "1.16.5-forge-36.2.39" + Common.q + "1.16.5-forge-36.2.39.json";
        File vanillaMinecraft = new File(minecraftVersions + "1.16.5" + Common.q + "1.16.5.jar");
        File vanillaMinecraftConfig = new File(minecraftVersions + "1.16.5" + Common.q + "1.16.5.json");
        File moddedMinecraftConfig = new File(moddedJson);

        boolean modConfig = moddedMinecraftConfig.exists();
        boolean vanilla = vanillaMinecraft.exists();
        boolean vanillaConfig = vanillaMinecraftConfig.exists();
        if (!vanilla || !vanillaConfig) {
            int noMinecraft = JOptionPane.showOptionDialog(new JFrame(), Strings.noVanillaMessage, Strings.noVanillaTitle
                    , JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null
                    , new String[]{"Okay", "I'm not using the default launcher"}, "default");
            if (noMinecraft == 0) {
                System.exit(0);
            } else {
                notUsingDefault = true;
            }
        }
        if (!notUsingDefault) {
            if (!modConfig) {
                JOptionPane.showMessageDialog(new JFrame(), Strings.noModsMessage, Strings.noModsTitle, JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
    }
}

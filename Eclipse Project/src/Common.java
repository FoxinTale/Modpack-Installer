import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Common {
    //This contains stuff that would otherwise be repeated elsewhere many times.
    // As well as a home for the download links so whenever I change something I change it once instead of changing it in multiple places.
    private static File minecraftInstallLocation = null;
    private static String minecraftInstall = null;
    private static String downloadsLocation = null;
    private static String desktopLocation = null;
    private static String minecraftDefaultInstall = null;

    static String modpackPartOneCheckedVersion;
    static String modpackPartTwoCheckedVersion;
    static String modpackPartOneInstalledVersion;
    static String modpackPartTwoInstalledVersion;

    static String q = File.separator;
    static Font pretty;
    static String zipFile = "Modpack.zip"; //This will get deleted soon, as it's no longer called this anymore.
    static File infoFile = new File("installerinfo.json");


    // Links.
    static String installerLatestLink = "https://api.github.com/repos/foxintale/modpack-installer/releases/latest";
    static String fontLink = "https://srv-store1.gofile.io/download/7cQzQT/InstallerFont.ttf";
    static String modpackPartOneLink = "https://api.github.com/repos/foxintale/minecraft-modpack/releases/latest";
    static String modpackPartTwoLink = "https://api.github.com/repos/foxintale/modpack-bigmods/releases/latest";
    static String resourcePackTexturesLink = "";


    // Font stuff.
    static String modpackVersion = "";
    public static void getFont(){
        try {
            File fontFile = new File(System.getProperty("user.dir")+ File.separator + "InstallerFont.ttf");
            if(!fontFile.exists()) {
                Downloader.downloadNoProgress(fontLink, fontFile);
            }
            pretty = Font.createFont(Font.TRUETYPE_FONT, new File(System.getProperty("user.dir") + File.separator + "InstallerFont.ttf")).deriveFont(16f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(System.getProperty("user.dir") + File.separator + "InstallerFont.ttf")));
            fontFile.deleteOnExit();
        } catch (IOException e) {

        } catch (FontFormatException e) {
            GUI.errors.setText("Screwy font");
        }
    }

    // This explains itself. I decided on a function because otherwise this would
    // have been repeated many times, taking up space in the code.
    public static void folderCreate(File folder) {
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    // Other variables.
    public static Boolean bePretty = false;

    // Getters and setters.
    public static String getDownloadsLocation() {
        return downloadsLocation;
    }

    public static File getMinecraftInstallLocation() {
        return minecraftInstallLocation;
    }

    public static String getMinecraftInstall() {
        return minecraftInstall;
    }

    public static String getDesktopLocation() {
        return desktopLocation;
    }

    public static void setDownloadsLocation(String absolutePath) {
        downloadsLocation = absolutePath;
    }

    public static void setMinecraftInstall(String absolutePath) {
        minecraftInstall = absolutePath;
    }

    public static void setMinecraftInstallLocation(File minecraftDefaultPath) {
        minecraftInstallLocation = minecraftDefaultPath;
    }

    public static void setDesktopLocation(String absolutePath) {
        desktopLocation = absolutePath;
    }
}

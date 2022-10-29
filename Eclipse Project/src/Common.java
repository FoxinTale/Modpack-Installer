import java.io.File;

public class Common {
    //This contains stuff that would otherwise be repeated elsewhere many times.
    // As well as a home for the download links so whenever I change something I change it once instead of changing it in multiple places.
    private static File minecraftInstallLocation = null;
    private static String minecraftInstall = null;
    private static String downloadsLocation = null;
    private static String desktopLocation = null;
    private static String minecraftDefaultInstall = null;
    static String q = File.separator;
    static String zipFile = ""; //This will get deleted soon, as it's no longer called this anymore.

    // Links.
    static String installerLatestLink = "https://api.github.com/repos/foxintale/modpack-installer/releases/latest";
    static String modpackLatestLink = "https://api.github.com/repos/foxintale/minecraft-modpack/releases/latest";
    static String modpackDataJsonLink = "https://raw.githubusercontent.com/FoxinTale/Minecraft-Modpack/1.16.5/data.json";

    static String jsonLibLink = "https://store1.gofile.io/download/1dc956b5-8dea-4293-91d3-1d25ddf0939b/json.jar";
    static String zipLibLink = "https://store7.gofile.io/download/d1d8321c-0834-4817-a0a5-3637674ba260/zip4j-2.11.2.jar";

    // This explains itself. I decided on a function because otherwise this would
    // have been repeated many times, taking up space in the code.
    public static void folderCreate(File folder) {
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

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

import ZipFileUtility.ZipException;
import ZipFileUtility.ZipFile;

import java.io.File;

public class Extractor {
    static String q = File.separator;

    public static void Extract(String fileLocation, String name, int op) {

        File modpack = new File(Driver.getDownloadsLocation() + q + "Modpack");
        if (modpack.exists()) {
            modpack.delete();
            // This deletes a modpack folder in the downloads if it exists already.
            // If this wasn't done, the extract would fail.
        }

        String folderPath = Driver.getDownloadsLocation() + q + name + q;
        switch (op) {
            case 0: // Modpack
                System.out.println(" Extracting modpack. Expect system lag.");
                unzip(fileLocation, folderPath, op);
                break;
            case 1: // Update
                unzip(fileLocation, name, op);
                break;
            case 2: // Resource Pack
                System.out.println(" Extracting resource pack. Expect system lag.");
                unzip(fileLocation, name, op);
                break;
            default:
                break;
        }
    }

    public static void unzip(String zipFilePath, String extractFolder, int op) {
        // 0 is the modpack, 1 is an update, and 2 is the resource pack.
        try {
            ZipFile zipFile = new ZipFile(zipFilePath);
            zipFile.extractAll(extractFolder);
        } catch (ZipException e) {
            GUI.errors.setText("Kyogre");
            Errors.init();
        }
        System.out.println(" Extraction complete.");
        switch (op) {
            case 0:
                Install.install();
                break;
            case 1:
                Updater.installUpdate();
            case 2:
                System.out.println(" Resource pack installed.");
                Install.end();
            default:
                break;
        }
    }
}

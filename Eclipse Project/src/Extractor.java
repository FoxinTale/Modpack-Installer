import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;

public class Extractor {
    public static void Extract(String fileLocation, int op) {

        String name = Common.zipFile.substring(0, Common.zipFile.lastIndexOf('.'));
        File modpack = new File(Common.getDownloadsLocation() + Common.q + name);
        if (modpack.exists()) {
            modpack.delete();
            // This deletes a modpack folder in the downloads if it exists already.
            // If this wasn't done, the extract would fail.
        }
        //System.out.println(nam1)
        String folderPath = Common.getDownloadsLocation() + Common.q + name + Common.q;
        switch (op) {
            case 0: // Modpack
                System.out.println(Strings.installerExtractNotice);
                unzip(fileLocation, folderPath, op);
                break;
            case 1: // Update
                unzip(fileLocation, name, op);
                break;
            case 2: // Resource Pack
                System.out.println(Strings.installerExtractNotice);
                unzip(fileLocation, name, op);
                break;
            case 3:
                unzip(fileLocation, name, 1);
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
            GUI.errorOccured("Kyogre");
            Errors.kyogre();
        }
        System.out.println(" Extraction complete.");
        switch (op) {
            case 0:
                Install.install();
                break;
            case 1:
                //Updater.installUpdate();
                System.out.println(" Extract complete!");
                break;
            default:
                break;
        }
    }
}

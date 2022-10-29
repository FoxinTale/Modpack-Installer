import zip4j.ZipFile;

import java.io.File;

public class Extractor {
    public static void Extract(String fileLocation) {

        String name = Common.zipFile.substring(0, Common.zipFile.lastIndexOf('.'));
        File modpack = new File(Common.getDownloadsLocation() + Common.q + name);
        if (modpack.exists()) {
            modpack.delete();
            // This deletes a modpack folder in the downloads if it exists already.
            // If this wasn't done, the extract would fail.
        }

        String folderPath = Common.getDownloadsLocation() + Common.q + name + Common.q;
    }


    public static void unzip(String zipFilePath, String extractFolder) {
        ZipFile zipFile = new ZipFile(zipFilePath);
        zipFile.extractAll(extractFolder);
        System.out.println(" Extraction complete.");
    }
}

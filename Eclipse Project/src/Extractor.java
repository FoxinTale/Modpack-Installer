import zip4j.ZipFile;
import zip4j.exception.ZipException;

import java.io.File;

public class Extractor {
    public static void Extract(String fileLocation) {

        String name = Common.zipFile.substring(0, Common.zipFile.lastIndexOf('.'));
        File modpack = new File(Common.getDownloadsLocation() + Common.q + name);
        if (modpack.exists()) {
            modpack.delete();
        }
        String folderPath = Common.getDownloadsLocation() + Common.q + name + Common.q;
    }


    public static void unzip(String zipFilePath, String extractFolder) throws ZipException {
        ZipFile zipFile = new ZipFile(zipFilePath);
        zipFile.extractAll(extractFolder);
        System.out.println(" Extraction complete.");
        Install.install();
    }
}

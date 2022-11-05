import zip4j.ZipFile;
import zip4j.exception.ZipException;

public class Extractor {
    public static void unzip(String zipFilePath, String extractFolder) throws ZipException {
        ZipFile zipFile = new ZipFile(zipFilePath);
        zipFile.extractAll(extractFolder);
        System.out.println(" Extraction complete.");
        Install.install();
    }
}

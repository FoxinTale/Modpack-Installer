import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Checksums {
    static MessageDigest md5Digest;
    static String cModpackSum, modpackSum;
    static ArrayList<String> checksums = Json.getChecksums();

    public static void checksum(File zipFile, String zipName) {
        try { // See the Downloads class for the full file names.
            md5Digest = MessageDigest.getInstance("MD5");

            System.out.println(Strings.installerVerifyingFile);
         //   zipFile = new File(Common.getDownloadsLocation() + Common.q + zipName);

            modpackSum = checksums.get(0);
            cModpackSum = getFileChecksum(md5Digest, zipFile);
            if (checkSums(modpackSum, cModpackSum)) {
                System.out.println(Strings.installerVerificationPassed);
                Extractor.unzip(zipFile.getPath(), Common.getDownloadsLocation() + Common.q + "modpack");
                //Extractor.Extract(Common.q + Common.getDownloadsLocation() + Common.q + Common.zipFile, "Modpack", 0);
            }
            if (!checkSums(modpackSum, cModpackSum)) {
  //              Downloader.redownloadModpack();
                System.out.println("Verification failed");
            }
        } catch (NoSuchAlgorithmException e) {
            // This should never, ever happen. Java required this catch.
            GUI.errorOccured("Blastoise");
            Errors.blastoise();
        } catch (IOException e) {
            GUI.errorOccured("Glameow");
            Errors.glameow();
        }
    }

    public static String getFileChecksum(MessageDigest digest, File file) throws IOException {
        FileInputStream fis = new FileInputStream(file); // Get file input stream for reading the file content
        byte[] byteArray = new byte[1024]; // Create byte array to read data in chunks
        int bytesCount = 0;
        while ((bytesCount = fis.read(byteArray)) != -1) { // Read file data and update in message digest
            digest.update(byteArray, 0, bytesCount); // Get the hash's bytes
        }
        ;
        fis.close(); // close the stream; We don't need it now.
        byte[] bytes = digest.digest(); // This bytes[] has bytes in decimal format;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) { // Convert it to hexadecimal format
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString(); // return complete hash
    }

    public static Boolean checkSums(String sumOne, String sumTwo) {
        Boolean good = false;
        if (sumOne.equals(sumTwo)) {
            good = true;
        }
        if (!sumOne.equals(sumTwo)) {
            good = false;
        }
        return good;
    }

}
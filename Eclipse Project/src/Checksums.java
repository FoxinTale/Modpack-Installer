import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;

public class Checksums {

	static MessageDigest md5Digest;

	static Boolean checksPassed;
	
	static String cModpackSum, cResourcesSum, modpackSum, resourcesSum;
	static String q = File.separator;

	static File resourcePackDir = new File(Driver.getMinecraftInstall() + q + "resourcepacks");
	static ArrayList<String> checksums = Json.getChecksums();

	public static void checksum(File zipFile, String zipName) {
		try { // See the Downloads class for the full file names.
			md5Digest = MessageDigest.getInstance("MD5");

			System.out.println(" Verifying integrity of file");
			zipFile = new File(Driver.getDownloadsLocation() + q + zipName);
			if (zipName.equals("Modpack.zip")) {
				modpackSum = checksums.get(0);
				cModpackSum = getFileChecksum(md5Digest, zipFile);
				if (checkSums(modpackSum, cModpackSum)) {
					System.out.println(" File verification passed!");
					Extractor.Extract(q + Driver.getDownloadsLocation() + q + Driver.zipFile, "Modpack", 0);
				}
				if (!checkSums(modpackSum, cModpackSum)) {
					Downloader.redownloadModpack();
				}
			}
			switch (zipName) {
			case "ACRP-TO.zip":
				resourceCheck(1, zipFile, "ACRP-TO");
				break;
			case "ACRP-MS.zip":
				resourceCheck(2, zipFile, "ACRP-MS");
				break;
			case "ACRP-MST.zip":
				resourceCheck(3, zipFile, "ACRP-MST");
				break;
			case "ACRP-AS.zip":
				resourceCheck(4, zipFile, "ACRP-AS");
				break;
			case "ACRP-E.zip":
				resourceCheck(5, zipFile, "ACRP-E");
			default:
				break;
			}
		} catch (NoSuchAlgorithmException e) {
			// This should never, ever happen. Java required this catch.
			GUI.errors.setText("Blastoise");
			Errors.init();
		} catch (IOException e) {
			GUI.errors.setText("Glameow");
			Errors.init();
		}
	}

	public static void resourceCheck(int opt, File zip, String name) {
		resourcesSum = checksums.get(opt);
		try {
			cResourcesSum = getFileChecksum(md5Digest, zip);
		} catch (IOException e) {
			// This would happen if the zip file being verified could not be found.
			e.printStackTrace();
		}
		if (checkSums(resourcesSum, cResourcesSum)) {
			verifyFinish(zip.getPath(), name);
		}
		if (!checkSums(resourcesSum, cResourcesSum)) {
			System.out.println(" Verification Failed...Redownloading.");
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

	public static void verifyFinish(String zipName, String folderName) {
		System.out.println(" File verification passed.");
		String t = "Would you like to extract the resource pack in order to improve load times on Minecraft?";
		int o = JOptionPane.showConfirmDialog(new JFrame(), t, "Extract pack?", JOptionPane.YES_NO_OPTION);
		try {
			if (o == JOptionPane.YES_OPTION) { // Copy and Extract.
				String baseLocation = resourcePackDir.getAbsolutePath() + File.separator;
				FileUtils.copyFileToDirectory(Downloader.zipFile, resourcePackDir);
				Extractor.Extract(baseLocation + zipName, baseLocation + folderName, 2);
			}
			if (o == JOptionPane.NO_OPTION) {
				FileUtils.copyFileToDirectory(Downloader.zipFile, resourcePackDir);
			}
		} catch (IOException e) {
			GUI.errors.setText("Luxray");
			Errors.init();
		}
	}
}
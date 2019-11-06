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

	static String cModpackSum;
	static String cResourcesSum;

	static String modpackSum;
	static String resourcesSum;

	static Boolean checksPassed;

	static String q = File.separator;

	static File resourcePackDir = new File(Driver.getMinecraftInstall() + q + "resourcepacks");

	public static void checksum(File zipFile, String zipName) {
		ArrayList<String> checksums = new ArrayList<>();
		String checkPage = "https://sites.google.com/view/aubreys-modpack-info/home/checksums";
		websiteReader.siteReader(checkPage, false, 3, checksums);

		try {
			md5Digest = MessageDigest.getInstance("MD5");
			// See the Downloads class for the full file names.

			System.out.println(" Verifying integrity of file");
			zipFile = new File(Driver.getDownloadsLocation() + q + zipName);
			if (zipName.equals("Modpack.zip")) {
				modpackSum = siteSums(modpackSum, 0);
				cModpackSum = getFileChecksum(md5Digest, zipFile);
				if (checkSums(modpackSum, cModpackSum)) {
					System.out.println(" File verification passed!");
					Extractor.Extract(q + Driver.getDownloadsLocation() + q + Driver.zipFile, "Modpack", 0);
				}
			}

			if (zipName.equals("ACRP-TO.zip")) {
				resourcesSum = siteSums(resourcesSum, 1);
				cResourcesSum = getFileChecksum(md5Digest, zipFile);
				if (checkSums(resourcesSum, cResourcesSum)) {
					verifyFinish(zipName, "ACRP-TO");
				}
				if (!checkSums(resourcesSum, cResourcesSum)) {
					System.out.println("Verification Failed.");
				}
			}

			if (zipName.equals("ACRP-MS.zip")) {
				resourcesSum = siteSums(resourcesSum, 2);
				cResourcesSum = getFileChecksum(md5Digest, zipFile);
				if (checkSums(resourcesSum, cResourcesSum)) {
					verifyFinish(zipName, "ACRP-MS");
				}
				if (!checkSums(resourcesSum, cResourcesSum)) {
					System.out.println("Verification Failed.");
				}
			}

			if (zipName.equals("ACRP-MST.zip")) {
				resourcesSum = siteSums(resourcesSum, 3);
				cResourcesSum = getFileChecksum(md5Digest, zipFile);
				if (checkSums(resourcesSum, cResourcesSum)) {
					verifyFinish(zipName, "ACRP-MST");
				}
				if (!checkSums(resourcesSum, cResourcesSum)) {
					System.out.println("Verification Failed.");
				}
			}

			if (zipName.equals("ACRP-AS.zip")) {
				resourcesSum = siteSums(resourcesSum, 4);
				cResourcesSum = getFileChecksum(md5Digest, zipFile);
				if (checkSums(resourcesSum, cResourcesSum)) {
					verifyFinish(zipName, "ACRP-AS");
				}
			}

			if (zipName.equals("ACRP-E.zip")) {
				resourcesSum = siteSums(resourcesSum, 5);
				cResourcesSum = getFileChecksum(md5Digest, zipFile);
				if (checkSums(resourcesSum, cResourcesSum)) {
					verifyFinish(zipName, "ACRP-E");
				}
				if (!checkSums(resourcesSum, cResourcesSum)) {
					System.out.println("Verification Failed.");
				}
			}

		} catch (NoSuchAlgorithmException e) {
			// This should never, ever happen. Java required this catch.
			GUI.errors.setText("Blastoise");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String siteSums(String itemSum, int pos) throws IOException {
		Object[] sumCharArray = websiteReader.getChecksums().toArray();
		StringBuilder actualSum = new StringBuilder();
		actualSum.append(sumCharArray[pos]);
		if (pos != 0) {
			actualSum.deleteCharAt(0);

		}
		itemSum = actualSum.toString().trim();
		return itemSum;
	}

	public static String getFileChecksum(MessageDigest digest, File file) throws IOException {
		FileInputStream fis = new FileInputStream(file); // Get file input stream for reading the file content
		byte[] byteArray = new byte[1024]; // Create byte array to read data in chunks
		int bytesCount = 0;
		// Read file data and update in message digest
		while ((bytesCount = fis.read(byteArray)) != -1) {
			digest.update(byteArray, 0, bytesCount);
		}
		;
		fis.close(); // close the stream; We don't need it now.
		// Get the hash's bytes
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
			if (o == JOptionPane.YES_OPTION) {
				// Copy and Extract.
				String baseLocation = resourcePackDir.getAbsolutePath() + File.separator;
				FileUtils.copyFileToDirectory(Downloader.zipFile, resourcePackDir);
				Extractor.Extract(baseLocation + zipName, baseLocation + folderName, 2);
			}
			if (o == JOptionPane.NO_OPTION) {
				FileUtils.copyFileToDirectory(Downloader.zipFile, resourcePackDir);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
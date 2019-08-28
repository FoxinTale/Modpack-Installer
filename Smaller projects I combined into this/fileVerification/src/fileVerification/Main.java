package fileVerification;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		download();

	}

	public static void download() {
		// A simple download function for the text file that lists the updates.
		File modpack = new File("C:\\Users\\Alyan\\Downloads\\Modpack.zip");
		/*
		 * try { String url =
		 * "https://aubreys-storage.s3.us-east-2.amazonaws.com/1.7.10/Modpack.zip";
		 * BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
		 * System.out.println("Beginning download"); FileOutputStream fileOut = new
		 * FileOutputStream(modpack); byte dataBuffer[] = new byte[1024]; int bytesRead;
		 * while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
		 * fileOut.write(dataBuffer, 0, bytesRead); } // System.out.println();
		 * fileOut.close(); System.out.println("Verifying File");
		 */
		try {
			MessageDigest md5Digest = MessageDigest.getInstance("MD5");
			String calcSum = getFileChecksum(md5Digest, modpack);
			ArrayList<String> checksum = new ArrayList<>();
			websiteReader.siteReader("https://sites.google.com/view/aubreys-modpack-info/home/checksums", false, 3,
					checksum);
			Object[] actSumArray = websiteReader.getChecksums().toArray();
			StringBuilder actSum = new StringBuilder();
			for (int i = 0; i < actSumArray.length; i++) {
				actSum.append(actSumArray[i]);
			}
			String calculate = actSum.toString().trim();
			System.out.println(calcSum);
			System.out.println(calculate);
			System.out.println(equalChecksums(calcSum, calculate));
			// getFileChecksum();
		} catch (NoSuchAlgorithmException e) {
			// This should never, ever happen.
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	public static Boolean equalChecksums(String sumOne, String sumTwo) {
		Boolean good = false;
		if (sumOne.equals(sumTwo)) {
			good = true;
		}
		if (!sumOne.equals(sumTwo)) {
			good = false;
		}
		return good;
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

}

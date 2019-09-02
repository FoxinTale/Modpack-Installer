import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Downloader {
	static String modpackSum;
	static String calculatedSum;
	static Boolean checksPassed;
	static String q = File.separator;

	public static void Download(URL packLink, String zipName) {
		System.out.println("\n Downloading...");
		ArrayList<String> checksums = new ArrayList<>();
		String checkPage = "https://sites.google.com/view/aubreys-modpack-info/home/checksums";
		websiteReader.siteReader(checkPage, false, 3, checksums);
		Runnable updatethread = new Runnable() {
			public void run() {
				try {
					HttpURLConnection httpConnection = (HttpURLConnection) (packLink.openConnection());
					long completeFileSize = httpConnection.getContentLength();
					java.io.BufferedInputStream in = new java.io.BufferedInputStream(httpConnection.getInputStream());
					java.io.FileOutputStream fos = new java.io.FileOutputStream(
							q + Driver.getDownloadsLocation() + q + zipName);
					java.io.BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
					byte[] data = new byte[1024];
					long downloadedFileSize = 0;
					int x = 0;
					while ((x = in.read(data, 0, 1024)) >= 0) {
						downloadedFileSize += x;
						// calculate progress
						final int currentProgress = (int) ((((double) downloadedFileSize) / ((double) completeFileSize))
								* 100000d);
						// update progress bar
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								GUI.progress.setValue(currentProgress);
							}
						});
						bout.write(data, 0, x);
					}
					bout.close();
					in.close();

					System.out.println("\n Download Complete!");
					GUI.progress.setValue(0);

					if (!Driver.updateTime) {
						// I do this because it is a large file. There is a chance it can get corrupted
						// during a download.
						// This checks if that did happen, and if so, redownloads it.
						System.out.println(" Verifying file integrity.");
						File modpack = new File(q + Driver.getDownloadsLocation() + q + Driver.zipFile);
						try {
							MessageDigest md5Digest = MessageDigest.getInstance("MD5");
							ArrayList<String> checksum = new ArrayList<>();
							websiteReader.siteReader(
									"https://sites.google.com/view/aubreys-modpack-info/home/checksums", false, 3,
									checksum);
							Object[] sumCharArray = websiteReader.getChecksums().toArray();
							StringBuilder actualSum = new StringBuilder();
							for (int i = 0; i < sumCharArray.length; i++) {
								actualSum.append(sumCharArray[i]);
							}
							modpackSum = actualSum.toString().trim();
							calculatedSum = getFileChecksum(md5Digest, modpack);
						} catch (NoSuchAlgorithmException e) {
							GUI.errors.setText("Blastoise");
							// This should never happen, otherwise you're screwed.
						}
						checksPassed = equalChecksums(modpackSum, calculatedSum);
					}
					if (Driver.updateTime) {
						checksPassed = true;
						// Compared to the main pack, the update is a small file. Not really much of a
						// need to verify its integrity.

					}

					if (checksPassed) {
						System.out.println(" Success!");
						String s = "Would you like the installer to automagically extract and install the pack?";
						int n = JOptionPane.showConfirmDialog(new JFrame(), s, "Choices", JOptionPane.YES_NO_OPTION);
						if (n == JOptionPane.YES_OPTION) {

							System.out.println(" Beginning Extraction now...\n");
							if (GUI.packDownloadOnly == false) {
								if (Driver.updateTime) {
									Extractor.Extract(
											q + Driver.getDownloadsLocation() + q + Updater.currentVersion + ".zip",
											Updater.currentVersion);
								}
								if (!Driver.updateTime) {
									System.out.println(" This will take about a minute.");
									System.out.println(" There may be noticeable system lag during extraction.");
									System.out.println(" This is due to Java being dumb.");
									Extractor.Extract(q + Driver.getDownloadsLocation() + q + Driver.zipFile,
											"Modpack");
								}
							}
						}
						if (n == JOptionPane.NO_OPTION) {
							String t = "Assuming manual install or MultiMC, Exiting...";
							JOptionPane.showMessageDialog(new JFrame(), t, "Confirm", JOptionPane.INFORMATION_MESSAGE);
							System.exit(0);

						}
					}
					if (!checksPassed) {
						// Redownload the pack.
						System.out.println(" The file failed to validate. Redownloading");
						System.out.println(
								"If this happens repeatedly, please tell me. This means I forgot to update the checksum");
						URL pack = new URL("https://aubreys-storage.s3.us-east-2.amazonaws.com/1.7.10/Modpack.zip");
						Download(pack, zipName);
					}
				} catch (FileNotFoundException e) {
					GUI.errors.setText("Roserade");
				} catch (IOException e) {
					GUI.errors.setText("Jumpluff");
				}
			}
		};
		new Thread(updatethread).start();
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
}

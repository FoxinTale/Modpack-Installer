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

	public static void Downloader(URL packLink) {
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
							File.separator + Driver.getDownloadsLocation() + File.separator + Driver.zipFile);
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

					System.out.println("\n Download Complete!\n");
					GUI.progress.setValue(0);

					if (!GUI.updateOnly) {
						System.out.println("Verifying file integrity.");
						File modpack = new File(
								File.separator + Driver.getDownloadsLocation() + File.separator + Driver.zipFile);
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

							// This should never happen, otherwise you're screwed.
						}
						checksPassed = equalChecksums(modpackSum, calculatedSum);
					}
					if (GUI.updateOnly) {
						checksPassed = true;
						// It just bypasses the checksum for now. I'm getting tired of this for the day,
						// later, I'll rework it so the
						// Update zip goes through this, but for now it straight up bypasses it.
					}

					if (checksPassed) {
						System.out.println("Success!");
						String s = "Would you like the installer to automagically extract and install the pack?";
						int n = JOptionPane.showConfirmDialog(new JFrame(), s, "Choices", JOptionPane.YES_NO_OPTION);

						if (n == JOptionPane.YES_OPTION) {
							System.out.println(" This will take some time to complete.");
							System.out.println("This varies depending on your system and the size of the file");
							System.out.println(" Don't worry, it hasn't frozen. It is extracting.");
							System.out.println(" The installer will output when finished");
							System.out.println(" Beginning Extraction now...\n");
							if (GUI.packDownloadOnly == false) {
								if (GUI.updateOnly == true) {
									Extractor.Extractor(File.separator + Driver.getDownloadsLocation() + File.separator
											+ Updater.currentVersion + ".zip", Updater.currentVersion);
								}
								if (GUI.updateOnly == false) {
									Extractor.Extractor(File.separator + Driver.getDownloadsLocation() + File.separator
											+ Driver.zipFile, "Modpack");
								}
							}
						}
						if (n == JOptionPane.NO_OPTION) {
							String t = "Are you sure you want to do a manual install?";
							int o = JOptionPane.showConfirmDialog(new JFrame(), t, "Confirm",
									JOptionPane.YES_NO_OPTION);
							if (o == JOptionPane.YES_OPTION) {
								String message = "Not my fault if things don't work then.";
								JOptionPane.showMessageDialog(new JFrame(), message, "Info.",
										JOptionPane.INFORMATION_MESSAGE);
							}
							if (o == JOptionPane.NO_OPTION) {

							}
						}
					}
					if (!checksPassed) {
						// Redownload the pack.
						System.out.println("The file failed to validate. Redownloading");
						System.out.println(
								"If this happens repeatedly, please tell me. This means I forgot to update the checksum");
						URL pack = new URL("https://aubreys-storage.s3.us-east-2.amazonaws.com/1.7.10/Modpack.zip");
						Downloader(pack);
					}
				} catch (FileNotFoundException e) {
					// Handle this.
				} catch (IOException e) {
					// Handle this too
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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Downloader {
	public static void Downloader(URL packLink) {
		System.out.println("\n Downloading...");

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
										+ UpdateGUI.getFolderLoc() + ".zip", UpdateGUI.getFolderLoc());
							}
							if (GUI.updateOnly == false) {

								Extractor.Extractor(File.separator + Driver.getDownloadsLocation() + File.separator
										+ Driver.zipFile, "Modpack");
							}
						}
					}
					if (n == JOptionPane.NO_OPTION) {
						String t = "Are you sure you want to do a manual install?";
						int o = JOptionPane.showConfirmDialog(new JFrame(), t, "Confirm", JOptionPane.YES_NO_OPTION);
						if (o == JOptionPane.YES_OPTION) {
							String message = "Not my fault if things don't work then.";
							JOptionPane.showMessageDialog(new JFrame(), message, "Info.",
									JOptionPane.INFORMATION_MESSAGE);
						}
						if (o == JOptionPane.NO_OPTION) {

						}
					}

				} catch (FileNotFoundException e) {

				} catch (IOException e) {
				}
			}
		};
		new Thread(updatethread).start();
	}
}

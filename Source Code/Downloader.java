import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.SwingUtilities;

public class Downloader {

	static String q = File.separator;
	static File zipFile;

	public static void Download(URL fileLink, String zipName, int whatIs) {
		/*
		 * whatIs determines what to do with the downloaded file. 0 is for the Modpack 1
		 * is for the Update 2 is for the Resource Packs
		 */

		/*
		 * Resource Pack Naming! Aubrey's Custom Resource Pack (ACRP)
		 * 
		 * ACRP-TO = Textures Only ACRP-MS - Game Music and Sounds ACRP-MST = Game
		 * Music, Sounds and Textures ACRP-AS = Ambiance Music and Sounds ACRP-E =
		 * Everything
		 */

		Runnable updatethread = new Runnable() {
			public void run() {
				try {
					HttpURLConnection httpConnection = (HttpURLConnection) (fileLink.openConnection());
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
					// zipFile needs to be set somehow. Or, some kind of actual file name needs to
					// be set.
					// Currently, the zipFile is null. Obviously, that's going to cause issues.
					System.out.println(" Download Complete!");
					GUI.progress.setValue(0);
					zipFile = new File(q + Driver.getDownloadsLocation() + q + zipName);
					if (whatIs == 0) {
						// Checksum it.
						Checksums.checksum(zipFile, "Modpack.zip");
					}

					if (whatIs == 1) {
						// Update
						if (!Install.featuresUsed) {
							String updateZip = Driver.getDownloadsLocation() + q + Updater.currentVersion + ".zip";
							String updateFolder = Driver.getDownloadsLocation() + q + Updater.currentVersion;
							Extractor.Extract(updateZip, updateFolder, 1);
						}
						if(Install.featuresUsed) {
							installOptions.again();
						}
						// Extract and move or copy the update files to the mod directory.
					}

					if (whatIs == 2) {
						// Resource Packs
						resourcePacks.creditsFrame.setVisible(false);
						Checksums.checksum(zipFile, zipName);
					}
				}

				catch (FileNotFoundException e) {
					GUI.errors.setText("Roserade");
				} catch (IOException e) {
					GUI.errors.setText("Jumpluff");
				}
			}
		};
		new Thread(updatethread).start();
	}
}

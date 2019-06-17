import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.SwingUtilities;

public class Downloader {
	public static void Downloader(URL packLink) {
		// consoleOutput.append("\nDownloading");
		// console.append("\nDownloading...");
		System.out.println("\n Downloading...");

		Runnable updatethread = new Runnable() {
			public void run() {
				try {
					HttpURLConnection httpConnection = (HttpURLConnection) (packLink.openConnection());
					long completeFileSize = httpConnection.getContentLength();
					java.io.BufferedInputStream in = new java.io.BufferedInputStream(httpConnection.getInputStream());
					java.io.FileOutputStream fos = new java.io.FileOutputStream(File.separator + Driver.getDownloadsLocation() + File.separator + Driver.zipFile);
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
					System.out.println(" There is an odd bug with extraction where progress will not show.");
					System.out.println(" Don't worry, it hasn't frozen. It is extracting.");
					System.out.println(" This will take roughly 10 to 15 minutes to complete.");
					System.out.println(" The installer will output when finished");
					System.out.println(" Beginning Extraction now...\n");
					Extractor.Extractor();
					
				} catch (FileNotFoundException e) {
					
				} catch (IOException e) {
				}
			}
		};
		new Thread(updatethread).start();
	}
}


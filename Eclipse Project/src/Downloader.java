import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Downloader {
    static File zipFile;

    public static void Download(URL fileLink, String zipName, int whatIs) {
        /*
         * whatIs determines what to do with the downloaded file. 0 is for the Modpack 1
         * is for the Update 2 is for the Resource Packs
         *
         * Resource Pack Naming! Aubrey's Custom Resource Pack (ACRP)
         *
         * ACRP-TO = Textures Only ACRP-MS - Game Music and Sounds ACRP-MST = Game
         * Music, Sounds and Textures ACRP-AS = Ambiance Music and Sounds ACRP-E =
         * Everything
         */
        Runnable updatethread = () -> {
            try {
                HttpURLConnection httpConnection = (HttpURLConnection) (fileLink.openConnection());
                long completeFileSize = httpConnection.getContentLength();
                java.io.BufferedInputStream in = new java.io.BufferedInputStream(httpConnection.getInputStream());
                java.io.FileOutputStream fos = new java.io.FileOutputStream(
                        Common.q + Common.getDownloadsLocation() + Common.q + zipName);
                BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
                byte[] data = new byte[1024];
                long downloadedFileSize = 0;
                int x = 0;
                while ((x = in.read(data, 0, 1024)) >= 0) {
                    downloadedFileSize += x;
                    // calculate progress
                    final int currentProgress = (int) ((((double) downloadedFileSize) / ((double) completeFileSize))
                            * 100000d);
                    // update progress bar
                    SwingUtilities.invokeLater(() -> GUI.progress.setValue(currentProgress));
                    bout.write(data, 0, x);
                }
                bout.close();
                in.close();

               // System.out.println(Strings.installerDownloadComplete);
                GUI.progress.setValue(0);
                zipFile = new File(Common.q + Common.getDownloadsLocation() + Common.q + zipName);
                switch (whatIs) {
                    case 0: //Part one.
                        Install.backupMinecraftContent();
                        Extractor.Extract(zipFile.getAbsolutePath(), 0);
                        break;
                    case 1:
                        Extractor.Extract(zipFile.getAbsolutePath(), 1);
                        break;
                    case 2:
                        resourcePacks.creditsFrame.setVisible(false);
                        break;
                    case 3: //Part one only. Extract,  install then do part two.
                        if (Install.featuresUsed) {
                            installOptions.again();
                        }
                        //Updater.getFileUpdate(new URL(Common.modpackPartTwoLink), 1);
                        break;
                    default:
                        break;
                }
            } catch (FileNotFoundException e) {
                // If the zip file could not be found.
                GUI.errors.setText("Roserade");
            } catch (IOException e) {
                // Generic IO error. Who knows what broke here.
                GUI.errors.setText("Jumpluff");
            }
        };
        new Thread(updatethread).start();
    }

    public static void downloadNoProgress(String url, File name){
        //No progress because it doesn't output to the progress bar. Used for real small files like the font.
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(name)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            // handle exception
        }
    }
}

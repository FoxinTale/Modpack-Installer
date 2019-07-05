import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileSystemView;

@SuppressWarnings("unused")
public class Extractor {
	public static void Extractor(String fileName, String loc) {
		String outputFolder = Driver.getDownloadsLocation() + File.separator + loc + File.separator;

		File zipLocation;
		// try {
		String zipFile = fileName;
		// }
		/*
		 * catch(FileNotFoundException n) { String message =
		 * "Unable to locate zip file."; JOptionPane.showMessageDialog(new JFrame(),
		 * message, "File Not Found.", JOptionPane.ERROR_MESSAGE); JFileChooser findFile
		 * = new JFileChooser();
		 * //findFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); int r =
		 * findFile.showSaveDialog(null); if (r == JFileChooser.APPROVE_OPTION) {
		 * zipLocation = findFile.getSelectedFile(); String zipFile =
		 * zipLocation.getName();
		 * 
		 * Driver.minecraftInstall = Driver.minecraftInstallLocation.getAbsolutePath();
		 * } // if the user cancelled the operation else { String messageTwo =
		 * "Cannot continue without Modpack!"; JOptionPane.showMessageDialog(new
		 * JFrame(), messageTwo, " Search Cancelled", JOptionPane.ERROR_MESSAGE);
		 * System.exit(0); }
		 * 
		 * 
		 * }
		 */

		try (ZipFile file = new ZipFile(zipFile)) {
			FileSystem fileSystem = FileSystems.getDefault();
			// Get file entries
			Enumeration<? extends ZipEntry> entries = file.entries();

			// We will unzip files in this folder
			String uncompressedDirectory = outputFolder;
			try {
				Files.createDirectory(fileSystem.getPath(uncompressedDirectory));

			} catch (FileAlreadyExistsException e) {

			}
			while (entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();
				// If directory then create a new directory in uncompressed folder
				if (entry.isDirectory()) {

					Files.createDirectories(fileSystem.getPath(uncompressedDirectory + entry.getName()));
				}
				// Else create the file
				else {
					InputStream is = file.getInputStream(entry);
					BufferedInputStream bis = new BufferedInputStream(is);
					String uncompressedFileName = uncompressedDirectory + entry.getName();
					Path uncompressedFilePath = fileSystem.getPath(uncompressedFileName);
					Files.createFile(uncompressedFilePath);
					FileOutputStream fileOutput = new FileOutputStream(uncompressedFileName);
					while (bis.available() > 0) {
						fileOutput.write(bis.read());
					}
					fileOutput.close();

				}
			}

			System.out.println(" Extraction Complete\n");
			Install.install();
		} catch (IOException e) {
			GUI.errors.setText("File not found.");
		}
	}
}

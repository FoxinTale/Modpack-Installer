import java.io.File;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class Extractor {
	static String q = File.separator;

	public static void Extract(String fileLocation, String name, int op) {

		File modpack = new File(Driver.getDownloadsLocation() + q + "Modpack");
		if (modpack.exists()) {
			modpack.delete();
			// This deletes a modpack folder in the downloads if it exists already.
			// If this wasn't done, the extract would fail.
		}
		String folderPath = Driver.getDownloadsLocation() + q + name + q;
		String password = "";

		if (op == 0) {
			// For the modpack
			System.out.println(" Extracting modpack. Expect system lag.");
			unzip(fileLocation, folderPath, password, op);
		}

		if (op == 1) {
			unzip(fileLocation, name, password, op);
		}

		if (op == 2) {
			System.out.println(" Extracting resource pack. Expect system lag.");
			unzip(fileLocation, name, password, op);
		}
	}

	public static void unzip(String zipFilePath, String extractFolder, String password, int op) {
		/*
		 * 0 is the modpack 1 is an update 2 is the ambiance pack
		 */
		try {
			ZipFile zipFile = new ZipFile(zipFilePath);
			if (zipFile.isEncrypted()) {
				zipFile.setPassword(password);
			}
			zipFile.extractAll(extractFolder);
		} catch (ZipException e) {
			GUI.errors.setText("Kyogre");
		}
		System.out.println(" Extraction complete.");
		if (op == 0) {
			Install.install();
		}
		if (op == 1) {
			Updater.installUpdate();
		}

		if (op == 2) {
			Install.resourceEnd();
		}
	}
}

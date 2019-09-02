import java.io.File;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class Extractor {
	static String q = File.separator;

	public static void Extract(String fileName, String loc) {

		String zipFilePath = fileName;
		File modpack = new File(Driver.getDownloadsLocation() + q + "Modpack");
		String folderPath = Driver.getDownloadsLocation() + q + loc + q;
		String password = "";
		if (modpack.exists()) {
			modpack.delete();
		}
		unzip(zipFilePath, folderPath, password);
	}

	public static void unzip(String zipFilePath, String folderPath, String password) {
		try {
			ZipFile zipFile = new ZipFile(zipFilePath);
			if (zipFile.isEncrypted()) {
				zipFile.setPassword(password);
			}
			zipFile.extractAll(folderPath);
		} catch (ZipException e) {
			GUI.errors.setText("Kyogre");
		}
		System.out.println(" Extraction complete.");
		if (!Driver.updateTime) {
			Install.install();
		}
		if (Driver.updateTime) {
			Updater.installUpdate();
		}
	}
}

import java.io.File;
import java.io.IOException;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class Extractor {
	public static void Extractor(String fileName, String loc) {
		String zipFilePath = fileName;
		File modpack = new File(Driver.getDownloadsLocation() + File.separator + "Modpack");
		String folderPath = Driver.getDownloadsLocation() + File.separator + loc + File.separator;
		String password = "";
		String zipFile = fileName;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Extraction complete.");
		Install.install();
	}
}

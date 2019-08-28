package fileExtract;

import java.io.File;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class main {

	public static void main(String[] args) {
		String folderName = "Modpack";
		File modpack = new File("C:\\Users\\Alyan\\Downloads\\" + File.separator + "Modpack");
		String targetPath = "C:\\Users\\Alyan\\Downloads\\";
        String zipFilePath = modpack.getPath() + ".zip";
        String unzippedFolderPath = "C:\\Users\\Alyan\\Downloads\\" + folderName;
        String password = ""; // keep it EMPTY<""> for applying no password protection
       //  zip(targetPath, zipFilePath, password);
        unzip(zipFilePath, unzippedFolderPath, password);

	}

	public static void zip(String targetPath, String destinationFilePath, String password) {
		try {
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

			if (password.length() > 0) {
				parameters.setEncryptFiles(true);
				parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
				parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
				parameters.setPassword(password);
			}

			ZipFile zipFile = new ZipFile(destinationFilePath);

			File targetFile = new File(targetPath);
			if (targetFile.isFile()) {
				zipFile.addFile(targetFile, parameters);
			} else if (targetFile.isDirectory()) {
				zipFile.addFolder(targetFile, parameters);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void unzip(String targetZipFilePath, String destinationFolderPath, String password) {
		
	}

}

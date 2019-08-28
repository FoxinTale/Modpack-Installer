import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class Main {

	public static void main(String[] args) throws IOException {

		
			String username = System.getProperty("user.name");
			File downloads = new File("C:\\Users\\" + username + "\\Downloads");
			String downloadsLocation = downloads.getCanonicalPath();
			String zipFile = downloadsLocation + "\\Modpack.zip";
			String outputFolder = downloadsLocation + "\\Modpack\\";

			File modpackLocation = new File(downloadsLocation + "\\Modpack");
			File modpackMods = new File(modpackLocation + "\\mods");
			File modpackConfig = new File(modpackLocation + "\\config");
			File modpackFlans = new File(modpackLocation + "\\Flan");

			/*
			if (!modpackLocation.exists()) {
				modpackLocation.mkdir();
			}

			if (!modpackFlans.exists()) {
				modpackFlans.mkdir();
			}

			if (!modpackMods.exists()) {
				modpackMods.mkdir();
			}

			if (!modpackConfig.exists()) {
				modpackConfig.mkdir();
			}
		*/
	
	 //Open the file
    try(ZipFile file = new ZipFile(zipFile))
    {
        FileSystem fileSystem = FileSystems.getDefault();
        //Get file entries
        Enumeration<? extends ZipEntry> entries = file.entries();
         
        //We will unzip files in this folder
        String uncompressedDirectory = outputFolder;
        try {
        Files.createDirectory(fileSystem.getPath(uncompressedDirectory));
        }
        catch(FileAlreadyExistsException e) {
        System.out.println("It exists");
        }
        //Iterate over entries
        while (entries.hasMoreElements())
        {
            ZipEntry entry = entries.nextElement();
            //If directory then create a new directory in uncompressed folder
            if (entry.isDirectory())
            {
                System.out.println("Creating Directory:" + uncompressedDirectory + entry.getName());
                Files.createDirectories(fileSystem.getPath(uncompressedDirectory + entry.getName()));
            }
            //Else create the file
            else
            {
                InputStream is = file.getInputStream(entry);
                BufferedInputStream bis = new BufferedInputStream(is);
                String uncompressedFileName = uncompressedDirectory + entry.getName();
                Path uncompressedFilePath = fileSystem.getPath(uncompressedFileName);
                Files.createFile(uncompressedFilePath);
                FileOutputStream fileOutput = new FileOutputStream(uncompressedFileName);
                while (bis.available() > 0)
                {
                    fileOutput.write(bis.read());
                }
                fileOutput.close();
                System.out.println("Written :" + entry.getName());
            }
        }
    }
    catch(IOException e)
    {
        e.printStackTrace();
    }
}
}
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Main {

	@SuppressWarnings("unused")
	public static void main(String[] args){

		String username = System.getProperty("user.name"); 

		File downloads = new File("C:\\Users\\" + username + "\\Downloads");
		File minecraftDefaultPath = new File("C:\\Users\\" + username + "\\AppData\\Roaming\\.minecraft");
		File desktop = new File("C:\\Users\\" + username + "\\Desktop");

		String downloadsLocation = downloads.getAbsolutePath();
		String minecraftDefaultInstall = minecraftDefaultPath.getAbsolutePath();
		String desktopLocation = desktop.getAbsolutePath();

		File minecraftMods = new File(minecraftDefaultInstall + "\\mods");
		File minecraftConfig = new File(minecraftDefaultInstall + "\\config");
		File minecraftFlans = new File(minecraftDefaultInstall + "\\Flan");

		String modsLocation = minecraftMods.getAbsolutePath();
		String configLocation = minecraftConfig.getAbsolutePath();

		File backups = new File(desktopLocation + "\\Minecraft Stuff");
		String backupsLocation = backups.getAbsolutePath();
		File backupMods = new File(backupsLocation + "\\Mods");
		File backupConfig = new File(backupsLocation + "\\Config");

		String backupModsLocation = backupMods.getAbsolutePath();
		String backupConfigLocation = backupConfig.getAbsolutePath();

		File modpackLocation = new File(downloadsLocation + "\\Modpack");
		File modpackMods = new File(modpackLocation + "\\mods");
		File modpackConfig = new File(modpackLocation + "\\config");
		File modpackFlans = new File(modpackLocation + "\\Flan");

	try {	// This double checks to make sure all the needed directories are there.
		if (!backups.exists()) {
			backups.mkdir();
		}

		if (!backupMods.exists()) {
			backupMods.mkdir();
		}

		if (!backupConfig.exists()) {
			backupConfig.mkdir();
		}

		if (!modpackLocation.exists()) {
			modpackLocation.mkdir();
		}
		// This handles the actual moving of files, but it moves the actual folders too.
		if (minecraftMods.exists()) {
			if (backupMods.exists()) {
				Files.move(minecraftMods.toPath(), backupMods.toPath(), StandardCopyOption.REPLACE_EXISTING);
			}
		}

		if (minecraftConfig.exists()) {
			if (backupConfig.exists()) {
				Files.move(minecraftConfig.toPath(), backupConfig.toPath(), StandardCopyOption.REPLACE_EXISTING);
			}
		}

		// Recreating the folders in the Minecraft directory, and making the folder for Flan's Mod.

		if (!minecraftMods.exists()) {
			minecraftMods.mkdir();
		}

		if (!minecraftConfig.exists()) {
			minecraftConfig.mkdir();
		}
		
		if(!minecraftFlans.exists()){
			minecraftFlans.mkdir();
		}
			

		// Moving the information downloaded in the modpack directory from Github. The
		// final folder structure is yet to be determined.
		if(modpackMods.exists()) {
			if(minecraftMods.exists()) {
				Files.move(modpackMods.toPath(), minecraftMods.toPath(), StandardCopyOption.REPLACE_EXISTING);
			}
		}
		
		if(modpackConfig.exists()) {
			if(minecraftConfig.exists()) {
				Files.move(modpackConfig.toPath(), minecraftConfig.toPath(), StandardCopyOption.REPLACE_EXISTING);
			}
		}
		
		if(modpackFlans.exists()) {
			if(minecraftFlans.exists()) {
				Files.move(modpackFlans.toPath(), minecraftFlans.toPath(), StandardCopyOption.REPLACE_EXISTING);
			}
		}
		
		//I always check if the directory exists, because you never know. Something could have gone wrong. 
		//Better to catch an error that likely won't ever happen that to have it actually happen and the 
		//program crash.
	}
	catch(IOException e) {
		System.out.println("Something went wrong during the file handling process");
		//Display this as a GUI error, then terminate the program.
	}
	
	catch(Exception e) {
		System.out.println("Something broke. I am unsure what, but if you see this, please alert the developer.");
	}
		
		
	}

}

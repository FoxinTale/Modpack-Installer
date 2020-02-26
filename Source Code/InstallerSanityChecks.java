import java.io.File;

public class InstallerSanityChecks {

	public static void check(int option) {
		switch (option) {
		case 0:
			optionsGUI.otherOptionsGUI();
			break;
		case 1:
			modOptions.modOptionsGui();
			break;
		case 2:
			resourcePacks.bigPack();
			break;
		case 3:
			resourceCheck.sigarCheck();
			break;
		case 4:
			System.out.println("Beginning read and write");
			String args = "-Xmx4G -XX:+UnlockExperimentalVMOptions -XX:+UseG1GC -XX:G1NewSizePercent=20 -XX:G1ReservePercent=20 -XX:MaxGCPauseMillis=50 -XX:G1HeapRegionSize=32M";
			Json.readProfileData(6144);
			break;
		case 5:
			installOptions.verifyInstall();
			break;
		case 6:
			System.out.println(" Running checksum verification.");
			String zipName = "Modpack.zip";
			String q = File.separator;
			File zipFile = new File(q + Driver.getDownloadsLocation() + q + zipName);
			Checksums.checksum(zipFile, zipName);
			break;
		case 7:
			Install.install();
			break;
		default:
			break;
		}
	}
}

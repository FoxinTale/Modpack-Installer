package launcherSet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Driver {

	public static void main(String[] args) {
		File launcherSettings = new File("C:\\Users\\Alyan\\Desktop\\launcher_profiles.json");
		ArrayList<String> launcherData = new ArrayList<>();
		
		try {		
			BufferedReader reader = new BufferedReader(new FileReader(launcherSettings));
			String s = "";
			String line = "";
			StringBuilder thing = new StringBuilder();
			while ((s = reader.readLine()) != null) {
				thing.append(s);

				thing.trimToSize();
				line = thing.toString().trim();
				line.trim();
				launcherData.add(line);
				thing.delete(0, thing.length());
			}
			reader.close();

			int versionPos = launcherData.indexOf("\"lastVersionId\" : \"1.7.10-Forge10.13.4.1614-1.7.10\",");
			int argsPos = versionPos - 2;
			String newArgs = "\"javaArgs\" :\"-XX:HeapDumpPath=MojangTricksIntelDriversForPerformance_javaw.exe_minecraft.exe.heapdump -XX:+UseG1GC -Xmx4G -Xms2G -XX:+UnlockExperimentalVMOptions -XX:G1NewSizePercent=20 -XX:G1ReservePercent=20 -XX:MaxGCPauseMillis=50 -XX:G1HeapRegionSize=32M\",";
			launcherData.set(argsPos, newArgs);
			
			Object[] settingsArray = launcherData.toArray();
		
			launcherSettings.delete();
			FileWriter newSettings = new FileWriter("C:\\Users\\Alyan\\Desktop\\launcher_profiles.json");
			  for (int i = 0; i < settingsArray.length; i++) {
			      newSettings.write(settingsArray[i] + "\n");
			    }
			    newSettings.close();

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}

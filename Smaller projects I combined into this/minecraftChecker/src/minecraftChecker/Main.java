package minecraftChecker;

import java.io.File;

public class Main {

	public static void main(String[] args) {
		String username = System.getProperty("user.name");
		File minecraftDefaultPath = new File("C:\\Users\\" + username + "\\AppData\\Roaming\\.minecraft");
		File vanillaMinecraft = new File(minecraftDefaultPath + "\\versions\\1.7.10\\1.7.10.jar");
		File vanillaMinecraftConfig = new File(minecraftDefaultPath + "\\versions\\1.7.10\\1.7.10.json");
		File moddedMinecraftConfig = new File(minecraftDefaultPath + "\\versions\\1.7.10-Forge10.13.4.1614-1.7.10\\1.7.10-Forge10.13.4.1614-1.7.10.json");

		if (vanillaMinecraft.exists() && vanillaMinecraftConfig.exists()) {
			System.out.println("Vanilla Minecraft found.");
	
		}

		if (!vanillaMinecraft.exists() || !vanillaMinecraftConfig.exists()) {
			System.out.println("Plase run Vanilla Minecraft 1.7.10 at least once before continuing.");
			
		}
		
		if(moddedMinecraftConfig.exists()) {
			System.out.println("Modded Minecraft found. Continuing.");
		}

	}

}

# What is this?
A simple modpack installer, coded in Java, using Swing as the GUI.

## What does it do?

Upon selecting the option of 1.7.10 modpack, it begins to download a zip file from an Amazon S3 server, and saves it to your downloads directory.
After the download finishes, it extracts the zip to a folder named "Modpack", also located in your downloads directory.

When that finishes, it looks in your Minecraft directory for a mods, config, and flans folder. If it sees them, it then moves them to a folder
on your desktop named "Minecraft stuff" It does this even if the folders are empty. It then remakes the folders in the minecraft directory, 
and moves the extracted files over to the minecraft folder. 

This then completes the installation process. All the user needs to do is verify they have the proper version of forge for 1.7.10 installed.
The version being 1.7.10 - 10.13.4.1614, and set their Minecraft Launcher to that, and update their allocated RAM to Minecraft, if needed. 
And that's it! Modpack fully installed.

Major Update! 7-5-2019

What I did in this update is remove some of the commented out code from within, added an entirely new component, the updater and it's GUI.
That knows which is the latest update, and it downloads and extracts it for you. It does not install yet, but I'm working on it.

To the main GUI, I added and removed a small bit. The "Other Modpack" option is now gone, replaced with "Download pack" 
Also, when the install finishes, it asks if you would like to check if the server is up or not. 

#### To do list:
- Make the updater component actually install the update.
- Optimize the code, if possible.
- Possibly add a forge auto-detector, and notify the user if forge is not found that they need to install it.
- Possibly add a feature that auto adjusts the allocated RAM for the profile, and sets the launcher profile to the pack.
- Document the source code.





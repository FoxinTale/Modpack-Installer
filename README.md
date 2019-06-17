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


#### To do list:
- Add a pack updater feature so users don't need to redownload the entire pack each time.
- Add a forge auto-detector, and notify the user if forge is not found that they need to install it.
- Possibly add a feature that auto adjusts the allocated RAM for the profile, and sets the launcher profile to the pack.
- Document the source code.
- Add in a feature that auto-pings the server to see if it is up when the install finishes.




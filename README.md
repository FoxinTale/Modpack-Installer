A simple modpack installer, coded in Java, using Swing as the GUI.

For those who are curious as to what exactly this does, well...

Upon selecting the option of 1.7.10 modpack, it begins to download a zip file from an Amazon S3 server, and saves it to your downloads directory.
After the download finishes, it extracts the zip to a folder named "Modpack", also located in your downloads directory.

on your desktop named "Minecraft stuff" It does this even if the folders are empty. It then remakes the folders in the minecraft directory, 
and moves the extracted files over to the minecraft folder. 
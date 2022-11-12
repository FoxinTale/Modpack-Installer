# What is this?
A simple modpack installer, coded in Java, using Swing as the GUI.
Licensed under the GNU General Public License, V3.

## What does it do?
It downloads and installs a modpack for a modded Minecraft server.

## Well, what's this got?
- Automatic modpack installation.
- An option to download the pack file for manual installation.
- Automatically backs up any pre-existing mods!
- An option to ping the server to see if it is up, upon completion of the installation.
- Oh, and since this is made in Java, it runs on Windows, Mac and Linux, making it platform agnostic.
- If it cannot find your default directories, it asks to be pointed to them.
- A nifty little feature that checks if the modpack got corrupted during its download.
- An aesthetically pleasing user interface.
- Multiple "failsafes" so it should be hard to break.

##### What is the "Other projects" folder?
This is, as it says. The smaller components that I made on their own to verify that they work on their own, before combining into this. It is how I decided to go about this project. Feel free to look at each one to see how it works.

#### To do list:
- Figure out how to Utilize and apply CurseForge's API. This will mean I do not have to host them as a separate download on a server...which was painful to setup.
- Figure out how to handle updates properly. Should it remove files that are no longer needed if it happens? 


#### Credits:
The following libraries are used in this installer:
- [Zip4j](https://mvnrepository.com/artifact/net.lingala.zip4j/zip4j), used for zip file extraction.
- [Org.json](https://mvnrepository.com/artifact/org.json/json), used for parsing Github's JSON based API.
- [Apache Commons IO](https://mvnrepository.com/artifact/org.apache.commons/commons-io/1.3.2) For file utilities.
- [Apache Common Collections](https://mvnrepository.com/artifact/org.apache.commons), for verifying all the mods installed correctly.



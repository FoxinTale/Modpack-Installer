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
<<<<<<< HEAD
=======

### Major Update 7-5-2019

What I did in this update is remove some of the commented out code from within, added an entirely new component, the updater and it's GUI.
That knows which is the latest update, and it downloads and extracts it for you. It does not install yet, but I'm working on it.

To the main GUI, I added and removed a small bit. The "Other Modpack" option is now gone, replaced with "Download pack" 
Also, when the install finishes, it asks if you would like to check if the server is up or not. 

### Major Update 8-19-2019

Oh, this is a massive update. While the update component is still unfinished, it has been totally reworked and overhauled. In addition to this, I've added so many features I'm surprised it all works. I'm damn proud of this now. It now has an option to set your Java arguments and adjust your ram via a GUI. 
I also made the GUI a bit prettier, and added full Linux and Mac file system compatibility.

### Major Update 9-02-2019

Reworked the GUI to be even prettier, added a new option and a new popup menu with the smaller options. Hopefully fixed the crash when setting the amount of ram via the GUI. Added a feature that when the installer launches, looks for Vanilla Minecraft and Forge 1.7.10. If it cannot find either,
it notifies the user. There are also "failsafes", that if you are sure that the various file operations throw a warning, you can actually point the installer to the proper location, because sometimes things wonk out. Fixed a wonky directory for Linux users. Added a pretty image background and a custom font for the GUI. 

And of course...The updater. This was actually rather simple to implement, but getting it to work together with everything wasn't easy.

#### Minor Update 9-05-2019
Added a version number at the bottom of the GUI. As the download is in a folder, with its resources, I also added a feature that if someone moved it out of that folder, it knows, and asks to be put back. Fixed an small error that was being thrown when the update option was ran.

### Overhaul Update #1 11-06-2019
Added a feature that automatically backs up the launcher settings, in case something goes wrong during setting the memory. Also added a restore option for the backed up launcher settings, under the "More Options". As well as options to download the rather large custom packs. Rewrote the downloader to be able to check the integrity of more than one file. Re-did the internal code structure, splitting up the workload of the classes.

Rearranged the main options. Split the downloader and file verification. Added contact information for bug reports. Added ability to select between the multiple resource packs, and also included a credits window that pops up while the pack is downloading. Oh, and over 1,000 lines of additional code. Redid how the main options were chosen. And, finally..fixed a few grammatical errors.

### Minor update 11-17-2019
Removed the usage of the memory adjuster, as it seems to not work anymore. It is still there, but unused while I fix it.
Redid the modpack installation verification. 

### Overhaul Update #2 12-13-2019
Rewrote the entire file verification, and installation verification methods. Changed from reading a webpage to using Json files. Added a little error dictionary
for whenever an error occurs, it says what caused it, and how to fix it. Work on the memory adjustment is still in progress. Also fixed a few more reported bugs.
Not much visual changes. Just a lot of under the hood changes going on. 

### Total rewrite 1
Absorbed all external libraries to in it to reduce the chance of a missing library causing the program to not work. Removed the external font and image for the same reason the libraries were absorbed.


#### What is the WTFPL?
This program is free software. It comes without any warranty, to the extent permitted by applicable law. 
You can redistribute it and/or modify it under the terms of the Do What The Fuck You Want To Public License, Version 2, as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.

>>>>>>> 4c262ff367eb3accdaa381630f05697bd2bac80e

##### What is the "Other projects" folder?
This is, as it says. The smaller components that I made on their own to verify that they work on their own, before combining into this. It is how I decided to go about this project. Feel free to look at each one to see how it works.

#### Note.
If you feel like it, go support the file host gofile.io. It's run by a few people and is powered by user donations. Not a sponsorship or partnership at all, but I think 
it should be mentioned.  

#### To do list:
- Figure out how to Utilize and apply CurseForge's API. This will mean I do not have to host them as a separate download on a server...which was painful to setup.
- Figure out how to handle updates properly. Should it remove files that are no longer needed if it happens? 


#### Credits:
The following libraries are used in this installer:
- [Zip4j](https://mvnrepository.com/artifact/net.lingala.zip4j/zip4j), used for zip file extraction.
- [Org.json](https://mvnrepository.com/artifact/org.json/json), used for parsing Github's JSON based API.
- [Apache Commons IO](https://mvnrepository.com/artifact/org.apache.commons/commons-io/1.3.2) For file utilities.
- [Apache Common Collections](https://mvnrepository.com/artifact/org.apache.commons), for verifying all the mods installed correctly.



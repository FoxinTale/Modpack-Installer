# What is this?
A simple modpack installer, coded in Java, using Swing as the GUI.
Licensed under the WTFPL Version 2.

## What does it do?

Upon selecting the option of 1.7.10 modpack, it begins to download a zip file from an Amazon S3 server, and saves it to your downloads directory. After the download finishes, it extracts the zip to a folder named "Modpack", also located in your downloads directory.

When that finishes, it looks in your Minecraft directory for a mods, config, and flans folder. If it sees them, it then moves them to a folder on your desktop named "Minecraft stuff" It does this even if the folders are empty. It then remakes the folders in the minecraft directory, and moves the extracted files over to the minecraft folder. 

This then completes the installation process. All the user needs to do is verify they have the proper version of forge for 1.7.10 installed. The version being 1.7.10 - 10.13.4.1614, and set their Minecraft Launcher to that, and update their allocated RAM to Minecraft, if desired. And that's it! Modpack fully installed. 

## Well, what's this got?
##### Oh, this little thing does quite a lot.
- Automatic modpack installation.
- An option to download the pack file for manual installation.
- Automatically backs up any pre-existing mods!
- An option to ping the server to see if it is up, upon completion of the installation.
- Oh, and since this is made in Java, it runs on Windows, Mac and Linux, making it platform agnostic.
- Licensed under the WTFPL V2, allowing you to do anything you please with the code. Modify it to your specific usage, or improve it!
- If it cannot find your default directories, it asks to be pointed to them.
- Features a GUI slider that allows you to allocate your Memory to Minecraft, and set Java arguments to optimized ones for you.
- A nifty little feature that checks if the modpack got corrupted during its download.
- An aesthetically pleasing user interface.
- It also has the ability to download updates for the modpack too!
- Multiple "failsafes" so it should be hard to break.
- Over 2800 lines of code!


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

#### What is the WTFPL?
This program is free software. It comes without any warranty, to the extent permitted by applicable law. 
You can redistribute it and/or modify it under the terms of the Do What The Fuck You Want To Public License, Version 2, as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.

##### Class File Code Count Breakdown
The amount of code doesn't really mean anything overall, It's just here for curious folks.
- assetCredits : 201
- Checksums : 167
- CustomOutputStream : 23
- Downloader : 110
- Driver : 157
- Errors : 116
- Extractor : 63
- GUI : 341
- Install : 205
- installOptions : 189
- JHyperlink : 66
- Json : 111
- Memory : 182
- optionsGUI: 208
- osDetect : 100
- resourcePacks: 407
- RoundedPanel : 56
- Updater : 110
- Total lines of code : 2812

##### What is the "Other projects" folder?
This is, as it says. The smaller components that I made on their own to verify that they work on their own, before combining into this. It is how I decided to go about this project. Feel free to look at each one to see how it works.


#### To do list:
- Bugfix as needed
- Add new features, if one gets a good idea.
- Eventually, transfer to using FTP download. AWS is nice, but it does have its limits.





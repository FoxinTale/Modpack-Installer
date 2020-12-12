public class Notes {

    //This will remain unused and will be deleted before release. This exists only to help me keep track of my thoughts.



    /*
     * Code comment to do list for the whole installer.
     *:
     * Update checking: (In progress)
     * - Figure out how to check if an update is available for each modpack part. (Done)
     * - Check if part one is or is not installed.
     * - Check if an update is available for part one if it is,  download and install.
     * - Check if part two is or is not installed.
     * - Check if an update is available for part two, if it is then download and install it.
     *
     * - The checking for each part can probably be accomplished by checking if one or more mods/files from each part exist.
     * - Part update checking can probably be accomplished via creating a file named "modpackInfo.json" storing the current version of part one and two.
     *     At launch the installer would check for the file, get the values and compare it to the versions pulled from the Github API. (In progress)
     *
     *
     * Testing things:
     * - Test file extraction with the newly merged libraries.
     * - Test json file creation and writing.
     * - Ensure that when part one is finished the installer does not quit and fires back for part two.
     *    After part two, it then goes into its previous routine of doing install-y things.
     * - Do a full test runthrough to make sure it all works. This will be the final thing completed.
     *
     *
     * Thing(s) to fix:
     * - Change the old AWS S3 links over to whichever file host I choose.
     *
     *
     * Thing(s) to add:
     * - Installer "remembering" installation paths.
     *     This can probably utilize the previously created Json file and store the paths for downloads and desktop, as apparently this had issues.
     * - Add the presence footsteps resources to modpack part one.
     *
     * Minecraft launcher memory adjustment:
     * - Verify that changing the memory in the launcher still works and Microsoft didn't rework it over the past year.
     * - Remove all usage of Sigar due to it not working without being used externally.
     * - Replace it with a slider with 1 Gb memory increments, from 2 Gb up to 14 Gb.
     *
     *
     * Other:
     * - Change the license from WTFPL V2 to Apache 2, or...GPL V3.
     * - Maybe remove the checksums part as I don't really see much of a use for it anymore.
     * - Figure out what more resource packs that were used and double check the usage permissions.
     *    After that, create a new repository with a released version of the texture pack.
     * - Remove InstallerSanityChecks, as that's just for testing the various parts.
     */




    /* For modpack update checking.
     *
     * Check if the installerinfo.json file exists, if not create it.
     * If it doesn't exist, get and write to it the current version tags of each modpack part.
     * Json will just be a general item, no arrays. "partOneVersion", "partTwoVersion"..
     *
     * If it does exist, this means it is not a first time run. Read from it and compare the existing version to
     *  the version gotten from reading it from the API. If each one is equal, output as text.
     *  If one is not equal, notify the user that an update is available, much like with the installer itself, and carry on.
     *
     *
     *
     * */

}

public class Notes {
    //This will remain unused and will be deleted before release. This exists only to help me keep track of my thoughts.


    /*
     * Code comment to do list for the whole installer.
     *
     * Update checking: (In progress)
     * - Check if part one is or is not installed.
     * - Check if an update is available for part one if it is,  download and install.
     * - Check if part two is or is not installed.
     * - Check if an update is available for part two, if it is then download and install it.
     *
     * - The checking for each part can probably be accomplished by checking if one or more mods/files from each part exist.
     *
     *
     * Testing things:
     * - Test file extraction with the newly merged libraries.
     * - Test json file creation and writing.
     * - Do a full test runthrough to make sure it all works. This will be the final thing completed.
     *
     *
     * Thing(s) to fix:
     * - Change the old file links. Resource packs may be tricky to find. May use gofile.io.
     * - Fix the custom font not showing up.
     *
     * Thing(s) to add:
     * - Installer "remembering" installation paths.
     *     This can probably utilize the previously created Json file and store the paths for downloads and desktop, as apparently this had issues.
     * - Add the presence footsteps resources to modpack part one.
     *
     *
     * Other:
     * - Figure out what more resource packs that were used and double check the usage permissions.
     *    After that, create a new repository with a released version of the texture pack.
     * - Remove InstallerSanityChecks, as that's just for testing the various parts.
     */
}

public class Errors{

    // Ideally, I'd like to use a data structure here, similar to a json file.
    // Kind of like "Error name: Severity, Cause, Fix" with all being a string
    public static String severity = "";
    public static String cause = "";
    public static String fix = "";

    public static void resetErrors(){
        severity = "";
        cause = "";
        fix = "";
    }

    public static void garchomp(){
        severity = "Unknown.";
        cause = "Somehow, the selection was invalid.";
        fix = "Contact me, saying exactly what you did to make this happen.";
    }

    public static void sylveon(){
        severity = "Severe";
        cause = "The JSON file is improperly formatted.";
        fix = "There's not really much you can do, other than tell me.";
    }
    
    public static void shaymin(){
        severity = "Minor";
        cause = "The file could not be found on the server.";
        fix = "Notify me. I likely forgot to set the file as public...";
    }
    
    public static void uxie(){
        severity = "Minor";
        cause = "The font used is in an invalid format";
        fix = "Delete the font in the resources folder.";
    }
    
    public static void roserade(){
        severity = "Severe";
        cause = "The installer could not find the downloaded file.";
        fix = "If the file is not in your downloads, put it there. Else, notify me.";
    }
    
    public static void jumpluff(){
        severity = "Unknown";
        cause = "A generic IO error...";
        fix = "Try putting the file in the downloads folder. ";
    }
    
    public static void blastoise(){
        severity = "Severe";
        cause = "The algorithm used for verifying files is invalid.";
        fix = "You're screwed. There is no fix, as this should never have happened."; 
    }
        
    public static void glameow(){
        severity = "Severe";
        cause = "The installer could not find the file for verification.";
        fix = "If the file is not in your downloads, put it there. Else, notify me.";
    }
    
    public static void luxray(){
        severity = "Severe";
        cause = "The installer could not find the file for extraction.";
        fix = "If the file is not in your downloads, put it there. Else, notify me.";
    }

    public static void kyogre(){
        severity = "Moderate";
        cause = "The zip file is corrupted or in an invalid format.";
        fix = "Restart the installer or let ir re-download the zip file.";
    }

    public static void chickorita(){
        severity = "Low";
        cause = "The installer failed to verify everything installed correctly.";
        fix = "You can ignore it.";
    }

    public static void marill(){
        severity = "Severe";
        cause = "The hostname for the Minecraft server is invalid.";
        fix = "Notify me. This should never happen.";
    }

    public static void mantyke(){
        severity = "Low";
        cause = "While verifying the pack installed, a file could not be found.";
        fix = "Ignore it, unless a mod rejection occurs on connection.";
    }

    public static void zebstrika(){
        severity = "Severe";
        cause = "Somehow, the Json file the installer uses on Github could not be found.";
        fix = "Nothing you can do other than tell me.";
    }

    public static void litwick(){
        severity = "Moderate";
        cause = "Generic IO error while handling the Json file.";
        fix = "No idea. This shouldn't happen.";
    }
}

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

public class modOptions extends installOptions {
    static JCheckBox controllerCheck, musicCheck, surroundingsCheck, footstepCheck, filterCheck, autofishCheck,
            noteblockCheck, brightCheck;
    static boolean useController, useMusic, useSurroundings, useFootsteps, useFilters, useAutofish, useNoteblock,
            useBright = false;
    static JTextArea modInfo = new JTextArea();
    static JScrollPane scroll = new JScrollPane(modInfo);
    static File modsDir = new File(Common.getMinecraftInstallLocation() + Common.q + "mods");

    public static void modOptionsGui() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.setTitle(Strings.optionalModsTitle);
        controllerCheck = new JCheckBox(Strings.optionalModsControllerLabel);
        musicCheck = new JCheckBox(Strings.optionalModsAmbianceLabel);
        surroundingsCheck = new JCheckBox(Strings.optionalModsDynamicLabel);
        footstepCheck = new JCheckBox(Strings.optionalModsFootstepsLabel);
        filterCheck = new JCheckBox(Strings.optionalModsFiltersLabel);
        autofishCheck = new JCheckBox(Strings.optionalModsFishingLabel);
        noteblockCheck = new JCheckBox(Strings.optionalModsNoteblockLabel);
        brightCheck = new JCheckBox(Strings.optionalModsGammabrightLabel);
        modInfo.setText(Strings.optionalModsInfoDefault);

        JButton install = new JButton(Strings.optionalModsInstall);
        JButton nope = new JButton(Strings.optionalModsCancel);

        modsDir = new File(Common.getMinecraftInstallLocation() + "mods" + Common.q);

        modInfo.setLineWrap(true);
        frame.getContentPane().add(scroll);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setBorder(new LineBorder(Color.black, 1, true));

/*        useController = false;
        useMusic = false;
        useSurroundings = false;
        useFootsteps = false;
        useFilters = false;
        useAutofish = false;
        useNoteblock = false;
        useBright = false;*/

        ActionListener controllerEvent = e -> {
            JCheckBox controller = (JCheckBox) e.getSource();
            selectionDo(controller, useController, Strings.optionalModsControllerInfo);
        };
        ActionListener musicEvent = e -> {
            JCheckBox music = (JCheckBox) e.getSource();
            selectionDo(music, useMusic, Strings.optionalModsAmbianceInfo);
        };
        ActionListener footstepsEvent = e -> {
            JCheckBox footsteps = (JCheckBox) e.getSource();
            selectionDo(footsteps, useFootsteps, Strings.optionalModsFootstepInfo);
        };
        ActionListener filtersEvent = e -> {
            JCheckBox filters = (JCheckBox) e.getSource();
            selectionDo(filters, useFilters, Strings.optionalModsFiltersLabel);
        };
        ActionListener surroundingsEvent = e -> {
            JCheckBox surroundings = (JCheckBox) e.getSource();
            selectionDo(surroundings, useSurroundings, Strings.optionalModsDynamicInfo);
        };
        ActionListener autofishEvent = e -> {
            JCheckBox autofish = (JCheckBox) e.getSource();
            selectionDo(autofish, useAutofish, Strings.optionalModsFishingInfo);
        };
        ActionListener noteblockEvent = e -> {
            JCheckBox noteblock = (JCheckBox) e.getSource();
            selectionDo(noteblock, useNoteblock, Strings.optionalModsNoteblockInfo);
        };
        ActionListener brightEvent = e -> {
            JCheckBox gammabright = (JCheckBox) e.getSource();
            selectionDo(gammabright, useBright, Strings.optionalModsGammabrightInfo);
        };

        ActionListener cancelEvent = e -> Install.end();

        ActionListener installEvent = e -> {
            String ambiance = modpackOptions + "ambiance" + Common.q;
            String utility = modpackOptions + "utility" + Common.q;
            if (useController) {
                System.out.println(" Controller support installed");
                modInstall(utility, "JoypadMod-1.7.10.jar");
            }
            if (useMusic) {
                System.out.println(" Music installed");
                modInstall(ambiance, "MusicChoices-1.3_for_1.7.10.jar");
            }
            if (useSurroundings) {
                System.out.println(" Surroundings installed");
                modInstall(ambiance, "DynamicSurroundings-1.7.10-1.0.6.4.jar");
            }
            if (useFootsteps) {
                System.out.println(" Footsteps installcomped");
                modInstall(ambiance, "PresenceFootsteps_r5b__1.7.10.litemod");
            }
            if (useFilters) {
                System.out.println(" Filters installed");
                modInstall(ambiance, "SoundFilters-0.8_for_1.7.X.jar");
            }

            if (useAutofish) {
                System.out.println(" Autofish installed");
                modInstall(utility, "mod_Autofish_0.4.9_mc1.7.10.litemod");
            }
            if (useNoteblock) {
                System.out.println(" Noteblock Display installed");
                modInstall(utility, "mod_noteblockdisplay_1.3.2_mc1.7.10.litemod");
            }
            if (useBright) {
                System.out.println(" Gammabright installed");
                modInstall(utility, "Gammabright v3.3[MC 1.7.10].litemod");
            }
            Install.end();
        };

        nope.setFont(Common.pretty);
        install.setFont(Common.pretty);
        controllerCheck.setFont(Common.pretty);
        musicCheck.setFont(Common.pretty);
        surroundingsCheck.setFont(Common.pretty);
        filterCheck.setFont(Common.pretty);
        footstepCheck.setFont(Common.pretty);
        autofishCheck.setFont(Common.pretty);
        brightCheck.setFont(Common.pretty);
        noteblockCheck.setFont(Common.pretty);
        modInfo.setFont(Common.pretty);

        nope.addActionListener(cancelEvent);
        install.addActionListener(installEvent);
        controllerCheck.addActionListener(controllerEvent);
        musicCheck.addActionListener(musicEvent);
        surroundingsCheck.addActionListener(surroundingsEvent);
        filterCheck.addActionListener(filtersEvent);
        footstepCheck.addActionListener(footstepsEvent);
        autofishCheck.addActionListener(autofishEvent);
        noteblockCheck.addActionListener(noteblockEvent);
        brightCheck.addActionListener(brightEvent);

        controllerCheck.setBounds(20, 25, 200, 20);
        musicCheck.setBounds(20, 50, 200, 20);
        surroundingsCheck.setBounds(20, 75, 200, 20);
        footstepCheck.setBounds(20, 100, 200, 20);
        filterCheck.setBounds(20, 125, 200, 20);
        autofishCheck.setBounds(20, 175, 200, 20);
        noteblockCheck.setBounds(20, 200, 200, 20);
        brightCheck.setBounds(20, 225, 200, 20);

        nope.setBounds(250, 400, 150, 20);
        install.setBounds(50, 400, 150, 20);
        scroll.setBounds(225, 30, 225, 300); // 180

        frame.add(nope);
        frame.add(install);
        frame.add(controllerCheck);
        frame.add(musicCheck);
        frame.add(surroundingsCheck);
        frame.add(footstepCheck);
        frame.add(filterCheck);
        frame.add(autofishCheck);
        frame.add(brightCheck);
        frame.add(noteblockCheck);

        frame.setSize(480, 480);
        frame.setResizable(false);

        frame.setLayout(null);
        frame.setVisible(true);
    }

    public static void selectionDo(JCheckBox cb, Boolean b, String s) {
        if (cb.isSelected()) {
            b = true;
            modInfo.setText("");
            modInfo.setText(s);
        } else {
            b = false;
            modInfo.setText("");
        }
    }

    public static void modInstall(String location, String modName) {
        File mod = new File(location + modName);
        copyFiles(mod, modsDir);
    }

/*    public static void animatedInstall() {
        String animatedOption = modpackOptions + "animations" + Common.q;
        File main = new File(animatedOption + "SmartMoving-1.7.10-15.6.jar");
        File apiOne = new File(animatedOption + "PlayerAPI-1.7.10-1.4.jar");
        File apiTwo = new File(animatedOption + "SmartRender-1.7.10-2.1.jar");
        copyFiles(main, modsDir);
        copyFiles(apiOne, modsDir);
        copyFiles(apiTwo, modsDir);
    }*/
}

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

public class modOptions extends installOptions {

    static JCheckBox controllerCheck, musicCheck, surroundingsCheck, footstepCheck, filterCheck, autofishCheck,
            noteblockCheck, brightCheck;
    static boolean useController, useMusic, useSurroundings, useFootsteps, useFilters, useAutofish, useNoteblock,
            useBright;
    static JTextArea modInfo = new JTextArea();
    static JScrollPane scroll = new JScrollPane(modInfo);
    static Font pretty;
    static String q = File.separator;
    static String installFolder = Driver.getDownloadsLocation() + q + "Modpack" + q + "extras" + q;
    static File modsDir = new File(Driver.getMinecraftInstallLocation() + q + "mods");
    static String s;

    public static void modOptionsGui() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.setTitle("Optional mod installation");
        controllerCheck = new JCheckBox("Controller Support.");
        musicCheck = new JCheckBox("Custom Ambiance Music.");
        surroundingsCheck = new JCheckBox("Dynamic Surroundings.");
        footstepCheck = new JCheckBox("Presence Footsteps.");
        filterCheck = new JCheckBox("Sound Filters.");
        autofishCheck = new JCheckBox("Auto Fishing");
        noteblockCheck = new JCheckBox("Noteblock Display");
        brightCheck = new JCheckBox("Gammabright");
        modInfo.setText(" These are optional. \n" + " You'll be able to connect\n" + " without problems if you \n"
                + " select none of these.");

        JButton install = new JButton("Install!");
        JButton nope = new JButton("Nah, I'm good.");

        modsDir = new File(Driver.getMinecraftInstallLocation() + q + "mods" + q);

        modInfo.setLineWrap(true);
        frame.getContentPane().add(scroll);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setBorder(new LineBorder(Color.black, 1, true));

        useController = false;
        useMusic = false;
        useSurroundings = false;
        useFootsteps = false;
        useFilters = false;
        useAutofish = false;
        useNoteblock = false;
        useBright = false;

        ActionListener controllerEvent = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JCheckBox controller = (JCheckBox) e.getSource();
                s = " Adds support for Xbox \n" + " controllers. I suppose other \n"
                        + " controllers could be used too.";
                selectionDo(controller, useController, s);
            }
        };
        ActionListener musicEvent = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JCheckBox music = (JCheckBox) e.getSource();
                s = " Environmentally aware music. \n" + " Required for the non-texture \n" + " resource packs.";
                selectionDo(music, useMusic, s);
            }
        };

        ActionListener footstepsEvent = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JCheckBox footsteps = (JCheckBox) e.getSource();
                s = " Dynamic sounds for every \n" + " block the player walks on. \n" + " Every block has sounds to \n"
                        + " better match what they're \n" + " made of.";
                selectionDo(footsteps, useFootsteps, s);
            }
        };
        ActionListener filtersEvent = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JCheckBox filters = (JCheckBox) e.getSource();
                s = " Adds reverb to sounds in \n" + " caves and mutes sounds while " + "\n underwater. Mutes sounds \n"
                        + " from behind a wall too.";
                selectionDo(filters, useFilters, s);
            }
        };
        ActionListener surroundingsEvent = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JCheckBox surroundings = (JCheckBox) e.getSource();
                s = " Realistic storms, Auroras, \n" + " Specific biome sounds, and more!";
                selectionDo(surroundings, useSurroundings, s);
            }
        };

        ActionListener autofishEvent = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JCheckBox autofish = (JCheckBox) e.getSource();
                s = " The name describes all. \n" + " It pulls in fishing rods \n" + " as soon as a bite happens";
                selectionDo(autofish, useAutofish, s);
            }
        };
        ActionListener noteblockEvent = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JCheckBox noteblock = (JCheckBox) e.getSource();
                s = " Shows a graphical config \n" + " menu for noteblocks.";
                selectionDo(noteblock, useNoteblock, s);
            }
        };
        ActionListener brightEvent = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JCheckBox gammabright = (JCheckBox) e.getSource();
                s = " The name describes all. \n" + " It pulls in fishing rods \n" + " as soon as a bite happens";
                selectionDo(gammabright, useBright, s);
            }
        };

        ActionListener cancelEvent = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Install.end();
            }
        };

        ActionListener installEvent = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ambiance = modpackOptions + "ambiance" + q;
                String utility = modpackOptions + "utility" + q;
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
            }
        };

        try {
            pretty = Font.createFont(Font.TRUETYPE_FONT, new File("resources" + q + "Font.ttf")).deriveFont(16f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("resources" + q + "Font.ttf")));
        } catch (IOException e) {
            // If the font could not be found. Not really bothersome.
        } catch (FontFormatException e) {
            GUI.errors.setText("Uxie");
            Errors.init();
        }

        nope.setFont(pretty);
        install.setFont(pretty);
        controllerCheck.setFont(pretty);
        musicCheck.setFont(pretty);
        surroundingsCheck.setFont(pretty);
        filterCheck.setFont(pretty);
        footstepCheck.setFont(pretty);
        autofishCheck.setFont(pretty);
        brightCheck.setFont(pretty);
        noteblockCheck.setFont(pretty);
        modInfo.setFont(pretty);

        nope.addActionListener(cancelEvent);
        install.addActionListener(installEvent);
        controllerCheck.addActionListener(controllerEvent);
        musicCheck.addActionListener(musicEvent);
        surroundingsCheck.addActionListener(surroundingsEvent);
        filterCheck.addActionListener(filtersEvent);
        footstepCheck.addActionListener(footstepsEvent);
        ;
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

    public static void animatedInstall() {
        String animatedOption = modpackOptions + "animations" + q;
        File main = new File(animatedOption + "SmartMoving-1.7.10-15.6.jar");
        File apiOne = new File(animatedOption + "PlayerAPI-1.7.10-1.4.jar");
        File apiTwo = new File(animatedOption + "SmartRender-1.7.10-2.1.jar");
        copyFiles(main, modsDir);
        copyFiles(apiOne, modsDir);
        copyFiles(apiTwo, modsDir);
    }
}

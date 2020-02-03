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

	static JCheckBox controllerCheck, musicCheck, surroundingsCheck, footstepCheck, filterCheck, animatedCheck;
	static boolean useController, useMusic, useSurroundings, useFootsteps, useFilters, useAnimated;
	static JTextArea modInfo = new JTextArea();
	static JScrollPane scroll = new JScrollPane(modInfo);
	static Font pretty;
	static String q = File.separator;
	static String installFolder = Driver.getDownloadsLocation() + q + "Modpack" + q + "extras" + q;
	static File modsDir;

	public static void modOptionsGui() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setTitle("Optional mod installation");
		controllerCheck = new JCheckBox("Controller Support.");
		musicCheck = new JCheckBox("Custom Ambiance Music.");
		surroundingsCheck = new JCheckBox("Dynamic Surroundings.");
		footstepCheck = new JCheckBox("Presence Footsteps.");
		filterCheck = new JCheckBox("Sound Filters.");
		animatedCheck = new JCheckBox("Animated Player");
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
		useAnimated = false;

		ActionListener controllerEvent = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCheckBox controller = (JCheckBox) e.getSource();
				if (controller.isSelected()) {
					useController = true;
					modInfo.setText("");
					modInfo.setText(" Adds support for Xbox \n" + " controllers.");
				} else {
					useController = false;
					modInfo.setText("");
				}
			}
		};
		ActionListener musicEvent = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCheckBox music = (JCheckBox) e.getSource();
				if (music.isSelected()) {
					useMusic = true;
					modInfo.setText("");
					modInfo.setText(" Environmentally aware music. \n" + " Required for the non-texture \n"
							+ " resource packs.");
				} else {
					useMusic = false;
					modInfo.setText("");
				}
			}
		};

		ActionListener footstepsEvent = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCheckBox footsteps = (JCheckBox) e.getSource();
				if (footsteps.isSelected()) {
					useFootsteps = true;
					modInfo.setText("");
					modInfo.setText(" Dynamic sounds for every \n" + " block the player walks on. \n"
							+ " Every block has sounds to \n" + " better match what they're \n" + " made of.");
				} else {
					useFootsteps = false;
					modInfo.setText("");
				}
			}
		};
		ActionListener filtersEvent = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCheckBox filters = (JCheckBox) e.getSource();
				if (filters.isSelected()) {
					useFilters = true;
					modInfo.setText("");
					modInfo.setText(" Adds reverb to sounds in \n" + " caves and mutes sounds while "
							+ "\n underwater. Mutes sounds \n" + " from behind a wall too.");
				} else {
					useFilters = false;
					modInfo.setText("");
				}
			}
		};

		ActionListener surroundingsEvent = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCheckBox surroundings = (JCheckBox) e.getSource();
				if (surroundings.isSelected()) {
					useSurroundings = true;
					modInfo.setText("");
					modInfo.setText(" Realistic storms, Auroras, \n" + " Specific biome sounds, and more!");
				} else {
					useFootsteps = false;
					modInfo.setText("");
				}
			}
		};
		ActionListener animatedEvent = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCheckBox animated = (JCheckBox) e.getSource();
				if (animated.isSelected()) {
					useAnimated = true;
					modInfo.setText("");
					modInfo.setText(
							" Detailed player animations.\n" + " Crawiling, ladders,  \n" + " flying and more.");
				} else {
					useAnimated = false;
					modInfo.setText("");
				}
			}
		};
		ActionListener cancelEvent = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Install.end();
			}
		};

		ActionListener installEvent = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (useController) {
					System.out.println(" Controller support installed");
					controllerInstall();
				}
				if (useMusic) {
					System.out.println(" Music installed");
					musicInstall();
				}
				if (useSurroundings) {
					System.out.println(" Surroundings installed");
					surroundingsInstall();
				}
				if (useFootsteps) {
					System.out.println(" Footsteps installed");
					footstepsInstall();
				}
				if (useFilters) {
					System.out.println(" Filters installed");
					filtersInstall();
				}
				if (useAnimated) {
					System.out.println(" Animations installed");
					animatedInstall();
				}
				Install.end();
			}
		};

		try {
			pretty = Font.createFont(Font.TRUETYPE_FONT, new File("resources" + q + "Font.ttf")).deriveFont(16f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("resources" + q + "Font.ttf")));
		} catch (IOException e) {

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
		animatedCheck.setFont(pretty);
		modInfo.setFont(pretty);

		nope.addActionListener(cancelEvent);
		install.addActionListener(installEvent);
		controllerCheck.addActionListener(controllerEvent);
		musicCheck.addActionListener(musicEvent);
		surroundingsCheck.addActionListener(surroundingsEvent);
		filterCheck.addActionListener(filtersEvent);
		footstepCheck.addActionListener(footstepsEvent);
		animatedCheck.addActionListener(animatedEvent);

		controllerCheck.setBounds(20, 25, 200, 20);
		musicCheck.setBounds(20, 50, 200, 20);
		surroundingsCheck.setBounds(20, 75, 200, 20);
		footstepCheck.setBounds(20, 100, 200, 20);
		filterCheck.setBounds(20, 125, 200, 20);
		animatedCheck.setBounds(20, 150, 200, 20);

		nope.setBounds(250, 240, 150, 20);
		install.setBounds(50, 240, 150, 20);
		scroll.setBounds(225, 30, 225, 180);

		frame.add(nope);
		frame.add(install);
		frame.add(controllerCheck);
		frame.add(musicCheck);
		frame.add(surroundingsCheck);
		frame.add(footstepCheck);
		frame.add(filterCheck);
		frame.add(animatedCheck);

		frame.setSize(480, 320);
		frame.setResizable(false);

		frame.setLayout(null);
		frame.setVisible(true);
	}

	public static void controllerInstall() {
		String joypadOption = modpackOptions + "controller" + q;
		File joypadMod = new File(joypadOption + "JoypadMod-1.7.10.jar");
		File modsDir = new File(Driver.getMinecraftInstallLocation() + q + "mods");
		copyFiles(joypadMod, modsDir);
	}

	public static void musicInstall() {
		String musicOption = modpackOptions + "ambiance" + q;
		File musicMod = new File(musicOption + "MusicChoices-1.3_for_1.7.10.jar");
		File modsDir = new File(Driver.getMinecraftInstallLocation() + q + "mods");
		copyFiles(musicMod, modsDir);
	}

	public static void surroundingsInstall() {
		String surroundingsOption = modpackOptions + "ambiance" + q;
		File surroundingsMod = new File(surroundingsOption + "DynamicSurroundings-1.7.10-1.0.6.4.jar");
		File modsDir = new File(Driver.getMinecraftInstallLocation() + q + "mods");
		copyFiles(surroundingsMod, modsDir);
	}

	// Change this to presence footsteps once you know the file name.
	public static void footstepsInstall() {
		String footstepsOption = modpackOptions + "ambiance" + q;
		File footstepsMod = new File(footstepsOption + "DynamicSurroundings-1.7.10-1.0.6.4.jar");
		File modsDir = new File(Driver.getMinecraftInstallLocation() + q + "mods");
		copyFiles(footstepsMod, modsDir);
	}

	public static void filtersInstall() {
		String filtersOption = modpackOptions + "ambiance" + q;
		File filtersMod = new File(filtersOption + "SoundFilters-0.8_for_1.7.X.jar");
		File modsDir = new File(Driver.getMinecraftInstallLocation() + q + "mods");
		copyFiles(filtersMod, modsDir);
	}

	public static void animatedInstall() { // Change the file names before testing...
		String animatedOption = modpackOptions + "animations" + q;

		File main = new File(animatedOption + "SoundFilters-0.8_for_1.7.X.jar");
		File apiOne = new File(animatedOption + "SoundFilters-0.8_for_1.7.X.jar");
		File apiTwo = new File(animatedOption + "SoundFilters-0.8_for_1.7.X.jar");
		File modsDir = new File(Driver.getMinecraftInstallLocation() + q + "mods");

		copyFiles(main, modsDir);
		copyFiles(apiOne, modsDir);
		copyFiles(apiTwo, modsDir);
	}
}

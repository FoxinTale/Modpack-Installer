import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

public class resourcePacks {

	static Font pretty;
	static String q = File.separator;
	static int selectedOption = 0;
	static JTextArea packInfo = new JTextArea();
	static JScrollPane scroll = new JScrollPane(packInfo);
	static String packLink;
	static JFrame creditsFrame = new JFrame("Asset Credits");
	static int selectedPack;

	public static void packGUI() {
		JFrame frame = new JFrame("Resource Pack Options");

		JRadioButton textures = new JRadioButton("One."); // Textures only
		JRadioButton musicTex = new JRadioButton("Two."); // Game music and sounds, no textures.
		JRadioButton gameMusic = new JRadioButton("Three."); // Game music, Sounds and textures
		JRadioButton ambiance = new JRadioButton("Four."); // Ambiance music, records , no textures.
		JRadioButton everything = new JRadioButton("Five."); // Ambiance, records, sounds and textures
		JRadioButton extra = new JRadioButton("Six."); // Ambiance, records, sounds and textures

		JLabel downloadSizeLabel = new JLabel("Download Size: ");
		JLabel downloadSize = new JLabel("None selected ");
		JButton go = new JButton("Continue");
		ButtonGroup options = new ButtonGroup();

		packInfo.setLineWrap(true);
		frame.getContentPane().add(scroll);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBorder(new LineBorder(Color.black, 1, true));

		packInfo.setEditable(false);

		options.add(gameMusic);
		options.add(musicTex);
		options.add(textures);
		options.add(ambiance);
		options.add(everything);
		options.add(extra);

		Color rbc = new Color(220, 255, 255); // Hex value: dcffff

		JPanel gameMusicPanel = new RoundedPanel(10, rbc);
		JPanel musicTexPanel = new RoundedPanel(10, rbc);
		JPanel texturesPanel = new RoundedPanel(10, rbc);
		JPanel ambiancePanel = new RoundedPanel(10, rbc);
		JPanel allPanel = new RoundedPanel(10, rbc);
		JPanel extraPanel = new RoundedPanel(10, rbc);

		Container c = frame.getContentPane();

		c.setBackground(new Color(255, 220, 220));// Hex value: ffdcdc

		gameMusic.setBackground(rbc);
		musicTex.setBackground(rbc);
		textures.setBackground(rbc);
		ambiance.setBackground(rbc);
		everything.setBackground(rbc);
		extra.setBackground(rbc);

		ActionListener radioButtonEvent = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				AbstractButton absButton = (AbstractButton) ae.getSource();
				String selection = absButton.getText();
				switch (selection) {
				case "One.":
					packInfo.setText(null);
					downloadSize.setText(null);
					selectedOption = 1;
					packInfo.setText(" This pack is textures and \n" + " sounds. \n" + " As a note, this is a high \n"
							+ " resolution texture pack.  \n" + " At least 128x128 quality. \n"
							+ " This does not include any \n" + " music replacements, \n"
							+ " sounds or ambiance music. ");
					downloadSize.setText("90 Mb");
					break;
				case "Two.":

					packInfo.setText(null);
					downloadSize.setText(null);
					selectedOption = 2;
					packInfo.setText(" This pack is game music \n" + " and sounds. No textures. \n"
							+ " No ambiance music. Only \n" + " replacing the games' music\n"
							+ "  and realistic sounds.");
					downloadSize.setText("210 Mb");
					break;
				case "Three":
					packInfo.setText(null);
					downloadSize.setText(null);
					selectedOption = 3;
					packInfo.setText(
							" This pack is game music \n" + " sounds and textures. \n" + " No ambiance music. Only \n"
									+ " replacing the games' music, \n" + " realistic sounds \n" + " and textures.");
					downloadSize.setText("255 Mb");
					break;

				case "Four.":
					packInfo.setText(null);
					downloadSize.setText(null);
					selectedOption = 4;
					packInfo.setText(
							" This pack is ambiance \n" + " music, music disks and \n" + " sounds. No textures.");
					downloadSize.setText("400 Mb");
					break;
				case "Five.":
					packInfo.setText(null);
					downloadSize.setText(null);
					selectedOption = 5;
					packInfo.setText(" This one has it all. \n" + " High resolution textures, \n"
							+ " Ambiance Music, realistic \n" + " sounds, and custom music \n"
							+ " disks. Note: This will \n" + " also extract the pack, due \n"
							+ " its size, to lessen load \n" + " times.");
					downloadSize.setText("550 Mb");
					break;
				case "Six.":

					packInfo.setText(null);
					downloadSize.setText(null);
					selectedOption = 6;
					packInfo.setText(" This one is like the \n" + " previous one, except with \n"
							+ " a crazy amount of ambiance \n" + " added. This one is so large, \n"
							+ " I am not entrusting the \n" + " installer to download it.\n\n"
							+ " If you choose this, you \n" + " will be directed to a link \n"
							+ " to download from within \n" + " your browser. ");

					downloadSize.setText("Huge.");
					break;
				default:
					break;
				}
			}
		};

		ActionListener buttonEvent = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					switch (selectedOption) {
					case 1:
						radioSet(ambiance, gameMusic, musicTex, textures, everything, extra);
						setSelectedPack(1);
						packLink = "https://aubreys-storage.s3.us-east-2.amazonaws.com/Resource-Packs/ACRP-TO.zip";
						Downloader.Download(new URL(packLink), "ACRP-TO.zip", 2);
						assetCredits.credits();
						break;
					case 2:
						radioSet(ambiance, gameMusic, musicTex, textures, everything, extra);
						setSelectedPack(2);
						packLink = "https://aubreys-storage.s3.us-east-2.amazonaws.com/Resource-Packs/ACRP-MS.zip";
						Downloader.Download(new URL(packLink), "ACRP-MS.zip", 2);
						assetCredits.credits();
						break;
					case 3:
						radioSet(ambiance, gameMusic, musicTex, textures, everything, extra);
						setSelectedPack(3);
						packLink = "https://aubreys-storage.s3.us-east-2.amazonaws.com/Resource-Packs/ACRP-MST.zip";
						Downloader.Download(new URL(packLink), "ACRP-MST.zip", 2);
						assetCredits.credits();
						break;
					case 4:
						radioSet(ambiance, gameMusic, musicTex, textures, everything, extra);
						setSelectedPack(4);
						packLink = "https://aubreys-storage.s3.us-east-2.amazonaws.com/Resource-Packs/ACRP-AS.zip";
						Downloader.Download(new URL(packLink), "ACRP-AS.zip", 2);
						assetCredits.credits();
						break;
					case 5:
						radioSet(ambiance, gameMusic, musicTex, textures, everything, extra);
						setSelectedPack(5);
						packLink = "https://aubreys-storage.s3.us-east-2.amazonaws.com/Resource-Packs/ACRP-E.zip";
						Downloader.Download(new URL(packLink), "ACRP-E.zip", 2);
						assetCredits.credits();
						break;
					case 6:
						radioSet(ambiance, gameMusic, musicTex, textures, everything, extra);
						setSelectedPack(6);
						bigPack();
						break;
					default:
						break;
					}
				} catch (MalformedURLException u) {
					// Handle this somehow.
				}
			}
		};

		try {
			pretty = Font.createFont(Font.TRUETYPE_FONT, new File("resources" + q + "Font.ttf")).deriveFont(16f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("resources" + q + "Font.ttf")));
		} catch (IOException e) {

		} catch (FontFormatException e) {
			GUI.errors.setText("Screwy font");
		}

		gameMusic.setFont(pretty);
		musicTex.setFont(pretty);
		textures.setFont(pretty);
		ambiance.setFont(pretty);
		go.setFont(pretty);
		extra.setFont(pretty);
		packInfo.setFont(pretty);

		gameMusic.addActionListener(radioButtonEvent);
		musicTex.addActionListener(radioButtonEvent);
		textures.addActionListener(radioButtonEvent);
		ambiance.addActionListener(radioButtonEvent);
		everything.addActionListener(radioButtonEvent);
		extra.addActionListener(radioButtonEvent);

		go.addActionListener(buttonEvent);

		texturesPanel.setBounds(40, 20, 175, 25);
		textures.setBounds(45, 25, 150, 15);

		musicTexPanel.setBounds(40, 55, 175, 25);
		musicTex.setBounds(45, 60, 150, 15);

		gameMusicPanel.setBounds(40, 90, 175, 25);
		gameMusic.setBounds(45, 95, 150, 15);

		ambiancePanel.setBounds(40, 125, 175, 25);
		ambiance.setBounds(45, 130, 150, 15);

		allPanel.setBounds(40, 160, 175, 25);
		everything.setBounds(45, 165, 150, 15);

		extraPanel.setBounds(40, 195, 175, 25);
		extra.setBounds(45, 200, 150, 15);

		downloadSizeLabel.setBounds(240, 250, 100, 20);
		downloadSize.setBounds(340, 250, 100, 20);

		scroll.setBounds(240, 30, 205, 170);

		go.setBounds(75, 250, 100, 20);

		gameMusic.setFont(pretty);
		musicTex.setFont(pretty);
		textures.setFont(pretty);
		go.setFont(pretty);
		ambiance.setFont(pretty);
		everything.setFont(pretty);
		downloadSizeLabel.setFont(pretty);
		downloadSize.setFont(pretty);

		frame.add(gameMusic);
		frame.add(gameMusicPanel);

		frame.add(musicTex);
		frame.add(musicTexPanel);

		frame.add(textures);
		frame.add(texturesPanel);

		frame.add(ambiance);
		frame.add(ambiancePanel);

		frame.add(everything);
		frame.add(allPanel);

		frame.add(downloadSizeLabel);
		frame.add(downloadSize);

		frame.add(extra);
		frame.add(extraPanel);

		frame.add(go);
		frame.add(scroll);
		frame.setSize(480, 320);
		frame.setResizable(false);

		frame.setLayout(null);// using no layout managers
		frame.setVisible(true);
	}

	public static void radioSet(JRadioButton a, JRadioButton b, JRadioButton c, JRadioButton d, JRadioButton e,
			JRadioButton f) {
		a.setEnabled(false);
		b.setEnabled(false);
		c.setEnabled(false);
		d.setEnabled(false);
		e.setEnabled(false);
		f.setEnabled(false);
	}

	public static void bigPack() {
		JTextArea info = new JTextArea();
		JScrollPane scroll2 = new JScrollPane(info);

		JFrame frame = new JFrame("Large pack option");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		info.setLineWrap(true);
		frame.getContentPane().add(scroll2);
		scroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll2.setBorder(new LineBorder(Color.black, 1, true));

		String text = " Seriously, this is a massive pack \n" + " and is not required. For further \n"
				+ " information, go to the below link. \n" + " It will direct you to my site \n"
				+ " which contains more information.\n" + " The link may take time to load.\n" + "\n"
				+ " Closing this will exit the program.";

		Color rbc = new Color(220, 255, 255); // Hex value: dcffff

		JPanel textPanel = new RoundedPanel(10, rbc);
		Container c = frame.getContentPane();
		c.setBackground(new Color(255, 220, 220));// Hex value: ffdcdc

		JHyperlink link = new JHyperlink("Go here for more information",
				"https://foxintale.github.io/aubsburrow/subpages/gameservers/resourcepack.html");

		try {
			pretty = Font.createFont(Font.TRUETYPE_FONT, new File("resources" + q + "Font.ttf")).deriveFont(16f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("resources" + q + "Font.ttf")));
		} catch (IOException e) {

		} catch (FontFormatException e) {
			GUI.errors.setText("Screwy font");
		}

		info.setText(text);
		info.setFont(pretty);
		info.setEditable(false);
		textPanel.setBounds(15, 10, 280, 250);
		scroll2.setBounds(25, 20, 260, 175);
		link.setBounds(60, 225, 200, 20);

		link.setFont(pretty);
		frame.add(scroll2);
		frame.add(link);
		frame.add(textPanel);

		frame.setSize(320, 320);
		frame.setResizable(false);
		frame.setLayout(null);// using no layout managers
		frame.setVisible(true);
	}

	public static int getSelectedPack() {
		return selectedPack;
	}

	public static void setSelectedPack(int selectedPack) {
		resourcePacks.selectedPack = selectedPack;
	}
}

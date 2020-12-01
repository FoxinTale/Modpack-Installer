import GUI.JHyperlink;
import GUI.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class assetCredits extends resourcePacks {
	public static void credits() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JLabel info = new JLabel("Go show these creators some support on their pages!");
		info.setBounds(35, 0, 350, 20);

		JLabel packOneA = new JLabel(" S & K Photo Realism. 128 x 128.");
		JLabel packOneB = new JLabel(" Created by:  [User or Users]. ");
		JHyperlink packOneC = new JHyperlink("Check it out here.",
				"https://www.planetminecraft.com/texture_pack/sampk-photo-realism-x512-hd/");

		JLabel packTwoA = new JLabel(" Milkyway Galaxy Day & Night");
		JLabel packTwoB = new JLabel(" Created by:Stingray Productions");
		JHyperlink packTwoC = new JHyperlink("Check it out here.",
				"https://stingrayproductionsminecraft.com/milkyway-galaxy-custom-sky");

		JLabel packThreeA = new JLabel(" Full of Life");
		JLabel packThreeB = new JLabel(" Created by:  Peasantry");
		JHyperlink packThreeC = new JHyperlink("Check it out here.",
				"https://www.planetminecraft.com/texture_pack/photo-realistic-alpha/");

		JLabel packFourA = new JLabel("SnowSong - Epic sound and music pack");
		JLabel packFourB = new JLabel("Created by: Sn0w");
		JHyperlink packFourC = new JHyperlink("Check it out here.",
				"https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/resource-packs/1245112-snowsong-the-epic-sound-pack-sound-resource-pack");

		JLabel packFiveA = new JLabel(" PonyCraft 128 x 128.");
		JLabel packFiveB = new JLabel(" Created by:  flutterstorm");
		JHyperlink packFiveC = new JHyperlink("Check it out here.",
				"https://www.planetminecraft.com/texture_pack/flutterstorms-ponycraft/");

		JLabel packSixA = new JLabel(" Pencil Pack");
		JLabel packSixB = new JLabel(" Created by:  jonoww");
		JHyperlink packSixC = new JHyperlink("Check it out here.", "https://www.planetminecraft.com/texture_pack/pencil-pack-hand-drawn/");

		JLabel packSevenA = new JLabel(" Rainbow Animated");
		JLabel packSevenB = new JLabel(" Created by:  Blazik3n");
		JHyperlink packSevenC = new JHyperlink("Check it out here.", "https://www.planetminecraft.com/texture_pack/animated-rainbow-1-14-pvp-pack-64x/");

		JLabel packEightA = new JLabel(" Nellik's GUIs");
		JLabel packEightB = new JLabel(" Created by:  ScottKillen");
		JHyperlink packEightC = new JHyperlink("Check it out here.", "https://www.curseforge.com/minecraft/texture-packs/nelliks-highdef-guis");

		Color rbc = new Color(220, 255, 255); // Hex value: dcffff
		JPanel packOnePanel = new RoundedPanel(10, rbc);
		JPanel packTwoPanel = new RoundedPanel(10, rbc);
		JPanel packThreePanel = new RoundedPanel(10, rbc);
		JPanel packFourPanel = new RoundedPanel(10, rbc);
		JPanel packFivePanel = new RoundedPanel(10, rbc);
		JPanel packSixPanel = new RoundedPanel(10, rbc);
		JPanel packSevenPanel = new RoundedPanel(10, rbc);
		JPanel packEightPanel = new RoundedPanel(10, rbc);

		try {
			pretty = Font.createFont(Font.TRUETYPE_FONT, new File("resources" + q + "Font.ttf")).deriveFont(16f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("resources" + q + "Font.ttf")));
		} catch (IOException e) {

		} catch (FontFormatException e) {
			GUI.errors.setText("Screwy font");
		}

		packOneA.setBounds(30, 30, 300, 20);
		packOneB.setBounds(30, 50, 300, 20);
		packOneC.setBounds(235, 50, 150, 20);
		packOnePanel.setBounds(20, 25, 345, 50);

		packTwoA.setBounds(30, 90, 300, 20);
		packTwoB.setBounds(30, 110, 300, 20);
		packTwoC.setBounds(235, 110, 150, 20);
		packTwoPanel.setBounds(20, 85, 345, 50);

		packThreeA.setBounds(30, 150, 300, 20);
		packThreeB.setBounds(30, 170, 300, 20);
		packThreeC.setBounds(235, 170, 150, 20);
		packThreePanel.setBounds(20, 145, 345, 50);

		packFourA.setBounds(30, 210, 300, 20);
		packFourB.setBounds(30, 230, 300, 20);
		packFourC.setBounds(235, 230, 300, 20);
		packFourPanel.setBounds(20, 205, 345, 50);

		packFiveA.setBounds(390, 30, 300, 20);
		packFiveB.setBounds(390, 50, 300, 20);
		packFiveC.setBounds(600, 50, 300, 20);
		packFivePanel.setBounds(385, 25, 345, 50);

		packSixA.setBounds(390, 90, 300, 20);
		packSixB.setBounds(390, 110, 300, 20);
		packSixC.setBounds(600, 110, 300, 20);
		packSixPanel.setBounds(385, 85, 345, 50);

		packSevenA.setBounds(390, 150, 300, 20);
		packSevenB.setBounds(390, 170, 300, 20);
		packSevenC.setBounds(600, 170, 300, 20);
		packSevenPanel.setBounds(385, 145, 345, 50);

		packEightA.setBounds(390, 210, 300, 20);
		packEightB.setBounds(390, 230, 300, 20);
		packEightC.setBounds(600, 230, 300, 20);
		packEightPanel.setBounds(385, 205, 345, 50);

		packOneA.setFont(pretty);
		packOneB.setFont(pretty);
		packOneC.setFont(pretty);

		packTwoA.setFont(pretty);
		packTwoB.setFont(pretty);
		packTwoC.setFont(pretty);

		packThreeA.setFont(pretty);
		packThreeB.setFont(pretty);
		packThreeC.setFont(pretty);

		packFourA.setFont(pretty);
		packFourB.setFont(pretty);
		packFourC.setFont(pretty);

		packFiveA.setFont(pretty);
		packFiveB.setFont(pretty);
		packFiveC.setFont(pretty);

		packSixA.setFont(pretty);
		packSixB.setFont(pretty);
		packSixC.setFont(pretty);

		packSevenA.setFont(pretty);
		packSevenB.setFont(pretty);
		packSevenC.setFont(pretty);

		info.setFont(pretty);

		packEightA.setFont(pretty);
		packEightB.setFont(pretty);
		packEightC.setFont(pretty);
		
		Container c = creditsFrame.getContentPane();

		c.setBackground(new Color(255, 220, 220));// Hex value: ffdcdc

		creditsFrame.add(packOneA);
		creditsFrame.add(packOneB);
		creditsFrame.add(packOneC);

		creditsFrame.add(packTwoA);
		creditsFrame.add(packTwoB);
		creditsFrame.add(packTwoC);

		creditsFrame.add(packThreeA);
		creditsFrame.add(packThreeB);
		creditsFrame.add(packThreeC);

		creditsFrame.add(packFourA);
		creditsFrame.add(packFourB);
		creditsFrame.add(packFourC);

		creditsFrame.add(packFiveA);
		creditsFrame.add(packFiveB);
		creditsFrame.add(packFiveC);

		creditsFrame.add(packSixA);
		creditsFrame.add(packSixB);
		creditsFrame.add(packSixC);

		creditsFrame.add(packSevenA);
		creditsFrame.add(packSevenB);
		creditsFrame.add(packSevenC);

		creditsFrame.add(packEightA);
		creditsFrame.add(packEightB);
		creditsFrame.add(packEightC);

		creditsFrame.add(packOnePanel);
		creditsFrame.add(packTwoPanel);
		creditsFrame.add(packThreePanel);
		creditsFrame.add(packFourPanel);
		creditsFrame.add(packFivePanel);
		creditsFrame.add(packSixPanel);
		creditsFrame.add(packSevenPanel);
		creditsFrame.add(packEightPanel);
		creditsFrame.add(info);

		creditsFrame.setResizable(false);
		creditsFrame.setSize(768, 320);
		creditsFrame.setLayout(null);
		creditsFrame.setVisible(true);
	}
}

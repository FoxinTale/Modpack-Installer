import GUI.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class optionsGUI extends installOptions {
    public static void otherOptionsGUI() {
        JFrame frame = new JFrame(Strings.optionsTitle);
        JFrame.setDefaultLookAndFeelDecorated(true);
        JRadioButton extract = new JRadioButton(Strings.optionsOne);
        JRadioButton launcher = new JRadioButton(Strings.optionsTwo);
        JRadioButton ping = new JRadioButton(Strings.optionsThree);
        JRadioButton restoreSettings = new JRadioButton(Strings.optionsFour);
        JRadioButton optionalMods = new JRadioButton(Strings.optionsFive);
        JButton go = new JButton("Continue");
        ButtonGroup options = new ButtonGroup();

        options.add(ping);
        options.add(launcher);
        options.add(extract);
        options.add(optionalMods);

        Color rbc = new Color(220, 255, 255); // Hex value: dcffff

        JPanel pingPanel = new RoundedPanel(10, rbc);
        JPanel launcherPanel = new RoundedPanel(10, rbc);
        JPanel extractPanel = new RoundedPanel(10, rbc);
        JPanel restorePanel = new RoundedPanel(10, rbc);
        JPanel modPanel = new RoundedPanel(10, rbc);

        Container c = frame.getContentPane();

        c.setBackground(new Color(255, 220, 220));// Hex value: ffdcdc

        ping.setBackground(rbc);
        launcher.setBackground(rbc);
        extract.setBackground(rbc);
        restoreSettings.setBackground(rbc);
        optionalMods.setBackground(rbc);

        ActionListener radioButtonEvent = ae -> {
            AbstractButton absButton = (AbstractButton) ae.getSource();
            String selection = absButton.getText();
            switch (selection) {
                case "Extract.":
                    selectedOption = 1;
                    break;
                case "Set Memory.":
                    selectedOption = 2;
                    break;
                case "Ping Server.":
                    selectedOption = 3;
                    break;
                case "Restore Settings.":
                    selectedOption = 4;
                    break;
                case "Optional Mods.":
                    selectedOption = 5;
                    break;
                default:
                    break;
            }
        };

        ActionListener buttonEvent = e -> {
            switch (selectedOption) {
                case 1:
                    radioSet(extract, launcher, ping, restoreSettings, optionalMods);
                    System.out.println(" Extracting File...");
                    Extractor.Extract(Common.q + Common.getDownloadsLocation() + Common.q + Common.zipFile, "Modpack", 0);
                    break;
                case 2:
                    radioSet(extract, launcher, ping, restoreSettings, optionalMods);
                    Memory.sliderGUI();
                    break;
                case 3:
                    radioSet(extract, launcher, ping, restoreSettings, optionalMods);
                    System.out.println(" Pinging Server...");
                    serverPing();
                    break;
                case 4:
                    radioSet(extract, launcher, ping, restoreSettings, optionalMods);
                    restore();
                    break;

                case 5:
                    radioSet(extract, launcher, ping, restoreSettings, optionalMods);
                    modOptions.modOptionsGui();
                    break;
                default:
                    break;
            }
        };

        ping.addActionListener(radioButtonEvent);
        launcher.addActionListener(radioButtonEvent);
        extract.addActionListener(radioButtonEvent);
        restoreSettings.addActionListener(radioButtonEvent);
        optionalMods.addActionListener(radioButtonEvent);

        ping.setFont(Common.pretty);
        launcher.setFont(Common.pretty);
        extract.setFont(Common.pretty);
        restoreSettings.setFont(Common.pretty);
        optionalMods.setFont(Common.pretty);

        go.addActionListener(buttonEvent);

        extractPanel.setBounds(63, 30, 200, 25);
        extract.setBounds(78, 35, 175, 15);

        pingPanel.setBounds(63, 65, 200, 25);
        ping.setBounds(68, 70, 175, 15);

        launcherPanel.setBounds(63, 100, 200, 25);
        launcher.setBounds(68, 105, 175, 15); // Launcher (Memory Set)

        restorePanel.setBounds(63, 135, 200, 25);
        restoreSettings.setBounds(68, 140, 175, 15); // Restore settings, Width 150.

        modPanel.setBounds(63, 170, 200, 25);
        optionalMods.setBounds(68, 175, 175, 15);

        go.setBounds(90, 300, 100, 30);

        frame.add(ping);
        frame.add(pingPanel);
        frame.add(launcher);
        frame.add(launcherPanel);
        frame.add(restoreSettings);
        frame.add(restorePanel);
        frame.add(optionalMods);
        frame.add(modPanel);
        frame.add(go);

        frame.setSize(320, 400);
        frame.setResizable(false);

        frame.setLayout(null);
        frame.setVisible(true);
    }

    public static void radioSet(JRadioButton a, JRadioButton b, JRadioButton c, JRadioButton d, JRadioButton e) {
        a.setEnabled(false);
        b.setEnabled(false);
        c.setEnabled(false);
        d.setEnabled(false);
        e.setEnabled(false);
    }
}

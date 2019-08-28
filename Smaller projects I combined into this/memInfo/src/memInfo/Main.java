package memInfo;

import java.io.PrintStream;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class Main {
	static JEditorPane pane = new JEditorPane();
	static JTextArea consoleOutput = new JTextArea();
	static JScrollPane scroll = new JScrollPane(consoleOutput);
	@SuppressWarnings("unused")
	private static PrintStream standardOut; // This sets the outputs.

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		PrintStream printStream = new PrintStream(new CustomOutputStream(consoleOutput));
		standardOut = System.out;
		System.setOut(printStream);
		System.setErr(printStream);
	
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("Memory Amount.");// creating instance of JFrame, and setting the title.
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		consoleOutput.setLineWrap(true);

		frame.getContentPane().add(scroll);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		scroll.setBounds(25, 25, 400, 200);
		consoleOutput.setEditable(false);
		frame.add(scroll);
		frame.setSize(480, 480);
		frame.setResizable(false);

		frame.setLayout(null);// using no layout managers
		frame.setVisible(true);
		getMemory();
	}
	
	public static void getMemory() {
		try {
			Sigar oof = new Sigar();
			Mem memory = new Mem();

			memory.gather(oof);
			long memSize = memory.getRam();
			int ram = Math.toIntExact(memSize);

			System.out.println(ram);
		} catch (SigarException se) {
			se.printStackTrace();
		}
	}

}

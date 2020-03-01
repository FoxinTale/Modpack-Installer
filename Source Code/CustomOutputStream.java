import java.io.OutputStream;

import javax.swing.JTextArea;

/*
What this does, is it redirects and and all System.out and System.err calls
and outputs them to the JTextArea in the GUI class. I found this was much easier to 
do than any other option.
*/
public class CustomOutputStream extends OutputStream {
	private JTextArea consoleOutput;

	public CustomOutputStream(JTextArea consoleOutput) {
		this.consoleOutput = consoleOutput;
	}
	@Override
	public void write(int b) {
		consoleOutput.append(String.valueOf((char) b));
		consoleOutput.setCaretPosition(consoleOutput.getDocument().getLength());
	}
}

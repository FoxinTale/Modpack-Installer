package memInfo;

import java.io.OutputStream;
import javax.swing.JTextArea;

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

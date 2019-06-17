import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;

public class CustomOutputStream extends OutputStream {
		private JTextArea consoleOutput;
		
		public CustomOutputStream(JTextArea consoleOutput) {
			this.consoleOutput = consoleOutput;
		}
		@Override
		public void write(int b) throws IOException{
			consoleOutput.append(String.valueOf((char)b));
			consoleOutput.setCaretPosition(consoleOutput.getDocument().getLength());
		}
	}
	
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class Errors {

	static Object fix;
	static Object cause;
	static Object severity;
	static String errorCode;
	static String q = File.separator;

	public static void init() {
		System.out.println(" Click the red square to look up the error.");
		errorCode = GUI.errors.getText();
		GUI.errorLookup.setVisible(true);
		Json.errorRead(errorCode);
	}

	public static void makeGUI() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		JLabel errorLabel = new JLabel("\"Error\" Code: ");
		JLabel severityLabel = new JLabel("Severity: ");
		JLabel causeLabel = new JLabel("Cause: ");
		JLabel fixLabel = new JLabel("Fix: ");

		JLabel errorBox = new JLabel(errorCode.toString());
		JLabel severityBox = new JLabel(severity.toString());
		JLabel causeBox = new JLabel(cause.toString());
		JLabel fixBox = new JLabel(fix.toString());

		errorLabel.setBounds(20, 25, 150, 36);
		severityLabel.setBounds(20, 50, 100, 36);
		causeLabel.setBounds(20, 75, 100, 36);
		fixLabel.setBounds(20, 100, 100, 36);

		errorBox.setBounds(120, 25, 350, 36);
		severityBox.setBounds(120, 50, 300, 36);
		causeBox.setBounds(120, 75, 300, 36);
		fixBox.setBounds(120, 100, 500, 36);

		frame.add(errorLabel);
		frame.add(severityLabel);
		frame.add(causeLabel);
		frame.add(fixLabel);

		frame.add(errorBox);
		frame.add(severityBox);
		frame.add(causeBox);
		frame.add(fixBox);

		frame.setSize(640, 240);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.setVisible(true);
	}

	public static Object getFix() {
		return fix;
	}

	public static void setFix(Object fix) {
		Errors.fix = fix;
	}

	public static Object getCause() {
		return cause;
	}

	public static void setCause(Object cause) {
		Errors.cause = cause;
	}

	public static Object getSeverity() {
		return severity;
	}

	public static void setSeverity(Object severity) {
		Errors.severity = severity;
	}
}

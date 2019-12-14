import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Errors {

	static Object fix;
	static Object cause;
	static Object severity;
	static Object errorCode; 
	static String q = File.separator;

	public static void init() {
		System.out.println(" Click the red square to look up the error.");
		errorCode = GUI.errors.getText();
		GUI.errorLookup.setVisible(true);
		errorRead(errorCode);
	}

	public static void makeGUI() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		JLabel errorLabel = new JLabel("\" Error \" Code: ");
		JLabel severityLabel = new JLabel("Severity: ");
		JLabel causeLabel = new JLabel("Cause: ");
		JLabel fixLabel = new JLabel("Fix: ");

		JLabel errorBox = new JLabel(errorCode.toString());
		JLabel severityBox = new JLabel(severity.toString());
		JLabel causeBox = new JLabel(cause.toString());
		JLabel fixBox = new JLabel(fix.toString());

		errorLabel.setBounds(20, 25, 100, 36);
		severityLabel.setBounds(20, 50, 100, 36);
		causeLabel.setBounds(20, 75, 100, 36);
		fixLabel.setBounds(20, 100, 100, 36);

		errorBox.setBounds(120, 25, 300, 36);
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void errorRead(Object errorCode) {
		String pwd = System.getProperty("user.dir");
		File errorsData = new File(pwd + q + "Modpack-Installer_lib" + "errors.json");

		try {
			Object fileData = new JSONParser().parse(new FileReader(errorsData));
			JSONObject data = (JSONObject) fileData;
			Object keyVal;
			Iterator<Map.Entry> itr1 = data.entrySet().iterator();
			while (itr1.hasNext()) {
				Map.Entry pair = itr1.next();
				keyVal = pair.getKey();
				if (keyVal.equals(errorCode)) {
					Map errorData = ((Map) data.get(errorCode));
					Iterator<Map.Entry> errorItr = errorData.entrySet().iterator();
					while (errorItr.hasNext()) {
						Map.Entry errorPair = errorItr.next();

						if (errorPair.getKey().equals("severity")) {
							severity = errorPair.getValue();
						}
						if (errorPair.getKey().equals("cause")) {
							cause = errorPair.getValue();
						}
						if (errorPair.getKey().equals("fix")) {
							fix = errorPair.getValue();
						}
					}
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		makeGUI();
	}
}

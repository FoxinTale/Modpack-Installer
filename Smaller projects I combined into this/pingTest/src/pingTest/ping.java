package pingTest;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ping {

	public static void main(String[] args) {
		try {
			// String address = "24.164.81.166:25525";
			// InetAddress server = InetAddress.getByName(address);
			// if (server.isReachable(60000)) {
				// String notification = "The server is up! Get on it and have fun!";
				// JOptionPane.showMessageDialog(new JFrame(), notification, "Server Up!", JOptionPane.INFORMATION_MESSAGE);
			
			//}
			
			Socket server = new Socket();
			server.connect(new InetSocketAddress("24.164.81.166", 25525), 60000);
			String notification = "The server is up! Get on it and have fun!";
			JOptionPane.showMessageDialog(new JFrame(), notification, "Server Up!", JOptionPane.INFORMATION_MESSAGE);
			
		} catch (UnknownHostException h) {
			System.out.println("Chimchar");
		} catch (IOException i) {
			String notification = "It isnt up, please let me know, and I'll get on it as soon as I can.";
			JOptionPane.showMessageDialog(new JFrame(), notification, "Server Down", JOptionPane.ERROR_MESSAGE);

		}
	}

}

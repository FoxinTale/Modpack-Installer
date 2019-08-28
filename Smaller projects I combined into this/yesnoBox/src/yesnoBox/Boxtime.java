package yesnoBox;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Boxtime {
	public static void main(String[] args) {
		String s = "Would you like the installer to do magic?";
		int n = JOptionPane.showConfirmDialog(new JFrame(), s, "Choices", JOptionPane.YES_NO_OPTION);
		
		if(n == JOptionPane.YES_OPTION) {
			// System.out.println("Yes!");
			
		}
		if(n == JOptionPane.NO_OPTION){
			// System.out.println("No");
			
		}
		
	}

}

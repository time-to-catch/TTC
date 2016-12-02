package myPackage;
import java.awt.event.*;
import javax.swing.*;

public class WindowHandler extends WindowAdapter {
	
	public void windowClosing(WindowEvent e){
		JOptionPane.showMessageDialog(null,
				"Hope you enjoyed the game. goodbye!", "Exit", JOptionPane.INFORMATION_MESSAGE);
		super.windowClosing(e);
	}

}

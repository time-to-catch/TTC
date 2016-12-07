package myPackage;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class WaitingPanel extends JPanel {

	JPanel panel;
	ImageIcon img = new ImageIcon("waiting.png");

	WaitingPanel() {
		panel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(img.getImage(), 0, 0, null);
//				setOpaque(false);
				super.paintComponent(g);
			}
		};
	}
}

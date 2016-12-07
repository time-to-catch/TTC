package myPackage;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class WaitingFrame extends JFrame {

	ImageIcon img = new ImageIcon("waiting.png");
	JScrollPane scrollPane;

	WaitingFrame() {
		 JFrame frame = new JFrame();
		 frame.setUndecorated(true);
		// setLayout(null);
		// setSize(593, 241);
		 frame.setResizable(false);
		// setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// setVisible(true);
		// setLocation(540, 270);
		 setSize(593, 241);
		// setResizable(false);
		// setLocation(540, 270);

		JPanel panel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(new ImageIcon("waiting.png").getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		
		 frame.getContentPane().add(panel, "Center");
	}
}

package myPackage;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class WaitingPanel extends JPanel {

	WaitingPanel() {
		setLayout(null);
		setBounds(20, 20, 20, 20);
		JLabel text = new JLabel("Searching for another users.");
		text.setFont(new Font("±Ã¼­", 15, 15));
		text.setBounds(20, 20, 200, 20);
		add(text);

		JLabel textTwo = new JLabel("Please wait...");
		textTwo.setFont(new Font("±Ã¼­", 15, 15));
		textTwo.setBounds(20, 20, 220, 20);
		add(textTwo);
		setSize(380, 200);
	}
}

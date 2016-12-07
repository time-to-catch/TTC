package myPackage;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class WaitingRoom extends JFrame{
	
	WaitingRoom(){
		
		JFrame frame = new JFrame("Waiting");
		frame.setLayout(null);
		frame.setUndecorated(true);
		
		JLabel text = new JLabel("Searching for another users.");
		text.setFont(new Font("±Ã¼­", 15, 15));
		text.setBounds(90, 40, 500, 15);
		frame.add(text);
		
		JLabel textTwo = new JLabel("Please wait...");
		textTwo.setFont(new Font("±Ã¼­", 15, 15));
		textTwo.setBounds(140, 60, 500, 15);
		frame.add(textTwo);
		frame.setResizable(false);
		frame.setVisible(true);
		
		frame.setLocation(540, 270);
		frame.setSize(380, 150);
		frame.setVisible(true);
	}
	
}

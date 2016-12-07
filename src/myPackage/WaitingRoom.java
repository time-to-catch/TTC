package myPackage;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class WaitingRoom extends JFrame{

	WaitingRoom(){	
		setTitle("Waiting");
		setLayout(null);
		setUndecorated(true);
		
		JLabel text = new JLabel("Searching for another users.");
		text.setFont(new Font("±Ã¼­", 15, 15));
		text.setBounds(90, 40, 500, 15);
		add(text);
		
		JLabel textTwo = new JLabel("Please wait...");
		textTwo.setFont(new Font("±Ã¼­", 15, 15));
		textTwo.setBounds(140, 60, 500, 15);
		add(textTwo);
		
		setSize(380, 200);
		setResizable(false);
		setVisible(true);
	}
}

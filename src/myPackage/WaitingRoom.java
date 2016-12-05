package myPackage;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class WaitingRoom extends JFrame{
	
	WaitingRoom(){
		
		JFrame frame = new JFrame("Waiting");
		frame.setLayout(null);
		
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
		
//		JButton cancel = new JButton("Cancel");
//		cancel.setBounds(110, 100, 150, 25);
//
////		frame.add(cancel);
//		cancel.addActionListener(new ActionListener(){
//			
//			public void actionPerformed(ActionEvent e){
//				
//				frame.setVisible(false);
//				
//			}
//		});	

		
		frame.setLocation(540, 270);
		frame.setSize(380, 200);
		frame.setVisible(true);
	}
	
	public static void closeWaiting(WaitingRoom w)
	{
		w.setVisible(false);
	}
}

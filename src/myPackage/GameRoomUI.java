package myPackage;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GameRoomUI extends JPanel{
	
	public GameRoomUI() {
		
	      setName("Time To Catch");

	      makeUI();
	      //setLocation(500,200);
	      setSize(1052, 764);
	   }

	   private void makeUI() {
	      this.setLayout(null);
	      this.setBounds(0, 0, 1052, 764);
	     
		  JTextArea question = new JTextArea("This is question!");
	      question.setBounds(48, 40, 950, 440);
	      question.setBackground(new Color(51, 0, 0));
	      question.setEditable(false);
	      question.setOpaque(false);
	      question.setFont(new Font("Serif", 20, 20));
	      question.setForeground(Color.WHITE);
	      this.add(question);
	      // Teambtn.setFont(new Font("Trajan Pro",Font.PLAIN,36));
	      
	      JTextArea status = new JTextArea("This is status of user!");
	      status.setBounds(48, 515, 777, 180);
	      status.setEditable(false);
	      status.setOpaque(false);
	      status.setFont(new Font("Serif", 20, 20));
	      status.setForeground(Color.WHITE);
	      this.add(status);
	      
	      JTextField getAnswer = new JTextField("Answer here!");
	      getAnswer.setBounds(48, 700, 777, 30);
	      getAnswer.setOpaque(false);
	      getAnswer.setFont(new Font("Serif", 20, 20));
	      getAnswer.setForeground(Color.WHITE);
	      this.add(getAnswer);
	      
	      JPanel buttonPanel = new JPanel();
	      buttonPanel.setLayout(new GridLayout(2,2));
	      buttonPanel.setLocation(750, 490);
	      buttonPanel.setBounds(833, 515, 170, 180);
	      
	      JButton buttonOne = new JButton(new ImageIcon("one.png"));
	      JButton buttonTwo = new JButton(new ImageIcon("two.png"));
	      JButton buttonThree = new JButton(new ImageIcon("three.png"));
	      JButton buttonFour = new JButton(new ImageIcon("Four.png"));
	      
	      buttonSet(buttonOne);
	      buttonSet(buttonTwo);
	      buttonSet(buttonThree);
	      buttonSet(buttonFour);
	      
	      buttonPanel.add(buttonOne);
	      buttonPanel.add(buttonTwo);
	      buttonPanel.add(buttonThree);
	      buttonPanel.add(buttonFour);
	      this.add(buttonPanel);
	      
	      /*
	      MyActionListener listenerT = new MyActionListener();
	      buttonOne.addActionListener(buttonOne);
	      buttonTwo.addActionListener(buttonTwo);
	      buttonThree.addActionListener(buttonThree);
	      buttonFour.addActionListener(buttonFour);
	      */
	      
	      JButton emergency = new JButton(new ImageIcon("emergency.png"));
	      emergency.setBounds(833, 700, 170, 30);
	      emergency.setBorderPainted(false);
	      emergency.setFocusPainted(false);
	      this.add(emergency);
	      
	      getAnswer.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) {
				
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
					status.setText("You entered, and the answer sends to server.");
					getAnswer.setText("");
				}
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
	    	  	    	  
	      });
	      
	      

	      
	   }
	  
	   public void buttonSet(JButton b){
		   
		   b.setBackground(Color.WHITE);
		   b.setBorderPainted(true);
		   b.setFocusPainted(false);
		   b.setContentAreaFilled(true);
		   
	   }

	   public void paintComponent(Graphics g) {// Graphics객체는 그릴수 있는 도구.
	        
		  super.paintComponent(g);
	      Image backImg = new ImageIcon("frame.png").getImage();
	      g.drawImage(backImg, 0, 0, getWidth(), getHeight(), this);

	   }
	
}

class ButtonActionListener implements ActionListener {

	   @Override
	   public void actionPerformed(ActionEvent e) {
	      // TODO Auto-generated method stub
	      JButton b = (JButton) e.getSource();

	     
	      
	      
	      
	   }

}

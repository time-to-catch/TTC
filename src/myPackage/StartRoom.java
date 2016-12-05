package myPackage;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class StartRoom extends JPanel {

   public StartRoom() {

      makeUI();
      // setLocation(500,200);
      setSize(1052, 764);

   }
   
   private void makeUI() {
      this.setLayout(null);
      this.setBounds(0, 0, 1052, 764);
      

      // ��ư ����
      JButton Teambtn = new JButton(new ImageIcon("teammatch.png"));
      JButton Singlebtn = new JButton(new ImageIcon("singlematch.png"));
      Teambtn.setText("TEAM");
      Singlebtn.setText("SINGLE");

      Teambtn.setBorder(new EmptyBorder(10, 10, 10, 10)); // �׵θ� ����ȭ
      Singlebtn.setBorder(new EmptyBorder(10, 10, 10, 10));
      Teambtn.setBackground(new Color(9, 21, 52));
      Singlebtn.setBackground(new Color(9, 21, 52));
      this.add(Singlebtn);
      this.add(Teambtn);
      Singlebtn.setBounds(690, 350, 310, 40);
      Teambtn.setBounds(718, 410, 280, 40);
      
      Singlebtn.setBorderPainted(false);
      Singlebtn.setFocusPainted(false);
      Singlebtn.setContentAreaFilled(true);
      Singlebtn.setOpaque(false);
      Teambtn.setBorderPainted(false);
      Teambtn.setFocusPainted(false);
      Teambtn.setContentAreaFilled(true);
      Teambtn.setOpaque(false);
      
      MyActionListener listenerT = new MyActionListener();
      Teambtn.addActionListener(listenerT);
      Singlebtn.addActionListener(listenerT);
      
   }

   public void paintComponent(Graphics g) {// Graphics��ü�� �׸��� �ִ� ����.
                           // �̹���ó��. ���
	   
	  super.paintComponent(g);
      Image backImg = new ImageIcon("Login3.png").getImage();
      g.drawImage(backImg, 0, 0, getWidth(), getHeight(), this);
      /*
       * //�̹���ó��. ��������Ʈ Image img =
       * Toolkit.getDefaultToolkit().getImage("background.jpg");
       * g.drawImage(img, 30, 150, this);
       * 
       * //���ڿ� ó�� g.drawString("paint() in SWING", 20, getHeight()-20);
       */

   }
   
}

class MyActionListener implements ActionListener {

	static WaitingRoom wait;
	
   @Override
   public void actionPerformed(ActionEvent e) {
      // TODO Auto-generated method stub
      JButton b = (JButton) e.getSource();

      TTC_Client.sendMode(b.getText());
     
      wait = new WaitingRoom(b.getText());
   }
   
   public static void stop()
   {
	   TTC_Client.stopWait(wait);
   }
}
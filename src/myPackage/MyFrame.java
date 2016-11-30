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

public class MyFrame extends JFrame {

   public MyFrame() {
      setTitle("Time To Catch");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      makeUI();
      // setLocation(500,200);
      setSize(1024, 768);
      setResizable(false);
      setVisible(true);
   }
   
   private void makeUI() {
      this.setLayout(null);
      this.setBounds(100, 0, 1024, 768);

      // 버튼 생성
      JButton Teambtn = new JButton(new ImageIcon("teammatch.png"));
      JButton Singlebtn = new JButton(new ImageIcon("singlematch.png"));
      Teambtn.setText("TEAM");
      Singlebtn.setText("SINGLE");

      // JButton Teambtn = new JButton("Team Match");
      // Teambtn.setFont(new Font("Trajan Pro",Font.PLAIN,36));

      // 버튼 투명화
      Teambtn.setBorder(new EmptyBorder(10, 10, 10, 10)); // 테두리 투명화
      Singlebtn.setBorder(new EmptyBorder(10, 10, 10, 10));
      Teambtn.setBackground(new Color(9, 21, 52));
      Singlebtn.setBackground(new Color(9, 21, 52));
      this.add(Singlebtn);
      this.add(Teambtn);
      Singlebtn.setBounds(660, 300, 310, 40);
      Teambtn.setBounds(690, 370, 280, 40);
      
      Singlebtn.setBorderPainted(false);
      Singlebtn.setFocusPainted(false);
      Singlebtn.setContentAreaFilled(true);
      Teambtn.setBorderPainted(false);
      Teambtn.setFocusPainted(false);
      Teambtn.setContentAreaFilled(true);
      
      MyActionListener listenerT = new MyActionListener();
      Teambtn.addActionListener(listenerT);
      Singlebtn.addActionListener(listenerT);
      
   }

   public void paint(Graphics g) {// Graphics객체는 그릴수 있는 도구.
                           // 이미지처리. 배경
      Image backImg = new ImageIcon("Login3.png").getImage();
      g.drawImage(backImg, 0, 0, getWidth(), getHeight(), this);
      /*
       * //이미지처리. 스프라이트 Image img =
       * Toolkit.getDefaultToolkit().getImage("background.jpg");
       * g.drawImage(img, 30, 150, this);
       * 
       * //문자열 처리 g.drawString("paint() in SWING", 20, getHeight()-20);
       */

   }
}

class MyActionListener implements ActionListener {

   @Override
   public void actionPerformed(ActionEvent e) {
      // TODO Auto-generated method stub
      JButton b = (JButton) e.getSource();

      TTC_Client.sendMode(b.getText());
     
      WaitingRoom wait = new WaitingRoom(b.getText());
   }

}
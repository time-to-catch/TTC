package myPackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class StartRoom extends JFrame {

	ModeSelect action = new ModeSelect();
	WaitingRoom wait;

	public StartRoom(String arg) {

		setTitle(arg);
		makeUI();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1052, 764);
		setVisible(true);
	}

	private void makeUI() {
		this.setLayout(null);
		this.setBounds(0, 0, 1052, 764);
		// 버튼 생성
		JButton Teambtn = new JButton(new ImageIcon("teammatch.png"));
		JButton Singlebtn = new JButton(new ImageIcon("singlematch.png"));
		Teambtn.setText("TEAM");
		Singlebtn.setText("SINGLE");
	
		Teambtn.setBorder(new EmptyBorder(10, 10, 10, 10)); // 테두리 투명화
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
		addAction(Teambtn, action);
		addAction(Singlebtn, action);
	}

	public void paintComponent(Graphics g) { // Graphics객체를 그릴수 있는 도구.

		paintComponents(g);
		Image backImg = new ImageIcon("Login3.png").getImage();
		g.drawImage(backImg, 0, 0, getWidth(), getHeight(), this);

	}

	class ModeSelect implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			System.out.println("Select button " + b.getText());
			TTC_Client.sendMode(b.getText());
			
			wait = new WaitingRoom();
	
			String line;
			try {
					line = TTC_Client.getIn().readLine();
					System.out.println(line);
					// game Start!

					if (line.startsWith("GAMESTART")) {
						wait.setVisible(false);
						TTC_Client.mainFrame.setVisible(false);
						
						try {
							TTC_Client.gameStart();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

		}
	}

	public void stopWaiting() {
		wait.setVisible(false);
		//wait.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void addAction(JButton A, ModeSelect T) {
		A.addActionListener(T);
	}

}

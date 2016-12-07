package myPackage;

import java.awt.Color;
import java.awt.Container;
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

public class StartPanel extends JPanel {

	ModeSelect action = new ModeSelect();
	//WaitingRoom wait;
	JFrame waitFrame;
	
	public StartPanel() {
		makeUI();
		setVisible(true);
	}

	private void makeUI() {
		setLayout(null);
		setBounds(0, 0, 1052, 764);
		// ��ư ����
		JButton Teambtn = new JButton(new ImageIcon("teammatch.png"));
		JButton Singlebtn = new JButton(new ImageIcon("singlematch.png"));
		Teambtn.setText("TEAM");
		Singlebtn.setText("SINGLE");

		Teambtn.setBorder(new EmptyBorder(10, 10, 10, 10)); // �׵θ� ����ȭ
		Singlebtn.setBorder(new EmptyBorder(10, 10, 10, 10));
		Teambtn.setBackground(new Color(9, 21, 52));
		Singlebtn.setBackground(new Color(9, 21, 52));
		add(Singlebtn);
		add(Teambtn);
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

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Image backImg = new ImageIcon("Login3.png").getImage();
		g.drawImage(backImg,0,0,getWidth(),getHeight(),this);
	}

	class ModeSelect implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			System.out.println("Select button " + b.getText());
			TTC_Client.sendMode(b.getText());

			waitFrame = new JFrame();
			Container con = waitFrame.getContentPane();
			con.add(new WaitingPanel());
			waitFrame.setUndecorated(true);
			waitFrame.setVisible(true);
			waitFrame.setSize(380,200);

			String line;
			try {
				line = TTC_Client.getIn().readLine();
				System.out.println(line);
				// game Start!

				if (line.startsWith("GAMESTART")) {
					try {
						waitFrame.setVisible(false);
						TTC_Client.mainFrame.dispose();
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

	/*public void stopWaiting() {
		wait.setVisible(false);
		// wait.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}*/

	public void addAction(JButton A, ModeSelect T) {
		A.addActionListener(T);
	}

}

package myPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import myPackage.StartPanel.ModeSelect;

public class TTC_Client {

	static BufferedReader in;
	static PrintWriter out;
	int pNum;
	static int answer;
	String name;
	JFrame frame = new JFrame("Info");
	static JFrame mainFrame;
	JFrame wait;
	JPanel panel;
	JTextField textField = new JTextField(40);
	JTextArea messageArea = new JTextArea(8, 40);

	public TTC_Client() {
		mainFrame = new JFrame();
		panel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(new ImageIcon("Login3.png").getImage(), 0, 0, getWidth(), getHeight(), this);
				setOpaque(false);
				super.paintComponent(g);
			}

		};

		JButton Teambtn = new JButton(new ImageIcon("teammatch.png"));
		JButton Singlebtn = new JButton(new ImageIcon("singlematch.png"));
		Teambtn.setText("TEAM");
		Singlebtn.setText("SINGLE");

		Teambtn.setBorder(new EmptyBorder(10, 10, 10, 10)); // 테두리 투명화
		Singlebtn.setBorder(new EmptyBorder(10, 10, 10, 10));
		Teambtn.setBackground(new Color(9, 21, 52));
		Singlebtn.setBackground(new Color(9, 21, 52));
		mainFrame.add(Singlebtn);
		mainFrame.add(Teambtn);
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

		Singlebtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// here do what you gotta do when the button is clicked
				JButton b = (JButton) e.getSource();
				TTC_Client.sendMode(b.getText());
			}

		});

		Teambtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// here do what you gotta do when the button is clicked
				JButton b = (JButton) e.getSource();
				TTC_Client.sendMode(b.getText());
			}

		});

		mainFrame.setTitle("Time to Catch!");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setVisible(true);
		mainFrame.add(panel);
		mainFrame.setResizable(false);
		mainFrame.setSize(1052, 764);
		mainFrame.setLocation(450, 125);
		mainFrame.setVisible(true);
	}

	private String getName() {
		return JOptionPane.showInputDialog(frame, "Enter your name:", "Name selection", JOptionPane.PLAIN_MESSAGE);
	}// 이건 첫번쨰 name 받을때

	private String getName(String str) {
		return JOptionPane.showInputDialog(frame, str, "Name selection", JOptionPane.PLAIN_MESSAGE);
	}

	private void run() throws IOException {

		// Make connection and initialize streams
		String serverAddress = "127.0.0.1";// Set server IP address
		Socket socket = new Socket(serverAddress, 9001);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
		String line;
		int c = 1;

		while (true) {

			line = in.readLine();
			System.out.println(line);
			if (line.startsWith("SUBMITNAME") && c == 1) {
				name = getName();
				out.println(name);
			} else if (line.startsWith("NAMEACCEPTED")) {
				break;
			} else {
				name = getName("Enter another name:");
				out.println(name);
			}
			c++;

		}

		while (true) {

			line = in.readLine();
			System.out.println(line);
			if (line.startsWith("WAIT")) {
				wait = new JFrame();
				JPanel p = new JPanel(){
					public void paintComponent(Graphics g) {
						g.drawImage(new ImageIcon("waiting.png").getImage(), 0, 0, getWidth(), getHeight(), this);
						setOpaque(false);
						super.paintComponent(g);
					}
				};
						
				wait.add(p);
				wait.setSize(593,241);
				wait.setLocation(700, 400);
				wait.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				wait.setUndecorated(true);
				wait.setVisible(true);
				
				//start wait!
			}else if (line.startsWith("GAMESTART")) {
				//stop wait!
				wait.setVisible(false);
				wait.dispose();
			} else if (line.startsWith("TEAM")) {
				line = line.substring(5, line.length());
				//change gui!
//				if (line.equalsIgnoreCase("infectee"))
//					con.add(new GameRoomPanel("TTC_Gui_infectee.png"));
//				else
//					con.add(new GameRoomPanel("TTC_Gui_Noninfectee.png"));
			} else if (line.startsWith("PROBLEM")) {
				line = line.substring(8, line.length());
				//print in frame!
			} else if (line.startsWith("CHOICE")) {
				line = line.substring(8, line.length());
				//print in frame!
			} else if (line.startsWith("ANSWER")) {
				answer = Integer.parseInt(line.substring(7, line.length()));
				//check and send result to server!
			} else if (line.startsWith("NOTICE")) {
				line = line.substring(7, line.length());
				//print in frame!
			}
		}

	}

	public static BufferedReader getIn() {
		return in;
	}

	public static void sendMode(String m) {
		out.println("MODE " + m);
	}

	public void answerCheck(String num) throws IOException {
		if (answer == Integer.parseInt(num)) {
			out.println("CORRECT " + name);
		} else {
			out.println("INCORRECT " + name);
		}
	}

	public static void main(String[] args) throws Exception {
		TTC_Client client = new TTC_Client();
		client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.frame.setVisible(false);
		client.frame.setResizable(false);
		client.run();
	}

}
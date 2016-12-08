package myPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class TTC_Client {

	static BufferedReader in;
	static PrintWriter out;
	int pNum;
	static int answer;
	String name;
	JFrame frame = new JFrame("Info");
	JFrame mainFrame;
	JFrame wait;
	JPanel panel;
	JPanel inP, nonP;
	JTextField textField = new JTextField(40);
	JTextArea messageArea = new JTextArea(8, 40);
	JTextArea question;
	JTextArea status;
	Scanner fr = null;
	JFrame gameframe;
	Sound bgm;

	public TTC_Client() {
		mainFrame = new JFrame();
		panel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(new ImageIcon("Login3.png").getImage(), 0, 0, getWidth(), getHeight(), this);
				setOpaque(false);
				super.paintComponent(g);
			}

		};

		nonP = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(new ImageIcon("TTC_Gui_Noninfectee.png").getImage(), 0, 0, getWidth(), getHeight(), this);
				setOpaque(false);
			};
		};

		inP = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(new ImageIcon("TTC_Gui_infectee.png").getImage(), 0, 0, getWidth(), getHeight(), this);
				setOpaque(false);
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
				sendMode(b.getText());
			}

		});

		Teambtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// here do what you gotta do when the button is clicked
				JButton b = (JButton) e.getSource();
				sendMode(b.getText());
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

		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) // WindowAdapter class
														// Overriding
			{
				mainFrame.dispose(); // 내가 사용하던 자원(memory)을 해제하는 method
				sendQuit();
				System.out.println("QUIT");
				System.exit(0); // 프로그램 종료 하기
			}
		});
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
		String color;
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

		c = 0;
		while (true) {

			line = in.readLine();
			System.out.println(line);
			if (line.startsWith("WAIT") && c == 0) {
				c++;
				wait = new JFrame();
				JPanel p = new JPanel() {
					public void paintComponent(Graphics g) {
						g.drawImage(new ImageIcon("waiting.png").getImage(), 0, 0, getWidth(), getHeight(), this);
						setOpaque(false);
						super.paintComponent(g);
					}
				};

				wait.add(p);
				wait.setSize(593, 241);
				wait.setLocation(700, 400);
				wait.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				wait.setUndecorated(true);
				wait.setVisible(true);
				// wait.setAlwaysOnTop(true);
				// start wait!
			} else if (line.startsWith("GAMESTART")) {
				// stop wait!
				wait.setVisible(false);
				wait.dispose();
			} else if (line.startsWith("TEAM")) {
				line = line.substring(5, line.length());
				// change gui!
				mainFrame.setVisible(false);
				mainFrame.dispose();
				gameframe = new JFrame();
				bgm = new Sound("mettaton_ex.wav", true);
				Container contentPane = gameframe.getContentPane();
				question = new JTextArea("This is question area!\n");
				status = new JTextArea("This is status of user!\n");
				JPanel buttonPanel = new JPanel();

				if (line.startsWith("NON")) {
					nonP.setSize(1052, 760);
					gameframe.setTitle("Noninfectee");
					gameframe.add(nonP, BorderLayout.CENTER);
					question.setBackground(new Color(71, 71, 71));
					status.setBackground(new Color(71, 71, 71));
					buttonPanel.setBackground(new Color(71, 71, 71));
				} else {
					inP.setSize(1052, 760);
					gameframe.setTitle("Infectee");
					gameframe.add(inP, BorderLayout.CENTER);
					question.setBackground(new Color(51, 0, 0));
					status.setBackground(new Color(51, 0, 0));
					buttonPanel.setBackground(new Color(51, 0, 0));
				}

				status.setVisible(true);
				gameframe.setLayout(null);
				gameframe.setBounds(0, 0, 1052, 764);
				question.setBounds(48, 40, 950, 440);
				question.setEditable(false);
				question.setOpaque(true);
				question.setFont(new Font("Terminal", 20, 20));
				question.setForeground(Color.WHITE);
				JScrollPane scroll1 = new JScrollPane(question);
				contentPane.add(scroll1);
				gameframe.add(question);

				status.setBounds(48, 515, 777, 180);
				status.setEditable(false);
				status.setOpaque(true);
				status.setFont(new Font("Terminal", 20, 20));
				status.setForeground(Color.WHITE);
				status.setVisible(true);
				JScrollPane scroll2 = new JScrollPane(status);
				contentPane.add(scroll2);
				gameframe.add(status);

				buttonPanel.setLayout(new GridLayout(2, 2));
				buttonPanel.setSize(20, 20);
				buttonPanel.setLocation(750, 490);
				buttonPanel.setBounds(833, 515, 170, 180);
				buttonPanel.setOpaque(true);
				buttonPanel.setVisible(true);

				JButton buttonOne = new JButton(new ImageIcon("one.png"));
				JButton buttonTwo = new JButton(new ImageIcon("two.png"));
				JButton buttonThree = new JButton(new ImageIcon("three.png"));
				JButton buttonFour = new JButton(new ImageIcon("Four.png"));

				buttonSet(buttonOne);
				buttonOne.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// here do what you gotta do when the button is clicked
						JButton b = (JButton) e.getSource();
						try {
							answerCheck("1");
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

				});

				buttonSet(buttonTwo);
				buttonTwo.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// here do what you gotta do when the button is clicked
						JButton b = (JButton) e.getSource();
						try {
							answerCheck("2");
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

				});

				buttonSet(buttonThree);
				buttonThree.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// here do what you gotta do when the button is clicked
						JButton b = (JButton) e.getSource();
						try {
							answerCheck("3");
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

				});

				buttonSet(buttonFour);
				buttonFour.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// here do what you gotta do when the button is clicked
						JButton b = (JButton) e.getSource();
						try {
							answerCheck("4");
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

				});

				buttonOne.setVisible(true);
				buttonTwo.setVisible(true);
				buttonThree.setVisible(true);
				buttonFour.setVisible(true);
				buttonPanel.add(buttonOne);
				buttonPanel.add(buttonTwo);
				buttonPanel.add(buttonThree);
				buttonPanel.add(buttonFour);
				buttonPanel.setVisible(true);

				gameframe.add(buttonPanel);
				gameframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				gameframe.setResizable(false);
				gameframe.setSize(1052, 764);
				gameframe.setLocation(450, 125);
				gameframe.setVisible(true);

				gameframe.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) // WindowAdapter
																// class
																// Overriding
					{
						gameframe.dispose(); // 내가 사용하던 자원(memory)을 해제하는 method
						sendQuit();
						System.out.println("QUIT");
						System.exit(0); // 프로그램 종료 하기
					}
				});
			} else if (line.startsWith("PROBLEM")) {
				line = line.substring(8, line.length());
				String filename = line + ".txt";
				try {
					fr = new Scanner(new File(filename));
					System.out.println("Opening file " + filename);
				} catch (FileNotFoundException e) {
					System.out.println("Error opening file " + filename);
					System.exit(0);
				}
				while (fr.hasNextLine()) {
					line = fr.nextLine();
					System.out.println(line);
					if (line.startsWith("PROBLEM")) {
						line = line.substring(8, line.length());
						question.setText(line + "\n\n");
					} else if (line.startsWith("CHOICE")) {
						line = line.substring(7, line.length());
						question.append("\t" + line + "\n");
						question.setCaretPosition(question.getDocument().getLength());
						// print in frame!
					} else if (line.startsWith("ANSWER")) {
						answer = Integer.parseInt(line.substring(7, line.length()));
						// check and send result to server!
					}
				} // print in frame!
			} else if (line.startsWith("NOTICE")) {
				line = line.substring(7, line.length());
				color = line.substring(0, 1);
				line = line.substring(2, line.length());
				System.out.println(color + " " + line);
				new Sound("notice.wav", false);
				if (color.equalsIgnoreCase("R"))
					status.setForeground(Color.RED);
				else if (color.equalsIgnoreCase("G"))
					status.setForeground(Color.GREEN);
				else if (color.equalsIgnoreCase("W"))
					status.setForeground(Color.WHITE);
				status.append(line + "\n");
				status.setForeground(Color.WHITE);
				status.setCaretPosition(status.getDocument().getLength());
				// print in frame!
			} else if (line.startsWith("GAMEOVER")) {
				bgm.stop();
				new Sound("knockdown.wav", false);
				status.setForeground(Color.RED);
				status.append(line + "\n");
				break;
			}
		}
	}

	public static BufferedReader getIn() {
		return in;
	}

	public void sendMode(String m) {
		out.println("MODE " + m);
	}

	public void sendQuit() {
		out.println("QUIT " + name);
	}

	public void answerCheck(String num) throws IOException {
		if (answer == Integer.parseInt(num)) {
			System.out.println("CORRECT");
			out.println("CORRECT " + name);
			new Sound("correct.wav", false);
		} else {
			System.out.println("INCORRECT");
			out.println("INCORRECT " + name);
			new Sound("incorrect.wav", false);
		}
	}

	public void buttonSet(JButton b) {

		b.setBackground(Color.WHITE);
		b.setBorderPainted(false);
		b.setFocusPainted(false);
		b.setContentAreaFilled(true);
		b.setOpaque(false);

	}

	public static void main(String[] args) throws Exception {
		TTC_Client client = new TTC_Client();
		client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.frame.setVisible(false);
		client.frame.setResizable(false);
		client.run();
	}

}
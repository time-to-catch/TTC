package myPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TTC_Client {

	static BufferedReader in;
	static PrintWriter out;
	int pNum;
	JFrame frame = new JFrame("Time To Catch!");
	static JFrame mainFrame = new JFrame("Time To Catch");
	JTextField textField = new JTextField(40);
	JTextArea messageArea = new JTextArea(8, 40);
	static StartRoom panel;

	public TTC_Client() {

		// Layout GUI - 이거 GUI 다 되면 고쳐야 함!
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLayout(null);
		mainFrame.setBounds(200, 0, 1052, 764);
		panel = new StartRoom();
		mainFrame.add(panel);
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);

		// Add Listeners
		// textField.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// out.println(textField.getText());
		// textField.setText("");
		// }
		// });
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
			if (line.startsWith("SUBMITNAME") && c == 1) { // filter duplicate
															// name
				out.println(getName());
			} else if (line.startsWith("NAMEACCEPTED")) {
				textField.setEditable(true);
				break;
			} else
				out.println(getName("Enter another name:"));
			c++;

		}


		// 문제 맞으면 correct name으로 서버에 보내주기!
		// 틀리면 incorrect name으로 보내주기!

		// 문제 맞으면 correct name으로 서버에 보내주기!
		// 틀리면 incorrect name으로 보내주기!

		// game ends!
		// WindowHandler.windowClosing(null);
		// windowHandler.windowClosing은 어떻게 쓰는거징...?
		// socket.close();
		frame.setVisible(false);
	} // set user's name!

	public static BufferedReader getIn(){
		return in;
	}
	
	public static void sendMode(String m) {
		out.println("MODE " + m);
	}

	public static void gameStart() throws IOException {
		GameRoomUI room;
		String line = in.readLine();
		System.out.println(line);
		line = in.readLine();
		System.out.println(line);
		if (line.startsWith("TEAM")) {
			line = line.substring(5, line.length());

			if (line.equalsIgnoreCase("infectee"))
				room = new GameRoomUI("TTC_Gui_infectee.png");
			else
				room = new GameRoomUI("TTC_Gui_Noninfectee.png");
		}

	}

	public void answerCheck() throws IOException {

	}

	public static void main(String[] args) throws Exception {
		TTC_Client client = new TTC_Client();
		client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.frame.setVisible(false);
		client.frame.setResizable(false);
		client.run();
	}

}

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
	JFrame frame = new JFrame("Info");
	JFrame mainFrame;
	JTextField textField = new JTextField(40);
	JTextArea messageArea = new JTextArea(8, 40);

	public TTC_Client() {
		mainFrame = new JFrame("Time To Catch");
		mainFrame.setTitle("Time to Catch!");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);
		mainFrame.add(new StartRoom());
		mainFrame.setSize(1052, 764);
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
				out.println(getName());
			} else if (line.startsWith("NAMEACCEPTED")) {
				textField.setEditable(true);
				break;
			} else
				out.println(getName("Enter another name:"));
			c++;

		}
	}

	public static BufferedReader getIn() {
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

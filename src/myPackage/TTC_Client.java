package myPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TTC_Client {

	BufferedReader in;
	static PrintWriter out;
	JFrame frame = new JFrame("Time To Catch!");
	JTextField textField = new JTextField(40);
	JTextArea messageArea = new JTextArea(8, 40);

	public TTC_Client() {

		// Layout GUI - 이거 GUI 다 되면 고쳐야 함!
		JFrame getFrame = new JFrame("Time To Catch");
		getFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrame.setLayout(null);
		getFrame.setBounds(200, 0, 1052, 764);
		getFrame.add(new StartRoom());
		getFrame.setVisible(true);

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
		String serverAddress = "172.16.33.89";// Set server IP address
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
			frame.setVisible(false);
		} // set user's name!

		// game Start!
		line = in.readLine();
		System.out.println(line);
		if (line.startsWith("GAMESTART"))
			gameStart();

		// game ends!
		socket.close();
	}

	public static void sendMode(String m) {
		out.println("MODE " + m);
	}

	private void gameStart() throws IOException {
		
	}

	public static void main(String[] args) throws Exception {
		TTC_Client client = new TTC_Client();
		client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.frame.setVisible(false);
		client.frame.setResizable(false);
		client.run();
	}
}
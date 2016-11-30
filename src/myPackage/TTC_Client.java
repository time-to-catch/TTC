import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TTC_Client {
/*Ä¿¹ÔÄ¿¹Ô*/
	BufferedReader in;
	PrintWriter out;
	JFrame frame = new JFrame("Time To Catch");
	JLabel label = new JLabel("Welcome to Time to Catch");
    JTextField textField = new JTextField(40);
    JTextArea messageArea = new JTextArea(8, 40);

	Dimension dim = new Dimension(1024, 768);

	public TTC_Client() {
		new MyFrame();

	}
	private String getServerAddress() {
		return JOptionPane.showInputDialog(frame, "Enter IP Address of the Server:", "Welcome to the Chatter",
				JOptionPane.QUESTION_MESSAGE);
	}

	private String getName() {
		return JOptionPane.showInputDialog(frame, "Choose a screen name:", "Screen name selecion",
				JOptionPane.PLAIN_MESSAGE);

	}

	class MyFrame extends JFrame {
		MyFrame() {
			setTitle("Time To Catch");
			setLocation(500,200);
			setSize(1024, 768);
			makeUI();
			setVisible(false);
		}

		private void makeUI() {
			JLabel lb = new JLabel("Welcome to TTC");
			lb.setHorizontalAlignment(JLabel.CENTER);
			add(lb, BorderLayout.CENTER);
			JTextField messageField = new JTextField();
			JTextArea messageArea = new JTextArea(1, 40);
			frame.setLocation(200, 400);
			frame.setPreferredSize(dim);
			frame.getContentPane().add(messageField, "North");
			frame.getContentPane().add(new JScrollPane(messageArea), "Center");
			frame.pack();

			messageField.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					out.println(messageField.getText());
					messageField.setText("");
				}
			});
		}
	}
	
    private void run() throws IOException {

        // Make connection and initialize streams
        String serverAddress = getServerAddress();
        Socket socket = new Socket(serverAddress, 9001);
        in = new BufferedReader(new InputStreamReader(
            socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        // Process all messages from server, according to the protocol.

        while (true) {
            String line = in.readLine();
            if (line.startsWith("SUBMITNAME")) {	//if the message from server is start with "SUBMITNAME"
                out.println(getName());		//send the name from user to server
            } else if (line.startsWith("NAMEACCEPTED")) {	//else if the name accepted, text field mode is change false to true
                textField.setEditable(true);
            } else if (line.startsWith("MESSAGE")) {	//else if the message is start with "MESSAGE ", print the message on client screen
                messageArea.append(line.substring(5) + "\n");
            }
        }
    }
    
    
	public static void main(String[] args) throws Exception {
		TTC_Client client = new TTC_Client();
		client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.run();
	}



}

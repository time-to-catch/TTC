import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class TTC_Server {

	private static final int PORT = 1109;
	private static ArrayList<String> names = new ArrayList<String>();
	private static ArrayList<PrintWriter> writers = new ArrayList<PrintWriter>();
	private static ArrayList<PrintWriter> infectee = new ArrayList<PrintWriter>();
	private static ArrayList<PrintWriter> nonInfectee = new ArrayList<PrintWriter>();

	public static void main(String[] args) throws Exception {
		System.out.println("TTC server is running.");

		ServerSocket listener = new ServerSocket(PORT);

		try {
			while (true) {
				new Handler(listener.accept()).start();
			}

		} finally {
			listener.close();
		}
	}

	private static class Handler extends Thread {

		private String name;
		private Socket socket;
		private BufferedReader inFromClient;
		private PrintWriter outToClient;
		public int currentRoom;

		String[] room;

		public Handler(Socket socket) {
			this.socket = socket;
		}

		public void run() {

			try {
				inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				outToClient = new PrintWriter(socket.getOutputStream(), true);

				// add name
				while (true) {
					outToClient.println("SUBMITNAME");
					name = inFromClient.readLine();
					if (name == null) {
						return;
					}
					synchronized (names) {
						if (!names.contains(name)) {
							names.add(name);
							break;
						}
					}
				}

				// 2 player game or team play game?
				// if players are even number?
				if (names.indexOf(name) % 2 == 0) {
					infectee.add(outToClient);
				} else {
					nonInfectee.add(outToClient);
				}
				// if not? wait

				gameStart();

			} catch (IOException e) {
				System.out.println(e);
			}
		}

		String answer = null;

		public void gameStart() throws IOException {

			try {
				inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				outToClient = new PrintWriter(socket.getOutputStream(), true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			outToClient.println("GAMESTART"); // if client receive this message,
												// change mode to GAMEMODE
			// 감염자인지 비감염자인지 보내줘야함

			makeRoom(names.size());
			setUserPlace(names.size());
			problemSender();
			answerCheck();

		}

		public void makeRoom(int numUser) {
			room = new String[numUser * 3]; // empty : 'E', exist: index of
											// names
			for (int i = 0; i < numUser * 3; i++) {
				room[i] = "E";
			}
		}

		public void setUserPlace(int numUser) {
			int fraction = 3;
			for (int i = 0; i < numUser; i++) {
				room[i * fraction] = String.valueOf(i);
				currentRoom = i * fraction;
			}

		}

		public void goNextRoom() {

		}

		public void problemSender() {
			try {
				inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				outToClient = new PrintWriter(socket.getOutputStream(), true);

				int fileNum = (int) (Math.random() + 1); // (int)(Math.random()*'problem
															// number' + 1)
				String fileName = fileNum + ".txt";
				Scanner fileReader = null;

				// file open
				try {
					fileReader = new Scanner(new File(fileName));
					System.out.println("Opening file " + fileName);
				} catch (FileNotFoundException e) {
					System.out.println("Error opening file " + fileName);
					System.exit(0);
				}

				// transfer problem
				String problem = null;
				outToClient = new PrintWriter(socket.getOutputStream(), true);
				while (true) {
					while (fileReader.hasNextLine()) {
						problem = fileReader.nextLine();
						if (!problem.startsWith("ANSWER")) {
							outToClient.println(problem);
						} else {
							answer = problem.substring(7, problem.length());
						}
					}
					fileReader.close();
				}
			} catch (IOException e) {
				System.out.println(e);
			}
		}

		public void answerCheck() throws IOException {
			try {
				inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				outToClient = new PrintWriter(socket.getOutputStream(), true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String ClientA;
			String ServerA;
			ClientA = inFromClient.readLine();
			ServerA = answer;

			if (ClientA.equalsIgnoreCase(ServerA)) {
				outToClient.println("CORRECT! GO to next room!");
				goNextRoom();
			} else {
				outToClient.println("INCORRECT! Go to next Problem.");
				problemSender();
			}

		}
	}
}

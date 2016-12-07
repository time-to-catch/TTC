package myPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TTC_Server {

	private static final int PORT = 9001;
	private static ArrayList<String> names = new ArrayList<String>();
	private static ArrayList<Player> singlePlayers = new ArrayList<Player>();
	private static ArrayList<Player> teamPlayers = new ArrayList<Player>();
	public static int totalcnt = 0;

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
							System.out.println("User -" + name + "- Login complete");
							break;
						}
					}
				}
				outToClient.println("NAMEACCEPTED");
				totalcnt++;
				System.out.println("number of total person: " + totalcnt);

				String mode;
				Player temp = new Player();

				mode = inFromClient.readLine();
				if (mode.startsWith("MODE")) {

					mode = mode.substring(5, mode.length());
					temp.name = name;
					temp.mode = Mode.valueOf(mode);
					temp.setPrintWriter(outToClient);
					if (temp.getMode() == Mode.TEAM)
						teamPlayers.add(temp);
					else if (temp.getMode() == Mode.SINGLE)
						singlePlayers.add(temp);

					System.out.println("user " + temp.getName() + " enter " + temp.getMode());

				} else {
					System.out.println("Mode Error");
				}
				System.out.println("");
				System.out.println("num of Single Match: " + singlePlayers.size());
				System.out.println("num of Team Match: " + teamPlayers.size());
				System.out.println("");

				// change to GAMEMODE
				if (singlePlayers.size()!=0&&singlePlayers.size()%2 == 0) {
					System.out.println("Single Match start");
					new singleGameStart(singlePlayers, socket);
				} else if (teamPlayers.size()!=0&&teamPlayers.size()%4 == 0) {
					System.out.println("Team Match start");
					new teamGameStart(teamPlayers);
				}

			} catch (IOException e) {
				System.out.println(e);
			}
		}

	}
}

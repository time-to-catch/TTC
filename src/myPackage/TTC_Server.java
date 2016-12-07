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
	static singleGameStart singleRoom;
	static teamGameStart teamRoom;
	
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

		public String line;
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
					line = inFromClient.readLine();
					if (line == null) {
						return;
					}
					synchronized (names) {
						if (!names.contains(line)) {
							names.add(line);
							System.out.println("User -" + line + "- Login complete");
							break;
						}
					}
				}
				outToClient.println("NAMEACCEPTED");
				totalcnt++;
				System.out.println("number of total person: " + totalcnt);

				Player temp = new Player();
				
				while(true){
				line = inFromClient.readLine();
				if (line.startsWith("MODE")) {
						
					line = line.substring(5, line.length());	//name
					temp.name = line;
					temp.mode = Mode.valueOf(line);
					temp.setPrintWriter(outToClient);
					if (temp.getMode() == Mode.TEAM)
						teamPlayers.add(temp);
					else if (temp.getMode() == Mode.SINGLE)
						singlePlayers.add(temp);
					System.out.println("user " + temp.getName() + " enter " + temp.getMode());
					System.out.println("num of Single Match: " + singlePlayers.size());
					System.out.println("num of Team Match: " + teamPlayers.size());
					
					for(int i=0;i<singlePlayers.size();i++)
						singlePlayers.get(i).toClient("WAIT");
				
					/*wait for another user*/
					while(singlePlayers.size()==0&&singlePlayers.size()%2 != 0)
					
					if (singlePlayers.size()!=0&&singlePlayers.size()%2 == 0) {
						System.out.println("Single Match start");
						singleRoom = new singleGameStart(singlePlayers, socket);
					} else if (teamPlayers.size()!=0&&teamPlayers.size()%4 == 0) {
						System.out.println("Team Match start");
						teamRoom = new teamGameStart(teamPlayers, socket);
					}
					
					
					
					
				}
				else if(line.startsWith("CORRECT")){
					int personID;
					line = line.substring(8, line.length());	//extract name
					
					personID = singlePlayers.indexOf(line);
					singleRoom.pass(personID);
					singleRoom.problemSender(personID);
				}
				else if(line.startsWith("INCORRECT")){
					int personID;
					line = line.substring(10, line.length());	//extract name
					personID = singlePlayers.indexOf(line);
					singleRoom.problemSender(personID);
				}
				else if(line.startsWith("QUIT")){	// "QUIT + name"
					int personID;
					line = line.substring(5, line.length());
					personID = singlePlayers.indexOf(line);
					singlePlayers.remove(personID);
				}
				
				
				}

				// change to GAMEMODE
				

			} catch (IOException e) {
				System.out.println(e);
			}
		}

	}
}

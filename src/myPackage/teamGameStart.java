package myPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class teamGameStart {
	
	private Socket socket;
	private BufferedReader inFromClient;
	private PrintWriter outToClient;
	
	private ArrayList<Player> currentPlayers;
	private static int roomSize;
	public int currentRoom;
	String answer = null;
	
	public teamGameStart(ArrayList<Player> singlePlayers) throws IOException {
		currentPlayers= singlePlayers;
		System.out.println("-Player List-");
		for(int i=0;i<singlePlayers.size();i++){
			System.out.println("user" +i+": " + currentPlayers.get(i).getName());
				}
		
		divdeTeam();
		setRoomSize(currentPlayers.size());
		setUserPlace(currentPlayers.size());
		
		for(int i=0;i<currentPlayers.size();i++){
			System.out.println(currentPlayers.get(i).getName()+", "
					+currentPlayers.get(i).getTeam()+", location : "+currentPlayers.get(i).getCurrentRoom());
		}
		
		gameStart();
		
	}
	public void gameStart() throws IOException {
	
		problemSender();
		answerCheck();

	}
	
	public void divdeTeam(){
		for(int i=0;i<currentPlayers.size();i++){
			if(i%2==0){
				currentPlayers.get(i).toClient("TEAM INFECTEE");
				currentPlayers.get(i).setTeam(Team.INFECTEE);
			}
			else{
				currentPlayers.get(i).toClient("TEAM NONINFECTEE");
				currentPlayers.get(i).setTeam(Team.NONINFECTEE);
			}
		}
	}

	public int getRoomSize(){
		return roomSize;
	}
	
	public void setRoomSize(int numUser) {
		roomSize = numUser * 3;
	}

	public void setUserPlace(int numUser) {
		for(int i=0;i<currentPlayers.size();i++){
			currentPlayers.get(i).setCurrentRoom(i*3);
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


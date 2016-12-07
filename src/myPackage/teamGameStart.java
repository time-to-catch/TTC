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

	private ArrayList<Player> currentPlayers;
	private static int roomSize;
	String answer;

	public teamGameStart(ArrayList<Player> singlePlayers, Socket soc) throws IOException {
		// TODO Auto-generated constructor stub
		currentPlayers = singlePlayers;
		System.out.println("-Player List-");
		for (int i = 0; i < singlePlayers.size(); i++) {
			System.out.println("user" + i + ": " + currentPlayers.get(i).getName());
			currentPlayers.get(i).toClient("GAMESTART");
		}
		divideTeam();
		setRoomSize(currentPlayers.size());
		for (int i = 0; i < currentPlayers.size(); i++) {
			currentPlayers.get(i).setRoomSize(roomSize);
		}
		System.out.println("Room size: " + getRoomSize());
		setUserPlace(currentPlayers.size());
		for (int i = 0; i < currentPlayers.size(); i++) {
			System.out.println(currentPlayers.get(i).getName() + ", " + currentPlayers.get(i).getTeam()
					+ ", location : " + currentPlayers.get(i).getCurrentRoom());
		}
		for (int i = 0; i < currentPlayers.size(); i++)
			this.problemSender(i);
	} // constructor

	public void divideTeam() {
		for (int i = 0; i < currentPlayers.size(); i++) {
			if (i % 2 == 0) {
				currentPlayers.get(i).toClient("TEAM INFECTEE");
				currentPlayers.get(i).setTeam(Team.INFECTEE);
			} else {
				currentPlayers.get(i).toClient("TEAM NONINFECTEE");
				currentPlayers.get(i).setTeam(Team.NONINFECTEE);
			}
		}
	}

	public int getRoomSize() {
		return roomSize;
	}

	public void setRoomSize(int numUser) {
		roomSize = numUser * 3;
	}

	public void setUserPlace(int numUser) {
		for (int i = 0; i < currentPlayers.size(); i++) {
			currentPlayers.get(i).setCurrentRoom((i * 3) % roomSize);
		}
	}

	public void checkingRoomState(int currentID) {
		for (int i = 0; i < currentPlayers.size(); i++) {
			{
				if (currentID != i && currentPlayers.get(i)
						.getCurrentRoom() == currentPlayers.get(currentID).getCurrentRoom() + 1) {
					sendEnding(i);
					currentPlayers.get(currentID).goToNextRoom();
					currentPlayers.get(currentID).toClient("NOTICE YOU CATCH user " + currentPlayers.get(i).getName());
					break;
				} else {
					currentPlayers.get(currentID).goToNextRoom();
				}
			}

		}
	}

	public void sendEnding(int indexOfUser) {
		currentPlayers.get(indexOfUser).setCurrentRoom(-1);
		currentPlayers.get(indexOfUser).toClient("NOTICE You are catched by opposing team HAAHAHA");
	}

	public void problemSender(int personIndex) {

		int fileNum = (int) (Math.random() + 8); // (int)(Math.random()*'problem
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
		String line;
		while (fileReader.hasNextLine()) {
			line = fileReader.nextLine();
			System.out.println(line);
			currentPlayers.get(personIndex).toClient(line);
		}
	}

	public void pass(int personIndex) {
		checkingRoomState(personIndex);
	}
}

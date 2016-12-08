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

public class singleGameStart {

	private ArrayList<Player> currentPlayers;
	private static int roomSize;
	String answer;

	public singleGameStart(ArrayList<Player> singlePlayers, Socket soc) throws IOException {
		// TODO Auto-generated constructor stub
		currentPlayers = singlePlayers;
		System.out.println("-Player List-");

		for (int i = 0; i < currentPlayers.size(); i++) {
			System.out.println("user " + i + ": " + currentPlayers.get(i).getName());
			currentPlayers.get(i).toClient("GAMESTART");
		}

		/* decide your team */
		divideTeam();

		/* decide room size */
		setRoomSize(currentPlayers.size());
		for (int i = 0; i < currentPlayers.size(); i++) {
			currentPlayers.get(i).setRoomSize(roomSize);
		}
		System.out.println("Room size: " + getRoomSize());

		/* setting location */
		setUserPlace(currentPlayers.size());
		for (int i = 0; i < currentPlayers.size(); i++) {
			System.out.println(currentPlayers.get(i).getName() + ", " + currentPlayers.get(i).getTeam()
					+ ", location : " + currentPlayers.get(i).getCurrentRoom());
		}

		/* problem send start */
		for (int i = 0; i < currentPlayers.size(); i++)
			problemSender(i);

	} // constructor

	public void divideTeam() {
		for (int i = 0; i < currentPlayers.size(); i++) {
			if (i % 2 == 0) {
				if(currentPlayers.get(i).getTeam()==Team.NONE){
				currentPlayers.get(i).toClient("TEAM INFECTEE");
				currentPlayers.get(i).setTeam(Team.INFECTEE);
				}
				else
					continue;
			} else {
				if(currentPlayers.get(i).getTeam()==Team.NONE){
				currentPlayers.get(i).toClient("TEAM NONINFECTEE");
				currentPlayers.get(i).setTeam(Team.NONINFECTEE);
				}
				else continue;
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

	public void pass(int personIndex) {
		checkingRoomState(personIndex);
	}
	
	public void checkingRoomState(int currentID) {
		for (int i = 0; i < currentPlayers.size(); i++) {
			{
				if (currentID != i && (currentPlayers.get(i)
						.getCurrentRoom() == currentPlayers.get(currentID).getCurrentRoom() + 1)) {
					sendEnding(i);
					currentPlayers.get(currentID).goToNextRoom();
					currentPlayers.get(currentID).toClient("NOTICE YOU CATCH user " + currentPlayers.get(i).getName());
					break;
				} else {
					currentPlayers.get(currentID).goToNextRoom();
				}
			}
			for (int j = i; j < currentPlayers.size(); j++) {
				checkingCloser(i, j);
			}
		}
		gameOver();
	}

	public void checkingCloser(int personA, int personB) {
		if (currentPlayers.get(personA).getCurrentRoom() - currentPlayers.get(personB).getCurrentRoom() == 1) {
			currentPlayers.get(personA).toClient("NOTICE You can feel warm temperature.");
			currentPlayers.get(personB).toClient("NOTICE You can hear foot step");
		} else if (currentPlayers.get(personB).getCurrentRoom() - currentPlayers.get(personA).getCurrentRoom() == 1) {
			currentPlayers.get(personB).toClient("NOTICE You can feel warm temperature");
			currentPlayers.get(personA).toClient("NOTICE You can hear foot step");
		}
		else return;
	}

	public void sendEnding(int indexOfUser) {
		currentPlayers.get(indexOfUser).setCurrentRoom(-1);
		currentPlayers.get(indexOfUser).toClient("NOTICE You are catched by opposing team HAAHAHA");
	}

	public void gameOver(){
		int c=0;
		for (int i = 0; i < currentPlayers.size(); i++) {
			if(currentPlayers.get(i).getCurrentRoom()==-1)
				c++;
		}
		
		if(c==currentPlayers.size()-1)
			for (int i = 0; i < currentPlayers.size(); i++)
				currentPlayers.get(i).toClient("GAMEOVER");
		else
			return;
	}

	public void problemSender(int personIndex) {
		int fileNum = (int) (Math.random() * 8 + 1);
		currentPlayers.get(personIndex).toClient("PROBLEM " + fileNum);
	}
	
	public int getPersonID(String name){
		for(int i=0;i<currentPlayers.size();i++){
			if(name.equalsIgnoreCase(currentPlayers.get(i).getName()))
					return i;				
		}
		return -1;		
	}

}

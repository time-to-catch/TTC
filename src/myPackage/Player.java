package myPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Player extends PersonInfo {
	public int currentRoom;
	public Team team;
	private static int roomSize;
	private PrintWriter outToClient;
	Scanner fileReader = null;
	
	Player(Mode m)
	{
		super(m);
		currentRoom = -1;
		team = Team.NONE;
	}
	
	Player()
	{
		super();
		currentRoom = -1;
		team = Team.NONE;
	}
	
	public void setPrintWriter(PrintWriter o)
	{
		outToClient = o;
	}
	
	public int getCurrentRoom()
	{
		return this.currentRoom;
	}
	
	public void setCurrentRoom(int num)
	{
		this.currentRoom = num;
	}
	
	public Team getTeam()
	{
		return team;
	}
	
	public void setTeam(Team t)
	{
		this.team = t;
	}
	
	public void toClient(String str)
	{
		outToClient.println(str);
	}
	
	public void sendProblem(){
		
		int fileNum = (int) (Math.random()*8); // (int)(Math.random()*'problem
		// number' + 1)
		String fileName = fileNum + ".txt";
		try {
			fileReader = new Scanner(new File(fileName));
			System.out.println("Opening file " + fileName);
		} catch (FileNotFoundException e) {
			System.out.println("Error opening file " + fileName);
			System.exit(0);
		}
		String line;
		while (fileReader.hasNextLine()) {
			line = fileReader.nextLine();
			System.out.println(line);
			this.toClient(line);
		}
	}
	
	public void goToNextRoom(){
		currentRoom = (currentRoom +1)%roomSize;
	}
	
	public void setRoomSize(int size){
		roomSize = size;
	}
}

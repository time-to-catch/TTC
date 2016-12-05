package myPackage;

import java.io.PrintWriter;

public class Player extends PersonInfo {
	public int currentRoom;
	public Team team;
	private static int roomSize;
	private PrintWriter outToClient;
	
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
	
	public void goToNextRoom(){
		currentRoom = (currentRoom +1)%roomSize;
	}
	
	public void setRoomSize(int size){
		roomSize = size;
	}
}

package myPackage;

import java.io.PrintWriter;

public class Player extends PersonInfo {
	public int room;
	public Team team;
	private PrintWriter outToClient;
	
	Player(Mode m)
	{
		super(m);
		room = -1;
		team = Team.NONE;
	}
	
	Player()
	{
		super();
		room = -1;
		team = Team.NONE;
	}
	
	public void setPrintWriter(PrintWriter o)
	{
		outToClient = o;
	}
	
	public int getRoom()
	{
		return this.room;
	}
	
	public void setRoom(int num)
	{
		this.room = num;
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
}

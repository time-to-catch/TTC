package myPackage;

public class Player extends PersonInfo {
	public int room;
	public Team team;
	
	Player(Mode m)
	{
		super(m);
		room = -1;
		team = Team.NONE;
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
}

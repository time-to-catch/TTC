package myPackage;

public class PersonInfo  {
	String name;
	public Mode mode;
	
	PersonInfo()
	{
		this.name = null;
		mode = Mode.NONE;
	}
	
	PersonInfo(Mode m)
	{
		this.name = null;
		mode = m;
	}
	
	public void setMode(Mode m)
	{
		this.mode = m;
	}
	
	public Mode getMode()
	{
		return this.mode;
	}
	
	public void setName(String n)
	{
		this.name = n;
	}
	
	public String getName()
	{
		return this.name;
	}

}

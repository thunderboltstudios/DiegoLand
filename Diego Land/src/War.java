
public class War {
	private double probability = 0.001;
	private String[] countries = {"Davis Land"};
	private int battles_won = 0;
	private int battles_lost = 0;
	
	public boolean isWarLost()
	{
		if(battles_lost >=5)
			return true;
		return false;
	}
	
	public boolean isWarWon(int enemy_units)
	{
		if(battles_won >= 5 || enemy_units <= 0)
			return true;
		return false;
	}
	
	public String WarStats() { return "War Stats\n\tBattles Won: "+battles_won+"\tBattles Lost: "+battles_lost; }
}

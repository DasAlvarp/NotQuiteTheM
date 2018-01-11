import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Blank extends Card
{
	public Blank()
	{

	}

	public int getID()
	{
		return 1;
	}

	public int[] getCost()
	{
		return new int[]{0};
	}

	public String getName()
	{
		return "Blank";
	}

	public int getMoves(){
		return 0;
	}

	public int Move()
	{
		return 0;
	}
	
	public int getHealth()
	{
		return 0;
	}
	
	public int getBaseHealth()
	{
		return 0;
	}
	
	public int getAdjustedBaseHealth()
	{
		return 0;
	}

	public int dealDamage(int damage)
	{
		return 0;
	}

	public int getAttack()
	{
		return 0;
	}
	
	public int getBaseAttack()
	{
		return 0;
	}
	
	public int getAdjustedBaseAttack()
	{
		return 0;
	}

	public int getCountdown()
	{
		return 0;
	}
	
	public int getBaseCountdown()
	{
		return 0;
	}

	public int getAdjustedBaseCountdown()
	{
		return 0;
	}

	public int[] update()
	{
		return new int[]{0};
	}

	public int[] getInfo()
	{
		return new int[]{0};
	}
}
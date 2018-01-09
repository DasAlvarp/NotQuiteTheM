import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class IceSoldier extends Card
{
	int maxMoves = 1;
	int moves = 1;
	
	int baseAtk = 2;
	int curAtk = 2;

	int baseHp = 3;
	int curHp = 3;

	int baseCd = 2;
	int cd = 2;

	public IceSoldier()
	{

	}

	public String getName()
	{
		return "Ice Soldier";
	}

	public int getMoves(){
		return moves;
	}

	public int Move()
	{
		return moves--;
	}
	
	public int getHealth()
	{
		return curHp;
	}
	
	public int getBaseHealth()
	{
		return baseHp;
	}
	
	public int getAdjustedBaseHealth()
	{
		return baseHp;//for now.
	}

	public int dealDamage(int damage)
	{
		curHp -= damage;
		return curHp;
	}

	public int getAttack()
	{
		return curAtk;
	}
	
	public int getBaseAttack()
	{
		return baseAtk;
	}
	
	public int getAdjustedBaseAttack()
	{
		return baseAtk;//once again, for now.
	}

	public int getCountdown()
	{
		return cd;
	}
	
	public int getBaseCountdown()
	{
		return baseCd;
	}

	public int getAdjustedBaseCountdown()
	{
		return baseCd;//once again, temp
	}

	public int[] update()
	{
		moves = maxMoves;
		return updateCd();
	}

	private int[] updateCd()
	{
		cd--;
		if(cd < 1){
			cd = getAdjustedBaseCountdown();
			return new int[]{0, getAttack, 0};
		}
		else
		{
			return new int[]{cd, 0, 0};//countdown, damage to them, damage to me.
		}
	}

	public int[] getInfo()
	{
		return new int[]{getAttack(), getHealth(), getCountdown()}
	}

	public int[] getCost()
	{
		return new int[]{2, 0, 2};
	}

}
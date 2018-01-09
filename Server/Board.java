import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Board
{
	ArrayList hand;
	ArrayList deck;

	ArrayList discard;
	ArrayList library;

	Card[][] parts;
	int[] idols;

	CardManager manage;

	int maxStamina;
	int curStamina;

	int maxFire;
	int curFire;

	int maxIce;
	int curIce;
	public Board()
	{
		maxStamina = 0;
		curStamina = 0;

		maxFire = 0;
		curFire = 0;

		maxIce = 0;
		curIce = 0;

		manage = new CardManager();
		idols = new int[5];
		parts = new Card[5][3];
		for(int x = 0; x < 5; x++)
		{
			idols[x] = 10;

			for(int y = 0; y < 3; y++)
			{
				parts[x][y] = new Blank();
			}
		}
	}

	public void place(int cardId, int x, int y)
	{
		Card temp = manage.getCard(cardId);
		
		//first: can we place it
		if(canAfford(temp))
		{
			if(canPlace(temp, x, y))
			{

			}
		}
	}

	public boolean canAfford(Card card)
	{
		int[] price = card.getCost();
		return (price[0] <= curStamina && price[1] <= curFire && price[2] <= curIce);
	}

	public boolean canPlace(Card card, int x, int y)
	{
		if(parts[x][y].getName().equals("Blank"))
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	//end of turn
	public void update()
	{
		for(int x = 0; x < 5; x++)
		{
			for(int y = 0; y < 3; y++)
			{
				parts[x][y].update();
			}
		}
	}

	public void getBoardState()
	{
		return parts;
	}
}
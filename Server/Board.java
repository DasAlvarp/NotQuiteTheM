import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.*;

public class Board
{
	//card stuff
	ArrayList hand;
	ArrayList deck;

	ArrayList discard;
	ArrayList library;


	//board stuff
	Card[][] parts;
	int[] idols;


	//resources
	int maxStamina;
	int curStamina;

	int maxFire;
	int curFire;

	int maxIce;
	int curIce;

	//utils
	CardManager manage;

	public Board()
	{
		//exists so a specific thing works
	}
	public Board(int[] cards)
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


		//initialize the thing
		for(int x = 0; x < 5; x++)
		{
			idols[x] = 10;

			for(int y = 0; y < 3; y++)
			{
				parts[x][y] = new Blank();
			}
		}

		//initialize hand and deck.
		hand = new ArrayList<Integer>();
		deck = new ArrayList<Integer>();

		discard = new ArrayList<Integer>();
		library = new ArrayList<Integer>();

		//fill deck.
		for(int x = 0; x < cards.length; x++)
		{
			library.add(cards[x]);
		}

		//draw cards
		for(int x = 0; x < 5; x++)
		{
			draw();
		}
		System.out.println(hand.size());
	}

	public int draw()
	{
		int card = (int)library.remove(0);//returns what was popped
		hand.add(card);
		return card;
	}

	public int sac(int handNumber, int resource)
	{
		//if resource is 0, it's cards. 1 = fire, 2=ice
		if(handNumber < hand.size())
		{
			int card = discard(handNumber);
			switch(resource)
			{
				case 0:
					draw();
					draw();
					break;
				case 1:
					maxFire++;
					curFire++;
					maxStamina++;
					curStamina++;
					break;
				case 2:
					maxIce++;
					curIce++;
					maxStamina++;
					curStamina++;
					break;
				default:
					System.out.println("Something broke");
					break;
			}
			return card;
		}
		else
		{
			return -1;
		}
	}

	public int discard(int handNumber)
	{
		int card = (int)hand.get(handNumber);
		hand.remove(handNumber);
		discard.add(card);
		return card;
	}

	//play card from hand
	public boolean placeFromHand(int handNumber, int x, int y)
	{
		if(hand.size() > 0)
		{
			int cardId = (int)hand.get(handNumber);
			if(place(cardId, x, y))
			{
				discard(handNumber);
				return true;
			}
		}
		return false;
	}

	//place card at specific position
	public boolean place(int cardId, int x, int y)
	{
		Card temp = manage.getCard(cardId);
		int[] cost = temp.getCost();
		getStats();
		if(canAfford(cost))
		{
			if(canPlace(temp, x, y))
			{
				parts[x][y] = manage.getCard(cardId);
				return true;
			}
		}
		return false;
	}

	private void getStats()
	{
		System.out.println("Stamina: " + curStamina + "\nFire: " + curFire + "\nIce: " + curIce);
	}

	public void forcePlace(int cardId, int x, int y)
	{
		parts[x][y] = manage.getCard(cardId);
	}

	public boolean canAfford(int[] price)
	{
		return (price[0] <= curStamina && price[1] <= curFire && price[2] <= curIce);
	}

	public boolean canPlace(Card card, int x, int y)
	{
		return (parts[x][y].getID() == 0);
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

	public List<Integer>[][] getBoardState()
	{
		List<Integer>[][] boardState = new List[5][3];
		for(int x = 0; x < 5; x++)
		{
			for(int y = 0; y < 3; y++)
			{
				System.out.println(x + ", " + y);
				boardState[x][y] = new ArrayList<Integer>();
				boardState[x][y].add(parts[x][y].getID());
				boardState[x][y].add(parts[x][y].getAttack());
				boardState[x][y].add(parts[x][y].getCountdown());
				boardState[x][y].add(parts[x][y].getHealth());
			}
		}
		return boardState;
	}

	public ArrayList<Integer> getHand()
	{
		return hand;
	}
}
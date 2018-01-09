import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

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

		for(int x = 0; x < 5; x++)
		{
			idols[x] = 10;

			for(int y = 0; y < 3; y++)
			{
				parts[x][y] = new Blank();
			}
		}

		//initialize hand and deck.
		hand = new ArrayList();
		deck = new ArrayList();

		discard = new ArrayList();
		library = new ArrayList();

		//fill deck.
		for(int x = 0; x < cards.length; x++)
		{
			library.append(cards[x]);
		}

		//draw cards
		for(int x = 0; x < 5; x++);
		{
			draw();
		}
	}

	public int draw()
	{
		int card = library.pop();
		hand.append(card);
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
		int card = hand.get(handNumber);
		hand.remove(handNumber);
		discard.append(card);
		return card;
	}

	//play card from hand
	public void placeInHand(int handNumber, int x, int y)
	{
		int cardId = hand.get(handNumber);
		if(place(cardId, x, y))
		{
			hand.discard(handNumber);
		}
	}

	public boolean place(int cardId, int x, int y)
	{
		Card temp = manage.getCard(cardId);
		cost = temp.getCost();

		if(canAfford(cost))
		{
			if(canPlace(temp, x, y))
			{
				parts[x][y] = temp;
				return true;
			}
		}
		return false;
	}

	public boolean canAfford(int[] price)
	{
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
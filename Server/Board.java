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

	//draw a card
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


	//slam that bad boy down.
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

	//reads inputs from player and parses it accordingly. Basically a big ass switch statement.
	public List<Integer> readInputs(ArrayList<Integer> inputs)
	{
		switch(inputs.get(0))
		{
			case 1:
				return readMove(inputs);
			default:
				return new ArrayList<Integer>(0);//0 = invalid. Negative numbers mean failed attempt at command.
		}
	}

	public List<Integer> readMove(ArrayList<Integer> inputs)
	{
		if(canMove(inputs.get(1), inputs.get(2), inputs.get(3), inputs.get(4)))
		{
			Card temp = parts[inputs.get(1)][inputs.get(2)];
			parts[inputs.get(1)][inputs.get(2)] = parts[inputs.get(3)][inputs.get(4)];
			parts[inputs.get(3)][inputs.get(4)] = temp;
			return new ArrayList<Integer>(1);
		}
		else
		{
			return new ArrayList<Integer>(-1);
		}
	}


	public boolean canMove(int x1, int y1, int x2, int y2)
	{
		//if it's adjacent and one of them isn't blank and the other is, then you can move.
		if(isAdjacent(x1, y1, x2, y2))
		{
			if(parts[x1][y1].getID() != parts[x2][y2].getID()) 
			{
				if(parts[x1][y1].getID() == 0)
				{
					if(parts[x2][y2].getMoves() > 0)
					{
						return true;
					}
				}
				else if(parts[x2][y2].getID() == 0)
				{
					if(parts[x1][y1].getMoves() > 0)
					{
						return true;
					}
				}
			}
		}
		return true;//true for pure thing goes from pt a to pt b to work
	}

	public boolean isAdjacent(int x1, int y1, int x2, int y2)
	{
		return true;
		//stub  for now.
		/*
		if(x1 == x2)
		{
			if(y1 == y2 + 1)
			{
				return true;
			}
			else if(y1 == y2 -1)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else if(y1 == y2)
		{
			if(x1 == x2 + 1)
			{
				return true;
			}
			else if(x1 == x2 - 1)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else if(x1 % 2 == 1)
		{
			if(y1 == y2 -1)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			if()
		}*/
	}
}
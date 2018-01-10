import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

//this assumes everybody's connected. DC's will be dealt with later.
public class GameManager
{
	Socket[] players;
	int roundNum;
	Board[] boards;

	public GameManager(Socket[] players)
	{
		boards = new Board[2];
		
		//filling up a board with all of a card. Won't be like that.
		int[] justSoldiers = new int[50];
		for(int x = 0; x < 50; x++)
		{
			justSoldiers[x] = 1;
		}

		for(int x = 0; x < 2; x++)
		{
			boards[x] = new Board(justSoldiers);
		}

		boards[0].placeInHand(0, 1, 1);
		boards[0].sac(0, 2);
		boards[0].sac(0, 2);
		boards[0].placeInHand(0, 2, 2);


		this.players = players;
		roundNum = 0;
	}
}
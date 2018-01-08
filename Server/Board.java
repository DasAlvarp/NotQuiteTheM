import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Board
{
	Card[][] parts;
	int[] idols;

	public Board()
	{
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
}
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Board
{
	Card[][] parts;
	public Board()
	{
		parts = new Card[3][5];
		for(int x = 0; x < 3; x++)
		{
			for(int y = 0; y < 5; y++)
			{
				parts[x][y] = new Blank();
			}
		}
	}
}
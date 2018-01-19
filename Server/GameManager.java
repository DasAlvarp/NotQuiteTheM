import java.io.*;
import java.net.*;
import java.nio.*;
import java.math.BigInteger;
import java.util.*;

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
		boards[0].sac(0, 2);
		boards[0].sac(0, 2);
		boards[0].placeFromHand(1, 2, 2);

		this.players = players;
		roundNum = 0;
		sendBoardState(0);
	}

	public void sendBoardState(int player)
	{
		//for now, we're just gonna send the player their own board state. Other ones will come later.
		List<Integer>[][] board = boards[player].getBoardState();

		ArrayList flattenedBoard = new ArrayList<Integer>();
		
		//2147483647
		for(int x = 0; x < 5; x++)
		{
			for(int y = 0; y < 3; y++)
			{
				//negative numbers are coordinates.
				flattenedBoard.add(-1 - x);
				flattenedBoard.add(-1 - y);
				for(int z = 0; z < board[x][y].size(); z++)
				{
					flattenedBoard.add(board[x][y].get(z));
				}
			}
		}

		byte[] toSend = new byte[4 * flattenedBoard.size()];
		for(int x = 0; x < toSend.length; x++)
		{
			toSend[x] = (byte)0;
		}

		for(int x = 0; x < flattenedBoard.size(); x++)
		{
			byte[] thatNumber = ByteBuffer.allocate(4).putInt((int)flattenedBoard.get(x)).array();
			for(int y = 0; y < thatNumber.length; y++)
			{
				toSend[x * 4 + y] = thatNumber[y]; 
			}
		}

		try
		{
			players[player].getOutputStream().write(toSend);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
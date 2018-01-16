import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class ServerManager
{
	Socket[] players;
	static ServerSocket server;

	public ServerManager(int port)
	{
		players = new Socket[2];
		try{
			server = new ServerSocket(port);
		}catch(Exception e){
			e.printStackTrace();
		}
		//first, we connect the players
		getPlayers(server);
		GameManager manager = new GameManager(players);
	}


	//connects those players.
	private void getPlayers(ServerSocket server)
	{	
		int socketNum = getValidSocket(10000);
		
		//connecting to both players
		while(socketNum != -1){
			//accept connections
			try
			{
				players[socketNum] = server.accept();
				System.out.println("Connected to player " + socketNum);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			socketNum = getValidSocket(10000);
		}
	}


	//makes sure the players are all good.
	private int getValidSocket(int timeout)
	{
		for(int x = 0; x < 2; x++){
			//if there's any kind of issue connecting, then it's not set up, so need to set that socket up.
			try{
				if(!players[x].getInetAddress().isReachable(timeout))
				{
					return x;
				}

			}catch(Exception e){
				return x;
			}
		}
		return -1;
	}

	private static void reset()
	{

	}
}
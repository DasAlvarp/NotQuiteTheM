import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class SingleGameConnector
{
	public static void main(String[] args)
	{
		int port = 56789;
		ServerManager server = new ServerManager(port);
	}
}
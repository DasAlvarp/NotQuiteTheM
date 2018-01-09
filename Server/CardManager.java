import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

//probably just a factory, but I've never used one of them before, so gonna get this.
public class CardManager
{
	public Card getCard(int id)
	{
		switch(id)
		{
			case 1:
				return new IceSoldier();
			default:
				return new Blank();
		}
	}
}
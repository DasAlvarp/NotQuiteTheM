import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

//this will probably evolve into a creature rather than card, but for now, we'll just sick with "card"
abstract public class Card
{
	abstract public String getName();
	abstract public int getMoves();
	abstract public int Move();

	abstract public int getHealth();
	abstract public int getBaseHealth();
	abstract public int getAdjustedBaseHealth();

	abstract public int dealDamage(int damage);

	abstract public int getAttack();
	abstract public int getBaseAttack();
	abstract public int getAdjustedBaseAttack();

	abstract public int getCountdown();
	abstract public int getBaseCountdown();
	abstract public int getAdjustedBaseCountdown();
	
	abstract public int[] getInfo();

	abstract public int[] update();
}
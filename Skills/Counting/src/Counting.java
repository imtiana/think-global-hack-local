import javax.swing.JFrame;
import javax.swing.JLabel;

public class Counting 
{
	public static void main(String[] args) 
	{
	    JFrame f = new GameWindow();
	    GameLogic newGame = new GameLogic(1);	// change the magic number later
	    Integer randomNum = newGame.GenerateRandomNumber();
	    JLabel num = new JLabel(randomNum.toString());
	    num.setVisible(true);
	    f.add(num);
	}
}

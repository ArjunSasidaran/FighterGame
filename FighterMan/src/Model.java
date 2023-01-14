/* Model.java
 * Created By: Arjun S.
 * Last Modified: 2022/06/01
 * Program Description: Class is used to to store the model code */

// Packages files and imports modules
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.PrintWriter;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class Model extends Object
{
	private View view;
	private Controller controller;
	private boolean added = false;
	private int playerWins; // player wins
	private int compWins; // computer wins
	private int rounds; // total rounds
	private int highestWinStreak; // highest win streak
	
	// constructor method
	public Model()
	{
		super();
	}

	// Method for setting the view for the game
	public void setGUI(View viewObject)
	{
		this.view = viewObject;
	}

	// Method for adding the icons to the game
	public ArrayList<Icons> addIcon()
	{
		Icons firstLive = new Icons(5, 5, 0, view);
		Icons secLive = new Icons(5, 5, 0, view);
		Icons thirdLive = new Icons(5, 5, 0, view);
		ArrayList<Icons> iconList = new ArrayList<Icons>(Arrays.asList(firstLive, secLive, thirdLive));
		return iconList;
	}

	// Method for creating thread
	public void createThread(JLabel text, ArrayList<Icons> iconList, boolean addController, String threadName, JFrame window, JPanel aPanel, Icons player, Icons aPunch, Icons aCompPunch , Icons aComp)
	{
		ThreadRunner threadRunner = new ThreadRunner(text, iconList, this, player, aPunch, aCompPunch , aComp);
		Thread createThread = new Thread(threadRunner);
		createThread.setName(threadName);
		createThread.start();

		// Adds listener based on parameter
		if (addController && !added)
		{
			Controller control = new Controller(window, aPanel, view, threadRunner, this);
			this.controller = control;
			control.addKeyListener();
		}
	} // end of createThread

	// Method for creating map
	public void createMap()
	{
		view.createMap();
		controller.setMenu(false);
	} // end of createMap

	//Method to move the enemy Robot
	public void moveRobot(Icons comp , Icons compPunch)
	{
		while(true)
		{
			//Variable Declaration
			double direction = Math.random(); // Generates a random number to determine direction
			int randomMoves = (int)(Math.random()*(150-100) + 50); //Moves a randomly generated number of spaces

			while(randomMoves > 0)
			{	
				//moves left if number is greater than 0.5
				if(direction>0.5)
				{
					comp.setBounds(comp.getX()-1, comp.getY(), 100, 100);
					{
						try { Thread.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
						
						// Prevents robot from getting stuck on the left
						if(comp.getX()<=0)
							direction = 0.4; // reset direction to the right
					}		
					randomMoves--;
				}
				else
				{
					comp.setBounds(comp.getX()+1, comp.getY(), 100, 100);
					{
						try { Thread.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }

						//Prevents robot from getting stuck on right
						if(comp.getX()>= view.getWidth()-40)
							direction = 0.6; //reset the direction to the left
					}
					randomMoves--;
				}

			}
		}

	} // end of moveRobot

	//Method for robot to shoot player 
	public void shootPlayer(Icons comp, Icons compPunch)
	{
		//Variable Declaration
		int y = compPunch.getY();	
		int x = comp.getX();
		int height = comp.getHeight();
		
		//Delay
		try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
		
		//Loops until fist hits the bottom
		while(true)
		{
			compPunch.setVisible(true);
			compPunch.setBounds(x, height + y++,100,100);
			try { Thread.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }

			//Checks to see if punch reaches the bottom
			if(compPunch.getY() == view.getHeight())
			{
				//Delay between shots
				try { Thread.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
				y = 0;	
				x = comp.getX();
			}
		}
	} // end of shootPlayer
	
	
	// Method for game over
	public void outOfLives(int playerLives, int compLives , int winStreak , int aRounds , int aCompWins, int aPlayerWins, int aHighestWinStreak)
	{
		String gameStatus = null;
		controller.setTracker(3);
		
		//Allocating variables
		rounds = aRounds;
		compWins = aCompWins;
		playerWins = aPlayerWins;
		highestWinStreak = aHighestWinStreak;
		
		// Player won
		if (playerLives > compLives)
		{
			view.gameOver(1, playerLives,  compLives , winStreak);
			gameStatus = "Player";
		}
		// Comp won
		else if (playerLives < compLives)
		{
			view.gameOver(2, playerLives,  compLives , winStreak);
			gameStatus = "Computer";
		}

		// Tie
		if (playerLives == compLives)
		{
			view.gameOver(3, playerLives,  compLives, winStreak);
			gameStatus = "Tie";
		}
	
		//Print to the file
		try {
			  File file = new File("results.txt");
		      PrintWriter myWriter = new PrintWriter(file);
		      myWriter.println("Rounds: " + rounds + " \n");
		      myWriter.println("Computer Wins : " + aCompWins + " \n");
		      myWriter.println("Player Wins : " + aPlayerWins + " \n");
		      myWriter.println("Highest Winstreak : " + aHighestWinStreak + " \n");
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();}
				      
	}
	
	// set live tracker
	public void setLives(boolean value)
	{
		controller.setLives(value);
	}
	
	// set tracker
	public void setTracker(int value)
	{
		controller.setTracker(value);
	}
	
	// set menu
	public void setMenu(boolean value)
	{
		controller.setMenu(value);
	}
		
	
	// method to read file
	public void readFile(File file)
	{
		try {
		file = new File("results.txt");
		Scanner myReader = new Scanner(file);
		      
		while (myReader.hasNextLine()) 
		{
		  String data = myReader.nextLine();
		  System.out.println(data);
		}
		
		myReader.close();
		} catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();}
	}
	
	// method to display the end screen using view class
	public void endScreen()
	{
		readFile(new File("results.txt"));
		view.endScreen(rounds, compWins, playerWins, highestWinStreak);
	}
}// end of class

/* ThreadRunner.java
 * Created by: Arjun.S 
 * Last Modified: 2022/06/02
 * Program Description: Class is used to run a multi-thread program */

// Packages files and imports modules
import javax.swing.*;
import java.util.ArrayList;

public class ThreadRunner implements Runnable
{
	// Variable declaration
	private static Icons player, playerPunch, compPunch , comp; // player
	private Model model; // Stores the model
	private static int rounds; // number of rounds
	private static int playerWins; // number of player wins
	private static int compWins; // number of computer wins
	private static int winStreak; //Stores the win streak
	private static int highestWinStreak;
	private int xValue = 0; // Stores the x value
	private JLabel text; // Stores the text for the JLabel
	private boolean menu = true; // Keeps track of whether the user is in the main menu
	private static boolean moveLeft = false, moveRight = false; // Checks whether the player is moving left
	private static boolean shooting = false; // Checks whether a bullet is shot
	private static boolean gameFinished = false, restart = false; // Stores whether game is finished or not
	private ArrayList<Icons> iconList; // list of lives
	private static ArrayList<Icons> playerList, compList; // class variable for lives

	// Constructor method
	public ThreadRunner(JLabel title, ArrayList<Icons> anIconList, Model aModel, Icons aPlayer, Icons playerPunch, Icons compPunch , Icons comp)
	{
		this.iconList = anIconList;
		this.text = title;
		this.model = aModel;
		this.player = aPlayer;
		this.playerPunch = playerPunch;
		this.compPunch = compPunch;
		this.comp = comp;
		this.restart = false;
	}

	// Run method
	@Override
	public void run() 
	{
		String currentThread = Thread.currentThread().getName();
		// Handles thread for the menu
		if (currentThread.equals("Menu-Thread"))
		{
			boolean direction = true; // Tracks direction
			// Loops until user is off the main screen
			while (menu)
			{
				// Moves text horizontally based on current location
				text.setBounds(xValue, 10, 200, 40);
				if (xValue >= 360)
					direction = false;
				else if (xValue == 0)
					direction = true;

				// Moves text to right
				if (direction)
					xValue++;
				// Moves text to left
				else
					xValue--;

				// Delay
				try 
				{
					Thread.sleep(50);
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			} // while loop
		}

		// Computer moving thread
		else if(currentThread.equals("Comp-Move"))
		{
			model.moveRobot(comp, compPunch);
		}

		// Computer shooting thread
		else if(currentThread.equals("Comp-Shoot"))
		{
			model.shootPlayer(comp, compPunch);
		}
		
		// Player life thread
		else if (currentThread.equals("Player-Thread"))
		{
			// Delay
			try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }

			// Loops until left most life is at a certain spot
			playerList = iconList;
			while (playerList.get(0).getX() > 10)
			{
				// Moves JLabel if it is not at certain location
				if (text.getX() != 10)
					text.setBounds(text.getX() - 1, 135, 100, 35);

				// Moves each icon in list back 1 pixel
				for (int counter = 0; counter < playerList.size(); counter++)				
					playerList.get(counter).setBounds(playerList.get(counter).getX() - 1, 165, 100, 100);
					try { Thread.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); } 
					
					  model.setLives(true);
			}
		}

		// Comp life thread
		else if (currentThread.equals("Comp-Thread"))
		{
			// Delay
			try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }

			compList = iconList;
			// Loops until left most life is at a certain spots
			while (compList.get(0).getX() < 440)
			{
				// Moves JLabel if it is not at certain location
				if (text.getX() != 440)
					text.setBounds(text.getX() + 1, 135, 100, 35);

				// Moves each icon in list back 1 pixel
				for (int counter = 0; counter < compList.size(); counter++)				
					compList.get(counter).setBounds(compList.get(counter).getX() + 1, 165, 100, 100);
				try { Thread.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
			}
		}

		// Game controls
		else if (currentThread.equals("Game-Controls"))
		{
			while (compList.size() > 0 && playerList.size() > 0)
			{
				// Moves left
				if (moveLeft) //&& !shooting)
				{
					if (player.getX() > -10)
						player.setBounds(player.getX() - 1, 280, 75, 75);
				}
				// Move right
				if (moveRight) // && !shooting
				{
					if (player.getX() < 480)
						player.setBounds(player.getX() + 1, 280, 75, 75);
				}
			
				// Hitbox for computer
				if     ((playerPunch.getY() >= comp.getY() - 20) && (playerPunch.getY() <= comp.getY() + 20)
				    &&  (playerPunch.getX() >= comp.getX() - 20) && (playerPunch.getX() <= comp.getX() + 35))
				{
					compList.get(compList.size() - 1).setVisible(false);
					compList.remove(compList.size() - 1);
					// Moves each icon in list back 1 pixel
					for (int counter = 0; counter < compList.size(); counter++)				
						compList.get(counter).setBounds(compList.get(counter).getX() + 1, 165, 100, 100);
					try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
				}
				
				// Hitbox for player
				if ((compPunch.getY() >= player.getY() - 20) && (compPunch.getY() <= player.getY() + 20)
						&&  (compPunch.getX() >= player.getX() - 20) && (compPunch.getX() <= player.getX() + 35))
				{
					// Removes player from list
					playerList.get(playerList.size() - 1).setVisible(false);
					playerList.remove(playerList.size() - 1);
					
					// Redraws lives
					for (int counter = 0; counter < playerList.size(); counter++)				
						playerList.get(counter).setBounds(playerList.get(counter).getX() + 1, 165, 100, 100);
					try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
				}
				
				// Ends game
				if (gameFinished)
				{
					gameFinished = false;
					break;
				}

				try { Thread.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
			}
			// check if game is finished
			if (!gameFinished)
			{
				rounds++;
				// increase win streak
				if(playerList.size()>compList.size())
				{	
					playerWins++; 
					winStreak++;
					// check to see if current win streak is the highest win streak
					if(highestWinStreak < winStreak)
					{
						highestWinStreak = winStreak;
					}
				}
				// reset win streak to zero
				else if(compList.size()>playerList.size())
				{
					compWins++;
					winStreak = 0;
				}
				else
					winStreak = 0;
				
				// send to model
				model.outOfLives(playerList.size(), compList.size(), winStreak , rounds , compWins, playerWins, highestWinStreak);

			}
		}

		// Game controls
		else if (currentThread.equals("Fight-Controls"))
		{
			// Loops until program is done
			while (!restart)
			{
				playerPunch.setBounds(player.getX(), player.getY(), 100, 100);
				playerPunch.setVisible(false);

				// Gets x and y
				int y = player.getY();
				int x = player.getX();
				
				while (shooting && !restart)
				{
					playerPunch.setVisible(true);
					playerPunch.setBounds(x, y--, 100, 100);
					
					//Boundary for the fist
					if(y<-35)
						shooting = false;

					try { Thread.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
					
				} // fight 

				try { Thread.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
			}
			model.setTracker(2);
		}

		// Checks whether the lives are done moving
		else
		{
			while (text.getX() != 10)
				try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
			model.createMap();
		}

	} // run method

	// Method for setting the menu
	public void setMenu(boolean value)
	{
		menu = value;
	}

	// Method for setting the moveLeft value
	public void setLeft(boolean value)
	{
		moveLeft = value;
	}

	// Method for setting the moveLeft value
	public void setRight(boolean value)
	{
		moveRight = value;
	}

	// Method for setting the value of shooting
	public void setShooting(boolean value)
	{
		this.shooting = value;
	}
	
	// Method for setting gameFinished value
	public void setGameFinished(boolean value)
	{
		this.gameFinished = value;
	}
	
	// Sets value for restarting
	public void setRestart(boolean value)
	{
		this.restart = value;
	}
	
	
} // end of class



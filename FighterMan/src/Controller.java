/* Controller.java
 * Created by: Arjun.S
 * Last Modified: 2022/06/02
 * Program Description: Class is used to implement the listeners for the game*/


// Packages files and imports module

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;


public class Controller 
{
	// Variable declaration
	private ThreadRunner menuThread;
	private Model model;
	private View view;
	private JFrame window;
	private JPanel components;
	private boolean menu = true, lives = false, released = false; 
	private int tracker = 0; // tracks where user is in the process of the program

	// constructor
	public Controller(JFrame aWindow, JPanel aPanel, View aView, ThreadRunner aMenuThread, Model aModel)
	{
		this.model = aModel;
		this.menuThread = aMenuThread;
		this.view = aView;
		this.window = aWindow;
		this.components = aPanel;
	}

	// Adds keyListener to JFrame
	public void addKeyListener()
	{
		window.addKeyListener(new KeyListener()
		{                
			// Method for pressing keys
			public void keyPressed(KeyEvent event)
			{
				// Reloads screen if user was in menu
				if (tracker == 0)
				{
					components.removeAll();
					window.setVisible(false);
					try { Thread.sleep(500); } catch (InterruptedException error) { error.printStackTrace(); }
					window.setVisible(true);
					menuThread.setMenu(false);
					tracker++;
				}
				// Map displayed
				if (tracker == 1)
				{
					view.showLives();
					tracker++;
				}
				// Controller while in game
				if (tracker == 2 && lives)
				{
					String button = Character.toString( (char)( event.getKeyCode() ) );
					// Moves left 
					if (button.equals("A") && !menu)
						menuThread.setLeft(true);
					else if (button.equals("D") && !menu)
						menuThread.setRight(true);
					else if (button.equals("#") && !menu) // end key
						menuThread.setGameFinished(true);
					else if (button.equals(" ") && !menu)
					{
						
						menuThread.setShooting(true);
					}
				}
				// End game
				if (tracker == 3)
				{
					lives = false;
					String button = Character.toString( (char)(event.getKeyCode()));
					// Ends program
					if (button.equals("N") && !menu)
					{
						try { Thread.sleep(500); } catch (InterruptedException error) { error.printStackTrace(); }
						model.endScreen();
					}
					// Restarts game
					else if (button.equals("Y") && !menu)
					{
						view.removeAll();
						menuThread.setRestart(true);
						menuThread.setGameFinished(false);
						view.layoutView();
						tracker = 4;
					}
				}
				
				
				// Remove controller
				if (tracker == 4)
					window.removeKeyListener(this);
				
				// Ends game
				if (tracker == 5 && released)
					System.exit(0);
				
				// Allows for game to be finished
				if (tracker == 5)
					released = true;
			}


			// Method for keyTyped
			public void keyTyped(KeyEvent e) {}

			// Method for keyReleased
			public void keyReleased(KeyEvent event) 
			{
				String button = Character.toString( (char)( event.getKeyCode() ) );
				// Stops moving character to the left
				if (button.equals("A") && !menu)
					menuThread.setLeft(false);
				else if (button.equals("D") && !menu)
					menuThread.setRight(false);
			}
		});
	} // keyListener

	// Method for tracking menu
	public void setMenu(boolean value)
	{
		this.menu = value;
	}

	// Method for setting tracker
	public void setTracker(int value)
	{
		this.tracker = value;
	}

	// method for setting whether lives are done moving
	public void setLives(boolean value)
	{
		this.lives = value;
	}
} // end of class

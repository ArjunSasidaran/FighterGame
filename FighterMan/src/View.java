/* View.java
 * Created By: Arjun.S
 * Last Modified: 2022/06/01
 * Program Description: Class is used to create the graphics for the game */


// Packages files and imports modules
import java.awt.*;
import java.util.*;
import javax.swing.*;


public class View extends JPanel
{
        // Variable declaration
        private Model model;
        private JFrame window;
        private JLabel winner = new JLabel(), restart = new JLabel(), wins = new JLabel();
        // Constructor method
        public View(Model aModel, JFrame aWindow)
        {
                super();
                this.window = aWindow;
                this.model = aModel;
                this.setLayout(null);
                this.model.setGUI(this);
                this.layoutView();
        } // end of constructor


        // Method for drawing the initial board
        public void layoutView()
        {
                // Declares variables
                JLabel title = new JLabel("Fighter Man"); // title of menu
                JLabel controls = new JLabel("<html>Controls! <br> A -  Move Left <br> D - Move Right <br> Space - Punch <br> End - End Game </html>"); // shows instructions
                JLabel instructions = new JLabel("<html>Welcome! <br> This is a 2D fighter game. It is very simple. You have 3 lives"
                                + " and you have to move left and right to avoid getting punched. Both you and the AI will punch each other and the first "
                                + "player to run out of lives loses. You are Blue. Good Luck. </html>"); // Instructions for the game
                JLabel continues = new JLabel("Press any key to continue."); // Tells user to continue by pressing any key
                
                // Configures JLabels
                title.setBounds(10, 10, 100, 35);
                title.setForeground(new Color(51, 220, 255));
                title.setFont(new Font("Comic Sans MS", Font.ITALIC, 30));
                controls.setBounds(40, 80, 150, 150);
                controls.setForeground(new Color(51, 135, 255));
                controls.setFont(new Font("Comic Sans MS", Font.ITALIC, 18));
                instructions.setBounds(200, 80, 300, 200);
                instructions.setForeground(new Color(51, 135, 255));
                instructions.setFont(new Font("Comic Sans MS", Font.ITALIC, 18));
                continues.setBounds(150, 200, 350, 250);
                continues.setForeground(new Color(51, 220, 255));
                continues.setFont(new Font("Comic Sans MS", Font.ITALIC, 18));
                
                // Configures GUI
                this.setBackground(new Color(50, 50, 50));
                this.add(instructions);
                this.add(continues);
                this.add(controls);
                this.add(title);


                // Animation
                model.createThread(title, null, true, "Menu-Thread", window, this, null, null, null, null);
                
        } // end of layoutView
        
        // Method for showing the lives
        public void showLives()
        {
                this.setBackground(new Color(255,165,0));
                // Creates initial screen to show lives
                JLabel firstPlayer = new JLabel("Player 1");
                JLabel comp = new JLabel("Computer");
                ArrayList<Icons> playerLife = new ArrayList<Icons>();
                ArrayList<Icons> compLife = new ArrayList<Icons>();
                
                // Creates lives for computer and user
                playerLife = model.addIcon();
                compLife = model.addIcon();
        
                // Configures JLabels
                firstPlayer.setBounds(150, 135, 100, 35);
                firstPlayer.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
                comp.setBounds(300, 135, 100, 35);
                comp.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
                this.add(firstPlayer);
                this.add(comp);
                
                // Configures lives
                for (int counter = 0; counter < playerLife.size(); counter++)
                {
                        playerLife.get(counter).setBounds(150 + counter * 15, 165, 100, 100);
                        compLife.get(counter).setBounds(310 + counter * 15, 165, 100, 100);
                }
                
                // Animates 
                model.createThread(firstPlayer, playerLife, false, "Player-Thread", window, this, null, null, null, null);
                model.createThread(comp, compLife, false, "Comp-Thread", window, this, null, null, null, null);
                model.createThread(firstPlayer, null, false, "null", window, this, null, null, null, null);
                model.setMenu(true);
                
        } // end of showLives
        
        // Method for creating the map
        public void createMap()
        {
                // Creates wall and players
                Icons playerIcon = new Icons(15, 15, 2, this);
                Icons comp = new Icons(15, 15, 3, this);
                Icons playerPunch = new Icons(15, 15, 4, this);
                Icons compPunch = new Icons(15, 15, 5, this);
                playerIcon.setBounds(200, 280, 75, 75);
                comp.setBounds(200, 5, 75, 75);
                
                // Adds controls thread
                model.createThread(null, null, false, "Game-Controls", window, this, playerIcon, playerPunch, compPunch, comp);
                model.createThread(null, null, false, "Fight-Controls", window, this, playerIcon, playerPunch, compPunch, comp);
                model.createThread(null, null, false, "Comp-Move", window, this,playerIcon, playerPunch, compPunch, comp);
                model.createThread(null, null, false, "Comp-Shoot", window, this,playerIcon, playerPunch, compPunch, comp);
            //avdd    model.createThread(null, null, false, "Camper", window, this,playerIcon, playerPunch, compPunch, comp);
        } // end of createMap
        
        // Method for executing end game window
        public void gameOver(int playerWon, int playerLives, int compLives, int winStreak)
        {
                // Re-instates the window and gets the winner
                this.removeAll();
                window.setVisible(false);
                window.setVisible(true);
                
                // Variable declaration
                winner.setBounds(135, 75, 300, 35);
                winner.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
                restart.setText("Player Lives: " + playerLives + " \t Restart: Y/N \t Computer Lives: " + compLives);
                restart.setBounds(80, 110, 450, 35);
                restart.setFont(new Font("Comic Sans MS", Font.ITALIC, 18));
                wins.setText("Total Winstreak: " + winStreak);
                wins.setFont(new Font("Comic Sans MS", Font.ITALIC, 18));
                wins.setBounds(185, 140, 180, 35);
                this.add(restart);
                this.add(winner);
                this.add(wins);
                
                
                // Player won
                if (playerWon == 1)
                {
                        // Smiley face
                        Icons smile = new Icons(40, 40, 6, this); // change 5 to 6
                        smile.setBounds(160, 180, 200, 200);
                        winner.setText("Congratulations, You Won!");
                }
                // Comp Won
                else if (playerWon == 2)
                {
                        // Smiley face
                        Icons smile = new Icons(40, 40, 7, this); // change 5 to 7
                        smile.setBounds(160, 180, 200, 200);
                        winner.setText("You Lost. Get Better");
                }
                // Tied
                else
                {
                        // Smiley face
                        Icons smile = new Icons(40, 40, 6, this); // change 5 to 6
                        smile.setBounds(160, 180, 200, 200);
                        winner.setText("You Tied.");
                }
                
        } // end of gameVver
        
        // Method for end of game
        public void endScreen(int aRoundsPlayed , int aCompWin, int aPlayerWin, int aHighestWinStreak) 
        {
        	// Resets screen
        	this.removeAll();
        	window.setVisible(false);
        	window.setVisible(true);
        	
        	// Variable declaration
        	JLabel roundsPlayed = new JLabel();
        	JLabel compWin = new JLabel();
        	JLabel playerWin = new JLabel();
        	JLabel highestWinStreak = new JLabel();
        	JLabel closeGame = new JLabel();
        	
        	// Configures JLabel
        	roundsPlayed.setText("Total Rounds Played: " + Integer.toString(aRoundsPlayed));
        	compWin.setText("Computer Wins: " + Integer.toString(aCompWin));
        	playerWin.setText("Player Wins: " + Integer.toString(aPlayerWin));
        	highestWinStreak.setText("Highest Win Streak: " + Integer.toString(aHighestWinStreak));
        	closeGame.setText("Press any key to exit.");
        	roundsPlayed.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        	compWin.setFont(new Font("Comic Sans MS", Font.ITALIC, 18));
        	playerWin.setFont(new Font("Comic Sans MS", Font.ITALIC, 18));
        	highestWinStreak.setFont(new Font("Comic Sans MS", Font.ITALIC, 18));
        	roundsPlayed.setBounds(150, 50, 300, 30);
        	compWin.setBounds(175, 100, 300, 30);
        	playerWin.setBounds(175, 150, 300, 30);
        	highestWinStreak.setBounds(175, 200, 300, 30);
        	closeGame.setBounds(175, 325, 300, 30);
        	
        	// Adds components to panel
        	this.add(closeGame);
        	this.add(roundsPlayed);
        	this.add(compWin);
        	this.add(playerWin);
        	this.add(highestWinStreak);
        	model.setTracker(5);
        	
        } // end of endScreen
} // end of class

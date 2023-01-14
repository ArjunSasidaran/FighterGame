
/* FighterManMain.java
 * Created By: Arjun.S
 * Last Modified: 2022/06/01
 * Program Description: Class is used to execute model view controller class */

// Packages files and imports module

import javax.swing.*;

public class FighterManMain 
{
	// main method
	public static void main(String[] args)
	{
		// variable declarationa
		Model model = new Model(); // This is urs
		JFrame window = new JFrame("Fighter Man");
		View viewer = new View(model, window);
		
		// JFrame configuration
		window.setSize(550, 400);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setContentPane(viewer);
		window.setVisible(true);
	} // end of main
} // end of class


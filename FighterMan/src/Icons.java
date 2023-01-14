/* Icons.java
 * Created By: Arjun.S
 * Last Modified: 2022/06/03
 * Program Description: Class is used to create the custom icons for this game*/

// Packages file and imports modules
import java.awt.*;
import javax.swing.*;

public class Icons extends JComponent
{
	private JPanel components;
	private int icon = 0;
	// constructor method
	public Icons(int sizeX, int sizeY, int aComponent, JPanel aPanel)
	{
		super();
		this.setPreferredSize(new Dimension(sizeX, sizeY));
		this.icon = aComponent;
		this.components = aPanel;
		components.add(this);
	}

	// Over-ridded paint method
	public void paintComponent(Graphics g)
	{	
		super.paintComponent(g);

		// Draws circle resembling lives
		if (icon == 0)
		{
			g.setColor(Color.RED);
			g.fillOval(10, 10, 10, 10);
		}

		// Draws wall 
		else if (icon == 1)
		{
			g.setColor(Color.BLACK);
			g.fillRect(10, 10, 20, 20);
		}
		// Draws player
		else if (icon == 2)
		{
			g.setColor(new Color(0, 0, 153));
			g.fillOval(20, 5, 26, 26);
			g.fillOval(18, 32, 28, 38);
		}

		// Draws comp
		else if (icon == 3)
		{
			g.setColor(new Color(179, 0, 0));
			g.fillOval(20, 5, 26, 26);
			g.fillOval(18, 32, 28, 38);
		}
		// Draws fist
		else if (icon == 4)
		{
			// Palm and thumb
			g.setColor(new Color(255, 223, 191));
			g.fillOval(10, 5, 40, 30);
			g.fillOval(2, 15, 12, 12);

			// Fingers
			g.setColor(new Color(139, 69, 19));
			g.fillOval(10, 2, 10, 15);
			g.fillOval(19, 2, 10, 15);
			g.fillOval(28, 2, 10, 15);
			g.fillOval(37, 2, 10, 15);
		}

		//Enemy Fist
		else if(icon == 5)
		{

			g.setColor(new Color(255, 223, 191));
			g.fillOval(10, 5, 40, 30);
			g.fillOval(44, 15, 12, 12);

			g.setColor(new Color(139, 69, 19));
			g.fillOval(10, 23, 10, 15);
			g.fillOval(19, 23, 10, 15);
			g.fillOval(28, 23, 10, 15);
			g.fillOval(37, 23, 10, 15);

		}

		// Smiley face
		else if (icon == 6 || icon == 7)
		{
			// Head
			if (icon == 6)
				g.setColor(new Color(255, 255, 0));
			else
				g.setColor(Color.RED);
			g.fillOval(10, 10, 150, 150);

			// Eyes
			g.setColor(Color.black);
			g.fillOval(40, 30, 30, 30);
			g.fillOval(95, 30, 30, 30);

			// Mouth
			g.fillOval(40, 120, 80, 20);
			if (icon == 6)
				g.setColor(Color.YELLOW);
			else
				g.setColor(Color.RED);
			g.fillOval(40, 110, 80, 20);
		}

	} // end of paintComponent
} // end of class

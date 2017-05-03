package dragonball.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.Serializable;

import javax.swing.JFrame;

public class FighterView extends JFrame implements Serializable{
	private FighterDestroyer myListener;
	
	public FighterView ()
	{
		Dimension t=Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize((int)(t.getWidth()),(int) (t.getHeight()));
		this.setTitle("My Fighter");
		myListener = new FighterDestroyer();
        addWindowListener(myListener);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setVisible(false);
		
	}

}

package dragonball.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameView extends JFrame implements Serializable{
	private WindowDestroyer windowListener;
		
	public GameView(){
		Dimension t=Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize((int)t.getWidth(),(int) t.getHeight());
		this.setTitle("Welcome to our Dragonball World!");
		windowListener = new WindowDestroyer();
        addWindowListener(windowListener);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setVisible(true);
		
	}
	
}

package dragonball.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;

import javafx.scene.layout.Border;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class StartMenuView extends JPanel implements Serializable {

	
	private JPanel buttons;
	private transient BufferedImage bufferedImage;
	private JButton start;
	private JButton load;
	
	
	public StartMenuView ()
	{
		Dimension t=Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize((int)(t.getWidth()),(int) (t.getHeight()));
		
		try 
		{
			bufferedImage = ImageIO.read(new File("StartMenu.jpg"));
		} 
		catch (IOException e) 
		{	
			e.printStackTrace();
		}
		this.setLayout(new BorderLayout());

		//we still need to center the buttons Panel
		buttons=new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.PAGE_AXIS));
		buttons.setOpaque(false);
		this.add(buttons, BorderLayout.CENTER);
		
		start=new JButton();
		start.setText("New Game");
		//start.setBackground(Color.darkGray);
		start.setOpaque(false);
		start.setContentAreaFilled(false);
		start.setBorderPainted(true);
		start.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 80));
		start.setForeground(Color.WHITE);
		start.setFocusPainted(false);		
		buttons.add(start);
		
		load=new JButton();
		load.setText("Load Game");
		//load.setBackground(Color.darkGray);
		load.setOpaque(false);
		load.setContentAreaFilled(false);
		load.setBorderPainted(true);
		load.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 80));
		load.setForeground(Color.WHITE);
		load.setFocusPainted(false);
		buttons.add(load);
		
		
		this.repaint();
		this.revalidate();
		this.setVisible(true);
		
	}
	//we need to resize image
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
	    g.drawImage((bufferedImage), 0, 0, this.getWidth(), this.getHeight(), null);
	        
	}

	public JPanel getButtons() {
		return buttons;
	}

	public void addButton(JButton button){
		buttons.add(button);
	}
	
	public JButton getStart() {
		return start;
	}
	
	public void setStart(JButton start) {
		this.start = start;
	}
	
	public JButton getLoad() {
		return load;
	}

	public void setLoad(JButton load) {
		this.load = load;
	}
	
	public static void main(String[]args)
	{
		new StartMenuView();
	}
}

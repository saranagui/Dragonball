package dragonball.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StartNewGameView extends JPanel implements Serializable {

	private JPanel fighterPanel;
	private JPanel playerPanel;
	private JPanel fighterButtons;
	private JTextField playerName;
	private JTextField fighterNameJT;
	private JButton playerNameSubmit;
	private JButton fighterNameSubmit;
	private transient BufferedImage bufferedImage;
	private JButton earthling;
	private JButton frieza;
	private JButton namekian;
	private JButton majin;
	private JButton saiyan;
	private JButton switchToWorldView;
	private JPanel fighterText;
	
	
	public JPanel getFighterText() {
		return fighterText;
	}

	public void setFighterText(JPanel fighterText) {
		this.fighterText = fighterText;
	}

	public StartNewGameView(){
		
		Dimension t=Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize((int)(t.getWidth()),(int) (t.getHeight()));
		this.setOpaque(false);
		this.setLayout(new BorderLayout());

		try 
		{
			bufferedImage = ImageIO.read(new File("wpLN0bn.jpg"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		fighterPanel=new JPanel();
		playerPanel=new JPanel();
		fighterButtons=new JPanel();
		fighterText=new JPanel();
		
		playerPanel.setLayout(new FlowLayout());
		playerPanel.setOpaque(false);
		JLabel playerNameField=new JLabel("Please Enter the Player name: ");
		playerNameField.setOpaque(false);
		playerNameField.setForeground(Color.ORANGE);
		playerNameField.setFont(new Font("Calibri", Font.PLAIN, 20));
		playerName = new JTextField();
		playerName.setPreferredSize(new Dimension(500,40));
		playerName.setFont(new Font("Calibri", Font.PLAIN, 30));
		playerName.setEditable(true);
		playerNameSubmit= new JButton("submit");
		playerNameSubmit.setBackground(Color.BLACK);
		playerNameSubmit.setOpaque(true);
		playerNameSubmit.setBorderPainted(false);
		playerNameSubmit.setFont(new Font("Calibri", Font.PLAIN, 20));
		playerNameSubmit.setForeground(Color.ORANGE);
		playerNameSubmit.setFocusPainted(false);	
		playerPanel.add(playerNameField);
		playerPanel.add(playerName);
		playerPanel.add(playerNameSubmit);
		
		fighterText.setLayout(new FlowLayout());
		fighterText.setOpaque(false);
		fighterPanel.setLayout(new BorderLayout());
		fighterPanel.setOpaque(false);
		JLabel fighterNameField=new JLabel("Please Enter the Fighter name: ");
		fighterNameField.setOpaque(false);
		fighterNameField.setForeground(Color.ORANGE);
		fighterNameField.setFont(new Font("Calibri", Font.PLAIN, 20));
		fighterNameJT = new JTextField();
		fighterNameJT.setPreferredSize(new Dimension(500,40));
		fighterNameJT.setFont(new Font("Calibri", Font.PLAIN, 30));
		fighterNameJT.setEditable(true);
		fighterNameSubmit= new JButton("submit");
		fighterNameSubmit.setBackground(Color.BLACK);
		fighterNameSubmit.setOpaque(true);
		fighterNameSubmit.setBorderPainted(false);
		fighterNameSubmit.setFont(new Font("Calibri", Font.PLAIN, 20));
		fighterNameSubmit.setForeground(Color.ORANGE);
		fighterNameSubmit.setFocusPainted(false);	
		fighterText.add(fighterNameField);
		fighterText.add(fighterNameJT);
		fighterText.add(fighterNameSubmit);
		fighterText.setVisible(false);
		
		fighterButtons.setLayout(new FlowLayout());
		fighterButtons.setOpaque(false);
		fighterButtons.setVisible(false);
		
		fighterPanel.add(fighterText,BorderLayout.NORTH);
		fighterPanel.add(fighterButtons,BorderLayout.CENTER);
		this.add(playerPanel, BorderLayout.NORTH);
		this.add(fighterPanel,BorderLayout.CENTER);
		
		switchToWorldView=new JButton();
		switchToWorldView.setText("Let's Rock and Roll");
		switchToWorldView.setBackground(Color.BLACK);
		switchToWorldView.setOpaque(false);
		switchToWorldView.setBorderPainted(false);
		switchToWorldView.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 40));
		switchToWorldView.setForeground(Color.ORANGE);
		switchToWorldView.setFocusPainted(false);
		switchToWorldView.setPreferredSize(new Dimension(500,50));
		switchToWorldView.setVisible(false);
		fighterPanel.add(switchToWorldView, BorderLayout.SOUTH);
		
		BufferedImage bufferedImageFighter=null;
		
		try
		{
			bufferedImageFighter= ImageIO.read(new File("earthling.png"));
			earthling=new JButton(new ImageIcon(fitImageToACertainSize(150,300,bufferedImageFighter)));
			earthling.setFocusPainted(false);
			earthling.setContentAreaFilled(false);
			//earthling.setBorderPainted(false);
			earthling.setBorder(BorderFactory.createTitledBorder("Earthling"));
			this.addButton(earthling);
		
			bufferedImageFighter= ImageIO.read(new File("Frieza_(2).png"));
			frieza=new JButton(new ImageIcon(fitImageToACertainSize(150,300,bufferedImageFighter)));
			frieza.setFocusPainted(false);
			frieza.setContentAreaFilled(false);
			//frieza.setBorderPainted(false);
			frieza.setBorder(BorderFactory.createTitledBorder("Frieza"));
			this.addButton(frieza);
		
			bufferedImageFighter= ImageIO.read(new File("Majin_buu.png"));
			majin=new JButton(new ImageIcon(fitImageToACertainSize(150,300,bufferedImageFighter)));
			majin.setFocusPainted(false);
			majin.setContentAreaFilled(false);
			//majin.setBorderPainted(false);
			majin.setBorder(BorderFactory.createTitledBorder("Majin"));
			this.addButton(majin);
	
			bufferedImageFighter= ImageIO.read(new File("namek_warrior.png"));
			namekian=new JButton(new ImageIcon(fitImageToACertainSize(150,300,bufferedImageFighter)));
			namekian.setFocusPainted(false);
			namekian.setContentAreaFilled(false);
			//namekian.setBorderPainted(false);
			namekian.setBorder(BorderFactory.createTitledBorder("Namekian"));	
			this.addButton(namekian);
		
			bufferedImageFighter= ImageIO.read(new File("Gokus - Copy.png"));
			saiyan=new JButton(new ImageIcon(fitImageToACertainSize(150,300,bufferedImageFighter)));
			saiyan.setFocusPainted(false);
			saiyan.setContentAreaFilled(false);
			//saiyan.setBorderPainted(false);
			saiyan.setBorder(BorderFactory.createTitledBorder("Saiyan"));	
			this.addButton(saiyan);	

		} 
		catch(IOException e)
		{
			e.printStackTrace();
		}				
		
		
		//playerPanel.setLocation(1000, 1000); this has no effect
		
		//fighterPanel.setLocation(5000, 1000); this has no effect
				
		this.repaint();
		this.revalidate();	
		this.setVisible(true);
	}
	
	public JPanel getFighterbuttons() {
		return fighterButtons;
	}

	public void setFighterbuttons(JPanel fighterbuttons) {
		this.fighterButtons = fighterbuttons;
	}

	public JTextField getPlayerName() {
		return playerName;
	}

	public void setPlayerName(JTextField playerName) {
		this.playerName = playerName;
	}

	public JButton getPlayerNameSubmit() {
		return playerNameSubmit;
	}

	public void setPlayerNameSubmit(JButton playerNameSubmit) {
		this.playerNameSubmit = playerNameSubmit;
	}

	public JButton getFighterNameSubmit() {
		return fighterNameSubmit;
	}

	public void setFighterNameSubmit(JButton fighterNameSubmit) {
		this.fighterNameSubmit = fighterNameSubmit;
	}

	public JButton getSwitchToWorldView() {
		return switchToWorldView;
	}

	public void setSwitchToWorldView(JButton switchToWorldView) {
		this.switchToWorldView = switchToWorldView;
	}

	public void setFighterName(JTextField fighterName) {
		this.fighterNameJT = fighterName;
	}

	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
	    g.drawImage((bufferedImage), 0, 0, this.getWidth(), this.getHeight(), null);
	        
	}
	public void addButton(JButton button){
		fighterButtons.add(button);
	}
	
	public Image fitImageToACertainSize(int width, int height, Image oldImage)
	{
		BufferedImage newImage=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics=newImage.createGraphics();
		graphics.drawImage(oldImage, 0, 0, width, height, null);
		graphics.dispose();
		return newImage;
	}
	
	public JPanel getFighterPanel() 
	{
		return fighterPanel;
	}
	public JPanel getPlayerPanel() {
		return playerPanel;
	}
	public JPanel getButtons() {
		return fighterButtons;
	}
	public String getName() {
		return playerName.getText();
	}
	public String getFighterName() {
		return fighterNameJT.getText();
	}
	public JButton getEarthling() {
		return earthling;
	}
	public void setEarthling(JButton earthling) {
		this.earthling = earthling;
	}
	public JButton getFrieza() {
		return frieza;
	}
	public void setFrieza(JButton frieza) {
		this.frieza = frieza;
	}
	public JButton getNamekian() {
		return namekian;
	}
	public void setNamekian(JButton namekian) {
		this.namekian = namekian;
	}
	public JButton getMajin() {
		return majin;
	}
	public void setMajin(JButton majin) {
		this.majin = majin;
	}
	public JButton getSaiyan() {
		return saiyan;
	}
	public void setSaiyan(JButton saiyan) {
		this.saiyan = saiyan;
	}
	public JTextField getFighterNameJT() {
		return fighterNameJT;
	}

	public void setFighterNameJT(JTextField fighterNameJT) {
		this.fighterNameJT = fighterNameJT;
	}

}

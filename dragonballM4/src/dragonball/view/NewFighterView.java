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

public class NewFighterView extends JPanel implements Serializable{
	
	private JPanel fighterPanel;
	private JTextField fighterNameField;
	private JLabel fighterNameTitle;
	private JPanel fighterButtons;
	private JButton fighterNameSubmit;
	private transient BufferedImage bufferedImage;
	private JButton newEarthling;
	private JButton newFrieza;
	private JButton newNamekian;
	private JButton newMajin;
	private JButton newSaiyan;
	
	public NewFighterView ()
	{
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
		
		fighterPanel= new JPanel();
		fighterPanel.setLayout(new FlowLayout());
		fighterPanel.setOpaque(false);
		fighterPanel.setVisible(true);
		this.add(fighterPanel, BorderLayout.NORTH);
		
		fighterNameTitle= new JLabel("Name of new fighter: ");
		fighterNameTitle.setOpaque(false);
		fighterNameTitle.setForeground(Color.ORANGE);
		fighterNameTitle.setFont(new Font("Calibri", Font.PLAIN, 20));
		fighterPanel.add(fighterNameTitle);
		
		fighterNameField=new JTextField();
		fighterNameField.setPreferredSize(new Dimension(350,50));
		fighterNameField.setFont(new Font("Calibri", Font.PLAIN, 20));
		fighterNameField.setEditable(true);
		fighterPanel.add(fighterNameField);
		
		fighterNameSubmit = new JButton("submit");
		fighterNameSubmit.setBackground(Color.BLACK);
		fighterNameSubmit.setOpaque(true);
		fighterNameSubmit.setBorderPainted(false);
		fighterNameSubmit.setFont(new Font("Calibri", Font.PLAIN, 15));
		fighterNameSubmit.setForeground(Color.ORANGE);
		fighterNameSubmit.setFocusPainted(false);	
		fighterPanel.add(fighterNameSubmit);
		
		fighterButtons= new JPanel();
		fighterButtons.setLayout(new FlowLayout());
		fighterButtons.setOpaque(false);
		fighterButtons.setVisible(false);
		this.add(fighterButtons, BorderLayout.CENTER);
		
		BufferedImage bufferedImageFighter=null;
		
		try
		{
			bufferedImageFighter= ImageIO.read(new File("earthling.png"));
			newEarthling=new JButton(new ImageIcon(fitImageToACertainSize(150,300,bufferedImageFighter)));
			newEarthling.setFocusPainted(false);
			newEarthling.setContentAreaFilled(false);
			//earthling.setBorderPainted(false);
			newEarthling.setBorder(BorderFactory.createTitledBorder("Earthling"));
			fighterButtons.add(newEarthling);
		
			bufferedImageFighter= ImageIO.read(new File("Frieza_(2).png"));
			newFrieza=new JButton(new ImageIcon(fitImageToACertainSize(150,300,bufferedImageFighter)));
			newFrieza.setFocusPainted(false);
			newFrieza.setContentAreaFilled(false);
			//frieza.setBorderPainted(false);
			newFrieza.setBorder(BorderFactory.createTitledBorder("Frieza"));
			fighterButtons.add(newFrieza);
		
			bufferedImageFighter= ImageIO.read(new File("Majin_buu.png"));
			newMajin=new JButton(new ImageIcon(fitImageToACertainSize(150,300,bufferedImageFighter)));
			newMajin.setFocusPainted(false);
			newMajin.setContentAreaFilled(false);
			//majin.setBorderPainted(false);
			newMajin.setBorder(BorderFactory.createTitledBorder("Majin"));
			fighterButtons.add(newMajin);
	
			bufferedImageFighter= ImageIO.read(new File("namek_warrior.png"));
			newNamekian=new JButton(new ImageIcon(fitImageToACertainSize(150,300,bufferedImageFighter)));
			newNamekian.setFocusPainted(false);
			newNamekian.setContentAreaFilled(false);
			//namekian.setBorderPainted(false);
			newNamekian.setBorder(BorderFactory.createTitledBorder("Namekian"));	
			fighterButtons.add(newNamekian);
		
			bufferedImageFighter= ImageIO.read(new File("Gokus - Copy.png"));
			newSaiyan=new JButton(new ImageIcon(fitImageToACertainSize(150,300,bufferedImageFighter)));
			newSaiyan.setFocusPainted(false);
			newSaiyan.setContentAreaFilled(false);
			//saiyan.setBorderPainted(false);
			newSaiyan.setBorder(BorderFactory.createTitledBorder("Saiyan"));	
			fighterButtons.add(newSaiyan);	

		} 
		catch(IOException e)
		{
			e.printStackTrace();
		}				
		
		this.repaint();
		this.revalidate();	
		this.setVisible(true);
	}
	
	public Image fitImageToACertainSize(int width, int height, Image oldImage)
	{
		BufferedImage newImage=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics=newImage.createGraphics();
		graphics.drawImage(oldImage, 0, 0, width, height, null);
		graphics.dispose();
		return newImage;
	}
	
	public void paintComponent(Graphics g) 
	{	
		super.paintComponent(g);
	    g.drawImage((bufferedImage), 0, 0, this.getWidth(), this.getHeight(), null);
	        
	}
	
	public JPanel getFighterPanel() {
		return fighterPanel;
	}
	public void setFighterPanel(JPanel fighterPanel) {
		this.fighterPanel = fighterPanel;
	}
	public JTextField getFighterNameField() {
		return fighterNameField;
	}
	public void setFighterNameField(JTextField fighterNameField) {
		this.fighterNameField = fighterNameField;
	}
	public JLabel getFighterNameTitle() {
		return fighterNameTitle;
	}
	public void setFighterNameTitle(JLabel fighterNameTitle) {
		this.fighterNameTitle = fighterNameTitle;
	}
	public JPanel getFighterButtons() {
		return fighterButtons;
	}
	public void setFighterButtons(JPanel fighterButtons) {
		this.fighterButtons = fighterButtons;
	}
	public JButton getFighterNameSubmit() {
		return fighterNameSubmit;
	}
	public void setFighterNameSubmit(JButton fighterNameSubmit) {
		this.fighterNameSubmit = fighterNameSubmit;
	}
	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}
	public void setBufferedImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}
	public JButton getNewEarthling() {
		return newEarthling;
	}
	public void setNewEarthling(JButton newEarthling) {
		this.newEarthling = newEarthling;
	}
	public JButton getNewFrieza() {
		return newFrieza;
	}
	public void setNewFrieza(JButton newFrieza) {
		this.newFrieza = newFrieza;
	}
	public JButton getNewNamekian() {
		return newNamekian;
	}
	public void setNewNamekian(JButton newNamekian) {
		this.newNamekian = newNamekian;
	}
	public JButton getNewMajin() {
		return newMajin;
	}
	public void setNewMajin(JButton newMajin) {
		this.newMajin = newMajin;
	}
	public JButton getNewSaiyan() {
		return newSaiyan;
	}
	public void setNewSaiyan(JButton newSaiyan) {
		this.newSaiyan = newSaiyan;
	}

	public static void main (String []args)
	{
		JFrame jFrame= new JFrame();
		jFrame.setSize(1000,500);
		NewFighterView view1= new NewFighterView();
		jFrame.setContentPane(view1);
		jFrame.setVisible(true);
		view1.setVisible(true);
		view1.getFighterButtons().setVisible(true);
		

	}

}

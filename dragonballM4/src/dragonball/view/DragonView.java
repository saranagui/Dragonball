package dragonball.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import dragonball.model.attack.SuperAttack;
import dragonball.model.attack.UltimateAttack;
import dragonball.model.dragon.Dragon;
import dragonball.model.player.Player;
import dragonball.model.world.World;

public class DragonView extends JPanel implements Serializable {
	
	private JPanel wishPanel;
	private Dragon dragon;
	private transient BufferedImage bufferedImage;
	private JButton chooseAWish;
	//private JComboBox availableWishes;
	private JPanel southPanel;
	private JButton senzuBeans;
	private JButton abilityPoints;
	private JButton superAttack;
	private JButton ultimateAttack;
	private JPanel commentPanel;
	private JTextArea comment;
	
	public DragonView(Dragon dragon){
		
		this.dragon=dragon;
		
		Dimension t=Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize((int)(t.getWidth()),(int) (t.getHeight()));
		this.setOpaque(false);
		this.setLayout(new BorderLayout());
		
		try 
		{
			bufferedImage = ImageIO.read(new File("dragon2.jpg"));
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		southPanel=new JPanel();
		southPanel.setLayout(new BorderLayout());
		southPanel.setOpaque(false);
		//southPanel.setPreferredSize(new Dimension(200,100));
		//southPanel.setSize(southPanel.getPreferredSize());
		
		wishPanel= new JPanel();
		wishPanel.setLayout(new BoxLayout(wishPanel,BoxLayout.LINE_AXIS));
		wishPanel.setOpaque(false);
	//	wishPanel.add(Box.createHorizontalStrut(800));
		
		chooseAWish=new JButton("choose a wish");
		chooseAWish.setOpaque(false);
		chooseAWish.setBorderPainted(true);
		chooseAWish.setContentAreaFilled(false);
		chooseAWish.setFont(new Font("Calibri", Font.PLAIN, 40));
		chooseAWish.setForeground(Color.WHITE);
		chooseAWish.setFocusPainted(false);	
		wishPanel.add(chooseAWish);
		chooseAWish.setVisible(true);
		
		/*String[] wishesToBeChoseFrom={" ","Sezu Beans","Dragon Balls","SuperAttack","UltimateAttack"};
		availableWishes=new JComboBox<String>(wishesToBeChoseFrom);
		availableWishes.setEditable(false);
		availableWishes.setSelectedIndex(0);
		availableWishes.setPreferredSize(new Dimension(200,30));
		availableWishes.setMaximumSize(availableWishes.getPreferredSize());
		availableWishes.setFont(new Font("Arial", Font.BOLD, 20));
		availableWishes.setMaximumRowCount(5);
		availableWishes.setOpaque(false);
		wishPanel.add(availableWishes);*/
		//availableWishes.setVisible(false);
		
		senzuBeans=new JButton("Senzu Beans");
		senzuBeans.setOpaque(false);
		senzuBeans.setBorderPainted(true);
		senzuBeans.setContentAreaFilled(false);
		senzuBeans.setFont(new Font("Calibri", Font.PLAIN, 40));
		senzuBeans.setForeground(Color.WHITE);
		senzuBeans.setFocusPainted(false);	
		wishPanel.add(senzuBeans);
		senzuBeans.setVisible(false);
		
		abilityPoints=new JButton("Ability Points");
		abilityPoints.setOpaque(false);
		abilityPoints.setBorderPainted(true);
		abilityPoints.setContentAreaFilled(false);
		abilityPoints.setFont(new Font("Calibri", Font.PLAIN, 40));
		abilityPoints.setForeground(Color.WHITE);
		abilityPoints.setFocusPainted(false);	
		wishPanel.add(abilityPoints);
		abilityPoints.setVisible(false);
		
		superAttack=new JButton("SuperAttack");
		superAttack.setOpaque(false);
		superAttack.setBorderPainted(true);
		superAttack.setContentAreaFilled(false);
		superAttack.setFont(new Font("Calibri", Font.PLAIN, 40));
		superAttack.setForeground(Color.WHITE);
		superAttack.setFocusPainted(false);	
		wishPanel.add(superAttack);
		superAttack.setVisible(false);
		
		ultimateAttack=new JButton("UltimateAttack");
		ultimateAttack.setOpaque(false);
		ultimateAttack.setBorderPainted(true);
		ultimateAttack.setContentAreaFilled(false);
		ultimateAttack.setFont(new Font("Calibri", Font.PLAIN, 40));
		ultimateAttack.setForeground(Color.WHITE);
		ultimateAttack.setFocusPainted(false);	
		wishPanel.add(ultimateAttack);
		ultimateAttack.setVisible(false);
		southPanel.add(wishPanel,BorderLayout.CENTER);
		
		commentPanel=new JPanel();
		commentPanel.setOpaque(false);
		commentPanel.setPreferredSize(new Dimension(100, 100));
		commentPanel.setVisible(true);
		comment=new JTextArea();
		comment.setOpaque(true);
		comment.setText("");
		comment.setFont(new Font("Calibri", Font.PLAIN, 30));
		comment.setEditable(false);
		comment.setVisible(false);
		commentPanel.add(comment);
		southPanel.add(commentPanel,BorderLayout.SOUTH);
		
		this.add(southPanel,BorderLayout.SOUTH);
		//setBounds
		//wishPanel.setLocation((int)t.getWidth()/2, 2*(int)t.getHeight()/3);
		
		this.repaint();
		this.revalidate();
		this.setVisible(true);
	}
	
	public JPanel getWishPanel() {
		return wishPanel;
	}

	public void setWishPanel(JPanel wishPanel) {
		this.wishPanel = wishPanel;
	}

	public Dragon getDragon() {
		return dragon;
	}

	public void setDragon(Dragon dragon) {
		this.dragon = dragon;
	}

	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	public void setBufferedImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}

	public JButton getChooseAWish() {
		return chooseAWish;
	}

	public void setChooseAWish(JButton chooseAWish) {
		this.chooseAWish = chooseAWish;
	}

	public JButton getSenzuBeans() {
		return senzuBeans;
	}

	public void setSenzuBeans(JButton senzuBeans) {
		this.senzuBeans = senzuBeans;
	}

	public JButton getAbilityPoints() {
		return abilityPoints;
	}

	public void setAbilityPoints(JButton abilityPoints) {
		this.abilityPoints = abilityPoints;
	}

	public JButton getSuperAttack() {
		return superAttack;
	}

	public void setSuperAttack(JButton superAttack) {
		this.superAttack = superAttack;
	}

	public JButton getUltimateAttack() {
		return ultimateAttack;
	}

	public void setUltimateAttack(JButton ultimateAttack) {
		this.ultimateAttack = ultimateAttack;
	}

	public JPanel getSouthPanel() {
		return southPanel;
	}

	public void setSouthPanel(JPanel southPanel) {
		this.southPanel = southPanel;
	}

	public JPanel getCommentPanel() {
		return commentPanel;
	}

	public void setCommentPanel(JPanel commentPanel) {
		this.commentPanel = commentPanel;
	}

	public JTextArea getComment() {
		return comment;
	}

	public void setComment(JTextArea comment) {
		this.comment = comment;
	}

	//using get wishes method in Dragon
	public void addWish(JButton wish){
		wishPanel.add(wish);
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
	    g.drawImage((bufferedImage), 0, 0, this.getWidth(), this.getHeight(), null);
	        
	}
	public static void main(String[]args){
		GameView test=new GameView();
		ArrayList<SuperAttack> superAttacks=new ArrayList<SuperAttack>();
		ArrayList<UltimateAttack> ultimateAttacks=new ArrayList<UltimateAttack>();
		Dragon myDragon=new Dragon("Jack the Dragon",superAttacks,ultimateAttacks,4,4);
		DragonView testDragon=new DragonView(myDragon);
		test.setContentPane(testDragon);
	}
}


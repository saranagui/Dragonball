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

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dragonball.model.game.Game;

public class UpgradeFighterView extends JPanel implements Serializable{
	
	
	private JPanel upgradeButtons;
	private JPanel info;
	private JLabel maxHealthPoints;
	private JLabel physicalDamage;
	private JLabel blastDamage;
	private JLabel maxKi;
	private JLabel maxStamina;
	private JLabel abilityPoints;
	private JLabel upgradeQuestion;
	private JButton upgradeHealthPoints;
	private JButton upgradePhysicalDamage;
	private JButton upgradeBlastDamage;
	private JButton upgradeMaxKi;
	private JButton upgradeMaxStamina;
	private JButton endUpgrade;
	private transient BufferedImage bufferedImage;

	
	public UpgradeFighterView()
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
		
		info= new JPanel();
		info.setLayout(new BoxLayout(info,BoxLayout.Y_AXIS));
		info.setOpaque(false);
		info.setVisible(true);
		this.add(info, BorderLayout.NORTH);
		
		maxHealthPoints= new JLabel("Your maximum Health Points: ");
		maxHealthPoints.setOpaque(false);
		maxHealthPoints.setForeground(Color.BLUE);
		maxHealthPoints.setFont(new Font("Calibri", Font.PLAIN, 40));
		info.add(maxHealthPoints);
		
		physicalDamage= new JLabel("Your Physical Damage: ");
		physicalDamage.setOpaque(false);
		physicalDamage.setForeground(Color.BLUE);
		physicalDamage.setFont(new Font("Calibri", Font.PLAIN, 40));
		info.add(physicalDamage);
		
		blastDamage= new JLabel("Your Blast Damage: ");
		blastDamage.setOpaque(false);
		blastDamage.setForeground(Color.BLUE);
		blastDamage.setFont(new Font("Calibri", Font.PLAIN, 40));
		info.add(blastDamage);
	
		
		maxKi= new JLabel("Your maximum Ki Bars: ");
		maxKi.setOpaque(false);
		maxKi.setForeground(Color.BLUE);
		maxKi.setFont(new Font("Calibri", Font.PLAIN, 40));
		info.add(maxKi);
		
		maxStamina= new JLabel("Your maximum Stamina: ");
		maxStamina.setOpaque(false);
		maxStamina.setForeground(Color.BLUE);
		maxStamina.setFont(new Font("Calibri", Font.PLAIN, 40));
		info.add(maxStamina);
		
		abilityPoints= new JLabel("Your available ability points: ");
		abilityPoints.setOpaque(false);
		abilityPoints.setForeground(Color.BLUE);
		abilityPoints.setFont(new Font("Calibri", Font.PLAIN, 40));
		info.add(abilityPoints);
		info.add(Box.createVerticalGlue());
		info.add(Box.createVerticalGlue());
		
		upgradeButtons= new JPanel();
		upgradeButtons.setLayout(new FlowLayout());
		upgradeButtons.setOpaque(false);
		upgradeButtons.setVisible(true);
		this.add(upgradeButtons, BorderLayout.CENTER);
		
		upgradeQuestion= new JLabel("Wish to upgrade?");
		upgradeQuestion.setOpaque(false);
		upgradeQuestion.setForeground(Color.BLUE);
		upgradeQuestion.setFont(new Font("Calibri", Font.PLAIN, 20));
		upgradeButtons.add(upgradeQuestion);
		
		upgradeHealthPoints = new JButton("Health");
		upgradeHealthPoints.setBackground(Color.BLACK);
		upgradeHealthPoints.setOpaque(true);
		upgradeHealthPoints.setBorderPainted(false);
		upgradeHealthPoints.setFont(new Font("Calibri", Font.PLAIN, 30));
		upgradeHealthPoints.setForeground(Color.ORANGE);
		upgradeHealthPoints.setFocusPainted(false);	
		upgradeButtons.add(upgradeHealthPoints);
		
		upgradePhysicalDamage = new JButton("Physical Damage");
		upgradePhysicalDamage.setBackground(Color.BLACK);
		upgradePhysicalDamage.setOpaque(true);
		upgradePhysicalDamage.setBorderPainted(false);
		upgradePhysicalDamage.setFont(new Font("Calibri", Font.PLAIN, 30));
		upgradePhysicalDamage.setForeground(Color.ORANGE);
		upgradePhysicalDamage.setFocusPainted(false);	
		upgradeButtons.add(upgradePhysicalDamage);
		
		upgradeBlastDamage = new JButton("Blast Damage");
		upgradeBlastDamage.setBackground(Color.BLACK);
		upgradeBlastDamage.setOpaque(true);
		upgradeBlastDamage.setBorderPainted(false);
		upgradeBlastDamage.setFont(new Font("Calibri", Font.PLAIN, 30));
		upgradeBlastDamage.setForeground(Color.ORANGE);
		upgradeBlastDamage.setFocusPainted(false);	
		upgradeButtons.add(upgradeBlastDamage);
		
		upgradeMaxKi = new JButton("Ki");
		upgradeMaxKi.setBackground(Color.BLACK);
		upgradeMaxKi.setOpaque(true);
		upgradeMaxKi.setBorderPainted(false);
		upgradeMaxKi.setFont(new Font("Calibri", Font.PLAIN, 30));
		upgradeMaxKi.setForeground(Color.ORANGE);
		upgradeMaxKi.setFocusPainted(false);	
		upgradeButtons.add(upgradeMaxKi);
		
		upgradeMaxStamina = new JButton("Stamina");
		upgradeMaxStamina.setBackground(Color.BLACK);
		upgradeMaxStamina.setOpaque(true);
		upgradeMaxStamina.setBorderPainted(false);
		upgradeMaxStamina.setFont(new Font("Calibri", Font.PLAIN, 30));
		upgradeMaxStamina.setForeground(Color.ORANGE);
		upgradeMaxStamina.setFocusPainted(false);	
		upgradeButtons.add(upgradeMaxStamina);
		
		endUpgrade= new JButton("End Upgrade Process");
		endUpgrade.setBackground(Color.BLACK);
		endUpgrade.setOpaque(true);
		endUpgrade.setBorderPainted(false);
		endUpgrade.setFont(new Font("Calibri", Font.PLAIN, 50));
		endUpgrade.setForeground(Color.ORANGE);
		endUpgrade.setFocusPainted(false);	
		this.add(endUpgrade, BorderLayout.SOUTH);
		
		this.repaint();
		this.revalidate();	
		this.setVisible(true);
		
	}
	
	public void paintComponent(Graphics g) 
	{	
		super.paintComponent(g);
	    g.drawImage((bufferedImage), 0, 0, this.getWidth(), this.getHeight(), null);
	        
	}

	public JPanel getUpgradeButtons() {
		return upgradeButtons;
	}

	public void setUpgradeButtons(JPanel upgradeButtons) {
		this.upgradeButtons = upgradeButtons;
	}

	public JPanel getInfo() {
		return info;
	}

	public void setInfo(JPanel info) {
		this.info = info;
	}

	public JLabel getMaxHealthPoints() {
		return maxHealthPoints;
	}

	public void setMaxHealthPoints(JLabel maxHealthPoints) {
		this.maxHealthPoints = maxHealthPoints;
	}

	public JLabel getPhysicalDamage() {
		return physicalDamage;
	}

	public void setPhysicalDamage(JLabel physicalDamage) {
		this.physicalDamage = physicalDamage;
	}

	public JLabel getBlastDamage() {
		return blastDamage;
	}

	public void setBlastDamage(JLabel blastDamage) {
		this.blastDamage = blastDamage;
	}

	public JLabel getMaxKi() {
		return maxKi;
	}

	public void setMaxKi(JLabel maxKi) {
		this.maxKi = maxKi;
	}

	public JLabel getMaxStamina() {
		return maxStamina;
	}

	public void setMaxStamina(JLabel maxStamina) {
		this.maxStamina = maxStamina;
	}

	public JLabel getAbilityPoints() {
		return abilityPoints;
	}

	public void setAbilityPoints(JLabel abilityPoints) {
		this.abilityPoints = abilityPoints;
	}

	public JLabel getUpgradeQuestion() {
		return upgradeQuestion;
	}

	public void setUpgradeQuestion(JLabel upgradeQuestion) {
		this.upgradeQuestion = upgradeQuestion;
	}

	public JButton getUpgradeHealthPoints() {
		return upgradeHealthPoints;
	}

	public void setUpgradeHealthPoints(JButton upgradeHealthPoints) {
		this.upgradeHealthPoints = upgradeHealthPoints;
	}

	public JButton getUpgradePhysicalDamage() {
		return upgradePhysicalDamage;
	}

	public void setUpgradePhysicalDamage(JButton upgradePhysicalDamage) {
		this.upgradePhysicalDamage = upgradePhysicalDamage;
	}

	public JButton getUpgradeBlastDamage() {
		return upgradeBlastDamage;
	}

	public void setUpgradeBlastDamage(JButton upgradeBlastDamage) {
		this.upgradeBlastDamage = upgradeBlastDamage;
	}

	public JButton getUpgradeMaxKi() {
		return upgradeMaxKi;
	}

	public void setUpgradeMaxKi(JButton upgradeMaxKi) {
		this.upgradeMaxKi = upgradeMaxKi;
	}

	public JButton getUpgradeMaxStamina() {
		return upgradeMaxStamina;
	}

	public void setUpgradeMaxStamina(JButton upgradeMaxStamina) {
		this.upgradeMaxStamina = upgradeMaxStamina;
	}

	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	public void setBufferedImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}
	public JButton getEndUpgrade() {
		return endUpgrade;
	}

	public void setEndUpgrade(JButton endUpgrade) {
		this.endUpgrade = endUpgrade;
	}
	
	public static void main (String []args)
	{
		JFrame jFrame= new JFrame();
		jFrame.setSize(1000,500);
		UpgradeFighterView view1= new UpgradeFighterView();
		jFrame.setContentPane(view1);
		jFrame.setVisible(true);
		view1.setVisible(true);
		
	}

	
}

package dragonball.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;

import dragonball.model.cell.Cell;
import dragonball.model.cell.Collectible;
import dragonball.model.cell.CollectibleCell;
import dragonball.model.cell.FoeCell;
import dragonball.model.character.fighter.Earthling;
import dragonball.model.character.fighter.Frieza;
import dragonball.model.character.fighter.Majin;
import dragonball.model.character.fighter.Namekian;
import dragonball.model.character.fighter.Saiyan;
import dragonball.model.player.Player;
import dragonball.model.world.World;

public class WorldView extends JPanel implements Serializable {
	
	private JPanel playerInfoPanel;
	private JPanel mapPanel;
	private JPanel commentPanel;
	private JTextArea comment;
	private JTextArea playerName;
	private Player player;
	private World world;
	private transient BufferedImage bufferedImage;
	private JButton[][] worldMap;
	private JPanel playerCollectibles;
	private JPanel senzuBeans;
	private JPanel dragonballs;
	private JLabel playerImage;
	private JPanel buttons;
	private JButton save;
	private JPanel playerInfoPanelWest;
	private JButton createNewFighter;
	private JButton upgradeFighter;
	private JButton changeFighter;
	private JButton AssignAttacks;
	private JPopupMenu popup;
	private JComboBox<String> chooseAFighter;
	private JButton assignSA;
	private JButton assignUA;
	private JComboBox<String> playerSA;
	private JComboBox<String> playerUA;
	private JComboBox<String> fighterSA;
	private JComboBox<String> fighterUA;
	private JButton submitAdd;
	private JButton submitReplace;
	private boolean assignSuper;
	private JButton add;
	private JButton replace;
	private JButton submitChoose;
	

	
	

	public WorldView(Player player, World world){
		this.player=player;
		this.world=world;
		this.popup = new JPopupMenu();
		
		Dimension t=Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize((int)(t.getWidth()),(int) (t.getHeight()));
		this.setOpaque(false);
		this.setLayout(new BorderLayout());
		
		try 
		{
			bufferedImage = ImageIO.read(new File("worldView.jpg"));
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		playerInfoPanel=new JPanel();
		mapPanel=new JPanel();
		commentPanel=new JPanel();
		
		
		Cell[][] actualMap= this.world.getMap();
		
		mapPanel.setLayout(new GridLayout(10,10));
		mapPanel.setOpaque(false);
		this.add(mapPanel,BorderLayout.CENTER);
		worldMap=new JButton[10][10];
		
		BufferedImage bufferedImageFoe;
		try {
			bufferedImageFoe = ImageIO.read(new File("strongFoe.png"));
			JButton cell=new JButton(new ImageIcon(fitImageToACertainSize(100,70,bufferedImageFoe)));
			cell.setFocusPainted(false);
			cell.setOpaque(false);
			cell.setContentAreaFilled(false);
			this.addCell(cell);
			worldMap[0][0]=cell;

			for(int i=0; i<10; i++)
			{
				for (int j=0; j<10; j++)
				{
					if(!((i==0 && j==0)))
					{
						cell = new JButton();
						if(actualMap[i][j] instanceof FoeCell)
						{
							bufferedImageFoe=ImageIO.read(new File("strongFoe.png"));
							cell=new JButton(new ImageIcon(fitImageToACertainSize(100,70,bufferedImageFoe)));
							//cell.setVisible(false);
						}
						else
						{
							if(actualMap[i][j] instanceof CollectibleCell)
							{
								if(((CollectibleCell)(actualMap[i][j])).getCollectible()==Collectible.DRAGON_BALL)
								{
									bufferedImageFoe=ImageIO.read(new File("wish.jpeg"));
									cell=new JButton(new ImageIcon(fitImageToACertainSize(100,70,bufferedImageFoe)));
									//cell.setVisible(false);
								}	
								else
								{
									bufferedImageFoe=ImageIO.read(new File("Senzu_Bean.png"));
									cell=new JButton(new ImageIcon(fitImageToACertainSize(100,70,bufferedImageFoe)));
									//cell.setVisible(false);
								}	
							}	
						}
					
						cell.setOpaque(false);
						cell.setFocusPainted(false);
						cell.setContentAreaFilled(false);
						this.addCell(cell);
						worldMap[i][j]=cell;
					}	
				}
			}
			if(player.getActiveFighter() instanceof Earthling)
				bufferedImageFoe = ImageIO.read(new File("earthling.png"));	
			else if(player.getActiveFighter() instanceof Frieza)
				bufferedImageFoe = ImageIO.read(new File("Frieza_(2).png"));
				else if(player.getActiveFighter() instanceof Majin)
					bufferedImageFoe = ImageIO.read(new File("Majin_buu.png"));
					else if(player.getActiveFighter() instanceof Namekian)
						bufferedImageFoe = ImageIO.read(new File("namek_warrior.png"));
						else
							bufferedImageFoe = ImageIO.read(new File("Gokus - Copy.png"));
			playerImage=new JLabel(new ImageIcon(fitImageToACertainSize(100,70,bufferedImageFoe)));
			
			this.paintPlayer();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		
		//playerInfoPanel.setBackground(Color.GRAY);
		playerInfoPanel.setOpaque(false);
		playerInfoPanel.setLayout(new BorderLayout());
		playerInfoPanel.setName("Player");
		
		playerInfoPanelWest=new JPanel();
		playerInfoPanelWest.setLayout(new BoxLayout(playerInfoPanelWest,BoxLayout.Y_AXIS));
		playerInfoPanelWest.setOpaque(false);
		
		playerName=new JTextArea();
		playerName.setVisible(true);
		playerName.setOpaque(false);
		playerName.setEditable(false);
		playerName.setText(player.getName()+" playing with: "+player.getActiveFighter().getName()+"\nLevel: "+player.getActiveFighter().getLevel());
		playerName.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 40));
		playerName.setForeground(Color.BLACK);
		playerInfoPanelWest.add(playerName);
				
		playerCollectibles=new JPanel();
		playerCollectibles.setLayout(new BoxLayout(playerCollectibles,BoxLayout.PAGE_AXIS));
		playerCollectibles.setOpaque(false);
		
		JLabel senzuBeansText=new JLabel("Senzu Beans: ");
		senzuBeansText.setOpaque(false);
		senzuBeansText.setForeground(Color.DARK_GRAY);
		senzuBeansText.setFont(new Font("Calibri", Font.PLAIN, 30));
		senzuBeans=new JPanel();
		senzuBeans.setLayout(new FlowLayout());
		senzuBeans.setOpaque(false);
		senzuBeans.setPreferredSize(new Dimension(100,100));
		//senzuBeans.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		senzuBeans.add(senzuBeansText);
		BufferedImage image;
		try {
			for(int i=0;i<this.player.getSenzuBeans();i++)
			{
				image = ImageIO.read(new File("Senzu_Bean.png"));
				ImageIcon icon = new ImageIcon((Image)image);
				JLabel label = new JLabel();
				label.setPreferredSize(new Dimension(50,10));
				label.setIcon(icon);
				senzuBeans.add(label);
			}
		}catch(IOException e)
		{
			
		}
		playerCollectibles.add(senzuBeans);
		
		JLabel dragonballsText=new JLabel("Dragon Balls: ");
		dragonballsText.setOpaque(false);
		dragonballsText.setForeground(Color.DARK_GRAY);
		dragonballsText.setFont(new Font("Calibri", Font.PLAIN, 30));
		dragonballs=new JPanel();
		dragonballs.setLayout(new FlowLayout());
		dragonballs.setOpaque(false);
		dragonballs.setPreferredSize(new Dimension(100,100));
		//dragonballs.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		dragonballs.add(dragonballsText);
		try {
			for(int i=0;i<this.player.getDragonBalls();i++)
			{
				image = ImageIO.read(new File("wish.jpeg"));
				ImageIcon icon = new ImageIcon((Image)image);
				JLabel label = new JLabel();
				label.setPreferredSize(new Dimension(50,10));
				label.setIcon(icon);
				dragonballs.add(label);
			}
		}catch(IOException e)
		{
			
		}
		playerCollectibles.add(dragonballs);
		playerInfoPanel.add(playerCollectibles,BorderLayout.CENTER);
		
		buttons=new JPanel();
		buttons.setLayout(new FlowLayout());
		buttons.setOpaque(false);
		playerInfoPanelWest.add(buttons);
		
		playerInfoPanel.add(playerInfoPanelWest,BorderLayout.WEST);

		
		String[] playerFighters= new String[(player.getFighters().size())+1];
		playerFighters[0]= "choose a fighter";
		for (int i=1; i<playerFighters.length; i++)
			playerFighters[i]=player.getFighters().get(i-1).getName();
	
		chooseAFighter = new JComboBox<String>(playerFighters);
		chooseAFighter.setEditable(false);
		chooseAFighter.setSelectedIndex(0);
		chooseAFighter.setPreferredSize(new Dimension(200,50));
		chooseAFighter.setMaximumSize(new Dimension(800,200));
		chooseAFighter.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 15));
		chooseAFighter.setMaximumRowCount(5);
		chooseAFighter.setOpaque(false);
		chooseAFighter.setVisible(false);
		buttons.add(chooseAFighter);
		
		submitChoose=new JButton();
		submitChoose.setText("submit");
		submitChoose.setOpaque(false);
		submitChoose.setContentAreaFilled(false);
		submitChoose.setBorderPainted(true);
		submitChoose.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 20));
		submitChoose.setForeground(Color.BLACK);
		submitChoose.setFocusPainted(false);	
		submitChoose.setVisible(false);
		buttons.add(submitChoose);
		
		assignSA=new JButton();
		assignSA.setText("Super Attack");
		assignSA.setOpaque(false);
		assignSA.setContentAreaFilled(false);
		assignSA.setBorderPainted(true);
		assignSA.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 30));
		assignSA.setForeground(Color.BLACK);
		assignSA.setFocusPainted(false);	
		assignSA.setVisible(false);
		buttons.add(assignSA);
		
		assignUA=new JButton();
		assignUA.setText("Ultimate Attack");
		assignUA.setOpaque(false);
		assignUA.setContentAreaFilled(false);
		assignUA.setBorderPainted(true);
		assignUA.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 30));
		assignUA.setForeground(Color.BLACK);
		assignUA.setFocusPainted(false);
		assignUA.setVisible(false);
		buttons.add(assignUA);
		
		String[] playerSA= new String[(player.getSuperAttacks().size())+1];
		playerSA[0]= "pick a super attack";
		for (int i=1; i<playerSA.length; i++)
			playerSA[i]=player.getSuperAttacks().get(i-1).getName();
	
		this.playerSA = new JComboBox<String>(playerSA);
		this.playerSA.setEditable(false);
		this.playerSA.setSelectedIndex(0);
		this.playerSA.setPreferredSize(new Dimension(200,50));
		this.playerSA.setMaximumSize(new Dimension(800,200));
		this.playerSA.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 15));
		this.playerSA.setMaximumRowCount(7);
		this.playerSA.setOpaque(false);
		this.playerSA.setVisible(false);
		buttons.add(this.playerSA);
		
		String[] playerUA= new String[(player.getUltimateAttacks().size())+1];
		playerUA[0]= "pick an ultimate attack";
		for (int i=1; i<playerUA.length; i++)
		{
			playerUA[i]=player.getUltimateAttacks().get(i-1).getName();
		}	
		this.playerUA = new JComboBox<String>(playerUA);
		this.playerUA.setEditable(false);
		this.playerUA.setSelectedIndex(0);
		this.playerUA.setPreferredSize(new Dimension(200,50));
		this.playerUA.setMaximumSize(new Dimension(800,200));
		this.playerUA.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 15));
		this.playerUA.setMaximumRowCount(7);
		this.playerUA.setOpaque(false);
		this.playerUA.setVisible(false);
		buttons.add(this.playerUA);
		
		add=new JButton();
		add.setText("Add");
		add.setOpaque(false);
		add.setContentAreaFilled(false);
		add.setBorderPainted(true);
		add.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 30));
		add.setForeground(Color.BLACK);
		add.setFocusPainted(false);	
		add.setVisible(false);
		buttons.add(add);
		
		submitAdd=new JButton();
		submitAdd.setText("submit");
		submitAdd.setOpaque(false);
		submitAdd.setContentAreaFilled(false);
		submitAdd.setBorderPainted(true);
		submitAdd.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 20));
		submitAdd.setForeground(Color.BLACK);
		submitAdd.setFocusPainted(false);	
		submitAdd.setVisible(false);
		buttons.add(submitAdd);
		
		replace=new JButton();
		replace.setText("Replace");
		replace.setOpaque(false);
		replace.setContentAreaFilled(false);
		replace.setBorderPainted(true);
		replace.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 30));
		replace.setForeground(Color.BLACK);
		replace.setFocusPainted(false);	
		replace.setVisible(false);
		buttons.add(replace);
	
		submitReplace=new JButton();
		submitReplace.setText("submit");
		submitReplace.setOpaque(false);
		submitReplace.setContentAreaFilled(false);
		submitReplace.setBorderPainted(true);
		submitReplace.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 20));
		submitReplace.setForeground(Color.BLACK);
		submitReplace.setFocusPainted(false);	
		submitReplace.setVisible(false);
		buttons.add(submitReplace);
		
		String[] fighterSA= new String[(player.getActiveFighter().getSuperAttacks().size())+1];
		fighterSA[0]= "pick an super attack";
		for (int i=1; i<fighterSA.length; i++)
			fighterSA[i]=player.getActiveFighter().getSuperAttacks().get(i-1).getName();
	
		this.fighterSA = new JComboBox<String>(fighterSA);
		this.fighterSA.setEditable(false);
		this.fighterSA.setSelectedIndex(0);
		this.fighterSA.setPreferredSize(new Dimension(200,50));
		this.fighterSA.setMaximumSize(new Dimension(800,200));
		this.fighterSA.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 15));
		this.fighterSA.setMaximumRowCount(7);
		this.fighterSA.setOpaque(false);
		this.fighterSA.setVisible(false);
		buttons.add(this.fighterSA);
		
		String[] fighterUA= new String[(player.getActiveFighter().getUltimateAttacks().size())+1];
		fighterUA[0]= "pick an ultimate attack";
		for (int i=1; i<fighterUA.length; i++)
			fighterUA[i]=player.getActiveFighter().getUltimateAttacks().get(i-1).getName();
	
		this.fighterUA = new JComboBox<String>(fighterUA);
		this.fighterUA.setEditable(false);
		this.fighterUA.setSelectedIndex(0);
		this.fighterUA.setPreferredSize(new Dimension(200,50));
		this.fighterUA.setMaximumSize(new Dimension(800,200));
		this.fighterUA.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 15));
		this.fighterUA.setMaximumRowCount(7);
		this.fighterUA.setOpaque(false);
		this.fighterUA.setVisible(false);
		buttons.add(this.fighterUA);
		
		save=new JButton();
		save.setText("save");
		//start.setBackground(Color.darkGray);
		save.setOpaque(false);
		save.setContentAreaFilled(false);
		save.setBorderPainted(true);
		save.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 30));
		save.setForeground(Color.BLACK);
		save.setFocusPainted(false);		
		buttons.add(save);
		
		createNewFighter=new JButton();
		createNewFighter.setText("New Fighter");
		//start.setBackground(Color.darkGray);
		createNewFighter.setOpaque(false);
		createNewFighter.setContentAreaFilled(false);
		createNewFighter.setBorderPainted(true);
		createNewFighter.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 30));
		createNewFighter.setForeground(Color.BLACK);
		createNewFighter.setFocusPainted(false);		
		buttons.add(createNewFighter);
		
		upgradeFighter=new JButton();
		upgradeFighter.setText("Upgrade Fighter");
		//start.setBackground(Color.darkGray);
		upgradeFighter.setOpaque(false);
		upgradeFighter.setContentAreaFilled(false);
		upgradeFighter.setBorderPainted(true);
		upgradeFighter.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 30));
		upgradeFighter.setForeground(Color.BLACK);
		upgradeFighter.setFocusPainted(false);		
		buttons.add(upgradeFighter);
		
		changeFighter=new JButton();
		changeFighter.setText("Switch Fighter");
		//start.setBackground(Color.darkGray);
		changeFighter.setOpaque(false);
		changeFighter.setContentAreaFilled(false);
		changeFighter.setBorderPainted(true);
		changeFighter.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 30));
		changeFighter.setForeground(Color.BLACK);
		changeFighter.setFocusPainted(false);		
		buttons.add(changeFighter);
		
		AssignAttacks=new JButton();
		AssignAttacks.setText("Assign Attacks");
		//start.setBackground(Color.darkGray);
		AssignAttacks.setOpaque(false);
		AssignAttacks.setContentAreaFilled(false);
		AssignAttacks.setBorderPainted(true);
		AssignAttacks.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 30));
		AssignAttacks.setForeground(Color.BLACK);
		AssignAttacks.setFocusPainted(false);		
		buttons.add(AssignAttacks);
		
		//playerInfoPanel.add(buttons,BorderLayout.WEST);
		this.add(playerInfoPanel,BorderLayout.NORTH);

		//commentPanel.setLayout(new BorderLayout());
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
		
		this.add(commentPanel,BorderLayout.SOUTH);
		
		
		
		
		this.repaint();
		this.revalidate();
		this.setVisible(true);
	}
	
	public void paintPlayer() {
		worldMap[this.world.getPlayerRow()][this.world.getPlayerColumn()].add(playerImage);
		//worldMap[this.world.getPlayerRow()][this.world.getPlayerColumn()].doClick();
	}

	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
	    g.drawImage((bufferedImage), 0, 0, this.getWidth(), this.getHeight(), null);
	        
	}
	
	public void addCell(JButton cell){
		mapPanel.add(cell);
	}
	public Image fitImageToACertainSize(int width, int height, Image oldImage)
	{
		BufferedImage newImage=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics=newImage.createGraphics();
		graphics.drawImage(oldImage, 0, 0, width, height, null);
		graphics.dispose();
		return newImage;
	}
	public JPanel getPlayerCollectibles() {
		return playerCollectibles;
	}

	public void setPlayerCollectibles(JPanel playerCollectibles) {
		this.playerCollectibles = playerCollectibles;
	}

	public JPanel getSenzuBeans() {
		return senzuBeans;
	}

	public void setSenzuBeans(JPanel senzuBeans) {
		this.senzuBeans = senzuBeans;
	}

	public JPanel getDragonballs() {
		return dragonballs;
	}

	public JButton getSubmitChoose() {
		return submitChoose;
	}

	public void setSubmitChoose(JButton submitChoose) {
		this.submitChoose = submitChoose;
	}

	public void setDragonballs(JPanel dragonballs) {
		this.dragonballs = dragonballs;
	}
	
	public JPanel getPlayerInfoPanel() {
		return playerInfoPanel;
	}
	public void setPlayerInfoPanel(JPanel playerInfoPanel) {
		this.playerInfoPanel = playerInfoPanel;
	}
	public JPanel getMapPanel() {
		return mapPanel;
	}
	public void setMapPanel(JPanel mapPanel) {
		this.mapPanel = mapPanel;
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
	public JTextArea getPlayerName() {
		return playerName;
	}
	public void setPlayerName(JTextArea playerName) {
		this.playerName = playerName;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public World getWorld() {
		return world;
	}
	public void setWorld(World world) {
		this.world = world;
	}
	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}
	public void setBufferedImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}
	public JButton[][] getWorldMap() {
		return worldMap;
	}
	public void setWorldMap(JButton[][] worldMap) {
		this.worldMap = worldMap;
	}
	public JLabel getPlayerImage() {
		return playerImage;
	}

	public void setPlayerImage(JLabel playerImage) {
		this.playerImage = playerImage;
	}

	public JPanel getButtons() {
		return buttons;
	}

	public void setButtons(JPanel buttons) {
		this.buttons = buttons;
	}

	public JButton getSave() {
		return save;
	}

	public void setSave(JButton save) {
		this.save = save;
	}

	public JPanel getPlayerInfoPanelWest() {
		return playerInfoPanelWest;
	}

	public void setPlayerInfoPanelWest(JPanel playerInfoPanelWest) {
		this.playerInfoPanelWest = playerInfoPanelWest;
	}

	public JButton getCreateNewFighter() {
		return createNewFighter;
	}

	public void setCreateNewFighter(JButton createNewFighter) {
		this.createNewFighter = createNewFighter;
	}

	public JButton getUpgradeFighter() {
		return upgradeFighter;
	}

	public void setUpgradeFighter(JButton upgradeFighter) {
		this.upgradeFighter = upgradeFighter;
	}

	public JButton getChangeFighter() {
		return changeFighter;
	}

	public void setChangeFighter(JButton changeFighter) {
		this.changeFighter = changeFighter;
	}

	public JButton getAssignAttacks() {
		return AssignAttacks;
	}

	public void setAssignAttacks(JButton assignAttacks) {
		AssignAttacks = assignAttacks;
	}
//	public void addSwitchFighters(){
//		 	JMenuItem item;
//		 BufferedImage bufferedImageFoe;
//		 	try{
//			 if(player.getActiveFighter() instanceof Earthling)
//				bufferedImageFoe = ImageIO.read(new File("earthling.png"));	
//				else if(player.getActiveFighter() instanceof Frieza)
//					bufferedImageFoe = ImageIO.read(new File("Frieza_(2).png"));
//					else if(player.getActiveFighter() instanceof Majin)
//						bufferedImageFoe = ImageIO.read(new File("Majin_buu.png"));
//						else if(player.getActiveFighter() instanceof Namekian)
//							bufferedImageFoe = ImageIO.read(new File("namek_warrior.png"));
//							else
//								bufferedImageFoe = ImageIO.read(new File("Gokus - Copy.png"));
//			ImageIcon chooseFighter=new ImageIcon(fitImageToACertainSize(30,30,bufferedImageFoe));
//			
//			for(int i=0;i<this.getPlayer().getFighters().size();i++)
//		 	{
//			 	popup.add(item = new JMenuItem(this.getPlayer().getFighters().get(i).getName(), chooseFighter));
//		    	item.setHorizontalTextPosition(JMenuItem.RIGHT);
//		 	}
//		 }catch(IOException e){
//			 
//		 }
		    //item.addActionListener(menuListener);

//		    popup.setLabel("Justification");
//		    popup.setBorder(new BevelBorder(BevelBorder.RAISED));
//		    popup.addPopupMenuListener(new PopupPrintListener());
//
//		    addMouseListener(new MousePopupListener());
	
//	}
	
	public JPopupMenu getPopup() {
		return popup;
	}

	public void setPopup(JPopupMenu popup) {
		this.popup = popup;
	}

	public JComboBox getChooseAFighter() {
		return chooseAFighter;
	}

	public void setChooseAFighter(JComboBox<String> chooseAFighter) {
		this.chooseAFighter = chooseAFighter;
	}

	public JButton getAssignSA() {
		return assignSA;
	}

	public void setAssignSA(JButton assignSA) {
		this.assignSA = assignSA;
	}

	public JButton getAssignUA() {
		return assignUA;
	}

	public void setAssignUA(JButton assignUA) {
		this.assignUA = assignUA;
	}

	public JComboBox<String> getPlayerSA() {
		return playerSA;
	}

	public void setPlayerSA(JComboBox<String> playerSA) {
		this.playerSA = playerSA;
	}

	public JComboBox<String> getPlayerUA() {
		return playerUA;
	}

	public void setPlayerUA(JComboBox<String> playerUA) {
		this.playerUA = playerUA;
	}

	public JComboBox<String> getFighterSA() {
		return fighterSA;
	}

	public void setFighterSA(JComboBox<String> fighterSA) {
		this.fighterSA = fighterSA;
	}

	public JComboBox<String> getFighterUA() {
		return fighterUA;
	}

	public void setFighterUA(JComboBox<String> fighterUA) {
		this.fighterUA = fighterUA;
	}

	
	public boolean isAssignSuper() {
		return assignSuper;
	}

	public void setAssignSuper(boolean assignSuper) {
		this.assignSuper = assignSuper;
	}
	
	public JButton getAdd() {
		return add;
	}

	public void setAdd(JButton add) {
		this.add = add;
	}

	public JButton getReplace() {
		return replace;
	}

	public void setReplace(JButton replace) {
		this.replace = replace;
	}

	public JButton getSubmitAdd() {
		return submitAdd;
	}

	public void setSubmitAdd(JButton submitAdd) {
		this.submitAdd = submitAdd;
	}

	public JButton getSubmitReplace() {
		return submitReplace;
	}

	public void setSubmitReplace(JButton submitReplace) {
		this.submitReplace = submitReplace;
	}
	
	public static void main (String[]args){
		GameView test=new GameView();
		Player player=new Player("Mark");
		player.createFighter('S', "Fighter1");
		World myWorld =new World();
		WorldView testWorld=new WorldView(player,myWorld);
		test.setContentPane(testWorld);
	}

}

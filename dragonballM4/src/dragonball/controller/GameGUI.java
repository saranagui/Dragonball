package dragonball.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;


import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

import dragonball.model.attack.PhysicalAttack;
import dragonball.model.attack.SuperAttack;
import dragonball.model.attack.UltimateAttack;
import dragonball.model.battle.Battle;
import dragonball.model.battle.BattleEvent;
import dragonball.model.battle.BattleEventType;
import dragonball.model.cell.Cell;
import dragonball.model.cell.Collectible;
import dragonball.model.cell.CollectibleCell;
import dragonball.model.cell.FoeCell;
import dragonball.model.character.fighter.Fighter;
import dragonball.model.character.fighter.NonPlayableFighter;
import dragonball.model.character.fighter.PlayableFighter;
import dragonball.model.character.fighter.Saiyan;
import dragonball.model.dragon.Dragon;
import dragonball.model.dragon.DragonWish;
import dragonball.model.dragon.DragonWishType;
import dragonball.model.exceptions.DuplicateAttackException;
import dragonball.model.exceptions.MapIndexOutOfBoundsException;
import dragonball.model.exceptions.MaximumAttacksLearnedException;
import dragonball.model.exceptions.MissingFieldException;
import dragonball.model.exceptions.NotASaiyanException;
import dragonball.model.exceptions.NotEnoughAbilityPointsException;
import dragonball.model.exceptions.NotEnoughKiException;
import dragonball.model.exceptions.NotEnoughSenzuBeansException;
import dragonball.model.exceptions.UnknownAttackTypeException;
import dragonball.model.game.Game;
import dragonball.model.game.GameListener;
import dragonball.model.game.GameState;
import dragonball.model.world.World;
import dragonball.view.BattleView;
import dragonball.view.DragonView;
import dragonball.view.FighterView;
import dragonball.view.GameView;
import dragonball.view.NewFighterView;
import dragonball.view.StartMenuView;
import dragonball.view.StartNewGameView;
import dragonball.view.UpgradeFighterView;
import dragonball.view.WorldView;

public class GameGUI implements Serializable,GameListener,ActionListener, KeyListener {

	private Game game;
	private StartMenuView startMenuView;
	private WorldView worldView;
	private BattleView battleView;
	private DragonView dragonView;
	private StartNewGameView startNewGameView; 
	private GameView gameView;
	private FighterView fighterView;
	private NewFighterView newFighterView;
	private UpgradeFighterView upgradeFighterView;	
	private static int saveIndex;
	
	
	
	public GameGUI()
	{
		try {
			game=new Game();
			saveIndex++;
		} 
		catch (MissingFieldException e) 
		{
			e.printStackTrace();
		}
		catch (UnknownAttackTypeException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		game.setListener(this);
		gameView=new GameView();
		
		
		int randDragonIndex= (int)(Math.random()*(game.getDragons().size()));
		Dragon dragon= game.getDragons().get(randDragonIndex);
		
		startMenuView=new StartMenuView();
		dragonView = new DragonView(dragon);
		startNewGameView= new StartNewGameView();
		newFighterView= new NewFighterView();
		upgradeFighterView = new UpgradeFighterView();
		
		
		(startMenuView.getStart()).addActionListener(this);		
		(startMenuView.getLoad()).addActionListener(this);
		
		gameView.setContentPane(startMenuView);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button=null;
		
		if(e.getSource() instanceof JButton)
			button = (JButton) e.getSource();
		
       if (e.getSource() instanceof JButton)
       {
    	   if(game.getState()==GameState.WORLD)
    	   {
    		   if(button==startMenuView.getStart() || button==startMenuView.getLoad())
    			   this.startPlaying(button);
    		   else if (button==startNewGameView.getEarthling() || button==startNewGameView.getFrieza() || button==startNewGameView.getNamekian() || button==startNewGameView.getMajin() || button==startNewGameView.getSaiyan())
    			   this.setUpPlayer(button);
					else if(button==startNewGameView.getPlayerNameSubmit())
					{
						if(!(startNewGameView.getPlayerName().getText().equals("") || startNewGameView.getPlayerName().getText().equals(" ")))
						{
							startNewGameView.getPlayerName().setEditable(false);
							(game.getPlayer()).setName(startNewGameView.getName());
							startNewGameView.getFighterPanel().setVisible(true);
							startNewGameView.getFighterText().setVisible(true);
							startNewGameView.repaint();
							startNewGameView.revalidate();
						}
						else
						{
							JFrame frame= new JFrame("JOptionPane showMessageDialog example");
	        				JOptionPane.showMessageDialog(frame, "Player name can't be empty");	
	        			
						}
					}
					else if(button==startNewGameView.getFighterNameSubmit())
					{
						if(!(startNewGameView.getFighterNameJT().getText().equals("") || startNewGameView.getFighterNameJT().getText().equals(" ")))
						{
							startNewGameView.getFighterNameJT().setEditable(false);
							startNewGameView.getFighterbuttons().setVisible(true);
							startNewGameView.repaint();
							startNewGameView.revalidate();
						}
						else
						{
							JFrame frame= new JFrame("JOptionPane showMessageDialog example");
	        				JOptionPane.showMessageDialog(frame, "Fighter name can't be empty");	
	        			
						}

					}
					else if (button==startNewGameView.getSwitchToWorldView())
					{
						this.switchToWorldView();
					}		
	
					else if(button==worldView.getSave())
					{
						try {
							game.save("Dragonball" + saveIndex + ".ser");
							JFrame frame= new JFrame("JOptionPane showMessageDialog example");
	        				JOptionPane.showMessageDialog(frame, "Your game has been saved successfully!");	
	        				this.switchToWorldView();
	        				worldView.repaint();
	        				worldView.revalidate();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else if(button==worldView.getCreateNewFighter())
					{
						fighterView=new FighterView();
						fighterView.setVisible(true);
						this.startNewFighterWindow();
					}
					else if (button==newFighterView.getFighterNameSubmit())
					{
						if(!(newFighterView.getFighterNameField().getText().equals("")|| newFighterView.getFighterNameField().getText().equals(" ")))
						{
							newFighterView.getFighterNameField().setEditable(false);
							newFighterView.getFighterButtons().setVisible(true);
							newFighterView.repaint();
							newFighterView.revalidate();
						}
						else
						{
							JFrame frame= new JFrame("JOptionPane showMessageDialog example");
	        				JOptionPane.showMessageDialog(frame, "Fighter name can't be empty");	
	   
						}
					}
					else if (button==newFighterView.getNewEarthling() || button==newFighterView.getNewFrieza() || button==newFighterView.getNewMajin() || button==newFighterView.getNewNamekian() || button==newFighterView.getNewSaiyan())
						this.setUpNewFighter(button);
					else if (button==worldView.getUpgradeFighter())
					{
						fighterView=new FighterView();
						fighterView.setVisible(true);
						this.startUpgradeFighterWindow();
					}
					else if (button==upgradeFighterView.getUpgradeHealthPoints() || button==upgradeFighterView.getUpgradeBlastDamage() || button==upgradeFighterView.getUpgradeMaxKi() || button==upgradeFighterView.getUpgradeMaxStamina() || button==upgradeFighterView.getUpgradePhysicalDamage())
						this.upgradeFighter(button);
					else if (button==upgradeFighterView.getEndUpgrade())
					{
						fighterView.dispose();
						this.switchToWorldView();
					}	
					else if(button==worldView.getCreateNewFighter())
					{
						fighterView=new FighterView();
						fighterView.setVisible(true);
						this.startNewFighterWindow();
					}
					else if (button==newFighterView.getFighterNameSubmit())
					{
						newFighterView.getFighterNameField().setEditable(false);
						newFighterView.getFighterButtons().setVisible(true);
						newFighterView.repaint();
						newFighterView.revalidate();
					}
					else if (button==newFighterView.getNewEarthling() || button==newFighterView.getNewFrieza() || button==newFighterView.getNewMajin() || button==newFighterView.getNewNamekian() || button==newFighterView.getNewSaiyan())
						this.setUpNewFighter(button);
					else if (button==worldView.getUpgradeFighter())
					{
						if(game.getPlayer().getActiveFighter().getAbilityPoints()>0)
						{
							fighterView=new FighterView();
							fighterView.setVisible(true);
							this.startUpgradeFighterWindow();
						}
						else
						{
							JFrame frame= new JFrame("JOptionPane showMessageDialog example");
	        				JOptionPane.showMessageDialog(frame, "Unfortunately, you have no ability points to upgrade your fighter");	
	        				this.switchToWorldView();
	        				worldView.repaint();
	        				worldView.revalidate();
						}
					}
					else if (button==upgradeFighterView.getUpgradeHealthPoints() || button==upgradeFighterView.getUpgradeBlastDamage() || button==upgradeFighterView.getUpgradeMaxKi() || button==upgradeFighterView.getUpgradeMaxStamina() || button==upgradeFighterView.getUpgradePhysicalDamage())
						this.upgradeFighter(button);
					else if (button==upgradeFighterView.getEndUpgrade())
					{
						fighterView.dispose();
						this.switchToWorldView();
					}
					else if(button==worldView.getChangeFighter())
					{
						if(game.getPlayer().getFighters().size()>1)
						{			
							this.switchToWorldView();
							worldView.getSave().setVisible(false);
							worldView.getCreateNewFighter().setVisible(false);
							worldView.getUpgradeFighter().setVisible(false);
							worldView.getChangeFighter().setVisible(false);
							worldView.getAssignAttacks().setVisible(false);
							worldView.getChooseAFighter().setVisible(true);
							worldView.getSubmitChoose().setVisible(true);
							worldView.repaint();
							worldView.revalidate();
						}
						else
						{
							JFrame frame= new JFrame("JOptionPane showMessageDialog example");
	        				JOptionPane.showMessageDialog(frame, "You currently have one fighter only!");	
	        				this.switchToWorldView();
	        				worldView.repaint();
	        				worldView.revalidate();
						}
        				
					}
					else if(button==worldView.getSubmitChoose())
					{
						 int select= worldView.getChooseAFighter().getSelectedIndex();
	    				
						 if(select>0)
		    			   {
		    				   game.getPlayer().setActiveFighter(game.getPlayer().getFighters().get(select-1));
		    				   this.switchToWorldView();
		    				   worldView.repaint();
		    				   worldView.revalidate();
		    			   }
					}
					else if(button==worldView.getAssignAttacks())
					{
						this.switchToWorldView();
						worldView.getSave().setVisible(false);
						worldView.getCreateNewFighter().setVisible(false);
						worldView.getUpgradeFighter().setVisible(false);
						worldView.getChangeFighter().setVisible(false);
						worldView.getAssignAttacks().setVisible(false);
						worldView.getAssignSA().setVisible(true);
						worldView.getAssignUA().setVisible(true);
						
						worldView.getAssignSA().addActionListener(this);
						worldView.getAssignUA().addActionListener(this);

						
						worldView.repaint();
						worldView.revalidate();
					}
					else if(button==worldView.getAssignSA())
					{
						if(game.getPlayer().getSuperAttacks().size()>0)
						{	
							worldView.getAssignSA().setVisible(false);
							worldView.getAssignUA().setVisible(false);
							worldView.getAdd().setVisible(true);
							worldView.getReplace().setVisible(true);
							worldView.setAssignSuper(true);
							
							worldView.getAdd().addActionListener(this);
							worldView.getReplace().addActionListener(this);
							
							worldView.repaint();
							worldView.revalidate();
						}
						else 
						{
							JFrame frame= new JFrame("JOptionPane showMessageDialog example");
	        				JOptionPane.showMessageDialog(frame, "Unfortunately, you currently have no super attacks!");	
	        				this.switchToWorldView();
	        				worldView.repaint();
	        				worldView.revalidate();
						}
					}
					else if (button== worldView.getAssignUA())
					{
						if(game.getPlayer().getUltimateAttacks().size()>0)
						{
							worldView.getAssignSA().setVisible(false);
							worldView.getAssignUA().setVisible(false);
							worldView.getAdd().setVisible(true);
							worldView.getReplace().setVisible(true);
							worldView.setAssignSuper(false);
							
							worldView.getAdd().addActionListener(this);
							worldView.getReplace().addActionListener(this);
							
							worldView.repaint();
							worldView.revalidate();
						}
						else 
						{
							JFrame frame= new JFrame("JOptionPane showMessageDialog example");
	        				JOptionPane.showMessageDialog(frame, "Unfortunately, you currently have no ultimate attacks!");	
	        				this.switchToWorldView();
	        				worldView.repaint();
	        				worldView.revalidate();
						}
					}
					else if(button==worldView.getAdd())
					{
						if(worldView.isAssignSuper())
						{
							worldView.getAdd().setVisible(false);
							worldView.getReplace().setVisible(false);
							worldView.getPlayerSA().setVisible(true);
							worldView.getSubmitAdd().setVisible(true);
							
							worldView.getPlayerSA().addActionListener(this);
							worldView.getSubmitAdd().addActionListener(this);
							
							worldView.repaint();
							worldView.revalidate();
						}
						else 
						{
							worldView.getAdd().setVisible(false);
							worldView.getReplace().setVisible(false);
							worldView.getPlayerUA().setVisible(true);
							worldView.getSubmitAdd().setVisible(true);

							worldView.getPlayerUA().addActionListener(this);
							worldView.getSubmitAdd().addActionListener(this);

							worldView.repaint();
							worldView.revalidate();
						}
					}
					else if (button==worldView.getReplace())
					{
						
							if(worldView.isAssignSuper())
							{
								if (game.getPlayer().getActiveFighter().getSuperAttacks().size()>0)
								{	
									worldView.getAdd().setVisible(false);
									worldView.getReplace().setVisible(false);
									worldView.getPlayerSA().setVisible(true);
									worldView.getFighterSA().setVisible(true);
									worldView.getSubmitReplace().setVisible(true);
							
									worldView.getPlayerSA().addActionListener(this);
									worldView.getFighterSA().addActionListener(this);
									worldView.getSubmitReplace().addActionListener(this);
							
									worldView.repaint();
									worldView.revalidate();
								}
								else
								{
									JFrame frame= new JFrame("JOptionPane showMessageDialog example");
			        				JOptionPane.showMessageDialog(frame, "Unfortunately, you currently have no assigned super attacks to replace!");	
			        				this.switchToWorldView();
			        				worldView.repaint();
			        				worldView.revalidate();
								
								}
							}
							else 
							{
								if(game.getPlayer().getActiveFighter().getUltimateAttacks().size()>0)
								{
									worldView.getAdd().setVisible(false);
									worldView.getReplace().setVisible(false);
									worldView.getPlayerUA().setVisible(true);
									worldView.getFighterUA().setVisible(true);
									worldView.getSubmitReplace().setVisible(true);
							
									worldView.getPlayerUA().addActionListener(this);
									worldView.getFighterUA().addActionListener(this);
									worldView.getSubmitReplace().addActionListener(this);
							
									worldView.repaint();
									worldView.revalidate();
								}
								else
								{
									JFrame frame= new JFrame("JOptionPane showMessageDialog example");
			        				JOptionPane.showMessageDialog(frame, "Unfortunately, you currently have no assigned ultimate attacks to replace!");	
			        				this.switchToWorldView();
			        				worldView.repaint();
			        				worldView.revalidate();
								
								}
							}
					}
					else if (button==worldView.getSubmitAdd())
					{
						if(worldView.isAssignSuper())
							this.assignSAToFighter();
						else
							this.assignUAToFighter();
					}
					else if(button==worldView.getSubmitReplace())
					{
						if(worldView.isAssignSuper())
							this.replaceSAToFighter();
						else
							this.replaceUAToFighter();
					}
					
				}
					else if(game.getState()==GameState.DRAGON)
					{	
						if(button==dragonView.getChooseAWish())
						{
							dragonView.getChooseAWish().setVisible(false);
							dragonView.getAbilityPoints().setVisible(true);
							dragonView.getSenzuBeans().setVisible(true);
							dragonView.getSuperAttack().setVisible(true);
							dragonView.getUltimateAttack().setVisible(true);
				
						}
						//else if(combo==dragonView.getAvailableWishes())
						else if(button==dragonView.getAbilityPoints())
						{
							/*	String wishChosen = (String)combo.getSelectedItem();
							DragonWish[] dragonWishes=dragonView.getDragon().getWishes();
						DragonWish wish=null;
						switch (wishChosen){
						case "Senzu Beans":wish=dragonWishes[0];break;
						case "Ability Points":wish=dragonWishes[1];break;
						case "SuperAttack":wish=dragonWishes[2];break;
						case "UltimateAttack":wish=dragonWishes[3];break;
						}---this line was the end of the comment but I removed it to end it later*/
        				game.getPlayer().chooseWish(dragonView.getDragon().getWishes()[1]);
        				JFrame frame= new JFrame("JOptionPane showMessageDialog example");
        				JOptionPane.showMessageDialog(frame, "Congratulations! you now have "+dragonView.getDragon().getWishes()[1].getAbilityPoints()+" new Ability Points!");		
        				this.switchToWorldView();				
        			}
        			else if(button==dragonView.getSenzuBeans())
        			{
        				game.getPlayer().chooseWish(dragonView.getDragon().getWishes()[0]);
        				JFrame frame= new JFrame("JOptionPane showMessageDialog example");
        				JOptionPane.showMessageDialog(frame, "Congratulations! you now have "+dragonView.getDragon().getWishes()[0].getSenzuBeans()+" new Senzu Beans!");					  
        				this.switchToWorldView();
        			}
        			else if(button==dragonView.getSuperAttack())
        			{
        				game.getPlayer().chooseWish(dragonView.getDragon().getWishes()[2]);


        				JFrame frame= new JFrame("JOptionPane showMessageDialog example");
        				JOptionPane.showMessageDialog(frame, "Congratulations! you unlocked the "+dragonView.getDragon().getWishes()[2].getSuperAttack().getName()+" Attack!");					  
        				this.switchToWorldView();
        			}
        			else if(button==dragonView.getUltimateAttack())
        			{
        				game.getPlayer().chooseWish(dragonView.getDragon().getWishes()[3]);
        				JFrame frame= new JFrame("JOptionPane showMessageDialog example");
        				JOptionPane.showMessageDialog(frame, "Congratulations! you unlocked the "+dragonView.getDragon().getWishes()[3].getUltimateAttack().getName()+" Attack!");					  
        				this.switchToWorldView();
        			}	
        		}
        		else if(game.getState()==GameState.BATTLE)
        		{
        			if(button==battleView.getAttack())
        			{
        				battleView.getAttack().setVisible(false);
        				battleView.getBlock().setVisible(false);
        				battleView.getUse().setVisible(false);
        				battleView.getPhysicalAttack().setVisible(true);
        				battleView.getPhysicalAttack().addActionListener(this);
        				
        				if(battleView.isAvailableSuperAttacksON())
        				{
        					battleView.getSuperAttack().setVisible(true);
        					battleView.getSuperAttack().addActionListener(this);
        				}
        				if(battleView.isAvailableUltimateAttacksON())
        				{
        					battleView.getUltimateAttack().setVisible(true);
        					battleView.getUltimateAttack().addActionListener(this);
        				}
        				
        				battleView.repaint();
        				battleView.revalidate();
        				
        			}	
        			else if(button==battleView.getBlock())
        			{
        				//((Fighter)(battleView.getBattle().getMe())).setHealthPoints(0);
        				battleView.getBattle().block();
        				//((Saiyan)(battleView.getBattle().getMe())).setTransformed(true);
        				
        				battleView.repaint();
        				battleView.revalidate();
        			}
        			else if(button==battleView.getUse())
        			{
        				try {
        					battleView.getBattle().use(game.getPlayer(),Collectible.SENZU_BEAN);
        				} catch (NotEnoughSenzuBeansException e1) {
        					JFrame frame= new JFrame("JOptionPane showMessageDialog example");
        					JOptionPane.showMessageDialog(frame,e1.getMessage());
        				}	
        				battleView.repaint();
        				battleView.revalidate();
        			}
        			else if(button==battleView.getPhysicalAttack())
        			{
        				try {
        					battleView.getBattle().attack(new PhysicalAttack());
        				} catch (NotEnoughKiException e1) {
        					//JFrame frame= new JFrame("JOptionPane showMessageDialog example");
        					//JOptionPane.showMessageDialog(frame, "You don't have enough ki bars to preform this Attack! "+e1.getMessage());
        				}
//        				battleView.getAttack().setVisible(true);
//        				battleView.getUse().setVisible(true);
//        				battleView.getBlock().setVisible(true);
//        				battleView.getPhysicalAttack().setVisible(false);
//        				battleView.getSuperAttack().setVisible(false);
//        				battleView.getUltimateAttack().setVisible(false);
//        				
//        				battleView.repaint();
//        				battleView.revalidate();
        			}	
        			else if(button==battleView.getSuperAttack())
        			{
        				if(battleView.isAvailableSuperAttacksON())
        				{
        					battleView.getPhysicalAttack().setVisible(false);
        					battleView.getSuperAttack().setVisible(false);
        					battleView.getUltimateAttack().setVisible(false);
        					battleView.getAvailableSuperAttacks().setVisible(true);
        					battleView.getAvailableSuperAttacks().addActionListener(this);
        					battleView.getSubmitSA().setVisible(true);
        					
        					battleView.repaint();
        					battleView.revalidate();
        				}
        				else
        				{
        					battleView.getAttack().setVisible(true);
            				battleView.getUse().setVisible(true);
            				battleView.getBlock().setVisible(true);
            				battleView.getPhysicalAttack().setVisible(false);
            				battleView.getSuperAttack().setVisible(false);
            				battleView.getUltimateAttack().setVisible(false);
            			
        					JFrame frame= new JFrame("JOptionPane showMessageDialog example");
        					JOptionPane.showMessageDialog(frame, "Sorry, you don't have any assigned super Attacks now," + "\n" + "please try using another option");
        						
            				battleView.repaint();
            				battleView.revalidate();
        				}
        			}
        			else if(button==battleView.getSubmitSA())
        			{
        				int select= battleView.getAvailableSuperAttacks().getSelectedIndex();
         			   if (select>0)
         			   {
         				   try
         				   {
         					   
         					   SuperAttack chosenSA= ((Fighter)(battleView.getBattle().getMe())).getSuperAttacks().get(select-1);
         					   battleView.getBattle().attack(chosenSA);
         					   
//         					   battleView.getAttack().setVisible(true);
//         					   battleView.getUse().setVisible(true);
//                					battleView.getBlock().setVisible(true);
//                					battleView.getPhysicalAttack().setVisible(false);
//                					battleView.getSuperAttack().setVisible(false);
//                					battleView.getUltimateAttack().setVisible(false);
//                				
//                					battleView.repaint();
//                					battleView.revalidate();
         				   
         				  }
         				   catch (NotEnoughKiException e1)
         				   {
            						
         					   battleView.getAvailableSuperAttacks().setSelectedIndex(-1);
         					   battleView.getAvailableSuperAttacks().setVisible(false);
         					   JFrame frame= new JFrame("JOptionPane showMessageDialog example");
            					   JOptionPane.showMessageDialog(frame, "You don't have enough ki bars to preform this Attack! "+e1.getMessage());
            					   battleView.getAttack().setVisible(true);
            					   battleView.getUse().setVisible(true);
             					battleView.getBlock().setVisible(true);
             					battleView.getPhysicalAttack().setVisible(false);
             					battleView.getSuperAttack().setVisible(false);
             					battleView.getUltimateAttack().setVisible(false);
             					
             					battleView.repaint();
             					battleView.revalidate();
         				   } 
         			   }
        			}
        			else if(button==battleView.getUltimateAttack())
        			{
        				
        					
        					if(battleView.isAvailableUltimateAttacksON())
        					{
        						battleView.getPhysicalAttack().setVisible(false);
        						battleView.getSuperAttack().setVisible(false);
        						battleView.getUltimateAttack().setVisible(false);
        						battleView.getAvailableUltimateAttacks().setVisible(true);
        						battleView.getAvailableUltimateAttacks().addActionListener(this);
        						battleView.getSubmitUA().setVisible(true);
        					
        						battleView.repaint();
        						battleView.revalidate();
        					}
        					else
        					{
        						battleView.getAttack().setVisible(true);
        						battleView.getUse().setVisible(true);
        						battleView.getBlock().setVisible(true);
        						battleView.getPhysicalAttack().setVisible(false);
        						battleView.getSuperAttack().setVisible(false);
        						battleView.getUltimateAttack().setVisible(false);
            				
        						JFrame frame= new JFrame("JOptionPane showMessageDialog example");
        						JOptionPane.showMessageDialog(frame, "Sorry, you don't have any assigned ultimate Attacks now," + "\n" + "please try using another option");
        						
        						battleView.repaint();
        						battleView.revalidate();
        					}	
        				
        			}
        			else if(button==battleView.getSubmitUA())
        			{
        				int select= battleView.getAvailableUltimateAttacks().getSelectedIndex();
         			   if (select>0)
         			   {
         				 try
         				   {
         					   UltimateAttack chosenUA= ((Fighter)(battleView.getBattle().getMe())).getUltimateAttacks().get(select-1);
         					   battleView.getBattle().attack(chosenUA);
         					   
//         					   battleView.getAttack().setVisible(true);
//         					   battleView.getUse().setVisible(true);
//                					battleView.getBlock().setVisible(true);
//                					battleView.getPhysicalAttack().setVisible(false);
//                					battleView.getSuperAttack().setVisible(false);
//                					battleView.getAvailableSuperAttacks().setEnabled(false);;
//                					battleView.getUltimateAttack().setVisible(false);
//                				
//                					battleView.repaint();
//                					battleView.revalidate();
         				   
         				  }
         				   catch (NotEnoughKiException e1)
         				   {
         					   battleView.getAvailableUltimateAttacks().setSelectedIndex(-1);
         					   battleView.getAvailableUltimateAttacks().setVisible(false);
         					
         					   JFrame frame= new JFrame("JOptionPane showMessageDialog example");
            					   JOptionPane.showMessageDialog(frame, "You don't have enough ki bars to preform this Attack! "+e1.getMessage());
            					   battleView.getAttack().setVisible(true);
            					    battleView.getUse().setVisible(true);
             					battleView.getBlock().setVisible(true);
             					battleView.getPhysicalAttack().setVisible(false);
             					battleView.getSuperAttack().setVisible(false);
             					battleView.getUltimateAttack().setVisible(false);

             					battleView.repaint();
             					battleView.revalidate();
         				   }
         			   }
        			}
        				
        		}
       }
	}
	
	public void replaceSAToFighter()
	{
		try
		{
			
			int chosenOldAttackIndex= worldView.getFighterSA().getSelectedIndex();
			int chosenNewAttackIndex= worldView.getPlayerSA().getSelectedIndex();
			if(chosenOldAttackIndex>0 && chosenNewAttackIndex>0)
			{
				SuperAttack chosenOldAttack= game.getPlayer().getActiveFighter().getSuperAttacks().get(chosenOldAttackIndex-1);
			
				SuperAttack chosenNewAttack= game.getPlayer().getSuperAttacks().get(chosenNewAttackIndex-1);
			
				game.getPlayer().assignAttack(game.getPlayer().getActiveFighter(),chosenNewAttack , chosenOldAttack);
		
				JFrame frame= new JFrame("JOptionPane showMessageDialog example");
				JOptionPane.showMessageDialog(frame, ""+ chosenNewAttack.getName() + " was assigned to " + game.getPlayer().getActiveFighter().getName() + "successfully");
			
				this.switchToWorldView();
				worldView.repaint();
				worldView.revalidate();
			}
		}
		
		catch (MaximumAttacksLearnedException e1) 
		{
			
		} 
		catch (DuplicateAttackException e1) 
		{
			JFrame frame= new JFrame("JOptionPane showMessageDialog example");
			JOptionPane.showMessageDialog(frame, e1.getMessage() + ","+ "\n"+" you can try again if you wish for a different attack or a different fighter.");
			this.switchToWorldView();
			worldView.repaint();
			worldView.revalidate();
		} 
		catch (NotASaiyanException e1) 
		{
			JFrame frame= new JFrame("JOptionPane showMessageDialog example");
			JOptionPane.showMessageDialog(frame, e1.getMessage() + ","+ "\n"+" you can try again if you wish for a different attack or a different fighter.");
			this.switchToWorldView();
			worldView.repaint();
			worldView.revalidate();
		}
	
	
	}
	
	public void replaceUAToFighter()
	{
		try
		{			
			int chosenOldAttackIndex= worldView.getFighterUA().getSelectedIndex();
			int chosenNewAttackIndex= worldView.getPlayerUA().getSelectedIndex();
			if(chosenOldAttackIndex>0 && chosenNewAttackIndex>0)
			{
				UltimateAttack chosenOldAttack= game.getPlayer().getActiveFighter().getUltimateAttacks().get(chosenOldAttackIndex-1);
			
				UltimateAttack chosenNewAttack= game.getPlayer().getUltimateAttacks().get(chosenNewAttackIndex-1);
			
			
				game.getPlayer().assignAttack(game.getPlayer().getActiveFighter(),chosenNewAttack , chosenOldAttack);
		
				JFrame frame= new JFrame("JOptionPane showMessageDialog example");
				JOptionPane.showMessageDialog(frame, ""+ chosenNewAttack.getName() + " was assigned to " + game.getPlayer().getActiveFighter().getName() + "successfully");
			
				this.switchToWorldView();
				worldView.repaint();
				worldView.revalidate();
			}
		}
		
		catch (MaximumAttacksLearnedException e1) 
		{
			
		} 
		catch (DuplicateAttackException e1) 
		{
			JFrame frame= new JFrame("JOptionPane showMessageDialog example");
			JOptionPane.showMessageDialog(frame, e1.getMessage() + ","+ "\n"+" you can try again if you wish for a different attack or a different fighter.");
			this.switchToWorldView();
			worldView.repaint();
			worldView.revalidate();
		} 
		catch (NotASaiyanException e1) 
		{
			JFrame frame= new JFrame("JOptionPane showMessageDialog example");
			JOptionPane.showMessageDialog(frame, e1.getMessage() + ","+ "\n"+" you can try again if you wish for a different attack or a different fighter.");
			this.switchToWorldView();
			worldView.repaint();
			worldView.revalidate();
		}
	}
	
	public void assignSAToFighter()
	{
			try
			{	
				int chosenAttackIndex= worldView.getPlayerSA().getSelectedIndex();
				if(chosenAttackIndex>0)
				{
					SuperAttack chosenAttack= game.getPlayer().getSuperAttacks().get(chosenAttackIndex-1);
				
					game.getPlayer().assignAttack(game.getPlayer().getActiveFighter(),chosenAttack , null);
			
					JFrame frame= new JFrame("JOptionPane showMessageDialog example");
					JOptionPane.showMessageDialog(frame, ""+ chosenAttack.getName() + " was assigned to " + game.getPlayer().getActiveFighter().getName() + "successfully");
				
					this.switchToWorldView();
					worldView.repaint();
					worldView.revalidate();
				}
			}
		
			catch (MaximumAttacksLearnedException e1) 
			{
				JFrame frame= new JFrame("JOptionPane showMessageDialog example");
				JOptionPane.showMessageDialog(frame, e1.getMessage() + "," + "\n" +"you can try again by replacing it with one of your fighter's super attacks");
		
				this.switchToWorldView();
				worldView.repaint();
				worldView.revalidate();
			
			} 
			catch (DuplicateAttackException e1) 
			{
				JFrame frame= new JFrame("JOptionPane showMessageDialog example");
				JOptionPane.showMessageDialog(frame, e1.getMessage() + ","+ "\n"+" you can try again if you wish for a different attack or a different fighter.");
				
				this.switchToWorldView();
				worldView.repaint();
				worldView.revalidate();
			} 
			catch (NotASaiyanException e1) 
			{
				JFrame frame= new JFrame("JOptionPane showMessageDialog example");
				JOptionPane.showMessageDialog(frame, e1.getMessage() + ","+ "\n"+" you can try again if you wish for a different attack or a different fighter.");
				
				this.switchToWorldView();
				worldView.repaint();
				worldView.revalidate();
			}	
	}
	
	public void assignUAToFighter()
	{

		try
		{
		
			int chosenAttackIndex= worldView.getPlayerUA().getSelectedIndex();
			if(chosenAttackIndex>0)
			{
				UltimateAttack chosenAttack= game.getPlayer().getUltimateAttacks().get(chosenAttackIndex-1);
			
				game.getPlayer().assignAttack(game.getPlayer().getActiveFighter(),chosenAttack , null);
		
				JFrame frame= new JFrame("JOptionPane showMessageDialog example");
				JOptionPane.showMessageDialog(frame, ""+ chosenAttack.getName() + " was assigned to " + game.getPlayer().getActiveFighter().getName() + "successfully");
			
				this.switchToWorldView();
				worldView.repaint();
				worldView.revalidate();
			}
		}
	
		catch (MaximumAttacksLearnedException e1) 
		{
			JFrame frame= new JFrame("JOptionPane showMessageDialog example");
			JOptionPane.showMessageDialog(frame, e1.getMessage() + "," + "\n" +"you can try again by replacing it with one of your fighter's ultimate attacks.");
	
			this.switchToWorldView();
			worldView.repaint();
			worldView.revalidate();
		
		} 
		catch (DuplicateAttackException e1) 
		{
			JFrame frame= new JFrame("JOptionPane showMessageDialog example");
			JOptionPane.showMessageDialog(frame, e1.getMessage() + ","+ "\n"+" you can try again if you wish for a different attack or a different fighter.");
			this.switchToWorldView();
			worldView.repaint();
			worldView.revalidate();
		} 
		catch (NotASaiyanException e1) 
		{
			JFrame frame= new JFrame("JOptionPane showMessageDialog example");
			JOptionPane.showMessageDialog(frame, e1.getMessage() + ","+ "\n"+" you can try again if you wish for a different attack or a different fighter.");
			this.switchToWorldView();
			worldView.repaint();
			worldView.revalidate();
		}
	
	
	}
	
	
	public void upgradeFighter(JButton button)
	{
		char race=' ';
		if (button==upgradeFighterView.getUpgradeHealthPoints())
			race='H';
		else if (button==upgradeFighterView.getUpgradePhysicalDamage())
			race='P';
		else if (button==upgradeFighterView.getUpgradeBlastDamage())
			race='B';
		else if(button==upgradeFighterView.getUpgradeMaxKi())
			race='K';
		else if (button==upgradeFighterView.getUpgradeMaxStamina())
			race='S';
		try{
			
			game.getPlayer().upgradeFighter(game.getPlayer().getActiveFighter(), race);
				
			(upgradeFighterView.getMaxHealthPoints()).setText("Your maximum Health Points: " + game.getPlayer().getActiveFighter().getMaxHealthPoints());
			(upgradeFighterView.getPhysicalDamage()).setText("Your physical damage: " + game.getPlayer().getActiveFighter().getPhysicalDamage());
			(upgradeFighterView.getBlastDamage()).setText("Your blast damage: " + game.getPlayer().getActiveFighter().getBlastDamage());
			(upgradeFighterView.getMaxKi()).setText("Your maximum Ki Bars: " + game.getPlayer().getActiveFighter().getMaxKi());
			(upgradeFighterView.getMaxStamina()).setText("Your maximum Stamina: " + game.getPlayer().getActiveFighter().getMaxStamina());
			(upgradeFighterView.getAbilityPoints()).setText("Your available ability Points: " + game.getPlayer().getActiveFighter().getAbilityPoints());
			
			upgradeFighterView.repaint();
			upgradeFighterView.revalidate();
		
		}
		catch(NotEnoughAbilityPointsException e)
		{
			JFrame frame= new JFrame("JOptionPane showMessageDialog example");
			JOptionPane.showMessageDialog(frame, e.getMessage());
		}
	}
	
	public void startUpgradeFighterWindow()
	{
		PlayableFighter active= game.getPlayer().getActiveFighter();
		upgradeFighterView = new UpgradeFighterView();
		(upgradeFighterView.getMaxHealthPoints()).setText("Your maximum Health Points: " + active.getMaxHealthPoints());
		(upgradeFighterView.getPhysicalDamage()).setText("Your physical damage: " + active.getPhysicalDamage());
		(upgradeFighterView.getBlastDamage()).setText("Your blast damage: " + active.getBlastDamage());
		(upgradeFighterView.getMaxKi()).setText("Your maximum Ki Bars: " + active.getMaxKi());
		(upgradeFighterView.getMaxStamina()).setText("Your maximum Stamina: " + active.getMaxStamina());
		(upgradeFighterView.getAbilityPoints()).setText("Your available ability Points: " + active.getAbilityPoints());
		
		(upgradeFighterView.getUpgradeHealthPoints()).addActionListener(this);
		(upgradeFighterView.getUpgradePhysicalDamage()).addActionListener(this);
		(upgradeFighterView.getUpgradeBlastDamage()).addActionListener(this);
		(upgradeFighterView.getUpgradeMaxKi()).addActionListener(this);
		(upgradeFighterView.getUpgradeMaxStamina()).addActionListener(this);
		(upgradeFighterView.getEndUpgrade()).addActionListener(this);
	
		upgradeFighterView.repaint();
		upgradeFighterView.revalidate();
		
		fighterView.setContentPane(upgradeFighterView);
		fighterView.repaint();
		fighterView.revalidate();
	}
	
	public void startNewFighterWindow()
	{
		
		newFighterView= new NewFighterView();
		(newFighterView.getFighterNameField()).addActionListener(this);
		(newFighterView.getFighterNameSubmit()).addActionListener(this);
		(newFighterView.getNewEarthling()).addActionListener(this);
		(newFighterView.getNewFrieza()).addActionListener(this);
		(newFighterView.getNewNamekian()).addActionListener(this);
		(newFighterView.getNewMajin()).addActionListener(this);
		(newFighterView.getNewSaiyan()).addActionListener(this);
		fighterView.setContentPane(newFighterView);
		fighterView.repaint();
		fighterView.revalidate();		
	}
	
	public void setUpNewFighter(JButton action)
	{
		
		char race=' ';
		if(action==newFighterView.getNewEarthling())
			race = 'E';	
		if (action==newFighterView.getNewFrieza())
			race= 'F';
		if (action==newFighterView.getNewNamekian())
			race='N';		
		if (action==newFighterView.getNewMajin())
			race='M';		
		if (action==newFighterView.getNewSaiyan())
			race='S';
		
		
		(game.getPlayer()).createFighter(race,newFighterView.getFighterNameField().getText());
		(game.getPlayer()).setActiveFighter(game.getPlayer().getFighters().get((game.getPlayer().getFighters().size())-1));
		
		fighterView.dispose();
		this.switchToWorldView();
		worldView.repaint();
		worldView.revalidate();
	
	}
	
	public void switchToWorldView()
	{
		worldView = new WorldView(game.getPlayer(), game.getWorld());				
		for(int i=0; i<10; i++)
		{
			for (int j=0; j<10; j++)
			{
				(worldView.getWorldMap())[i][j].addKeyListener(this);
			}
		}	
		
		worldView.getSave().addActionListener(this);
		worldView.getChangeFighter().addActionListener(this);
		worldView.getAssignAttacks().addActionListener(this);
		worldView.getUpgradeFighter().addActionListener(this);
		worldView.getCreateNewFighter().addActionListener(this);
		worldView.getChooseAFighter().addActionListener(this);
		worldView.getAssignSA().addActionListener(this);
		worldView.getAssignUA().addActionListener(this);
		worldView.getAdd().addActionListener(this);
		worldView.getReplace().addActionListener(this);
		worldView.getPlayerSA().addActionListener(this);
		worldView.getPlayerUA().addActionListener(this);
		worldView.getFighterSA().addActionListener(this);
		worldView.getFighterUA().addActionListener(this);
		worldView.getSubmitAdd().addActionListener(this);
		worldView.getSubmitReplace().addActionListener(this);
		worldView.getSubmitChoose().addActionListener(this);
		worldView.getPlayerName().setText(game.getPlayer().getName()+" playing with: "+game.getPlayer().getActiveFighter().getName()+"\nLevel: "+game.getPlayer().getActiveFighter().getLevel());
		
		gameView.setContentPane(worldView);
		gameView.repaint();
		gameView.revalidate();
	}
	public void setUpPlayer(JButton action)
	{
		//where is the name attribute?
		(game.getPlayer()).setName(startNewGameView.getName());
		startNewGameView.getEarthling().setVisible(false);
		startNewGameView.getFrieza().setVisible(false);
		startNewGameView.getMajin().setVisible(false);
		startNewGameView.getNamekian().setVisible(false);
		startNewGameView.getSaiyan().setVisible(false);
		startNewGameView.getSwitchToWorldView().setVisible(true);

		char race=' ';
		if(action==startNewGameView.getEarthling())
		{
			race = 'E';
			startNewGameView.getEarthling().setVisible(true);
		}
		if (action==startNewGameView.getFrieza())
		{
			race= 'F';
			startNewGameView.getFrieza().setVisible(true);
		}
		if (action==startNewGameView.getNamekian())
		{
			race='N';		
			startNewGameView.getNamekian().setVisible(true);
		}
		if (action==startNewGameView.getMajin())
		{
			race='M';
			startNewGameView.getMajin().setVisible(true);			
		}
		if (action==startNewGameView.getSaiyan())
		{
			race='S';
			startNewGameView.getSaiyan().setVisible(true);
		}
		
		(game.getPlayer()).createFighter(race,startNewGameView.getFighterName());
	
	}
	
	
	public void startPlaying(JButton action)
	{
		if(action==startMenuView.getLoad())
		{	try {
				game.load("Dragonball" +saveIndex + ".ser");
				game.setListener(this);
				this.switchToWorldView();
				
			} catch (ClassNotFoundException e1) {
				JFrame frame= new JFrame("JOptionPane showMessageDialog example");
				JOptionPane.showMessageDialog(frame, "Can't find a saved game, Please start a new game");
						
				} 
		catch (IOException e1) {
				JFrame frame= new JFrame("JOptionPane showMessageDialog example");
				JOptionPane.showMessageDialog(frame, "Can't find a saved game, Please start a new game");
				
			}			
		}
		else if(action==startMenuView.getStart())
		{
			startNewGameView=new StartNewGameView();
			(startNewGameView.getEarthling()).addActionListener(this);
			(startNewGameView.getFrieza()).addActionListener(this);
			(startNewGameView.getMajin()).addActionListener(this);
			(startNewGameView.getNamekian()).addActionListener(this);
			(startNewGameView.getSaiyan()).addActionListener(this);
			(startNewGameView.getPlayerNameSubmit()).addActionListener(this);
			(startNewGameView.getFighterNameSubmit()).addActionListener(this);
			(startNewGameView.getSwitchToWorldView()).addActionListener(this);
			(startNewGameView.getPlayerName()).addActionListener(this);
			(startNewGameView.getFighterNameJT()).addActionListener(this);
			
			
			
			gameView.setContentPane(startNewGameView);
		}	
	}
	
	@Override
	public void onDragonCalled(Dragon dragon) {
		// TODO Auto-generated method stub
		dragonView=new DragonView(dragon);
		dragonView.getChooseAWish().addActionListener(this);
		dragonView.getAbilityPoints().addActionListener(this);
		dragonView.getSenzuBeans().addActionListener(this);
		dragonView.getSuperAttack().addActionListener(this);
		dragonView.getUltimateAttack().addActionListener(this);
		
		gameView.setContentPane(dragonView);
		gameView.repaint();
		gameView.revalidate();
	}

	@Override
	public void onCollectibleFound(Collectible collectible) {
		// TODO Auto-generated method stub
		switch (collectible) {
		
			case SENZU_BEAN:
			BufferedImage image;
			try {
				image = ImageIO.read(new File("Senzu_Bean.png"));
		    ImageIcon icon = new ImageIcon((Image)image);
		    JLabel label = new JLabel();
		    label.setPreferredSize(new Dimension(50,10));
		    label.setIcon(icon);
			worldView.getSenzuBeans().add(label);
			worldView.getComment().setText("Congratulations!, You collected " + game.getPlayer().getSenzuBeans() + " senzuBean(s) till now");
			worldView.getComment().setVisible(true);

			break;
			}
			catch(IOException e)
			{
				
			}
			case DRAGON_BALL:
			try{
			image = ImageIO.read(new File("wish.jpeg"));
		    ImageIcon icon = new ImageIcon((Image)image);
		    JLabel label = new JLabel();
		    label.setPreferredSize(new Dimension(50,10));
		    label.setIcon(icon);
			worldView.getDragonballs().add(label);
			worldView.getComment().setText("Congratulations!, You collected " + game.getPlayer().getDragonBalls() + " dragonballs till now");
			worldView.getComment().setVisible(true);


			break;
			}
			catch(IOException e){
		
			}
			worldView.repaint();
			worldView.revalidate();
		}
	}

	@Override
	public void onBattleEvent(BattleEvent e) {
		//consider the event type!!
		Battle battle=(Battle)(e.getSource());
		if(e.getType()==BattleEventType.STARTED)
		{
			
			this.switchToBattleView((Battle)e.getSource());
			
		}
		else if(e.getType()==BattleEventType.ATTACK){
			//if(((Fighter)(battleView.getBattle().getFoe())).getHealthPoints()>0 && ((Fighter)(battleView.getBattle().getMe())).getHealthPoints()>0)
			//{
				//this.switchToBattleView(battle);
			Timer timer = new Timer( 30, new ActionListener(){
				  @Override
				  public void actionPerformed( ActionEvent e1 ){
					  if(((Fighter)(battleView.getBattle().getFoe())).getHealthPoints()==0 || ((Fighter)(battleView.getBattle().getMe())).getHealthPoints()==0)
						  battleView.getComment().setText(((Fighter)(battle.getAttacker())).getName()+" used a(n) "+e.getAttack().getName()+"!!");
					  else
						  battleView.getComment().setText(((Fighter)(battle.getDefender())).getName()+" used a(n) "+e.getAttack().getName()+"!!");
					  battleView.getComment().setVisible(true);
//						battleView.updateFoeInfo();
//						battleView.updatePlayerInfo();
					  battleView.repaint();
					  battleView.revalidate();
					  //gameView.setContentPane(battleView);
				  	}
				} );
			timer.setRepeats(false);
			timer.start();
			//}
			
		}
		else if(e.getType()==BattleEventType.BLOCK){
			
			//if(((Fighter)(battleView.getBattle().getFoe())).getHealthPoints()>0 && ((Fighter)(battleView.getBattle().getMe())).getHealthPoints()>0)
			//{this.switchToBattleView(battle);
			Timer timer = new Timer( 30, new ActionListener(){
				  @Override
				  public void actionPerformed( ActionEvent e1 ){
//					  if(((Fighter)(battleView.getBattle().getFoe())).getHealthPoints()==0 || ((Fighter)(battleView.getBattle().getMe())).getHealthPoints()==0)
//						  battleView.getComment().setText(((Fighter)(battle.getAttacker())).getName()+" blocks!!");
//					  else
						  battleView.getComment().setText(((Fighter)(battle.getDefender())).getName()+" blocks!!");
					  battleView.getComment().setVisible(true);
//						battleView.updateFoeInfo();
//						battleView.updatePlayerInfo();
					  battleView.repaint();
					  battleView.revalidate();
					  //gameView.setContentPane(battleView);
				  	}
				} );
			timer.setRepeats(false);
			timer.start();
			//}
			
			
		}
		else if(e.getType()==BattleEventType.USE){
			//if(((Fighter)(battleView.getBattle().getFoe())).getHealthPoints()>0 && ((Fighter)(battleView.getBattle().getMe())).getHealthPoints()>0)
			//{this.switchToBattleView(battle);
			Timer timer = new Timer( 30, new ActionListener(){
				  @Override
				  public void actionPerformed( ActionEvent e1 ){
//					  if(((Fighter)(battleView.getBattle().getFoe())).getHealthPoints()==0 || ((Fighter)(battleView.getBattle().getMe())).getHealthPoints()==0)
//						  battleView.getComment().setText(((Fighter)(battle.getAttacker())).getName()+" used a Senzu Bean!!");
//					  else
						  battleView.getComment().setText(((Fighter)(battle.getDefender())).getName()+" used a Senzu Bean!!");
					  battleView.getComment().setVisible(true);
//						battleView.updateFoeInfo();
//						battleView.updatePlayerInfo();
					  battleView.repaint();
					  battleView.revalidate();
					  //gameView.setContentPane(battleView);
				  	}
				} );
			timer.setRepeats(false);
			timer.start();
			//}
			
			
		}
		else if(e.getType()==BattleEventType.NEW_TURN){
			if(((Fighter)(battleView.getBattle().getFoe())).getHealthPoints()>0 && ((Fighter)(battleView.getBattle().getMe())).getHealthPoints()>0)
			{this.switchToBattleView(battle);
			this.updateFighters();
			if(battleView.getBattle().getAttacker().equals(battleView.getBattle().getFoe()))
			{
				
				battleView.getPlayerArrow().setVisible(false);
				battleView.getActionsOffered().setVisible(false);
				battleView.getFoeArrow().setVisible(true);
				
				((Fighter)(battleView.getBattle().getMe())).setHealthPoints(0);
				Timer timer = new Timer( 50, new ActionListener(){
					  @Override
					  public void actionPerformed( ActionEvent e ){
						  try {
								battle.play();
							} catch (NotEnoughKiException e1) {
								battleView.getComment().setText("Please Wait, I'm stuck picking my attack!");
								battleView.repaint();
								battleView.revalidate();
							}			
					  	}
					} );
					timer.setRepeats(false);
					timer.start();
					
			}
			else
			{
				battleView.getPlayerArrow().setVisible(true);
				battleView.getActionsOffered().setVisible(true);
				battleView.getFoeArrow().setVisible(false);
			}
			}

			//battleView.getBattle();
//			System.out.println(((Fighter)(battleView.getBattle().getMe())).getKi());
//			System.out.println(((Fighter)(battleView.getBattle().getFoe())).getKi());
//			this.updateFighters();
			
		}
		else if(e.getType()==BattleEventType.ENDED){
			//Should indicate who won!
			
			
			Boolean won=e.getWinner().equals(battle.getMe());
			if(won)
			{
				if((((NonPlayableFighter)(battle.getFoe())).isStrong()))
				{  
	        		String gainedSA=  "";
					ArrayList<SuperAttack> foeSA= ((Fighter)battle.getFoe()).getSuperAttacks();
					
					for(SuperAttack sA: foeSA)
						gainedSA+= sA.getName() + ", ";
					
					String gainedUA= "";
					ArrayList<UltimateAttack> foeUA=((Fighter)battle.getFoe()).getUltimateAttacks();
					
					for(UltimateAttack uA: foeUA)
						gainedUA+= uA.getName() + ", ";
					
					int oldLevel= battleView.getOldLevel();
					int oldAbilityPoints= battleView.getOldAbilityPoints();
					
					String text= "Congratulations! You won and completed the level!" + "\n" + 
								"Your current Xp points: " + ((PlayableFighter)(battle.getMe())).getXp() + "points" + "\n";
							
					if(foeSA.size()>0)
						text+= "Your learned SuperAttacks: " + gainedSA + "\n" ;
					
					if(foeUA.size()>0)
						text+="You learned UltimateAttacks: " + gainedUA + "\n";
					
					int newLevel= ((PlayableFighter)battleView.getBattle().getMe()).getLevel();
					int levelUp= newLevel-(battleView.getOldLevel());
					
					int newAbilityPoints= ((PlayableFighter)battleView.getBattle().getMe()).getAbilityPoints();
					int gainedAP= newAbilityPoints - (battleView.getOldAbilityPoints());
					
					if(newLevel>oldLevel)
						text+= "You leveled up " + levelUp + (levelUp>1? " levels":" level") + "\n";
								
					text+= "Your current Level: " + newLevel + "\n";
					
					if(gainedAP>0)
						text+= "You gained " + gainedAP + (gainedAP>1? " ability points":" ability point") + "\n";
					
					text+= "Your current abilityPoints: " + newAbilityPoints + "\n";
										
					
					JFrame frame= new JFrame("JOptionPane showMessageDialog example");
					JOptionPane.showMessageDialog(frame, text) ;
				}
				else
				{
					
					String gainedSA=  "";
					ArrayList<SuperAttack> foeSA= ((Fighter)battle.getFoe()).getSuperAttacks();
					
					for(SuperAttack sA: foeSA)
						gainedSA+= sA.getName() + ", ";
					
					String gainedUA= "";
					ArrayList<UltimateAttack> foeUA=((Fighter)battle.getFoe()).getUltimateAttacks();
					
					for(UltimateAttack uA: foeUA)
						gainedUA+= uA.getName() + ", ";
					
					int oldLevel= battleView.getOldLevel();
					int oldAbilityPoints= battleView.getOldAbilityPoints();
					
					String text= "Congratulations! You won!! let's explore the rest of the Map" + "\n" + 
								"Your current Xp points: " + ((PlayableFighter)(battle.getMe())).getXp() + "points" + "\n";
							
					if(foeSA.size()>0)
						text+= "Your learned SuperAttacks: " + gainedSA + "\n" ;
					
					if(foeUA.size()>0)
						text+="You learned UltimateAttacks: " + gainedUA + "\n";
					
					int newLevel= ((PlayableFighter)battleView.getBattle().getMe()).getLevel();
					int levelUp= newLevel-(battleView.getOldLevel());
					
					int newAbilityPoints= ((PlayableFighter)battleView.getBattle().getMe()).getAbilityPoints();
					int gainedAP= newAbilityPoints - (battleView.getOldAbilityPoints());
					
					if(newLevel>oldLevel)
						text+= "You leveled up " + levelUp + (levelUp>1? " levels":" level") + "\n";
								
					text+= "Your current Level: " + newLevel + "\n";
					
					if(gainedAP>0)
						text+= "You gained " + gainedAP + (gainedAP>1? " ability points":" ability point") + "\n";
					
					text+= "Your current abilityPoints: " + newAbilityPoints + "\n";
										
					
					JFrame frame= new JFrame("JOptionPane showMessageDialog example");
					JOptionPane.showMessageDialog(frame, text) ;
				}
				//System.out.println("I was here three");

			}
			else 
			{  
				//if(game.getLastSavedFile()!=null)
				//	game.load("Dragonball.ser");
				
			
				JFrame frame= new JFrame("JOptionPane showMessageDialog example");
				JOptionPane.showMessageDialog(frame,"You lost! Better Luck next time!");
				game.setListener(this);
				game.getWorld().resetPlayerPosition();
				this.switchToWorldView();
				worldView.repaint();
				worldView.revalidate();
				gameView.setContentPane(worldView);
				gameView.repaint();
				gameView.revalidate();
				
			/*	battleView.setVisible(false);
				game.getWorld().resetPlayerPosition();
				this.switchToWorldView();
				worldView.repaint();
				worldView.revalidate();
				gameView.setContentPane(worldView);
				gameView.repaint();
				gameView.revalidate();*/
			
				
				/*		try{
				game.load("Dragonball.ser");
				game.setListener(this);
				game.getWorld().resetPlayerPosition();
				this.switchToWorldView();
				worldView.repaint();
				worldView.revalidate();
				gameView.setContentPane(worldView);
				gameView.repaint();
				gameView.revalidate();
			}
			catch(FileNotFoundException e1)
			{
				World world = new World();
			//	Game game= new Game();
				world.generateMap(game.getWeakFoes(), game.getStrongFoes());
				world.setListener(game);
				worldView= new WorldView(game.getPlayer(), world);
				
				this.switchToWorldView();
				worldView.repaint();
				worldView.revalidate();
				game.setWorld(new World());
				game.getWorld().generateMap(game.getWeakFoes(), game.getStrongFoes());
				game.getWorld().setListener(game);
				
			}
			catch(IOException e1)
			{
				
			}
			catch(ClassNotFoundException e1)
			{
				
			}*/
			
				
						
			}			
		}
	}
	
	public void switchToBattleView(Battle battle){
		
		battleView=new BattleView(battle);
		battleView.getAttack().addActionListener(this);
		battleView.getBlock().addActionListener(this);
		battleView.getUse().addActionListener(this);
		battleView.getPhysicalAttack().addActionListener(this);
		battleView.getSuperAttack().addActionListener(this);
		battleView.getUltimateAttack().addActionListener(this);
		battleView.getAvailableUltimateAttacks().addActionListener(this);
		battleView.getAvailableSuperAttacks().addActionListener(this);
		battleView.getSubmitSA().addActionListener(this);
		battleView.getSubmitUA().addActionListener(this);
		
		battleView.repaint();
		battleView.revalidate();
		gameView.setContentPane(battleView);
	}
	public static void main(String[]args){
		new GameGUI();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		try 
		{
			if (key == KeyEvent.VK_LEFT) {
				worldView.getWorld().moveLeft();
				worldView.repaint();
				worldView.revalidate();
				worldView.paintPlayer();
				worldView.repaint();
				worldView.revalidate();
			}

			if (key == KeyEvent.VK_RIGHT) {
				worldView.getWorld().moveRight();
				worldView.repaint();
				worldView.revalidate();
				worldView.paintPlayer();
				worldView.repaint();
				worldView.revalidate();
			}

			if (key == KeyEvent.VK_UP) {
				worldView.getWorld().moveUp();
				worldView.repaint();
				worldView.revalidate();
				worldView.paintPlayer();
				worldView.repaint();
				worldView.revalidate();
			}
	    
			if (key == KeyEvent.VK_DOWN) {
				worldView.getWorld().moveDown();
				worldView.repaint();
				worldView.revalidate();
				worldView.paintPlayer();
				worldView.repaint();
				worldView.revalidate();
			}
		}
		catch (MapIndexOutOfBoundsException e1)
		{
			
		}
		
	}
	public void BattleUpdatePlayerInfo(){
		
		battleView.setPlayerInfo(new JPanel());
		battleView.getPlayerInfo().setOpaque(false);
		battleView.getPlayerInfo().setLayout(new BoxLayout(battleView.getPlayerInfo(), BoxLayout.PAGE_AXIS));
		
		battleView.setPlayerName(new JTextArea());
		battleView.getPlayerName().setVisible(true);
		battleView.getPlayerName().setOpaque(false);
		battleView.getPlayerName().setEditable(false);
		battleView.getPlayerName().setText(((Fighter)battleView.getBattle().getMe()).getName()+" Level: "+((Fighter)battleView.getBattle().getMe()).getLevel());
		battleView.getPlayerName().setFont(new Font("Lucida Calligraphy", Font.PLAIN, 40));
		battleView.getPlayerName().setForeground(Color.BLACK);
		battleView.getPlayerInfo().add(battleView.getPlayerName());
		battleView.getPlayerInfo().add(Box.createVerticalStrut(5));
		
		battleView.setMaxHealthPointsPlayer(new JPanel());
		battleView.getMaxHealthPointsPlayer().setOpaque(false);
		//GridLayout(1,1+(((Fighter)(battle.getMe())).getMaxHealthPoints())/50)
		battleView.getMaxHealthPointsPlayer().setLayout(new BoxLayout(battleView.getMaxHealthPointsPlayer(),BoxLayout.LINE_AXIS));
		JLabel empty = new JLabel("max HealthPoints: "+(((Fighter)(battleView.getBattle().getMe())).getMaxHealthPoints()));
		empty.setForeground(Color.BLACK);
		battleView.getMaxHealthPointsPlayer().add(empty);
		
//		empty = new JLabel("curr HealthPoints: "+(((Fighter)(battle.getMe())).getHealthPoints()));
//		empty.setForeground(Color.BLACK);
//		maxHealthPointsPlayer.add(empty);
		
		empty=new JLabel();
		BufferedImage image;
		try {
			image = ImageIO.read(new File("healthPoints.png"));
			ImageIcon icon = new ImageIcon(fitImageToACertainSize(20,20,image));
			empty.setPreferredSize(new Dimension(20,20));
			empty.setOpaque(false);
			empty.setIcon(icon);
			battleView.getMaxHealthPointsPlayer().add(empty);
			battleView.getMaxHealthPointsPlayer().add(Box.createHorizontalStrut(20));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			for(int i=0;i<((Fighter)(battleView.getBattle().getMe())).getHealthPoints()/50;i++)
			{
				empty=new JLabel();
				image = ImageIO.read(new File("green.png"));
				ImageIcon icon = new ImageIcon(fitImageToACertainSize(10,10,image));
				empty.setPreferredSize(new Dimension(10,10));
				empty.setIcon(icon);
				battleView.getMaxHealthPointsPlayer().add(empty);
				battleView.getMaxHealthPointsPlayer().add(Box.createHorizontalStrut(5));
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			for (int i =((Fighter)(battleView.getBattle().getMe())).getHealthPoints(); i<(((Fighter)(battleView.getBattle().getMe())).getMaxHealthPoints()); i+=50)
			{
				empty=new JLabel();
				image = ImageIO.read(new File("red.png"));
				ImageIcon icon = new ImageIcon(fitImageToACertainSize(10,10,image));
				empty.setPreferredSize(new Dimension(10,10));
				empty.setIcon(icon);
				battleView.getMaxHealthPointsPlayer().add(empty);
				battleView.getMaxHealthPointsPlayer().add(Box.createHorizontalStrut(5));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		battleView.getPlayerInfo().add(battleView.getMaxHealthPointsPlayer());
		
		battleView.setMaxKiPlayer(new JPanel());
		battleView.getMaxKiPlayer().setOpaque(false);
		battleView.getMaxKiPlayer().setLayout(new BoxLayout(battleView.getMaxKiPlayer(), BoxLayout.X_AXIS));
		empty = new JLabel("max Ki: "+(((Fighter)(battleView.getBattle().getMe())).getMaxKi()));
		empty.setForeground(Color.BLACK);
		battleView.getMaxKiPlayer().add(empty);
//		empty = new JLabel("curr Ki: "+(((Fighter)(battle.getMe())).getKi()));
//		empty.setForeground(Color.BLACK);
//		maxKiPlayer.add(empty);
		//maxKiPlayer.setBounds(10, 50, 20, 10*((Fighter)(battle.getMe())).getMaxKi());
		//new GridLayout(1,1+((Fighter)(battle.getMe())).getMaxKi())
		empty = new JLabel();
		try {
			image = ImageIO.read(new File("ki.jpg"));
			ImageIcon icon = new ImageIcon(fitImageToACertainSize(20,20,image));
			empty.setPreferredSize(new Dimension(20,20));
			empty.setIcon(icon);
			battleView.getMaxKiPlayer().add(empty);
			battleView.getMaxKiPlayer().add(Box.createHorizontalStrut(20));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
		//	System.out.println(((Fighter)(battleView.getBattle().getMe())).getKi());
			for(int i=0;i<((Fighter)(battleView.getBattle().getMe())).getKi();i++)
			{
				empty=new JLabel();
				image = ImageIO.read(new File("green.png"));
				ImageIcon icon = new ImageIcon(fitImageToACertainSize(10,10,image));
				empty.setPreferredSize(new Dimension(10,10));
				empty.setIcon(icon);
				battleView.getMaxKiPlayer().add(empty);
				battleView.getMaxKiPlayer().add(Box.createHorizontalStrut(5));
		//		System.out.println("Iwas insde the ki loop green");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			
			for (int i =((Fighter)(battleView.getBattle().getMe())).getKi(); i<(((Fighter)(battleView.getBattle().getMe())).getMaxKi()); i++)
			{
				empty=new JLabel();
				image = ImageIO.read(new File("red.png"));
				ImageIcon icon = new ImageIcon(fitImageToACertainSize(10,10,image));
				empty.setPreferredSize(new Dimension(10,10));
				empty.setIcon(icon);
				battleView.getMaxKiPlayer().add(empty);
				battleView.getMaxKiPlayer().add(Box.createHorizontalStrut(5));
			//	System.out.println("Iwas insde the ki loop red");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		battleView.getPlayerInfo().add(battleView.getMaxKiPlayer());
		
		battleView.setMaxStaminaPlayer(new JPanel());
		battleView.getMaxStaminaPlayer().setOpaque(false);
		battleView.getMaxStaminaPlayer().setLayout(new BoxLayout(battleView.getMaxStaminaPlayer(), BoxLayout.X_AXIS));
		empty = new JLabel("max Stamina: "+(((Fighter)(battleView.getBattle().getMe())).getMaxStamina()));
		empty.setForeground(Color.BLACK);
		battleView.getMaxStaminaPlayer().add(empty);
//		empty = new JLabel("curr Stamina: "+(((Fighter)(battle.getMe())).getStamina()));
//		empty.setForeground(Color.BLACK);
//		maxStaminaPlayer.add(empty);
		//new GridLayout(1,1+((Fighter)(battle.getMe())).getMaxStamina())
		empty = new JLabel();
		try {
			image = ImageIO.read(new File("stamina.jpg"));
			ImageIcon icon = new ImageIcon(fitImageToACertainSize(20,20,image));
			empty.setPreferredSize(new Dimension(20,20));
			empty.setIcon(icon);
			battleView.getMaxStaminaPlayer().add(empty);
			battleView.getMaxStaminaPlayer().add(Box.createHorizontalStrut(20));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			for(int i=0;i<((Fighter)(battleView.getBattle().getMe())).getStamina();i++)
			{
				empty=new JLabel();
				image = ImageIO.read(new File("green.png"));
				ImageIcon icon = new ImageIcon(fitImageToACertainSize(10,10,image));
				empty.setPreferredSize(new Dimension(10,10));
				empty.setIcon(icon);
				battleView.getMaxStaminaPlayer().add(empty);
				battleView.getMaxStaminaPlayer().add(Box.createHorizontalStrut(5));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			for (int i =((Fighter)(battleView.getBattle().getMe())).getStamina(); i<(((Fighter)(battleView.getBattle().getMe())).getMaxStamina()); i++)
			{
				empty=new JLabel();
				image = ImageIO.read(new File("red.png"));
				ImageIcon icon = new ImageIcon(fitImageToACertainSize(10,10,image));
				empty.setPreferredSize(new Dimension(10,10));
				empty.setIcon(icon);
				battleView.getMaxStaminaPlayer().add(empty);
				battleView.getMaxStaminaPlayer().add(Box.createHorizontalStrut(5));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		battleView.getPlayerInfo().add(battleView.getMaxStaminaPlayer());
		battleView.getFightersInfo().add(battleView.getPlayerInfo(), BorderLayout.WEST);
	//	System.out.println("I made it to here");
	}
	public void updateFoeInfo(){
		
		battleView.setFoeInfo(new JPanel());
		battleView.getFoeInfo().setOpaque(false);
		battleView.getFoeInfo().setLayout(new BoxLayout(battleView.getFoeInfo(), BoxLayout.PAGE_AXIS));
		
		battleView.setFoeName(new JTextArea());
		battleView.getFoeName().setVisible(true);
		battleView.getFoeName().setOpaque(false);
		battleView.getFoeName().setEditable(false);
		battleView.getFoeName().setText(((Fighter)battleView.getBattle().getFoe()).getName()+" Level: "+((Fighter)battleView.getBattle().getFoe()).getLevel());
		battleView.getFoeName().setFont(new Font("Lucida Calligraphy", Font.PLAIN, 40));
		battleView.getFoeName().setForeground(Color.WHITE);
		battleView.getFoeInfo().add(battleView.getFoeName());
		battleView.getFoeInfo().add(Box.createVerticalStrut(5));
		
		battleView.setMaxHealthPointsFoe(new JPanel());
		battleView.getMaxHealthPointsFoe().setOpaque(false);
		battleView.getMaxHealthPointsFoe().setLayout(new BoxLayout(battleView.getMaxHealthPointsFoe(),BoxLayout.LINE_AXIS));		
		JLabel empty = new JLabel("max HealthPoints: "+(((Fighter)(battleView.getBattle().getFoe())).getMaxHealthPoints()));
		empty.setForeground(Color.WHITE);
		battleView.getMaxHealthPointsFoe().add(empty);
//		empty = new JLabel("curr HealthPoints: "+(((Fighter)(battle.getFoe())).getHealthPoints()));
//		empty.setForeground(Color.WHITE);
//		battleView.getMaxHealthPointsFoe().add(empty);
		empty = new JLabel();
		BufferedImage image;
		empty = new JLabel();
		try {
			image = ImageIO.read(new File("healthPoints.png"));
			ImageIcon icon = new ImageIcon(fitImageToACertainSize(20,20,image));
			empty.setPreferredSize(new Dimension(20,20));
			empty.setOpaque(false);
			empty.setIcon(icon);
			battleView.getMaxHealthPointsFoe().add(empty);
			battleView.getMaxHealthPointsFoe().add(Box.createHorizontalStrut(20));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		empty=new JLabel();
		try {
			
			for(int i=0;i<(((Fighter)(battleView.getBattle().getFoe())).getHealthPoints())/50;i++)
			{
				empty=new JLabel();
				image = ImageIO.read(new File("green.png"));
				ImageIcon icon = new ImageIcon(fitImageToACertainSize(10,10,image));
				empty.setPreferredSize(new Dimension(10,10));
				empty.setIcon(icon);
				battleView.getMaxHealthPointsFoe().add(empty);
				battleView.getMaxHealthPointsFoe().add(Box.createHorizontalStrut(5));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			
			for (int i =((Fighter)(battleView.getBattle().getFoe())).getHealthPoints(); i<(((Fighter)(battleView.getBattle().getFoe())).getMaxHealthPoints()); i+=50)
			{
				empty=new JLabel();
				image = ImageIO.read(new File("red.png"));
				ImageIcon icon = new ImageIcon(fitImageToACertainSize(10,10,image));
				empty.setPreferredSize(new Dimension(10,10));
				empty.setIcon(icon);
				battleView.getMaxHealthPointsFoe().add(empty);
				battleView.getMaxHealthPointsFoe().add(Box.createHorizontalStrut(5));
			}
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		battleView.getFoeInfo().add(battleView.getMaxHealthPointsFoe());
		
		battleView.setMaxKiFoe(new JPanel());
		battleView.getMaxKiFoe().setOpaque(false);
		battleView.getMaxKiFoe().setLayout(new BoxLayout(battleView.getMaxKiFoe(),BoxLayout.X_AXIS));
		empty = new JLabel("max Ki: "+(((Fighter)(battleView.getBattle().getFoe())).getMaxKi()));
		empty.setForeground(Color.WHITE);
		battleView.getMaxKiFoe().add(empty);
//		empty = new JLabel("curr Ki: "+(((Fighter)(battle.getFoe())).getKi()));
//		empty.setForeground(Color.WHITE);
//		maxKiFoe.add(empty);
		empty = new JLabel();
		try {
			image = ImageIO.read(new File("ki.jpg"));
			ImageIcon icon = new ImageIcon(fitImageToACertainSize(20,20,image));
			empty.setPreferredSize(new Dimension(20,20));
			empty.setIcon(icon);
			battleView.getMaxKiFoe().add(empty);
			battleView.getMaxKiFoe().add(Box.createHorizontalStrut(20));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		empty=new JLabel();
		try {
			
			for(int i=0;i<((Fighter)(battleView.getBattle().getFoe())).getKi();i++)
			{
				empty = new JLabel();
				image = ImageIO.read(new File("green.png"));
				ImageIcon icon = new ImageIcon(fitImageToACertainSize(10,10,image));
				empty.setPreferredSize(new Dimension(10,10));
				empty.setIcon(icon);
				battleView.getMaxKiFoe().add(empty);
				battleView.getMaxKiFoe().add(Box.createHorizontalStrut(5));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			
			for (int i =((Fighter)(battleView.getBattle().getFoe())).getKi(); i<(((Fighter)(battleView.getBattle().getFoe())).getMaxKi()); i++)
			{
				empty = new JLabel();
				image = ImageIO.read(new File("red.png"));
				ImageIcon icon = new ImageIcon(fitImageToACertainSize(10,10,image));
				empty.setPreferredSize(new Dimension(10,10));
				empty.setIcon(icon);
				battleView.getMaxKiFoe().add(empty);
				battleView.getMaxKiFoe().add(Box.createHorizontalStrut(5));
			}
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		battleView.getFoeInfo().add(battleView.getMaxKiFoe());
		

		battleView.setMaxStaminaFoe(new JPanel());
		battleView.getMaxStaminaFoe().setOpaque(false);
		battleView.getMaxStaminaFoe().setLayout(new BoxLayout(battleView.getMaxStaminaFoe(),BoxLayout.LINE_AXIS));
		empty = new JLabel("max Stamina: "+(((Fighter)(battleView.getBattle().getFoe())).getMaxStamina()));
		empty.setForeground(Color.WHITE);
		battleView.getMaxStaminaFoe().add(empty);
//		empty = new JLabel("curr Stamina: "+(((Fighter)(battle.getFoe())).getStamina()));
//		empty.setForeground(Color.WHITE);
//		battleView.getMaxStaminaFoe().add(empty);
		empty = new JLabel();
		try {
			image = ImageIO.read(new File("stamina.jpg"));
			ImageIcon icon = new ImageIcon(fitImageToACertainSize(20,20,image));
			empty.setPreferredSize(new Dimension(20,20));
			empty.setIcon(icon);
			battleView.getMaxStaminaFoe().add(empty);
			battleView.getMaxStaminaFoe().add(Box.createHorizontalStrut(20));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		empty=new JLabel();
		try {
			for(int i=0;i<((Fighter)(battleView.getBattle().getFoe())).getStamina();i++)
			{
				empty=new JLabel();
				image = ImageIO.read(new File("green.png"));
				ImageIcon icon = new ImageIcon(fitImageToACertainSize(10,10,image));
				empty.setPreferredSize(new Dimension(10,10));
				empty.setIcon(icon);
				battleView.getMaxStaminaFoe().add(empty);
				battleView.getMaxStaminaFoe().add(Box.createHorizontalStrut(5));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			
			for (int i =((Fighter)(battleView.getBattle().getFoe())).getStamina(); i<(((Fighter)(battleView.getBattle().getFoe())).getMaxStamina()); i++)
			{
				empty = new JLabel();
				image = ImageIO.read(new File("red.png"));
				ImageIcon icon = new ImageIcon(fitImageToACertainSize(10,10,image));
				empty.setPreferredSize(new Dimension(10,10));
				empty.setIcon(icon);
				battleView.getMaxStaminaFoe().add(empty);
				battleView.getMaxStaminaFoe().add(Box.createHorizontalStrut(5));
			}
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		battleView.getFoeInfo().add(battleView.getMaxStaminaFoe());
//		if(((Fighter)(battleView.getBattle().getFoe())) instanceof Saiyan)
//		{
//			if(((Saiyan)(battleView.getBattle().getFoe())).isTransformed())
//			{
//				ImageIcon icon = new ImageIcon("superSaiyan.gif");
//				Image imageS=(icon.getImage()).getScaledInstance(300, 600, Image.SCALE_FAST);
//				ImageIcon iconWithImage=new ImageIcon(imageS);
//				battleView.getFoeGif().setPreferredSize(new Dimension(300,600));
//				battleView.getFoeGif().setIcon(iconWithImage);
//			}
//		}
		battleView.getFightersInfo().add(battleView.getFoeInfo(),BorderLayout.EAST);
	}
	public void updateFighters() {

		battleView.setFightersInfo(new JPanel());
		battleView.getFightersInfo().setLayout(new BorderLayout());
		battleView.getFightersInfo().setOpaque(false);
		battleView.getFightersInfo().setPreferredSize(new Dimension(500,200));
		battleView.getFightersInfo().setSize(battleView.getFightersInfo().getPreferredSize());
		
		this.BattleUpdatePlayerInfo();

		this.updateFoeInfo();
		
		//we should add here playerInfo components(Name, level, HP, KI, Stamina)
		battleView.add(battleView.getFightersInfo(), BorderLayout.NORTH);
		battleView.getFightersInfo().repaint();
		battleView.getFightersInfo().validate();
	}
	public Image fitImageToACertainSize(int width, int height, Image oldImage)
	{
		BufferedImage newImage=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics=newImage.createGraphics();
		graphics.drawImage(oldImage, 0, 0, width, height, null);
		graphics.dispose();
		return newImage;
	}
//   Cell playerCell= game.getWorld().getMap()[game.getWorld().getPlayerRow()][game.getWorld().getPlayerColumn()];
// if(!(playerCell instanceof CollectibleCell && ((CollectibleCell)playerCell).getCollectible()==Collectible.SENZU_BEAN))
// 	worldView.getComment().setText("");
  
//  worldView.repaint();
//  worldView.revalidate();
	
}

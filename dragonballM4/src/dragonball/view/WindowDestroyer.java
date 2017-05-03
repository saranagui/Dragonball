package dragonball.view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;

import javax.swing.JOptionPane;

public class WindowDestroyer extends WindowAdapter implements Serializable {
	
	public void windowClosing(WindowEvent w) {
		String closingOptions[] = {"Yes","No"};
	    int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to quit the Game? We will miss you!", "Quit!", 
	        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,closingOptions,closingOptions[1]);
	    if(PromptResult==0)
	    {
	      System.exit(0);          
	    }
    }

}

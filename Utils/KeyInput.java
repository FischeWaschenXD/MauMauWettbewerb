package Utils;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import GUI.Consol;

public class KeyInput extends KeyAdapter{
	Consol con;
	
	public KeyInput(Consol con) {
		this.con = con;
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		con.sendPressed();
	}
}

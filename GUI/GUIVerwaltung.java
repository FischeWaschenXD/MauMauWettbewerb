package GUI;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import Utils.Cards;
import Utils.GUIWindow;
import default2.Spiel;
import default2.SpielNeu;
import default2.SpielerNeu;

public class GUIVerwaltung {
	GUIWindow window;
	Game gui;
	Consol con;
	
	boolean init = false;
	
//	public static void main(String[] argv) {
//		String[] arr = {"Tom", "Peter"};
//		new Spiel(2, 1, arr);
//	}
	
	public GUIVerwaltung() {
		gui = new Game(this);
		con = new Consol(this);
		window = con;
	}
	
	public void eingabeMethodeAendern(boolean consol) {
		window.hide();
		window = (GUIWindow) ((consol) ? con : gui);
		window.show();
	}
	
	public void spielerAendern(int spieler) {
		con.spielerWechseln(spieler);
		gui.spielerWechseln(spieler);
	}
	
	public void komplettNeuesSpiel(int spielerZahl, String mcName, String[] names, boolean[] ki) {
		new SpielNeu(spielerZahl, mcName, names, ki);
		refresh();
	}
	
	public void neuesSpiel() {
		init = true;
		SpielNeu.spiel.newGame();
	}
	
	public ArrayList<Cards> getHand(int spieler) {
		return SpielNeu.spiel.getSpielerListe()[spieler].getHandKarten();
	}
	
	public Cards getTopKarte() {
		return SpielNeu.spiel.getTop();
	}
	
	public void gewonnen(SpielerNeu[] spieler) {
		String[] names = new String[spieler.length];
		int[] punkte = new int[spieler.length];
		for(int i = 0; i < spieler.length; i++) {
			names[i] = spieler[i].getName();
			punkte[i] = spieler[i].berechneScore();
		}
		con.displayResult(names, punkte);
		if(window instanceof Game) gui.displayResult(names, punkte);
	}
	
	public int amZug() {
//		while(!init) {
//			try {
//				TimeUnit.MICROSECONDS.sleep(100);
//			}catch(InterruptedException e) {}
//		}
		return window.amZug();
	}
	
	public String farbeWuenschen() {
		return window.farbeWuenschen();
	}
	
	public void refresh() {
		gui.ablageStapelAendern(SpielNeu.spiel.getTop());
		gui.spielerWechseln(SpielNeu.spiel.getAktuellerSpieler());
	}
	
	public void informieren(String s) {
		if(con == null) return;
		
		con.print(s + "\n");
		gui.addLog(s, true);
	}
	
	public void informiere(String s) {
		if(init == false) return;
		if(con == null) return;
		
		con.print(s);
		gui.addLog(s);
	}
}

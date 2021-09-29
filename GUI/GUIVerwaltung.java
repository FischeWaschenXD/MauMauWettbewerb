package GUI;

import Utils.GUIWindow;
import default2.Karte;
import default2.MyArrayList;
import default2.Spiel;
import default2.Spieler;

public class GUIVerwaltung {
	GUIWindow window;
	Game gui;
	Consol con;
	
	public static void main(String[] argv) {
		GUIVerwaltung test = new GUIVerwaltung();
	}
	
	public GUIVerwaltung() {
		gui = new Game(this);
		con = new Consol(this);
	}
	
	public void eingabeMethodeAendern(boolean consol) {
		window.hide();
		window = (GUIWindow) ((consol) ? con : gui);
		window.show();
	}
	
	public void spielerAendern(int spieler) {
		window.spielerWechseln(spieler);
	}
	
	public void komplettNeuesSpiel(int spielerZahl, String mcName, String[] names, boolean[] ki) {
		int anzahlCom = 0;
		String[] namen = new String[spielerZahl];
		
		
		new Spiel(spielerZahl, anzahlCom);
	}
	
	public void neuesSpiel() {
		Spiel.spiel.newGame();
	}
	
	public MyArrayList<Karte> getHand(int spieler) {
		return Spiel.spiel.getSpielerListe()[spieler].getHandKarten();
	}
	
	public Karte getTopKarte() {
		return Spiel.spiel.getAblageStapel().get(Spiel.spiel.getAblageStapel().size() - 1);
	}
	
	public void gewonnen(Spieler[] spieler) {
		String[] names = new String[spieler.length];
		int[] punkte = new int[spieler.length];
		for(int i = 0; i < spieler.length; i++) {
			names[i] = spieler[i].getName();
			punkte[i] = spieler[i].berechnePunkte();
		}
		window.displayResult(names, punkte);
	}
	
	public int amZug() {
		return window.amZug();
	}
	
	public String farbeWuenschen() {
		return window.farbeWuenschen();
	}
	
	public void refresh() {
		//TODO alles auf neues Spiel vorbereiten
	}
}

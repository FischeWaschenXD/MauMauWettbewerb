package GUI;

import java.util.ArrayList;

import Utils.GUIWindow;
import default2.Karte;

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
		//TODO neues Spiel mit neuen Spielern
	}
	
	public void neuesSpiel() {
		//TODO neuesSpiel mit gleichen Spielern
	}
	
	public ArrayList<Karte> getHand(int spieler) {
		//TODO holt sich die hand von Spieler von 0-3
		ArrayList<Karte> list = new ArrayList<Karte>();
		list.add(new Karte(7, "Pik"));
		list.add(new Karte(8, "Pik"));
		list.add(new Karte(9, "Pik"));
		list.add(new Karte(10, "Pik"));
		list.add(new Karte(11, "Pik"));
		list.add(new Karte(12, "Pik"));
		list.add(new Karte(13, "Pik"));
		list.add(new Karte(14, "Pik"));
		return list;
	}
	
	public Karte getTopKarte() {
		//TODO oberste gelegte Karte returnen
		return new Karte(8, "Pik");
	}
}

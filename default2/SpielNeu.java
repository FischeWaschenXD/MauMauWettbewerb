package default2;

import java.util.ArrayList;
import java.util.Random;

import GUI.GUIVerwaltung;
import Utils.Cards;

public class SpielNeu {
	
	public static void main(String[] argv) {
		String[] names = {"Franz"};
		boolean[] kis = {true};
		new SpielNeu(2, "Peter", names, kis);
	}
	
	public static SpielNeu spiel;
	public static GUIVerwaltung verwaltung;
	
	private SpielerNeu[] playerList;
	private int spielerAmZug;
	private boolean aussetzen;
	private boolean aufsteigend;
	private int ziehRunden = 0;
	
	private Cards top;
	private ArrayList<Cards> ziehStapel = new ArrayList<Cards>();
	
	private String farbWunsch = "Karo";
	
	public SpielNeu(int spielerZahl, String mcName, String[] names, boolean[] ki) {
		spiel = this;
		if(verwaltung == null) verwaltung = new GUIVerwaltung();
		
		initPlayerList(spielerZahl, mcName, names, ki);
		
		initZiehStapel();
		
		kartenAusteilen();
		
		spielStarten();
	}
	
	private void spielStarten() {
		top = ziehStapel.remove(0);
		if(top.nummer == 9) {
			richtungWechseln();
		}
		verwaltung.spielerAendern(spielerAmZug);
		while(sieg() == null) {
			verwaltung.refresh();
			
			playerList[spielerAmZug].zug();
		}
		
		verwaltung.gewonnen(sieg());
	}
	
	protected void initPlayerList(int spielerZahl, String mcName, String[] names, boolean[] ki) {
		playerList = new SpielerNeu[spielerZahl];
		playerList[0] = new SpielerNeu(0, mcName, false);
		
		for(int i = 1; i < spielerZahl; i++) {
			playerList[i] = new SpielerNeu(i, names[i - 1], ki[i - 1]);
		}
		
	}
	
	private void kartenAusteilen() {
		for(int i = 0; i < 5; i++) {
			for(SpielerNeu spieler : playerList) {
				spieler.karteHinzufuegen(ziehStapel.remove(0));
			}
		}
	}
	
	private void initZiehStapel() {
		for(Cards card : Cards.values()) {
			if(card.nummer == 0) continue;
			einmischen(card);
		}
	}
	
	public boolean zug(Cards card) {
		if(card == null) {
			karteZiehen(); 
			return false;
		}
		if(top.nummer != 11) {
			if(!top.fits(card)) {
				return false;
			}
		}else {
			if(!Cards.valueOf(farbWunsch.toUpperCase() + "_" + top.nummer).fits(card)) {
				return false;
			}
		}
		if(ziehRunden > 0 && card.nummer != 7) {
			siebenenZiehen();
			ziehRunden = 0;
		}
		if(card.nummer == 8) {
			aussetzen = true;
		}
		
		if(card.nummer == 9) {
			richtungWechseln();
		}
		
		if(card.nummer == 11) {
			farbWunsch = verwaltung.farbeWuenschen();
		}
		
		einmischen(top);
		top = card;
		naechsterSpieler();
		return true;
	}
	
	private void siebenenZiehen() {
		for(int i = 0; i < ziehRunden; i++) {
			karteZiehen();
			karteZiehen();
		}
	}
	
	private void karteZiehen() {
		playerList[spielerAmZug].karteHinzufuegen(ziehStapel.remove(0));
	}
	
	public void naechsterSpieler() {
		if(aufsteigend) {
			spielerAmZug++;
			if(aussetzen) {
				spielerAmZug++;
				aussetzen = false;
			}
		}else {
			spielerAmZug--;
			if(aussetzen) {
				spielerAmZug--;
				aussetzen = false;
			}
		}
		if(spielerAmZug < 0) {
			spielerAmZug += playerList.length;
		}
		spielerAmZug %= playerList.length;
		verwaltung.spielerAendern(spielerAmZug);
	}
	
	private void einmischen(Cards card) {
		Random rand = new Random();
		if(ziehStapel.size() <= 0) {
			ziehStapel.add(card);
			return;
		}
		int randInt = rand.nextInt(ziehStapel.size());
		ziehStapel.add(randInt, card);
	}
	
	public SpielerNeu[] sieg() {
		ArrayList<SpielerNeu> players = new ArrayList<SpielerNeu>();
		int kartenImSpiel = 0;
		for(SpielerNeu spieler : playerList) {
			kartenImSpiel += spieler.getHandKarten().size();
		}
		players.add(playerList[0]);
		for(int i = 1; i < playerList.length; i++) {
			for(int j = 0; j < players.size(); j++) {
				if(players.get(j).berechneScore() > playerList[i].berechneScore()) {
					players.add(j, playerList[i]);
					break;
				}
				if(j + 1 >= players.size()) {
					players.add(playerList[i]);
					break;
				}
			}
		}
		if(players.get(0).getHandKarten().size() == 0 || kartenImSpiel >= 29) {
			return (SpielerNeu[]) players.toArray();
		}else {
			return null;
		}
	}
	
	protected void richtungWechseln() {
		aufsteigend = !aufsteigend;
	}
	
	public void newGame() {
		String[] names = new String[playerList.length - 1];
		boolean[] ki = new boolean[playerList.length - 1];
		for(int i = 1; i < playerList.length; i++) {
			names[i - 1] = playerList[i].getName();
			ki[i - 1] = playerList[i].isKI();
		}
		new SpielNeu(playerList.length, playerList[0].getName(), names, ki);
		verwaltung.refresh();
	}
	
	public SpielerNeu[] getSpielerListe() {
		return playerList;
	}
	
	public Cards getTop() {
		return top;
	}
	
	public int getAktuellerSpieler() {
		return spielerAmZug;
	}
}

package default2;

import java.util.ArrayList;

import Utils.Cards;

public class SpielerNeu {
	protected int nummer;
	protected String name;
	protected ArrayList<Cards> handKarten = new ArrayList<Cards>();
	
	private boolean ki;
	
	public SpielerNeu(int n, String name, boolean ki) {
		nummer = n;
		this.name = name;
		this.ki = ki;
	}
	
	protected void zug() {
		Cards top = SpielNeu.spiel.getTop();
		int karte;
		do {
			SpielNeu.verwaltung.informieren("Bitte Lege eine Karte.");
			karte = SpielNeu.verwaltung.amZug();
			System.out.println(karte);
			if(karte < 0 || karte >= handKarten.size()) {
				karte = -1;
			}
			System.out.println(karte);
		}while(karte == -1 || !top.fits(handKarten.get(karte - 1)));
		if(!SpielNeu.spiel.zug((karte == 0) ? null : handKarten.get(karte - 1))) {
			do {
				SpielNeu.verwaltung.informieren("Bitte Lege eine Karte.");
				karte = SpielNeu.verwaltung.amZug();
				System.out.println(karte);
				if(karte < 0 || karte >= handKarten.size()) {
					karte = -1;
				}
				System.out.println(karte);
			}while(karte == -1 || !top.fits(handKarten.get(karte - 1)));
			if(karte == 0) {
				SpielNeu.spiel.naechsterSpieler();
				return;
			}
			if(SpielNeu.spiel.zug(handKarten.get(karte - 1))) {
				handKarten.remove(karte - 1);
			}
		}else {
			handKarten.remove(karte - 1);
		}
	}
	
	protected void karteHinzufuegen(Cards card) {
		handKarten.add(card);
	}
	
	public int berechneScore() {
		int score = 0;
		for(Cards card : handKarten) {
			score += card.punkte();
		}
		if(SpielNeu.spiel.getTop().nummer == 11) {
			score *= 2;
		}
		return score;
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<Cards> getHandKarten() {
		return handKarten;
	}
	
	public boolean isKI() {
		return ki;
	}
}

package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Utils.SpielCallBack;
import Utils.StartCallBack;
import Utils.Card;
import Utils.CardButton;
import Utils.CardPanel;
import Utils.Cards;

public class Game {
	private JFrame frame;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private CardPanel headsUpCard;
	private JTextArea ausgabeFeld;
	private SpielCallBack callBack;
	
	JScrollPane handKartenScroll;
	private JPanel[] handKarten;
	
	private JLabel playerNow;
	private JLabel playerNumber;
	
	
	public Game(SpielCallBack callBack) {
		this.callBack = callBack;
		
		frame = new JFrame("Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(screenSize);
		frame.setMinimumSize(new Dimension(400, 400));
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
		
		StartWindow start = new StartWindow(new StartCallBack() {
			
			@Override
			public void starten(int spielerZahl, String mcName, String[] names, boolean[] ki) {
				postInit(spielerZahl, mcName, names, ki);
				frame.setVisible(true);
				
				karteHinzufuegen(Cards.HERZ_12, 0);
			}
		});
		
		preInit();
	}
	
	private void neueHand() {
		for(int i = 0; i < handKarten.length; i++) {
			handKarten[i] = new JPanel();
			handKarten[i].setLayout(new BoxLayout(handKarten[i], BoxLayout.X_AXIS));
		}
	}
	
	private void preInit() {
		Dimension playFieldSize = new Dimension(screenSize.width / 5 * 4, screenSize.height);
		
		//Text Ausgabe
		ausgabeFeld = new JTextArea();
		JScrollPane ausgabePanel = new JScrollPane(ausgabeFeld);
		ausgabeFeld.setEditable(false);
		ausgabeFeld.setWrapStyleWord(true);
		ausgabeFeld.setLineWrap(true);
		
		
		
		JPanel playField = new JPanel();
		playField.setPreferredSize(playFieldSize);
		
		
		JPanel infoLeiste = new JPanel();
		playerNumber = new JLabel("Spieler Anzahl: 0");
		playerNow = new JLabel("Aktueller Spieler: PJ");
		
		infoLeiste.add(playerNumber);
		infoLeiste.add(Box.createRigidArea(new Dimension(20, 20)));
		infoLeiste.add(playerNow);
		
		playField.add(infoLeiste);
		
		
		JPanel bigCards = new JPanel();
		CardButton headsDownCard = new CardButton();
		headsDownCard.display(Cards.CARD_BACK, true);
		headsUpCard = new CardPanel();
		headsUpCard.display(Cards.CARD_BACK, true);
		
		bigCards.add(Box.createRigidArea(new Dimension((int) (screenSize.getWidth() / 5 * 4 / 2 - Card.getImage(Cards.CARD_BACK, true).getWidth()), 10)));
		bigCards.add(headsDownCard);
		bigCards.add(Box.createRigidArea(new Dimension(10, 10)));
		bigCards.add(headsUpCard);
		bigCards.add(Box.createRigidArea(new Dimension((int) (screenSize.getWidth() / 5 * 4 / 2 - Card.getImage(Cards.CARD_BACK, true).getWidth()), 10)));
		
		playField.add(bigCards);
		
		
		handKartenScroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		handKartenScroll.setPreferredSize(new Dimension(playFieldSize.width, Card.getImage(Cards.CARD_BACK).getHeight() + 20));
		
		
		playField.add(handKartenScroll);
		
		
		
		frame.add(ausgabePanel, BorderLayout.CENTER);
		frame.add(playField, BorderLayout.EAST);
	}
	
	private void postInit(int spielerZahl, String mcName, String[] names, boolean[] ki) {
		int nichtKISpieler = 1;
		for(int i = 0; i < spielerZahl - 1; i++) {
			if(!ki[i]) {
				nichtKISpieler++;
			}
		}
		handKarten = new JPanel[nichtKISpieler];
		System.out.println(nichtKISpieler);
		neueHand();
		handKartenScroll.setViewportView(handKarten[0]);
		playerNumber.setText("Spieler Anzahl: " + spielerZahl);
	}
	
	/**
	 * fügt einer Spieler Hand eine Karte Hinzu
	 * @param card hinzuzufügende Karte
	 * @param player der Spieler
	 */
	public void karteHinzufuegen(Cards card, int player) {
		CardButton karte = new CardButton();
		karte.display(card);
		handKarten[player].add(karte);
		handKarten[player].repaint();
	}
	
	
	public void karteEntfernen(JButton button, int player) {
		handKarten[player].remove(button);
		handKarten[player].repaint();
	}
	
	public void ablageStapelAendern(Cards card) {
		headsUpCard.display(card);
	}
	
	public void ablageStapelAendern(CardButton button) {
		ablageStapelAendern(button.getCard());
	}
	
	/**
	 * fügt den Text ans ende des ausgabeFelds an ohne Zeilenumbruch
	 * @param text der anzufügende Text
	 */
	public void addLog(String text) {
		ausgabeFeld.append(text + "\n");
	}
	
	/**
	 * fügt den Text ans ende des ausgabeFelds
	 * @param text der anzufügende Text
	 * @param zeilenumbruch spezifiziert ob ein Zeilenumbruch nach der Zeile passieren soll
	 */
	public void addLog(String text, boolean zeilenumbruch) {
		ausgabeFeld.append(text + ((zeilenumbruch) ? "\n" : ""));
	}
	
	public void changeCurrentPlayer(int player) {
		//TODO implementation
	}
	
	/**
	 * Zeigt ein DialogFenster an das die Punkte aller Teilnehmner anzeigt
	 * names[0] ist der Nickname der Person mit score[0] punkten...
	 * die Arrays müssen sortiert sein gewinner an Position 0 verlierer an letzter position
	 * @param names die namen aller Spieler
	 * @param score die Punkte aller Spieler
	 */
	private int displayResult(String[] names, int[] score) {
		Object[] options = {"Yes Daddy", "Yes but new Round", "I gotta go now"};
		
		StringBuilder sb = new StringBuilder();
		sb.append("Herzlichen Glückwunsch " + names[0] + " du hast das Spiel mit " + score[0] + " Punkten Gewonnen!\n");
		for(int i = 2; i <= score.length; i++) {
			sb.append("Herzlichen Glückwunsch " + names[i - 1] + " du bist " + i + ". mit " + score[i - 1] + " Punkten.\n");
		}
		sb.append("\nMüchtest du nochmal Spielen?");
		return JOptionPane.showOptionDialog(null, sb.toString(), "Das Spiel ist zuende", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	}
	
	//TODO spielerHinzufügen Methode
	//TODO mehere Hände Hinzufügen
	//TODO nextPlayer Methode
}


//TODO Confirm Window das der Nächste dran ist
//TODO Switch zwischen GUI und Konsole
//TODO Spielstand Speichern









package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Utils.StartCallBack;
import default2.Karte;
import Utils.Card;
import Utils.CardButton;
import Utils.CardPanel;
import Utils.Cards;
import Utils.GUIWindow;

public class Game extends GUIWindow {
	private JFrame frame;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	private CardPanel headsUpCard;
	private JTextArea ausgabeFeld;
	
	private JScrollPane handKartenScroll;
	private JPanel[] handKarten;
	
	private JLabel playerNow;
	private JLabel playerNumber;
	
	private GUIVerwaltung verwaltung;
	
	public Game(GUIVerwaltung verwaltung) {
		this.verwaltung = verwaltung;
		
		frame = new JFrame("Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(screenSize);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
		
		StartWindow start = new StartWindow(new StartCallBack() {
			
			@Override
			public void starten(int spielerZahl, String mcName, String[] names, boolean[] ki) {
				postInit(spielerZahl, mcName, names, ki);
				frame.setVisible(true);
				
				karteHinzufuegen(Cards.HERZ_12, 0);
				karteHinzufuegen(Cards.HERZ_9, 0);
				karteHinzufuegen(Cards.HERZ_11, 0);
				karteHinzufuegen(Cards.HERZ_8, 0);
				karteHinzufuegen(Cards.HERZ_10, 0);
				karteHinzufuegen(Cards.HERZ_7, 0);
				karteHinzufuegen(Cards.HERZ_14, 0);
				karteHinzufuegen(Cards.PIK_10, 0);
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
		
		JMenuBar menuBar = new JMenuBar();
		JMenuItem item = new JMenuItem("Zu Konsole wechseln");
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				verwaltung.eingabeMethodeAendern(true);
			}
		});
		menuBar.add(item);
		
		
		frame.setJMenuBar(menuBar);
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
		neueHand();
		handKartenScroll.setViewportView(handKarten[0]);
		playerNumber.setText("Spieler Anzahl: " + spielerZahl);
		playerNow.setText("Aktueller Spieler: " + mcName);
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
	@Override
	public void spielerWechseln(int player) {
		updateHand(player);
		String[] options = {"OK"};
		handKartenScroll.removeAll(); //TODO das macht immer alles kaputt also kp ob das funktioniert sonst remove index nutzen
		handKartenScroll.repaint();
		frame.setVisible(true);
		JOptionPane.showOptionDialog(null, "Nun ist Spieler ... dran", "Näachster Spieler", JOptionPane.PLAIN_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		//TODO spielernamen hinzufügen
		handKartenScroll.setViewportView(handKarten[player]);
	}
	
	private void updateHand(int spieler) {
		ArrayList<Karte> hand = verwaltung.getHand(spieler);
		handKarten[spieler] = new JPanel();
		handKarten[spieler].setLayout(new BoxLayout(handKarten[spieler], BoxLayout.X_AXIS));
		for(Karte karte : hand) {
			CardButton card = new CardButton();
			card.display(Cards.valueOf(karte.getTyp().toUpperCase() + "_" + karte.getZahl()));
			handKarten[spieler].add(card);
		}
	}
	
	/**
	 * Zeigt ein DialogFenster an das die Punkte aller Teilnehmner anzeigt
	 * names[0] ist der Nickname der Person mit score[0] punkten...
	 * die Arrays müssen sortiert sein gewinner an Position 0 verlierer an letzter position
	 * @param names die namen aller Spieler
	 * @param score die Punkte aller Spieler
	 */
	@Override
	public int displayResult(String[] names, int[] score) {
		Object[] options = {"Yes Daddy", "Yes but new Round", "I gotta go now"};
		
		StringBuilder sb = new StringBuilder();
		sb.append("Herzlichen Glückwunsch " + names[0] + " du hast das Spiel mit " + score[0] + " Punkten Gewonnen!\n");
		for(int i = 2; i <= score.length; i++) {
			sb.append("Herzlichen Glückwunsch " + names[i - 1] + " du bist " + i + ". mit " + score[i - 1] + " Punkten.\n");
		}
		sb.append("\nMöchtest du nochmal Spielen?");
		return JOptionPane.showOptionDialog(null, sb.toString(), "Das Spiel ist zuende", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	}
	
	@Override
	public void hide() {
		frame.setVisible(false);
	}
	
	@Override
	public void show() {
		frame.setVisible(true);
	}
	
	//TODO spielerHinzufügen Methode
	//TODO mehere Hände Hinzufügen
	//TODO nextPlayer Methode
}


//TODO Confirm Window das der Nächste dran ist
//TODO Switch zwischen GUI und Konsole
//TODO Spielstand Speichern
//TODO alle Fenster namen ändern









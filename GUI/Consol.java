package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Utils.GUIWindow;
import Utils.KeyInput;
import default2.Karte;
import default2.MyArrayList;

public class Consol extends GUIWindow{
	private JFrame consol;
	private JTextArea consolText;
	private JTextField consolInput;
	private JButton consolButton;
	
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	private GUIVerwaltung verwaltung;
	
	private String lastAdd;
	
	public Consol(GUIVerwaltung verwaltung) {
		this.verwaltung = verwaltung;
		
		KeyInput input; 
		
		consol = new JFrame("Consol");
		
		consol.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		consol.setSize(screenSize);
		consol.setLocationRelativeTo(null);
		consol.setResizable(false);
		
		consol.addKeyListener(input = new KeyInput(this));
		
		consol.setLayout(new BorderLayout());
		consolText = new JTextArea();
		consolText.setEditable(false);
		consolText.setLineWrap(false);
		consolText.setWrapStyleWord(false);
		JScrollPane scroll = new JScrollPane(consolText);
		
		consolButton = new JButton("Senden");
		consolButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				lastAdd = new String(consolInput.getText().strip());
				print(lastAdd + "\n");
				consolInput.setText("");
				consolInput.requestFocus();
			}
		});
		consol.setVisible(true);
		consolInput = new JTextField();
		consolInput.setPreferredSize(new Dimension(screenSize.width - 300, 27));
		
		consolInput.addKeyListener(input);
		
		JPanel bottomBar = new JPanel();
		
		bottomBar.add(consolInput);
		bottomBar.add(consolButton);
		
		consol.add(scroll, BorderLayout.CENTER);
		consol.add(bottomBar, BorderLayout.SOUTH);
		
		consolInput.requestFocus();
		
		consol.setVisible(true);
		
		requestNames();
	}
	
	public void sendPressed() {
		lastAdd = new String(consolInput.getText().strip());
		print(lastAdd + "\n");
		consolInput.setText("");
		consolInput.requestFocus();
	}
	
	public void print(String s) {
		consolText.append(s);
	}
	
	public void clear() {
		consolText.setText("");
	}
	
	private void requestNames() {
		CompletableFuture.supplyAsync(() -> spielerNameKriegen());
	}
	
	public String spielerNameKriegen() {
		print("Was ist dein Name\n");
		String mcName = lastAdd;
		while(mcName == lastAdd) {
			try {
				TimeUnit.MICROSECONDS.sleep(10);
			}catch(InterruptedException e) {}
		}
		mcName = lastAdd;
		
		print("Wieviele Mitspieler hast du?\n");
		int anzahlExtraSpieler = 0;
		String s = lastAdd;
		while(anzahlExtraSpieler == 0) {
			if(s != lastAdd) {
				try {
					TimeUnit.MICROSECONDS.sleep(10);
					anzahlExtraSpieler = Integer.parseInt(lastAdd);
					if(anzahlExtraSpieler < 1 || anzahlExtraSpieler > 3) {
						print("Du kannst nur zwischen 1 und 3 Mitspieler haben\n");
						anzahlExtraSpieler = 0;
					}
				}catch(Exception e) {
					print("Bitte gib eine Zahl an\n");
				}
				s = lastAdd;
			}
			//das bracht man sonst crasht die ComplepableFuture und man bekommt kein ergebniss
			try {
				TimeUnit.MICROSECONDS.sleep(10);
			}catch (InterruptedException e) {}
		}
		
		
		String[] names = new String[anzahlExtraSpieler];
		boolean[] ki = new boolean[anzahlExtraSpieler];
		for(int i = 1; i <= anzahlExtraSpieler; i++) {
			print("Was soll der Name des Mitspielers Nr." + i + " sein?\n");
			s = lastAdd;
			while(s == lastAdd) {
				try {
					TimeUnit.MICROSECONDS.sleep(10);
				}catch(InterruptedException e) {}
			}
			names[i - 1] = lastAdd;
			
			print("Soll " + lastAdd + " eine KI sein?\n");
			s = lastAdd;
			while(true) {
				while(s == lastAdd) {
					try {
						TimeUnit.MICROSECONDS.sleep(10);
					}catch(InterruptedException e) {}
				}
				if(lastAdd.toLowerCase().equals("ja") || lastAdd.toLowerCase().equals("yes")) {
					ki[i - 1] = true;
					break;
				}else if(lastAdd.toLowerCase().equals("nein") || lastAdd.toLowerCase().equals("no")) {
					ki[i - 1] = false;
					break;
				}else {
					print("Die Antwort möglichkeiten sind Ja oder Nein\n");
					s = lastAdd;
				}
				try {
					TimeUnit.MICROSECONDS.sleep(10);
				}catch(InterruptedException e) {}
			}
		}
		
		verwaltung.komplettNeuesSpiel(anzahlExtraSpieler + 1, mcName, names, ki);
		spielerWechseln(0);
		
		return null;
	}
	
	@Override
	public int amZug() {
		print("Welche Karte Möchtest du Spielen\nDie Karten sind von links nach rechts mit eins beginned aufgelisted\n0 bedeuted Nachziehen\n");
		int kartenNummer = 0;
		String s = lastAdd;
		while(kartenNummer == 0) {
			if(s != lastAdd) {
				try {
					TimeUnit.MICROSECONDS.sleep(10);
					kartenNummer = Integer.parseInt(lastAdd);
//					if(anzahlExtraSpieler < 1 || anzahlExtraSpieler > 3) {
//						print("Du kannst nur zwischen 1 und 3 Mitspieler haben\n");
//						anzahlExtraSpieler = 0;
//					}
				}catch(Exception e) {
					print("Bitte gib eine Zahl an\n");
				}
				s = lastAdd;
			}
			//das bracht man sonst crasht die ComplepableFuture und man bekommt kein ergebniss
			try {
				TimeUnit.MICROSECONDS.sleep(10);
			}catch (InterruptedException e) {}
		}
		return kartenNummer;
	}
	
	@Override
	public void spielerWechseln(int spieler) {
		clear();
		print("Bist du Spieler"); //TODO spieleranmen Einfügen
		lastAdd = "";
		while(!lastAdd.equals("ja") && !lastAdd.equals("yes")) {
			try {
				TimeUnit.MICROSECONDS.sleep(10);
			}catch(InterruptedException e) {}
		}
		clear();
		
		print("Oberste Karte:\n");
		
		for(String s : generateCard(verwaltung.getTopKarte().getZahl(), verwaltung.getTopKarte().getTyp())) {
			print(s + "\n");
		}
		
		print("Deine Hand:\n");
		
		MyArrayList<Karte> hand = verwaltung.getHand(spieler);
		String[] stringHand = {"", "", "", "", "", "", "", "", ""};
		for(int i = 0; i < hand.size() - 1; i++) {
			Karte karte = hand.get(i);
			String[] arr = generateCard(karte.getZahl(), karte.getTyp());
			for(int j = 0; j < arr.length; j++) {
				stringHand[j] += arr[j];
			}
		}
		String[] arr = generateCard(hand.get(hand.size() - 1).getZahl(), hand.get(hand.size() - 1).getTyp());
		for(int j = 0; j < arr.length; j++) {
			stringHand[j] += arr[j];
		}
		for(String s : stringHand) {
			print(s + "\n");
		}
	}
	
	private String[] generateCard(int rank, String suits) {
		String[] karteGanz = 
			{"┌───────────┐",
			"│" + toRank(rank, true) + "│",
			"│                      │",
			"│                      │",
			"│         " + '\u2009' + getSuits(suits) + '\u200A' + "         │",
			"│                      │",
			"│                      │",
			"│" + toRank(rank, false) + "│",
			"└───────────┘"};
		return karteGanz;
	}
	
	private char getSuits(String suits) {
		switch(suits) {
		case "Pik":
			return '\u2664';
		case "Herz":
			return '\u2661';
		case "Kreuz":
			return '\u2667';
		case "Karo":
			return '\u2662';
		default:
			return ' ';
		}
	}
	
	private String toRank(int rank, boolean top) {
		String out;
		String space;
		switch(rank) {
		case 11:
			out = "J";
			space = "                    ";
			break;
		case 12:
			out = "Q";
			space = "                   ";
			break;
		case 13:
			out = "K";
			space = "                   ";
			break;
		case 14:
			out = "A";
			space = "                   ";
			break;
		case 10:
			out = "10";
			space = "                 ";
			break;
		default:
			out = String.valueOf(rank);
			space = "                    ";
			break;
		}
		out = (top) ? out + space : space + out;
		return out;
	}
	
	@Override
	public int displayResult(String[] names, int[] score) {
		clear();
		print("Herzlichen Glückwunsch " + names[0] + " du hast das Spiel mit " + score[0] + " Punkten Gewonnen!\n");
		print("Herzlichen Glückwunsch " + names[1] + " du bist " + 2 + ". mit " + score[1] + " Punkten.\n");
		if(names.length >= 3) {
			print("Herzlichen Glückwunsch " + names[2] + " du bist " + 3 + ". mit " + score[2] + " Punkten.\n");
			if(names.length >= 4) {
				print("Herzlichen Glückwunsch " + names[3] + " du bist " + 4 + ". mit " + score[3] + " Punkten.\n");
			}
		}
		return 0;
	}
	
	@Override
	public void hide() {
		consol.setVisible(false);
	}
	
	@Override
	public void show() {
		consol.setVisible(true);
	}
}











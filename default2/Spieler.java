package default2;

import java.util.Scanner;

/**
 * Die Klasse umfasst die Aktionen und Eigenschaften, welche digitale Spieler beim MauMau Spiel 
 * nachkommen können und müssen. So werden die Handkarten eines Spielers hier verwaltet und auch
 * die Aktionen der Spielteilnahme hier ausgeführt.
 * 
 * @author Hein
 * @version 26.03.2016
 */
public class Spieler
{
    protected int nummer;
    protected int kartenanzahl;
    protected MyArrayList<Karte> handkarten;
    protected MyArrayList<Karte> ablageStapel;
    protected MyArrayList<Karte> ziehStapel;
    protected Spiel spiel;
    protected String aName;
    private boolean aIstMensch;
    protected int zaehlerSieben;
    String gF;

    public Spieler(int n, String pName, MyArrayList<Karte> stapelZ, MyArrayList<Karte> stapelA, Spiel s, boolean pIstMensch)
    {
        nummer = n;
        aName = pName;
        kartenanzahl = 0;
        handkarten = new MyArrayList<Karte>();
        ablageStapel = stapelA;
        ziehStapel = stapelZ;
        spiel = s;
        aIstMensch = pIstMensch;
        zaehlerSieben = 0;
    }

    protected int getKartenanzahl(){
        return kartenanzahl;
    }

    protected void zugMachen(){
        int letzteZahl = ((Karte)(ablageStapel.get(ablageStapel.size()-1))).getZahl();
        String letzterTyp = ((Karte)(ablageStapel.get(ablageStapel.size()-1))).getTyp();      
        
        karteSpielen(letzteZahl,letzterTyp);
    }

    protected void karteZiehenStrafeSieben()
    {        
        for(int i = zaehlerSieben; i > 0; i--)
        {
         karteZiehen();
         karteZiehen();
        }
    }

    protected void karteZiehen(){
        if(!ziehStapel.isEmpty())
        {
            Karte merker;
            merker = (Karte)ziehStapel.get(0);
            ziehStapel.remove(0);
            kartenanzahl++;
            handkarten.add(merker);
        }
        else{
            mischeStapel();
            karteZiehen();
        }
    }

    protected int handkartenAusgeben(){
        int zaehler = 0;
        if(!handkarten.isEmpty()){
        	Spiel.rGame.informiere("Deine Karten: [");
        	Spiel.rGame.informiere(((Karte)handkarten.get(0)).karte());
            for(int i = 1; i < handkarten.size(); i++){
            	Spiel.rGame.informiere("|");
            	Spiel.rGame.informiere(((Karte)handkarten.get(i)).karte());
                zaehler++;
            }
            Spiel.rGame.informieren("]");
            return zaehler;
        }
        else
        {
        	Spiel.rGame.informieren("Sie haben keine Karten mehr auf der Hand!");
            return 0;
        }
    }

    protected void karteSpielen(int zahl, String typ){
        handkartenAusgeben();
    	Spiel.rGame.informieren(">> Geben Sie die Zahl der Karte (ab 1) an, welche Sie spielen möchten.");
    	Spiel.rGame.informieren(">> Geben Sie die 0 an, um eine Karte zu ziehen, falls Sie nicht können.");        
    	Spiel.rGame.informieren(">> Wenn Sie keine gültige Zahl angeben, ziehen Sie eine Karte und Passen.");
        Scanner scanner = new Scanner(String.valueOf(Spiel.rGame.amZug()));
        int eingabe;
        Karte gKarte;
        if(scanner.hasNextInt()){
            eingabe = (int)scanner.nextInt();
            scanner.close();
            if(eingabe<=kartenanzahl){
                if(eingabe==0){
                    karteZiehen();
                    Spiel.rGame.refresh();
                    handkartenAusgeben();
                    Spiel.rGame.informieren(">> Können Sie nun eine Karte ausspielen?");
                    Spiel.rGame.informieren(">> Geben Sie die 0 an, falls Sie erneut nicht können."); 
                    scanner = new Scanner(String.valueOf(Spiel.rGame.amZug()));
                    
                    eingabe = (int)scanner.nextInt();
                    scanner.close();
                }
                if(eingabe==0)
                {
                	Spiel.rGame.informieren("Ich kann immer noch nicht.");
                }
                else
                {
                    gKarte = (Karte)handkarten.get(eingabe-1);
                    if(karteErlaubt(gKarte,zahl,typ))
                    {
                    	Spiel.rGame.informieren("KARTE GESPIELT: " + gKarte.karte());
                        kartenanzahl--;
                        if(gKarte.getZahl() != 7 && zaehlerSieben > 0){
                        	Spiel.rGame.informieren("Ich musste" + zaehlerSieben *2 + "Karten als Strafe zur 7 ziehen!");
                            karteZiehenStrafeSieben();                            
                            zaehlerSieben = 0;
                        }
                        else if(gKarte.getZahl() == 7)
                        {
                           zaehlerSieben = zaehlerSieben +1;
                        }
                        else if(gKarte.getZahl()==8)
                        {
                            spiel.gibAussetzImpuls();
                        }
                        else if(gKarte.getZahl() == 11)
                        {
                        	Spiel.rGame.informieren("Wünsche Dir ein Symbol. Erlaubte Eingaben sind: Karo, Kreuz, Pik, Herz.");
                            gF = bubeWünscheDirFarbe();
                            gKarte.setTyp(gF);
                        }
                        ablageStapel.add((Karte)handkarten.get(eingabe-1));
                        handkarten.remove(eingabe-1);
                        
                    }
                    else
                    {
                    	Spiel.rGame.informieren("Diese Karte können Sie nicht legen; Sie ziehen eine Karte und Passen.");
                        karteZiehen();
                    }
                }
            } 
            else
            {
            	Spiel.rGame.informieren("Diese Karte gibt es nicht; Sie ziehen eine Karte und Passen.");
                karteZiehen();
            }
        } 
         
        else 
        {
        	Spiel.rGame.informieren("Diese Eingabe war unültig; Sie ziehen eine Karte und Passen.");
            karteZiehen();
        }
       
        
    }

    public String bubeWünscheDirFarbe()
    {
        Scanner scanner = new Scanner(Spiel.rGame.farbeWuenschen());
        return (String)scanner.next();        
    }
    
    protected boolean karteErlaubt(Karte g, int z, String t){
        boolean erlaubt = false;
        if(g.getZahl()==z) 
            erlaubt = true;
        if(g.getTyp().equals(t))
            erlaubt = true;
        //if(z==0 && g.getTyp().equals(gewünschteFarbe))
        //    erlaubt = true;
        if(g.getZahl()==11)
            erlaubt = true;
        if(g.getZahl()==11 && z==11)
            erlaubt = false;
        //System.out.println("Karte Erlaubt:  "+erlaubt + " " + t + " " + z);
        return erlaubt;
    }

    protected void mischeStapelNeu(){
        //Die Karten im Ablagestapel zählen, um später alle zu übertragen.
        int zaehler = ablageStapel.size();

        //Die Karten gemischt durch den Zufall vom Ablage- auf den Ziehstapel übertragen.
        int zufallszahl = (int)((Math.random()) * zaehler); 
        while(zaehler>-1){
            ziehStapel.add((Karte)ablageStapel.get(zufallszahl));
            ablageStapel.remove(zufallszahl);
            zaehler--;
            zufallszahl = (int)((Math.random()) * zaehler);
        }
    }

    protected void mischeStapel(){
        //Die zuletzt gespielte Karte behalten und dazu sichern.
        Karte merker = (Karte)ablageStapel.get(ablageStapel.size()-1);
        ablageStapel.remove(ablageStapel.size()-1);

        //Die verbliebenen Karten des Stapels auf- und untermischen.
        mischeStapelNeu();

        //Die zuletzt gespielte Karte wiederauf den Ablagestapel legen.
        ablageStapel.add(merker);
    }
    
    public boolean istMensch(){
        return aIstMensch;
    }
    
    public String getName() {
    	return aName;
    }
    
    public MyArrayList<Karte> getHandKarten() {
    	return handkarten;
    }
    
    public int berechnePunkte(){
        if(!handkarten.isEmpty()){
            int letzteZahl = ((Karte)(ablageStapel.get(ablageStapel.size()-1))).getZahl(); //Abfrage der zuletzt gespielten Karte
            int spielerPunkte = 0;
            spielerPunkte = spielerPunkte - ((Karte)handkarten.get(0)).punkte();
            for(int i = 1; i < handkarten.size(); i++){
                spielerPunkte = spielerPunkte - ((Karte)handkarten.get(i)).punkte();
            }
            if(letzteZahl==11){
                    spielerPunkte = spielerPunkte - spielerPunkte; //Falls die zuletzt gespielte Karte ein Bube war, werden die Punkte verdoppelt
                }
            return spielerPunkte;
        }
        else
        {
            return 0;
        }
    }
}

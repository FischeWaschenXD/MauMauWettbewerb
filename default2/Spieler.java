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
    }

    protected int getKartenanzahl(){
        return kartenanzahl;
    }

    protected void zugMachen(){
        int letzteZahl = ((Karte)(ablageStapel.get(ablageStapel.size()-1))).getZahl();
        String letzterTyp = ((Karte)(ablageStapel.get(ablageStapel.size()-1))).getTyp();
        if(letzteZahl==7){
            System.out.println("Ich musste zwei Karten als Strafe zur 7 ziehen!");
            karteZiehenStrafeSieben();
            karteSpielen(letzteZahl,letzterTyp);
        }
        else if(letzteZahl==11){
            System.out.println("Ich kann eine beliebige Karte legen - nur keinen weiteren Buben!");
            karteSpielen(0,"freieWahl");
        }
        else
        {
            karteSpielen(letzteZahl,letzterTyp);
        }
    }

    protected void karteZiehenStrafeSieben(){
        karteZiehen();
        karteZiehen();
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
            System.out.print("Deine Karten: [");
            System.out.print(((Karte)handkarten.get(0)).karte());
            for(int i = 1; i < handkarten.size(); i++){
                System.out.print("|");
                System.out.print(((Karte)handkarten.get(i)).karte());
                zaehler++;
            }
            System.out.println("]");
            return zaehler;
        }
        else
        {
            System.out.println("Sie haben keine Karten mehr auf der Hand!");
            return 0;
        }
    }

    protected void karteSpielen(int zahl, String typ){
        handkartenAusgeben();
        System.out.println(">> Geben Sie die Zahl der Karte (ab 1) an, welche Sie spielen möchten.");
        System.out.println(">> Geben Sie die 0 an, um eine Karte zu ziehen, falls Sie nicht können.");        
        System.out.println(">> Wenn Sie keine gültige Zahl angeben, ziehen Sie eine Karte und Passen.");
        Scanner scanner = new Scanner(System.in);
        int eingabe;
        if(scanner.hasNextInt()){
            eingabe = (int)scanner.nextInt();
            if(eingabe<=kartenanzahl){
                if(eingabe==0){
                    karteZiehen();
                    handkartenAusgeben();
                    System.out.println(">> Können Sie nun eine Karte ausspielen?");
                    System.out.println(">> Geben Sie die 0 an, falls Sie erneut nicht können."); 
                    scanner = new Scanner(System.in);
                    eingabe = (int)scanner.nextInt();
                }
                if(eingabe==0)
                {
                    System.out.println("Ich kann immer noch nicht.");
                }
                else
                {
                    Karte gKarte = (Karte)handkarten.get(eingabe-1);
                    if(karteErlaubt(gKarte,zahl,typ))
                    {
                        System.out.println("KARTE GESPIELT: " + gKarte.karte());
                        kartenanzahl--;
                        if(gKarte.getZahl()==8)
                            spiel.gibAussetzImpuls();
                        ablageStapel.add((Karte)handkarten.get(eingabe-1));
                        handkarten.remove(eingabe-1);
                    }
                    else
                    {
                        System.out.println("Diese Karte können Sie nicht legen; Sie ziehen eine Karte und Passen.");
                        karteZiehen();
                    }
                }
            } 
            else
            {
                System.out.println("Diese Karte gibt es nicht; Sie ziehen eine Karte und Passen.");
                karteZiehen();
            }
        } 
        else
        {
            System.out.println("Diese Eingabe war unültig; Sie ziehen eine Karte und Passen.");
            karteZiehen();
        }
    }

    protected boolean karteErlaubt(Karte g, int z, String t){
        boolean erlaubt = false;
        if(g.getZahl()==z) 
            erlaubt = true;
        if(g.getTyp().equals(t))
            erlaubt = true;
        if(t.equals("freieWahl"))
            erlaubt = true;
        if(g.getZahl()==11)
            erlaubt = true;
        if(g.getZahl()==11 && t.equals("freieWahl"))
            erlaubt = false;
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
}

package default2; 

import GUI.GUIVerwaltung;

public class Spiel
{
	public static Spiel spiel;
	
    //Liste um eine Liste mit 2-4 Spielern zu erstellen
    private Spieler[] rSpielerliste;
    private int aAnzahlDerSpieler;
    private int aAnzahlDerCOM;
    private String[] aNamen;

    //Merker für den aktuellen Spieler und ein mögliches Aussetzen des nächsten Zuges.
    private int spielerAmZug;
    private boolean aussetzen;

    //Zwei Stapel im System, einer als Ablagestapel, einer als Nachziehstapel.
    private MyArrayList<Karte> ablegeStapel;
    private MyArrayList<Karte> ziehStapel;
    public static GUIVerwaltung rGame;

    public Spiel(int pAnzahlDerSpieler, int pAnzahlDerCOM, String[] pNamen)
    {
    	spiel = this;
    	
    	if(rGame == null) rGame = new GUIVerwaltung();
    	
    	aAnzahlDerSpieler = pAnzahlDerSpieler;

        aAnzahlDerCOM = pAnzahlDerCOM;
        
        aNamen = pNamen;
    	
        //Generierung der Spielerliste
        rSpielerliste = new Spieler[pAnzahlDerSpieler];

        //Spielkarten deklarieren, initialisieren und in die Stapel legen
        generiereKarten();

        //Spieler deklarieren und initialisieren: generiereSpieler() für 2x Spieler, generiereSpielerMitKI() für 1x Spieler und 1x PC-Gegner
        generiereSpielerMitKI(pAnzahlDerCOM);
        
        //Spiel initalisieren und beginnen, diese Methode läuft bis zum Sieg
        spielBeginn();
    }

    private void generiereKarten(){
        //Stapel
        ablegeStapel = new MyArrayList<Karte>();
        ziehStapel = new MyArrayList<Karte>();

        //Karten
        legeKartenAn("Karo");
        legeKartenAn("Herz");
        legeKartenAn("Kreuz");
        legeKartenAn("Pik");

        //Stapelcheck inaktiv
        //ausgabeBeiderStapel();
    }

    private void legeKartenAn(String s){
        //Legt 8 Karten des gleichen Typs an ...
        Karte kar7 = new Karte(7, s);
        Karte kar8 = new Karte(8, s);
        Karte kar9 = new Karte(9, s);
        Karte kar10 = new Karte(10, s);
        Karte karB = new Karte(11, s);
        Karte karD = new Karte(12, s);
        Karte karK = new Karte(13, s);
        Karte karA = new Karte(14, s);
        //... und fügt diese dem Ablagestapel hinzu.
        ablegeStapel.add(kar7);
        ablegeStapel.add(kar8);
        ablegeStapel.add(kar9);
        ablegeStapel.add(kar10);
        ablegeStapel.add(karB);
        ablegeStapel.add(karD);
        ablegeStapel.add(karK);
        ablegeStapel.add(karA);
    }

    private void ausgabeBeiderStapel(){
        //Ausgabe des Ablagestapels
        rGame.informieren("");
        rGame.informieren("AUSGABE des ABLAGESTAPEL");
        if(!ablegeStapel.isEmpty()){
            for(int i = 0; i < ablegeStapel.size(); i++){
                rGame.informieren(((Karte)ablegeStapel.get(i)).karte());
            }
        }
        else
        {
            rGame.informieren("Keine Karten vorhanden!");
        }
        //Ausgabe des Nachziehstapels
        rGame.informieren("");
        rGame.informieren("AUSGABE des NACHZIEHSTAPEL");
        if(!ziehStapel.isEmpty()){
            for(int i = 0; i < ziehStapel.size(); i++){
                rGame.informieren(((Karte)ziehStapel.get(i)).karte());
            }
        }
        else
        {
            rGame.informieren("Keine Karten vorhanden!");
        }
    }

    private void generiereSpieler(){
        rGame.informieren("Initialisiere die Spieler des Spiels!");
        Spieler lSpieler;
        String lName;
        //Einrichten von n menschlichen Spielern
        for(int i = 0; i < rSpielerliste.length; i++){
            lSpieler = new Spieler(i, aNamen[i], ziehStapel, ablegeStapel, this, true);
            rSpielerliste[i] = lSpieler;
        }
        aussetzen = false;
    }

    private void generiereSpielerMitKI(int pAnzahlDerCOM){
        rGame.informieren("Initialisiere die Spieler und Computer des Spiels!");
        Spieler lSpieler;
        int lAnzahlDerCOM = pAnzahlDerCOM;
        //Einrichten von n menschlichen Spielern
        if(pAnzahlDerCOM < rSpielerliste.length){
            for(int i = 0; i < rSpielerliste.length; i++){
                if(lAnzahlDerCOM > 0){
                    lSpieler = new Spieler_KI(i, aNamen[i], ziehStapel, ablegeStapel, this, false);
                    rSpielerliste[i] = lSpieler;
                    lAnzahlDerCOM--;
                }
                else{
                    lSpieler = new Spieler(i, aNamen[i], ziehStapel, ablegeStapel, this, true);
                    rSpielerliste[i] = lSpieler;
                }
            }
            aussetzen = false;
        }
        else {rGame.informieren("Fehler! Mehr KIs als Menschen");}
    }

    private void spielBeginn(){
        //Beide Spieler müssen zunächst mal 5 Karten ziehen bzw. auf die Hand nehmen.
        rGame.informieren("Die Spieler ziehen jeweils fünf Karten!");
        int lErsterSpieler;
        if(aAnzahlDerCOM > 0){
            lErsterSpieler = aAnzahlDerCOM;
        }
        else{lErsterSpieler = 0;}
        rSpielerliste[lErsterSpieler].mischeStapelNeu();
        for(int j = 0; j < aAnzahlDerSpieler; j++){
            for(int i = 5; i > 0; i--){
                rSpielerliste[j].karteZiehen();
            }
        }

        //Die erste Karte des Nachziehstapels wird aufgedeckt und auf dem neuen Ablagestapel gelegt.
        rGame.informieren("");
        rGame.informieren("Decke die erste Karte des Spiels auf:");
        Karte merker = (Karte)ziehStapel.get(0);
        if(merker.getZahl() == 9){
            richtungsWechsel();
        }
        ablegeStapel.add(merker);
        ziehStapel.remove(0);
        rGame.informieren("BEGINN des Spiels mit der KARTE " + merker.karte());
        
        //Solange es keinen Sieger gibt wird gespielt, notfalls unendlich oft.
        while(sieg() == null)
        {
            //Der nächste Spieler am Zug wird angsagt.
            rGame.informieren("");
            rGame.informieren("Nächster Spieler: " + (spielerAmZug+1)%aAnzahlDerSpieler);

            //Der Spieler müsste möglicherweise aussetzen ...
            if(aussetzen){
                rGame.informieren("Ich muss eine Runde aussetzen, die 8 liegt oben!");
                spielerAmZug = (spielerAmZug+1)%aAnzahlDerSpieler;
                aussetzen = false;
            }
 
            //... wenn nicht, darf er den Zug ausführen.
            else
            {
                spielZug();
                spielerAmZug = (spielerAmZug+1)%aAnzahlDerSpieler;
            }
        }

        //Wenn die Spiel-Schleife beendet ist, gibt es einen Sieger. Dieser muss bekannt gegeben werden.
        if (!(sieg() == null)){
            rGame.gewonnen(sieg());
        }
    }

    private Spieler[] sieg(){
        Spieler[] lGewonnenReihenfolge = new Spieler[aAnzahlDerSpieler];
	for(int i = 0; i < rSpielerliste.length; i++){
		lGewonnenReihenfolge[i] = rSpielerliste[i];
	}
        int lKartenAufHaenden = 0;
        //Spieler 1 siegt mit dem Code 1, wenn er keine Karten mehr auf der Hand hat.
        for(int i = 0; i < aAnzahlDerSpieler; i++){
            lKartenAufHaenden = lKartenAufHaenden + rSpielerliste[i].getKartenanzahl();
        }
        
        Spieler temp;
        int lGrenze = 1;
	int lIndex;
	while(lGrenze < rSpielerliste.length){
		temp = lGewonnenReihenfolge[lGrenze];
		lIndex = lGrenze;
		while(lIndex >= 0 && lGewonnenReihenfolge[lIndex-1].getKartenanzahl() > temp.getKartenanzahl()){
			lGewonnenReihenfolge[lIndex] = lGewonnenReihenfolge[lIndex-1];
			lIndex--;
		}
		lGewonnenReihenfolge[lIndex] = temp;
		lGrenze++;
	}
	      
        /*for (i = 0; i < rSpielerliste.length; i++) {  
            temp = rSpielerliste[i];  
            j = i - 1;  
      
            while(j>=0 && temp.getKartenanzahl() <= rSpielerliste[j].getKartenanzahl())  /* Move the elements greater than temp to one position ahead from their current position  
            {    
                lGewonnenReihenfolge[j+1] = rSpielerliste[j];     
                j = j-1;    
            }    
            lGewonnenReihenfolge[j+1] = temp;    
        }  */
        
        if(lGewonnenReihenfolge[0].getKartenanzahl()==0 || lKartenAufHaenden==29)
            return lGewonnenReihenfolge;
        else{return null;}
        /*if(lGewonnenReihenfolge[0].getKartenanzahl()==0)
            return 1;
        //Spieler 2 siegt mit dem Code 2, wenn er keine Karten mehr auf der Hand hat.
        else if(rSpielerliste[1].getKartenanzahl()==0)
            return 2;
        else if(rSpielerliste.length >= 3 && rSpielerliste[2].getKartenanzahl()==0)
            return 3;
        else if(rSpielerliste.length >= 4 && rSpielerliste[3].getKartenanzahl()==0)
            return 4;
        //Das Spiel endet auch, wenn 29 Karten auf beiden Händen der Spieler sind.
        else if(lKartenAufHaenden==29)
        {
            int lSpielerGewonnenIndex = 0;
            Spieler lSpielerGewonnen = rSpielerliste[0];
            for(int i = 1; i < rSpielerliste.length; i++){
                if(lSpielerGewonnen.getKartenanzahl() > rSpielerliste[i].getKartenanzahl()){
                    lSpielerGewonnenIndex = i;
                    lSpielerGewonnen = rSpielerliste[i];
                }
            }
            return lSpielerGewonnenIndex + 1;
        }
        //Gibt es noch keinen Sieger nach den genannten Kriterien, dann gibt die Methode den Code 0 aus.
        else{return 0;}
        */
    }

    private void spielZug(){
        //Je nachdem welcher SpielerAmZug ist (Code 0 = Spieler 1, Code 1 = Spieler 2), darf der Spieler einen Zug ausführen.
        int letzteZahl = ablegeStapel.get(ablegeStapel.size()-1).getZahl();
        String letzterTyp = ablegeStapel.get(ablegeStapel.size()-1).getTyp();
        if(spielerAmZug==0){
            rSpielerliste[0].karteSpielen(letzteZahl, letzterTyp);
            if(ablegeStapel.get(ablegeStapel.size()-1).getZahl() == 9){
                richtungsWechsel();
            }
        }
        else if (spielerAmZug==1){
            rSpielerliste[1].karteSpielen(letzteZahl, letzterTyp);
            if(ablegeStapel.get(ablegeStapel.size()-1).getZahl() == 9){
                richtungsWechsel();
            }
        }
        else if (spielerAmZug==2){
            rSpielerliste[2].karteSpielen(letzteZahl, letzterTyp);
            if(ablegeStapel.get(ablegeStapel.size()-1).getZahl() == 9){
                richtungsWechsel();
            }
        }
        else if (spielerAmZug==3){
            rSpielerliste[3].karteSpielen(letzteZahl, letzterTyp);
            if(ablegeStapel.get(ablegeStapel.size()-1).getZahl() == 9){
                richtungsWechsel();
            }
        }
    }
    
    public Spieler[] gibSpielerliste(){
        return rSpielerliste;
    }
    
    public void gibAussetzImpuls(){
        //Mit dieser Methode kann ein Spieler den Aussetz-Impuls geben, mit dem der nächste Spieler der Reihe einmal übersprungen wird.
        aussetzen = true;
    }
    
    public void richtungsWechsel(){
        if(aAnzahlDerSpieler > 2){
            
            Spieler[] lHilfsfeld = new Spieler[rSpielerliste.length];
            int lIndex = 0;
            for(int i = rSpielerliste.length-1; i>=0; i--){
                lHilfsfeld[lIndex] = rSpielerliste[i];
                lIndex++;
            }
            rSpielerliste = lHilfsfeld;
        }
    }
    
    public Spieler[] getSpielerListe() {
    	return rSpielerliste;
    }
    
    public MyArrayList<Karte> getAblageStapel() {
    	return ablegeStapel;
    }
    
    public void newGame() {
    	new Spiel(aAnzahlDerSpieler, aAnzahlDerCOM, aNamen);
    	rGame.refresh();
    }
}

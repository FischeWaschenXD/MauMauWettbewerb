import GUI.Game;

public class Spiel
{
    //Liste um eine Liste mit 2-4 Spielern zu erstellen
    private Spieler[] rSpielerliste;
    private int aAnzahlDerSpieler;
    private int aAnzahlDerCOM;
    private Game rGUI;
    private String[] aNamen;

    //Merker für den aktuellen Spieler und ein mögliches Aussetzen des nächsten Zuges.
    private int spielerAmZug;
    private boolean aussetzen;

    //Zwei Stapel im System, einer als Ablagestapel, einer als Nachziehstapel.
    private MyArrayList<Karte> ablegeStapel;
    private MyArrayList<Karte> ziehStapel;
    private Game rGame;

    public Spiel(int pAnzahlDerSpieler, int pAnzahlDerCOM, String[] pNamen)
    {
        //Generierung der Spielerliste
        rSpielerliste = new Spieler[pAnzahlDerSpieler];

        //Spielkarten deklarieren, initialisieren und in die Stapel legen
        generiereKarten();

        //Spieler deklarieren und initialisieren: generiereSpieler() für 2x Spieler, generiereSpielerMitKI() für 1x Spieler und 1x PC-Gegner
        generiereSpielerMitKI(pAnzahlDerCOM);

        //Spiel initalisieren und beginnen, diese Methode läuft bis zum Sieg
        spielBeginn();

        aAnzahlDerSpieler = pAnzahlDerSpieler;

        aAnzahlDerCOM = pAnzahlDerCOM;
        
        aNamen = pNamen;
        
        rGame = new Game();
    }

    private void generiereKarten(){
        //Ausgabe
        System.out.println("Initialisiere die Karten des Spiels!");

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
        System.out.println();
        System.out.println("AUSGABE des ABLAGESTAPEL");
        if(!ablegeStapel.isEmpty()){
            for(int i = 0; i < ablegeStapel.size(); i++){
                System.out.println(((Karte)ablegeStapel.get(i)).karte());
            }
        }
        else
        {
            System.out.println("Keine Karten vorhanden!");
        }
        //Ausgabe des Nachziehstapels
        System.out.println();
        System.out.println("AUSGABE des NACHZIEHSTAPEL");
        if(!ziehStapel.isEmpty()){
            for(int i = 0; i < ziehStapel.size(); i++){
                System.out.println(((Karte)ziehStapel.get(i)).karte());
            }
        }
        else
        {
            System.out.println("Keine Karten vorhanden!");
        }
    }

    private void generiereSpieler(){
        System.out.println("Initialisiere die Spieler des Spiels!");
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
        System.out.println("Initialisiere die Spieler und Computer des Spiels!");
        Spieler lSpieler;
        int lAnzahlDerCOM = pAnzahlDerCOM;
        //Einrichten von n menschlichen Spielern
        if(pAnzahlDerCOM < (rSpielerliste.length - 1)){
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
        else {System.out.println("Fehler! Mehr KIs als Menschen");}
    }

    private void spielBeginn(){
        //Beide Spieler müssen zunächst mal 5 Karten ziehen bzw. auf die Hand nehmen.
        System.out.println("Die Spieler ziehen jeweils fünf Karten!");
        int lErsterSpieler;
        if(aAnzahlDerCOM > 0){
            lErsterSpieler = aAnzahlDerCOM;
        }
        else{lErsterSpieler = 0;}
        rSpielerliste[lErsterSpieler].mischeStapelNeu();
        for(int i = 5; i > 0; i--){
            for(int j = 0; i > 4; i++){
                rSpielerliste[i].karteZiehen();
            }
        }

        //Die erste Karte des Nachziehstapels wird aufgedeckt und auf dem neuen Ablagestapel gelegt.
        System.out.println("");
        System.out.println("Decke die erste Karte des Spiels auf:");
        Karte merker = (Karte)ziehStapel.get(0);
        if(merker.getZahl() == 9){
            richtungsWechsel();
        }
        ablegeStapel.add(merker);
        ziehStapel.remove(0);
        System.out.println("BEGINN des Spiels mit der KARTE " + merker.karte());
        
        //Solange es keinen Sieger gibt wird gespielt, notfalls unendlich oft.
        while(sieg() == null)
        {
            //Der nächste Spieler am Zug wird angsagt.
            System.out.println("");
            System.out.println("Nächster Spieler: " + (spielerAmZug+1));

            //Der Spieler müsste möglicherweise aussetzen ...
            if(aussetzen){
                System.out.println("Ich muss eine Runde aussetzen, die 8 liegt oben!");
                spielerAmZug = (spielerAmZug+1)%4;
                aussetzen = false;
            }

            //... wenn nicht, darf er den Zug ausführen.
            else
            {
                spielZug();
                spielerAmZug = (spielerAmZug+1)%4;
            }
        }

        //Wenn die Spiel-Schleife beendet ist, gibt es einen Sieger. Dieser muss bekannt gegeben werden.
        if (!(sieg() == null)){
            rGame.gewonnen(sieg());
        }
    }

    private Spieler[] sieg(){
        Spieler[] lGewonnenReihenfolge = new Spieler[aAnzahlDerSpieler];
        int lKartenAufHaenden = 0;
        //Spieler 1 siegt mit dem Code 1, wenn er keine Karten mehr auf der Hand hat.
        for(int i = 0; i < aAnzahlDerSpieler; i++){
            lKartenAufHaenden = lKartenAufHaenden + rSpielerliste[i].getKartenanzahl();
        }
        
        Spieler temp;
        int i, j;  
        for (i = 1; i < rSpielerliste.length; i++) {  
            temp = rSpielerliste[i];  
            j = i - 1;  
      
            while(j>=0 && temp.getKartenanzahl() <= rSpielerliste[j].getKartenanzahl())  /* Move the elements greater than temp to one position ahead from their current position*/  
            {    
                lGewonnenReihenfolge[j+1] = rSpielerliste[j];     
                j = j-1;    
            }    
            lGewonnenReihenfolge[j+1] = temp;    
        }  
        
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
        if(spielerAmZug==0)
            rSpielerliste[0].zugMachen();
        else if (spielerAmZug==1)
            rSpielerliste[1].zugMachen();
        else if (spielerAmZug==2)
            rSpielerliste[2].zugMachen();
        else if (spielerAmZug==3)
            rSpielerliste[3].zugMachen();
    }
    
    public Spieler[] gibSpielerliste(){
        return rSpielerliste;
    }
    
    public void gibAussetzImpuls(){
        //Mit dieser Methode kann ein Spieler den Aussetz-Impuls geben, mit dem der nächste Spieler der Reihe einmal übersprungen wird.
        aussetzen = true;
    }
    
    public void richtungsWechsel(){
        Spieler[] lHilfsfeld = new Spieler[rSpielerliste.length];
        int lIndex = 0;
        for(int i = rSpielerliste.length-1; i>=0; i--){
            lHilfsfeld[lIndex] = rSpielerliste[i];
            lIndex++;
        }
        rSpielerliste = lHilfsfeld;
    }
}

package default2;

public class Spieler_KI extends Spieler
{
    public Spieler_KI(int n, String pName, MyArrayList<Karte> stapelZ, MyArrayList<Karte> stapelA, Spiel s, boolean pIstMensch)
    {
        super(n, pName, stapelZ, stapelA, s, pIstMensch);
    }

    @Override protected void karteSpielen(int zahl, String typ){
        boolean weiter = true;
        Karte gKarte;
        for(int i = 0; i < handkarten.size() && weiter; ){
            gKarte = (Karte)handkarten.get(i);
            if(karteErlaubt(gKarte,zahl,typ))
            {
            	Spiel.rGame.informieren("KARTE GESPIELT: " + gKarte.karte());
                kartenanzahl--;
                if(gKarte.getZahl()==8)
                    spiel.gibAussetzImpuls();
                    else if(gKarte.getZahl()==11)
                {
                   double zufallszahl = Math.random();
                   if(zufallszahl < 0.25)
                   {
                      gF = "Karo";  
                      
                   }
                   else if(zufallszahl >= 0.25 && zufallszahl < 0.5)
                   {
                      gF = "Herz";                      
                   }
                   else if(zufallszahl >= 0.5 && zufallszahl < 0.75)
                   {
                      gF = "Pik";                      
                   }
                   else if(zufallszahl >= 0.75)
                   {
                      gF = "Kreuz";                      
                   }
                   Spiel.rGame.informieren("Ich wünsche mir " + gF);
                }
                ablageStapel.add((Karte)handkarten.get(i));
                handkarten.remove(i);
                weiter = false;
                Spiel.rGame.informieren("Ich habe noch " + kartenanzahl + " Karte(n).");
            }
            else
            {
                i++;
            }
        }
        if(weiter){
            karteZiehen();
            gKarte = (Karte)handkarten.get(handkarten.size()-1);
            if(karteErlaubt(gKarte,zahl,typ))
            {
            	Spiel.rGame.informieren("KARTE GESPIELT: " + gKarte.karte());
                kartenanzahl--;
                if(gKarte.getZahl()==8)
                    spiel.gibAussetzImpuls();
                else if(gKarte.getZahl()==11)
                {
                   double zufallszahl = Math.random();
                   if(zufallszahl < 0.25)
                   {
                      gF = "Karo";                      
                   }
                   else if(zufallszahl >= 0.25 && zufallszahl < 0.5)
                   {
                      gF = "Herz";                      
                   }
                   else if(zufallszahl >= 0.5 && zufallszahl < 0.75)
                   {
                      gF = "Pik";                      
                   }
                   else if(zufallszahl >= 0.75)
                   {
                      gF = "Kreuz";                      
                   }
                   Spiel.rGame.informieren("Ich wünsche mir " + gF);
                }
                ablageStapel.add((Karte)handkarten.get(handkarten.size()-1));
                handkarten.remove(handkarten.size()-1);
                Spiel.rGame.informieren("Ich habe noch " + kartenanzahl + " Karte(n).");
            }else{
            	Spiel.rGame.informieren("Ich habe noch " + kartenanzahl + " Karte(n).");
            	Spiel.rGame.informieren("Ich kann nicht spielen, Sie sind am Zug!");
            }
        }
    }
}

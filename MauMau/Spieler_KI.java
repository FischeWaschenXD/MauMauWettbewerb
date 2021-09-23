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
                System.out.println("KARTE GESPIELT: " + gKarte.karte());
                kartenanzahl--;
                if(gKarte.getZahl()==8)
                    spiel.gibAussetzImpuls();
                ablageStapel.add((Karte)handkarten.get(i));
                handkarten.remove(i);
                weiter = false;
                System.out.println("Ich habe noch " + kartenanzahl + " Karte(n).");
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
                System.out.println("KARTE GESPIELT: " + gKarte.karte());
                kartenanzahl--;
                if(gKarte.getZahl()==8)
                    spiel.gibAussetzImpuls();
                ablageStapel.add((Karte)handkarten.get(handkarten.size()-1));
                handkarten.remove(handkarten.size()-1);
                System.out.println("Ich habe noch " + kartenanzahl + " Karte(n).");
            }else{
                System.out.println("Ich habe noch " + kartenanzahl + " Karte(n).");
                System.out.println("Ich kann nicht spielen, Sie sind am Zug!");
            }
        }
    }
}

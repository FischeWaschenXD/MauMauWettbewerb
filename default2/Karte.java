package default2;

/**
 * Die Klasse Karte dient als Basis des digitalen Kartenspiels MauMau.
 * Sie umfasst die Daten auf einer Karte und Informationsrückgaben zu dieser Karte.
 * 
 * @author Hein 
 * @version 09.03.16 V1.2
 */
public class Karte
{
    private int zahl; 
    // Globale Variable beschreibt den jeweiligen Wert der Karte.
    // 7 = 7, 8 = 8, 9 = 9, 10 = 10, 11 = Bube, 12 = Dame, 13 = König, 14 = Ass

    private String typ;
    // Globale Variable beschreibt das Zeichen bzw. die Farbe der Karte.   
    // Zulässige Varianten dieser Codierung: Karo, Kreuz, Herz und Pik.

    /**
     * Konstruktor für eine neue Karte.
     * Der Konstruktor initialisiert die globalen Variablen und bestimmt somit jede Karte als Teil eines Sets eindeutig.
     * 
     * @param int z - Die Zahl der neuen Karte auf Basis der Kodierung, siehe oben.
     * @param String t - Die Farbe und Typvariante der neuen Karte auf Basis der Kodierung, siehe oben.
     */
    public Karte(int z, String t)
    {
        zahl = z;
        typ = t;
    }

    /**
     * Getter-Methode für die globale Variable zahl.
     * Gibt die Zahl der aktuellen Karte dem Anfrage-Steller zurück.
     * 
     * @return int zahl - Die Zahl in Form der Kodierung des Kartenwertes, siehe oben.
     */
    public int getZahl(){
        return zahl;
    }

    /**
     * Getter-Methode für die globale Variable typ.
     * Gibt den Typ der aktuellen Karte dem Anfrage-Steller zurück.
     * 
     * @return String typ - Der Typ in Form der Kodierung der Kartenvariante, siehe oben.
     */
    public String getTyp(){
        return typ;
    }

    /**
     * Methode zur Rückgabe der eindeutigen Kartenbezeichnung.
     * Gibt die Zahl und den Typ der Karte in Form eine String dem Anfrage-Steller zurück.
     * 
     * @return String ausgabe - Ein Zeichenstring bestehend auf der Kartenvariante und dem Kartenwert.
     */
    public String karte()
    {
        String ausgabe = "";
        switch(zahl)
        {
            case 7: ausgabe = typ + " 7";
            break;
            case 8: ausgabe = typ + " 8";
            break;
            case 9: ausgabe = typ + " 9";
            break;
            case 10: ausgabe = typ + " 10";
            break;
            case 11: ausgabe = typ + " Bube";
            break;
            case 12: ausgabe = typ + " Dame";
            break;
            case 13: ausgabe = typ + " König";
            break;
            case 14: ausgabe = typ + " Ass";
            break;
            default: System.err.println("FEHLER bei der Kartenkonventierung!");
            break;
        }
        return ausgabe;
    }
}

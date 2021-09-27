package default2;

public class MyArrayList<ContentType>
{
    Object[] meinFeld;
    int ersteFreieStelle;

    public MyArrayList()
    {
        meinFeld = new Object[10];
        ersteFreieStelle = 0;
    }

    /**
     * Methode zum Vergrößern der Feldgröße um den Faktor x.
     * Ein lokales Hilfsfeld wird dazu initialisiert als Kopiergrund.
     */
    private void feldVergroessern(int x){
        Object[] hilfsFeld = new Object[meinFeld.length];
        for(int i = 0; i<meinFeld.length; i++){
            hilfsFeld[i] = meinFeld[i];
        }
        meinFeld = new Object[meinFeld.length*x];
        for(int i = 0; i<hilfsFeld.length; i++){
            meinFeld[i] = hilfsFeld[i];
        }
    }

    /**
     * Fügt das Element ct an das Ende der Liste an.
     * @param ct vom Typ ContentType als neues Element
     * @return true bei Erfolg, sonst false
     */    
    public boolean add(ContentType ct){
        if(ersteFreieStelle<meinFeld.length){
            meinFeld[ersteFreieStelle++] = ct;
        }else{
            this.feldVergroessern(2);
            this.add(ct);
        }
        return true;
    }

    /**
     * Fügt das Element ct an der Position ein.
     * Ein vorhandenes Element und ggf. weitere dann rekursiv werden nach hinten verschoben.
     * @param index als gewünschte Position des Elements
     * @param ct vom Typ ContentTyp als neues Element
     */    
    public void add(int index, ContentType ct){
        if(index>=ersteFreieStelle && index > 0){
            this.add(ct);
        }else if (index > 0){
            if(index<meinFeld.length){
                for(int i = ersteFreieStelle; i > index; i--){
                    meinFeld[i] = meinFeld[i-1];
                }
                meinFeld[index]=ct;
                ersteFreieStelle++;
            }else{
                this.feldVergroessern(2);
                this.add(index,ct);
            }
        }
    }

    /**
     * Löscht das Element ct an der Position.
     * Vorhandene Elemente und ggf. weitere dann rekursiv werden nach vorne verschoben.
     * @param index als Position des zu löschenden Elements
     */    
    public void remove(int index){
        if(index < ersteFreieStelle && index >= 0){
            for(int i = index; i < ersteFreieStelle-1; i++){
                meinFeld[i] = meinFeld[i+1];
            }
            meinFeld[ersteFreieStelle] = null;
            ersteFreieStelle--;
        }
    }

    /**
     * Löscht alle Elemente der ArrayList.
     */  
    public void clear(){
        meinFeld = new Object[meinFeld.length];
        ersteFreieStelle = 0;
    }

    /**
     * Gibt die Anzahl der Elemente in der ArrayList zurück.
     * @return int als die Anzahl der Elemente
     */  
    public int size(){        
        return ersteFreieStelle;
    }

    /**
     * Gibt Auskunft darüber, ob die ArrayList leer ist.
     * @return true falls die ArrayListe keine Elemente enthält, sonst false
     */  
    public boolean isEmpty(){
        return (ersteFreieStelle==0);
    }

    /**
     * Gibt ein spezifisches Element an einer Stelle zurück.
     * Das ELement wird dabei nicht entfernt oder modifiziert.
     * @param index als Position des zu lesenden Elements
     * @return Element vom Typ ContentType der gesuchten Position
     */  
    public ContentType get(int index){
        if(index<meinFeld.length && index>=0){
            return (ContentType)meinFeld[index];
        }else{
            return null;
        }
    }

}

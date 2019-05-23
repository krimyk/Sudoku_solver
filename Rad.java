
import java.util.ArrayList;

/**
 *
 * @author Kristoffer
 */
public class Rad {
    
    Rute[] ruter;
    int storrelse;
    int id;
    ArrayList<Integer> verdier = new ArrayList<Integer>();
    
    Rad(int storrelse, int id){
        ruter = new Rute[storrelse];
        this.storrelse = storrelse;
        this.id = id;
    }
    
    //Metode som plasserer en rute inn i arrayet
    public void kobleMedRute(Rute rute, int plass){
        ruter[plass] = rute;
        if (rute.verdi != 0) {
            verdier.add(rute.verdi);
        }
    }
    
    /**
     * containsmetode som sjekker om en verdi allerede er i raden
     * @param verdiSjekkes  Verdien det blir sjekket mot
     * @return              Returnerer et boolsk uttrykk
     */
    public boolean contains(int verdiSjekkes){
        for(int i=0; i<ruter.length; i++){
            if(ruter[i].verdi == verdiSjekkes){
                return true;
            }
        }
        return false;
    }
    
    public boolean containsInArray(int i){
        if(verdier.contains(i)){
            return true;
        }
        return false;
    }
    
    @Override
    public String toString(){
        String utprint = "Dette er raden: ";
        for(int i=0; i<storrelse; i++){
            if(ruter[i] != null){
                utprint = utprint + " " + ruter[i].toString();
            }
        }
        return utprint;
    }
}


import java.util.ArrayList;

/**
 *
 * @author Kristoffer
 */
public class Boks {
    
    Rute[][] ruter;     //ruter[rad][kolonne]
    int rader;
    int kolonner;
    int storrelse;
    ArrayList<Integer> verdier = new ArrayList<Integer>();
    
    /*
    * Konstruktoren til boks
    * @param hoyde Hoyden paa boksen
    * @param bredde Bredden paa boksen
    */
    Boks(int rader, int kolonner){
        this.rader = rader;
        this.kolonner = kolonner;
        storrelse = rader * kolonner;
        ruter = new Rute[rader][kolonner];
    }
    
    //Metode som plasserer en rute inn i arrayet
    public void kobleMedRute(Rute rute, int rad, int kolonne){
        ruter[rad][kolonne] = rute;
        if (rute.verdi != 0) {
            verdier.add(rute.verdi);
        }
    }
    
    /**
     * containsmetode som sjekker om en verdi allerede er i boksen
     * @param verdiSjekkes  Verdien det blir sjekket mot
     * @return              Returnerer et boolsk uttrykk
     */
    public boolean contains(int verdiSjekkes){
        for(int i=0; i<rader; i++){
            for(int j=0; j<kolonner; j++){
                if(ruter[i][j].verdi == verdiSjekkes){
                    return true;
                }
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
        String utprint =""; // = "Dette er elementene i boksen: ";
        for(int i=0; i<rader; i++){
            for(int j=0; j<kolonner; j++){
                utprint += ruter[i][j].verdi + ", ";
            }
        }
        return utprint;
    }
}
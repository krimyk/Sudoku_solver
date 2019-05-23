/**
 *
 * @author Kristoffer
 */
import java.util.*;

public class Rute {
    
    public int verdi;
    public Boks boks;
    public Rad rad;
    public Kolonne kolonne;
    public Rute nesteRute;
    public Brett brett;
    public int storrelse;
    
    Rute(int verdi, Brett brett, int storrelse){
        this.verdi = verdi;
        this.brett = brett;
        this.storrelse = storrelse;
    }
    
    /**
     * Metode som tar inn et radobjekt og setter en peker til det
     * @param rad
     */
    public void kobleMedRad(Rad rad){
        this.rad = rad;
    }
    public void kobleMedKolonne(Kolonne kolonne){
        this.kolonne = kolonne;
    }
    public void kobleMedBoks(Boks boks){
        this.boks = boks;
    }
    
    public int[] finnAlleMuligeTall(){
        ArrayList<Integer> midlertidigListe = new ArrayList<>();
        for(int i=0; i<=rad.storrelse; i++){
            if(!rad.contains(i) && !kolonne.contains(i) && !boks.contains(i)){
                midlertidigListe.add(i);
            }
        }
        int[] alleMulige = new int[midlertidigListe.size()];
        for(int i=0; i<alleMulige.length; i++){
            alleMulige[i] = midlertidigListe.get(i);
        }
        return alleMulige;
    }
    
    public void fyllUtDenneRuteOgResten(){
        if (verdi == 0) {
            for (int i = 1; i <= storrelse; i++) {
                if (!rad.containsInArray(i) && !kolonne.containsInArray(i) && !boks.containsInArray(i)) {
                    settTallIArray(i);
                    if(nesteRute == null){
                        brett.hentLosninger().settInn(new Losning(brett.hentRuter()));
                        fjernTallFraArray(i);
                    }
                    else {
                        nesteRute.fyllUtDenneRuteOgResten();
                        fjernTallFraArray(i);
                    }
                }
            }
        }
        else if (verdi != 0) {
            if (nesteRute == null) {
                brett.hentLosninger().settInn(new Losning(brett.hentRuter()));
            }
            if (nesteRute != null) {
                nesteRute.fyllUtDenneRuteOgResten();
            }
        }
    }

    public void skrivUtDenneOgNeste(){
        if(nesteRute != null){
            System.out.print(verdi);
            nesteRute.skrivUtDenneOgNeste();
        }
        else if(nesteRute == null){
            System.out.println(verdi);
        }
    }
    
    public void settTallIArray(int i){
        verdi = i;
        rad.verdier.add(i);
        kolonne.verdier.add(i);
        boks.verdier.add(i);
    }
    
    public void fjernTallFraArray(Integer i){
        verdi = 0;
        rad.verdier.remove(i);
        kolonne.verdier.remove(i);
        boks.verdier.remove(i);
    }
    
    @Override
    public String toString(){
        return String.valueOf(verdi);
    }
}


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kristoffer
 */
public class SudokuBeholder {
    
    private final int maksLosninger = 3500;
    private int antallLosninger;
    private Brett brett;
    private ArrayList<Losning> losninger = new ArrayList<Losning>();
    
    SudokuBeholder(Brett brett){
        this.brett = brett;
        antallLosninger = 0;
    }
    
    public void settInn(Losning losning){
        if(antallLosninger < maksLosninger){
            losninger.add(losning);
        }
        antallLosninger++;
    }
    
    public Losning taUt(int plass) {
        if (plass < losninger.size()) {
            return losninger.get(plass);
        }
        return null;
    }
    
    public int hentAntallLosninger(){
        return antallLosninger;
    }
    
    public void skrivUtAlleLosninger() {
        try {
            if (antallLosninger < 3) {
                for (Losning l : losninger) {
                    int[][] verdier = l.hentVerdier();
                    System.out.println(brett.antRader);
                    System.out.println(brett.antKolonner);
                    for (int i = 0; i < verdier.length; i++) {
                        for (int j = 0; j < verdier.length; j++) {
                            System.out.print(verdiTilTegn(verdier[i][j]));
                        }
                        System.out.println();
                    }
                    System.out.println();
                }
            } else {
                System.out.println("Dette er losningene som ble funnet");
                for (int i = 1; i <= antallLosninger; i++) {
                    if (i <= maksLosninger) {
                        System.out.print(i + ": ");
                        int[][] verdier = losninger.get(i - 1).hentVerdier();
                        for (int j = 0; j < verdier.length; j++) {
                            for (int k = 0; k < verdier.length; k++) {
                                System.out.print(verdiTilTegn(verdier[j][k]));
                            }
                            System.out.print("//");
                        }
                        System.out.println();
                    }
                }
            }
        } catch (UgyldigVerdiUnntak e) {
            System.out.println("Feil med utprint av verdier");
        }
    }
    
    public void skrivTilFil(String filnavn){
        try {
            PrintWriter pw = new PrintWriter(filnavn);
            if (antallLosninger < 3) {
                for (Losning l : losninger) {
                    int[][] verdier = l.hentVerdier();
                    pw.println(brett.antRader);
                    pw.println(brett.antKolonner);
                    for (int i = 0; i < verdier.length; i++) {
                        for (int j = 0; j < verdier.length; j++) {
                            pw.print(verdiTilTegn(verdier[i][j]));
                        }
                        pw.println();
                    }
                    pw.println();
                }
            } else {
                pw.println("Dette er losningene som ble funnet");
                for (int i = 1; i <= antallLosninger; i++) {
                    if (i <= maksLosninger) {
                        pw.print(i + ": ");
                        int[][] verdier = losninger.get(i - 1).hentVerdier();
                        for (int j = 0; j < verdier.length; j++) {
                            for (int k = 0; k < verdier.length; k++) {
                                pw.print(verdiTilTegn(verdier[j][k]));
                            }
                            pw.print("//");
                        }
                        pw.println();
                    }
                }
            }
            pw.println("Antall losninger som er funnet: " + antallLosninger + " stk.");
            pw.close();
        } catch (UgyldigVerdiUnntak e) {
            System.out.println("Feil med utprint av verdier");
        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException");
        }
    }
    
    /**
     * Oversetter en tallverdi (int) til et tegn (char)
     *
     * @param verdi     verdien som skal oversettes
     * @return          tegnet som verdien tilsvarer
     * @throws UgyldigVerdiUnntak
     */
    public char verdiTilTegn(int verdi/*, char tom*/) throws UgyldigVerdiUnntak {
        if (verdi == 0) {                           // tom
            return ' ';
        } else if (1 <= verdi && verdi <= 9) {      // tegn er i [1, 9]
            return (char) (verdi + '0');
        } else if (10 <= verdi && verdi <= 35) {    // tegn er i [A, Z]
            return (char) (verdi + 'A' - 10);
        } else if (verdi == 36) {                   // tegn er @
            return '@';
        } else if (verdi == 37) {                   // tegn er #
            return '#';
        } else if (verdi == 38) {                   // tegn er &
            return '&';
        } else if (39 <= verdi && verdi <= 64) {    // tegn er i [a, z]
            return (char) (verdi + 'a' - 39);
        } else {                                    // tegn er ugyldig
            throw new UgyldigVerdiUnntak();    // HUSK DEFINISJON AV UNNTAKSKLASSE
        }
    }
}

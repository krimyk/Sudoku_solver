/**
 *
 * @author Kristoffer
 */
import java.util.*;
import java.io.*;

public class Brett {

    Rute[][] ruter;     //ruter[rad][kolonne]
    Boks[][] bokser;    //bokser[rad][kolonne]
    Rad[] rader;        //Array som inneholder radene
    Kolonne[] kolonner; //Array som inneholder kolonnene
    int storrelse;      //paa et 6x6 vil 6 vaere storrelsen
    int antRader;       //antall rader i en boks
    int antKolonner;    //antall kolonner i en boks
    SudokuBeholder losninger;   //beholder som tar vare paa losninger

    //Konstruktor av brett som tar inn filnavn og kjorer lesFil()
    Brett(String filnavn){
        try{
            lesFil(filnavn);
            opprettDatastruktur();
        }
        catch(UgyldigStorrelseUnntak e){
            System.out.println("Storrelsen paa brettet du prover aa lese inn er ugyldig");
        }
        catch(UgyldigVerdiUnntak f){
            System.out.println("En av verdiene paa brettet du prover aa lese inn er ikke gyldig");
        }
        catch(UgyldigAntallTegnUnntak g){
            System.out.println("Det er et ugyldig antall tegn i filen");
        }
    }

    public void losBrett(String filnavnUt){
        losninger = new SudokuBeholder(this);
        ruter[0][0].fyllUtDenneRuteOgResten();
        losninger.skrivUtAlleLosninger();
        System.out.println("Antall losninger funnet: " + losninger.hentAntallLosninger());
        losninger.skrivTilFil(filnavnUt);
    }

    /**
     * Metoden leser inn filen og oppretter alle rutene paa brettet og gir dem riktig verdi
     * @param filnavn   Filnavnet skla vaere filen som skal leses inn
     */
    private void lesFil(String filnavn) throws UgyldigStorrelseUnntak, UgyldigVerdiUnntak, UgyldigAntallTegnUnntak{
        try{
            File fil = new File(filnavn);
            Scanner innleser = new Scanner(fil);
            int teller = -2;
            int rad = 1;
            while(innleser.hasNextLine()){
                //leser inn antall kolonner og rader forst
                if(teller == -2){
                    antRader = Integer.parseInt(innleser.nextLine());
                    teller ++;
                }
                else if(teller == -1){
                    antKolonner = Integer.parseInt(innleser.nextLine());
                    storrelse = antRader * antKolonner;
                    if(storrelse > 64){
                        throw new UgyldigStorrelseUnntak();
                    }
                    ruter = new Rute[storrelse][storrelse];
                    teller ++;
                }
                else if(teller > -1){
                    String input = innleser.nextLine();
                    for(int i = 0; i < input.length(); i++){
                        if(tegnTilVerdi(input.charAt(i)) > storrelse){
                            throw new UgyldigVerdiUnntak();
                        }
                        if (i > storrelse) {
                            throw new UgyldigAntallTegnUnntak();
                        }
                        ruter[teller][i] = new Rute(tegnTilVerdi(input.charAt(i)), this, storrelse);
                        if (i == 0 && teller != 0) {
                            ruter[teller - 1][storrelse-1].nesteRute = ruter[teller][i];    //Setter neste for siste rute paa raden
                        }
                        else if(teller == storrelse-1 && i == storrelse-1){
                            ruter[teller][i].nesteRute = null;      //Setter siste sin neste rute til null
                        }
                        else if(teller != 0){
                            ruter[teller][i - 1].nesteRute = ruter[teller][i];
                        }
                        else if(teller == 0){
                            if(i != 0){
                                ruter[teller][i - 1].nesteRute = ruter[teller][i];
                            }
                        }
                    }
                    teller ++;
                }
            }
        }
        catch(FileNotFoundException e){
            System.out.println("Filen du prover aa lese inn er ikke tilgjengelig");
        }
        catch (UgyldigVerdiUnntak w){
            System.out.println("Det er et ugyldig tegn i filen");
        }
        catch (ArrayIndexOutOfBoundsException a){
            System.out.println("Filen har et ugyldig antall tegn i seg");
        }
    }

    /**
     * En metode som oppretter riktig anntall rader, bokser og kolonner
     * Deretter lager den pekere mellom rutene og radene, boksene og kolonnene
     */
    private void opprettDatastruktur(){
        try{
            //Opprettelse av BoksArrayet
            bokser = new Boks[antKolonner][antRader];
            for(int i=0; i<antKolonner; i++){
                for(int j=0; j<antRader; j++){
                    bokser[i][j] = new Boks(antRader, antKolonner);
                }
            }

            //Opprettelse av KolonneArrayet
            kolonner = new Kolonne[storrelse];
            for(int i=0; i<storrelse; i++){
                kolonner[i] = new Kolonne(storrelse, i);
            }

            //Opprettelse av RadArrayet
            rader = new Rad[storrelse];
            for(int i=0; i<storrelse; i++){
                rader[i] = new Rad(storrelse, i);
            }

            //Her ordner jeg pekerne, for naa er alle objektene opprettet
            //Ideen min er at jeg kjorer gjennom ruter(arrayet) og plasserer hver
                //rute i sine respektive kolonner og rader.

            //Denne lokken kobler sammen rutene med radene og kolonnene
            for(int i=0; i<storrelse; i++){
                for(int j=0; j<storrelse; j++){
                    rader[i].kobleMedRute(ruter[i][j], j);
                    kolonner[j].kobleMedRute(ruter[i][j], i);
                    ruter[i][j].kobleMedRad(rader[i]);
                    ruter[i][j].kobleMedKolonne(kolonner[j]);
                }
            }

            //Denne lokken kobler sammen rutene med boksene
            int radTeller = 0;
            int kolonneTeller = 0;
            int kolonneBoks = 0;
            int radBoks = 0;
            for(int i=0; i<storrelse; i++){
                for(int j=0; j<storrelse; j++){
                    if(kolonneTeller < antKolonner){
                        bokser[radBoks][kolonneBoks].kobleMedRute(ruter[i][j], radTeller, kolonneTeller);
                        ruter[i][j].kobleMedBoks(bokser[radBoks][kolonneBoks]);
                        kolonneTeller ++;
                    }
                    else{
                        kolonneTeller = 0;
                        kolonneBoks ++;
                        bokser[radBoks][kolonneBoks].kobleMedRute(ruter[i][j], radTeller, kolonneTeller);
                        ruter[i][j].kobleMedBoks(bokser[radBoks][kolonneBoks]);
                        kolonneTeller ++;
                    }
                }
                if(radTeller < antRader-1){
                    kolonneTeller = 0;
                    kolonneBoks = 0;
                    radTeller ++;
                }
                else{
                    kolonneTeller = 0;
                    kolonneBoks = 0;
                    radTeller = 0;
                    radBoks ++;
                }
            }
        }
        catch(NullPointerException n){
            System.out.println("Opprettelsen av datastruktur feilet pga. Null-objekt");
        }
    }

    /**
     * Metode som skriver ut gjeldende sudokubrett til terminalen
     * Utskriften skriver ut bokstaver etter samme regler som i innlesingen.
     * Dvs. tallene over 9 er representert ved bokstaver eller tegn.
     * Tomme ruter blir her skrevet ut som et tomt felt.
     */
    public void skrivUtBrett(){
        try{
            System.out.println("Dette er gjeldende Sudokubrett:");
            int radTeller = 0;
            int kolonneTeller = 0;
            int antStreklinjer = 0;
            for(int i=0; i<storrelse; i++){
                for(int j=0; j<storrelse; j++){
                    if(kolonneTeller == antKolonner){
                        System.out.print("|");
                        System.out.print(verdiTilTegn(ruter[i][j].verdi));
                        kolonneTeller = 1;
                    }
                    else{
                        System.out.print(verdiTilTegn(ruter[i][j].verdi));
                        kolonneTeller ++;
                    }
                }
                if(radTeller == antRader-1 && antStreklinjer <antKolonner-1){
                    kolonneTeller = 0;
                    System.out.println();
                    int plussTeller = 0;
                    for(int k=0; k<storrelse; k++){
                        if(plussTeller == antKolonner){
                            System.out.print("+");
                            plussTeller = 0;
                            k -= 1;
                        }
                        else{
                            System.out.print("-");
                            plussTeller++;
                        }
                    }
                    antStreklinjer ++;
                    System.out.println();
                    radTeller = 0;
                }
                else{
                    kolonneTeller = 0;
                    radTeller ++;
                    System.out.println();
                }
            }
        }
        catch (UgyldigVerdiUnntak q){
            System.out.println("Kunne ikke skrive ut paa grunn av en ugyldig verdi i brettet");
        }
    }

    /**
     * Oversetter et tegn (char) til en tallverdi (int)
     *
     * @param tegn      tegnet som skal oversettes
     * @return          verdien som tegnet tilsvarer
     * @throws UgyldigVerdiUnntak
     */
    public int tegnTilVerdi(char tegn) throws UgyldigVerdiUnntak{
        if (tegn == '.') {                // tom rute, DENNE KONSTANTEN MAA DEKLARERES
            return 0;
        } else if ('1' <= tegn && tegn <= '9') {    // tegn er i [1, 9]
            return tegn - '0';
        } else if ('A' <= tegn && tegn <= 'Z') {    // tegn er i [A, Z]
            return tegn - 'A' + 10;
        } else if (tegn == '@') {                   // tegn er @
            return 36;
        } else if (tegn == '#') {                   // tegn er #
            return 37;
        } else if (tegn == '&') {                   // tegn er &
            return 38;
        } else if ('a' <= tegn && tegn <= 'z') {    // tegn er i [a, z]
            return tegn - 'a' + 39;
        } else {                                    // tegn er ugyldig
            throw new UgyldigVerdiUnntak();
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

    public Rute nesteLedigeRute(int rad, int kolonne) throws FantIngenLedigUnntak{
        Rute nesteLedige = ruter[rad][kolonne];
        int startRad = rad;
        int startKolonne = kolonne;
        sok:
        {
            while(startRad < storrelse) {
                while(startKolonne < storrelse) {
                    if (ruter[startRad][startKolonne].verdi == 0) {
                        nesteLedige = ruter[startRad][startKolonne];
                        break sok;
                    }
                    startKolonne++;
                }
                startRad++;
            }
            if(nesteLedige == ruter[rad][kolonne]) {
                throw new FantIngenLedigUnntak();
            }
        }
        return nesteLedige;
    }

    public Rute[][] hentRuter(){
        return ruter;
    }

    public SudokuBeholder hentLosninger(){
        return losninger;
    }
}


import java.util.Arrays;

/**
 *
 * @author Kristoffer
 */

public class Sudokuloser {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        Brett sudokuBrett = new Brett(args[0]);
        
        sudokuBrett.skrivUtBrett();
        
        sudokuBrett.ruter[0][0].skrivUtDenneOgNeste();
        
        sudokuBrett.losBrett(args[1]);

    }
    
}

/**
 *
 * @author Kristoffer
 */
public class Losning {
    private int storrelse;
    private int[][] verdier;
    
    Losning(Rute[][] ruter){
        storrelse = ruter.length;
        verdier = new int[storrelse][storrelse];
        
        for(int i = 0; i < ruter.length; i++){
            for(int j = 0; j < ruter.length; j++){
                verdier[i][j] = ruter[i][j].verdi;
            }
        }
    }
    
    public int[][] hentVerdier(){
        return verdier;
    }
    
    @Override
    public String toString(){
        String losning = "";
        for(int[] i : verdier){
            for(int j : i){
                losning += j;
            }
        }
        return losning;
    }
}

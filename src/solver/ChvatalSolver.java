package solver;

import util.ElementSet;

/**
 *
 * @author cooneyth
 */
public class ChvatalSolver extends GreedySolver{
    
    public ChvatalSolver(){
        super();
        _name = "Chvatal";
    }
    
    
    @Override
    public ElementSet nextBestSet(){
        double CoverageCounter = 0;
        double NumbaJuanCoverageCounter = 0.5;
        double NumbaJuanCost = Double.MAX_VALUE;
        ElementSet NumbaJuanSet = null;        
        //for every element set
         for(ElementSet x : _SumOfSets){
             CoverageCounter = 0;
             ElementSet temp = x;         
            //check if any element in the set matched an element in uncovered
            for (int i : _ssUncovered){
                if(temp._ssElements.contains(i)){
                    CoverageCounter++;                }
            }
            //check if there exists an element with better cost/coverage
            if(!_solnSets.contains(x) && (double)CoverageCounter>0 && ((double)temp.getCost()/(double)CoverageCounter) < ((double)NumbaJuanCost/(double)NumbaJuanCoverageCounter)){
                NumbaJuanCost = temp.getCost();
                NumbaJuanCoverageCounter = CoverageCounter;
                NumbaJuanSet = x;
            }    
        }
        return NumbaJuanSet;
    }
}    
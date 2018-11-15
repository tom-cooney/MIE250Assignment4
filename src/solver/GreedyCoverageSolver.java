package solver;

import util.ElementSet;

/**
 *
 * @author cooneyth
 */
public class GreedyCoverageSolver extends GreedySolver {

	public GreedyCoverageSolver() {
		super();
		_name = "Coverage";
		
		

	}

	@Override
    public ElementSet nextBestSet(){
        ElementSet NumbaJuanSet = null;
        double NumbaJuanSetCounter = 0;
        double PresentSetCounter = 0;        
        //for every element set
        for(ElementSet x : _SumOfSets){
            //ElementSet temp = x;
            PresentSetCounter = 0;
            //how many uncovered sets are covered by the cuirrent set
            //check if any element in the set macthed an element in uncovered
            for (int i : _ssUncovered){
                if(x._ssElements.contains(i)){
                    //increase coverage
                    PresentSetCounter++;
                }                
            }
            if(PresentSetCounter > NumbaJuanSetCounter){
                    NumbaJuanSet = x;
                   
                    NumbaJuanSetCounter = PresentSetCounter;
                }
        }
        
        return NumbaJuanSet;      
     
    }
}
package solver;

import util.ElementSet;

/**
 *
 * @author cooneyth
 */
public class GreedyCostSolver extends GreedySolver{
    
    public GreedyCostSolver (){
        super();
        _name = "Cost";
        
        
    
    }
    
    
    @Override
    public ElementSet nextBestSet(){
        double PresentCost = 0;
        double PresentSetCounter = 0;
        double NumbaJuanCost = Double.MAX_VALUE;
        ElementSet NumbaJuanSet = null;
        //for every element set
        for(ElementSet x : _SumOfSets){
            ElementSet temp = x;
            PresentCost = 0;
            PresentSetCounter = 0;
            //check if any element in the set matches an element in uncovered
            for (int i : _ssUncovered){   
                if(temp._ssElements.contains(i)){                    
                    PresentSetCounter++;
                    //if at least one element in uncovered is covered                    
                    if(PresentSetCounter>0){
                        //if the cost of the discovered set is less than present best cost update best cost and best set
                        if(temp.getCost()<NumbaJuanCost){
                            NumbaJuanSet = temp;
                            NumbaJuanCost = temp.getCost();
                        }                            
                    }
                }
            }
        }
        return NumbaJuanSet;
    }
}
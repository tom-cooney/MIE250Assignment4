package solver;

import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;
import model.SCPModel;
import util.ElementSet;


/**
 * This is the main method that all solvers inherit from. It is important to
 * note how this solver calls nextBestSet() polymorphically! Subclasses should
 * *not* override solver(), they need only override nextBestSet().
 *
 * We'll assume models are well-defined here and do not specify Exceptions to
 * throw.
 *
 * @author ssanner@mie.utoronto.ca
 *
 */
public abstract class GreedySolver {

    protected String _name;			  // name of algorithm type
    protected double _alpha;          // minimum required coverage level in range [0,1]
    protected SCPModel _model;        // the SCP model we're currently operating on
    protected double _objFn;          // objective function value (*total cost sum* of all sets used)
    protected double _coverage;       // actual coverage fraction achieved
    protected long _compTime;         // computation time (ms)
    protected TreeSet<Integer> _ssUncovered;
    protected TreeSet<ElementSet> _solnSets;
    protected TreeSet<ElementSet> _SumOfSets;
    protected double numUni;

    // Basic setter (only one needed)
    public void setMinCoverage(double alpha) {
        _alpha = alpha;
    }

    public void setModel(SCPModel model) {
        _model = model;
    }

    // Basic getters
    public double getMinCoverage() {
        return _alpha;
    }

    public double getObjFn() {
        return _objFn;
    }

    public double getCoverage() {
        return _coverage;
    }

    public long getCompTime() {
        return _compTime;
    }

    public String getName() {
        return _name;
    }

    // TODO: Add any helper methods you need
    /**
     * Run the simple greedy heuristic -- add the next best set until either (1)
     * The coverage level is reached, or (2) There is no set that can increase
     * the coverage.
     */
    public void solve() {
    	System.out.println("Running '" + _name + "'...");
        // Reset the solver
        reset();
        int num_to_cover = (int)Math.ceil(_alpha * _model.getElementsNum());
        int num_can_leave_uncovered = _model.getElementsNum() - num_to_cover;
        double currentCov = 0d;
        numUni = _model.getElements().size();
        // Begin the greedy selection loop
        long start = System.currentTimeMillis();
     // NOTE: In order to match the solution, pay attention to the following
     		//       calculations (where you have to replace ALL-CAPS parts)
     		//
     		// int num_to_cover = (int)Math.ceil(_alpha * TOTAL_NUMBER_OF_ELEMENTS_IN_SCPMODEL);
     		// int num_can_leave_uncovered = TOTAL_NUMBER_OF_ELEMENTS_IN_SCPMODEL - num_to_cover;
     		//
     		// while (NUM_ELEMENTS_NOT_COVERED > num_can_leave_uncovered 
     		//        && ALL_POSSIBLE_SETS_HAVE_NOT_BEEN_SELECTED)
     		//
     		//      Call nextBestSet() to get the next best ElementSet to add (if there is one)
     		// 		Update solution and local members
        //currentCov <= _alpha
        while (_ssUncovered.size() > num_can_leave_uncovered && _solnSets.size() != _model.getElementSetsNum() ) {
            //      Call nextBestSet() to get the next best ElementSet to add (if there is one)
            //      Update solution and local members            
            ElementSet _nextBest = nextBestSet();            
            if (_nextBest != null) {  
            	 System.out.println("- Selected: " + _nextBest);
                _solnSets.add(_nextBest);               
                _ssUncovered.removeAll(_nextBest.getElements());                
                _SumOfSets.remove(_nextBest);                
                //currentCov = (double)(numUni - _ssUncovered.size())/(double)numUni;
                //System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&" + currentCov);
                _objFn = _objFn + _nextBest.getCost();               
            }
        }
       

        _coverage = (double) (numUni - _ssUncovered.size()) / (double)numUni; // TODO: Correct this, should be coverage of solution found
       
        _compTime = System.currentTimeMillis() - start;
        if (_coverage < _alpha) {
            System.out.format("\nWARNING: Impossible to reach %.2f%% coverage level.\n", 100 * _alpha);
        }
//        for (ElementSet set : _solnSets)
//            System.out.println("- Selected: " + set);
        System.out.println("Done.");
    }

    /**
     * Returns the next best set to add to the solution according to the
     * heuristic being used.
     *
     * NOTE 1: This is the **only** method to be implemented in child classes.
     *
     * NOTE 2: If no set can improve the solution, returns null to allow the
     * greedy algorithm to terminate.
     *
     * NOTE 3: This references an ElementSet class which is a tuple of (Set ID,
     * Cost, Integer elements to cover) which you must define.
     *
     * @return
     */
    public abstract ElementSet nextBestSet(); // Abstract b/c it must be implemented by subclasses

    /**
     * Print the solution
     *
     */
    public void print() {
        System.out.println("\n'" + getName() + "' results:");
        System.out.format("'" + getName() + "'   Time to solve: %dms\n", _compTime);
        System.out.format("'" + getName() + "'   Objective function value: %.2f\n", _objFn);
        System.out.format("'" + getName() + "'   Coverage level: %.2f%% (%.2f%% minimum)\n", 100 * _coverage, 100 * _alpha);
        System.out.format("'" + getName() + "'   Number of sets selected: %d\n", _solnSets.size());
        System.out.format("'" + getName() + "'   Sets selected: ");
        TreeSet<ElementSet> _solnSets2 = new TreeSet<ElementSet>(_solnSets);
        for (ElementSet s : _solnSets2) {
            System.out.print(s.getId() + " ");
        }
     
        
        System.out.println("\n");
    }

    /**
     * Print the solution performance metrics as a row
     *
     */
    public void printRowMetrics() {
        System.out.format("%-25s%12d%15.4f%17.2f\n", getName(), _compTime, _objFn, 100 * _coverage);
    }

    public void reset() {
        _objFn = 0.0;
        _coverage = 0.0;
        _compTime = 0;
        numUni = _model.getElements().size();
        _ssUncovered = _model.getElements();
        _SumOfSets = new TreeSet<ElementSet>(_model.getElementSets());
        _solnSets = new TreeSet<ElementSet>();
        
    }
}
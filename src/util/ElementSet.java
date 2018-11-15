package util;

import java.util.Collection;
/**
 *
 * @author cooneyth
 */
import java.util.HashSet;
import java.util.TreeSet;


public class ElementSet implements Comparable{
    
	
	
    private int _iId;
    private double _dCost;
    public HashSet<Integer> _ssElements;
    
    public ElementSet(int id, double cost, Collection<Integer> elements){
        this._iId = id;
        this._dCost = cost;
        _ssElements = new HashSet<Integer>(elements);
    }
    
    
    public int getId(){
        return _iId;
    }
    
    
    public double getCost(){
        return _dCost;
    }
    
    
    public HashSet<Integer> getElements(){
        return _ssElements;
    }
    
    public TreeSet<Integer> getSortedElements(){
    	return new TreeSet<Integer>(_ssElements);
    }
    
   // public HashSet<Integer> newElements(){
    //    return new HashSet<Integer>(_ssElements);
   // }
    
    
    @Override
    public boolean equals(Object o){
        
        ElementSet x = (ElementSet) o;
        if (x == null)
            return false;
        
        if (this._iId == x.getId() && this._dCost == x.getCost() && this._ssElements.equals(x._ssElements))
            return true;
        else
            return false;
    
    }
    
    
    @Override
    public String toString(){
        return String.format("Set ID: %3d   Cost: %6.2f   Element IDs: %s", _iId, _dCost, getSortedElements());
    }
    
    
    @Override
    public int compareTo(Object o){
        
        if(o instanceof ElementSet){
            ElementSet x = (ElementSet) o;        
             if (this._iId == x.getId())
                return 0;
            else if (this._iId < x.getId())
                return -1;
             else if (this._iId> x.getId())
                return 1;
        }
       
        return 0;
    }
}
package model;

/**
 *
 * @author cooneyth
 */
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Collection;

import util.ElementSet;

public class SCPModel {
    
    private TreeSet<ElementSet> _ssElementSet;
    private TreeSet<Integer> _ssElements;
    
    
    
    public SCPModel(){
        _ssElementSet = new TreeSet<ElementSet>();
        _ssElements = new TreeSet<Integer>();
    }    
    
    
    public int getElementsNum(){
        return _ssElements.size();
    }
    
    
    public int getElementSetsNum(){
        return _ssElementSet.size();
    }
    
    
    public TreeSet<Integer> getElements(){
        
        return new TreeSet<>(_ssElements);
    }
    
     public TreeSet<ElementSet> getElementSets(){
         return _ssElementSet;
     }
    
    
    public void addSetToCover (int id, double cost, Collection<Integer> e){
        //System.out.println(e);
        ElementSet newew = new ElementSet(id, cost, e);
        //System.out.println(newew);
        _ssElementSet.add(newew);
        _ssElements.addAll(e);
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("Weighted SCP model:\n");
        sb.append("---------------------\n");
        sb.append("Number of elements (n): ");
        sb.append(_ssElements.size());
        sb.append("\n");
        sb.append("Number of sets (m): ");
        sb.append(_ssElementSet.size());
        sb.append("\n\n");
        sb.append("Set details:\n");
        sb.append("----------------------------------------------------------\n");
        //convert elementsets from storing elements in a hashset to a treeset right here so order mathces solution
        
        for(ElementSet x : this.getElementSets()){
            sb.append(x.toString());
            sb.append("\n");
        }    
        return sb.toString();
    }
}


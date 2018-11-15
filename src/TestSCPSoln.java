import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import soln.model.SCPModel;
import soln.solver.ChvatalSolver;
import soln.solver.GreedyCostSolver;
import soln.solver.GreedyCoverageSolver;
import soln.solver.GreedySolver;
import soln.util.ElementSet;

import java.io.*;

/** Example testing class to show solution, identical to TestSCP except for classes used.
 * 
 * @author ssanner@mie.utoronto.ca
 *
 */
public class TestSCPSoln {
	
	public static BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
	
	public static void main(String[] args) throws IOException {

		
		SCPModel model = new SCPModel();
		
		
		
		
		/*
		
		// Create a weighted SCP with
		//   Set 1: weight 3.0, elements { 1, 3, 5, 7, 9 }
		//   Set 2: weight 2.0, elements { 1, 5, 9 }
		//   Set 3: weight 2.0, elements { 5, 7, 9 }
		//   Set 4: weight 5.0, elements { 2, 4, 6, 8, 100 }
		//   Set 5: weight 2.0, elements { 2, 6, 100 }
		//   Set 6: weight 2.0, elements { 4, 8 }
		model.addSetToCover(6, 2.0, Arrays.asList(new Integer[] {4,8}));
		model.addSetToCover(5, 2.0, Arrays.asList(new Integer[] {2,6,100}));
		model.addSetToCover(4, 5.0, Arrays.asList(new Integer[] {2,4,6,8,100}));
		model.addSetToCover(3, 2.0, Arrays.asList(new Integer[] {5,7,9}));
		model.addSetToCover(2, 2.0, Arrays.asList(new Integer[] {1,5,9}));
		model.addSetToCover(1, 3.0, Arrays.asList(new Integer[] {1,3,5,7,9}));
		
		*/
		
		File f = new File (".\\SCPInstances\\SCP_S_50-40.txt");
		
		model = TestSCPSoln.readFromFile(f);
		
		GreedyCoverageSolver CoverageMethod = new GreedyCoverageSolver();
		GreedyCostSolver CostMethod = new GreedyCostSolver();
		ChvatalSolver ChvatalMethod = new ChvatalSolver();
		
		List<GreedySolver> solvers = Arrays.asList(new GreedySolver[] {CoverageMethod, CostMethod, ChvatalMethod});
		printComparison(solvers, model, 0.5);
		System.out.println("==========================================================================");
		printComparison(solvers, model, 0.3);
		System.out.println("==========================================================================");
		printComparison(solvers, model, 0.9);
	}
		
	
	//public static BufferedReader cin2 = new BufferedReader(new InputStreamReader(System.in));
		public static SCPModel readFromFile(File file) throws IOException{
			BufferedReader fin = new BufferedReader(new FileReader (file));
			SCPModel model2 = new SCPModel();
			ArrayList<String> list = new ArrayList<String>();
			String str;
			while((str = fin.readLine()) != null) {
				list.add(str);
			}
			list.remove(0);
			list.remove(0);
			Double cost = 0.0;
			int i = 1;
			List<Integer> intList = new ArrayList();
			for(String str1 : list) {
				if(str1.contains(".")){
					cost = Double.parseDouble(str1);
				}
				else {
					if(str1.equals("0")) {
						model2.addSetToCover(i, cost, intList);
						i++;
						intList.clear();
					}
					else {
						intList.add(Integer.parseInt(str1));
					}
				}
			}
			fin.close();
			return model2;
		}
	
	
	
	// set minimum coverage level for solution methods
	public static void printComparison(List<GreedySolver> solvers, SCPModel model, double alpha) {
			
		// Show the model
		System.out.println(model);
		
		// Run all solvers and record winners
		GreedySolver timeWinner = null;
		long minTime = Long.MAX_VALUE;
		
		GreedySolver objWinner = null;
		double minObj = Double.MAX_VALUE;
		
		GreedySolver covWinner = null;
		double maxCov = -Double.MAX_VALUE;
		
		for (GreedySolver s : solvers) {
			s.setMinCoverage(alpha);
			s.setModel(model);
			s.solve();
			s.print();
			s.printRowMetrics();
			
			if (minTime > s.getCompTime()) {
				minTime = s.getCompTime();
				timeWinner = s;
			}
			
			if (minObj > s.getObjFn()) {
				minObj = s.getObjFn();
				objWinner = s;
			}
			
			if (maxCov < s.getCoverage()) {
				maxCov = s.getCoverage();
				covWinner = s;
			}
		}

		System.out.format("\nAlpha: %.2f%%\n\n", 100*alpha);
		System.out.println("Algorithm                   Time (ms)     Obj Fn Val     Coverage (%)");
		System.out.println("---------------------------------------------------------------------");
		System.out.println("---------------------------------------------------------------------");
		for (GreedySolver s : solvers)
			s.printRowMetrics();
		System.out.format("%-25s%12s%15s%17s\n", "Category winner", timeWinner.getName(), objWinner.getName(), covWinner.getName());
		System.out.println("---------------------------------------------------------------------\n");
		
		String overall = "Unclear";
		if (timeWinner.getName().equals(objWinner.getName()) && 
			objWinner.getName().equals(covWinner.getName()))
			overall = timeWinner.getName();
		
		System.out.println("Overall winner: " + overall + "\n");
	}
	
}

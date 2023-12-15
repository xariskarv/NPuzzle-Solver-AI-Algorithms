
public class Main {

	public static final int N = 3;        //Size of the problem (N^2 - 1) puzzle.
	public static final int BREADTH = 1;  //Breadth-first-search choice;
	public static final int DEPTH = 2;    //Depth-first-search choice;
	public static final int BEST = 3;     //Best-first-search (heuristic) choice.
	public static final int ASTAR = 4;    //A* search choice.
	public static final int FIRST_ROW = 0;
	public static final int FIRST_COLUMN = 0;
	public static final int[][] SOLUTION_PUZZLE =  {{1,2,3},{4,5,6},{7,8,0}};
	public static int METHOD = 0;         //The method which is used for the searching (bfs,dfs,best,astar).
	
	public static void main(String[] args) {
		
		if(args.length != 3)
		{
			Message.syntax_message();
		}
		else
		{
			NPuzzle game = new NPuzzle();
			
			//Get the search method which has been inputed.
			METHOD = NPuzzle.getMethod(args[0]);
			
			//Get the input file of the puzzle description.
			String in = args[1];
			
			//Create the first node (root) of the tree.
			TreeNode node = new TreeNode();
			node.createRoot(in);
			
			//Get the output file in which the solution will be written.
			String out = args[2];
			
			//Depending on the method value, perform the right search algorithm.
				if(METHOD == BREADTH)
				{
					game.breadth_first_search(node,out);
				}
				else if(METHOD == DEPTH)
				{
					game.depth_first_search(node,out);
				}
				else if(METHOD == BEST)
				{
					game.best_first_search(node,out);
				}
				else if(METHOD == ASTAR)
				{
					game.astar_search(node,out);
				}
		}
	}
}

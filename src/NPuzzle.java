import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/*This class executes the 4 possible search algorithms
 * Breadth-First-Search(BFS)
 * Depth-First-Search(DFS)
 * Best-First-Search
 * A* Search
 * 
 * The search algorithms are the same but each algorithm
 * uses a different data structure to travarse the search
 * tree. 
 */
public class NPuzzle {
	
	/*This function applies bfs(breadth-first-search)
	 * algorithm to solve the problem.
	 */
	public void breadth_first_search(TreeNode root, String out)
	{
		Queue<TreeNode> queue = new LinkedList<TreeNode>();
		int steps =0;
		double duration;
	
		queue.add(root);
		
		long startTime = System.currentTimeMillis();
				
		while(!queue.isEmpty())
		{
			TreeNode temp = queue.poll();
			temp.createChildren();
			
			if(!temp.getChildren().isEmpty())
			{
				for(int i=0; i<temp.getChildren().size(); i++)
				{
					queue.add(temp.getChildren().get(i));	
				}	
			}
			
			if(temp.isSolution())
			{
				double endTime = System.currentTimeMillis();
				
				//Converting milliseconds to seconds.
				duration = (endTime - startTime) / 1000;
				
				//Write the solution to the output file.
				temp.writeToFile(out, steps, duration);

				System.out.println("The solution has been printed to " + out + " file");
				break;
			}
			steps++;
		}
	}
	
	/*This function applies dfs(depth-first-search)
	 * algorithm to solve the problem.
	 */
	public void depth_first_search(TreeNode root, String out)
	{
		Deque<TreeNode> deque = new ArrayDeque<TreeNode>();
		int steps=0;
		double duration;
		
		deque.add(root);
		
		long startTime = System.currentTimeMillis();
		
		while(!deque.isEmpty())
		{
			TreeNode temp = deque.pollLast();
			temp.createChildren();
			
			if(!temp.getChildren().isEmpty())
			{
				for(int i=0; i<temp.getChildren().size(); i++)
				{
					deque.addLast(temp.getChildren().get(i));
				}
			}
			
			if(temp.isSolution())
			{
				double endTime = System.currentTimeMillis();
				
				//Converting milliseconds to seconds.
				duration = (endTime - startTime) / 1000;
			
				temp.writeToFile(out, steps, duration);

				System.out.println("The solution has been printed to " + out + " file");
				break;
			}
			steps++;
		}
	}
	
	/*This function applies best-first-search
	 * algorithm to solve the problem.
	 */
	public void best_first_search(TreeNode root, String out)
	{
		PriorityQueue<TreeNode> pQueue = new PriorityQueue<TreeNode>();
		int steps = 0;
		double duration;
		
		pQueue.add(root);
		
		long startTime = System.currentTimeMillis();
		
		while(!pQueue.isEmpty())
		{
			TreeNode temp = pQueue.poll();
			temp.createChildren();
			
			if(!temp.getChildren().isEmpty())
			{
				for(int i=0; i<temp.getChildren().size(); i++)
				{
					pQueue.add(temp.getChildren().get(i));
				}
			}
			
			if(temp.isSolution())
			{
				double endTime = System.currentTimeMillis();
				
				//Converting milliseconds to seconds.
				duration = (endTime - startTime) / 1000;
				
				temp.writeToFile(out, steps, duration);

				System.out.println("The solution has been printed to " + out + " file");
				break;
			}
			steps++;
		}
	}
	
	/*This function applies A* search
	 * algorithm to solve the problem.
	 */
	public void astar_search(TreeNode root, String out)
	{
		PriorityQueue<TreeNode> pQueue = new PriorityQueue<TreeNode>();
		int steps = 0;
		double duration;
		
		pQueue.add(root);
		
		long startTime = System.currentTimeMillis();
		
		while(!pQueue.isEmpty())
		{
			TreeNode temp = pQueue.poll();
			temp.createChildren();
			
			if(!temp.getChildren().isEmpty())
			{
				for(int i=0; i<temp.getChildren().size(); i++)
				{
					pQueue.add(temp.getChildren().get(i));
				}
			}
			
			if(temp.isSolution())
			{
				double endTime = System.currentTimeMillis();
				
				//Converting milliseconds to seconds.
				duration = (endTime - startTime) / 1000;
				
				temp.writeToFile(out, steps, duration);

				System.out.println("The solution has been printed to " + out + " file");
				break;
			}
			steps++;
		}
	}
	
	/*Return the search method that the user selected.*/
	public static int getMethod(String s) {
		if(s.equals("breadth"))
			return Main.BREADTH;
		else if(s.equals("depth"))
			return Main.DEPTH;
		else if(s.equals("best"))
			return Main.BEST;
		else if(s.equals("astar"))
			return Main.ASTAR;
	
		return -1;
	}

}

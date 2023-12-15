import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TreeNode implements Comparable<TreeNode>{
	
	private int[][] puzzle;                  //Every node has a specific state of the puzzle;
	private int height;                      //The height (distance from the root) of node.
	private int manhattan_distance;          //The heuristic value (manhattan distance) for best and astar methods.
	private TreeNode parent;                 //The parent of the node.
	private ArrayList<TreeNode> children;    //The children of the node.      
	
	public TreeNode()
	{
		puzzle = new int[Main.N][Main.N];
		height = 0;
		manhattan_distance = 0;
		parent = null;
		children = new ArrayList<TreeNode>();
	}
	
	/*Creates a deep copy of a node. Copy Constructor*/
	public TreeNode(TreeNode another)
	{
		this.puzzle = clonePuzzle(another.puzzle);
		this.height = 0;
		this.manhattan_distance = 0;
		if(this.parent != null)
		{
			this.parent = null;
		}
		else
		{
			this.parent = null;
		}
		this.children = new ArrayList<TreeNode>();
	}
	
	//Clones the children array.
	public static ArrayList<TreeNode> cloneChildren(ArrayList<TreeNode> children)
	{
		ArrayList<TreeNode> clonedChildren = new ArrayList<TreeNode>();
		
		for(int i=0; i<children.size(); i++)
		{
			clonedChildren.add(children.get(i));
		}
		return clonedChildren;
	}
	
	//Clones the puzzle array.
	public static int[][] clonePuzzle(int[][] puzzle)
	{
		int[][] clonedPuzzle = new int[Main.N][Main.N];
		
		for(int i=0; i<Main.N; i++)
		{
			for(int j=0; j<Main.N; j++)
			{
				clonedPuzzle[i][j] = puzzle[i][j];
			}
		}
		
		return clonedPuzzle;
	}
		
	/*This function reads the input file (puzzle)
	 *and creates the first node(root) of the tree.
	 */
	public void createRoot(String in)
	{
		BufferedReader inputStream = null;
		String[] splittedValues = new String[Main.N];
		String folder = "puzzles\\";   //The folder in which the puzzles will be stored.
		
		try {
			
			inputStream = new BufferedReader
					(new FileReader(folder + in));
			
			String line;
			
			int i=0;
			
			/*Transferring the values from the file to the puzzle*/
			while((line = inputStream.readLine()) != null)
			{
				splittedValues = line.split("\t");
				
				for(int j=0; j<Main.N; j++)
				{
					puzzle[i][j] = Integer.parseInt(splittedValues[j]);
				}
				i++;
			}
			
			//Compute the manhattan distance for the root.
			this.manhattanDistance();
			
		}catch(IOException e){
			e.printStackTrace();
			}
	}
	
	/*This function creates the children of the given node.*/
	public void createChildren()
	{
		int children_number = 0;
		
		//Create the coordinates(row,column) in which the empty index of the puzzle will be found.
		Coordinate emptyIndex = new Coordinate();
		
		//Get the row and column of the empty index(where the '0' value is positioned)
		emptyIndex =  searchEmptyIndex();
		
		/*Get the number of possible children*/
		children_number = getChildrenNumber(emptyIndex);
		
		/*These variables show if the empty index ('0')
		 * has moved in one of the possible directions
		 * which might create a solution of the puzzle.
		 */
		boolean isMovedUp = false;
		boolean isMovedDown = false;
		boolean isMovedLeft = false;
		boolean isMovedRight = false;
		
		/*Checks all possible solutions(children) of the given node and creates all the possible moves*/
		for(int i=children_number; i>0; i--)
		{
			//Possible left move.
			if(emptyIndex.getColumn() != Main.FIRST_COLUMN && isMovedLeft == false)
			{
				 moveIndexLeft(emptyIndex);
				 isMovedLeft = true;
				 continue;
			}
			
			//Possible upwards move.
			if(emptyIndex.getRow() != Main.FIRST_ROW && isMovedUp == false)
			{
				 moveIndexUp(emptyIndex);
				 isMovedUp = true;
				 continue;
			}
			
			//Possible right move.
			if(emptyIndex.getColumn() != Main.N - 1 && isMovedRight == false)
			{
				 moveIndexRight(emptyIndex);
				 isMovedRight = true;
				 continue;
			}
			
			//Possible downwards move.
			if(emptyIndex.getRow() != Main.N - 1 && isMovedDown == false)
			{
				 moveIndexDown(emptyIndex);
				 isMovedDown = true;
				 continue;
			}
		}
		
	}
	
	/*Checks if a node is duplicate inside the tree*/
	public boolean isDuplicate()
	{
		TreeNode parent = new TreeNode();
		
		parent = this.getParent();
			
		/*Checks all of the ancestors of the node for duplicates*/
		while(parent != null)
		{	
				
			if(Arrays.deepEquals(this.puzzle, parent.puzzle))
			{
				return true;
			}	
			parent = parent.getParent();
		}
		return false;
	}
	
	/*Searches the puzzle to find in which row and column the empty index is*/
	public Coordinate searchEmptyIndex()
	{
		Coordinate emptyPosition = new Coordinate();
		
		for(int i=0; i<Main.N; i++)
		{
			for(int j=0; j<Main.N; j++)
			{
				if(this.puzzle[i][j] == 0)
				{
					emptyPosition.setRow(i);
					emptyPosition.setColumn(j);
					break;
				}
			}
		}
		return emptyPosition;
	}
	
	/*This function shifts the '0' value one position
	 *upwards. If the created new node isnt dupilcate
	 *(one of its ancestors hasnt the same puzzle) it 
	 *adds the new node to the children list of the
	 *node which called the function.
	 */
	public void moveIndexUp(Coordinate emptyIndex)
	{   
		TreeNode child = new TreeNode(this);  //Create a deep copy if the initial node.
		
		//Swap the '0' value with the left value.
		child.puzzle[emptyIndex.getRow()][emptyIndex.getColumn()] 
				= puzzle[emptyIndex.getRow() - 1][emptyIndex.getColumn()];
		
		child.puzzle[emptyIndex.getRow() - 1][emptyIndex.getColumn()] = 0;
		
		child.setParent(this);
		child.increaseHeight();
		child.manhattanDistance();
				
		//Checks if the new node is the same with one of its ancestors.
		if(child.isDuplicate())
		{
			return;
		}
		else
		{
			this.children.add(child);
		}
	}
	
	/*This function shifts the '0' value one position
	 *downwards. If the created new node isnt dupilcate
	 *(one of its ancestors hasnt the same puzzle) it 
	 *adds the new node to the children list of the
	 *node which called the function.
	 */
	public void moveIndexDown(Coordinate emptyIndex)
	{		
		TreeNode child = new TreeNode(this);
		
		child.puzzle[emptyIndex.getRow()][emptyIndex.getColumn()] 
				= puzzle[emptyIndex.getRow() + 1][emptyIndex.getColumn()];
		
		child.puzzle[emptyIndex.getRow() + 1][emptyIndex.getColumn()] = 0;
		
		child.setParent(this);
		child.increaseHeight();
		child.manhattanDistance();
		
		if(child.isDuplicate())
		{
			return;
		}
		else
		{
			this.children.add(child);
		}
	}
	
	/*This function shifts the '0' value one position
	 *to the left. If the created new node isnt dupilcate
	 *(one of its ancestors hasnt the same puzzle) it adds
	 *the new node to the children list of the node which 
	 *called the function.
	 */
	public void moveIndexLeft(Coordinate emptyIndex)
	{	
		TreeNode child = new TreeNode(this);
		
		child.puzzle[emptyIndex.getRow()][emptyIndex.getColumn()] 
				= puzzle[emptyIndex.getRow()][emptyIndex.getColumn() - 1];
		
		child.puzzle[emptyIndex.getRow()][emptyIndex.getColumn() - 1] = 0;
		
		child.setParent(this);
		child.increaseHeight();
		child.manhattanDistance();
		
		if(child.isDuplicate())
		{
			return;
		}
		else
		{
			this.children.add(child);
		}		
	}
	
	/*This function shifts the '0' value one position
	 *to the right. If the created new node isnt dupilcate
	 *(one of its ancestors hasnt the same puzzle) it adds
	 *the new node to the children list of the node which 
	 *called the function.
	 */
	public void moveIndexRight(Coordinate emptyIndex)
	{	
		TreeNode child = new TreeNode(this);
		
		child.puzzle[emptyIndex.getRow()][emptyIndex.getColumn()] 
				= puzzle[emptyIndex.getRow() ][emptyIndex.getColumn() + 1];
		
		child.puzzle[emptyIndex.getRow()][emptyIndex.getColumn() + 1] = 0;
		
		child.setParent(this);
		child.increaseHeight();
		child.manhattanDistance();
		
		if(child.isDuplicate())
		{
			return;
		}
		else
		{
			this.children.add(child);
		}
	}
	
	/*Returns the possible number of children a node might have*/
	public int getChildrenNumber(Coordinate emptyIndex)
	{
		int children_number = 0;
		
		/*If the empty index is either in the first or in the last row*/
		if(emptyIndex.getRow() == Main.FIRST_ROW || emptyIndex.getRow() == Main.N - 1)
		{
			/*Only one swap can be done (upwards or downwards)*/
			children_number++;
		}
		else 
		{
			children_number += 2;
		}
		
		if(emptyIndex.getColumn() == Main.FIRST_COLUMN || emptyIndex.getColumn() == Main.N - 1)
		{
			/*Only one swap can be done (left or right)*/
			children_number++;
		}
		else
		{
			children_number +=2;
		}
		
		return children_number;
	}
	
	public boolean isSolution()
	{
		if(Arrays.deepEquals(this.puzzle, Main.SOLUTION_PUZZLE))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/*This function computes the manhattan distance
	 * of the calling node.
	 */
	public void manhattanDistance()
	{	
		//Current row and col are the coordinates where the current value exists in the puzzle.
		int current_row = 0;
		int current_col = 0;
		
		//Target row and col are the coordinates where the current value should be in the puzzle.
		int target_row = 0;
		int target_col = 0;
		
		for(int i=0; i<Main.N; i++)
		{
			for(int j=0; j<Main.N; j++)
			{
				//Dont count in the manhattan distance the '0' value.
				if(!(this.puzzle[i][j] == 0))
				{
					//Getting the current coordinates of every element.
					current_row = i;
					current_col = j;
					
					/*Compute the target's (goal's) coordinates of every element.
					 * (puzzle[i][j] - 1) / N and (puzzle[i][j] - 1) % N are the 
					 * target coordinates(row,column) in which every 
					 * element should be.
					 * For example if puzzle[i][j] == 4 then that means:
					 * (puzzle[i][j] - 1) / 3  == 1 and puzzle[i][j] % 3  == 1.
					 * So the puzzle[i][j] == 4 should be placed in
					 * the first row and first column of the puzzle.
					 */
					target_row = (puzzle[i][j] - 1) / Main.N;
					target_col = (puzzle[i][j] - 1) % Main.N;
					
					this.manhattan_distance += Math.abs(current_row - target_row) 
							+ Math.abs(current_col - target_col);
				}
			}
		}
		
	}
	
	/*This function writes to solution to the out file.*/
	public void writeToFile(String out, int steps, double duration)
	{
		File file = new File(out);
		FileWriter fwriter = null;
		
		try {
			
			fwriter = new FileWriter(file);
			
			//Write the search method to output file.
			if(Main.METHOD == 1)
			{
				fwriter.write("Search algorithm: " + "Breadth-First-Search \n");
			}
			else if(Main.METHOD == 2)
			{
				fwriter.write("Search algorithm: " + "Depth-First-Search \n");
			}
			else if(Main.METHOD == 3)
			{
				fwriter.write("Search algorithm: " + "Best-First-Search \n");
			}
			else if(Main.METHOD == 4)
			{
				fwriter.write("Search algorithm: " + "A* Search \n");
			}
			
			fwriter.write("Number of steps to achieve solution: " + steps);
			fwriter.write(System.lineSeparator());
			fwriter.write("Execution time: " + duration + " seconds \n");
			fwriter.write(System.lineSeparator());
			for(int i=0; i<Main.N; i++)
			{
				for(int j=0; j<Main.N; j++)
				{
					fwriter.write(this.puzzle[i][j] + "\t");
				}
				fwriter.write(System.lineSeparator());
			}
			fwriter.write(System.lineSeparator());
			
			fwriter.write("Height: " + this.height + "\n");
			
			fwriter.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*This function increases the height of the calling
	 * node by 1.
	 */
	public void increaseHeight()
	{
		//If the node is the root.
		if(this.getParent() == null)
		{
			return;
		}
		else
		{
			//Increase the current's node height by 1 level.
			this.height = this.getParent().getHeight() + 1;
		}
	}
	
	@Override
	/*This function compares the heuristic values 
	 *(for best and astar methods). It must be overridden
	 *because it implements the comparable interface.
	 */
	public int compareTo(TreeNode other)
	{
		if(this.equals(other))
		{
			return 0;
		}
		
		/*In case the algorithm which is used is the
		 * best-first-search or A*.
		 */
		if(Main.METHOD == Main.BEST)
		{
			/*Gets the node with the lowest manhattan distance*/
			if(other.manhattan_distance < manhattan_distance)
			{
				return 1;
			}
			else
			{
				return -1;
			}
		}
		else if(Main.METHOD == Main.ASTAR)
		{
			/*Gets the node with the lowest manhattan distance but with the biggest height.*/
			if(other.manhattan_distance + other.height < manhattan_distance + height)
			{
				return 1;
			}
			else
			{
				return -1;
			}
		}
		else
		{
			return 1;
		}
	}
	
	/*Getters and Setters*/
	public TreeNode getParent()
	{
		return parent;
	}
	
	public ArrayList<TreeNode> getChildren()
	{
		return children;
	}
	
	public void setParent(TreeNode aNode)
	{
		parent = aNode;
	}
	
	public void setChild(TreeNode aNode)
	{
		children.add(aNode);
	}
	
	public int getHeight()
	{
		return height;
	}
	
}

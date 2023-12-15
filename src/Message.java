
public class Message {
	
	//This function displays a message in case of wrong input parameters
	public static void syntax_message() 
	{
		System.out.println("\tmain <method> <input-file> <output-file>\n\n");
		System.out.println("where: ");
		System.out.println("\t<method> = breadth|depth|best|astar");
		System.out.println("\t<input-file> is a file containing the puzzle description.");
		System.out.println("\t<output-file> is the name of the file where the solution will be written.\n");
		System.exit(1);
	}

}

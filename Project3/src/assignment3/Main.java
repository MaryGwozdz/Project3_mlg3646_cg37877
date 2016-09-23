/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * Mary Gwozdz
 * mlg3646
 * 16450
 * Christopher Gang
 * cg37877
 * 16450
 * Slip days used: <0>
 * Git URL:https://github.com/MaryGwozdz/Project3_mlg3646_cg37877
 * Fall 2016
 */

package assignment3;
import java.util.*;
import java.io.*;

public class Main {
	
	// static variables and constants only here.
	
	public static void main(String[] args) throws Exception {
		
		Scanner kb;	// input Scanner for commands
		PrintStream ps;	// output file
		// If arguments are specified, read/write from/to files instead of Std IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps);			// redirect output to ps
		} else {
			kb = new Scanner(System.in);// default from Stdin
			ps = System.out;			// default to Stdout
		}
		initialize();
		ArrayList<String> twoWords = parse(kb);
		ArrayList<String> ladder = getWordLadderDFS(twoWords.get(0), twoWords.get(1));

		printLadder(ladder);
		
		// TODO methods to read in words, output ladder
	}
	
	public static void initialize() {
		// initialize your static variables or constants here.
		// We will call this method before running our JUNIT tests.  So call it 
		// only once at the start of main.
	}
	
	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of 2 Strings containing start word and end word. 
	 * If command is /quit, return empty ArrayList. 
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		ArrayList<String> input = new ArrayList<String>();
		input.add(keyboard.next());
		input.add(keyboard.next());
		return input;
	}
	
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		ArrayList<String> ladder = new ArrayList<String>();

		Queue<String> queue = new LinkedList<String>();
		
		
		// Adding first word to queue
		
		
		// Adding first word to ladder
		ladder.add(start);
		
		Set<String> dict = makeDictionary();
		Iterator dictIt = dict.iterator();
		String test;
		
		Node root = new Node(start, null, null);
		root.setRoot(root);
//		root.setChildren(wordTree);
		
		/*		
		Iterator queueIt = queue.iterator();
		String nextRung;
		
		while (queueIt.hasNext()) {
			nextRung = (String) queueIt.next();
			if (end.equals(nextRung)) {
				ladder.add(nextRung);
				ladder.add(end);
				return ladder;
			} else if (!getWordLadderDFS(nextRung, end).isEmpty()) {
				ladder.addAll(getWordLadderDFS(nextRung, end));
				return ladder;
			}
		}
*/
		
		return new ArrayList<String>();
	}
	
	public void wordTree(Node root, Node parent, String start, Set<String> dict) {
//		Node child = new Node(start, parent, root);		
		ArrayList<Node> children = new ArrayList<Node>();

		
		Iterator dictIt = dict.iterator();
		String test;
		while (dictIt.hasNext()) {
			test = (String) dictIt.next();
			if (oneLetterDiff(start, test)) {
				
				parent.getChildren().add(wordTree(root, child, test, dict));
			}
		}
//		child.setChildren(children);
	}
	
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		return null;
	}
    
    private static boolean oneLetterDiff(String a, String b) {
    	String lowA = a.toLowerCase();
    	String lowB = b.toLowerCase();
    	int diffCounter = 0;
    	
    	for (int i = 0; i < a.length(); i++) {
    		if (lowA.charAt(i) != lowB.charAt(i)) {
    			diffCounter++;
    		}
    	}
    	
    	return diffCounter == 1 ? true : false;
    }
    
	public static Set<String>  makeDictionary () {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner (new File("five_letter_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!");
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		}
		return words;
	}
	
	public static void printLadder(ArrayList<String> ladder) {
		if (ladder.isEmpty()) {
			System.out.println("Ladder is empty");
		}
		for (int i = 0; i < ladder.size(); i++) {
			System.out.println(ladder.get(i));
		}
	}
	// TODO
	// Other private static methods here
}

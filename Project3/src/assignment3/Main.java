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
	static HashMap<String, String> ladderTracker; // <child, parent>
	static Set<String> dict; //dictionary
	static HashSet<String> curWords;
	private static boolean oneMatchesEnd = false;
	private static boolean endFound = false;
	
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
		ladderTracker = new HashMap<String, String>();
		dict = makeDictionary();
		curWords = new HashSet<String>();
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
		
		// Adding first word to ladder
		ladder.add(start);
		
		// Sets up iterator for dictionary
		Set<String> dict = makeDictionary();
		Iterator dictIt = dict.iterator();
		String test;
		
		// Sets up word tree
		Node root = new Node(start, null, null);
		root.setRoot(root);
		for (int i = 0; i < 5; i++) {
			if (start.charAt(i) == end.charAt(i)) {
				oneMatchesEnd = true;
			}
		}
		root.setChildren(wordTree(root, root, start, end, dict));
		
		// Gets and prints word ladder
		Stack<String> chosen = new Stack<String>();
		boolean found = find(root, chosen, end);
		if (found) {
			for (int i = 0; i < chosen.size(); i++) {
				System.out.println(chosen.pop());
			}
		} else if (!found) {
			System.out.println("No word ladder can be found between " + start + " and " + end);
		}
		
		return new ArrayList<String>();
	}
	
	public static ArrayList<Node> wordTree(Node root, Node parent, String start, String end, Set<String> dict) {
		ArrayList<Node> children = new ArrayList<Node>();

		Iterator dictIt = dict.iterator();
		String test;
		while (dictIt.hasNext()) {
			test = (String) dictIt.next();
			
			// testMatchesEnd is initially false. If at least one of the letters of test matches end and oneMatchesEnd
			// is true then testMatchesEnd is true.
			boolean testMatchesEnd = false;
			for (int i = 0; i < 5; i++) {
				if (test.charAt(i) == end.toUpperCase().charAt(i) && oneMatchesEnd) {
					testMatchesEnd = true;
					oneMatchesEnd = true;
				}
			}
			if ((!testMatchesEnd && !oneMatchesEnd) || (testMatchesEnd && oneMatchesEnd)) {
				testMatchesEnd = true;
			}
			
			// Checks if test word is equal to end word
			if (end.toUpperCase().equals(test) && oneLetterDiff(end, test)) {
				endFound = true;
			}
			
			// Checks if ancestry contains same word as test word
			// If there is a match, set equalsAncestor to true and exit while loop
			boolean equalsAncestor = false;
			Node checkAncestry = parent;
			while (checkAncestry != null) {
				equalsAncestor = checkAncestry.getName().equals(test);
				if (equalsAncestor) {
					break;
				}
				checkAncestry = checkAncestry.getParent();
			}
			
			// If there is a 1 letter difference and there is no equivalent ancestor 
			// then add the node to children
			if (oneLetterDiff(start, test) && !equalsAncestor && !endFound && testMatchesEnd) {
				Node child = new Node(test, parent, root);
				child.setChildren(wordTree(root, child, test, end, dict));
				children.add(child);
			}
		}
		return children; 
	}
	
	private static boolean find(Node node, Stack<String> chosen, String value) {
		if (node == null) {
			return false;
		}
		node.setVisited(true);
		if (node.getName().equals(value)) {
			return true;
		} else {
			for (Node neighbor : node.getParent().getChildren()) {
				if (!neighbor.isVisited()) {
					chosen.push(value);
					boolean found = find(neighbor, chosen, value);
					if (found) {
						return true;
					}
					chosen.pop();
				}
			}
		}
		return false;
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

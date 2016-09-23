package assignment3;

import java.util.ArrayList;

public class Node {
	private String name;
	private Node parent;
	private ArrayList<Node> children;
	private Node root;
	
	public Node(String name, Node parent, Node root) {
		this.name = name;
		this.parent = parent;
		this.root = root;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	public ArrayList<Node> getChildren() {
		return children;
	}
	public void setChildren(ArrayList<Node> children) {
		this.children = children;
	}
	public Node getRoot() {
		return root;
	}
	public void setRoot(Node root) {
		this.root = root;
	}
	
	
}

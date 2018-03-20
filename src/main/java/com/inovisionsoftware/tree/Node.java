package com.inovisionsoftware.tree;

public class Node {

	public char c;
	public Node[] childs;
	public boolean visited = false;
	
	public Node(char c, Node[] childs) {
		this.c = c;
		this.childs = childs;
	}
}

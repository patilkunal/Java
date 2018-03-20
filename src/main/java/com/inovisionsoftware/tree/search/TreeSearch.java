package com.inovisionsoftware.tree.search;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import com.inovisionsoftware.tree.Node;

public class TreeSearch {

	Node rootNode = null;
	
	public void depthFirstSearch() {
		Stack<Node> s = new Stack<Node>();
		s.push(rootNode);
		rootNode.visited = true;
		while(!s.empty()) {
			Node n = s.peek();
			Node child = getUnvisitedChild(n);
			if(child != null) {
				child.visited = true;
				s.push(child);
			} else {
				s.pop();
			}
		}
	}
	
	public void breadthFirstSearch() {
		Queue<Node> q = new LinkedList<Node>();
		q.add(rootNode);
		rootNode.visited = true;
		while(!q.isEmpty()) {
			Node n = q.remove();
			Node child = null;
			while((child = getUnvisitedChild(n)) != null) {
				child.visited = true;
				q.add(child);
			}
		}
	}

	private Node getUnvisitedChild(Node n) {
		if(n == null) return null;
		for(int i=0; i < n.childs.length; i++) {
			if(!n.childs[i].visited)
				return n.childs[i];
		}
		return null;
	}
}

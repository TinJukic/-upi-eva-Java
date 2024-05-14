package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/**
 * 
 * @author Tin Jukić
 * @Node base class for all graph nodes
 *
 */

public class Node {
	private ArrayIndexedCollection internalCollectionOfChildren = null;
	
	/**
	 * 
	 * adds given child to an internally managed collection of children
	 * @param child
	 */
	public void addChildNode(Node child) {
		if(this.internalCollectionOfChildren == null) {
			// create collection only when it is actually needed
			this.internalCollectionOfChildren = new ArrayIndexedCollection();
		}
		
		this.internalCollectionOfChildren.add(child);
	}
	
	/**
	 * 
	 * @return a number of (direct) children
	 */
	public int numberOfChildren() {
		return this.internalCollectionOfChildren.size();
	}
	
	/**
	 * 
	 * @param index of the wanted element
	 * @return selected child
	 * @throws IndexOutOfBoundsException if the index is invalid
	 */
	public Node getChild(int index) throws IndexOutOfBoundsException {
		try {
			return (Node) this.internalCollectionOfChildren.get(index);
		} catch(IndexOutOfBoundsException e) { throw e; }
	}
}


// CS 0445 Spring 2020
// LinkedListPlus<T> class partial implementation

// See the commented methods below.  You must complete this class by
// filling in the method bodies for the remaining methods.  Note that you
// may NOT add any new instance variables, but you may use method variables
// as needed.

public class LinkedListPlus<T> extends A2LList<T> {
	// Default constructor simply calls super()
	public LinkedListPlus() {
		super();
	}

	// Copy constructor. This is a "deepish" copy so it will make new
	// Node objects for all of the nodes in the old list. However, it
	// is not totally deep since it does NOT make copies of the objects
	// within the Nodes -- rather it just copies the references. The
	// idea of this method is as follows: If oldList has at least one
	// Node in it, create the first Node for the new list, then iterate
	// through the old list, appending a new Node in the new list for each
	// Node in the old List. At the end, link the Nodes around to make sure
	// that the list is circular.
	// FOR ROTATE: REMOVE FROM BACK, ADD IT TO THE FRONT-- DO IT IN A FOR LOOP
	public LinkedListPlus(LinkedListPlus<T> oldList) {
		super();
		if (oldList.getLength() > 0) {
			// Special case for first Node since we need to set the
			// this.firstNode instance variable.
			Node temp = oldList.firstNode;
			Node newNode = new Node(temp.data);
			this.firstNode = newNode;

			// Now we traverse the old list, appending a new Node with
			// the correct data to the end of the new list for each Node
			// in the old list. Note how the loop is done and how the
			// Nodes are linked.
			Node currNode = this.firstNode;
			temp = temp.next;
			int count = 1;
			while (count < oldList.getLength()) {
				newNode = new Node(temp.data);
				currNode.next = newNode;
				newNode.prev = currNode;
				temp = temp.next;
				currNode = currNode.next;
				count++;
			}
			currNode.next = this.firstNode; // currNode is now at the end of the list.
			this.firstNode.prev = currNode; // link to make the list circular
			numberOfEntries = oldList.numberOfEntries;
		}
	}

	// Make a StringBuilder then traverse the nodes of the list, appending the
	// toString() of the data for each node to the end of the StringBuilder.
	// Finally, return the StringBuilder as a String. Note that since the list
	// is circular, we cannot look for null. Rather we must count the Nodes as
	// we progress down the list.
	public String toString() {
		StringBuilder b = new StringBuilder();
		Node curr = this.firstNode;
		int i = 0;
		while (i < this.getLength()) {
			b.append(curr.data.toString());
			b.append(" ");
			curr = curr.next;
			i++;
		}
		return b.toString();
	}

	// Remove num items from the front of the list
	public void leftShift(int num) {
		Node lastNode = this.firstNode.getPrevNode();

		// Check if user input is <=0. If so, nothing happens.
		if (num <= 0)
			return;
		// Normal case
		else {
			if (!this.isEmpty()) {
				for (int i = 0; i < num; i++) {
					if (this.firstNode.getNextNode() == this.firstNode) {
						this.remove(getLength());
						this.firstNode = null;
						lastNode = null;
					} 
					else {
						this.firstNode = this.firstNode.getNextNode();
						this.firstNode.setPrevNode(lastNode);
						lastNode.setNextNode(this.firstNode);
						this.remove(getLength());
					}
				}
			}
		}

	}

	// Remove num items from the end of the list
	public void rightShift(int num) {
		Node lastNode = this.firstNode.getPrevNode();

		// Check if user input is <=0. If so, nothing happens.
		if (num <= 0)
			return;

		// Normal case
		else {
			if (!this.isEmpty()) {
				for (int i = 0; i < num; i++) {
					if (this.firstNode.getNextNode() == this.firstNode) {
						this.remove(getLength());
						this.firstNode = null;
						lastNode = null;
					} 
					else {
						lastNode = lastNode.getPrevNode();
						lastNode.setNextNode(this.firstNode);
						this.firstNode.setPrevNode(lastNode);
						this.remove(getLength());
					}
				}
			}
		}

	}

	// Rotate to the left num locations in the list. No Nodes
	// should be created or destroyed.
	public void leftRotate(int num) {
		Node lastNode = this.firstNode.getPrevNode();

		// If num is less than 0, run the rightRotate, which is equivalent to rotating
		// left in a negative fashion.
		if (num < 0)
			rightRotate(Math.abs(num));
		// If num rotations is 0, don't change the list at all.
		if (num == 0)
			return;
		// Normal case
		else {
			for (int i = 0; i < num; i++) {
				lastNode.setNextNode(this.firstNode);
				lastNode = lastNode.getNextNode();
				this.firstNode = lastNode.getNextNode();
				lastNode.setNextNode(null);
			}
		}
	}

	// Rotate to the right num locations in the list. No Nodes
	// should be created or destroyed.
	public void rightRotate(int num) {
		Node lastNode = this.firstNode.getPrevNode();

		// If num is less than 0, run the leftRotate, which is equivalent to rotating
		// right in a negative fashion.
		if (num < 0)
			leftRotate(Math.abs(num));
		// If num rotations is 0, don't change the list at all.
		if (num == 0)
			return;
		// Normal case
		else {
			for (int i = 0; i < num; i++) {
				lastNode.setNextNode(this.firstNode);
				lastNode = lastNode.getPrevNode();
				this.firstNode = lastNode.getNextNode();
				lastNode.setNextNode(null);
			}
		}
	}

	// Reverse the nodes in the list. No Nodes should be created
	// or destroyed.
	public void reverse() {

		// No point in reversing if size = 1 or 0.
		if (this.firstNode.getNextNode() == this.firstNode)
			return;

		Node foo = null;
		for (int i = 0; i < getLength(); i++) {
			Node temp = this.firstNode;
			this.firstNode = this.firstNode.getNextNode();
			temp.setNextNode(foo);
			foo = temp;
		}
		this.firstNode = foo;
	}
}

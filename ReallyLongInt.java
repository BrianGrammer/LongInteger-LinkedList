// CS 0445 Spring 2020
// This is a partial implementation of the ReallyLongInt class.  You need to
// complete the implementations of the remaining methods.  Also, for this class
// to work, you must complete the implementation of the LinkedListPlus class.
// See additional comments below.

public class ReallyLongInt 	extends LinkedListPlus<Integer> 
							implements Comparable<ReallyLongInt>
{
	private ReallyLongInt()
	{
		super();
	}

	// Data is stored with the LEAST significant digit first in the list.  This is
	// done by adding all digits at the front of the list, which reverses the order
	// of the original string.  Note that because the list is doubly-linked and 
	// circular, we could have just as easily put the most significant digit first.
	// You will find that for some operations you will want to access the number
	// from least significant to most significant, while in others you will want it
	// the other way around.  A doubly-linked list makes this access fairly
	// straightforward in either direction.
	public ReallyLongInt(String s)
	{
		super();
		char c;
		int digit = -1;
		// Iterate through the String, getting each character and converting it into
		// an int.  Then make an Integer and add at the front of the list.  Note that
		// the add() method (from A2LList) does not need to traverse the list since
		// it is adding in position 1.  Note also the the author's linked list
		// uses index 1 for the front of the list.
		for (int i = 0; i < s.length(); i++)
		{
			c = s.charAt(i);
			if (('0' <= c) && (c <= '9'))
			{
				digit = c - '0';
				// Do not add leading 0s
				if (!(digit == 0 && this.getLength() == 0)) 
					this.add(1, new Integer(digit));
			}
			else throw new NumberFormatException("Illegal digit " + c);
		}
		// If number is all 0s, add a single 0 to represent it
		if (digit == 0 && this.getLength() == 0)
			this.add(1, new Integer(digit));
	}

	public ReallyLongInt(ReallyLongInt rightOp){
		super(rightOp);
	}
	
	// Method to put digits of number into a String.  Note that toString()
	// has already been written for LinkedListPlus, but you need to
	// override it to show the numbers in the way they should appear.
	public String toString(){
		Node curr = this.firstNode.getPrevNode();
		StringBuilder s = new StringBuilder("");
		for(int i = 0; i < this.getLength(); i++) {
			s.append(curr.getData().toString());
			curr = curr.getPrevNode();
		}
		return s.toString();
	}

	// See notes in the Assignment sheet for the methods below.  Be sure to
	// handle the (many) special cases.   Some of these are demonstrated in the
	// RLITest.java program.

	// Return new ReallyLongInt which is sum of current and argument
	/**
	 * Overall explanation of how the method works:
		The addition method adds two ReallyLongInts recursively. It might have been easier to do it iteratively, but the clearest method to me was using recursion.
		Starting from the 1's place, the digits in the 1's place of both numbers are added.
		If the sum of both digits is greater than 10, the number mod 10 is taken to get the 1's value of the digit. The 10's value of the sum the modded digits is added to int "carry." 
		Since the 10's value of the modded digit increases as we iterate down the number, the number added to carry is 10^placeCounter, where placeCounter keeps track of the place i.e. ones, tens, hundreds, etc. 
		PlaceCounter incremements by 1 every time both digits are added.
		The mod 10 of that number is then added to the ReallyLongInt var "summed." 
		If the sum of the digits is less than 10, then just the sum is added to var "summed."
		
		If var "carry" is 0 by the time counter reaches 0, that means no carrying had to be done.
		If "carry" is non-zero, that means the add method is called again to add "carry" and "summed"
		This process repeats until "carry" is 0, meaning all the carrying is finished, in which the method returns the sum.
		
	**/
	public ReallyLongInt add(ReallyLongInt rightOp){
		//Local Variables
		long carry = 0;
		int counter, placeCounter = 0;
		boolean shorterIsDone = false;
		boolean sameLength = false;
		ReallyLongInt longer = null, shorter = null;
		ReallyLongInt summed = new ReallyLongInt();
		Node currDigitLonger; 
		Node currDigitShorter; 
		Node originalFirstShorter; 
		
		//Get longer and shorter list. Loop runs for length of longer list. If the same, "this" is chosen as longer--it wouldn't make a difference either way
		if(getLength() > rightOp.getLength()) {
			longer = this;
			shorter = rightOp;
		}
		if(getLength() < rightOp.getLength()) {
			longer = rightOp;
			shorter = this;
		}
		if(getLength() == rightOp.getLength()) {
			longer = this;
			shorter = rightOp;
			sameLength = true;
		}
		
		//Differentiate between nodes by longer or shorter list.
		currDigitLonger = longer.firstNode;
		currDigitShorter = shorter.firstNode;
		//Stores original first node of shorter so no looping of list occurs.
		originalFirstShorter = shorter.firstNode;
		
		//Counter is set to length of longer list and decreases every time an addition between digits is made.
		counter = longer.getLength();
		int sumDigits;
		sumDigits = currDigitLonger.getData() + currDigitShorter.getData();
	
		
		while(counter > 0) {	
			//If sumDigits is less than 10, no carrying has to be done. The sumDigit is then added to the new ReallyLongInt.
			if(sumDigits<10) {
				summed.add(new Integer(sumDigits));
				
				//If statements below check to see if the next node equals the original first node. 
					//If the shorter number "ends," then the sumDigits is set equal to the longer lists next node's data.
					//Checking to see if the longer number "ends" is pointless since the while loop iterates until all the longer digit's have been added.
				if(currDigitShorter.getNextNode() == originalFirstShorter) {
					shorterIsDone = true;
				}
				if(shorterIsDone){
					if(sameLength) {
						counter--;
					}
					else {
						currDigitLonger = currDigitLonger.getNextNode();
						sumDigits = currDigitLonger.getData();
						counter--;
					}
				}
				//If the numbers don't "end" then current nodes are set equal to next nodes, and the digits are added.
				if(shorterIsDone == false) {
					currDigitLonger = currDigitLonger.getNextNode();
					currDigitShorter = currDigitShorter.getNextNode();
					sumDigits = currDigitLonger.getData() + currDigitShorter.getData();
					counter--;
				}
				
				placeCounter++;
				continue;
			}
			//If sumDigits is more than 10, carrying has to be done.
			else {
				//sumDigits%10 is added to the new ReallyLongInt list. For explanation, see documentation above the method.
				summed.add( new Integer(sumDigits%10));
				//Carry+=10^placeCounter-- again, for explanation see documentation above method.
				carry+=(long)Math.pow(10, placeCounter+1);
				
				//If statements below check to see if the next node equals the original first node. 
					//If the shorter number "ends," then the sumDigits is set equal to the longer lists next node's data.
					//Checking to see if the longer number "ends" is pointless since the while loop iterates until all the longer digit's have been added.
				if(currDigitShorter.getNextNode() == originalFirstShorter) {
					shorterIsDone = true;
				}
				if(shorterIsDone){
					if(sameLength) {
						counter--;
					}
					else {
						currDigitLonger = currDigitLonger.getNextNode();
						sumDigits = currDigitLonger.getData();
						counter--;
					}
				}
				if(shorterIsDone == false) {
					currDigitLonger = currDigitLonger.getNextNode();
					currDigitShorter = currDigitShorter.getNextNode();
					sumDigits = currDigitLonger.getData() + currDigitShorter.getData();
					counter--;
				}
				placeCounter++;
				continue;
			}
		}
		//If carry = 0, then no more carrying has to be done, so the number is returned
		if(carry == 0) 
			return summed;
		//More carrying must be done, so the method is called again to add the carry and the sum.
		else {
			return summed.add(new ReallyLongInt(Long.toString(carry)));
		}
	}
	
	// Return new ReallyLongInt which is difference of current and argument
	/**
	 * Overall explanation of how the method works:
	  	Method tries to subtract the 1's place digits first. If the "above" digit is greater than or equal to the "below" digit, the two are subtracted, then it moves onto the 10's digits, and so on.
	  	If the "above" digit is smaller than the "below" digit, 10 is added to the "above" digit, then their difference is taken. 
	  	To compensate for the added "10," the digit in the next place over is decremented by 1.
	  	This process continues and after the loop is finished, the number is returned.
	  	
	 */
	public ReallyLongInt subtract(ReallyLongInt rightOp){
		//Counter keeps the while loop going for the length of the longest number, which is always "this" because negatives are not allowed.
		//currLongData & currShortData keep track of the current numbers, so I don't have to set the values in the list to different values. **Possibly unnecessary, however my subtraction method wouldn't work without it.
		int counter = 0, currLongData=0, currShortData=0;
		Node currLong, currShort, originalFrontLong, originalFrontShort;
		ReallyLongInt diffed = new ReallyLongInt();
		
		//Checks to see if "this" is less than "rightOp" using compareTo. If it is, an ArithmeticException is thrown.
		if(this.compareTo(rightOp) == -1) {
			throw new ArithmeticException();
		}
		//Checks to see if lists are same length, if they are equal length, it then checks to see if they are equal. If equal, 0 is returned (Which is faster than subtracting 2 equal values).
		if(this.getLength() == rightOp.getLength()) {
			if(this.compareTo(rightOp)==0) {
				return new ReallyLongInt("0");
			}
		}
		//Differentiate between nodes by longer or shorter list.
		currLong = this.firstNode;
		currShort = rightOp.firstNode;
		
		//Stores original first node of shorter so no looping of list occurs.
		originalFrontLong = this.firstNode;
		originalFrontShort = rightOp.firstNode;
		
		//Counter is set to length of longer list and decreases every time a difference between digits is taken.
		counter = this.getLength();
		currLongData = currLong.getData();
		currShortData = currShort.getData();
		while(counter > 0) {
			//If the larger number's first digit is greater than the smaller number's first digit, no borrowing must be done, so difference between digits is taken.
			if(currLongData>=currShortData) {
				diffed.add(new Integer(currLongData-currShortData));
				currLong = currLong.getNextNode();
				currLongData = currLong.getData();
				counter--;
				if(currShort.getNextNode()!=originalFrontShort) {
					currShort = currShort.getNextNode();
					currShortData = currShort.getData();
				}
				else {
					currShortData = 0;
				}
			}
			//Larger number's first digit is smaller than the smaller number's first digit, so borrowing must be done.
			else {
				if(currLong.getNextNode()!=originalFrontLong) {
					//"10" is added to the digit on top, and then the digits are subtracted.
					diffed.add(new Integer((currLongData+10)-currShortData));
					currLong = currLong.getNextNode();
					currLongData = currLong.getData();
					//To make up for the 10 being added, the digit that was borrowed from is decremented by 1 (Equivalent of borrowing 10)
					currLongData--;
					if(currShort.getNextNode()!=originalFrontShort) {
						currShort = currShort.getNextNode();
						currShortData = currShort.getData();
					}
					else {
						currShortData = 0;
					}
				}
				counter--;
			}
		}
		//Returns new ReallyLongInt to get rid of any leading zeros.
		return new ReallyLongInt(diffed.toString());
	}

	// Return -1 if current ReallyLongInt is less than rOp
	// Return 0 if current ReallyLongInt is equal to rOp
	// Return 1 if current ReallyLongInt is greater than rOp
	public int compareTo(ReallyLongInt rOp){
		//First: check length
		if(this.getLength()>rOp.getLength())
			return 1;
		if(this.getLength()<rOp.getLength())
			return -1;
		//Check values
		if(this.getLength()==rOp.getLength()) {
			Node currThis = this.firstNode.getPrevNode();
			Node currrOp = rOp.firstNode.getPrevNode();
			int counter = 0;
			while(counter < this.getLength()) {
				//If the next digit is greater than the other's next digit, 1 is returned. -1 is returned conversely.
				if(currThis.getData().compareTo(currrOp.getData())<0)
					return -1;
				if(currThis.getData().compareTo(currrOp.getData())>0)
					return 1;
				//If digits are equal, the next digits of both numbers are checked.
				if(currThis.getData().compareTo(currrOp.getData())==0) {
					currThis = currThis.getPrevNode();
					currrOp = currrOp.getPrevNode();
					counter++;
				}
			}
			return 0;
		}
		return -1;
	}

	// Is current ReallyLongInt equal to rightOp?
	public boolean equals(Object rightOp){
		//If the objects aren't of the same type, automatically not equal.
		if(rightOp instanceof ReallyLongInt) {
			//Uses compareTo to see if they're equal.
			if(this.compareTo((ReallyLongInt) rightOp)==0) {
				return true;
			}
			else
				return false;
		}
		return false;
	}

	// Mult. current ReallyLongInt by 10^num
	public void multTenToThe(int num){
		//Just add num 0's to the end of the number.
		//Does not add 0's if the only number is a 0.
		if(this.firstNode.getData()==0) {
			return;
		}
		else {
			for(int i = 0; i < num; i++) {
				this.add(1, new Integer(0));
			}
		}
	}

	// Divide current ReallyLongInt by 10^num
	public void divTenToThe(int num){
		//Delete num integers from end
		//If length is less than 1, set only digit left to 0 (simulating integer division)
		for(int i = 0; i < num; i++) {
			if(this.getLength()==1) {
				this.replace(1, 0);
				break;
			}
			else
				this.remove(1);
		}
	}

}

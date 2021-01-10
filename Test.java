
public class Test {

	public static void main(String[] args) {
		ReallyLongInt R1 = new ReallyLongInt("23456");
		//System.out.println(R1);
		ReallyLongInt R2 = new ReallyLongInt("4567");
		//System.out.println(R2);
		System.out.println(R1.subtract(R2));
		System.out.println(R1 + " " + R2);
//		Integer one = new Integer(1);
//		Integer two = new Integer(2);
//		System.out.println(one + two);
	}
}

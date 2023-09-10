package optimized3;

public class PartialGen4Helper {

	//Idea:
	// Datastruct is just a growable short array and this class contains the helper method to navigate the operations required on that short array
	//I haven't finished building it yet...
	
	//1st index is short that has 1 byte for min and 1 byte for max.
	
	//TODO: make helper functions to handle everything PartialGen3 can do...
	//Including the constructor and the helper functions.
	
	//TODO: reused functions in PrimeFinder as much as possible.

	public static int NUM_BITES_IN_BYTE = 8;
	public static int NUM_COMBOS_BYTES = (int)Math.pow(2, NUM_BITES_IN_BYTE);
	public static int SIZE_DESC_INDEX = 0;
	
	public static int getMinSquares(short cur[]) {
		return cur[SIZE_DESC_INDEX] >> NUM_BITES_IN_BYTE;
	}

	
	public static int getMaxSquares(short cur[]) {
		return cur[SIZE_DESC_INDEX] % NUM_COMBOS_BYTES;
	}

	public static int getNumResiduesUsed(short cur[]) {
		
		return (cur.length - 1) / (getMaxSquares(cur) - getMinSquares(cur) + 1);
		
	}
	
	public static short[] getStoredResidues(short cur[], int numSquares) {
		return getStoredResidues(cur, numSquares, getMinSquares(cur), getMaxSquares(cur), getNumResiduesUsed(cur));
	}
	
	//Return null if it's not applicable... (not within min and max)
	public static short[] getStoredResidues(short cur[], int numSquares, int minSquares, int maxSquares, int numResiduesUsed) {
		
		if(numSquares < minSquares || numSquares > maxSquares) {
			
			return null;
		}

		//I put all the residues of same num squares together:
		//Answer: of same numSquares together
		//that means slightly more work when adding a residue, but whatever.
		
		short ret[] = new short[numResiduesUsed];
		int firstIndex = 1 + (numSquares - minSquares) * numResiduesUsed;
		
		for(int i=0; i<numResiduesUsed; i++) {
			ret[i] = cur[firstIndex + i];
		}
		
		return ret;
	}
	
	
	
	public static void main(String args[]) {
		
		//Min/max test:
		short test[] = new short[1];
		
		test[0] = 1034;
		
		System.out.println(getMinSquares(test));
		System.out.println("to ");
		System.out.println(getMaxSquares(test));
		if( getMinSquares(test) != 4) {
			System.out.println("test failed (min)");
		}
		if( getMaxSquares(test) != 10) {
			System.out.println("test failed (max)");
		}

		short test2[] = new short[29];

		test2[0] = 1034;
		
		System.out.println("Num residues used test:");
		if( getNumResiduesUsed(test2) != 4) {
			System.out.println("test failed (num residues)");
		}
		System.out.println(getNumResiduesUsed(test2));
	
		System.out.println("Test getStoredResidues:");
		
		test2[9] = (short)1;
		test2[10] = (short)2;
		test2[11] = (short)3;
		test2[12] = (short)4;
		
		short res[] = getStoredResidues(test2, 6);
		
		for(int i=0; i<res.length; i++) {
			if(res[i] != i + 1) {
				System.out.println("Doh!");
				System.exit(1);
			} else {
				System.out.println("res[" + i+ "] = " + res[i]);
			}
		}
		System.out.println("(Expects 1, 2, 3, 4)");
	}
	
	
	
}

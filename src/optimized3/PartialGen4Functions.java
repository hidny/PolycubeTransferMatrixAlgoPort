package optimized3;

import java.math.BigInteger;

import Utils.ChineseRemainderTheoremUtilFunctions;

public class PartialGen4Functions {

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
	
	public static short[] getInitialPartialGen() {
		
		short ret[] = new short[2];
		
		ret[0] = 0;
		ret[1] = 1;
		
		return ret;
	}
	
	public static short[] hardCopy(short cur[]) {
		
		//TODO: avoid copying overly simple short arrays...
		//There's a lot of opportunity to save space for the simple arrays.
		
		short ret[] = new short[cur.length];
		
		for(int i=0; i<ret.length; i++) {
			ret[i] = cur[i];
		}
		
		return ret;
	}
	
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
	
	//Returns null if it's not applicable... (not within min and max)
	public static short[] getStoredResidues(short cur[], int numSquares, int minSquares, int maxSquares, int numResiduesUsed) {
		
		if(numSquares < minSquares || numSquares > maxSquares) {
			return null;
		}

		//I put all the residues of same num squares together.
		//That means slightly more work when adding a residue, but whatever.
		
		short ret[] = new short[numResiduesUsed];
		int firstIndex = 1 + (numSquares - minSquares) * numResiduesUsed;
		
		for(int i=0; i<numResiduesUsed; i++) {
			ret[i] = cur[firstIndex + i];
		}
		
		return ret;
	}
	
	
	public static short[] hardCopyMerge(short cur1[], short cur2[]) {
		int minSquares1 = getMinSquares(cur1);
		int maxSquares1 = getMaxSquares(cur1);
		int numResiduesUsed1 = getNumResiduesUsed(cur1);
		
		
		int minSquares2 = getMinSquares(cur2);
		int maxSquares2 = getMaxSquares(cur2);
		int numResiduesUsed2 = getNumResiduesUsed(cur2);
		
		int minSquares = Math.min(minSquares1, minSquares2);
		int maxSquares = Math.min(maxSquares1, maxSquares2);
		int numResiduesToUse = Math.max(numResiduesUsed1, numResiduesUsed2);
		
		//Check if we need to add new residue:
		boolean addNewResidue = false;

		for(int numSquares=minSquares; numSquares<=maxSquares; numSquares++) {
			short first[] = getStoredResidues(cur1, numSquares);
			short second[] = getStoredResidues(cur2, numSquares);
			
			BigInteger sum = BigInteger.ZERO;
			if(first != null) {
				sum.add(ChineseRemainderTheoremUtilFunctions.deriveTotalBasedOnModResidues(first));
			}
			if(second != null) {
				sum.add(ChineseRemainderTheoremUtilFunctions.deriveTotalBasedOnModResidues(second));
			}
			
			short tmpStoredResidues[] = ChineseRemainderTheoremUtilFunctions.convertBigIntegerToResidues(sum, numResiduesToUse);
			
			if(ChineseRemainderTheoremUtilFunctions.shouldAddNewResidueToDataStruct(tmpStoredResidues)) {
				addNewResidue = true;
				break;
			}
			
		}
		
		if(addNewResidue) {
			numResiduesToUse++;
		}
		//End check if we need to add new residue.
		
		short ret[] = new short[1 + numResiduesToUse * (maxSquares - minSquares + 1)];
		
		ret[0] = (short)(minSquares * NUM_COMBOS_BYTES + maxSquares);
		
		
		int curIndex = 1;
		for(int numSquares=minSquares; numSquares<=maxSquares; numSquares++) {
			short first[] = getStoredResidues(cur1, numSquares);
			short second[] = getStoredResidues(cur2, numSquares);
			
			BigInteger sum = BigInteger.ZERO;
			if(first != null) {
				sum.add(ChineseRemainderTheoremUtilFunctions.deriveTotalBasedOnModResidues(first));
			}
			if(second != null) {
				sum.add(ChineseRemainderTheoremUtilFunctions.deriveTotalBasedOnModResidues(second));
			}
			
			short tmpStoredResidues[] = ChineseRemainderTheoremUtilFunctions.convertBigIntegerToResidues(sum, numResiduesToUse);
			
			for(int i=0; i<numResiduesToUse; i++) {
				ret[curIndex + i] = tmpStoredResidues[i];
			}
			
			curIndex += numResiduesToUse;
		}
		
		return ret;
	}

	//TODO: hardCopyAdd1SquareThough
	//Note that hardCopyAdd1Square might means reducing the size of the output array, so be careful about that.
	
	//TODO: do it right!
	public static short[] hardCopyAdd1SquareThough(short cur[], int maxForNumSquaresTarget) {
		
		short ret[] = new short[cur.length];
		
		for(int i=0; i<ret.length; i++) {
			ret[i] = cur[i];
		}

		int minSquares = getMinSquares(cur) + 1;
		int maxSquares = getMaxSquares(cur) + 1;
		
		//TODO: if maxSquares goes over limit, there's more work to be done!
		
		ret[0] = (short)(minSquares * NUM_COMBOS_BYTES + maxSquares);
		
		
		return ret;
	}
	
	//TODO: test this debug function...
	public void printEnumerationsForBoundary(short cur[], int length) {
		
		int minSquares = getMinSquares(cur);
		int maxSquares = getMaxSquares(cur);
		int numResiduesUsed = getNumResiduesUsed(cur);

		
		for(int i=0; i<length; i++) {
			
			if(i >= minSquares && i <= maxSquares) {
				
				short storedResidues[] = getStoredResidues(cur, i, minSquares, maxSquares, numResiduesUsed);
				
				System.out.print(ChineseRemainderTheoremUtilFunctions.deriveTotalBasedOnModResidues(storedResidues));
				
			}
			
			System.out.print(cur + ", ");

		}
		System.out.println();
		
	}
	
	//TODO: test
	public static BigInteger getEnuration(short cur[], int numSquares) {
		int minSquares = getMinSquares(cur);
		int maxSquares = getMaxSquares(cur);
		int numResiduesUsed = getNumResiduesUsed(cur);
		
		
		if(numSquares >= minSquares && numSquares <= maxSquares) {
			short storedResidues[] = getStoredResidues(cur, numSquares, minSquares, maxSquares, numResiduesUsed);

			return ChineseRemainderTheoremUtilFunctions.deriveTotalBasedOnModResidues(storedResidues);

		} else {
			return BigInteger.ZERO;
		}
		
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

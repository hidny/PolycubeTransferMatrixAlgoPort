package optimized5;

import java.math.BigInteger;


public class PartialGen7Functions {

	//Idea:
	// just represent numbers with 8 shorts... and keep everything in arrays.
	// I'll think about space optimization tricks later.
	
	//1st index is short that has 1 byte for min and 1 byte for max.
	
	public static int NUM_BITES_IN_BYTE = 8;
	public static int NUM_COMBOS_BYTES = (int)Math.pow(2, NUM_BITES_IN_BYTE);
	public static int SIZE_DESC_INDEX = 0;
	
	public static final int NUM_RESIDUES = 8;
	
	public static short[] getInitialPartialGen() {
		
		short ret[] = new short[1 + NUM_RESIDUES];
		
		ret[0] = 0;
		for(int i=0; i<NUM_RESIDUES; i++) {

			ret[i + 1] = 1;
		}
		
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

	
	public static short[] getStoredResidues(short cur[], int numSquares) {
		return getStoredResidues(cur, numSquares, getMinSquares(cur), getMaxSquares(cur), NUM_RESIDUES);
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
		
		
		int minSquares2 = getMinSquares(cur2);
		int maxSquares2 = getMaxSquares(cur2);
		
		int minSquares = Math.min(minSquares1, minSquares2);
		int maxSquares = Math.max(maxSquares1, maxSquares2);
		
		
		short ret[] = new short[1 + NUM_RESIDUES * (maxSquares - minSquares + 1)];
		
		ret[0] = (short)(minSquares * NUM_COMBOS_BYTES + maxSquares);
		
		
		int curIndex = 1;
		for(int numSquares=minSquares; numSquares<=maxSquares; numSquares++) {
			short first[] = getStoredResidues(cur1, numSquares);
			short second[] = getStoredResidues(cur2, numSquares);
			
			if(first != null && second != null) {
				for(int i=0; i<NUM_RESIDUES; i++) {
					ret[curIndex + i] = (short)((first[i] + second[i]) % CRTUtilFunctions2.primes[i]);
				}
			} else if(first != null) {
				for(int i=0; i<NUM_RESIDUES; i++) {
					ret[curIndex + i] = first[i];
				}
				
			} else if(second != null) {
				for(int i=0; i<NUM_RESIDUES; i++) {
					ret[curIndex + i] = second[i];
				}
			} else {
				
				for(int i=0; i<NUM_RESIDUES; i++) {
					ret[curIndex + i] = 0;
				}
			}
			curIndex += NUM_RESIDUES;
		}
		
		return ret;
	}

	//TODO: do it right!
	public static short[] hardCopyAdd1SquareThough(short cur[], int maxForNumSquaresTarget) {

		int minSquares = getMinSquares(cur) + 1;
		int maxSquares = getMaxSquares(cur) + 1;
		
		short ret[] = null;
		
		if(maxSquares <= maxForNumSquaresTarget) {
			//TODO: if maxSquares goes over limit, there's more work to be done!
			
			ret = new short[cur.length];
			
		} else if(minSquares > maxForNumSquaresTarget) {
			System.out.println("ERROR: min squares is too high in hardCopyAdd1SquareThough");
			System.exit(1);
			return null;
		
		} else {
			maxSquares = maxForNumSquaresTarget;
			
			ret = new short[1 + NUM_RESIDUES * (maxSquares - minSquares + 1)];
		}
		

		for(int i=0; i<ret.length; i++) {
			ret[i] = cur[i];
		}
		
		ret[0] = (short)(minSquares * NUM_COMBOS_BYTES + maxSquares);
		
		return ret;
	}
	

	public static void printEnumerationsForBoundary(short cur[], int numSquares) {
		
		int minSquares = getMinSquares(cur);
		int maxSquares = getMaxSquares(cur);

		
		for(int i=0; i<numSquares + 1; i++) {
			
			if(i >= minSquares && i <= maxSquares) {
				
				short storedResidues[] = getStoredResidues(cur, i, minSquares, maxSquares, NUM_RESIDUES);
				
				System.out.print(CRTUtilFunctions2.deriveTotalBasedOnModResidues(storedResidues) + ", ");
				
			} else {
			
				System.out.print("0, ");
			}
		}
		System.out.println();
		
	}
	
	
	
}

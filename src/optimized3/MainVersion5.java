package optimized3;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import optimized2.RecursiveMinSquaresNeedToAttach;

public class MainVersion5 {

	//goal:
	// Implement the algo described in https://arxiv.org/pdf/cond-mat/0007239.pdf
	/* "Enumerations of lattice animals and trees
	Iwan Jensen
	Department of Mathematics and Statistics,
	The University of Melbourne,
	Victoria 3010, Australia
	September 11, 2018
"*/

/* After I successfully port it, I'll try to modify it to find the number of rotational symmetries in 2D,
 * then I'll to do the same thing for 2D reflections.
 * Afterwards, I believe I will be able to deduce the number of free polyominoes (A000105) up to N=56 (or go as high as the number of fixed 2D solutions goes (fixed: https://oeis.org/A001168) )
 * My only doubt is that my program might be so inefficient that N=56 is still out of reach even when given the handicap of being able to force a symmetry... We'll see though.
 */

	//This is a lot less efficient than what they describe...
	
	public static void main(String args[]) {
		
		testSignature();
		
		initLowerTwoDissapeared();
		
		//solve(5);
		
		testArray();
		//System.out.println("Num hashes removed: " + debugTooBig);
	}
	
	public static void testArray() {
		
		int MAX = 42;
		
		BigInteger output[] = new BigInteger[MAX + 1];
		for(int i=0; i<MAX + 1; i++) {
			
			output[i] = solve(i);
		}
		
		System.out.println("Sequence:");
		for(int i=0; i<output.length; i++) {
			System.out.println(i + ": " + output[i]);
		}
	}
	/*
	 * Sequence:
0: 1
1: 1
2: 2
3: 6
4: 19
5: 63
6: 216
7: 760
8: 2725
9: 9910
10: 36446
11: 135268
12: 505861
13: 1903890
14: 7204874
15: 27394666
16: 104592937
17: 400795844
18: 1540820542
19: 5940738676
20: 22964779660
21: 88983512783
22: 345532572678
23: 1344372335524
24: 5239988770268
25: 20457802016011
26: 79992676367108
27: 313224032098244
Final number for N = 28:    1228088671826973
Final number for N = 29:    4820975409710116
Final number for N = 30:   18946775782611174 (took 10 minutes)
Final number for N = 31:   74541651404935148 (took almost 20 minutes)
Final number for N = 32:  293560133910477776 (took about 15 minutes)
Final number for N = 33: 1157186142148293638 (took about 30 minutes)
Final number for N = 34: 4565553929115769162 (took about 1 hour and 30 minutes) **
Final number for N = 35: 18027932215016128134 (1.5 hours?)
Final number for N = 36: 71242712815411950635 (3 hours?)
Final number for N = 37: 281746550485032531911 (2 hours?)
Final number for N = 38: 1115021869572604692100 (5 hours ?)
Final number for N = 39: 2146745613912593599828 ( 5 hours?) (It's wrong! long type finally failed me...)
// long overflow: (expectedAns - ans)/2^(64-1) = (4415695134978868448596-2146745613912593599828)/2^63 = 246. Looks like it overflowed 246 times...

	 */

	//Really inefficient storage method...
	public static HashMap<Long, short[]> prevConfigs;
	public static HashMap<Long, short[]> curConfigs;
	

	public static final int NEW_LEFT_EMPTY_SQUARE_INDEX = 0;
	public static final int NEW_TOP_EMPTY_SQUARE_INDEX = 1;

	public static final int FILL_SQUARE_INDEX_DISPLACEMENT = 2;

	public static final boolean INIT_UPPER_BORDER_UNTOUCHED = false;
	public static final boolean INIT_LOWER_BORDER_UNTOUCHED = false;
	
	public static final int BOOL_OPTIONS_COUNT = 2;
	
	//Matrix is just a copy of what's in the paper
	public static int transitionMatrix[][][] =
             {{{ 0,  0,   1,  0}, { 0,  1,   2,   4}, { 0,  2,   2,   3}, { 0,  3,   3,   3}, { 0,  4,   3,   4}},
              {{-1, -1,   1,  0}, {-2, -2,   2,   4}, {-2, -2,   2,   3}, {-2, -2,   3,   3}, {-3, -3,  -3,  -3}},
              {{ 0,  0,   2,  0}, { 0,  1,   2,   3}, { 0,  2,   2,   3}, { 0,  2,   2,   3}, { 0,  1,   2,   4}},
              {{ 0,  0,   3,  0}, { 0,  1,   3,   3}, { 0,  2,   3,   3}, { 0,  3,   3,   3}, { 0,  4,   3,   4}},
              {{ 0,  0,   4,  0}, { 0,  1,   3,   4}, { 0,  2,   3,   3}, { 0,  3,   3,   3}, {-3, -3,  -3,  -3}}
          };
	
	public static boolean lowerTwoDissapeared[][][] = null;
	public static boolean lowerFourDissapeared[][][] = null;
	
	public static boolean mergingTwoSeparteSections[][][] = null;
	
	
	public static void initLowerTwoDissapeared() {
		lowerTwoDissapeared = new boolean[transitionMatrix.length][transitionMatrix[0].length][BOOL_OPTIONS_COUNT];
		lowerFourDissapeared = new boolean[transitionMatrix.length][transitionMatrix[0].length][BOOL_OPTIONS_COUNT];
		mergingTwoSeparteSections = new boolean[transitionMatrix.length][transitionMatrix[0].length][BOOL_OPTIONS_COUNT];
		
		for(int i=0; i<lowerTwoDissapeared.length; i++) {
			for(int j=0; j<lowerTwoDissapeared[0].length; j++) {
				for(int k=0; k<lowerTwoDissapeared[0][0].length; k++) {
					lowerTwoDissapeared[i][j][k] = false;
					lowerFourDissapeared[i][j][k] = false;
					mergingTwoSeparteSections[i][j][k] = false;
				}
			}
		}
		
		//This logic is based on where the paper put the ^ and - symbols on top of the output in the table
		
		lowerTwoDissapeared[2][0][0] = true;
		lowerTwoDissapeared[2][1][0] = true;
		lowerTwoDissapeared[2][2][0] = true;

		lowerFourDissapeared[4][0][0] = true;
		lowerFourDissapeared[4][1][0] = true;
		lowerFourDissapeared[4][2][0] = true;
		lowerFourDissapeared[4][3][0] = true;
		
		
		//Apparently, On a merge section, there's 3 cases and the lower one determines the case:
		mergingTwoSeparteSections[2][2][1] = true;
		mergingTwoSeparteSections[3][2][1] = true;
		mergingTwoSeparteSections[4][3][1] = true;
		
		
	}
	
	public static final int ABANDONED_ISOLATED_CELL_POSSIBLY_OK = -1;
	public static final int CELL_MUST_BE_OCCUPIED = -2;
	public static final int ERROR_SITUATION_SHOUND_NOT_HAPPEN = -3;
	

	public static final BigInteger TWO = new BigInteger("2");
	
	public static BigInteger solve(int numSquares) {
		
		if(numSquares == 0) {
			return BigInteger.ONE;
		}
		

		//According to the paper, and the weird proof in my head,
		// if a 'animal' lives in a L*W rect lattice and L>=W, then W <= floor((maxSquares + 1)/2)
		int maxWidth = Math.floorDiv(numSquares + 1, 2);
		
		BigInteger ret = BigInteger.ZERO;
		
		
		for(int width=1; width<=maxWidth; width++) {
			
			short curPartail[] = PartialGen4Functions.getInitialPartialGen();
			
			long seed = getSignature(createIntialBoundaryLine(width), INIT_UPPER_BORDER_UNTOUCHED, INIT_LOWER_BORDER_UNTOUCHED);
			
			prevConfigs = new HashMap<Long, short[]>();
			prevConfigs.put(seed, curPartail);
			
			
			//I added +5 at the end just in case it matters, but I think it doesn't.
			for(int length=1; length <= numSquares - width + 1 + 5; length++) {
				
				int minLengthToGo = Math.max(0, width - length);
				
				for(int i=0; i<width; i++) {
					
					curConfigs = new HashMap<Long, short[]>();
					
					// Took from: https://sentry.io/answers/iterate-hashmap-java/
					Iterator<Map.Entry<Long, short[]>> iterator = prevConfigs.entrySet().iterator();					
					
				    while (iterator.hasNext()) {
				    	
				    	Map.Entry<Long, short[]> entry = iterator.next();

				    	short prevPartialGen[] = prevConfigs.get(entry.getKey());
						
						long curSignature = entry.getKey();

						boolean origUpperBorderTouched = ((curSignature % 4) /2) == 1;
						boolean origLowerBorderTouched = (curSignature % 2) == 1;

						int boundaryLine[] =  getBoundaryLineFromSignature(curSignature, width);
						
						int origTop = 0;
						int origBottom = 0;
						
						if(i == 0) {
							origTop = 0;
						} else {

							origTop = boundaryLine[i - 1];
						}						
						origBottom = boundaryLine[i];

						
						// Assume there's no new square:
						for(int fillSquare=0; fillSquare<2; fillSquare++) {

							boundaryLine =  getBoundaryLineFromSignature(curSignature, width);
							
							int newTop = transitionMatrix[origBottom][origTop][NEW_TOP_EMPTY_SQUARE_INDEX + FILL_SQUARE_INDEX_DISPLACEMENT * fillSquare];
							int newBottom = transitionMatrix[origBottom][origTop][NEW_LEFT_EMPTY_SQUARE_INDEX + FILL_SQUARE_INDEX_DISPLACEMENT * fillSquare];
	
							if(newTop < 0 || newBottom < 0) {
								
								if(newTop == ABANDONED_ISOLATED_CELL_POSSIBLY_OK) {
									
									//Check if we're just done with making an animal and there's new graph with a separate component: 
									boolean looksGoodSoFar = true;
									if(origUpperBorderTouched && origLowerBorderTouched) {
										
										for(int j=0; j<boundaryLine.length; j++) {
											if(j != i && boundaryLine[j] != 0) {
												looksGoodSoFar = false;
												break;
											}
										}
										
									} else {
										looksGoodSoFar = false;
									}
									
									if( looksGoodSoFar ) {
										
										//At this point, we're done with making an animal 
										
										if(length > width && origUpperBorderTouched && origLowerBorderTouched) {
											BigInteger numSolutions = PartialGen4Functions.getEnuration(prevConfigs.get(curSignature), numSquares);
											
											
											if(length == width + 1) {
												ret = ret.add(numSolutions);

											} else {
												ret = ret.add(numSolutions.multiply(TWO));		

											}
										}
										
									}
									continue;
								
								} else if(newTop == CELL_MUST_BE_OCCUPIED) {
									continue;

								} else if(newTop == ERROR_SITUATION_SHOUND_NOT_HAPPEN) {

									System.out.println("ERROR: transition matrix gave \"ERROR_SITUATION_SHOUND_NOT_HAPPEN\"!");
									System.exit(1);
									
								} else {
									System.out.println("ERROR: transition matrix gave an unknown negative number! HANDLE IT OR FIX IT");
									System.exit(1);
								}
								
							} else if(lowerTwoDissapeared[origBottom][origTop][fillSquare]) {

								int numNestedTwos = 0;
								
								for(int j=i-1; j>=0; j--) {
									if(boundaryLine[j] == 3 && numNestedTwos == 0) {
										boundaryLine[j] = 2;
										break;

									} else if(boundaryLine[j] == 2) {
										numNestedTwos++;

									} else if(boundaryLine[j] == 4) {

										if(numNestedTwos == 0) {
											boundaryLine[j] = 1;
											break;
										}
										
										numNestedTwos--;

									} else if(j == 0) {
										System.out.println("ERROR: could not find the cell connected to the 2 that dissapeared!");
										System.exit(1);
									}
								}
								
							} else if(lowerFourDissapeared[origBottom][origTop][fillSquare]) {
								
								int numNestedFours = 0;
								
								for(int j=i+1; j<width; j++) {
									if(boundaryLine[j] == 3 && numNestedFours == 0) {
										boundaryLine[j] = 4;
										break;

									} else if(boundaryLine[j] == 4) {
										numNestedFours++;

									} else if(boundaryLine[j] == 2) {

										if(numNestedFours == 0) {
											boundaryLine[j] = 1;
											break;
										}
										
										numNestedFours--;

									} else if(j == width - 1) {
										System.out.println("ERROR: could not find the cell connected to the 4 that dissapeared!");
										System.exit(1);
									}
								}
								
							} else if(mergingTwoSeparteSections[origBottom][origTop][fillSquare]) {

								if(origBottom == 4) {

									//Also cancel associated 2
									int numNestedFours = 0;
									
									for(int j=i+1; j<width; j++) {
										
										if(j == width - 1) {
											System.out.println("ERROR: could not find the cell 4 that's getting merged (case origLeft != 2)");
											System.exit(1);
	
										} else if(boundaryLine[j] == 4) {
											numNestedFours++;

										} else if(boundaryLine[j] == 2) {

											if(numNestedFours == 0) {
												boundaryLine[j] = 3;
												break;
											}

											numNestedFours--;

										}
									}
									
									
								} else  {
									//Also cancel associated 4
									// numNestedTwos starts at -1 in this case:
									int numNestedTwos = -1;
									
									for(int j=i-1; j>=0; j--) {

										if(j == 0) {
											System.out.println("ERROR: could not find the cell 4 that's getting merged (case origLeft != 4)");
											System.exit(1);

										} else if(boundaryLine[j] == 2) {
											numNestedTwos++;

										} else if(boundaryLine[j] == 4) {

											if(numNestedTwos == 0) {
												boundaryLine[j] = 3;
												break;
											}
											
											numNestedTwos--;

										}
									}
								}
							}
							
							
							if(i>0) {
								boundaryLine[i - 1] = newTop;
							}
							
							boundaryLine[i] = newBottom;
							
							
							boolean upperBorderTouched = origUpperBorderTouched;
							boolean lowerBorderTouuched = origLowerBorderTouched;
							
							if(fillSquare == 1 && i == 0) {
								upperBorderTouched = true;
							}
							
							if(fillSquare == 1 && i == width - 1) {
								lowerBorderTouuched = true;
							}
							
							long newSignature = getSignature(boundaryLine, upperBorderTouched, lowerBorderTouuched);
						
							if(fillSquare == 0) {
								if(curConfigs.containsKey(newSignature)) {
									curConfigs.put(newSignature, PartialGen4Functions.hardCopyMerge(curConfigs.get(newSignature), prevPartialGen));
								} else {
									curConfigs.put(newSignature, PartialGen4Functions.hardCopy(prevPartialGen));
								}
							} else {
								
								int maxSquaresAllowedForSignature = numSquares - (getNumToAddToCompleteBasedOnSignature(newSignature, width, minLengthToGo) + minLengthToGo);
								
								if(maxSquaresAllowedForSignature < PartialGen4Functions.getMinSquares(prevPartialGen) + 1) {
									//Nope
								} else {
								
									//"with an addition weight factor u on the source if the new site is occupied"
									// Why not just say weight factor of 1?? Update: I still don't know, but I got away with assuming u=1.

									if(curConfigs.containsKey(newSignature)) {
										// adds more memory:
										
										curConfigs.put(newSignature, PartialGen4Functions.hardCopyMerge(
												curConfigs.get(newSignature),
												PartialGen4Functions.hardCopyAdd1SquareThough(prevPartialGen, maxSquaresAllowedForSignature))
											);
										
									} else {
										
										curConfigs.put(newSignature, PartialGen4Functions.hardCopyAdd1SquareThough(
												prevPartialGen,
												maxSquaresAllowedForSignature));
										
	
									}
								}
							}

						}
						
					}

					//System.out.println(curConfigs.size());
				    prevConfigs = curConfigs;

				} //End adding wedges


				int NUM_TO_REMOVE = 4;

				for(int i=0; i<NUM_TO_REMOVE; i++) {
					boolean upperTouched = i / 2 == 1;
					boolean lowerTouched = i % 2 == 1;

					long signatureToRemove = getSignature(createIntialBoundaryLine(width), upperTouched, lowerTouched);


					if(prevConfigs.containsKey(signatureToRemove)) {

						prevConfigs.remove(signatureToRemove);
						
						if(upperTouched || lowerTouched || length > 1) {
							System.out.println("ERROR: didn't expect to find empty boundary here!");
							System.exit(1);
						}
					}
					
					
				}

			} //END length loop


			//System.out.println("Partial check: " + ret);

		} //END big width loop
		
		
	    System.out.println("Final number for N = " + numSquares + ": " + ret);
		
		return ret;
	}
	
	public static long NUM_STATES = 5;
	
	
	//Observations:
	//TODO: this won't fit into a long in the future.
	
	// What about the num kink?
	// AHA: The signatures are updated at every step, so we don't need the kink
	
	//Guess based on reading the paper:
	// We don't need to add it as part of the signature? A: Yes
	
	//What about the current Num animals? Does that need to be part of signature?
	//AHA: the num animals is the value of the hash! (The hash value is an array)
	/*" Firstly, for each configuration we keep track of the current minimum number of\r\n"
	+ "occupied sites Ncur which have been inserted to the left of the intersection in order to build\r\n"
	+ "up that particular configuration."
	*/
	
	
	
	public static long getSignature(int boundaryLine[], boolean upperBorderTouched, boolean lowerBorderTouuched) {
		
		long ret = 0;
		for(int i=0; i<boundaryLine.length; i++) {
			
			ret = NUM_STATES*ret + boundaryLine[i];
		}
		
		ret*=2;
		
		if(upperBorderTouched) {
			ret+=1;
		}
		
		ret*=2;
		
		if(lowerBorderTouuched) {
			ret+=1;
		}
		return ret;
	}
	
	public static int[] getBoundaryLineFromSignature(long signature, int width) {
		
		int ret[] = new int[width];
		
		long tmp = signature / 4;
		
		for(int i=0; i<ret.length; i++) {
			
			ret[ret.length - 1 - i] = (int)(tmp % NUM_STATES);
			
			tmp /= NUM_STATES;
		}
		
		return ret;
	}
	

	//probably not the way to do this... but it works!
	public static int getNumToAddToCompleteBasedOnSignature(long signature, int width, int minLengthToGo) {

		boolean origUpperBorderTouched = ((signature % 4) /2) == 1;
		boolean origLowerBorderTouched = (signature % 2) == 1;
		
		int boundary[] = getBoundaryLineFromSignature(signature, width);
		
		int ret = 0;
		boolean boundaryAllZeros = false;
		
		if( ! origUpperBorderTouched) {
			
			
			for(int i=0; i<boundary.length && boundary[i] == 0; i++) {
				ret++;
			}
			
			//In practice, this won't happen, but whatever!
			boundaryAllZeros = ret >= boundary.length;
		}
		
		if( ! origLowerBorderTouched && !boundaryAllZeros) {
			
			for(int i=boundary.length - 1; i>=0 && boundary[i] == 0; i--) {
				ret++;
			}
		}

		int tmpRecursive = RecursiveMinSquaresNeedToAttach.getMinDistRecursive(boundary);
		ret += tmpRecursive;
		
		//This fixes the bug where I double count num squares to add because it's already counted in minLengthToGo:
		if(minLengthToGo > 0 && tmpRecursive > 0) {
			ret--;
		}
		
		return ret;
	}
	

	
	public static int[] createIntialBoundaryLine(int width) {

		int initBoundaryLine[] = new int[width];
		for(int i=0; i<initBoundaryLine.length; i++) {
			initBoundaryLine[i] = 0;
		}
		
		return initBoundaryLine;
	}

	public static void testSignature() {
	
		int fakeBoundary[] = new int[2];
		
		for(int i=0; i<NUM_STATES; i++) {
			for(int j=0; j<NUM_STATES; j++) {
				fakeBoundary[0] = i;
				fakeBoundary[1] = j;
				
				boolean tmp1 = false;
				boolean tmp2 = false;
				
				for(int k=0; k<4; k++) {
					if(k % 2 == 1) {
						tmp1 = true;
					}
					
					if(k / 2 == 1) {
						tmp2 = true;
					}
					

					long signature = getSignature(fakeBoundary, tmp1, tmp2);
					
					int newBoundary[] =  getBoundaryLineFromSignature(signature, fakeBoundary.length);
					
					if(newBoundary.length != fakeBoundary.length) {
						System.out.println("signature test: OOPS2!");
						System.exit(1);
					}
					
					//System.out.println();
					for(int i2=0; i2<newBoundary.length; i2++) {
						//System.out.println(newBoundary[i2] + " vs " +  fakeBoundary[i2]);
						if(newBoundary[i2] != fakeBoundary[i2]) {
							System.out.println("signature test: OOPS!");
							
							System.exit(1);
							
						}
					}
					
				}
			}
		}
		
		
	}
	
	public static void debugPrintCurState(int boundaryLine[], int curI, boolean topTouched, boolean bottomTouched) {
		
		System.out.println("-----");
		if(topTouched) {
			System.out.println("(Top touched)");
		}
		for(int i2=0; i2<boundaryLine.length; i2++) {
			if(i2 < curI) {
				System.out.println("   " + boundaryLine[i2]);
			} else {
				System.out.println(boundaryLine[i2]);
			}
		}
		if(bottomTouched) {
			System.out.println("(Bottom touched)");
		}
		System.out.println("-----");
	}
}
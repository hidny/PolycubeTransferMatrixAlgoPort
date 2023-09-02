package optimized2;

public class RecursiveMinSquaresNeedToAttach {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		standardTests();
		//specificTests();
		
		//specificTests2();
	}
	
	
	public static void standardTests() {
		//Top-level function tests:
		System.out.println("Top level function test:");
		//Expect: 0
		int oneOne[] = new int[] {0,0,0,0,0,1,0};
		

		if(getMinDistRecursive(oneOne) != 0) {
			System.out.println("Test oneOne failed! " + getMinDistRecursive(oneOne));
		}
		

		//Expect: 0
		int allZeros[] = new int[] {0,0,0,0,0,0,0};


		if(getMinDistRecursive(allZeros) != 0) {
			System.out.println("Test allZeros failed! " + getMinDistRecursive(allZeros));
		}

		//Expect: 2
		int twoOnes[] = new int[] {0,0,1,0,1,0,0};
		

		if(getMinDistRecursive(twoOnes) != 2) {
			System.out.println("Test twoOnes failed! " + getMinDistRecursive(twoOnes));
		}
		

		//Expect: 1
		int twoOnesadjacent[] = new int[] {0,0,1,1,0,0,0};
		
		if(getMinDistRecursive(twoOnesadjacent) != 1) {
			System.out.println("Test twoOnesadjacent failed! " + getMinDistRecursive(twoOnesadjacent));
		}
		
		//Expect: 2
		int basic42Test[] = new int[] {0,1,0,4,0,0,2};
		
		if(getMinDistRecursive(basic42Test) != 2) {
			System.out.println("Test basic42Test failed! " + getMinDistRecursive(basic42Test));
		}
		

		int testStart42[] = new int[] {4,0,2,0,0,1,0};
		
		if(getMinDistRecursive(testStart42) != 3) {
			System.out.println("Test testStart42 failed! " + getMinDistRecursive(testStart42));
		}


		int testEnd42[] = new int[] {0,1,0,0,0,4,0,2};
		
		if(getMinDistRecursive(testEnd42) != 4) {
			System.out.println("Test testStart42 failed! " + getMinDistRecursive(testEnd42));
		}
		


		int teststartEnd42[] = new int[] {4,0,2,0,0,4,0,2};
		
		if(getMinDistRecursive(teststartEnd42) != 3) {
			System.out.println("Test teststartEnd42 failed! " + getMinDistRecursive(teststartEnd42));
		}
		
		int teststart42Complicated[] = new int[] {4,0,2,0,0,4,0,2, 0, 0, 0, 1, 0, 0};
		
		if(getMinDistRecursive(teststart42Complicated) != 8) {
			System.out.println("Test teststart42Complicated failed! " + getMinDistRecursive(teststart42Complicated));
		}

		//Recursive tests:

		System.out.println("Basic Recursion tests:");
		
		int teststart42FromBottomBasic[] = new int[] {4,0,0,0,0,1,0,2, 0, 0, 0, 0, 0, 0};
		
		if(getMinDistRecursive(teststart42FromBottomBasic) != 2) {
			System.out.println("Test teststart42FromBottomBasic failed! " + getMinDistRecursive(teststart42FromBottomBasic));
		}

		int teststart42FromTopBasic[] = new int[] {4,0,0,1,0,0,0,0,2, 0, 0, 0, 0, 0, 0};
		
		if(getMinDistRecursive(teststart42FromTopBasic) != 3) {
			System.out.println("Test teststart42FromTopBasic failed! " + getMinDistRecursive(teststart42FromTopBasic));
		}
		
		
		int teststart42FromBottomBasic2[] = new int[] {4,0,0,0,4,2,0,2, 0, 0, 0, 0, 0, 0};
		
		if(getMinDistRecursive(teststart42FromBottomBasic2) != 2) {
			System.out.println("Test teststart42FromBottomBasic2 failed! " + getMinDistRecursive(teststart42FromBottomBasic2));
		}

		int teststart42FromTopBasic2[] = new int[] {4,0,0,4,2,0,0,0,2, 0, 0, 0, 0, 0, 0};
		
		if(getMinDistRecursive(teststart42FromTopBasic2) != 3) {
			System.out.println("Test teststart42FromTopBasic2 failed! " + getMinDistRecursive(teststart42FromTopBasic2));
		}
		//TODO: test with the 3s...
		
		int teststart432FromBottomBasic[] = new int[] {4,0,0,0,0,1,0,0, 3, 0, 0, 0, 0, 2};
		
		if(getMinDistRecursive(teststart432FromBottomBasic) != 3) {
			System.out.println("Test teststart42FromBottomBasic failed! " + getMinDistRecursive(teststart432FromBottomBasic));
		}
		
		int teststart432FromTopBasic[] = new int[] {4,0,0,0,0,0,0,0, 3, 1, 0, 0, 0, 2};
		
		if(getMinDistRecursive(teststart432FromTopBasic) != 1) {
			System.out.println("Test teststart432FromTopBasic failed! " + getMinDistRecursive(teststart432FromTopBasic));
		}
		
		//Double recursion test:
		System.out.println();
		System.out.println("Double Recursion tests:");
		
		int testDoubleRecursionBasic[] = new int[] {4,0,0,0,0,4,0,1, 0, 0, 0, 0, 2, 2};
		
		if(getMinDistRecursive(testDoubleRecursionBasic) != 4) {
			System.out.println("Test testDoubleRecursionBasic failed! " + getMinDistRecursive(testDoubleRecursionBasic));
		}


		int testDoubleDoubleRecursionBasic[] = new int[] {4,4,2,0,0,4,0,0, 0,0,0, 1, 0, 0, 0, 2, 0, 2};
		
		if(getMinDistRecursive(testDoubleDoubleRecursionBasic) != 8) {
			System.out.println("Test testDoubleDoubleRecursionBasic failed! " + getMinDistRecursive(testDoubleDoubleRecursionBasic));
		}
		
		int testDoubleDoubleRecursionBasic2[] = new int[] {4,0,0,4,2,0,0,4,0,0, 0,0,0, 1, 0, 0, 0, 2, 0, 2};
		
		if(getMinDistRecursive(testDoubleDoubleRecursionBasic2) != 10) {
			System.out.println("Test testDoubleDoubleRecursionBasic2 failed! " + getMinDistRecursive(testDoubleDoubleRecursionBasic));
		}
		
		int testRecursionStraightThruFromBottom[] = new int[] {4,0,0,0,4,2,0,0,4,3,3, 0,0,2, 0, 4, 3, 3, 2, 0, 2};
		
		if(getMinDistRecursive(testRecursionStraightThruFromBottom) != 9) {
			System.out.println("Test testRecursionStraightThruFromBottom failed! " + getMinDistRecursive(testRecursionStraightThruFromBottom));
		}
		
		int testRecursionStraightThruFromBottomAmbiguous[] = new int[] {4,0,0,4,2,0,0,4,3,3, 0,0,2, 0, 4, 3, 3, 2, 0, 2};

		if(getMinDistRecursive(testRecursionStraightThruFromBottomAmbiguous) != 9) {
			System.out.println("Test testRecursionStraightThruFromBottomAmbiguous failed! " + getMinDistRecursive(testRecursionStraightThruFromBottomAmbiguous));
		}

		System.out.println();
		System.out.println("Test random complicated:");
		
		int testRandomComplicated[] = new int[] {4,0,1, 0 ,4,2,0,0,4,3,3, 0,0,2, 0, 4, 3, 3, 2, 0, 0, 0, 2};

		if(getMinDistRecursive(testRandomComplicated) != 11) {
			System.out.println("Test testRandomComplicated failed! " + getMinDistRecursive(testRandomComplicated));
		}
		
		int testRandomComplicated2[] = new int[] {4,0,1, 0 ,4,2,0,0,4,3,3, 0,0,2, 0, 4, 3, 3, 2, 0, 0, 0, 2, 0, 0, 4, 0, 0, 2};

		if(getMinDistRecursive(testRandomComplicated2) != 15) {
			System.out.println("Test testRandomComplicated2 failed! " + getMinDistRecursive(testRandomComplicated2));
		}
		
		int testRandomComplicated3[] = new int[] {4,0,1, 0 ,4,2,0,0,4,3,3, 0,0,2, 0, 4, 3, 3, 2, 0, 0, 0, 2, 0, 0, 4, 0, 0, 0, 1, 0, 2};

		if(getMinDistRecursive(testRandomComplicated3) != 18) {
			System.out.println("Test testRandomComplicated3 failed! " + getMinDistRecursive(testRandomComplicated3));
		}
		
		System.out.println();
		System.out.println("Test semi-functional:");
		
		int testSemiFunctional1[] = new int[] {4,0,1,0,0,2,0,0, 0, 1};
		
		if(getMinDistRecursive(testSemiFunctional1) != 7) {
			System.out.println("Test testSemiFunctional1 failed! " + getMinDistRecursive(testSemiFunctional1));
		}
		

		int testSemiFunctional2[] = new int[] {0, 1, 0, 4,0,1,0,0,2,0,0, 0, 0};
		
		if(getMinDistRecursive(testSemiFunctional2) != 4) {
			System.out.println("Test testSemiFunctional2 failed! " + getMinDistRecursive(testSemiFunctional2));
		}
		
		int testBetween3s[]  = new int[] {0, 4, 0, 3,0,1,3,3,0,3,0, 2, 0};
		if(getMinDistRecursive(testBetween3s) != 1) {
			System.out.println("Test testBetween3s failed! " + getMinDistRecursive(testBetween3s));
		}
		

		int testBetween3s2[]  = new int[] {0, 4, 0, 3,0,1,3,3,0,3,0,0,4,2,0,0,0,0,3, 2, 0};
		if(getMinDistRecursive(testBetween3s2) != 5) {
			System.out.println("Test testBetween3s2 failed! " + getMinDistRecursive(testBetween3s2));
		}
		
		int testBetween3s3[]  = new int[] {0, 4, 0, 3,0,1,3,3,0,3,0,0,0,0,0,4,2,0,0,3, 2, 0};
		if(getMinDistRecursive(testBetween3s) != 5) {
			System.out.println("Test testBetween3s failed! " + getMinDistRecursive(testBetween3s));
		}
		
		int testTricky1[]  = new int[] {0, 4, 0, 3,3,3,3,3,4,3,3,3,0,0,0,2,2,0,0};
		if(getMinDistRecursive(testTricky1) != 1) {
			System.out.println("Test testTricky1 failed! " + getMinDistRecursive(testTricky1));
		}
		
		
		System.out.println("Done test");
		System.out.println();
	}

	public static void specificTests() {
		
		System.out.println("Hello specific test");
		
		int testBefween3Simple[]  = new int[] {0, 4, 0, 3, 0, 0, 1, 3, 0, 0, 3, 2, 0};
		if(getMinDistRecursive(testBefween3Simple) != 1) {
			System.out.println("Test testBefween3Simple failed! " + getMinDistRecursive(testBefween3Simple));
		}
	}
	
	public static void specificTests2() {
		
		System.out.println("Hello specific test 2");
		
		int testBetween3s3[]  = new int[] {0, 4, 0, 3,0,1,3,3,0,3,0,0,0,0,0,4,2,0,0,3, 2, 0};
		if(getMinDistRecursive(testBetween3s3) != 5) {
			System.out.println("Test testBetween3s failed! " + getMinDistRecursive(testBetween3s3));
		}
	}

	public static final int RET_INDEX = 0;
	public static final int TOP_USED_INDEX = 1;
	public static final int BOTTOM_INDEX_USED = 2;
	

	public static int getMinDistRecursive(int boundary[]) {
		
		//From the top:
		
		//Assume that the 1st square is already setup to attach to the others on the boundary by setting this to -1:
		int ret = -1;

		boolean TOP_LEVEL_COMING_FROM_TOP_TO_BOTTOM = true;
		
		int prevIndex = -1;
		
		int curIndex = 0;
		do {
			
			boolean prevStartIndexUsed = false;
			
			for(; curIndex<boundary.length; curIndex++) {
				if(boundary[curIndex] == 4 ||  boundary[curIndex] == 1) {
					break;
				} else if(boundary[curIndex] != 0 ) {
					System.out.println("ERROR: something went wrong in getMinDistRecursive (1)");
					System.exit(1);
				}
			}
			
			if(curIndex < boundary.length) {
				
				if(prevIndex != -1) {
					
					ret += curIndex - prevIndex;
					
					if( ! prevStartIndexUsed) {
						ret++;
					}
				}
				
				if(boundary[curIndex] == 4) {

					
					int index4 = curIndex;
					
					//System.out.println("TEST: " + getIndex2Equiv(boundary, index4));
					
					//
					int tmp[] = getMinDistRecursiveBetween4and2(boundary, TOP_LEVEL_COMING_FROM_TOP_TO_BOTTOM, index4, getIndex2Equiv(boundary, index4));
					
					ret+=tmp[RET_INDEX];

					//Check for bottom bonus:
					prevStartIndexUsed = tmp[BOTTOM_INDEX_USED] == 1;

					prevIndex = getIndex2Equiv(boundary, index4);

					curIndex = prevIndex;
	
				} else if(boundary[curIndex] == 1) {
					
					prevStartIndexUsed = true;
					
				} else {
					System.out.println("ERROR: something went wrong in getMinDistRecursive (2)");
					System.exit(1);
				}

				prevIndex = curIndex;
				
				curIndex++;
			}
		} while(curIndex < boundary.length);
		
		
		
		
		return Math.max(0,  ret);
	}
	
	
	private static int[] getMinDistRecursiveBetween4and2(int boundary[], boolean comingFromTop, int index4, int index2) {
		
		int ret[] = new int[3];
	
		
		
		if(comingFromTop) {
			int prevIndex = index4;
			boolean foundInBetween = false;
			
			int curNumLevelsDeep = 0;
			
			boolean curComingFromTop = true;
			
			for(int i=index4 + 1; i<index2; i++ ) {
				//System.out.println("cur: " + ret[RET_INDEX]);
			
				if(curNumLevelsDeep == 0) {
					if(boundary[i] == 4) {

						foundInBetween = true;
						curNumLevelsDeep++;
						
					} else if(boundary[i] == 1) {
						foundInBetween = true;
					
					} else if(boundary[i] == 3 || boundary[i] == 2) {
						
						if(foundInBetween) {
							
							//System.out.println("In recur");
							int tmp[] = getNumBetween33(boundary, curComingFromTop, false, prevIndex, i);

							//System.out.println("Out recur");
							curComingFromTop = tmp[BOTTOM_INDEX_USED] == 1;
							ret[RET_INDEX] += tmp[0];
							//System.out.println("tmp[0]: " + tmp[0]);
							//System.out.println("More: " + ret[RET_INDEX]);
							
						} else {

							curComingFromTop = false;
						}
						prevIndex= i;
						foundInBetween = false;
					}
				} else {
					if(boundary[i] == 4) {
						curNumLevelsDeep++;

					} else if(boundary[i] == 2) {
						curNumLevelsDeep--;
					}
				}
				
			}

		} else if( ! comingFromTop) {
			
			//TODO: mirror above, but do it from the bottom.
		}

		//System.out.println("cur end: " + ret[RET_INDEX]);
		return ret;
	}
	
	
	private static int[] getNumBetween33(int boundary[], boolean comingFromTop, boolean comingFromBottom, int index4or3, int index3or2) {
		
		int numSectionsToAttachTo = getNumSectionsToAttachTo(boundary, index4or3, index3or2);
		
		if(numSectionsToAttachTo == 0) {
			System.out.println("ERROR: called getNumBetween33 even though there's nothing to attach to");
			System.exit(1);
			return new int[] {0, 0, 0};
			
		}
		
		
		//100000 is just a big number..
		int ret = 1000000;
		int retComingFromTop = 0;
		int retComingFromBottom = 0;
		
		for(int numAttachToTop=0; numAttachToTop<=numSectionsToAttachTo; numAttachToTop++) {
			
			int curMinSquaresCount = 0;
			
			int prevStartIndexTop = index4or3;
			int curIndexFromTop = index4or3 + 1;

			boolean curPrevStartIndexUsed = comingFromTop;
			
			for(int i=0; i<numAttachToTop; i++) {
				
				while(boundary[curIndexFromTop] == 0) {
					curIndexFromTop++;
					
					if(curIndexFromTop >= index3or2) {
						System.out.println("ERROR: something went terribly wrong...");
						System.exit(1);
					}
				}
				
				if(boundary[curIndexFromTop] == 1) {
					curMinSquaresCount += curIndexFromTop - prevStartIndexTop;
					if( ! curPrevStartIndexUsed) {
						curMinSquaresCount++;
					}
					
					curPrevStartIndexUsed = true;
					prevStartIndexTop = curIndexFromTop;

				} else if(boundary[curIndexFromTop] == 4) {
					
					int index2Equiv = getIndex2Equiv(boundary, curIndexFromTop);

					int recursion[] = getMinDistRecursiveBetween4and2(boundary, true, curIndexFromTop, index2Equiv);
					
					curMinSquaresCount += recursion[0];
					
					curMinSquaresCount += curIndexFromTop - prevStartIndexTop;
					if( ! curPrevStartIndexUsed) {
						curMinSquaresCount++;
					}

					if(recursion[2] == 1) {
						curPrevStartIndexUsed = true;
					}
					prevStartIndexTop = index2Equiv;
					
				} else {
					System.out.println("ERROR: something went terribly wrong... Unexpected boundary number 1");
					System.exit(1);
				}
				
				curIndexFromTop++;
				
			}
			
			//Copy/pastish code, but from bottom

			boolean curPrevStartIndexUsedFromBottom = comingFromBottom;
			int prevStartIndexBottom = index3or2;
			int curIndexFromBottom = index3or2 - 1;
			
			int numAttachToBottom=numSectionsToAttachTo - numAttachToTop;

			for(int j=0; j<numAttachToBottom; j++) {
				
				while(boundary[curIndexFromBottom] == 0) {
					curIndexFromBottom--;
					
					if(curIndexFromBottom <= index4or3) {
						System.out.println("ERROR: something went terribly wrong in bottom part of getNumBetween33...");
						System.exit(1);
					}
				}
				
				if(boundary[curIndexFromBottom] == 1) {
					curMinSquaresCount += prevStartIndexBottom - curIndexFromBottom;
					if( ! curPrevStartIndexUsedFromBottom) {
						curMinSquaresCount++;
					}
					
					curPrevStartIndexUsedFromBottom = true;
					prevStartIndexBottom = curIndexFromBottom;

				} else if(boundary[curIndexFromBottom] == 2) {
					
					int index4Equiv = getIndex4Equiv(boundary, curIndexFromBottom);

					int recursion[] = getMinDistRecursiveBetween4and2(boundary, false, index4Equiv, curIndexFromBottom);
					
					curMinSquaresCount += recursion[0];
					
					curMinSquaresCount += prevStartIndexBottom - curIndexFromBottom;
					if( ! curPrevStartIndexUsedFromBottom) {
						curMinSquaresCount++;
					}

					if(recursion[1] == 1) {
						curPrevStartIndexUsedFromBottom = true;
					}
					prevStartIndexBottom = index4Equiv;
					
				} else {
					System.out.println("ERROR: something went terribly wrong... Unexpected boundary number 1");
					System.exit(1);
				}
				

				curIndexFromBottom--;
				
			}
			//End copy/pastish code, but from bottom
			
			
			//Update the minimum squares required:
			if(curMinSquaresCount < ret) {
				ret = curMinSquaresCount;
				
				if(numAttachToTop > 0 || comingFromTop) {
					retComingFromTop = 1;
				} else {
					retComingFromTop = 0;
				}

				if(numAttachToBottom > 0 || comingFromBottom) {
					retComingFromBottom = 1;
				} else {
					retComingFromBottom= 0;
				}
			} else if(curMinSquaresCount == ret) {
				
				//TODO: this is broken!
				//TODO: What if we have the choice of going from top or from bottom.
				//In that case, we should be guided to pick one.
				
				
				//Check if we could also attach to top/bottom with 0 cost:
				if(numAttachToTop > 0 || comingFromTop || retComingFromTop == 1) {
					retComingFromTop = 1;
				} else {
					retComingFromTop = 0;
				}

				if(numAttachToBottom > 0 || comingFromBottom || retComingFromBottom == 1) {
					retComingFromBottom = 1;
				} else {
					retComingFromBottom= 0;
				}

			}
			
		}
		
		return new int[] {ret, retComingFromTop, retComingFromBottom};
	}
	
	public static int getNumSectionsToAttachTo(int boundary[], int index4or3, int index3or2) {
		
		int ret = 0;
		
		int num4sNested = 0;
		int curIndex = index4or3 + 1;

		for(; curIndex<boundary.length; curIndex++) {

			
			if(boundary[curIndex] == 4) {
				if(num4sNested == 0) {
					ret++;
				}
				num4sNested++;

			} else if(boundary[curIndex] == 2 || boundary[curIndex] == 3) {
				num4sNested--;
				if(num4sNested == -1) {
					break;
				}
			} else if(boundary[curIndex] == 1 && num4sNested == 0) {
				ret++;
			}
			
			
		}
		
		return ret;
	}
	
	

	public static int getIndex2Equiv(int boundary[], int index4) {

		int curIndex = index4 + 1;
		
		int num4sNested = 1;
		for(; curIndex<boundary.length; curIndex++) {

			if(boundary[curIndex] == 4) {
				num4sNested++;

			} else if(boundary[curIndex] == 2) {
				num4sNested--;
				
				if(num4sNested == 0) {
					break;
				}
			}
		}
		
		return curIndex;
	}
	
	public static int getIndex4Equiv(int boundary[], int index2) {

		int curIndex = index2 - 1;
		
		int num2sNested = 1;

		for(; curIndex>=0; curIndex--) {

			if(boundary[curIndex] == 2) {
				num2sNested++;
				
			} else if(boundary[curIndex] == 4) {
				num2sNested--;
				
				if(num2sNested == 0) {
					break;
				}
			}
		}
		
		return curIndex;
	}
	
	
}

package optimized2;

public class RecursiveMinSquaresNeedToAttach {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
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


		System.out.println("Done test");
		
	}

	
	public static final int RET_INDEX = 0;
	public static final int TOP_USED_INDEX = 1;
	public static final int BOTTOM_INDEX_USED = 2;
	

	public static int getMinDistRecursive(int boundary[]) {
		
		//From the top:
		
		//Assume that the 1st square is already setup to attach to the others on the boundary by setting this to -1:
		int ret = -1;

		int firstIndex = -1;
		int prevIndex = -1;
		
		int curIndex = 0;
		do {
			
			for(; curIndex<boundary.length; curIndex++) {
				if(boundary[curIndex] == 4 ||  boundary[curIndex] == 1) {
					
					break;
				}
			}
			
			if(curIndex < boundary.length) {
				if(firstIndex == -1) {
					firstIndex = curIndex;
				}
				
				if(prevIndex != -1) {
					ret += curIndex - prevIndex + 1;
				}
				
				prevIndex = curIndex;
				
				if(boundary[firstIndex] == 4) {

					
					int index4 = curIndex;
					curIndex = curIndex + 1;
					
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
					
					int index2 = curIndex;
					
					int tmp[] = getMinDistRecursiveBetween4and2(boundary, true, index4, index2);
					
					ret+=tmp[RET_INDEX];
					//Check for bottom bonus:
					if(tmp[BOTTOM_INDEX_USED] == 1) {
						ret--;
					}
							 
					//TODO: Get next 2 index
					prevIndex = curIndex;
				}
				
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
			
				if(curNumLevelsDeep == 0) {
					if(boundary[i] == 4) {
						foundInBetween = true;
						curNumLevelsDeep++;
						
					} else if(boundary[i] == 1) {
						foundInBetween = true;
					
					} else if(boundary[i] == 3 || boundary[i] == 2) {
						
						if(foundInBetween) {
							
							int tmp[] = getNumBetween33(boundary, curComingFromTop, false, prevIndex, i);
							
							prevIndex= i;
							curComingFromTop = tmp[BOTTOM_INDEX_USED] == 1;
							ret[RET_INDEX] += tmp[0];
							
						}
						
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
		
		return ret;
	}
	
	
	private static int[] getNumBetween33(int boundary[], boolean comingFromTop, boolean comingFromBottom, int index4or3, int index3or2) {
		
		int numSectionsToAttachTo = getNumSectionssToAttachTo(boundary, index4or3, index3or2);
		
		if(numSectionsToAttachTo == 0) {
			System.out.println("ERROR: called getNumBetween33 even thougj there's nothing to attach to");
			System.exit(1);
			return new int[] {0, 0, 0};
			
		}
		//TODO: Actually implement this function.
		return new int[] {0, 0, 0};
	}
	
	public static int getNumSectionssToAttachTo(int boundary[], int index4or3, int index3or2) {
		
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
	
}
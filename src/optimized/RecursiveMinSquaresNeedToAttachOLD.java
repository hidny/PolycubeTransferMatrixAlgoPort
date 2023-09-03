package optimized;

public class RecursiveMinSquaresNeedToAttachOLD {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/*//Expect: 0
		int oneOne[] = new int[] {0,0,0,0,0,1,0};
		

		if(getMinDistRecursive(oneOne) != 0) {
			System.out.println("Test oneOne failed! " + getMinDistRecursive(oneOne));
		}
		

		//Expect: 0
		int allZeros[] = new int[] {0,0,0,0,0,0,0};


		if(getMinDistRecursive(allZeros) != 0) {
			System.out.println("Test oneOne failed! " + getMinDistRecursive(allZeros));
		}

		//Expect: 2
		int twoOnes[] = new int[] {0,0,1,0,1,0,0};
		

		if(getMinDistRecursive(twoOnes) != 0) {
			System.out.println("Test twoOnes failed! " + getMinDistRecursive(twoOnes));
		}
		

		//Expect: 1
		int twoOnesadjacent[] = new int[] {0,0,1,1,0,0,0};
		
		if(getMinDistRecursive(twoOnesadjacent) != 0) {
			System.out.println("Test twoOnesadjacent failed! " + getMinDistRecursive(twoOnesadjacent));
		}*/
		
		//Expect: 2
		int basic42Test[] = new int[] {0,1,0,4,0,0,2};
		
		if(getMinDistRecursive(basic42Test) != 2) {
			System.out.println("Test basic42Test failed! " + getMinDistRecursive(basic42Test));
		}

		System.out.println("Done test");
		
	}

	
	private static int tmp[] = new int[2];

	public static int getMinDistRecursive(int boundary[]) {
		
		//From top
		
		int retTop = -1;

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
					//TODO: the +1 is contested here...
					retTop += curIndex - prevIndex + 1;
				}
				
				prevIndex = curIndex;
				
				if(boundary[firstIndex] == 4) {
					// TODO: do recursion
					
					//END TODO
					//TODO: return: has bonus on bottom!
					//TODO: return an array

					curIndex = curIndex + 1;
					
					int indexAfter4 = curIndex;
					
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
					
					int indexBefore2 = curIndex--;
					
					tmp = getMinDistRecursiveBetween4and2(boundary, true, indexAfter4, indexBefore2);
					
					retTop+=tmp[0];
					//Check for bottom bonus:
					if(tmp[1] == 1) {
						retTop--;
					}
							 
					//TODO: Get next 2 index
					prevIndex = curIndex;
				}
				
				curIndex++;
			}
		} while(curIndex < boundary.length);
		
		
		//From bottom
		int retBottom = -1;
		//TODO:
		retBottom = 999;
		
		//END TODO
		
		return Math.max(0,  Math.min(retTop, retBottom));
	}
	
	
	private static int[] getMinDistRecursiveBetween4and2(int boundary[], boolean comingFromTop, int indexAfter4, int indexBefore2) {
		
		boolean useTopBonus = false;
		boolean useBottomBonus = false;
		if(comingFromTop) {
			useTopBonus = comingFromTop;
		} else {
			useBottomBonus = !comingFromTop;
		}
		
		//TODO: get num
		int numItemsToAttachTo = getNumSectionssToAttachTo(boundary, indexAfter4, indexBefore2);
		
		int ret = 0;
		
		for(int attachFromTop=0; attachFromTop<=numItemsToAttachTo; attachFromTop++) {
			
			int curRet = 0;
			//TODO: attach from top by attachFromTop
			
			for(int j=0; j<attachFromTop; j++) {
				
			}
			

			//TODO: attach from bottom by attachFromTop
			int attachFromBottom= numItemsToAttachTo - attachFromTop;
			
			for(int j=0; j<attachFromBottom; j++) {
				
			}
			
			if(attachFromTop == 0) {
				//TODO:
				ret = curRet;
			} else {
				
				//TODO: check for bottom bonus

				//The last attachment has to attach to the 2...
				//if(curRet <= ret) {
				//	tmp[1] = 1;
				//}
				
				
				ret = Math.min(curRet, ret);
			}
		}
		
		tmp[0] = ret;
		tmp[1] = 0;
		
		return tmp;
	}
	
	public static int getNumSectionssToAttachTo(int boundary[], int indexAfter4, int indexBefore2) {
		
		int ret = 0;
		
		int num4sNested = 0;
		int curIndex = indexAfter4;

		for(; curIndex<boundary.length; curIndex++) {

			
			if(boundary[curIndex] == 4) {
				if(num4sNested == 0) {
					ret++;
				}
				num4sNested++;

			} else if(boundary[curIndex] == 2) {
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

package optimized2;

public class PartialGen3 {

	public long numAnimals[];
	
	/*
	 * "Firstly, for each configuration we keep track of the current minimum number of
occupied sites Ncur which have been inserted to the left of the intersection in order to build
up that particular configuration."
I don't know why it has to be inserted to the left of the intersection...

I'll try inserted to the left or above and see what happens.
Update: I got away with it!

	 */
	public int minSquares;
	public int maxSquares;

	PartialGen3() {
		this(0, 0);
	}

	PartialGen3(int minSquares, int maxSquares) {
		this.numAnimals = new long[maxSquares - minSquares + 1];
		
		//I don't think it's needed, but whatever:
		for(int i=0; i<this.numAnimals.length; i++) {
			this.numAnimals[i] = 0;
		}
		
		this.minSquares = minSquares;
		this.maxSquares = maxSquares;
	}
	
	PartialGen3(long ret[], int minSquares, int maxSquares) {
		
		this.numAnimals = ret;
		this.minSquares = minSquares;
		this.maxSquares = maxSquares;
	}
	
	public static PartialGen3 hardCopy(PartialGen3 pg) {
		long ret[] = new long[pg.numAnimals.length];
		for(int i=0; i<pg.numAnimals.length; i++) {
			ret[i] = pg.numAnimals[i];
		}
		
		return new PartialGen3(ret, pg.minSquares, pg.maxSquares);
	}
	
	public static PartialGen3 hardCopyMerge(PartialGen3 pg, PartialGen3 pg2) {
		
		int retMin = Math.min(pg.minSquares, pg2.minSquares);
		int retMax = Math.max(pg.maxSquares, pg2.maxSquares);
		
		long ret[] = new long[retMax - retMin + 1];
		
		for(int i=retMin; i<=retMax; i++) {
			
			ret[i - retMin] = 0;
			
			if(i >= pg.minSquares && i <= pg.maxSquares) {
				ret[i - retMin] += pg.numAnimals[i - pg.minSquares];
			}

			if(i >= pg2.minSquares && i <= pg2.maxSquares) {
				ret[i - retMin] += pg2.numAnimals[i - pg2.minSquares];
			}
		}
		
		return new PartialGen3(ret, retMin, retMax);
		
	}
	
	public static PartialGen3 hardCopyAdd1SquareThough(PartialGen3 pg, int maxForNumSquaresTarget) {

		int retMin = pg.minSquares + 1;
		int retMax = Math.min(pg.maxSquares + 1, maxForNumSquaresTarget);
		

		if(retMin > retMax) {
			System.out.println("ERROR: partialGen3's hardCopyAdd1SquareThough has retMin > retMax");
			System.exit(1);
		}
		
		long ret[] = new long[retMax - retMin + 1];
		
		for(int i=0; i<ret.length; i++) {
			ret[i] = pg.numAnimals[i];
		}
		
		
		return new PartialGen3(ret, retMin, retMax);
	}
	
	public void printEnumerationsForBoundary(int numSquares) {
		
		for(int i=0; i<numSquares + 1; i++) {
			
			long cur = 0;
			
			if(i >= this.minSquares && i <= this.maxSquares) {
				cur = this.numAnimals[i - this.minSquares];
			}
			
			System.out.print(cur + ", ");

		}
		System.out.println();
		
	}
	
	public long getEnuration(int numSquares) {
		long ret = 0;
		
		if(numSquares >= this.minSquares && numSquares <= this.maxSquares) {
			ret = this.numAnimals[numSquares - this.minSquares];
		}
		
		return ret;
	}
}

package optimized;

public class PartialGen2 {

	public long numAnimals[];
	
	/*
	 * "Firstly, for each configuration we keep track of the current minimum number of
occupied sites Ncur which have been inserted to the left of the intersection in order to build
up that particular configuration."
I don't know why it has to be inserted to the left of the intersection...

I'll try inserted to the left or above and see what happens.
It looks like I got away with it!

	 */
	public int nCur;
	
	PartialGen2(int maxNumAnimals) {
		this.numAnimals = new long[maxNumAnimals];
		
		//I don't think it's needed, but whatever:
		for(int i=0; i<this.numAnimals.length; i++) {
			this.numAnimals[i] = 0;
		}
		
		this.nCur = 0;
	}
	
	PartialGen2(long ret[], int nCur) {
		
		this.numAnimals = ret;
		this.nCur = nCur;
	}
	
	public static PartialGen2 hardCopy(PartialGen2 pg) {
		long ret[] = new long[pg.numAnimals.length];
		for(int i=0; i<pg.numAnimals.length; i++) {
			ret[i] = pg.numAnimals[i];
		}
		
		return new PartialGen2(ret, pg.nCur);
	}
	
	public static PartialGen2 hardCopyMerge(PartialGen2 pg, PartialGen2 pg2) {
		long ret[] = new long[pg.numAnimals.length];
		
		if(pg.numAnimals.length != pg2.numAnimals.length) {
			System.out.println("Doh! Array lengths are not the same!");
			System.exit(1);
		}
		
		for(int i=0; i<pg.numAnimals.length; i++) {
			ret[i] = pg.numAnimals[i] + pg2.numAnimals[i];
		}
		
		return new PartialGen2(ret, Math.min(pg.nCur, pg2.nCur));
		
	}
	
	public static PartialGen2 hardCopyAdd1SquareThough(PartialGen2 pg) {
		long ret[] = new long[pg.numAnimals.length];
		
		ret[0] = 0L;
		
		for(int i=1; i<pg.numAnimals.length; i++) {
			ret[i] = pg.numAnimals[i - 1];
		}
		
		return new PartialGen2(ret, pg.nCur + 1);
	}
}

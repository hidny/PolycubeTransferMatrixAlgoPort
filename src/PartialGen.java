
public class PartialGen {

	public long numAnimals[];
	
	PartialGen(int maxNumAnimals) {
		this.numAnimals = new long[maxNumAnimals];
		
		//I don't think it's needed, but whatever:
		for(int i=0; i<this.numAnimals.length; i++) {
			this.numAnimals[i] = 0;
		}
	}
	
	PartialGen(long ret[]) {
		
		this.numAnimals = ret;
	}
	
	public static PartialGen hardCopy(PartialGen pg) {
		long ret[] = new long[pg.numAnimals.length];
		for(int i=0; i<pg.numAnimals.length; i++) {
			ret[i] = pg.numAnimals[i];
		}
		
		return new PartialGen(ret);
	}
	
	public static PartialGen hardCopyMerge(PartialGen pg, PartialGen pg2) {
		long ret[] = new long[pg.numAnimals.length];
		
		if(pg.numAnimals.length != pg2.numAnimals.length) {
			System.out.println("Doh! Array lengths are not the same!");
			System.exit(1);
		}
		
		for(int i=0; i<pg.numAnimals.length; i++) {
			ret[i] = pg.numAnimals[i] + pg2.numAnimals[i];
		}
		
		return new PartialGen(ret);
		
	}
	
	public static PartialGen hardCopyAdd1SquareThough(PartialGen pg) {
		long ret[] = new long[pg.numAnimals.length];
		
		ret[0] = 0L;
		
		for(int i=1; i<pg.numAnimals.length; i++) {
			ret[i] = pg.numAnimals[i - 1];
		}
		
		return new PartialGen(ret);
	}
}

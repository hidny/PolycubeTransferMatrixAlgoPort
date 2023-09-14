package optimized4LONG;

public class CRTLongUtilFunctions {


	private static short MAX_NUM_RESIDUES = 10;
	
	//private static short INIT_MAX_PRIME = 100;
	public static short INIT_MAX_PRIME = (short)Math.pow(2, 15);
	
	//TODO: make private:
	public static short primes[];
	public static long primeslong[];

	private static long curProdPrimes[] = new long[MAX_NUM_RESIDUES];
	private static long curProdPrimesDiv2[] = new long[MAX_NUM_RESIDUES];
	
	private static long inverses[] = new long[MAX_NUM_RESIDUES];
	
	
	static {
		initialize();
	}

	public static void initialize() {
		primes = new short[MAX_NUM_RESIDUES];
		primeslong = new long[MAX_NUM_RESIDUES];
		
		short curMaxPrime = INIT_MAX_PRIME;
		for(int i=0; i<primes.length; i++) {
			primes[i] = gethighestPrimeUnderN(curMaxPrime);
			primeslong[i] = primes[i];
			curMaxPrime = primes[i];
		}
		
		for(int i=0; i<primes.length; i++) {
			curProdPrimes[i] = 1L;
			inverses[i] = 0L;
		}

		for(int i=0; i<primes.length; i++) {
			
			if(i == 0) {
				curProdPrimes[i] = primes[i];
			} else {
				curProdPrimes[i] = curProdPrimes[i-1] * primes[i];
			}
			
			if(i < primes.length - 1) {
				inverses[i] = getInverse(curProdPrimes[i], primes[i+1]);
			}
			
		}
		
		for(int i=0; i<primes.length; i++) {
			curProdPrimesDiv2[i] = curProdPrimes[i] / 2;
		}
	}

	public short[] addNewResidueToDataStructIfApplicable(short storedResidues[]) {
		
		return addNewResidueToDataStructIfApplicableInner(storedResidues,
				CRTLongUtilFunctions.primes,
				CRTLongUtilFunctions.curProdPrimes,
				CRTLongUtilFunctions.inverses);
	}

	
	//TODO: test that this works with more primes
	public static long deriveTotalBasedOnModResidues(short storedResidues[]) {
		

		return deriveTotalBasedOnModResiduesInner(storedResidues, 
				CRTLongUtilFunctions.primes,
				CRTLongUtilFunctions.curProdPrimes,
				CRTLongUtilFunctions.inverses);
	}
	
	public static short[] convertLongToResidues(long bigLong, int numResidues) {
		short ret[] = new short[numResidues];

		for(int i=0; i<ret.length; i++) {
			ret[i] = (short) (bigLong % primeslong[i]);
		}
		
		return ret;
	}
	
	public static boolean shouldAddNewResidueToDataStruct(short storedResidues[]) {
		return shouldAddNewResidueToDataStruct(storedResidues, primes, curProdPrimes, inverses);
	}
	
	
	public static boolean shouldAddNewResidueToDataStruct(long num, int curNumResidues) {
		return num > curProdPrimesDiv2[curNumResidues - 1];
	}

	private short[] addNewResidueToDataStructIfApplicableInner(short storedResidues[], short primes[], long curProdPrimes[], long inverses[]) {
		
		if( shouldAddNewResidueToDataStruct(storedResidues, primes, curProdPrimes, inverses)) {
			return addNewResidueToNumber(storedResidues, primes, curProdPrimes, inverses);
			
		} else {
			return storedResidues;
		}
	}
	
	private static boolean shouldAddNewResidueToDataStruct(short storedResidues[], short primes[], long curProdPrimes[], long inverses[]) {
		
		return deriveTotalBasedOnModResiduesInner(storedResidues, primes, curProdPrimes, inverses)
				> curProdPrimesDiv2[storedResidues.length - 1];
	}
	
	private static short[] addNewResidueToNumber(short storedMods[], short primes[], long curProdPrimes[], long inverses[]) {
		
		short ret[] = new short[storedMods.length + 1];
		
		for(int i=0; i<storedMods.length; i++) {
			ret[i] = storedMods[i];
		}
		
		int newPrimeIndex = ret.length - 1;
		
		ret[newPrimeIndex] = (short)(deriveTotalBasedOnModResiduesInner(storedMods, primes, curProdPrimes, inverses) % primes[newPrimeIndex]);
		
		
		return ret;
	}


	private static long deriveTotalBasedOnModResiduesInner(short storedResidues[], short primes[], long curProdPrimes[], long inverses[]) {
		

		long curRet = storedResidues[0];
		
		
		for(int numStoredModsUsed = 1; numStoredModsUsed < storedResidues.length; numStoredModsUsed++) {
			
			long nextStoredMod = storedResidues[numStoredModsUsed]; 
			
			long k = ((nextStoredMod - curRet) * inverses[numStoredModsUsed - 1]) % primes[numStoredModsUsed];

			if(k<0) {
				k+=primes[numStoredModsUsed];
			}
			
			curRet += k * curProdPrimes[numStoredModsUsed - 1];
		
		}
		
		return  curRet;
	}

	
	private static boolean isPrime(int n) {
		
		if(n<=1) {
			return false;
		}
		
		int sqrt = (int)Math.sqrt(n);
		
		for(int i=2; i<=sqrt; i++) {
			if(n % i == 0) {
				return false;
			}
		}
		
		return true;
	}
	
	private static short gethighestPrimeUnderN(short n) {
		
		
		for(short j=(short)(n-1); j>1; j--) {
			
			short sqrt = (short)Math.sqrt(n);
			
			boolean isStillPrime = true;
			
			for(short i=2; i<=sqrt; i++) {
				if(j % i == 0) {
					isStillPrime = false;
					break;
				}
			}
			
			if(isStillPrime) {
				return j;
			}
			
		}
		
		return (short)-1;
		
	}
	
	
	private static long getInverse(long n, long coPrimeMod) {
		return getInverse(n, coPrimeMod);
	}
	
	private static long getInverse(long n, int coPrimeMod) {
		for(int j=0; j<coPrimeMod; j++) {
			if( (n * j) % coPrimeMod == 1L) {
				return j;
			}
		}
		
		return 0L;
		
	}
	

	private static void testPrimes() {
		System.out.println("Hello");
		
		int max = (int)Math.pow(2,  15);
		for(int i=0; i<max; i++) {
			if(isPrime(i)) {
				System.out.println(i);
			}
		}
		
		short test = 101;
		
		System.out.println("Under " + test);
		System.out.println(gethighestPrimeUnderN(test));
		
		test = (short)5;

		
		System.out.println("Under " + test);
		System.out.println(gethighestPrimeUnderN(test));
		
		System.out.println("Inverse: " + getInverse(7L, 101));
		
	}
	

	public static void main(String args[]) {
		
		initialize();

		int START_SIZE = 1;
		
		short storedResidues[] = new short[START_SIZE];
		
		for(int i=0; i<storedResidues.length; i++) {
			storedResidues[i] = (short)0;
		}
		
		
		
		int TEST_MULT = 1;
		
		//DONE: increase storage when i is half way there...
		//DONE: make this easier to use for the purpose of the data structure.
		for(int i=0; i<1000000001; i++) {
			
			
			for(int j=0; j<storedResidues.length; j++) {
				storedResidues[j] = (short) ((storedResidues[j] + TEST_MULT) % primes[j]);
			}
			
			long tmp1 = TEST_MULT * (i+1);
			long tmp2 = deriveTotalBasedOnModResiduesInner(storedResidues, primes, curProdPrimes, inverses);
			
			if(shouldAddNewResidueToDataStruct(storedResidues, primes, curProdPrimes, inverses)) {
				//System.exit(1);
				System.out.println("PROMOTE " + storedResidues.length);
				storedResidues = addNewResidueToNumber(storedResidues, primes, curProdPrimes, inverses);
			}
			
			if(tmp1 != tmp2) {
				System.out.println("ERROR: " + tmp1 + " vs " + tmp2);
				
				System.out.println("Primes used:");
				
				long prod = 1L;
				for(int j=0; j<storedResidues.length; j++) {
					System.out.println(primes[j]);
					prod *= primes[j];
				}
				
				System.out.println("Product of the primes: " + prod);
				
				System.exit(1);
			} else {
				//System.out.println(tmp2);
			}
			
		}
		
	}
}

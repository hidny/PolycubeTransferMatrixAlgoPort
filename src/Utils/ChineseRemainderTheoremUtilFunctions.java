package Utils;
import java.math.BigInteger;

public class ChineseRemainderTheoremUtilFunctions {


	private static short MAX_NUM_RESIDUES = 10;
	
	//private static short INIT_MAX_PRIME = 100;
	public static short INIT_MAX_PRIME = (short)Math.pow(2, 15);
	
	//TODO: make private:
	public static short primes[];
	public static BigInteger primesBigInteger[];

	private static BigInteger curProdPrimes[] = new BigInteger[MAX_NUM_RESIDUES];
	private static BigInteger curProdPrimesDiv2[] = new BigInteger[MAX_NUM_RESIDUES];
	
	private static BigInteger inverses[] = new BigInteger[MAX_NUM_RESIDUES];
	
	
	static {
		initialize();
	}

	public static void initialize() {
		primes = new short[MAX_NUM_RESIDUES];
		primesBigInteger = new BigInteger[MAX_NUM_RESIDUES];
		
		short curMaxPrime = INIT_MAX_PRIME;
		for(int i=0; i<primes.length; i++) {
			primes[i] = gethighestPrimeUnderN(curMaxPrime);
			primesBigInteger[i] = new BigInteger("" + primes[i]);
			curMaxPrime = primes[i];
		}
		
		for(int i=0; i<primes.length; i++) {
			curProdPrimes[i] = BigInteger.ONE;
			inverses[i] = BigInteger.ZERO;
		}

		for(int i=0; i<primes.length; i++) {
			
			if(i == 0) {
				curProdPrimes[i] = new BigInteger(primes[i] + "");
			} else {
				curProdPrimes[i] = curProdPrimes[i-1].multiply(new BigInteger(primes[i] + ""));
			}
			
			if(i < primes.length - 1) {
				inverses[i] = getInverse(curProdPrimes[i], primes[i+1]);
			}
			
		}
		
		for(int i=0; i<primes.length; i++) {
			curProdPrimesDiv2[i] = curProdPrimes[i].divide(new BigInteger("2"));
		}
	}

	public short[] addNewResidueToDataStructIfApplicable(short storedResidues[]) {
		
		return addNewResidueToDataStructIfApplicableInner(storedResidues,
				ChineseRemainderTheoremUtilFunctions.primes,
				ChineseRemainderTheoremUtilFunctions.curProdPrimes,
				ChineseRemainderTheoremUtilFunctions.inverses);
	}

	
	//TODO: test that this works with more primes
	public static BigInteger deriveTotalBasedOnModResidues(short storedResidues[]) {
		

		return deriveTotalBasedOnModResiduesInner(storedResidues, 
				ChineseRemainderTheoremUtilFunctions.primes,
				ChineseRemainderTheoremUtilFunctions.curProdPrimes,
				ChineseRemainderTheoremUtilFunctions.inverses);
	}
	
	public static short[] convertBigIntegerToResidues(BigInteger bigInt, int numResidues) {
		short ret[] = new short[numResidues];

		for(int i=0; i<ret.length; i++) {
			ret[i] = (short) bigInt.divideAndRemainder(primesBigInteger[i])[1].shortValue();
		}
		
		return ret;
	}
	
	public static boolean shouldAddNewResidueToDataStruct(short storedResidues[]) {
		return shouldAddNewResidueToDataStruct(storedResidues, primes, curProdPrimes, inverses);
	}
	

	private short[] addNewResidueToDataStructIfApplicableInner(short storedResidues[], short primes[], BigInteger curProdPrimes[], BigInteger inverses[]) {
		
		if( shouldAddNewResidueToDataStruct(storedResidues, primes, curProdPrimes, inverses)) {
			return addNewResidueToNumber(storedResidues, primes, curProdPrimes, inverses);
			
		} else {
			return storedResidues;
		}
	}
	
	private static boolean shouldAddNewResidueToDataStruct(short storedResidues[], short primes[], BigInteger curProdPrimes[], BigInteger inverses[]) {
		
		return deriveTotalBasedOnModResiduesInner(storedResidues, primes, curProdPrimes, inverses)
				.compareTo(curProdPrimesDiv2[storedResidues.length - 1]) > 0;
	}
	
	private static short[] addNewResidueToNumber(short storedMods[], short primes[], BigInteger curProdPrimes[], BigInteger inverses[]) {
		
		short ret[] = new short[storedMods.length + 1];
		
		for(int i=0; i<storedMods.length; i++) {
			ret[i] = storedMods[i];
		}
		
		int newPrimeIndex = ret.length - 1;
		
		ret[newPrimeIndex] = deriveTotalBasedOnModResiduesInner(storedMods, primes, curProdPrimes, inverses)
				.divideAndRemainder(new BigInteger("" + primes[newPrimeIndex]))[1].shortValue();
		
		
		return ret;
	}


	private static BigInteger deriveTotalBasedOnModResiduesInner(short storedResidues[], short primes[], BigInteger curProdPrimes[], BigInteger inverses[]) {
		

		BigInteger curRet = new BigInteger("" + storedResidues[0]);
		
		
		for(int numStoredModsUsed = 1; numStoredModsUsed < storedResidues.length; numStoredModsUsed++) {
			
			BigInteger nextStoredMod = new BigInteger(storedResidues[numStoredModsUsed] + ""); 
			
			BigInteger k = (nextStoredMod.subtract(curRet)).multiply(inverses[numStoredModsUsed - 1]).mod(new BigInteger("" + primes[numStoredModsUsed]));

			curRet = curRet.add(k.multiply(new BigInteger(curProdPrimes[numStoredModsUsed - 1] + "")));
		
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
	
	
	private static BigInteger getInverse(BigInteger n, BigInteger coPrimeMod) {
		return getInverse(n, (int)coPrimeMod.longValue());
	}
	
	private static BigInteger getInverse(BigInteger n, int coPrimeMod) {
		for(int j=0; j<coPrimeMod; j++) {
			if( n.multiply(new BigInteger(j + "")).divideAndRemainder(new BigInteger("" + coPrimeMod))[1].longValue() == 1L) {
				return new BigInteger(j + "");
			}
		}
		
		return BigInteger.ZERO;
		
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
		
		System.out.println("Inverse: " + getInverse(new BigInteger("7"), 101));
		
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
			
			BigInteger tmp1 = new BigInteger((TEST_MULT*(i+1)) + "");
			BigInteger tmp2 = deriveTotalBasedOnModResiduesInner(storedResidues, primes, curProdPrimes, inverses);
			
			if(shouldAddNewResidueToDataStruct(storedResidues, primes, curProdPrimes, inverses)) {
				//System.exit(1);
				System.out.println("PROMOTE " + storedResidues.length);
				storedResidues = addNewResidueToNumber(storedResidues, primes, curProdPrimes, inverses);
			}
			
			if(tmp1.compareTo(tmp2) != 0) {
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

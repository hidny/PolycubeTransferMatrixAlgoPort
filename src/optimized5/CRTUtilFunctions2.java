package optimized5;
import java.math.BigInteger;

public class CRTUtilFunctions2 {

	private static short MAX_NUM_RESIDUES = 10;
	
	//private static short INIT_MAX_PRIME = 100;
	public static short INIT_MAX_PRIME = (short)Math.pow(2, 15);
	
	//TODO: make private:
	public static short primes[];
	public static BigInteger primesBigInteger[];

	private static BigInteger curProdPrimes[] = new BigInteger[MAX_NUM_RESIDUES];
	
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
	}

	public static BigInteger deriveTotalBasedOnModResidues(short storedResidues[]) {
		
		return deriveTotalBasedOnModResiduesInner(storedResidues, 
				CRTUtilFunctions2.primes,
				CRTUtilFunctions2.curProdPrimes,
				CRTUtilFunctions2.inverses);
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
	
	
	private static BigInteger getInverse(BigInteger n, int coPrimeMod) {
		for(int j=0; j<coPrimeMod; j++) {
			if( n.multiply(new BigInteger(j + "")).divideAndRemainder(new BigInteger("" + coPrimeMod))[1].longValue() == 1L) {
				return new BigInteger(j + "");
			}
		}
		
		return BigInteger.ZERO;
		
	}
	
	
}

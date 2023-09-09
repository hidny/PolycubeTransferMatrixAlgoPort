import java.math.BigInteger;

public class PrimeFinder {
	
	public static int MAX_NUM_PRIMES_TO_USE = 10;

	public static void main(String args[]) {
		
		
		short primes[] = new short[MAX_NUM_PRIMES_TO_USE];
		
		//short maxNumPrime = (short)Math.pow(2, 15);
		short maxNumPrime = (short)100;
		short curMaxNumPrimes = maxNumPrime;
		
		for(int i=0; i<primes.length; i++) {
			primes[i] = gethighestPrimeUnderN(curMaxNumPrimes);
			curMaxNumPrimes = primes[i];
		}
		
		
		BigInteger curProdPrimes[] = new BigInteger[MAX_NUM_PRIMES_TO_USE];
		BigInteger inverses[] = new BigInteger[MAX_NUM_PRIMES_TO_USE];
		
		
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
		
		System.out.println("Mod and inverses:");
		for(int i=0; i<primes.length - 1; i++) {
			System.out.println("i = " + i);
			System.out.println(primes[i]);
			System.out.println(curProdPrimes[i]);
			System.out.println(inverses[i]);
			System.out.println("Next prime: " + primes[i+1]);
			System.out.println();
		}
		
		short testStorage[] = new short[2];
		
		for(int i=0; i<2; i++) {
			testStorage[i] = (short)0;
		}
		
		
		
		int MULT = 1;
		
		//TODO: increase storage when i is half way there...
		//TODO: make this easier to use for the purpose of the data structure.
		for(int i=0; i<1000000001; i++) {
			
			
			for(int j=0; j<2; j++) {
				testStorage[j] = (short) ((testStorage[j] + MULT) % primes[j]);
			}
			
			BigInteger tmp1 = new BigInteger((MULT*(i+1)) + "");
			//BigInteger tmp2 = deriveTotalBasedOnMods(testStorage, primes, inverses);
			BigInteger tmp2 = deriveTotalBasedOnMods2(testStorage, primes, curProdPrimes, inverses);
			if(tmp1.compareTo(tmp2) != 0) {
				System.out.println("ERROR: " + tmp1 + " vs " + tmp2);
				System.exit(1);
			} else {
				System.out.println(tmp2);
			}
			
		}
		
		
	}

	//TODO: test that this works with more primes
	public static BigInteger deriveTotalBasedOnMods2(short storedMods[], short primes[], BigInteger curProdPrimes[], BigInteger inverses[]) {
		

		BigInteger curRet = new BigInteger("" + storedMods[0]);
		
		
		for(int numStoredModsUsed = 1; numStoredModsUsed < storedMods.length; numStoredModsUsed++) {
			
			BigInteger nextStoredMod = new BigInteger(storedMods[numStoredModsUsed] + ""); 
			
			BigInteger k = (nextStoredMod.subtract(curRet)).multiply(inverses[numStoredModsUsed - 1]).mod(new BigInteger("" + primes[numStoredModsUsed]));

			curRet = curRet.add(k.multiply(new BigInteger(curProdPrimes[numStoredModsUsed - 1] + "")));
		
		}
		
		return  curRet;
	}
	
	public static void testPrimes() {
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
	
	public static boolean isPrime(int n) {
		
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
	
	public static short gethighestPrimeUnderN(short n) {
		
		
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
	
	
	public static BigInteger getInverse(BigInteger n, BigInteger coPrimeMod) {
		return getInverse(n, (int)coPrimeMod.longValue());
	}
	public static BigInteger getInverse(BigInteger n, int coPrimeMod) {
		
		
		
		for(int j=0; j<coPrimeMod; j++) {
			if( n.multiply(new BigInteger(j + "")).divideAndRemainder(new BigInteger("" + coPrimeMod))[1].longValue() == 1L) {
				return new BigInteger(j + "");
			}
		}
		
		return BigInteger.ZERO;
		
	}
}

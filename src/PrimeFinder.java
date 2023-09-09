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
		
		
		BigInteger curMod[] = new BigInteger[MAX_NUM_PRIMES_TO_USE];
		BigInteger inverses[] = new BigInteger[MAX_NUM_PRIMES_TO_USE];
		
		
		for(int i=0; i<primes.length; i++) {
			curMod[i] = BigInteger.ONE;
			inverses[i] = BigInteger.ZERO;
		}
		for(int i=0; i<primes.length; i++) {
			
			if(i == 0) {
				curMod[i] = new BigInteger(primes[i] + "");
			} else {
				curMod[i] = curMod[i-1].multiply(new BigInteger(primes[i] + ""));
			}
			
			if(i < primes.length - 1) {
				inverses[i] = getInverse(curMod[i], primes[i+1]);
			}
			
		}
		
		System.out.println("Mod and inverses:");
		for(int i=0; i<primes.length - 1; i++) {
			System.out.println(curMod[i]);
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
			BigInteger tmp2 = deriveTotalBasedOnMods(testStorage, primes, inverses);
			
			if(tmp1.compareTo(tmp2) != 0) {
				System.out.println("ERROR: " + tmp1 + " vs " + tmp2);
				System.exit(1);
			} else {
				System.out.println(tmp2);
			}
			
		}
	}
	
	//TODO: make this work with more primes
	public static BigInteger deriveTotalBasedOnMods(short storedMods[], short primes[], BigInteger inverses[]) {
		
		BigInteger k = new BigInteger((storedMods[1] - storedMods[0]) + "").multiply(inverses[0]).mod(new BigInteger("" + primes[1]));

		return k.multiply(new BigInteger(primes[0] + "")).add(new BigInteger(storedMods[0] + ""));
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

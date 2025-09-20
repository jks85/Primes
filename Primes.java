
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.Set;
import java.util.HashSet;
import java.util.AbstractMap.SimpleEntry;

import java.lang.Math;


public class Primes {
    /*
     * Class inspired by Project Euler Problems #4 and #5 which are related to primes.
     * This class provides a number of public methods related to primes including, but
     * not limited to the following:
     * 
     * - check if an integer is prime
     * - find the prime factors of an integer
     * - find the prime factorization of an integer
     * - find the LCM of a list of integers
     */

    
    // map below contains prime factorization of all maps computed by the object
    private Map<Integer, Map<Integer, Integer>> primeFactorizationMap; 

    // constructors
    public Primes(){
        this.primeFactorizationMap = new TreeMap<>();
    }

    public Primes(Map<Integer, Map<Integer, Integer>> primeFactorizationMap){
        this.primeFactorizationMap = primeFactorizationMap;
    }

    // public methods
    public Map<Integer, Map<Integer, Integer>> getPrimesMap(){
        // returns current map of integers and their prime factorizations
        // <number, <prime, degree>>
        return this.primeFactorizationMap;
    }

    public static boolean isPrime(int n){
        // checks if number is prime using trial division of possible prime factors
        if (n == 1){
            return false;
        }
        List<Integer> primesList = new ArrayList<>();
        primesList.add(2);
        int upperBound = (int) Math.sqrt(n);
        List<Integer> possiblePrimeFactors = getPrimesToNum(upperBound);

        for (int prime : possiblePrimeFactors){
            if (n % prime == 0){
                return false;
            }
        }
        return true;
    }

    public List<Integer> getPrimeFactors(int n){
        List<Integer> primeFactors = new ArrayList<>();
        List<Integer> primes = getPrimesToNum(n);
        for (int prime: primes){
            if (n % prime == 0){
                primeFactors.add(prime);
            }
    }
        return primeFactors;
    }

    public void addPrimeToMap(int n){
        // adds map entry of a prime number and it's prime factorization to the main map
        Map.Entry<Integer, Map<Integer, Integer>> mapEntry = getPrimeFactorizationMapEntry(n);
        this.primeFactorizationMap.put(mapEntry.getKey(), mapEntry.getValue());
    }

    public void addPrimeToMap(List<Integer> intList){
        // add list of integers to prime number map
        for (int num : intList){
            this.addPrimeToMap(num);
        }
    }

    public Map<Integer, Integer> getPrimeFactorization(int n){
        // returns a map whose key/val pairs are the <prime, degree> for prime factors of n
        Map<Integer, Integer> primeFactorsMap = new TreeMap<>();

        List<Integer> primeFactors = this.getPrimeFactors(n);
        // get degree of each prime factor
        for (int prime : primeFactors){
            Map.Entry<Integer, Integer> primesMapEntry = getPrimeFactorDegree(n, prime);
            int currentPrime = primesMapEntry.getKey();
            int currentDegree = primesMapEntry.getValue();
            primeFactorsMap.put(currentPrime, currentDegree);
        }

        return primeFactorsMap;
    }

    public Map<Integer, Integer> getPrimeFactorization(int n, boolean addToMainMap){
        // overloaded version of getPrimeFactorization
        // boolean parameter allows user to store the = prime mapping in the main map
        Map<Integer, Integer> primeFactorsMap = new TreeMap<>();

        List<Integer> primeFactors = this.getPrimeFactors(n);
        // get degree of each prime factor
        for (int prime : primeFactors){
            Map.Entry<Integer, Integer> primesMapEntry = getPrimeFactorDegree(n, prime);
            int currentPrime = primesMapEntry.getKey();
            int currentDegree = primesMapEntry.getValue();
            primeFactorsMap.put(currentPrime, currentDegree);
        }

        if (addToMainMap){
            this.addPrimeToMap(n);
        }

        return primeFactorsMap;
    }

     public int getLCM(List<Integer> intList){
        // build primes map from list
        // compute max degree of each prime factor
        // get LCM

        Map<Integer, Map<Integer, Integer>> mainNumMap = this.buildPrimesMapFromList(intList);
        Set<Integer> uniquePrimes = findUniquePrimes(mainNumMap);
        Map<Integer, Integer> primesAndMaxDegrees = findMaxPrimeDegrees(uniquePrimes, mainNumMap);

        int LCM = computeLCMFromPrimeFactorization(primesAndMaxDegrees);
        return LCM;
    }

     public static List<Integer> getPrimesToNum(int num){
        // return list of primes up to and including num
        // identifies prime using wheel division and sieve

        ArrayList<Integer> primesList = new ArrayList<>();
        primesList.add(2);
        int currentVal = 3;

        while (currentVal <= num){
            boolean valIsPrime = true;
            for (int prime: primesList){
                if (currentVal % prime == 0){
                    valIsPrime = false;
                }
            }

            if (valIsPrime){
                primesList.add(currentVal);
            }
            currentVal += 2;
        }
        return primesList;
    }

    // private methods

    private int computeLCMFromPrimeFactorization(Map<Integer, Integer> maxPrimesMap){
        // computes LCM using map containing primes and max degrees
        int product = maxPrimesMap.keySet().stream()
        .map(prime -> {int degree = maxPrimesMap.get(prime);
                        return (int) Math.pow(prime, degree);})
        .reduce(1, (prev, next) -> prev*next);
        return product;
    }
    private Set<Integer> findUniquePrimes(Map<Integer, Map<Integer, Integer>> mainNumMap){
        // return list of unique primes from map of prime factorizations
        Set<Integer> uniquePrimes = new HashSet<Integer>();
        for (Map<Integer, Integer> map : mainNumMap.values()){
            Set<Integer> currentPrimes = map.keySet();
            uniquePrimes.addAll(currentPrimes);
        }

        return uniquePrimes;
    }

    private Map<Integer, Integer> findMaxPrimeDegrees(Set<Integer> uniquePrimes, Map<Integer, Map<Integer, Integer>> mainNumMap){
        // find largest degree of prime factors across map of prime factorizations
        Map<Integer, Integer> primesAndMaxDegrees = new TreeMap<>();

        for (int prime : uniquePrimes){
            int[] primeInfo = getMaxPrimeDegree(prime, mainNumMap);
            int currentPrime = primeInfo[0];
            int currentDegree = primeInfo[1];
            primesAndMaxDegrees.put(currentPrime, currentDegree);
        }
        return primesAndMaxDegrees;
    }

    private Map.Entry<Integer, Integer> getPrimeFactorDegree(int n, int prime){
        // checks the prime factor degree for a prime p for an integer n
        // returns the prime, degree as a map entry 
        
        int dividend = n;
        int degree = 0;
        while(dividend > 1){
            if (dividend % prime == 0){
                degree++;
                dividend /= prime;
            } else{
                break;
            }
        }
        Map.Entry<Integer, Integer> primeMapEntry = new SimpleEntry<Integer, Integer>(prime, degree);
        return primeMapEntry;
    }

    private Map.Entry<Integer, Map<Integer, Integer>> getPrimeFactorizationMapEntry(int n){
        // returns a map entry
        // key is an integer
        // val is a map containing <prime, degree> key/val pairs corresponding to the prime factorization of n
        Map<Integer, Integer> primeFactorsMap = new TreeMap<>();

        List<Integer> primeFactors = this.getPrimeFactors(n);
        // get degree of each prime factor
        for (int prime : primeFactors){
            Map.Entry<Integer, Integer> primesMapEntry = getPrimeFactorDegree(n, prime);
            int currentPrime = primesMapEntry.getKey();
            int currentDegree = primesMapEntry.getValue();
            primeFactorsMap.put(currentPrime, currentDegree);
        }

        Map.Entry<Integer, Map<Integer, Integer>> mainMapEntry = new SimpleEntry<Integer, Map<Integer, Integer>>(n, primeFactorsMap);
        return mainMapEntry;
    }

    private Map<Integer, Map<Integer, Integer>> buildPrimesMapFromList(List<Integer> intList){
        // given a list of integers returns a map of prime factorizations
        // <number, <prime, degree>>

        Map<Integer, Map<Integer, Integer>> primesMap = new TreeMap<>();
        for (int num : intList){
            Map.Entry<Integer, Map<Integer, Integer>> currentMapEntry = this.getPrimeFactorizationMapEntry(num);
            int number = currentMapEntry.getKey();
            Map<Integer, Integer> primeFactorMap = currentMapEntry.getValue();
            primesMap.put(number, primeFactorMap);
        }

        return primesMap;
    }

    private int[] getMaxPrimeDegree(int prime, Map<Integer, Map<Integer, Integer>> primeFactorsMap){
        // returns array containing a prime factor and its highest degree within the map
        // recall that the main key of the prime factors map is an integer.
        // the sub key is a prime factor of that integer
        // the sub value is the degree of that prime within the factorization

        int[] primeDegreeInfo = new int[2];
        primeDegreeInfo[0] = prime;
        int degree = 0;
        Set<Integer> nums = primeFactorsMap.keySet();
        for (int num : nums){
            Map<Integer, Integer> primeDegreeMap = primeFactorsMap.get(num);
            int currentDegree = 0;
            if (primeDegreeMap.keySet().contains(prime)){
                currentDegree = primeDegreeMap.get(prime);
            }
            
            if (currentDegree > degree){
                degree = currentDegree;
            }
        }
        primeDegreeInfo[1] = degree;
        return primeDegreeInfo;
    }

}

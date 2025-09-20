
import java.util.List;
import java.util.ArrayList;

public class Main {
    
    public static void main(String[] args){
        List<Integer> testList = new ArrayList<>();
        Primes primesTester = new Primes();

        testList.add(20);
        testList.add(12);

        // LCM
        System.out.println(primesTester.getLCM(testList));  // LCM(12, 20) = 60

        // Check if numbers are prime
        System.out.println(Primes.isPrime(1)); // false
        System.out.println(Primes.isPrime(9)); // false
        System.out.println(Primes.isPrime(71)); // true

        // List of primes up to a number
        System.out.println(Primes.getPrimesToNum(100));
        // [2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97]


        // Get prime factors
        System.out.println(primesTester.getPrimeFactors(30));   // [2, 3, 5]
        System.out.println(primesTester.getPrimeFactors(124));  // [2, 31]

        // Get prime factorization
        System.out.println(primesTester.getPrimeFactorization(124));
        // {2=2, 31=1}
        // 124 = 2^2 + 31^4

        
     }
}

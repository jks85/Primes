# Primes

A Java class providing operations relating to prime numbers.

## Description

This class inspired by Project Euler problems #4 and #5, which are related to primes.
The class provides a number of public methods related to primes including, but not 
limited to the following:

1. Check if an integer is prime
2. Find the prime factors of an integer
3. Find the prime factorization of an integer
4. Find the least common multiple (LCM) of a list of integers

*Note: The class supports `int` or `Integer` objects. Large integers such as `long` instances are not supported.

## Code Design

### Methods of Interest

- *static isPrime(int n)* 
    - indicates if an integer is prime

- *static getPrimesToNum(int num)* 
    - provides a list of integers up to and including num

- *getPrimeFactors(int n)* 
    - returns list of integers (prime factors)

- *getPrimeFactorization(int n)*
    - returns a map whose keys are prime factors and whose values are the degree of said factor
    - note that this type of map is a value of the class data field `primeFactorizationMap`
    - this method has an overloaded version that allows the user to add the returned map to the `primeFactorizationMap` 

- getLCM(List<Integer> intList)
    - computes the LCM of a list of integers


Overall the class contains two (2) constructors, two (2) static public methods, eleven (11) public instance methods, and seven (7) private instance methods.

### Data Structures

The class contains one data field `primeFactorizationMap`, a Map containing information about an
integer's prime factorization. The map structure is: 

`Map<Integer, Map<Integer, Map>>`. Maps are implemented using the `TreeMap` class.

The primary key is an integer corresponding to the number in question, while the primary value is
a map containing the prime factors of a number (sub-key) and the corresponding degrees of factors (sub-value). 

For example 300 can be factored as 2^2 * 3^1 * 5^2. Within the map this is stored as
`<300, <<2,2>, <3,1>, <5,2>>`


### Algorithms

`Wheel Factorization` was used to for identifying prime factors.


### Possible Future Updates
I may create a custom class `PrimeBundle` to store information. An instance would contain an integer and its prime factorization.
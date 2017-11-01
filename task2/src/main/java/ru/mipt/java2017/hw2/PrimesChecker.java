package ru.mipt.java2017.hw2;

import static java.lang.Math.ceil;
import static java.lang.Math.sqrt;

import java.math.BigInteger;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PrimesChecker {

  private ExecutorService executorService;

  public PrimesChecker(int cores) {
    executorService = Executors.newFixedThreadPool(cores);
  }

  private boolean isPrimeNaive(long number) {
    if (number == 2) {
      return true;
    }
    if (number % 2 == 0) {
      return false;
    }
    long top = (long) ceil(sqrt(number));
    for (long i = 3; i <= top; i += 2) {
      if (number % i == 0) {
        return false;
      }
    }
    return true;
  }

  public boolean isPrime(long number) {
    return BigInteger.valueOf(number).isProbablePrime(200);// && isPrimeNaive(number);
  }

  public Future<Boolean> isPrimePromise(final long number) {
    return executorService.submit(new Callable<Boolean>() {
      @Override
      public Boolean call() throws Exception {
        return isPrimeNaive(number);
      }
    });
  }


}

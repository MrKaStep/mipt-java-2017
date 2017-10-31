package ru.mipt.java2017.hw2;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Client class to calculate sum of primes in given range
 */
public class Client {

  private static final Logger logger = LoggerFactory.getLogger("Client");

  private final Dispatcher dispatcher;

  public Client(List<Address> addresses) {
    dispatcher = new Dispatcher(addresses);
  }

  static class Address {
    final String host;
    final int port;

    public Address(String host, int port) {
      this.host = host;
      this.port = port;
    }
  }

  public void shutdown() throws InterruptedException {
    dispatcher.shutdown();
  }

  private void sendRequest(long start, long end) {

    Range range = Range.newBuilder().setStart(start).setEnd(end).build();
    dispatcher.sendRequest(range);
  }

  private int getRequestsCount(long start, long end, int serversCount) {
    double t = Math.ceil(Math.sqrt(start)) / 1e5;
    int suggestedSize = (int)Math.min(Math.min(1000, end - start), (double)(end - start) / t);
    logger.debug("suggestedSize: " + suggestedSize);
    if(suggestedSize <= 1)
      return (int)(end - start);
    return (int)(end - start) / Math.min(suggestedSize, (int)(end - start) / serversCount / 2);
  }

  private long getIthPoint(long start, long end, int i, int N) {
    return start + (end - start) / N * i + (Math.min(i, (end - start) % N));
  }

  /**
   * Acquires result for given range
   * @param start - start of the range (inclusive)
   * @param end - end of the range (exclusive)
   * @return Sum of primes on given range
   * @throws InterruptedException
   */

  private long getResult(long start, long end) throws InterruptedException {
    int requestsCount = getRequestsCount(start, end, dispatcher.getServersCount());
    final BlockingQueue<Long> results = new ArrayBlockingQueue<Long>(requestsCount);
    final CountDownLatch latch = new CountDownLatch(requestsCount);

    dispatcher.setResults(results);
    dispatcher.setLatch(latch);

    logger.info("Passing requests to dispatcher ...");
    for (int i = 0; i < requestsCount; i++) {
      long currentStart = getIthPoint(start, end, i, requestsCount);
      long currentEnd = getIthPoint(start, end, i + 1, requestsCount);
      sendRequest(currentStart, currentEnd);
    }
    logger.info("All " + requestsCount + " requests passed");
    logger.info("Waiting for results...");
    latch.await();
    logger.info("All results acquired");

    long result = 0;
    for(long b : results) {
      result += b;
    }

    return result;
  }

  /**
   * Prints sum of prime numbers on given range
   * @param args - Range to calculate sum on. After - host-port pairs of servers
   * @throws InterruptedException
   */

  public static void main(String[] args) throws InterruptedException {
    if(args.length < 4 || args.length % 2 != 0) {
      System.err.println("Usage: <start> <end> <host1> <port1> [<host2> <port2> ...]");
      return;
    }

    int servers = (args.length - 2) / 2;
    long start = Long.valueOf(args[0]);
    long end = Long.valueOf(args[1]) + 1;
    List<Address> addresses = new ArrayList<>(servers);
    for (int i = 0; i < servers; i++) {
      addresses.add(new Address(args[2 * i + 2], Integer.valueOf(args[2 * i + 3])));
    }

    Client client = new Client(addresses);
    System.out.println(client.getResult(start, end));
  }

}
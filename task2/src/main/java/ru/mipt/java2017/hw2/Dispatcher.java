package ru.mipt.java2017.hw2;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mipt.java2017.hw2.Client.Address;


/**
 * Class for handling client-server communication. Main purpose - distributing requests between
 * servers and returning results to the client
 */
class Dispatcher {

  private static Logger logger = LoggerFactory.getLogger("Dispatcher");

  private final List<ManagedChannel> channels;
  private final List<PrimesSumCalculatorGrpc.PrimesSumCalculatorStub> asyncStubs;

  private final BlockingQueue<Integer> servers = new LinkedBlockingQueue<Integer>();
  private final BlockingQueue<Range> queries = new LinkedBlockingQueue<>();

  private final ReentrantLock pushLock = new ReentrantLock();

  private BlockingQueue<Long> results;
  private CountDownLatch latch;

  /**
   * Creates new Dispatcher entry
   *
   * @param addresses - hosts addresses to connect to
   */

  Dispatcher(List<Address> addresses) {
    int serversCount = addresses.size();

    channels = new ArrayList<>(serversCount);
    for (Address address : addresses) {
      channels.add(ManagedChannelBuilder.forAddress(address.host, address.port)
          .usePlaintext(true)
          .build()
      );
      logger.debug("Added channel for address {}:{}", address.host, address.port);
    }
    asyncStubs = new ArrayList<>(channels.size());
    for (int i = 0; i < serversCount; ++i) {
      asyncStubs.add(PrimesSumCalculatorGrpc.newStub(channels.get(i)));
      servers.add(i);
    }
  }

  /**
   * Returns total amount of servers
   *
   * @return - servers available //TODO
   */
  int getServersCount() {
    return channels.size();
  }

  /**
   * Attempts to push all pending requests to servers
   *
   * In case of failed request (server failure) put query back to queue without returning server to
   * queue
   *
   * After successful response, push server back to queue
   */
  private void pushRequests() {
    pushLock.lock();
    logger.debug("Entering pushRequests");
    while (!queries.isEmpty() && !servers.isEmpty()) {
      final int serverIndex = servers.poll();
      final Range query = queries.poll();
      logger.debug("Sending request [{},{}) to server {}",
          query.getStart(),
          query.getEnd(),
          serverIndex + 1);
      asyncStubs.get(serverIndex).calculateSum(query,
          new StreamObserver<PrimesSum>() {

            @Override
            public void onNext(PrimesSum response) {
              results.add(response.getValue());
            }

            @Override
            public void onError(Throwable t) {
              logger.warn("Server {} failed: {}", serverIndex + 1, Status.fromThrowable(t));
              queries.add(query);
              pushRequests();
            }

            @Override
            public void onCompleted() {
              latch.countDown();
              servers.add(serverIndex);
              logger.debug("Server {} free", serverIndex + 1);
              pushRequests();
            }
          });
    }
    logger.debug("Exiting pushRequests");
    pushLock.unlock();
  }

  /**
   * Add new request to queue
   *
   * @param range - range queried
   */

  void sendRequest(Range range) {
    logger.debug("Recieved request [{},{})", range.getStart(), range.getEnd());
    queries.add(range);
    pushRequests();
  }

  void shutdown() throws InterruptedException {
    for (int i = 0; i < channels.size(); ++i) {
      channels.get(i).shutdown().awaitTermination(1, TimeUnit.SECONDS);
    }
  }

  public void setResults(BlockingQueue<Long> results) {
    this.results = results;
  }

  public void setLatch(CountDownLatch latch) {
    this.latch = latch;
  }

  /**
   * Class representing a single query to server
   */
}

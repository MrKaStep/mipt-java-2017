package ru.mipt.java2017.hw2;


import static java.lang.System.exit;

import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Server providing method to calculate sum of primes on given range
 */

public class Server {

  private static final Logger logger = LoggerFactory.getLogger("Server");

  private io.grpc.Server server;

  private static PrimesChecker primesChecker;

  private void start(int port, int cores) throws IOException {
    primesChecker = new PrimesChecker(cores);

    logger.info("Primes checker on {} cores initialized", cores);

    server = ServerBuilder.forPort(port)
        .addService(new PrimesSumCalculator())
        .build()
        .start();

    logger.info("Server started, listening on port {}", port);

    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        System.err.println("*** shutting down gRPC server since JVM is shutting down");
        Server.this.stop();
        System.err.println("*** server shut down");
      }
    });


  }

  private void stop() {
    if (server != null) {
      server.shutdown();
    }
  }

  private void blockUntilShutdown() throws InterruptedException {
    if (server != null) {
      server.awaitTermination();
    }
  }

  /**
   * Start the server
   *
   * @param args - number of available cores and port to listen to
   */

  public static void main(String[] args) throws IOException, InterruptedException {
    if (args.length != 2) {
      System.err.println("Usage: server <# of cores> <port>");
      exit(-1);
    }

    int cores, port;
    cores = Integer.valueOf(args[0]);
    port = Integer.valueOf(args[1]);

    Server server = new Server();
    server.start(port, cores);
    server.blockUntilShutdown();
  }

  /**
   * Service implementation
   */

  static class PrimesSumCalculator extends PrimesSumCalculatorGrpc.PrimesSumCalculatorImplBase {

    @Override
    public void calculateSum(Range range, StreamObserver<PrimesSum> responseObserver) {
      long start = range.getStart();
      long end = range.getEnd();

      logger.debug("Got request for range [{},{})", start, end);

      List<Future<Boolean>> promises = new ArrayList<Future<Boolean>>((int) (end - start));
      for (int i = 0; i < end - start; ++i) {
        Future<Boolean> promise = primesChecker.isPrimePromise(start + i);
        promises.add(promise);
      }

      long ans = 0;

      for (int i = 0; i < end - start; ++i) {
        Future<Boolean> promise = promises.get(i);
        boolean prime = false;
        try {
          prime = promise.get();
        } catch (InterruptedException | ExecutionException e) {
          logger.error(e.getMessage());
        }
        logger.debug("{} is " + (prime ? "" : "not ") + "prime", start + i);
        if (prime) {
          ans += start + i;
        }
      }
      logger.debug("Sending result for range [{},{})", start, end);
      responseObserver.onNext(PrimesSum.newBuilder().setValue(ans).build());
      responseObserver.onCompleted();
    }
  }

}

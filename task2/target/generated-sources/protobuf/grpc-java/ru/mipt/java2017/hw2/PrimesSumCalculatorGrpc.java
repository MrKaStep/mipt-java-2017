package ru.mipt.java2017.hw2;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.7.0)",
    comments = "Source: primessum.proto")
public final class PrimesSumCalculatorGrpc {

  private PrimesSumCalculatorGrpc() {}

  public static final String SERVICE_NAME = "ru.mipt.java2017.hw2.PrimesSumCalculator";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<ru.mipt.java2017.hw2.Range,
      ru.mipt.java2017.hw2.PrimesSum> METHOD_CALCULATE_SUM =
      io.grpc.MethodDescriptor.<ru.mipt.java2017.hw2.Range, ru.mipt.java2017.hw2.PrimesSum>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ru.mipt.java2017.hw2.PrimesSumCalculator", "CalculateSum"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              ru.mipt.java2017.hw2.Range.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              ru.mipt.java2017.hw2.PrimesSum.getDefaultInstance()))
          .setSchemaDescriptor(new PrimesSumCalculatorMethodDescriptorSupplier("CalculateSum"))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PrimesSumCalculatorStub newStub(io.grpc.Channel channel) {
    return new PrimesSumCalculatorStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PrimesSumCalculatorBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new PrimesSumCalculatorBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PrimesSumCalculatorFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new PrimesSumCalculatorFutureStub(channel);
  }

  /**
   */
  public static abstract class PrimesSumCalculatorImplBase implements io.grpc.BindableService {

    /**
     */
    public void calculateSum(ru.mipt.java2017.hw2.Range request,
        io.grpc.stub.StreamObserver<ru.mipt.java2017.hw2.PrimesSum> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CALCULATE_SUM, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_CALCULATE_SUM,
            asyncUnaryCall(
              new MethodHandlers<
                ru.mipt.java2017.hw2.Range,
                ru.mipt.java2017.hw2.PrimesSum>(
                  this, METHODID_CALCULATE_SUM)))
          .build();
    }
  }

  /**
   */
  public static final class PrimesSumCalculatorStub extends io.grpc.stub.AbstractStub<PrimesSumCalculatorStub> {
    private PrimesSumCalculatorStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PrimesSumCalculatorStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PrimesSumCalculatorStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PrimesSumCalculatorStub(channel, callOptions);
    }

    /**
     */
    public void calculateSum(ru.mipt.java2017.hw2.Range request,
        io.grpc.stub.StreamObserver<ru.mipt.java2017.hw2.PrimesSum> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CALCULATE_SUM, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class PrimesSumCalculatorBlockingStub extends io.grpc.stub.AbstractStub<PrimesSumCalculatorBlockingStub> {
    private PrimesSumCalculatorBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PrimesSumCalculatorBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PrimesSumCalculatorBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PrimesSumCalculatorBlockingStub(channel, callOptions);
    }

    /**
     */
    public ru.mipt.java2017.hw2.PrimesSum calculateSum(ru.mipt.java2017.hw2.Range request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CALCULATE_SUM, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class PrimesSumCalculatorFutureStub extends io.grpc.stub.AbstractStub<PrimesSumCalculatorFutureStub> {
    private PrimesSumCalculatorFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PrimesSumCalculatorFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PrimesSumCalculatorFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PrimesSumCalculatorFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ru.mipt.java2017.hw2.PrimesSum> calculateSum(
        ru.mipt.java2017.hw2.Range request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CALCULATE_SUM, getCallOptions()), request);
    }
  }

  private static final int METHODID_CALCULATE_SUM = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PrimesSumCalculatorImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PrimesSumCalculatorImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CALCULATE_SUM:
          serviceImpl.calculateSum((ru.mipt.java2017.hw2.Range) request,
              (io.grpc.stub.StreamObserver<ru.mipt.java2017.hw2.PrimesSum>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class PrimesSumCalculatorBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PrimesSumCalculatorBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return ru.mipt.java2017.hw2.PrimesSumProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PrimesSumCalculator");
    }
  }

  private static final class PrimesSumCalculatorFileDescriptorSupplier
      extends PrimesSumCalculatorBaseDescriptorSupplier {
    PrimesSumCalculatorFileDescriptorSupplier() {}
  }

  private static final class PrimesSumCalculatorMethodDescriptorSupplier
      extends PrimesSumCalculatorBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    PrimesSumCalculatorMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (PrimesSumCalculatorGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PrimesSumCalculatorFileDescriptorSupplier())
              .addMethod(METHOD_CALCULATE_SUM)
              .build();
        }
      }
    }
    return result;
  }
}

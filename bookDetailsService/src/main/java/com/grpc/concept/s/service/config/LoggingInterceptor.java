package com.grpc.concept.s.service.config;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingInterceptor implements ServerInterceptor {
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        log.info("Call started for " + call.getMethodDescriptor().getFullMethodName());
//        return next.startCall(call, headers);
        ServerCall.Listener<ReqT> listener = next.startCall(call, headers);

        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(listener) {

            @Override
            public void onComplete() {
                log.info("After completing the service for method : " + call.getMethodDescriptor().getFullMethodName());
                super.onComplete();
            }

            @Override
            public void onCancel() {
                log.info("After cancelling the service for method : " + call.getMethodDescriptor().getFullMethodName());
                super.onCancel();
            }
        };
    }
}
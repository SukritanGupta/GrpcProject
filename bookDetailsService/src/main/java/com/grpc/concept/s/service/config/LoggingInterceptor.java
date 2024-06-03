package com.grpc.concept.s.service.config;

import com.grpc.concept.s.UiService.JwtService;
import io.grpc.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoggingInterceptor implements ServerInterceptor {
    private final Environment environment;
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        log.info("Call started for " + call.getMethodDescriptor().getFullMethodName());
        log.info("loggingInterceptor");
//        return next.startCall(call, headers);
        ServerCall.Listener<ReqT> listener = next.startCall(call, headers);
        if(!environment.acceptsProfiles(Profiles.of("local"))){
            String token = headers.get(Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER));

            if (token == null || !token.startsWith("Bearer ")) {
                call.close(Status.UNAUTHENTICATED.withDescription("Authorization token is missing"), headers);
                log.info("call is closed");
                return new ServerCall.Listener<ReqT>() {};
            }

            token = token.substring(7); // Remove "Bearer " prefix
                log.info("Inside the prod profile ");
            try {
                String username = JwtService.extractUsername(token);
                Context ctx = Context.current().withValue(Context.key("username"), username);
                return Contexts.interceptCall(ctx, call, headers, next);
            } catch (Exception e) {
                call.close(Status.UNAUTHENTICATED.withDescription("Authorization token is invalid"), headers);
                return new ServerCall.Listener<ReqT>() {};
            }
            finally {
                log.info("Call is closed");
            }
        }
        else{
            log.info("Inside the local profile");
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
}
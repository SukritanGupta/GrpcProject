package com.grpc.concept.s;

import io.grpc.CallCredentials;
import io.grpc.Metadata;
import io.grpc.Status;
import org.springframework.context.annotation.Profile;

import java.util.concurrent.Executor;

@Profile("prod")
//CallCredentials to include the JWT token in the metadata of each gRPC call.
public class JwtCallCredentials extends CallCredentials {

    private final String jwtToken;

    public JwtCallCredentials(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    @Override
    public void applyRequestMetadata(RequestInfo requestInfo, Executor appExecutor, MetadataApplier applier) {
        appExecutor.execute(() -> {
            try {
                Metadata headers = new Metadata();
                Metadata.Key<String> jwtKey = Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER);
                headers.put(jwtKey, "Bearer " + jwtToken);
                applier.apply(headers);
            } catch (Throwable e) {
                applier.fail(Status.UNAUTHENTICATED.withCause(e));
            }
        });
    }

    @Override
    public void thisUsesUnstableApi() {
        // Do nothing
    }
}

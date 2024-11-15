package com.example.demo;

import org.springframework.stereotype.Service;

import com.exemplo.servicoc.grpc.InformacaoServiceGrpc;
import com.exemplo.servicoc.grpc.InformacaoServiceProto.InformacaoRequest;
import com.exemplo.servicoc.grpc.InformacaoServiceProto.InformacaoResponse;

import io.grpc.ManagedChannel;

@Service
public class ServicoBClient {
    private final InformacaoServiceGrpc.InformacaoServiceBlockingStub stub;

    public ServicoBClient(ManagedChannel channel) {
        this.stub = InformacaoServiceGrpc.newBlockingStub(channel);
    }

    public InformacaoResponse getInformacao(long id) {
        return stub.getInformacao(InformacaoRequest.newBuilder().setId(id).build());
    }
}

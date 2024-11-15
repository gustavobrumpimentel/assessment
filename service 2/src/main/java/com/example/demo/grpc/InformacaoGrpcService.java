package com.example.demo.grpc;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.Informacao;
import com.example.demo.repository.InformacaoRepository;
import com.exemplo.servicob.grpc.InformacaoServiceGrpc.InformacaoServiceImplBase;
import com.exemplo.servicob.grpc.InformacaoServiceProto.InformacaoCreateRequest;
import com.exemplo.servicob.grpc.InformacaoServiceProto.InformacaoRequest;
import com.exemplo.servicob.grpc.InformacaoServiceProto.InformacaoResponse;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class InformacaoGrpcService extends InformacaoServiceImplBase {

    private final InformacaoRepository informacaoRepository;

    @Autowired
    public InformacaoGrpcService(InformacaoRepository informacaoRepository) {
        this.informacaoRepository = informacaoRepository;
    }

    @Override
    public void getInformacao(InformacaoRequest request, StreamObserver<InformacaoResponse> responseObserver) {
        try {
            Informacao informacao = informacaoRepository.findById(request.getId()).orElse(null);
            if (informacao == null) {
                responseObserver.onError(Status.NOT_FOUND
                    .withDescription("Informação não encontrada para o ID: " + request.getId())
                    .asRuntimeException());
                return;
            }
            
            InformacaoResponse response = InformacaoResponse.newBuilder()
                    .setId(informacao.getId())
                    .setData(informacao.getData())
                    .build();
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL
                .withDescription("Erro interno ao processar a solicitação")
                .augmentDescription(e.getMessage())
                .asRuntimeException());
        }
    }

    @Override
    public void createInformacao(InformacaoCreateRequest request, StreamObserver<InformacaoResponse> responseObserver) {
        Informacao informacao = new Informacao();
        informacao.setData(request.getData());
        Informacao savedInformacao = informacaoRepository.save(informacao);
        
        InformacaoResponse response = InformacaoResponse.newBuilder()
                .setId(savedInformacao.getId())
                .setData(savedInformacao.getData())
                .build();
        
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}

package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import com.example.demo.grpc.InformacaoGrpcService;
import com.example.demo.model.Informacao;
import com.example.demo.repository.InformacaoRepository;
import com.exemplo.servicob.grpc.InformacaoServiceProto.InformacaoRequest;
import com.exemplo.servicob.grpc.InformacaoServiceProto.InformacaoResponse;

import io.grpc.stub.StreamObserver;

@ExtendWith(MockitoExtension.class)
class InformacaoGrpcServiceTest {

    @Mock
    private InformacaoRepository repository;

    private InformacaoGrpcService grpcService;

    @BeforeEach
    void setUp() {
        grpcService = new InformacaoGrpcService(repository); // Passando o mock diretamente
    }

    @Test
    void shouldReturnInformacao() {
        Informacao informacao = new Informacao();
        informacao.setId(1L);
        informacao.setData("Teste");

        when(repository.findById(1L)).thenReturn(Optional.of(informacao));

        InformacaoRequest request = InformacaoRequest.newBuilder().setId(1L).build();
        StreamObserver<InformacaoResponse> responseObserver = mock(StreamObserver.class);

        grpcService.getInformacao(request, responseObserver);

        ArgumentCaptor<InformacaoResponse> captor = ArgumentCaptor.forClass(InformacaoResponse.class);
        verify(responseObserver).onNext(captor.capture());
        verify(responseObserver).onCompleted();

        assertEquals("Teste", captor.getValue().getData());
    }
}

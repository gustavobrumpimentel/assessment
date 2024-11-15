package com.example.demo;

import com.exemplo.servicoc.grpc.InformacaoServiceGrpc;
import com.exemplo.servicoc.grpc.InformacaoServiceProto.InformacaoRequest;
import com.exemplo.servicoc.grpc.InformacaoServiceProto.InformacaoResponse;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicoBClientTest {

    private ManagedChannel channel;
    private InformacaoServiceGrpc.InformacaoServiceBlockingStub stub;
    private ServicoBClient servicoBClient;

    @BeforeEach
    void setUp() {
        channel = mock(ManagedChannel.class);
        stub = mock(InformacaoServiceGrpc.InformacaoServiceBlockingStub.class);

        try (MockedStatic<InformacaoServiceGrpc> mockedStatic = Mockito.mockStatic(InformacaoServiceGrpc.class)) {
            mockedStatic.when(() -> InformacaoServiceGrpc.newBlockingStub(channel)).thenReturn(stub);
            servicoBClient = new ServicoBClient(channel);
        }
    }

    @Test
    void shouldReturnInformacaoWhenValidId() {
        long id = 1L;
        InformacaoResponse mockResponse = InformacaoResponse.newBuilder()
                .setId(id)
                .setData("Valid Data")
                .build();

        when(stub.getInformacao(InformacaoRequest.newBuilder().setId(id).build())).thenReturn(mockResponse);

        InformacaoResponse response = servicoBClient.getInformacao(id);

        assertNotNull(response);
        assertEquals("Valid Data", response.getData());
        verify(stub, times(1)).getInformacao(InformacaoRequest.newBuilder().setId(id).build());
    }

    @Test
    void shouldThrowExceptionWhenGrpcServiceFails() {
        long id = 2L;

        when(stub.getInformacao(InformacaoRequest.newBuilder().setId(id).build()))
                .thenThrow(new StatusRuntimeException(io.grpc.Status.NOT_FOUND));

        StatusRuntimeException exception = assertThrows(StatusRuntimeException.class, () -> servicoBClient.getInformacao(id));
        assertEquals(io.grpc.Status.NOT_FOUND.getCode(), exception.getStatus().getCode());

        verify(stub, times(1)).getInformacao(InformacaoRequest.newBuilder().setId(id).build());
    }
}

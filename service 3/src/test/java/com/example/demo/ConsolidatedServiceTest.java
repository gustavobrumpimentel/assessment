package com.example.demo;

import com.exemplo.servicoc.grpc.InformacaoServiceProto.InformacaoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ConsolidatedServiceTest {

    @Mock
    private ServicoAClient servicoAClient;

    @Mock
    private ServicoBClient servicoBClient;

    @InjectMocks
    private ConsolidatedService consolidatedService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnConsolidatedDataWhenBothServicesAreSuccessful() {
        Long id = 1L;

        InformacaoDTO informacaoA = new InformacaoDTO();
        informacaoA.setData("Data from Service A");

        InformacaoResponse informacaoB = InformacaoResponse.newBuilder()
                .setData("Data from Service B")
                .build();

        when(servicoAClient.getInformacao(id)).thenReturn(informacaoA);
        when(servicoBClient.getInformacao(id)).thenReturn(informacaoB);

        ConsolidatedResponse result = consolidatedService.getConsolidatedData(id);

        assertEquals("Data from Service A", result.getDataA());
        assertEquals("Data from Service B", result.getDataB());

        verify(servicoAClient, times(1)).getInformacao(id);
        verify(servicoBClient, times(1)).getInformacao(id);
    }

    @Test
    void shouldHandleErrorInServiceA() {
        Long id = 2L;

        when(servicoAClient.getInformacao(id)).thenThrow(new RuntimeException("Service A Error"));

        InformacaoResponse informacaoB = InformacaoResponse.newBuilder()
                .setData("Data from Service B")
                .build();

        when(servicoBClient.getInformacao(id)).thenReturn(informacaoB);

        ConsolidatedResponse result = consolidatedService.getConsolidatedData(id);

        assertEquals("Erro no Serviço A: Service A Error", result.getDataA());
        assertEquals("Data from Service B", result.getDataB());

        verify(servicoAClient, times(1)).getInformacao(id);
        verify(servicoBClient, times(1)).getInformacao(id);
    }

    @Test
    void shouldHandleErrorInServiceB() {
        Long id = 3L;

        InformacaoDTO informacaoA = new InformacaoDTO();
        informacaoA.setData("Data from Service A");

        when(servicoAClient.getInformacao(id)).thenReturn(informacaoA);
        when(servicoBClient.getInformacao(id)).thenThrow(new RuntimeException("Service B Error"));

        ConsolidatedResponse result = consolidatedService.getConsolidatedData(id);

        assertEquals("Data from Service A", result.getDataA());
        assertEquals("Erro no Serviço B: Service B Error", result.getDataB());

        verify(servicoAClient, times(1)).getInformacao(id);
        verify(servicoBClient, times(1)).getInformacao(id);
    }

    @Test
    void shouldHandleErrorsInBothServices() {
        Long id = 4L;

        when(servicoAClient.getInformacao(id)).thenThrow(new RuntimeException("Service A Error"));
        when(servicoBClient.getInformacao(id)).thenThrow(new RuntimeException("Service B Error"));

        ConsolidatedResponse result = consolidatedService.getConsolidatedData(id);

        assertEquals("Erro no Serviço A: Service A Error", result.getDataA());
        assertEquals("Erro no Serviço B: Service B Error", result.getDataB());

        verify(servicoAClient, times(1)).getInformacao(id);
        verify(servicoBClient, times(1)).getInformacao(id);
    }
}

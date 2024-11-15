package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ServicoAClientTest {

    @MockBean
    private ServicoAClient mockServicoAClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnInformacaoWhenValidId() {
        Long id = 1L;
        InformacaoDTO mockResponse = new InformacaoDTO();
        mockResponse.setId(id);
        mockResponse.setData("Test Data");

        when(mockServicoAClient.getInformacao(id)).thenReturn(mockResponse);

        InformacaoDTO result = mockServicoAClient.getInformacao(id);

        assertNotNull(result);
        assertEquals("Test Data", result.getData());
    }

    @Test
    void shouldThrowExceptionWhenServiceReturnsError() {
        Long id = 999L;

        when(mockServicoAClient.getInformacao(id)).thenThrow(new RuntimeException("Service Error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> mockServicoAClient.getInformacao(id));
        assertEquals("Service Error", exception.getMessage());
    }
}

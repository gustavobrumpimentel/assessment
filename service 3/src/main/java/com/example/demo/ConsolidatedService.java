package com.example.demo;

import org.springframework.stereotype.Service;

import com.exemplo.servicoc.grpc.InformacaoServiceProto.InformacaoResponse;

@Service
public class ConsolidatedService {
    private final ServicoAClient servicoAClient;
    private final ServicoBClient servicoBClient;

    public ConsolidatedService(ServicoAClient servicoAClient, ServicoBClient servicoBClient) {
        this.servicoAClient = servicoAClient;
        this.servicoBClient = servicoBClient;
    }

    public ConsolidatedResponse getConsolidatedData(Long id) {
        String dataA = null;
        try {
            InformacaoDTO informacaoA = servicoAClient.getInformacao(id);
            dataA = informacaoA.getData();
        } catch (RuntimeException e) {
            dataA = "Erro no Serviço A: " + e.getMessage();
        }
    
        String dataB = null;
        try {
            InformacaoResponse informacaoB = servicoBClient.getInformacao(id);
            dataB = informacaoB.getData();
        } catch (RuntimeException e) {
            dataB = "Erro no Serviço B: " + e.getMessage();
        }
    
        return ConsolidatedResponse.builder()
                .id(id)
                .dataA(dataA)
                .dataB(dataB)
                .build();
    }
}

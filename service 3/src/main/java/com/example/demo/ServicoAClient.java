package com.example.demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "servicoA", url = "http://localhost:8080")
public interface ServicoAClient {
    @GetMapping("/api/informacoes/{id}")
    InformacaoDTO getInformacao(@PathVariable("id") Long id);
}

package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Informacao;
import com.example.demo.repository.InformacaoRepository;

@RestController
@RequestMapping("/api/informacoes")
public class InformacaoController {
    @Autowired
    private InformacaoRepository informacaoRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Informacao> getInformacao(@PathVariable Long id) {
        return informacaoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Informacao createInformacao(@RequestBody Informacao informacao) {
        return informacaoRepository.save(informacao);
    }
}

package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.model.Informacao;
import com.example.demo.repository.InformacaoRepository;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class InformacaoRepositoryTest {

    @Autowired
    private InformacaoRepository repository;

    @Test
    void shouldSaveAndRetrieveInformacao() {
        Informacao informacao = new Informacao();
        informacao.setData("Teste");

        Informacao saved = repository.save(informacao);

        assertNotNull(saved.getId());
        assertEquals("Teste", saved.getData());
    }
}

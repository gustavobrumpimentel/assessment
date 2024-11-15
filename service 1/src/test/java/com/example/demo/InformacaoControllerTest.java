package com.example.demo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import com.example.demo.controller.InformacaoController;
import com.example.demo.model.Informacao;
import com.example.demo.repository.InformacaoRepository;

@WebMvcTest(InformacaoController.class)
@ExtendWith(SpringExtension.class)
class InformacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InformacaoRepository repository;

    @Test
    void shouldReturnInformacao() throws Exception {
        Informacao informacao = new Informacao();
        informacao.setId(1L);
        informacao.setData("Teste");

        when(repository.findById(1L)).thenReturn(Optional.of(informacao));

        mockMvc.perform(get("/api/informacoes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("Teste"));
    }

    @Test
    void shouldCreateInformacao() throws Exception {
        Informacao informacao = new Informacao();
        informacao.setId(1L);
        informacao.setData("Teste");

        when(repository.save(any(Informacao.class))).thenReturn(informacao);

        mockMvc.perform(post("/api/informacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"data\": \"Teste\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("Teste"));
    }
}

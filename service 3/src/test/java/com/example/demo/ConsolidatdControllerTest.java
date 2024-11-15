package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class ConsolidatedControllerTest {

    @Mock
    private ConsolidatedService consolidatedService;

    @InjectMocks
    private ConsolidatedController consolidatedController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(consolidatedController).build();
    }

    @Test
    void shouldReturnConsolidatedDataWhenIdIsValid() throws Exception {
        Long id = 1L;

        ConsolidatedResponse response = ConsolidatedResponse.builder()
                .id(id)
                .dataA("Test Data A")
                .dataB("Test Data B")
                .build();

        when(consolidatedService.getConsolidatedData(id)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/consolidated/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.dataA").value("Test Data A"))
                .andExpect(jsonPath("$.dataB").value("Test Data B"));

        verify(consolidatedService, times(1)).getConsolidatedData(id);
    }

    @Test
    void shouldReturnNotFoundWhenIdIsInvalid() throws Exception {
        Long id = 999L;

        when(consolidatedService.getConsolidatedData(id)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/consolidated/{id}", id))
                .andExpect(status().isNotFound());

        verify(consolidatedService, times(1)).getConsolidatedData(id);
    }
}

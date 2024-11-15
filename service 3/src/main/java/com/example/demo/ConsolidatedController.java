package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/consolidated")
public class ConsolidatedController {
    private final ConsolidatedService consolidatedService;

    public ConsolidatedController(ConsolidatedService consolidatedService) {
        this.consolidatedService = consolidatedService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsolidatedResponse> getConsolidated(@PathVariable Long id) {
        ConsolidatedResponse response = consolidatedService.getConsolidatedData(id);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }
    
}

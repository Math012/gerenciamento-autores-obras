package com.math012.autoresobras.controller;

import com.math012.autoresobras.business.DTO.request.ObraRequestDTO;
import com.math012.autoresobras.business.DTO.response.ObraResponseDTO;
import com.math012.autoresobras.business.service.ObraService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/author/works")
public class ObraController {

    private final ObraService service;

    @PostMapping("/create")
    public ResponseEntity<ObraResponseDTO> createAuthorsWorks(@RequestBody ObraRequestDTO obraRequestDTO) {
        return ResponseEntity.ok(service.createAuthorsWorks(obraRequestDTO));
    }

    @GetMapping("/page/all")
    public ResponseEntity<Map<String, Object>> getAllAuthorsWorksWithPage(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "5", required = false) int size
    ) {
        return new ResponseEntity<>(service.findAllWithPages(page, size), HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<List<ObraResponseDTO>> findAllAuthorsWorksByName(@RequestParam("author") String author) {
        return ResponseEntity.ok(service.findAllAuthorsWorksByName(author));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ObraResponseDTO> updateAuthorsWorks(@PathVariable("id") Long id, @RequestBody ObraRequestDTO obraRequestDTO) {
        return ResponseEntity.ok(service.updateAuthorsWorks(id, obraRequestDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAuthorsWorksById(@PathVariable("id") Long id) {
        service.deleteAuthorsWorksById(id);
        return ResponseEntity.ok().build();
    }
}
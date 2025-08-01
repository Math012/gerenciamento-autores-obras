package com.math012.autoresobras.controller;

import com.math012.autoresobras.business.DTO.request.AutorRequestDTO;
import com.math012.autoresobras.business.DTO.response.AutorResponseDTO;
import com.math012.autoresobras.business.service.AutorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/author")
public class AutorController {

    private final AutorService service;

    @PostMapping("/create")
    public ResponseEntity<AutorResponseDTO> createAuthor(@RequestBody @Valid AutorRequestDTO request) {
        return ResponseEntity.ok(service.createAuthor(request));
    }

    @GetMapping("/find/name")
    public ResponseEntity<AutorResponseDTO> findAuthorByName(@RequestParam("name") String name) {
        return ResponseEntity.ok(service.findAuthorByName(name));
    }

    @GetMapping("/find/all/page")
    ResponseEntity<Map<String, Object>> findAllAuthorsWithPage(@RequestParam(defaultValue = "0", required = false) int page,
                                                               @RequestParam(defaultValue = "5", required = false) int size) {
        return new ResponseEntity<>(service.findAllAuthorsWithPage(page, size), HttpStatus.OK);
    }

    @GetMapping("/find/all")
    ResponseEntity<List<AutorResponseDTO>> findAllAuthors() {
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/update/{id}")
    ResponseEntity<AutorResponseDTO> updateAuthor(@PathVariable("id") Long id, @RequestBody AutorRequestDTO autorRequestDTO) {
        return ResponseEntity.ok(service.updateAuthor(id, autorRequestDTO));
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<Void> deleteAuthorById(@PathVariable("id") Long id) {
        service.deleteAuthorById(id);
        return ResponseEntity.ok().build();
    }
}
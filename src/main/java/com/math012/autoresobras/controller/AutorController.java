package com.math012.autoresobras.controller;

import com.math012.autoresobras.business.DTO.request.AutorRequestDTO;
import com.math012.autoresobras.business.DTO.response.AutorResponseDTO;
import com.math012.autoresobras.business.service.AutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Endpoint para Criar um autor", description = "Criando um autor")
    @ApiResponse(responseCode = "200", description = "Autor criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao criar autor, campos inválidos")
    @ApiResponse(responseCode = "400", description = "Erro ao criar autor, CPF vazio")
    @ApiResponse(responseCode = "400", description = "Erro ao criar autor, CPF inválido")
    @ApiResponse(responseCode = "401", description = "Falha na autenticação")
    @ApiResponse(responseCode = "403", description = "Credenciais inválidas")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<AutorResponseDTO> createAuthor(@RequestBody @Valid AutorRequestDTO request) {
        return ResponseEntity.ok(service.createAuthor(request));
    }

    @GetMapping("/find/name")
    @Operation(summary = "Endpoint para encontrar um autor", description = "Encontrando um autor através do seu nome")
    @ApiResponse(responseCode = "200", description = "Autor encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Erro ao encontrar autor, nome não encontrado")
    @ApiResponse(responseCode = "401", description = "Falha na autenticação")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<AutorResponseDTO> findAuthorByName(@RequestParam("name") String name) {
        return ResponseEntity.ok(service.findAuthorByName(name));
    }

    @GetMapping("/find/all/page")
    @Operation(summary = "Endpoint para listar todos os autores", description = "Listando todos os autores com paginação")
    @ApiResponse(responseCode = "200", description = "Autores encontrados com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    ResponseEntity<Map<String, Object>> findAllAuthorsWithPage(@RequestParam(defaultValue = "0", required = false) int page,
                                                               @RequestParam(defaultValue = "5", required = false) int size) {
        return new ResponseEntity<>(service.findAllAuthorsWithPage(page, size), HttpStatus.OK);
    }

    @GetMapping("/find/all")
    @Operation(summary = "Endpoint para listar todos os autores", description = "Listando todos os autores")
    @ApiResponse(responseCode = "200", description = "Autores encontrados com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    ResponseEntity<List<AutorResponseDTO>> findAllAuthors() {
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Endpoint para atualizar um autor", description = "Atualizando um autor")
    @ApiResponse(responseCode = "200", description = "Autor atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao atualizar autor, CPF vazio")
    @ApiResponse(responseCode = "400", description = "Erro ao atualizar autor, CPF inválido")
    @ApiResponse(responseCode = "401", description = "Falha na autenticação")
    @ApiResponse(responseCode = "403", description = "Credenciais inválidas")
    @ApiResponse(responseCode = "404", description = "Erro ao atualizar autor, id não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    ResponseEntity<AutorResponseDTO> updateAuthor(@PathVariable("id") Long id, @RequestBody AutorRequestDTO autorRequestDTO) {
        return ResponseEntity.ok(service.updateAuthor(id, autorRequestDTO));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Endpoint para deletar um autor", description = "Deletando um autor")
    @ApiResponse(responseCode = "200", description = "Autor deletado com sucesso")
    @ApiResponse(responseCode = "401", description = "Falha na autenticação")
    @ApiResponse(responseCode = "403", description = "Credenciais inválidas")
    @ApiResponse(responseCode = "404", description = "Erro ao deletar autor, id não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    ResponseEntity<Void> deleteAuthorById(@PathVariable("id") Long id) {
        service.deleteAuthorById(id);
        return ResponseEntity.ok().build();
    }
}
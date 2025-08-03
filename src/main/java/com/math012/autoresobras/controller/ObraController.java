package com.math012.autoresobras.controller;

import com.math012.autoresobras.business.DTO.request.ObraRequestDTO;
import com.math012.autoresobras.business.DTO.response.ObraResponseDTO;
import com.math012.autoresobras.business.service.ObraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Endpoint para criar uma obra", description = "Criando uma obra")
    @ApiResponse(responseCode = "200", description = "Obra criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao criar a obra, campos inválidos")
    @ApiResponse(responseCode = "401", description = "Falha na autenticação")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<ObraResponseDTO> createAuthorsWorks(@RequestBody ObraRequestDTO obraRequestDTO) {
        return ResponseEntity.ok(service.createAuthorsWorks(obraRequestDTO));
    }

    @GetMapping("/page/all")
    @Operation(summary = "Endpoint para listar todas as obras", description = "Listando todas as obras com paginação")
    @ApiResponse(responseCode = "200", description = "Obras encontradas com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<Map<String, Object>> getAllAuthorsWorksWithPage(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "5", required = false) int size
    ) {
        return new ResponseEntity<>(service.findAllWithPages(page, size), HttpStatus.OK);
    }

    @GetMapping("/name")
    @Operation(summary = "Endpoint para encontrar obras através do nome do autor", description = "Encontrando todas obras com base no nome do autor")
    @ApiResponse(responseCode = "200", description = "Obra encontrada com sucesso")
    @ApiResponse(responseCode = "401", description = "Falha na autenticação")
    @ApiResponse(responseCode = "404", description = "Erro ao encontrar a obra, autor não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<List<ObraResponseDTO>> findAllAuthorsWorksByName(@RequestParam("author") String author) {
        return ResponseEntity.ok(service.findAllAuthorsWorksByName(author));
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Endpoint para atualizar obra através do id", description = "Atualizando obra com base no id")
    @ApiResponse(responseCode = "200", description = "Obra atualizada com sucesso")
    @ApiResponse(responseCode = "401", description = "Falha na autenticação")
    @ApiResponse(responseCode = "404", description = "Erro ao atualizar a obra, id não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<ObraResponseDTO> updateAuthorsWorks(@PathVariable("id") Long id, @RequestBody ObraRequestDTO obraRequestDTO) {
        return ResponseEntity.ok(service.updateAuthorsWorks(id, obraRequestDTO));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Endpoint para deletar obra através do id", description = "Deletando obra com base no id")
    @ApiResponse(responseCode = "200", description = "Obra deletada com sucesso")
    @ApiResponse(responseCode = "401", description = "Falha na autenticação")
    @ApiResponse(responseCode = "404", description = "Erro ao deletar a obra, id não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<Void> deleteAuthorsWorksById(@PathVariable("id") Long id) {
        service.deleteAuthorsWorksById(id);
        return ResponseEntity.ok().build();
    }
}
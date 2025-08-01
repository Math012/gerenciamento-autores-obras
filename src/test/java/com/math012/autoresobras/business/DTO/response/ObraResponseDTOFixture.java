package com.math012.autoresobras.business.DTO.response;

import java.util.Date;
import java.util.List;

public class ObraResponseDTOFixture {
    public static ObraResponseDTO build(Long id, String nome, String descricao, Date dataPublicacao, List<AutorResponseDTO> autorEntityList) {
        return new ObraResponseDTO(id, nome, descricao, dataPublicacao, autorEntityList);
    }
}
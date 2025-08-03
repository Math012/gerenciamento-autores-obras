package com.math012.autoresobras.business.DTO.request;

import java.util.Date;

public class ObraRequestDTOFixture {
    public static ObraRequestDTO build(String nome, String descricao, Date dataPublicacao) {
        return new ObraRequestDTO(nome, descricao, dataPublicacao);
    }
}
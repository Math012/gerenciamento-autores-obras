package com.math012.autoresobras.business.DTO.response;

import java.util.Date;
import java.util.List;

public class AutorResponseDTOFixture {
    public static AutorResponseDTO build(Long id, String nome, String sexo, String email, Date dataNascimento, String pais, String cpf, List<ObraResponseDTO> obraEntityList){
        return new AutorResponseDTO(id,nome,sexo,email,dataNascimento,pais,cpf,obraEntityList);
    }
}
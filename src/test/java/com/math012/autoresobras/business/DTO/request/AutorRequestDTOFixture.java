package com.math012.autoresobras.business.DTO.request;

import java.util.Date;
import java.util.List;

public class AutorRequestDTOFixture {
    public static AutorRequestDTO build(String nome, String sexo, String email, Date dataNascimento, String pais, String cpf, List<ObraRequestDTO> obraEntityList){
        return new AutorRequestDTO(nome,sexo,email,dataNascimento,pais,cpf,obraEntityList);
    }
}
package com.math012.autoresobras.business.DTO.response;

import lombok.*;

import java.util.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class AutorResponseDTO {
    private Long id;
    private String nome;
    private String sexo;
    private String email;
    private Date dataNascimento;
    private String pais;
    private String cpf;
    private List<ObraResponseDTO> obraEntityList;
}

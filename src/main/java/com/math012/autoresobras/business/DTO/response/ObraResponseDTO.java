package com.math012.autoresobras.business.DTO.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ObraResponseDTO {
    private Long id;
    private String nome;
    private String descricao;
    private Date dataPublicacao;
    @JsonIgnore
    private List<AutorResponseDTO> autorEntityList;
}

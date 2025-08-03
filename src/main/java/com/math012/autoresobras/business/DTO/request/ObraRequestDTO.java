package com.math012.autoresobras.business.DTO.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class ObraRequestDTO {

    private String nome;

    private String descricao;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataPublicacao;

}

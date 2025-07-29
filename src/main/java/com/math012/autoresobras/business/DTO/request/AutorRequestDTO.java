package com.math012.autoresobras.business.DTO.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
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
public class AutorRequestDTO {

    private String nome;

    private String sexo;

    @Email(message = "Email inválido, tente novamente")
    private String email;

    @Past
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataNascimento;

    private String pais;

    private String cpf;

    private List<ObraRequestDTO> obraEntityList;
}

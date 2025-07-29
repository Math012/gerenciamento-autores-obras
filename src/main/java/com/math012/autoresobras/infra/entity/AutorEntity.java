package com.math012.autoresobras.infra.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@Entity(name = "autor")
public class AutorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "campo nome é obrigatório")
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "sexo")
    private String sexo;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Past
    @Column(name = "data_nascimento")
    private Date dataNascimento;

    @Column(name = "pais_origem", nullable = false)
    private String pais;

    @Column(name = "cpf", unique = true)
    private String cpf;


    @Cascade(CascadeType.ALL)
    @ManyToMany
    @JoinTable(name = "autores_obras", joinColumns = @JoinColumn(name = "autor_id"),
            inverseJoinColumns = @JoinColumn(name = "obra_id")
    )
    private List<ObraEntity> obraEntityList;
}
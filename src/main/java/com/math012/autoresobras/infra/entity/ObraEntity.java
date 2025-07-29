package com.math012.autoresobras.infra.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
@Entity(name = "obra")
public class ObraEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao", length = 240)
    private String descricao;

    @Column(name = "data_publicacao")
    private Date dataPublicacao;

    @JsonIgnore
    @Cascade(CascadeType.ALL)
    @ManyToMany(mappedBy = "obraEntityList")
    private List<AutorEntity> autorEntityList;
}
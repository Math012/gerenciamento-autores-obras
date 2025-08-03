package com.math012.autoresobras.infra.repository;

import com.math012.autoresobras.infra.entity.ObraEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ObraRepository extends JpaRepository<ObraEntity, Long> {

    Optional<ObraEntity> findByNome(String nome);

    @Transactional
    @Query("SELECT o FROM obra o JOIN o.autorEntityList a WHERE a.nome = :nome")
    Optional<List<ObraEntity>> findObrasByAuthor(String nome);

    @Override
    Page<ObraEntity> findAll(Pageable pageable);
}

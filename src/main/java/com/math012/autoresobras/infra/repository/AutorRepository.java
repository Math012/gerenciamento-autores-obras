package com.math012.autoresobras.infra.repository;

import com.math012.autoresobras.infra.entity.AutorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AutorRepository extends JpaRepository<AutorEntity, Long> {
    Optional<AutorEntity> findByNome(String nome);

    @Override
    Page<AutorEntity> findAll(Pageable pageable);

}

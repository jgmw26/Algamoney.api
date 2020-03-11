package com.algaworks.algamoney.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.algaworks.algamoney.api.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	
	@Query(value = "SELECT * FROM categoria WHERE nome LIKE %:nome%", nativeQuery = true)
	Page<Categoria> findByNome(@Param("nome") String nome, Pageable paginacao);

}

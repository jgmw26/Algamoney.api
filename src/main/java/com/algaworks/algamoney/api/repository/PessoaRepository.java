package com.algaworks.algamoney.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.algaworks.algamoney.api.model.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long>{
	
	@Query(value = "SELECT * FROM pessoa WHERE nome LIKE %:nome%", nativeQuery = true)
	Page<Pessoa> findByNome(@Param("nome") String nome,Pageable paginacao);

}

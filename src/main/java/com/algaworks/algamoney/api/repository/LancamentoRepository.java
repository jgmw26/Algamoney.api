package com.algaworks.algamoney.api.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.model.LancamentoSmall;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
	
	@Query(value = "SELECT * FROM lancamento WHERE tipo LIKE %:tipo%", nativeQuery = true)
	Page<Lancamento> findByTipoLike(@Param("tipo") String tipo, Pageable paginacao);

	@Query(value = "SELECT * FROM lancamento WHERE data_vencimento LIKE %:data%", nativeQuery = true)
	Page<Lancamento> findByDataVencimento(@Param("data") LocalDate data, Pageable paginacao);
	
	@Query(value = "SELECT * FROM lancamento WHERE descricao LIKE %:descricao%", nativeQuery = true)
	Page<Lancamento> findByDescricaoLike(@Param("descricao") String descricao, Pageable paginacao);
	
	@Query(value = "SELECT la.* FROM pessoa AS pe INNER JOIN lancamento AS la ON la.pessoa_id = pe.id AND pe.nome LIKE %:nome%", nativeQuery = true)
	List<Lancamento> findByPessoaLike(@Param("nome") String nome);
	
	@Query(value = "SELECT * FROM lancamento ORDER BY pessoa_id DESC", nativeQuery = true)
	List<Lancamento> findByLancamentoSmall();
	
	
}

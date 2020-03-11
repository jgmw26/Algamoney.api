package com.algaworks.algamoney.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.repository.LancamentoRepository;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private PessoaService pessoaSerice;

	@Autowired
	private CategoriaService categoriaService;

	public Lancamento converte(Lancamento lancamento) {
		Lancamento lancamentoNovo = lancamento;
		lancamentoNovo.setCategoria(categoriaService.buscarPessoaPeloId(lancamento.getCategoria().getId(),1));
		lancamentoNovo.setPessoa(pessoaSerice.buscarPessoaPeloId(lancamento.getPessoa().getId(),1));
		return lancamentoNovo;
	}

	public Lancamento atualizar(Long id, Lancamento lancamento) {
		Lancamento lancamentoSalva = buscarPessoaPeloId(id);
		BeanUtils.copyProperties(lancamento, lancamentoSalva, "id");
		return lancamentoRepository.save(lancamentoSalva);
	}

	private Lancamento buscarPessoaPeloId(Long id) {
		Optional<Lancamento> lancamentoSalva = lancamentoRepository.findById(id);
		if (lancamentoSalva.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		return lancamentoSalva.get();
	}

}

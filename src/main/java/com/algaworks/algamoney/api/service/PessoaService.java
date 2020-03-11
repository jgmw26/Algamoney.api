package com.algaworks.algamoney.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.PessoaRepository;
import com.algaworks.algamoney.api.service.exception.PessoaInexistenteOuInativaException;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	public void atualizaPropriedadeAtivo(Long id, Boolean ativo) {
		Pessoa pessoaSalva = buscarPessoaPeloId(id, 0);
		pessoaSalva.setAtivo(ativo);
		pessoaRepository.save(pessoaSalva);
	}

	public Pessoa atualizar(Long id, Pessoa pessoa) {
		Pessoa pessoaSalva = buscarPessoaPeloId(id, 0);
		BeanUtils.copyProperties(pessoa, pessoaSalva, "id");
		return pessoaRepository.save(pessoaSalva);
	}

	public Pessoa buscarPessoaPeloId(Long id, int tipoException) {
		System.out.println(tipoException);
		Optional<Pessoa> pessoaSalva = pessoaRepository.findById(id);
		if (pessoaSalva.isEmpty() && tipoException == 0) {
			throw new EmptyResultDataAccessException(1);
		} else if (pessoaSalva.isEmpty() && tipoException == 1 || !pessoaSalva.get().getAtivo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		return pessoaSalva.get();
	}

}

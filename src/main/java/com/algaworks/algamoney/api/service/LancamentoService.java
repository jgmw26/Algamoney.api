package com.algaworks.algamoney.api.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.model.LancamentoSmall;
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
	
//	Total de Despesas e receitas
	public List<LancamentoSmall> lancamentoTotalSmall(Pageable paginacao) {
		
		List<Lancamento> lancamentoSalva = lancamentoRepository.findByLancamentoSmall();
		List<LancamentoSmall> listLancamentosSmall = new ArrayList<LancamentoSmall>();
		for (Lancamento lancamento : lancamentoSalva) {
			boolean aux = false;
			LancamentoSmall lancamentoSmallAux = new LancamentoSmall();
			LancamentoSmall lancamentoSmall = new LancamentoSmall();
			lancamentoSmall.setNome(lancamento.getCategoria().getNome());
			lancamentoSmall.setValor(lancamento.getValor());
			lancamentoSmall.setTipo(lancamento.getTipo());
			lancamentoSmall.setNomePessoa(lancamento.getPessoa().getNome());
			lancamentoSmall.setData(lancamento.getDataVencimento());
			if(listLancamentosSmall.isEmpty()) {
				listLancamentosSmall.add(lancamentoSmall);
			}else {
				for (LancamentoSmall lancamentoSmall2 : listLancamentosSmall) {
					if(lancamentoSmall2.getNome().equals(lancamentoSmall.getNome()) && 
					   lancamentoSmall2.getNomePessoa().equals(lancamentoSmall.getNomePessoa())) {
						aux = true;
						lancamentoSmallAux = lancamentoSmall2;
						break;
					}else{
						aux = false;
					}
				}
				if(aux) {
					lancamentoSmall.setValor(lancamentoSmallAux.getValor().add(lancamentoSmall.getValor()));
					listLancamentosSmall.remove(listLancamentosSmall.indexOf(lancamentoSmallAux));
					listLancamentosSmall.add(lancamentoSmall);
				}else {
					listLancamentosSmall.add(lancamentoSmall);
				}
			}
		
		}
		return listLancamentosSmall;
	}
	
	public List<LancamentoSmall> lancamentoSmall(Pageable paginacao){
		List<Lancamento> lancamentoSalva = lancamentoRepository.findAll(paginacao).getContent();
		List<LancamentoSmall> listLancamentosSmall = new ArrayList<LancamentoSmall>();
		for (Lancamento lancamento : lancamentoSalva) {
			
			LancamentoSmall lancamentoSmall = new LancamentoSmall();
			lancamentoSmall.setNome(lancamento.getCategoria().getNome());
			lancamentoSmall.setValor(lancamento.getValor());
			lancamentoSmall.setTipo(lancamento.getTipo());
			lancamentoSmall.setNomePessoa(lancamento.getPessoa().getNome());
			lancamentoSmall.setData(lancamento.getDataVencimento());
			listLancamentosSmall.add(lancamentoSmall);
			
			
		}
		return listLancamentosSmall;
	}
	
	public List<LancamentoSmall> lancamentoSmallPessoaLike(String nome) {
		
		List<Lancamento> lancamentoSalva = lancamentoRepository.findByPessoaLike(nome);
		List<LancamentoSmall> listLancamentosSmall = new ArrayList<LancamentoSmall>();
		for (Lancamento lancamento : lancamentoSalva) {
			boolean aux = false;
			LancamentoSmall lancamentoSmallAux = new LancamentoSmall();
			LancamentoSmall lancamentoSmall = new LancamentoSmall();
			lancamentoSmall.setNome(lancamento.getCategoria().getNome());
			lancamentoSmall.setValor(lancamento.getValor());
			lancamentoSmall.setTipo(lancamento.getTipo());
			lancamentoSmall.setNomePessoa(lancamento.getPessoa().getNome());
			lancamentoSmall.setData(lancamento.getDataVencimento());
			if(listLancamentosSmall.isEmpty()) {
				listLancamentosSmall.add(lancamentoSmall);
			}else {
				for (LancamentoSmall lancamentoSmall2 : listLancamentosSmall) {
					if(lancamentoSmall2.getNome().equals(lancamentoSmall.getNome())) {
						aux = true;
						lancamentoSmallAux = lancamentoSmall2;
						break;
					}else{
						aux = false;
					}
				}
				if(aux) {
					lancamentoSmall.setValor(lancamentoSmallAux.getValor().add(lancamentoSmall.getValor()));
					listLancamentosSmall.remove(listLancamentosSmall.indexOf(lancamentoSmallAux));
					listLancamentosSmall.add(lancamentoSmall);
				}else {
					listLancamentosSmall.add(lancamentoSmall);
				}
			}
		
		}
		return listLancamentosSmall;
	}

}

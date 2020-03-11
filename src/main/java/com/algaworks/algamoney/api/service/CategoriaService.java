package com.algaworks.algamoney.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algamoney.api.model.Categoria;
import com.algaworks.algamoney.api.repository.CategoriaRepository;
import com.algaworks.algamoney.api.service.exception.CategoriaInexistenteOuInativaException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public Categoria atualizar(Long id, Categoria categoria) {
		Categoria categoriaSalva = buscarPessoaPeloId(id,0);
		BeanUtils.copyProperties(categoria, categoriaSalva, "id");
		return categoriaRepository.save(categoriaSalva);
	}

	public Categoria buscarPessoaPeloId(Long id,int tipoException) {
		Optional<Categoria> categoriaSalva = categoriaRepository.findById(id);
		if (categoriaSalva.isEmpty()&&tipoException==0) {
			throw new EmptyResultDataAccessException(1);
		}else if(categoriaSalva.isEmpty()&&tipoException==1){
			throw new CategoriaInexistenteOuInativaException();
		}
		return categoriaSalva.get();
	}

}

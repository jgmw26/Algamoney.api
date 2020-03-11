package com.algaworks.algamoney.api.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Categoria {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	@NotNull
	@Size(min = 3,max = 50)
	private String nome;
	@OneToMany(mappedBy = "categoria")
	List<Lancamento> lancamentos= new ArrayList<>();

	public Categoria() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}

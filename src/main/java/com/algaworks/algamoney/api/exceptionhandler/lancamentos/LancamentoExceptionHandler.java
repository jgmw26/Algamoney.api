package com.algaworks.algamoney.api.exceptionhandler.lancamentos;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.algaworks.algamoney.api.exceptionhandler.AlgamoneyExceptionHandler.Erro;
import com.algaworks.algamoney.api.service.exception.CategoriaInexistenteOuInativaException;
import com.algaworks.algamoney.api.service.exception.PessoaInexistenteOuInativaException;

@ControllerAdvice
public class LancamentoExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler({ PessoaInexistenteOuInativaException.class })
	public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex) {
		String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null,
				LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
	@ExceptionHandler({ CategoriaInexistenteOuInativaException.class })
	public ResponseEntity<Object> handleCategoriaInexistenteOuInativaException(CategoriaInexistenteOuInativaException ex) {
		String mensagemUsuario = messageSource.getMessage("categoria.inexistente", null,
				LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}

}

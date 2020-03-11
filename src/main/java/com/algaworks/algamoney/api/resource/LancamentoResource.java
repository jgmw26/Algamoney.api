package com.algaworks.algamoney.api.resource;

import java.time.LocalDate;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algamoney.api.event.RecursoCriadoEvent;
import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.repository.LancamentoRepository;
import com.algaworks.algamoney.api.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
@CrossOrigin("http://localhost:4200")
public class LancamentoResource {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private LancamentoService lancamentoService;

	@GetMapping
	public Page<Lancamento> Lista(@RequestParam(required = false) LocalDate dataVencimento,
			@RequestParam(required = false) String descricao,
			@RequestParam(required = false) String tipo,
			@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao) {
		System.out.println(dataVencimento);
		if (dataVencimento == null && descricao == null&&tipo==null) {
			return lancamentoRepository.findAll(paginacao);
		} else if (descricao == null&&tipo==null) {
			return lancamentoRepository.findByDataVencimento(dataVencimento, paginacao);
		} else if(tipo==null) {
			return lancamentoRepository.findByDescricaoLike(descricao, paginacao);
		} else {
			return lancamentoRepository.findByTipoLike(tipo, paginacao);
		}
	}

	@GetMapping("/{id}")
	public Optional<Lancamento> BuscaId(@PathVariable Long id) {
		return lancamentoRepository.findById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Lancamento> cadastra(@Valid @RequestBody Lancamento lancamento,
			HttpServletResponse response) {

		Lancamento lancamentoSalvo = lancamentoRepository.save(lancamentoService.converte(lancamento));

		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long id) {
		lancamentoRepository.deleteById(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Lancamento> atualizar(@PathVariable Long id, @Valid @RequestBody Lancamento lancamento) {
		Lancamento lancamentoSalva = lancamentoService.atualizar(id, lancamento);
		return ResponseEntity.ok(lancamentoSalva);
	}

}

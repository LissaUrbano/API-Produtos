package com.gft.produtos.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gft.produtos.api.event.RecursoCriadoEvent;
import com.gft.produtos.api.model.Fornecedor;
import com.gft.produtos.api.repository.FornecedorRepository;
import com.gft.produtos.api.service.FornecedorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Fornecedores")
@RestController
@RequestMapping("/fornecedores")
public class FornecedorResource {

	@Autowired
	private FornecedorRepository fornecedorRepository;

	@Autowired
	private FornecedorService fornecedorService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	
	@ApiOperation("Listar todos os fornecedores")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping
	public List<Fornecedor> listar(){
		return fornecedorRepository.findAll();
	}

	@ApiOperation("Inserir fornecedor")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@PostMapping
	public ResponseEntity<Fornecedor> criar(@Valid @RequestBody Fornecedor fornecedor, HttpServletResponse response){
		Fornecedor fornecedorSalvo = fornecedorRepository.save(fornecedor);
		 publisher.publishEvent(new RecursoCriadoEvent(this, response, fornecedorSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(fornecedorSalvo);
	}
	
	@ApiOperation("Buscar por ID")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/{id}")
	public Fornecedor buscarPorId(@PathVariable Long id) {
		return fornecedorService.buscarPorId(id);
	}
	
	@ApiOperation("Atualizar fornecedor")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@PutMapping("/{id}")
	public ResponseEntity<Fornecedor> atualizar(@PathVariable Long id, @Valid @RequestBody Fornecedor fornecedor) {
		Fornecedor fornecedorSalvo = fornecedorService.atualizar(id, fornecedor);
		return ResponseEntity.ok(fornecedorSalvo);
	}
	
	@ApiOperation("Excluir fornecedor")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT) 
	public void remover(@PathVariable Long 	id) { 
		fornecedorService.remover(id); 
	}
	
	@ApiOperation("Listar os fornecedores em ordem alfabética crescente por nome")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/asc")
	public List<Fornecedor> listarPorNomeCrescente() {
		return fornecedorService.listarNomeCrescente();
	}

	@ApiOperation("Listar os fornecedores em ordem alfabética decrescente por nome")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/desc") public Iterable<Fornecedor> listarPorNomeDecrescente() {
	return fornecedorService.listarNomeDecrescente(); }
	 
	@ApiOperation("Buscar fornecedor por nome")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/nome/{nome}")
	public List<Fornecedor> buscarPorNome(@PathVariable String nome) {
		return fornecedorService.buscarPorNome(nome);
	}
}

package com.gft.produtos.api.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.gft.produtos.api.event.RecursoCriadoEvent;
import com.gft.produtos.api.exceptionhandler.ProdutosExceptionHandler.Erro;
import com.gft.produtos.api.model.Venda;
import com.gft.produtos.api.repository.VendaRepository;
import com.gft.produtos.api.service.VendaService;
import com.gft.produtos.api.service.exception.ListaProdutosVaziaException;
import com.gft.produtos.api.service.exception.ProdutoInexistenteParaFornecedorException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Vendas")
@RestController
@RequestMapping("/vendas")
public class VendaResource {

	@Autowired
	private VendaRepository vendaRepository;
	
	@Autowired
	private VendaService vendaService;
	
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private MessageSource messageSource;
	
	@ApiOperation("Listar todas as vendas")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping
	public List<Venda> listar(){
		return vendaRepository.findAll();
	}
	
	@ApiOperation("Inserir cadastro de venda")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@PostMapping
	public ResponseEntity<Venda> criar(@Valid @RequestBody Venda venda, HttpServletResponse response){
		Venda vendaSalva = vendaService.salvar(venda);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, vendaSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(vendaSalva);
	}
	
	@ApiOperation("Buscar por ID")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/{id}")
	public Venda buscarPorId(@PathVariable Long id) {
		return vendaService.buscarPorId(id);
	}
	
	@ApiOperation("Atualiza vendas")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@PutMapping("/{id}")
	public ResponseEntity<Venda> atualizar(@PathVariable Long id, @Valid @RequestBody Venda venda) {
		Venda vendaSalva = vendaService.atualizar(id, venda);
		return ResponseEntity.ok(vendaSalva);
	}
	
	
	@ApiOperation("Exclui venda")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT) 
	public void remover(@PathVariable Long 	id) { 
		this.vendaRepository.deleteById(id); 
	}
	
	@ApiOperation("Listar compras do cliente")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/cliente/{id}")
	public List<Venda> buscarComprasPorCliente(@PathVariable Long id) {
		return vendaService.buscarComprasPorCliente(id);
	}
	
	@ApiOperation("Listar compras do fornecedor")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/fornecedor/{id}")
	public List<Venda> buscarComprasPorFornecedor(@PathVariable Long id) {
		return vendaService.buscarComprasPorFornecedor(id);
	}
	
	@ApiOperation("Listar compras que contem produto")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/produto/{id}")
	public List<Venda> buscarComprasPorProduto(@PathVariable Long id) {
		return vendaService.buscarComprasPorProduto(id);
	}
	
	@ApiOperation("Listar vendas por valor da compra decrescente")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/valor-desc") public List<Venda> listarValorDecrescente() {
		return vendaService.ordenarPorTotalCompraDecrescente();
	}
	
	@ApiOperation("Listar vendas por valor da compra crescente")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/valor-asc") public List<Venda> listarValorCrescente() {
		return vendaService.ordenarPorTotalCompraCrescente();
	}
	
	@ApiOperation("Listar vendas por data decrescente")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/data-desc") public List<Venda> listarDataDecrescente() {
		return vendaService.ordenarPorDataDecrescente();
	}
	
	@ApiOperation("Listar vendas por data crescente")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/data-asc") public List<Venda> listarDataCrescente() {
		return vendaService.ordenarPorDataCrescente();
	}
	
	@ExceptionHandler({ProdutoInexistenteParaFornecedorException.class})
	public ResponseEntity<Object> handleProdutoInexistenteParaFornecedorException(ProdutoInexistenteParaFornecedorException ex){
		String mensagemUsuario = messageSource.getMessage("produto.inexistente-para-fornecedor", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
	
	@ExceptionHandler({ListaProdutosVaziaException.class})
	public ResponseEntity<Object> handleListaProdutosVaziaException(ListaProdutosVaziaException ex){
		String mensagemUsuario = messageSource.getMessage("produto.lista-vazia", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
}

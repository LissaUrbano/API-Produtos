package com.gft.produtos.api.service;



import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.EmptyResultDataAccessException;

import org.springframework.stereotype.Service;

import com.gft.produtos.api.model.Cliente;
import com.gft.produtos.api.model.Fornecedor;
import com.gft.produtos.api.model.Produto;
import com.gft.produtos.api.model.Venda;
import com.gft.produtos.api.repository.VendaRepository;
import com.gft.produtos.api.service.exception.ListaProdutosVaziaException;
import com.gft.produtos.api.service.exception.ProdutoInexistenteParaFornecedorException;

@Service
public class VendaService {

	@Autowired
	private VendaRepository vendaRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private FornecedorService fornecedorService;
	
	@Autowired
	private ProdutoService produtoService;
	

	
	public Venda salvar(@Valid Venda venda) {
		Fornecedor fornecedor = fornecedorService.buscarPorId(venda.getFornecedor().getId());
		Cliente cliente = clienteService.buscarPorId(venda.getCliente().getId());
		List<Produto> produtosDaVenda = venda.getProdutos();
		if (produtosDaVenda != null) {
			for (Produto produto : produtosDaVenda) {
				Produto produtoEncontrado = produtoService.buscarPorId(produto.getId());
				if (produtoEncontrado.getFornecedor().getId() == fornecedor.getId()) {
				}else {
					throw new ProdutoInexistenteParaFornecedorException();
				}
			}
			venda.setTotalCompra(calcularTotalCompra(produtosDaVenda));
			produtoService.decrementaProdutosEstoque(produtosDaVenda);
			return vendaRepository.save(venda);
		} else {
			throw new ListaProdutosVaziaException();}
	}

	public Venda atualizar(Long id, Venda venda) {
		Venda vendaSalva = buscarPorId(id);
		BeanUtils.copyProperties(venda, vendaSalva, "id", "totalCompra", "dataCompra");
		return vendaRepository.save(vendaSalva);
	}

	public Venda buscarPorId(Long id) {
		Venda vendaSalva = vendaRepository.findById(id).orElse(null);
		if (vendaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return vendaSalva;
	}
	
	private Double calcularTotalCompra(List<Produto> produtosDaVenda) {
		Double totalCompra = 0.00;
		for (Produto produto : produtosDaVenda) {
			totalCompra += produtoService.precoDoProduto(produto.getId());
		}
		return totalCompra;
	}
	

	public List<Venda> buscarComprasPorCliente(Long id) {
		Cliente cliente = clienteService.buscarPorId(id);
		List<Venda> vendasEncontradas = vendaRepository.findByCliente(cliente);
		if (vendasEncontradas == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return vendasEncontradas;
	}
	
	
	public List<Venda> ordenarPorTotalCompraCrescente() {
		List<Venda> vendasEncontradas = vendaRepository.findAllByOrderByTotalCompraAsc();
		if (vendasEncontradas == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return vendasEncontradas;
	}
	
	public List<Venda> ordenarPorTotalCompraDecrescente() {
		List<Venda> vendasEncontradas = vendaRepository.findAllByOrderByTotalCompraDesc();
		if (vendasEncontradas == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return vendasEncontradas;
	}
	
	public List<Venda> buscarComprasPorFornecedor(Long id) {
		Fornecedor fornecedor = fornecedorService.buscarPorId(id);
		List<Venda> vendasEncontradas = vendaRepository.findByFornecedor(fornecedor);
		if (vendasEncontradas == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return vendasEncontradas;
	}
	
	public List<Venda> buscarComprasPorProduto(Long id) {
		Produto produto = produtoService.buscarPorId(id);
		List<Venda> vendasEncontradas = vendaRepository.findByProdutos(produto);
		if (vendasEncontradas == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return vendasEncontradas;
	}


	public List<Venda> ordenarPorDataCrescente() {
		List<Venda> vendasEncontradas = vendaRepository.findAllByOrderByDataCompraAsc();
		if (vendasEncontradas == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return vendasEncontradas;
	}
	
	public List<Venda> ordenarPorDataDecrescente() {
		List<Venda> vendasEncontradas = vendaRepository.findAllByOrderByDataCompraDesc();
		if (vendasEncontradas == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return vendasEncontradas;
	}
	

}

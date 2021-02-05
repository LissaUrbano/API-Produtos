package com.gft.produtos.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gft.produtos.api.model.Produto;
import com.gft.produtos.api.repository.ProdutoRepository;
import com.gft.produtos.api.service.exception.ProdutoNaoEncontradoException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	public Produto atualizar(Long id, Produto produto) {
		Produto produtoSalvo = buscarPorId(id);
		BeanUtils.copyProperties(produto, produtoSalvo, "id");
		return produtoRepository.save(produtoSalvo);
	}

	public Produto buscarPorId(Long id) {
		Produto produtoSalvo = produtoRepository.findById(id).orElse(null);
		if (produtoSalvo == null) {
			throw new ProdutoNaoEncontradoException();
		}
		return produtoSalvo;
	}

	public List<Produto> buscarPorNome(String nome) {
		List<Produto> produtosEncontrados = produtoRepository.findByNomeContainingIgnoreCase(nome);
		if (produtosEncontrados == null) {
			throw new ProdutoNaoEncontradoException();
		}
		return produtosEncontrados;
	}
	
	public List<Produto> listarNomeCrescente() {
		List<Produto> produtosEncontrados = produtoRepository.findAllByOrderByNomeAsc();
		if (produtosEncontrados == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return produtosEncontrados;
	}
	
	 public List<Produto> listarNomeDecrescente() { 
		 List<Produto> produtosEncontrados = produtoRepository.findAllByOrderByNomeDesc(); 
		 if (produtosEncontrados == null) { 
			 throw new EmptyResultDataAccessException(1);
	 } 
	 return produtosEncontrados; }
	 
	 public Double precoDoProduto(Long id) {
		Produto produto = buscarPorId(id);
		if (produto.getPromocao()) {
			return produto.getValorPromo();
		} else {
			return produto.getValor();
		}
	}
	 
	 public void decrementaProdutosEstoque(List<Produto> produtosDaVenda) {
		for (Produto produto : produtosDaVenda) {
			Produto produtoAtual = buscarPorId(produto.getId());
			produtoAtual.setQuantidade(produtoAtual.getQuantidade()-1);
		}
	}

	public void remover(Long id) {
		Produto produto = produtoRepository.findById(id).orElse(null);
		if (produto == null) {
			throw new ProdutoNaoEncontradoException();
		}
		produtoRepository.deleteById(id);
	}
	
}

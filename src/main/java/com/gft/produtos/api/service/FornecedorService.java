package com.gft.produtos.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gft.produtos.api.model.Cliente;
import com.gft.produtos.api.model.Fornecedor;
import com.gft.produtos.api.repository.FornecedorRepository;
import com.gft.produtos.api.service.exception.FornecedorNaoEncontradoException;

@Service
public class FornecedorService {
	
	@Autowired
	private FornecedorRepository fornecedorRepository;

	public Fornecedor atualizar(Long id, Fornecedor fornecedor) {
		Fornecedor fornecedorSalvo = buscarPorId(id);
		BeanUtils.copyProperties(fornecedor, fornecedorSalvo, "id");
		return fornecedorRepository.save(fornecedorSalvo);
	}

	public Fornecedor buscarPorId(Long id) {
		Fornecedor fornecedorSalvo = fornecedorRepository.findById(id).orElse(null);
		if (fornecedorSalvo == null) {
			throw new FornecedorNaoEncontradoException();
		}
		return fornecedorSalvo;
	}
	
	public void remover(Long id) {
		Fornecedor fornecedor = fornecedorRepository.findById(id).orElse(null);
		if (fornecedor == null) {
			throw new FornecedorNaoEncontradoException();
		}
		fornecedorRepository.deleteById(id);
	}

	public List<Fornecedor> buscarPorNome(String nome) {
		List<Fornecedor> fornecedoresEncontrados = fornecedorRepository.findByNomeContainingIgnoreCase(nome);
		if (fornecedoresEncontrados == null) {
			throw new FornecedorNaoEncontradoException();
		}
		return fornecedoresEncontrados;
	}
	
	public List<Fornecedor> listarNomeCrescente() {
		List<Fornecedor> fornecedoresEncontrados = fornecedorRepository.findAllByOrderByNomeAsc();
		if (fornecedoresEncontrados == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return fornecedoresEncontrados;
	}
	
	 public List<Fornecedor> listarNomeDecrescente() { 
		 List<Fornecedor> fornecedoresEncontrados = fornecedorRepository.findAllByOrderByNomeDesc(); if
		 (fornecedoresEncontrados == null) { 
			 throw new EmptyResultDataAccessException(1);
	 } 
	 return fornecedoresEncontrados; }



}

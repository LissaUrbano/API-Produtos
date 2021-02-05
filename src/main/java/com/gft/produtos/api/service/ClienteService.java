package com.gft.produtos.api.service;


import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.gft.produtos.api.model.Cliente;
import com.gft.produtos.api.repository.ClienteRepository;
import com.gft.produtos.api.service.exception.ClienteNaoEncontradoException;


@Service 
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente atualizar(Long id, Cliente cliente) {
		Cliente clienteSalvo = buscarPorId(id);
		BeanUtils.copyProperties(cliente, clienteSalvo, "id", "dataCadastro");
		return clienteRepository.save(clienteSalvo);
	}

	public Cliente buscarPorId(Long id) {
		Cliente clienteSalvo = clienteRepository.findById(id).orElse(null);
		if (clienteSalvo == null) {
			throw new ClienteNaoEncontradoException();
		}
		return clienteSalvo;
	}
	
	public void remover(Long id) {
		Cliente cliente = clienteRepository.findById(id).orElse(null);
		if (cliente == null) {
			throw new ClienteNaoEncontradoException();
		}
		clienteRepository.deleteById(id); 
	}
	

	public List<Cliente> buscarPorNome(String nome) {
		List<Cliente> clientesEncontrados = clienteRepository.findByNomeContainingIgnoreCase(nome);
		if (clientesEncontrados == null) {
			throw new ClienteNaoEncontradoException();
		}
		return clientesEncontrados;
	}
	
	public List<Cliente> listarNomeCrescente() {
		List<Cliente> clientesEncontrados = clienteRepository.findAllByOrderByNomeAsc();
		if (clientesEncontrados == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return clientesEncontrados;
	}
	
	 public List<Cliente> listarNomeDecrescente() { 
		 List<Cliente> clientesEncontrados = clienteRepository.findAllByOrderByNomeDesc(); if
		 (clientesEncontrados == null) { 
			 throw new EmptyResultDataAccessException(1);
	 } 
	 return clientesEncontrados; }
	

}

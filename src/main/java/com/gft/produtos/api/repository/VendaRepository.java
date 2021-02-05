package com.gft.produtos.api.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gft.produtos.api.model.Cliente;
import com.gft.produtos.api.model.Fornecedor;
import com.gft.produtos.api.model.Produto;
import com.gft.produtos.api.model.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long>{

	List<Venda> findByCliente(Cliente cliente);
	List<Venda> findAllByOrderByTotalCompraAsc(); 
	List<Venda> findAllByOrderByTotalCompraDesc();
	
	 List<Venda> findByFornecedor(Fornecedor fornecedor);
	 List<Venda> findByProdutos(Produto produto);
	 List<Venda> findAllByOrderByDataCompraAsc();
	 List<Venda> findAllByOrderByDataCompraDesc(); 

}

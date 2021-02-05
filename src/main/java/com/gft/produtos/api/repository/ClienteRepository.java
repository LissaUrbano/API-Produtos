package com.gft.produtos.api.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.gft.produtos.api.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	
	List<Cliente> findByNomeContainingIgnoreCase(String nome);
	List<Cliente> findAllByOrderByNomeAsc();
	List<Cliente> findAllByOrderByNomeDesc();

}

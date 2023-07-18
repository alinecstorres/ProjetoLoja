package com.dev.loja.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dev.loja.modelos.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {

   Cliente findByDocumento(String documento);

   @Query(value = "SELECT * FROM loja.clientes WHERE nome LIKE %?%", nativeQuery = true)
   List<Cliente> findAllByNome(String nome);
   List<Cliente> findAllByDocumento(String documento);
    
}

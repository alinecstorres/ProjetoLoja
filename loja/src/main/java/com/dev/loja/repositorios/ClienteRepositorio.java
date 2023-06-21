package com.dev.loja.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.loja.modelos.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {

   Cliente findByDocumento(String documento);
   List<Cliente> findAllByNome(String nome);
   List<Cliente> findAllByDocumento(String documento);
    
}

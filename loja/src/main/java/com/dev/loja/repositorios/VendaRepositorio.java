package com.dev.loja.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.loja.modelos.Venda;

public interface VendaRepositorio extends JpaRepository<Venda, Long>{
    
}

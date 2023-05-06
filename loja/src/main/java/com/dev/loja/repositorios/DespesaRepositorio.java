package com.dev.loja.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.loja.modelos.Despesa;

public interface DespesaRepositorio extends JpaRepository<Despesa, Long>{
    
}

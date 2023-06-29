package com.dev.loja.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dev.loja.modelos.Despesa;

public interface DespesaRepositorio extends JpaRepository<Despesa, Long>{
    
    @Override
    @Query(value = "SELECT * FROM loja.debitos ORDER BY data DESC", nativeQuery = true)
    List<Despesa> findAll();
}

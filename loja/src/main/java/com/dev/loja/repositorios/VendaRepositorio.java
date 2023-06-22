package com.dev.loja.repositorios;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.loja.modelos.Venda;

public interface VendaRepositorio extends JpaRepository<Venda, Long>{

    List<Venda> findAllByDataEntrada(Date data);
    
}

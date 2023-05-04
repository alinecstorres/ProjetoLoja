package com.dev.loja.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.loja.modelos.VendaItens;

public interface VendaItensRepositorio extends JpaRepository<VendaItens, Long>{
}

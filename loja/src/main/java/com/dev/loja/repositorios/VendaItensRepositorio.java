package com.dev.loja.repositorios;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.loja.modelos.VendaItens;

public interface VendaItensRepositorio extends JpaRepository<VendaItens, Long>{

    List<VendaItens> findByVendaId(Long id);
}

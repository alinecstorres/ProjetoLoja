package com.dev.loja.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.loja.modelos.Produto;

public interface ProdutoRepositorio extends JpaRepository<Produto, Long>{

    Produto findByNomeCompletoProduto(String nomeCompleto);
    List<Produto> findAllByNomeProduto(String nome);
    
}

package com.dev.loja.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dev.loja.modelos.Produto;

public interface ProdutoRepositorio extends JpaRepository<Produto, Long>{

    Produto findByNomeCompletoProduto(String nomeCompleto);

    @Query(value = "SELECT * FROM loja.produtos WHERE nome_produto LIKE %?%", nativeQuery = true)
    List<Produto> findAllByNomeProduto(@Param("NOME") String NOME);
    
}

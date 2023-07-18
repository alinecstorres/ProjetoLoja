package com.dev.loja.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.loja.modelos.Permissao;

public interface PermissaoRepositorio extends JpaRepository<Permissao, Long> {
    
}

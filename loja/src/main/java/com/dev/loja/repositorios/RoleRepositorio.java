package com.dev.loja.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.loja.modelos.Role;

public interface RoleRepositorio extends JpaRepository<Role, Long> {
    
}

package com.dev.loja.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.loja.enums.RoleName;
import com.dev.loja.modelos.Role; // Não se esqueça de importar o Enum

public interface RoleRepositorio extends JpaRepository<Role, Long> {
    
    // Método criado automaticamente pelo Spring Data JPA para buscar pelo RoleName
    Role findByRoleName(RoleName roleName);
    
}
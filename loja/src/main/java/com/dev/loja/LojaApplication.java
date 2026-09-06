package com.dev.loja;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dev.loja.enums.RoleName;
import com.dev.loja.modelos.Funcionario;
import com.dev.loja.modelos.Role;
import com.dev.loja.repositorios.FuncionarioRepositorio;
import com.dev.loja.repositorios.RoleRepositorio;

@SpringBootApplication
public class LojaApplication {

    public static void main(String[] args) {
        SpringApplication.run(LojaApplication.class, args);
    }

    @Bean
    public CommandLineRunner criarUsuarioPadrao(
            FuncionarioRepositorio funcionarioRepositorio, 
            PasswordEncoder passwordEncoder,
            RoleRepositorio roleRepositorio) {
        return args -> {
            // Verifica se não há funcionários cadastrados
            if (funcionarioRepositorio.count() == 0) {
                
                // 1. Tenta buscar a Role ROLE_ADMIN no banco, ou cria se não existir
                // (Caso seu Enum use outro nome, ajuste ROLE_ADMIN para o valor correto)
                Role roleAdmin = roleRepositorio.findByRoleName(RoleName.ROLE_ADMIN);
                if (roleAdmin == null) {
                    roleAdmin = new Role();
                    roleAdmin.setRoleName(RoleName.ROLE_ADMIN);
                    roleRepositorio.save(roleAdmin);
                }

                // 2. Cria o funcionário administrador
                Funcionario admin = new Funcionario();
                admin.setNome("Administrador do Sistema");
                admin.setCpf("000.000.000-00");
                admin.setEmail("admin@loja.com");
                admin.setSenha(passwordEncoder.encode("admin123"));
                
                // 3. Associa a Role ao funcionário
                admin.setRoles(Arrays.asList(roleAdmin));
                
                funcionarioRepositorio.save(admin);
                
                System.out.println("==================================================");
                System.out.println("✅ Administrador criado com a role ROLE_ADMIN!");
                System.out.println("==================================================");
            }
        };
    }
}
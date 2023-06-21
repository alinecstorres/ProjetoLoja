package com.dev.loja.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dev.loja.modelos.Funcionario;

public interface FuncionarioRepositorio extends JpaRepository<Funcionario, Long> {
    
List<Funcionario> findAllByNome(String nome);
List<Funcionario> findAllByCpf(String cpf);


}

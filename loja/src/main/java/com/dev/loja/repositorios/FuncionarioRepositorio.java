package com.dev.loja.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dev.loja.modelos.Funcionario;

public interface FuncionarioRepositorio extends JpaRepository<Funcionario, Long> {

@Query(value = "SELECT * FROM loja.funcionario WHERE nome LIKE %?%", nativeQuery = true)
List<Funcionario> findAllByNome(String nome);

List<Funcionario> findAllByCpf(String cpf);


}

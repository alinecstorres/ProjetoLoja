package com.dev.loja.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.loja.modelos.Saldo;

public interface FinanceiroRepositorio extends JpaRepository<Saldo, Long> {
}

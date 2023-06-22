package com.dev.loja.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dev.loja.modelos.Saldo;

public interface SaldoRepositorio extends JpaRepository<Saldo, Long> {

    @Query(value = "SELECT * FROM loja.saldo ORDER BY data_atualizacao DESC limit 1", nativeQuery = true)
    Saldo findLastSaldo();
}

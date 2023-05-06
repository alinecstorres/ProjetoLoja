package com.dev.loja.modelos;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "saldo")
public class Saldo implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date data = new Date();

    private Double caixa;
    private Double saldoBancario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Double getCaixa() {
        return caixa;
    }

    public void setCaixa(Double caixa) {
        this.caixa = caixa;
    }

    public Double getSaldoBancario() {
        return saldoBancario;
    }

    public void setSaldoBancario(Double saldoBancario) {
        this.saldoBancario = saldoBancario;
    }

   

    
}

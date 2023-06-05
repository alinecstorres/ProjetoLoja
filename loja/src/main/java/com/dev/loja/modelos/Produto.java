package com.dev.loja.modelos;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "produtos")
public class Produto implements Serializable {

    public Produto() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nomeProduto;
    private String tamanhoProduto;
    private String nomeCompletoProduto = "";
    private Double valorProduto;
    private Double alturaProduto;
    private Double larguraProduto;
    private Double profundidadeProduto;
    private Double pesoProduto;
    private int quantEstoque;
    private String nomeImagem;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNomeProduto() {
        return nomeProduto;
    }
    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }
    public String getTamanhoProduto() {
        return tamanhoProduto;
    }
    public void setTamanhoProduto(String tamanhoProduto) {
        this.tamanhoProduto = tamanhoProduto;
    }    
    public Double getValorProduto() {
        return valorProduto;
    }
    public void setValorProduto(Double valorProduto) {
        this.valorProduto = valorProduto;
    }
    public Double getAlturaProduto() {
        return alturaProduto;
    }
    public void setAlturaProduto(Double alturaProduto) {
        this.alturaProduto = alturaProduto;
    }
    public Double getLarguraProduto() {
        return larguraProduto;
    }
    public void setLarguraProduto(Double larguraProduto) {
        this.larguraProduto = larguraProduto;
    }
    public Double getProfundidadeProduto() {
        return profundidadeProduto;
    }
    public void setProfundidadeProduto(Double profundidadeProduto) {
        this.profundidadeProduto = profundidadeProduto;
    }
    public Double getPesoProduto() {
        return pesoProduto;
    }
    public void setPesoProduto(Double pesoProduto) {
        this.pesoProduto = pesoProduto;
    }
    public int getQuantEstoque() {
        return quantEstoque;
    }
    public void setQuantEstoque(int quantEstoque) {
        this.quantEstoque = quantEstoque;
    }
    public String getNomeImagem() {
        return nomeImagem;
    }
    public void setNomeImagem(String nomeImagem) {
        this.nomeImagem = nomeImagem;
    }
    public String getNomeCompletoProduto() {
        return nomeCompletoProduto;
    }
    public void setNomeCompletoProduto(String nomeCompletoProduto) {
        this.nomeCompletoProduto = nomeCompletoProduto;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nomeCompletoProduto == null) ? 0 : nomeCompletoProduto.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Produto other = (Produto) obj;
        if (nomeCompletoProduto == null) {
            if (other.nomeCompletoProduto != null)
                return false;
        } else if (!nomeCompletoProduto.equals(other.nomeCompletoProduto))
            return false;
        return true;
    }
    
    
    
}

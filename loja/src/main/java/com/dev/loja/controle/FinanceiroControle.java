package com.dev.loja.controle;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dev.loja.modelos.Despesa;
import com.dev.loja.modelos.Saldo;
import com.dev.loja.repositorios.DespesaRepositorio;
import com.dev.loja.repositorios.FinanceiroRepositorio;

@Controller
public class FinanceiroControle {

    List<Despesa> listaDespesas = new ArrayList<Despesa>();

    private DespesaRepositorio despesaRepositorio;
    private FinanceiroRepositorio financeiroRepositorio;

    @GetMapping("/administrativo/despesas/cadastrar")
    public ModelAndView cadastrar(Despesa despesa) {
        ModelAndView mv = new ModelAndView("administrativo/despesas/cadastro");
        mv.addObject("despesa", despesa);
        return mv;
    }

    @GetMapping("/administrativo/despesas/listar")
    public ModelAndView listarDespesas() {
        ModelAndView mv = new ModelAndView("administrativo/despesas/lista");
        mv.addObject("listaDespesas",despesaRepositorio.findAll());
        return mv;
    }

    @GetMapping("/administrativo/financeiro")
    public ModelAndView dadosFinanceiros(Saldo saldo) {
        ModelAndView mv = new ModelAndView("administrativo/financeiro/dados");
        mv.addObject("saldoCaixa", saldo.getCaixa());
        mv.addObject("saldoBancario", saldo.getSaldoBancario());
        return mv;
    }

    @PostMapping("administrativo/financeiro/atualizar")
    public ModelAndView atualizar(@Validated Saldo saldo, BindingResult result) {
        if(result.hasErrors()) {
            return dadosFinanceiros(saldo);
        }
        financeiroRepositorio.saveAndFlush(saldo);
        return dadosFinanceiros(saldo);
    }
}

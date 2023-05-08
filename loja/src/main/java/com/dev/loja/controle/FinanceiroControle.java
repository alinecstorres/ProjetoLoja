package com.dev.loja.controle;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private DespesaRepositorio despesaRepositorio;

    @Autowired
    private FinanceiroRepositorio financeiroRepositorio;

    List<Despesa> listaDespesas = new ArrayList<Despesa>();

    @GetMapping("/administrativo/despesas/cadastrar")
    public ModelAndView cadastrar(Despesa despesa) {
        ModelAndView mv = new ModelAndView("administrativo/financeiro/cadastro");
        mv.addObject("despesa", despesa);
        listaDespesas = despesaRepositorio.findAll();
        mv.addObject("listaDespesas", listaDespesas);
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

    @PostMapping("administrativo/despesas/salvar")
    public ModelAndView salvar(Despesa despesa, String acao) {

        if (acao.equals("salvar")) {
            despesaRepositorio.saveAndFlush(despesa);
            this.listaDespesas.add(despesa);
        } else {
            for (Despesa despesa1 : listaDespesas) {
                if (despesa1.getId().equals(Long.parseLong(acao))) {
                    despesaRepositorio.deleteById(Long.parseLong(acao));
                }
            }
        }
        System.out.println("--------------->"+acao);
        return cadastrar(new Despesa());
    }
}


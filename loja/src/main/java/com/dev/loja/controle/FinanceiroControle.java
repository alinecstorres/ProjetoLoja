package com.dev.loja.controle;

import java.sql.Date;
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
import com.dev.loja.modelos.Venda;
import com.dev.loja.modelos.VendaItens;
import com.dev.loja.repositorios.DespesaRepositorio;

import com.dev.loja.repositorios.ProdutoRepositorio;
import com.dev.loja.repositorios.SaldoRepositorio;
import com.dev.loja.repositorios.VendaItensRepositorio;
import com.dev.loja.repositorios.VendaRepositorio;

@Controller
public class FinanceiroControle {

    @Autowired
    private DespesaRepositorio despesaRepositorio;

    @Autowired
    private ProdutoRepositorio produtoRepositorio;

    @Autowired
    private SaldoRepositorio saldoRepositorio;

    @Autowired
    private VendaRepositorio vendaRepositorio;

    @Autowired
    private VendaItensRepositorio vendaItensRepositorio;

    List<Despesa> listaDespesas = new ArrayList<Despesa>();
    Date dataHoje;
    Double faturamentoZero;
    String produtoQualquer;

    @GetMapping("/administrativo/despesas/cadastrar")
    public ModelAndView cadastrar(Despesa despesa) {
        ModelAndView mv = new ModelAndView("administrativo/financeiro/cadastro");
        mv.addObject("despesa", despesa);
        listaDespesas = despesaRepositorio.findAll();
        mv.addObject("listaDespesas", listaDespesas);
        return mv;
    }

    @GetMapping("/administrativo/financeiro")
    public ModelAndView dadosFinanceiros(Date dataInicial, Date dataFinal, Double faturamentoPeriodo, Double faturamentoProduto,String produto) {
        ModelAndView mv = new ModelAndView("administrativo/financeiro/dados");
        System.out.println(dataHoje);
        Double saldoCaixa = 0%.2d;
        Double saldoBancario = 0%.2d;
        Saldo saldoAtual = saldoRepositorio.findLastSaldo();

        if (!saldoRepositorio.findAll().isEmpty()) {
            saldoBancario = saldoAtual.getSaldoBancario();
            saldoCaixa = saldoAtual.getCaixa();
        }

        mv.addObject("produto", produto);
        mv.addObject("faturamentoPeriodo", faturamentoPeriodo);
        mv.addObject("faturamentoProduto", faturamentoProduto);
        mv.addObject("dataInicial", dataInicial);
        mv.addObject("dataFinal", dataFinal);
        mv.addObject("saldoCaixa", saldoCaixa);
        mv.addObject("saldoBancario", saldoBancario);
        mv.addObject("saldo", saldoAtual);
        mv.addObject("listaProdutos", produtoRepositorio.findAll());
        mv.addObject("despesas", despesaRepositorio.findAll());
        mv.addObject("vendas", vendaRepositorio.findAll());
        return mv;
    }

    @GetMapping("/administrativo/financeiro/faturamento/periodo")
    public ModelAndView buscarFaturamentoPeriodo(Date dataInicial, Date dataFinal) {
        List<Venda> listaVendas = vendaRepositorio.findAll();
        Double faturamentoPeriodo = 0%.2d;

        for (Venda venda : listaVendas) {
            if ((venda.getDataEntrada().after(dataInicial)&&venda.getDataEntrada().before(dataFinal))||venda.getDataEntrada().equals(dataInicial)||venda.getDataEntrada().equals(dataFinal)) {
                faturamentoPeriodo += venda.getValorComDesconto();
            }
        }

        return dadosFinanceiros(dataInicial, dataFinal, faturamentoPeriodo, faturamentoZero, produtoQualquer);
    }

    @GetMapping("/administrativo/financeiro/faturamento/produto")
    public ModelAndView buscarFaturamentoProduto(String produto) {
        
        List<VendaItens> listaVendas = vendaItensRepositorio.findAll();
        Double faturamentoProduto = 0%.2d;

        for (VendaItens item : listaVendas) {
            if (item.getProduto().getNomeCompletoProduto().equals(produto)) {
                faturamentoProduto += item.getTotalComDesconto();
            }
        }
        
        return dadosFinanceiros(dataHoje, dataHoje, faturamentoZero, faturamentoProduto, produto);
    }

    @PostMapping("administrativo/financeiro/atualizar")
    public ModelAndView atualizar(@Validated Saldo novoSaldo, BindingResult result) {

        if(novoSaldo.getSaldoBancario() == null) {
            novoSaldo.setSaldoBancario(saldoRepositorio.findLastSaldo().getSaldoBancario());
        }
        if (novoSaldo.getCaixa() == null) {
            novoSaldo.setCaixa(saldoRepositorio.findLastSaldo().getCaixa());
        }

        saldoRepositorio.saveAndFlush(novoSaldo);

        return dadosFinanceiros(dataHoje, dataHoje, faturamentoZero, faturamentoZero, produtoQualquer);
    }

    @PostMapping("administrativo/despesas/salvar")
    public ModelAndView salvar(Despesa despesa, String acao) {

        listaDespesas = despesaRepositorio.findAll();

        if (acao.equals("salvar")) {
            if (!listaDespesas.contains(despesa)) {
                despesaRepositorio.saveAndFlush(despesa);
            }
            
        } else {
            for (Despesa despesa1 : listaDespesas) {
                if (despesa1.getId().equals(Long.parseLong(acao))) {
                    despesaRepositorio.deleteById(Long.parseLong(acao));
                }
            }
        }

        return cadastrar(new Despesa());
    }
}


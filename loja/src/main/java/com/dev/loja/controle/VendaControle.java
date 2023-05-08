package com.dev.loja.controle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dev.loja.modelos.Produto;
import com.dev.loja.modelos.Venda;
import com.dev.loja.modelos.VendaItens;
import com.dev.loja.repositorios.ClienteRepositorio;
import com.dev.loja.repositorios.FuncionarioRepositorio;
import com.dev.loja.repositorios.ProdutoRepositorio;
import com.dev.loja.repositorios.VendaItensRepositorio;
import com.dev.loja.repositorios.VendaRepositorio;

@Controller
public class VendaControle {
    
    Double valorVenda = 0%.2d;
    Double valorComDesconto = 0%.2d;

    List<VendaItens> listaItens = new ArrayList<VendaItens>();

    @Autowired
    private VendaRepositorio vendaRepositorio;

    @Autowired
    private VendaItensRepositorio vendaItensRepositorio;

    @Autowired
	private FuncionarioRepositorio funcionarioRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

	@Autowired
	private ProdutoRepositorio produtoRepositorio;

    @GetMapping("/administrativo/venda/cadastrar")
    public ModelAndView cadastrar(Venda venda, VendaItens vendaItens) {
        ModelAndView mv = new ModelAndView("administrativo/venda/cadastro");
        mv.addObject("venda", venda);
		mv.addObject("listaVendaItens", this.listaItens);
        mv.addObject("vendaItens", vendaItens);
		mv.addObject("listaFuncionarios", funcionarioRepositorio.findAll());
        mv.addObject("listaClientes", clienteRepositorio.findAll());
		mv.addObject("listaProdutos", produtoRepositorio.findAll());
        mv.addObject("valorVenda", this.valorVenda);
        mv.addObject("valorComDesconto", this.valorComDesconto);
        return mv;
    }

    @GetMapping("/administrativo/venda/listar")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("administrativo/venda/lista");
        mv.addObject("listaVendas",vendaRepositorio.findAll());
        return mv;
    }

    @GetMapping("/administrativo/venda/itens/{id}")
    public ModelAndView listarItens(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("administrativo/venda/itens");
        mv.addObject("itens", vendaItensRepositorio.findByVendaId(id));
        return mv;
    }

    @PostMapping("administrativo/venda/salvar")
    public ModelAndView salvar(String acao, Venda venda, VendaItens vendaItens) {


        if (acao.equals("itens")) {

            for (VendaItens it : listaItens) {
                if (it.getProduto().getId().equals(vendaItens.getProduto().getId())) {
                    return cadastrar(venda, vendaItens);
                }
            }
            Double totalPorProduto = vendaItens.getProduto().getValorProduto()*vendaItens.getQuantidade();
            vendaItens.setTotal(totalPorProduto);
            this.listaItens.add(vendaItens);
            valorVenda += (vendaItens.getProduto().getValorProduto()*vendaItens.getQuantidade());
            valorComDesconto = (valorVenda-(valorVenda*(venda.getDesconto()/100)));
            return cadastrar(venda, vendaItens);
            
		} else if(acao.equals("salvar")) {

            if (this.listaItens.isEmpty()) {
                return cadastrar(new Venda(), new VendaItens());
            } else {
                vendaRepositorio.saveAndFlush(venda);
                venda.setValorTotal(valorVenda);
                venda.setValorComDesconto(valorComDesconto);
            
                for(VendaItens it : listaItens) {
                    it.setVenda(venda);
                    vendaItensRepositorio.saveAndFlush(it);
                    Optional<Produto> prod = produtoRepositorio.findById(it.getProduto().getId());
                    Produto produto = prod.get();
                    produto.setQuantEstoque(produto.getQuantEstoque() - it.getQuantidade());
                    produtoRepositorio.saveAndFlush(produto);
                    this.listaItens = new ArrayList<>();

                };
                valorComDesconto = 0d;
                valorVenda = 0d;
                return cadastrar(new Venda(), new VendaItens());
            }
			
		} else if(acao.contains("-")) {
            for (VendaItens it : listaItens) {
                if (acao.equals(it.getProduto().getNomeCompletoProduto())) {
                    listaItens.remove(it);
                    valorVenda -= (it.getProduto().getValorProduto()*it.getQuantidade());
                    valorComDesconto = (valorVenda-(valorVenda*(venda.getDesconto()/100)));
                    return cadastrar(venda, vendaItens);
                }
            }
        } else if(acao.equals("clear")) {
            listaItens.clear();
            valorVenda = 0d;
            valorComDesconto = 0d;
            return cadastrar(new Venda(), new VendaItens());
        }

        return cadastrar(venda, new VendaItens());
    }
   
}

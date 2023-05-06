package com.dev.loja.controle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dev.loja.modelos.EntradaItens;
import com.dev.loja.modelos.EntradaProduto;
import com.dev.loja.modelos.Produto;
import com.dev.loja.repositorios.EntradaItensRepositorio;
import com.dev.loja.repositorios.EntradaProdutoRepositorio;
import com.dev.loja.repositorios.FuncionarioRepositorio;
import com.dev.loja.repositorios.ProdutoRepositorio;

@Controller
public class EntradaProdutoControle {


	private List<EntradaItens> listaEntrada = new ArrayList<EntradaItens>();

    @Autowired
    private EntradaProdutoRepositorio entradaProdutoRepositorio;

	@Autowired
	private EntradaItensRepositorio entradaItensRepositorio;

	@Autowired
	private FuncionarioRepositorio funcionarioRepositorio;

	@Autowired
	private ProdutoRepositorio produtoRepositorio;

    @GetMapping("/administrativo/entrada/cadastrar")
    public ModelAndView cadastrar(EntradaProduto entrada, EntradaItens entradaItens) {
        ModelAndView mv = new ModelAndView("administrativo/entrada/cadastro");
        mv.addObject("entrada", entrada);
		mv.addObject("listaEntradaItens", this.listaEntrada);
		mv.addObject("entradaItens", entradaItens);
		mv.addObject("listaFuncionarios", funcionarioRepositorio.findAll());
		mv.addObject("listaProdutos", produtoRepositorio.findAll());
        return mv;
    }  

    @GetMapping("/administrativo/entrada/listar")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("administrativo/entrada/lista");
        mv.addObject("listaEntradas",entradaProdutoRepositorio.findAll());
        mv.addObject("listaProdutos", entradaItensRepositorio.findAll());
        return mv;
    }

    @PostMapping("administrativo/entrada/salvar")
    public ModelAndView salvar(String acao, EntradaProduto entradaProduto, EntradaItens entradaItens) {

        if (acao.equals("itens")) {
            for (EntradaItens it : listaEntrada) {
                if (it.getProduto().getId().equals(entradaItens.getProduto().getId())&&it.getObservacao().equals(entradaItens.getObservacao())) {
                    return cadastrar(entradaProduto, entradaItens);
                } 
            }
            
            this.listaEntrada.add(entradaItens);
            
		} else if(acao.equals("salvar")) {
			entradaProdutoRepositorio.saveAndFlush(entradaProduto);
			for(EntradaItens it : listaEntrada) {
				it.setEntradaProduto(entradaProduto);
				entradaItensRepositorio.saveAndFlush(it);
				Optional<Produto> prod = produtoRepositorio.findById(it.getProduto().getId());
				Produto produto = prod.get();
                if (it.getObservacao().equals("NOVO")) {
                    produto.setQuantEstoque(produto.getQuantEstoque() + it.getQuantidade());
                } else {
                    produto.setQuantEstoque(produto.getQuantEstoque() - it.getQuantidade());
                }
				
				produtoRepositorio.saveAndFlush(produto);
				this.listaEntrada = new ArrayList<>();
			}
			return cadastrar(new EntradaProduto(), new EntradaItens());
		} else if(acao.contains("-")) {
            for (EntradaItens it : listaEntrada) {
                if (acao.equals(it.getProduto().getNomeCompletoProduto())) {
                    listaEntrada.remove(it);
                    return cadastrar(entradaProduto, entradaItens);
                }
            }
        } else if(acao.equals("clear")) {
            listaEntrada.clear();
            return cadastrar(new EntradaProduto(), new EntradaItens());
        }

        return cadastrar(entradaProduto, new EntradaItens());
    }
}
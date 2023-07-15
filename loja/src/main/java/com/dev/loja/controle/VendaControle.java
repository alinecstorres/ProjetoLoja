package com.dev.loja.controle;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dev.loja.modelos.Cliente;
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
    Double desconto = 0%.2d;

    String nomeCliente = "";

    String logoNome = "Logo9.png";
    String logoImagem = "Logo8.png";

    List<VendaItens> listaItens = new ArrayList<VendaItens>();

    private static String caminhoImagens = "C:/Users/aline/Workspace/Meus projetos/loja/loja/src/main/resources/static/image/";

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
        
        
        mv.addObject("nomeCliente", this.nomeCliente);
        mv.addObject("venda", venda);
        mv.addObject("desconto", this.desconto);
		mv.addObject("listaVendaItens", this.listaItens);
        mv.addObject("vendaItens", vendaItens);
		mv.addObject("listaFuncionarios", funcionarioRepositorio.findAll());
        mv.addObject("listaClientes", clienteRepositorio.findAll());
		mv.addObject("listaProdutos", produtoRepositorio.findAll());
        mv.addObject("valorVenda", this.valorVenda);
        mv.addObject("valorComDesconto", this.valorComDesconto);

        return mv;
    }

    @GetMapping("/administrativo/venda/pedido")
    public ModelAndView gerarPedido(Venda venda, VendaItens vendaItens) throws IOException {

        ModelAndView mv = new ModelAndView("administrativo/venda/pedido");
        int quantItens = this.listaItens.size();

        mv.addObject("quantItens", quantItens);
        mv.addObject("desconto", this.desconto);
        mv.addObject("logoNome", this.logoNome);
        mv.addObject("logoImagem", this.logoImagem);
        mv.addObject("nomeCliente", this.nomeCliente);
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

    @GetMapping("/administrativo/venda/mostrarImagem/{imagem}")
    @ResponseBody
    public byte[] retornarImagem(@PathVariable("imagem") String imagem) throws IOException {
        File imagemArquivo = new File(caminhoImagens+imagem);  
        
        if (imagem != null && imagem.trim().length()>0) { 
            return Files.readAllBytes(imagemArquivo.toPath());
        }
        
        return null;
    }

    @GetMapping("/administrativo/venda/listar")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("administrativo/venda/lista");
        mv.addObject("listaVendas",vendaRepositorio.findAll());
        return mv;
    }

    @GetMapping("/administrativo/venda/buscar/data")
    public ModelAndView buscarPorData(Date date) {
        ModelAndView mv = new ModelAndView("administrativo/venda/lista");
        mv.addObject("listaVendas",vendaRepositorio.findAllByDataEntrada(date));
        return mv;
    }

    @GetMapping("/administrativo/venda/buscar/funcionario")
    public ModelAndView buscarPorFuncionario(String funcionario) {
        ModelAndView mv = new ModelAndView("administrativo/venda/lista");
        List<Venda> listaVendas = vendaRepositorio.findAll();
        List<Venda> listaPorFuncionario = new ArrayList<>();

        for (Venda venda : listaVendas) {
            if (venda.getFuncionario().getNome().contains(funcionario)) {
                listaPorFuncionario.add(venda);
            }
        }
        mv.addObject("listaVendas", listaPorFuncionario);
        return mv;
    }

    @GetMapping("/administrativo/venda/buscar/cliente")
    public ModelAndView buscarPorCliente(String cliente) {
        ModelAndView mv = new ModelAndView("administrativo/venda/lista");
        List<Venda> listaVendas = vendaRepositorio.findAll();
        List<Venda> listaPorCliente = new ArrayList<>();

        for (Venda venda : listaVendas) {
            if (venda.getCliente().getNome().contains(cliente)) {
                listaPorCliente.add(venda);
            }
        }
        mv.addObject("listaVendas", listaPorCliente);
        return mv;
    }

    @GetMapping("/administrativo/venda/itens/{id}")
    public ModelAndView listarItens(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("administrativo/venda/itens");
        mv.addObject("itens", vendaItensRepositorio.findByVendaId(id));
        return mv;
    }

    @GetMapping("/administrativo/venda/cliente/buscar")
    public ModelAndView buscarCliente(String documento, Venda venda) {
        Cliente clienteAchado = clienteRepositorio.findByDocumento(documento);
        venda.setCliente(clienteAchado);
        nomeCliente = clienteAchado.getNome();
        return cadastrar(venda, new VendaItens());
    }

    @PostMapping("administrativo/venda/salvar")
    public ModelAndView salvar(String acao, Venda venda, VendaItens vendaItens) {


        if (acao.equals("itens") && vendaItens.getProduto() != null && vendaItens.getQuantidade() != 0) {

            for (VendaItens it : listaItens) {
                if (it.getProduto().getId().equals(vendaItens.getProduto().getId())) {
                    return cadastrar(venda, vendaItens);
                }
            }

            Double totalPorProduto = vendaItens.getProduto().getValorProduto()*vendaItens.getQuantidade();
            vendaItens.setTotal(totalPorProduto);
            vendaItens.setTotalComDesconto(totalPorProduto-(totalPorProduto*(venda.getDesconto()/100)));
            this.listaItens.add(vendaItens);
            valorVenda += (vendaItens.getProduto().getValorProduto()*vendaItens.getQuantidade());
            valorComDesconto = (valorVenda-(valorVenda*(venda.getDesconto()/100)));
            desconto = venda.getDesconto();
            return cadastrar(venda, vendaItens);
            
		} else if(acao.equals("salvar")) {

            if (this.listaItens.isEmpty()) {
                return cadastrar(new Venda(), new VendaItens());
            } else {
                vendaRepositorio.saveAndFlush(venda);
                venda.setValorTotal(valorVenda);
                venda.setValorComDesconto(valorComDesconto);
                venda.setDesconto(desconto);

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
                desconto = 0d;
                nomeCliente = "";
                return cadastrar(new Venda(), new VendaItens());
            }
			
		} else if(acao.contains("-")) {
            for (VendaItens it : listaItens) {
                if (acao.equals(it.getProduto().getNomeCompletoProduto())) {
                    listaItens.remove(it);
                    valorVenda -= (it.getProduto().getValorProduto()*it.getQuantidade());
                    valorComDesconto = (valorVenda-(valorVenda*(desconto/100)));
                    return cadastrar(venda, vendaItens);
                }
            }
        } else if(acao.equals("clear")) {
            listaItens.clear();
            valorVenda = 0d;
            valorComDesconto = 0d;
            desconto = 0d;
            nomeCliente = "";
            return cadastrar(new Venda(), new VendaItens());
        }

        return cadastrar(venda, new VendaItens());
    }
   
}

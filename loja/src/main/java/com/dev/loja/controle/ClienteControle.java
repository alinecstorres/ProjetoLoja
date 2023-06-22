package com.dev.loja.controle;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dev.loja.modelos.Cliente;
import com.dev.loja.repositorios.ClienteRepositorio;

@Controller
public class ClienteControle {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    private List<Cliente> listaClientes;

    @GetMapping("/administrativo/clientes/cadastrar")
    public ModelAndView cadastrar(Cliente cliente) {
        ModelAndView mv = new ModelAndView("administrativo/clientes/cadastro");
        mv.addObject("cliente", cliente);
        return mv;
    }

    @GetMapping("/administrativo/clientes/listar")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("administrativo/clientes/lista");
        mv.addObject("listaClientes",clienteRepositorio.findAll());
        String nome = "";
        mv.addObject("nome", nome);
        return mv;
    }

    @GetMapping("/administrativo/clientes/buscar/nome")
    public ModelAndView buscarNome(String nome) {
        ModelAndView mv = new ModelAndView("administrativo/clientes/busca");
        mv.addObject("listaClientes", clienteRepositorio.findAllByNome(nome));
        return mv;
    }

    @GetMapping("/administrativo/clientes/buscar/documento")
    public ModelAndView editar(String documento) {
        ModelAndView mv = new ModelAndView("administrativo/clientes/busca");
        mv.addObject("listaClientes", clienteRepositorio.findAllByDocumento(documento));
        return mv;
    }

    @GetMapping("/administrativo/clientes/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("administrativo/clientes/editar");
        Optional<Cliente> cliente = clienteRepositorio.findById(id);
        mv.addObject("cliente", cliente);
        return mv;
    }

    @GetMapping("/administrativo/clientes/remover/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        Optional<Cliente> cliente = clienteRepositorio.findById(id);
        clienteRepositorio.delete(cliente.get());     
        return listar();
    }

    @GetMapping("/administrativo/clientes/erro/{id}")
    public ModelAndView error(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("/administrativo/clientes/erro");
        Optional<Cliente> cliente = clienteRepositorio.findById(id);
        mv.addObject("cliente", cliente);
        return mv;
    }

    @PostMapping("administrativo/clientes/salvar")
    public ModelAndView salvar(@Validated Cliente cliente, BindingResult result) {

        listaClientes = clienteRepositorio.findAll();

        if(result.hasErrors()) {
            return cadastrar(cliente);
        }

        if (listaClientes.contains(cliente)) {
            Cliente cliente2 = clienteRepositorio.findByDocumento(cliente.getDocumento());
            return error(cliente2.getId());
        } else {
            clienteRepositorio.saveAndFlush(cliente);
            return cadastrar(new Cliente());
        }
    }
}
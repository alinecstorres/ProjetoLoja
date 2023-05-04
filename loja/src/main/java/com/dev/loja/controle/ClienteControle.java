package com.dev.loja.controle;

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
        return mv;
    }

    @GetMapping("/administrativo/clientes/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Cliente> cliente = clienteRepositorio.findById(id);
        return cadastrar(cliente.get());
    }

    @GetMapping("/administrativo/clientes/remover/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        Optional<Cliente> cliente = clienteRepositorio.findById(id);
        clienteRepositorio.delete(cliente.get());     
        return listar();
    }

    @PostMapping("administrativo/clientes/salvar")
    public ModelAndView salvar(@Validated Cliente cliente, BindingResult result) {
        if(result.hasErrors()) {
            return cadastrar(cliente);
        }
        clienteRepositorio.saveAndFlush(cliente);
        return cadastrar(new Cliente());
    }
}
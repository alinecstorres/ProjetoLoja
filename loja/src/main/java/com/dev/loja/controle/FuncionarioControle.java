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

import com.dev.loja.modelos.Funcionario;
import com.dev.loja.modelos.Role;
import com.dev.loja.repositorios.FuncionarioRepositorio;
import com.dev.loja.repositorios.RoleRepositorio;

@Controller
public class FuncionarioControle {

    @Autowired
    private FuncionarioRepositorio funcionarioRepositorio;

    @Autowired
    private RoleRepositorio roleRepositorio;

    @GetMapping("/administrativo/funcionarios/cadastrar")
    public ModelAndView cadastrar(Funcionario funcionario) {
        ModelAndView mv = new ModelAndView("administrativo/funcionarios/cadastro");
        mv.addObject("funcionario", funcionario);
        return mv;
    }

    @GetMapping("/administrativo/funcionarios/listar")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("administrativo/funcionarios/lista");
        mv.addObject("listaFuncionarios",funcionarioRepositorio.findAll());
        return mv;
    }

    @GetMapping("/administrativo/funcionarios/buscar/nome")
    public ModelAndView buscarNome(String nome) {
        ModelAndView mv = new ModelAndView("administrativo/funcionarios/busca");
        mv.addObject("listaFuncionarios", funcionarioRepositorio.findAllByNome(nome));
        return mv;
    }

    @GetMapping("/administrativo/funcionarios/buscar/cpf")
    public ModelAndView editar(String cpf) {
        ModelAndView mv = new ModelAndView("administrativo/funcionarios/busca");
        mv.addObject("listaFuncionarios", funcionarioRepositorio.findAllByCpf(cpf));
        return mv;
    }

    @GetMapping("/administrativo/funcionarios/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("administrativo/funcionarios/editar");
        Optional<Funcionario> funcionario = funcionarioRepositorio.findById(id);
        mv.addObject("funcionario", funcionario);
        return mv;
    }

    @GetMapping("/administrativo/funcionarios/remover/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        Optional<Funcionario> funcionario = funcionarioRepositorio.findById(id);
        funcionarioRepositorio.delete(funcionario.get());     
        return listar();
    }

    @PostMapping("administrativo/funcionarios/salvar")
    public ModelAndView salvar(@Validated Funcionario funcionario, BindingResult result) {
        if(result.hasErrors()) {
            return cadastrar(funcionario);
        }
        funcionarioRepositorio.saveAndFlush(funcionario);
        return cadastrar(new Funcionario());
    }

    @GetMapping("administrativo/funcionarios/permissoes")
    public ModelAndView listarPermissoes() {
        ModelAndView mv = new ModelAndView("administrativo/funcionarios/permissoes");
        
        List<Funcionario> listaFuncionarios = funcionarioRepositorio.findAll();
        List<Role> listaRoles = roleRepositorio.findAll();
        
        mv.addObject("listaRoles", listaRoles);
        mv.addObject("listaFuncionarios", listaFuncionarios);
        return mv;
    }


    @PostMapping("administrativo/funcionarios/permissoes/salvar")
    public ModelAndView atualizarPermissoes(String acao, Long funcionarioId, Long roleId) {

        Funcionario funcionario = funcionarioRepositorio.findById(funcionarioId).get();
        List<Role> listaRoles = funcionario.getRoles();
        Role role = roleRepositorio.findById(roleId).get();

        if (!listaRoles.contains(role)) {
            listaRoles.add(role);
            funcionario.setRoles(listaRoles);
            funcionarioRepositorio.saveAndFlush(funcionario);
        }
        
        return listarPermissoes();
    }

    @GetMapping("/administrativo/funcionarios/permissoes/editar/{id}")
    public ModelAndView excluirPermissoes(@PathVariable("id") Long id) {
        Funcionario funcionario = funcionarioRepositorio.findById(id).get();
        funcionario.getRoles().clear();
        funcionarioRepositorio.saveAndFlush(funcionario);
        return listarPermissoes();
    }

}
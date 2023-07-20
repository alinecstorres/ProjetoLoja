package com.dev.loja.controle;

import java.util.ArrayList;
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

import com.dev.loja.modelos.Role;
import com.dev.loja.repositorios.RoleRepositorio;

@Controller
public class RoleControle {

    @Autowired
    private RoleRepositorio roleRepositorio;

    List<Role> listaRoles = new ArrayList<>();

    @GetMapping("/administrativo/roles/cadastrar")
    public ModelAndView cadastrar(Role role) {
        ModelAndView mv = new ModelAndView("administrativo/roles/cadastro");
        mv.addObject("listaRoles", roleRepositorio.findAll());
        mv.addObject("role", role);
        return mv;
    }

    @GetMapping("/administrativo/roles/listar")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("administrativo/roles/lista");
        mv.addObject("listaRoles",roleRepositorio.findAll());
        return mv;
    }

    @GetMapping("/administrativo/roles/remover/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        Optional<Role> role = roleRepositorio.findById(id);
        roleRepositorio.delete(role.get());     
        return listar();
    }

    @PostMapping("administrativo/roles/salvar")
    public ModelAndView salvar(@Validated Role role, String acao, BindingResult result) {

        listaRoles = roleRepositorio.findAll();

        if (acao.equals("salvar")) {
            if (!listaRoles.contains(role)) {
                roleRepositorio.saveAndFlush(role);
            }

        } else {
            for (Role role1 : listaRoles) {
                if (role1.getRoleId().equals(Long.parseLong(acao))) {
                    roleRepositorio.deleteById(Long.parseLong(acao));
                }
            }
        }
        return cadastrar(new Role());
    }
    
}

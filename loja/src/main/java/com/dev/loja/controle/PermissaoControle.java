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

import com.dev.loja.modelos.Permissao;
import com.dev.loja.repositorios.FuncionarioRepositorio;
import com.dev.loja.repositorios.PermissaoRepositorio;
import com.dev.loja.repositorios.RoleRepositorio;

@Controller
public class PermissaoControle {

    @Autowired
    private PermissaoRepositorio permissaoRepositorio;

    @Autowired 
    private FuncionarioRepositorio funcionarioRepositorio;

    @Autowired 
    private RoleRepositorio roleRepositorio;

    List<Permissao> listaPermissoes = new ArrayList<Permissao>();

    @GetMapping("/administrativo/permissoes/cadastrar")
    public ModelAndView cadastrar(Permissao permissao) {
        ModelAndView mv = new ModelAndView("administrativo/permissoes/cadastro");
        mv.addObject("permissao", permissao);
        mv.addObject("listaFuncionarios", funcionarioRepositorio.findAll());
        mv.addObject("listaRoles", roleRepositorio.findAll());
        mv.addObject("listaPermissoes", permissaoRepositorio.findAll());
        return mv;
    }

    @GetMapping("/administrativo/permissoes/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Permissao> permissao = permissaoRepositorio.findById(id);
        return cadastrar(permissao.get());
    }

    @PostMapping("/administrativo/permissoes/salvar")
    public ModelAndView salvar(@Validated Permissao permissao, String acao, BindingResult result) {

        listaPermissoes = permissaoRepositorio.findAll();

        if (result.hasErrors()) {
            return cadastrar(permissao);
        }

        if (acao.equals("salvar")) {
            if (!listaPermissoes.contains(permissao)) {
                permissaoRepositorio.saveAndFlush(permissao);
            }

        } else {
            for (Permissao permissao1 : listaPermissoes) {
                if (permissao1.getId().equals(Long.parseLong(acao))) {
                    permissaoRepositorio.deleteById(Long.parseLong(acao));
                }
            }
        }

        return cadastrar(new Permissao());
    }
}

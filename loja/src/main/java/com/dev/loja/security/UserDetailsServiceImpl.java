package com.dev.loja.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dev.loja.modelos.Funcionario;
import com.dev.loja.repositorios.FuncionarioRepositorio;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    final FuncionarioRepositorio funcionarioRepositorio;

    public UserDetailsServiceImpl(FuncionarioRepositorio funcionarioRepositorio) {
        this.funcionarioRepositorio = funcionarioRepositorio;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Funcionario funcionario = funcionarioRepositorio.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + username));

        return new User(funcionario.getUsername(), funcionario.getPassword(), true, true, true, true, funcionario.getAuthorities());
    }
    
}

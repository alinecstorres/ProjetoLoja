package com.dev.loja.controle;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dev.loja.modelos.Produto;
import com.dev.loja.repositorios.ProdutoRepositorio;

@Controller
public class ProdutoControle {

    private static String caminhoImagens = "C:/Users/aline/Workspace/Meus projetos/loja/loja/src/main/resources/static/image/";

    @Autowired
    private ProdutoRepositorio produtoRepositorio;

    private List<Produto> listaProdutos;

    @GetMapping("/administrativo/produtos/cadastrar")
    public ModelAndView cadastrar(Produto produto) {
        ModelAndView mv = new ModelAndView("administrativo/produtos/cadastro");
        mv.addObject("produto", produto);
        return mv;
    }

    @GetMapping("/administrativo/produtos/listar")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("administrativo/produtos/lista");
        mv.addObject("listaProdutos",produtoRepositorio.findAll());
        return mv;
    }

    @GetMapping("/administrativo/produtos/buscar/nome")
    public ModelAndView buscarNome(String nome) {
        ModelAndView mv = new ModelAndView("administrativo/produtos/busca");
        mv.addObject("listaProdutos",produtoRepositorio.findAllByNomeProduto(nome));
        return mv;
    }

    @GetMapping("/administrativo/produtos/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("administrativo/produtos/editar");
        Optional<Produto> produto = produtoRepositorio.findById(id);
        mv.addObject("produto", produto);
        return mv;
    }

    @GetMapping("/administrativo/produtos/remover/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        Optional<Produto> produto = produtoRepositorio.findById(id);
        produtoRepositorio.delete(produto.get());     
        return listar();
    }

    @GetMapping("/administrativo/produtos/mostrarImagem/{imagem}")
    @ResponseBody
    public byte[] retornarImagem(@PathVariable("imagem") String imagem) throws IOException {
        File imagemArquivo = new File(caminhoImagens+imagem);  
        
        if (imagem != null && imagem.trim().length()>0) { 
            return Files.readAllBytes(imagemArquivo.toPath());
        }
        
        return null;
    }

    @PostMapping("administrativo/produtos/salvar")
    public ModelAndView salvar(@RequestPart String nomeProduto, String tamanhoProduto, Double valorProduto, Double alturaProduto, Double larguraProduto, Double profundidadeProduto, Double pesoProduto, BindingResult result, MultipartFile arquivo) {
        
        listaProdutos = produtoRepositorio.findAll();
        String nomeCompleto = nomeProduto.concat(" - ").concat(tamanhoProduto);

        Produto produto = new Produto();
        produto.setNomeProduto(nomeProduto);
        produto.setAlturaProduto(alturaProduto);
        produto.setLarguraProduto(larguraProduto);
        produto.setPesoProduto(pesoProduto);
        produto.setProfundidadeProduto(profundidadeProduto);
        produto.setTamanhoProduto(tamanhoProduto);
        produto.setValorProduto(valorProduto);
        produto.setNomeCompletoProduto(nomeCompleto);

        if(result.hasErrors()) {
            return listar();
        }

        if (listaProdutos.isEmpty()) {
            
            produtoRepositorio.save(produto);

            try {
                if (!arquivo.isEmpty()) {
    
                    byte[] bytes = arquivo.getBytes();
                    Path caminho = Paths.get(caminhoImagens+String.valueOf(produto.getId())+arquivo.getOriginalFilename());
                    Files.write(caminho, bytes);
                    produto.setNomeImagem(String.valueOf(produto.getId())+arquivo.getOriginalFilename());
                
                    produtoRepositorio.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        } else {

            if (listaProdutos.contains(produto)) {     
                
                Produto produto2 = produtoRepositorio.findByNomeCompletoProduto(nomeCompleto);
                
                produto2.setAlturaProduto(alturaProduto);
                produto2.setLarguraProduto(larguraProduto);
                produto2.setPesoProduto(pesoProduto);
                produto2.setProfundidadeProduto(profundidadeProduto);
                produto2.setValorProduto(valorProduto);
            

                try {
                    if (!arquivo.isEmpty()) {
        
                        byte[] bytes = arquivo.getBytes();
                        Path caminho = Paths.get(caminhoImagens+String.valueOf(produto2.getId())+arquivo.getOriginalFilename());
                        Files.write(caminho, bytes);
        
                        produto2.setNomeImagem(String.valueOf(produto2.getId())+arquivo.getOriginalFilename());
                    
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
    
            } else {

                produtoRepositorio.save(produto);

                try {
                    if (!arquivo.isEmpty()) {
        
                        byte[] bytes = arquivo.getBytes();
                        Path caminho = Paths.get(caminhoImagens+String.valueOf(produto.getId())+arquivo.getOriginalFilename());
                        Files.write(caminho, bytes);
                        produto.setNomeImagem(String.valueOf(produto.getId())+arquivo.getOriginalFilename());
                    
                        produtoRepositorio.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
        }

        return listar();
    }

}
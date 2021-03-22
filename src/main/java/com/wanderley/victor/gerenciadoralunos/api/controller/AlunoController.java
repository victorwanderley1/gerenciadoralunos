package com.wanderley.victor.gerenciadoralunos.api.controller;

import com.wanderley.victor.gerenciadoralunos.domain.model.Aluno;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Victor Wanderley
 */
@RestController
@RequestMapping("/alunos")
public class AlunoController {
    private List<Aluno> alunos;
    
    public AlunoController(){
        this.alunos = new ArrayList();
        
    }
    
    // Não consegui que o swagger aceitasse que o valor da variável idade voltasse a ser null 
    //após ser utilizado algum elemento nela.
    @GetMapping
    public List<Aluno> listar(@RequestParam(required = false) String nome,
            @RequestParam(value = "idade", required = false, defaultValue = "0") Integer idade){
        if (idade == 0) idade = null;
        if ((nome != null) && (idade == null)){
            return this.filtrarNome(nome);
        }
        else if ((nome == null) && (idade!= null)){
            return this.filtrardade(idade);
        }
        else if ((nome != null) && (idade!= null)){
            return this.filtrarNomeEIdade(nome, idade);
        }
        return this.alunos;
    }
    
    private List<Aluno> filtrardade(Integer idade){
        return this.alunos.stream()
                .filter(aluno -> aluno.getIdade().equals(idade))
                .collect(Collectors.toList());
    }
    
    private List<Aluno> filtrarNome(String nome){
        return this.alunos.stream()
                .filter(aluno -> aluno.getNome().contains(nome))
                .collect(Collectors.toList());
    }
    
    private List<Aluno> filtrarNomeEIdade(String nome, Integer idade){
        return this.filtrarNome(nome).stream()
                .filter(aluno -> aluno.getIdade().equals(idade))
                .collect(Collectors.toList());
    }
    
    @GetMapping("/{id}")
    public Aluno buscar(@PathVariable Long id){
       if(idMenorQueLista(id)){
           return this.alunos.get(id.intValue()-1);
       }
       return null;
    }
    
    private Boolean idMenorQueLista(Long id){
        return ((this.alunos.size() >= id) && (id>0));
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Boolean cadastrar(@RequestBody Aluno aluno){
        aluno.setId((long) this.alunos.size()+1);
        return this.alunos.add(aluno);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Boolean deletar(@PathVariable Long id){
        if(idMenorQueLista(id)){
            this.alunos.remove(id.intValue()-1);
            return true;
        }
        return false;
    }
    
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable Long id,
            @RequestParam(required = false) String nome, Integer idade){
        if(idMenorQueLista(id)){
            atualizaNome(id, nome);
            atualizaIdade(id, idade);
        }
    }
    
    private Boolean atualizaNome(Long id, String nome){
        if (nome != null){
            this.alunos.get(id.intValue()-1).setNome(nome);
            return true;
        }
        return false;
    }
    
    private Boolean atualizaIdade(Long id, Integer idade){
        if (idade != null){
            this.alunos.get(id.intValue()-1).setIdade(idade);
            return true;
        }
        return false;
    }
    
}

package com.wanderley.victor.gerenciadoralunos.api.controller;

import com.wanderley.victor.gerenciadoralunos.domain.model.Aluno;
import com.wanderley.victor.gerenciadoralunos.exceptions.AlunoNaoEncontradoException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class AlunoService {
    
    private List<Aluno> alunos;
    
    public AlunoService(){
        this.alunos = new ArrayList();
    }
    //Métodos de buscas
    public List<Aluno> listar(String nome, Integer idade){
        //Retornar ao método pra melhorar a aparencia
        List<Aluno> alunosFiltrados;
        
        if ((nome != null) && (idade == null)){
            alunosFiltrados = this.listarAlunos(nome);
        }
        else if ((nome == null) && (idade!= null)){
            alunosFiltrados = this.listarAlunos(idade);
        }
        else if ((nome != null) && (idade!= null)){
            alunosFiltrados = this.listarAlunos(nome, idade);
        }else{
            alunosFiltrados = this.alunos;
        }
        if(alunosFiltrados.isEmpty()){
            throw new AlunoNaoEncontradoException();
        }else{
            return alunosFiltrados;
        }
    }
    
    public List<Aluno> listarAlunos(){
        return this.alunos;
    }
    
    public List<Aluno> listarAlunos(final String nome){
        return filtrarNomeAluno(nome);
    }
    
    public List<Aluno> listarAlunos(final Integer idade){
        return filtrarIdadeAluno(idade);
    }
    
    public List<Aluno> listarAlunos(final String nome, final Integer idade){
        return filtrarNomeIdadeAluno(nome, idade);
    }
    
    private List<Aluno> filtrarNomeAluno(final String nome){
        return this.alunos.stream().filter(aluno -> aluno.getNome().contains(nome))
                .collect(Collectors.toList());
    }
    
    private List<Aluno> filtrarIdadeAluno(final Integer idade){
        return this.alunos.stream().filter(aluno -> aluno.getIdade().equals(idade))
                .collect(Collectors.toList());
    }
    
    private List<Aluno> filtrarNomeIdadeAluno(final String nome, final Integer idade){
        return this.alunos.stream().filter(aluno -> aluno.getNome().contains(nome))
                .filter(aluno -> aluno.getIdade().equals(idade))
                .collect(Collectors.toList());
    }
    
    public Aluno buscar(final Long id){
       if(idDentroDaLista(id)){
           return this.alunos.get(id.intValue()-1);
       }else{
           throw new AlunoNaoEncontradoException();
       }
    }
    
    private Boolean idDentroDaLista(Long id){
        return ((this.alunos.size() >= id) && (id>0));
    }
    
    // Métodos de inserção
    
    public Boolean cadastrar(Aluno aluno){
        aluno.setId((long) this.alunos.size()+1);
        return this.alunos.add(aluno);
    }
    
    public Boolean deletar(Long id){
        if(idDentroDaLista(id)){
            this.alunos.remove(id.intValue()-1);
            return true;
        }
        return false;
    }
    
    public void atualizar(Long id,
            String nome, Integer idade){
        if(idDentroDaLista(id)){
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


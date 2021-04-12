package com.wanderley.victor.gerenciadoralunos.api.controller;

import com.wanderley.victor.gerenciadoralunos.domain.model.Aluno;
import com.wanderley.victor.gerenciadoralunos.exceptions.AlunoNaoEncontradoException;
import com.wanderley.victor.gerenciadoralunos.exceptions.FalhaNoCadastroException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AlunoService {
    Logger LOGGER = LoggerFactory.getLogger(AlunoService.class); 
    private List<Aluno> listAlunos;
    
    public AlunoService(){
        this.listAlunos = new ArrayList();
    }
    //Métodos de buscas
    public List<Aluno> listar(String nome, Integer idade){
        //Retornar ao método pra melhorar a aparencia
        List<Aluno> alunosFiltrados = new ArrayList();
        
        if ((nome != null) && (idade == 0)){
            alunosFiltrados.addAll(this.listarAlunos(nome));
        }
        else if ((nome == null) && (idade!= 0)){
            alunosFiltrados.addAll(this.listarAlunos(idade));
        }
        else if ((nome != null) && (idade!= 0)){
            alunosFiltrados.addAll(this.listarAlunos(nome, idade));
        }else{
            alunosFiltrados.addAll(this.listAlunos);
        }
        if(alunosFiltrados.isEmpty()){
            throw new AlunoNaoEncontradoException();
        }else{
            return alunosFiltrados;
        }
    }
    
    public List<Aluno> listarAlunos(){
        return this.listAlunos;
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
        return this.listAlunos.stream().filter(aluno -> aluno.getNome().contains(nome))
                .collect(Collectors.toList());
    }
    
    private List<Aluno> filtrarIdadeAluno(final Integer idade){
        return this.listAlunos.stream().filter(aluno -> aluno.getIdade().equals(idade))
                .collect(Collectors.toList());
    }
    
    private List<Aluno> filtrarNomeIdadeAluno(final String nome, final Integer idade){
        return this.listAlunos.stream().filter(aluno -> aluno.getNome().contains(nome))
                .filter(aluno -> aluno.getIdade().equals(idade))
                .collect(Collectors.toList());
    }
    
    public Aluno buscar(final Long id){
        idDentroDaLista(id);
        return this.listAlunos.get(id.intValue()-1);
    }
    
    private Boolean idDentroDaLista(Long id){
        if((this.listAlunos.size() >= id) && (id>0)) return true;
        else throw new AlunoNaoEncontradoException();
    }
    
    // Métodos de inserção e modificação
    
    public Aluno cadastrar(final Aluno aluno){
        aluno.setId((long) this.listAlunos.size()+1);
        try{
            isAlunoNull(aluno);
            this.listAlunos.add(aluno);
            return aluno;
            
        }catch (NullPointerException e){
            throw new FalhaNoCadastroException();
        }
    }
    
    private void isAlunoNull(final Aluno aluno){
        if ((aluno.getIdade().equals(null)) || (aluno.getIdade().equals(0)) 
                || (aluno.getNome().isBlank()) || (aluno.getNome().equals(null))
                || (aluno.getNome().equals(""))) throw new NullPointerException();
    }
    
    public Boolean deletar(Long id){
        if(idDentroDaLista(id)){
            this.listAlunos.remove(id.intValue()-1);
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
            this.listAlunos.get(id.intValue()-1).setNome(nome);
            return true;
        }
        return false;
    }
    
    private Boolean atualizaIdade(Long id, Integer idade){
        if (idade != null){
            this.listAlunos.get(id.intValue()-1).setIdade(idade);
            return true;
        }
        return false;
    }
}


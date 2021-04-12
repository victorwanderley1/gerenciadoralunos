package com.wanderley.victor.gerenciadoralunos.api.controller;

import com.wanderley.victor.gerenciadoralunos.domain.model.Aluno;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Victor Wanderley
 */
@RestController
@RequestMapping("/alunos")
public class AlunoController {
    private AlunoService alunoService;
    
    public AlunoController(){
        this.alunoService = new AlunoService();
        
    }
    
    @GetMapping
    public ResponseEntity<List<Aluno>> listar(@RequestParam(required = false) String nome,
            @RequestParam(value = "idade", required = false, defaultValue = "0") Integer idade){
        return new ResponseEntity(this.alunoService.listar(nome, idade), HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscar(@PathVariable Long id){
       return new ResponseEntity(this.alunoService.buscar(id), HttpStatus.FOUND);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Aluno cadastrar(@RequestBody Aluno aluno){
        return this.alunoService.cadastrar(aluno);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Boolean deletar(@PathVariable Long id){
        return this.alunoService.deletar(id);
    }
    
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable Long id,
            @RequestParam(required = false) String nome, Integer idade){
        this.alunoService.atualizar(id, nome, idade);
    }
}

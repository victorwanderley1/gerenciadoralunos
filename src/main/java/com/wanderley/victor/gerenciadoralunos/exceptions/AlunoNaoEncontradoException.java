/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wanderley.victor.gerenciadoralunos.exceptions;

/**
 *
 * @author Victor Wanderley
 */
public class AlunoNaoEncontradoException extends RuntimeException{
    public AlunoNaoEncontradoException(){
        super("Aluno não encontrado!");
    }
}

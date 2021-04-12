/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wanderley.victor.gerenciadoralunos.api.controller;

import com.wanderley.victor.gerenciadoralunos.exceptions.AlunoNaoEncontradoException;
import com.wanderley.victor.gerenciadoralunos.exceptions.FalhaNoCadastroException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author Victor Wanderley
 */

@ControllerAdvice
public class AlunoControllerAdvice {
    
    final static Logger LOGGER = LoggerFactory.getLogger(AlunoControllerAdvice.class);
    
    @ExceptionHandler(AlunoNaoEncontradoException.class)
    public ResponseEntity<String> alunoNaoEncontradoHandle(final AlunoNaoEncontradoException e){
        LOGGER.error(e.getMessage());
        return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(FalhaNoCadastroException.class)
    public ResponseEntity<String> falhaNoCadastroHandle(final FalhaNoCadastroException e){
        LOGGER.error("Cadastro com erro");
        return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

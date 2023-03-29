package com.example.application.repositories;

import java.util.List;

import com.example.application.entidades.Aluno;

public interface AlunoRepository {
    public void inserir(Aluno aluno);
    public void editar(Aluno aluno);
    public void remover(int id);
    public List<Aluno> listar();
}

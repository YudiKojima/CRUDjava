package com.example.application.repositories;

import java.util.List;

import com.example.application.entidades.Curso;

public interface CursoRepository {
    public void inserir(Curso curso);
    public void editar(Curso curso);
    public void remover(int id);
    public List<Curso> listar();
}

package com.example.application.repositories;

import java.util.List;

import com.example.application.entidades.Disciplina;

public interface DisciplinaRepository {
    public void inserir(Disciplina disciplina);
    public void editar(Disciplina disciplina);
    public void remover(int id);
    public List<Disciplina> listar();
}

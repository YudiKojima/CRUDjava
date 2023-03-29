package com.example.application.repositories;

import java.util.List;

import com.example.application.entidades.Professor;

public interface ProfessorRepository {
    public void inserir(Professor professor);
    public void editar(Professor professor);
    public void remover(int id);
    public List<Professor> listar();
}

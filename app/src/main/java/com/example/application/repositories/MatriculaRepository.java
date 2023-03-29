package com.example.application.repositories;

import java.util.List;

import com.example.application.entidades.Matricula;

public interface MatriculaRepository {
    public void inserir(Matricula matricula);
    public void editar(Matricula matricula);
    public void remover(int id);
    public List<Matricula> listar();
}

package com.example.application.repositories;

import java.util.List;

import com.example.application.entidades.FeedBack;

public interface FeedBackRepository {
    public void inserir(FeedBack feedBack);
    public void editar(FeedBack feedBack);
    public void remover(int id);
    public List<FeedBack> listar();
}

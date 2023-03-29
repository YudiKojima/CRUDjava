package com.example.application.repositories.postgres;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.example.application.entidades.Curso;
import com.example.application.repositories.CursoRepository;

public class CursoRepositoryImpl 
                    implements CursoRepository{

    public void inserir(Curso curso){
        Connection con;
        try {
            con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "123");
            String sql = "INSERT INTO curso (nome, idcurso) VALUES (?,?);";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, curso.getNome());
            pstm.setInt(2, curso.getIdcurso());
            pstm.execute();
            con.close();
        } catch(Exception erro){
            throw new RuntimeException(erro);
        }
       
    }


    public List<Curso> listar(){
        List<Curso> lista = new ArrayList<>();
        Connection con;
        try {
            con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "123");
            String sql = "SELECT * FROM curso;";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                Curso d = new Curso();
                d.setNome(rs.getString("nome"));
                d.setId(rs.getInt("id"));
                d.setIdcurso(rs.getInt("idcurso"));
                lista.add(d);
            }
            con.close();
        } catch(Exception erro){
            throw new RuntimeException(erro);
        }
        return lista;
    }

    public void remover(int id){
        Connection con;
        try {
            con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "123");
            String sql = "DELETE FROM curso where id=?;";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.execute();
            con.close();
        } catch(Exception erro){
            throw new RuntimeException(erro);
        }
    }

    public void editar(Curso curso){
        Connection con;
        try {
            con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "123");
            String sql = "UPDATE curso SET nome=?, idcurso=? WHERE id=?;";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, curso.getNome());
            pstm.setInt(2, curso.getIdcurso());
            pstm.setInt(3, curso.getId());
            pstm.execute();
            con.close();
        } catch(Exception erro){
            throw new RuntimeException(erro);
        }
    }
}

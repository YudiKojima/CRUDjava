package com.example.application.repositories.postgres;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.example.application.entidades.Disciplina;
import com.example.application.repositories.DisciplinaRepository;

public class DisciplinaRepositoryImpl 
                    implements DisciplinaRepository{

    public void inserir(Disciplina disciplina){
        Connection con;
        try {
            con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "123");
            String sql = "INSERT INTO disciplina (nome, carga_horaria, idcurso) VALUES (?,?,?);";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, disciplina.getNome());
            pstm.setInt(2, disciplina.getCargaHoraria());
            pstm.setInt(3, disciplina.getIdcurso());
            pstm.execute();
            con.close();
        } catch(Exception erro){
            throw new RuntimeException(erro);
        }
       
    }


    public List<Disciplina> listar(){
        List<Disciplina> lista = new ArrayList<>();
        Connection con;
        try {
            con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "123");
            String sql = "SELECT * FROM disciplina;";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                Disciplina d = new Disciplina();
                d.setNome(rs.getString("nome"));
                d.setId(rs.getInt("id"));
                d.setCargaHoraria(rs.getInt("carga_horaria"));
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
            String sql = "DELETE FROM disciplina where id=?;";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.execute();
            con.close();
        } catch(Exception erro){
            throw new RuntimeException(erro);
        }
    }

    public void editar(Disciplina disciplina){
        Connection con;
        try {
            con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "123");
            String sql = "UPDATE disciplina SET nome=?, carga_horaria=?, idcurso=? WHERE id=?;";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, disciplina.getNome());
            pstm.setInt(2, disciplina.getCargaHoraria());
            pstm.setInt(3, disciplina.getIdcurso());
            pstm.setInt(4, disciplina.getId());
            pstm.execute();
            con.close();
        } catch(Exception erro){
            throw new RuntimeException(erro);
        }
    }
}

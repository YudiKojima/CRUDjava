package com.example.application.repositories.postgres;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.example.application.entidades.Matricula  ;
import com.example.application.repositories.MatriculaRepository;

public class MatriculaRepositoryImpl 
                    implements MatriculaRepository{

    public void inserir(Matricula matricula){
        Connection con;
        try {
            con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "123");
            String sql = "INSERT INTO matricula (cpf, idaluno, cep) VALUES (?,?,?);";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, matricula.getCpf());
            pstm.setInt(2, matricula.getIdaluno());
            pstm.setInt(3, matricula.getCep());
            pstm.execute();
            con.close();
        } catch(Exception erro){
            throw new RuntimeException(erro);
        }
       
    }


    public List<Matricula> listar(){
        List<Matricula> lista = new ArrayList<>();
        Connection con;
        try {
            con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "123");
            String sql = "SELECT * FROM matricula;";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                Matricula d = new Matricula();
                d.setCpf(rs.getInt("cpf"));
                d.setId(rs.getInt("id"));
                d.setIdaluno(rs.getInt("idaluno"));
                d.setCep(rs.getInt("cep"));
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
            String sql = "DELETE FROM matricula where id=?;";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.execute();
            con.close();
        } catch(Exception erro){
            throw new RuntimeException(erro);
        }
    }

    public void editar(Matricula matricula){
        Connection con;
        try {
            con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "123");
            String sql = "UPDATE matricula SET cpf=?, idaluno=?, cep=? WHERE id=?;";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1,matricula.getCpf());
            pstm.setInt(2, matricula.getIdaluno());
            pstm.setInt(3, matricula.getCep());
            pstm.setInt(4, matricula.getId());
            pstm.execute();
            con.close();
        } catch(Exception erro){
            throw new RuntimeException(erro);
        }
    }
}

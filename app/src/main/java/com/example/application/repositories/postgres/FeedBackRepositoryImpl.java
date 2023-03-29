package com.example.application.repositories.postgres;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.example.application.entidades.FeedBack;
import com.example.application.repositories.FeedBackRepository;

public class FeedBackRepositoryImpl 
                    implements FeedBackRepository{

    public void inserir(FeedBack feedBack){
        Connection con;
        try {
            con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "123");
            String sql = "INSERT INTO feedback (nota, comentario) VALUES (?,?);";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, feedBack.getNota());
            pstm.setString(2, feedBack.getComentario());
            pstm.execute();
            con.close();
        } catch(Exception erro){
            throw new RuntimeException(erro);
        }
       
    }


    public List<FeedBack> listar(){
        List<FeedBack> lista = new ArrayList<>();
        Connection con;
        try {
            con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "123");
            String sql = "SELECT * FROM feedback;";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                FeedBack d = new FeedBack();
                d.setNota(rs.getInt("nota"));
                d.setId(rs.getInt("id"));
                d.setComentario(rs.getString("comentario"));
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
            String sql = "DELETE FROM feedback where id=?;";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.execute();
            con.close();
        } catch(Exception erro){
            throw new RuntimeException(erro);
        }
    }

    public void editar(FeedBack feedBack){
        Connection con;
        try {
            con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "123");
            String sql = "UPDATE feedback SET nota=?, comentario=? WHERE id=?;";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, feedBack.getNota());
            pstm.setString(2, feedBack.getComentario());
            pstm.setInt(3, feedBack.getId());
            pstm.execute();
            con.close();
        } catch(Exception erro){
            throw new RuntimeException(erro);
        }
    }
}

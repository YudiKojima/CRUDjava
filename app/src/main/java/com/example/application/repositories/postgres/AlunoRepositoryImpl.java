package com.example.application.repositories.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.application.entidades.Aluno;
import com.example.application.repositories.AlunoRepository;

public class AlunoRepositoryImpl implements AlunoRepository{

    public void inserir(Aluno aluno){
        Connection con;
        try {
            con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "123");
            String sql = "INSERT INTO aluno (nome, datadenascimento, telefone, email) VALUES (?,?,?,?);";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, aluno.getNome());
            pstm.setString(2, aluno.getDatadenascimento());
            pstm.setString(3, aluno.getTelefone());
            pstm.setString(4, aluno.getEmail());
            pstm.execute();
            con.close();
        } catch(Exception erro){
            throw new RuntimeException(erro);
        }
       
    }


    public List<Aluno> listar(){
        List<Aluno> lista = new ArrayList<>();
        Connection con;
        try {
            con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "123");
            String sql = "SELECT * FROM aluno;";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                Aluno d = new Aluno();
                d.setNome(rs.getString("nome"));
                d.setId(rs.getInt("id"));
                d.setDatadenascimento(rs.getString("DataDeNascimento"));
                d.setTelefone(rs.getString("telefone"));
                d.setEmail(rs.getString("email"));
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
            String sql = "DELETE FROM aluno where id=?;";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.execute();
            con.close();
        } catch(Exception erro){
            throw new RuntimeException(erro);
        }
    }

    public void editar(Aluno aluno){
        Connection con;
        try {
            con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "123");
            String sql = "UPDATE aluno SET nome=?, datadenascimento=?, telefone=?, email=? WHERE id=?;";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, aluno.getNome());
            pstm.setString(2, aluno.getDatadenascimento());
            pstm.setString(3, aluno.getTelefone());
            pstm.setString(4, aluno.getEmail());
            pstm.setInt(5, aluno.getId());
            pstm.execute();
            con.close();
        } catch(Exception erro){
            throw new RuntimeException(erro);
        }
    }
}

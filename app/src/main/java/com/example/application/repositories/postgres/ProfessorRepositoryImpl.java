package com.example.application.repositories.postgres;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.example.application.entidades.Professor  ;
import com.example.application.repositories.ProfessorRepository;

public class ProfessorRepositoryImpl 
                    implements ProfessorRepository{

    public void inserir(Professor professor){
        Connection con;
        try {
            con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "123");
            String sql = "INSERT INTO professor (nome, idprofessor, telefone, email) VALUES (?,?,?,?);";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, professor.getNome());
            pstm.setInt(2, professor.getIdprofessor());
            pstm.setString(3, professor.getTelefone());
            pstm.setString(4, professor.getEmail());
            pstm.execute();
            con.close();
        } catch(Exception erro){
            throw new RuntimeException(erro);
        }
       
    }


    public List<Professor> listar(){
        List<Professor> lista = new ArrayList<>();
        Connection con;
        try {
            con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "123");
            String sql = "SELECT * FROM professor;";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                Professor d = new Professor();
                d.setNome(rs.getString("nome"));
                d.setId(rs.getInt("id"));
                d.setIdprofessor(rs.getInt("idprofessor"));
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
            String sql = "DELETE FROM professor where id=?;";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.execute();
            con.close();
        } catch(Exception erro){
            throw new RuntimeException(erro);
        }
    }

    public void editar(Professor professor){
        Connection con;
        try {
            con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "123");
            String sql = "UPDATE professor SET nome=?, idprofessor=?, telefone=?, email=? WHERE id=?;";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, professor.getNome());
            pstm.setInt(2, professor.getIdprofessor());
            pstm.setString(3, professor.getTelefone());
            pstm.setString(4, professor.getEmail());
            pstm.setInt(5, professor.getId());
            pstm.execute();
            con.close();
        } catch(Exception erro){
            throw new RuntimeException(erro);
        }
    }
}

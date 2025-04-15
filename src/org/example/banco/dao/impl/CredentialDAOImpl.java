package org.example.banco.dao.impl;

import org.example.banco.dao.CredentialDAO;
import org.example.banco.enums.Permiso;
import org.example.banco.model.Credencial;
import org.example.banco.util.ConexionSQL;

import java.sql.*;
import java.util.Optional;

public class CredentialDAOImpl implements CredentialDAO {

    Connection connection;

    public CredentialDAOImpl() {
        connection = ConexionSQL.conectar();
    }

    @Override
    public void guardar(Credencial credencial) {
        // id_usuario, username, password, permiso
        String sql = "INSERT INTO credenciales(id_usuario,username,password,permiso) VALUES(?,?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, credencial.getIdUsuario());
            pstmt.setString(2, credencial.getUsername());
            pstmt.setString(3, credencial.getPassword());
            pstmt.setString(4, credencial.getPermiso().toString());
            pstmt.executeUpdate();
            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) {
                int idGenerado = keys.getInt(1);
                credencial.setId(idGenerado);
            }
        } catch (SQLException e) {
            System.out.println("Error insertando credencial: " + e.getMessage());
        }

    }

    @Override
    public Optional<Credencial> buscarPorUsuarioName(String usuarioName) {
        // id_usuario, username, password, permiso
        String sql = "SELECT id_usuario,username,password,permiso FROM credenciales WHERE username=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, usuarioName);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                Credencial credencial = new Credencial();
                credencial.setIdUsuario(resultSet.getInt("id_usuario"));
                credencial.setUsername(resultSet.getString("username"));
                credencial.setPassword(resultSet.getString("password"));
                credencial.setPermiso(Permiso.valueOf(resultSet.getString("permiso")));
                return Optional.of(credencial);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            System.out.println("Error buscar credencial: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Credencial> buscarPorLoginYPassword(String login, String password) {
        String sql = "SELECT id_credencial,id_usuario,username,password,permiso FROM credenciales WHERE username=? AND password=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, login);
            pstmt.setString(2, password);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                Credencial credencial = new Credencial();
                credencial.setId(resultSet.getInt("id_credencial"));
                credencial.setIdUsuario(resultSet.getInt("id_usuario"));
                credencial.setUsername(resultSet.getString("username"));
                credencial.setPassword(resultSet.getString("password"));
                credencial.setPermiso(Permiso.valueOf(resultSet.getString("permiso")));
                return Optional.of(credencial);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            System.out.println("Error buscar credencial por login y password: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Credencial> buscarPorIdUsuario(int idUsuario) {
        String sql = "SELECT id_credencial, id_usuario, username, password, permiso FROM credenciales WHERE id_usuario = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idUsuario);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                Credencial credencial = new Credencial();
                credencial.setId(resultSet.getInt("id_credencial"));
                credencial.setIdUsuario(resultSet.getInt("id_usuario"));
                credencial.setUsername(resultSet.getString("username"));
                credencial.setPassword(resultSet.getString("password"));
                credencial.setPermiso(Permiso.valueOf(resultSet.getString("permiso")));
                return Optional.of(credencial);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar credencial por id_usuario: " + e.getMessage());
            return Optional.empty();
        }
    }
}

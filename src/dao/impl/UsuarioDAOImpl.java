package org.example.banco.dao.impl;

import org.example.banco.dao.UsuarioDAO;
import org.example.banco.enums.Permiso;
import org.example.banco.model.Credencial;
import org.example.banco.model.Usuario;
import org.example.banco.util.ConexionSQL;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public class UsuarioDAOImpl implements UsuarioDAO {

    private Connection connection;

    public UsuarioDAOImpl() {
        connection = ConexionSQL.conectar();
    }

    @Override
    public void guardar(Usuario usuario) {
        String sql = "INSERT INTO usuarios(nombre,apellido,dni,email) VALUES(?,?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getApellido());
            pstmt.setString(3, usuario.getDni());
            pstmt.setString(4, usuario.getEmail());
            pstmt.executeUpdate();
            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) {
                int idGenerado = keys.getInt(1);
                usuario.setId(idGenerado);
            }
        } catch (SQLException e) {
            System.out.println("Error insertando usuario: " + e.getMessage());
        }
    }

    @Override
    public Optional<Usuario> buscarPorDni(String dni) {
        String sql = "SELECT nombre,apellido,dni,email FROM usuarios WHERE dni=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, dni);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setNombre(resultSet.getString("nombre"));
                usuario.setApellido(resultSet.getString("apellido"));
                usuario.setDni(resultSet.getString("dni"));
                usuario.setEmail(resultSet.getString("email"));
                return Optional.of(usuario);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            System.out.println("Error buscar usuario: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Usuario> buscarPorId(int id) {
        String sql = "SELECT id_usuario,nombre,apellido,dni,email FROM usuarios WHERE id_usuario=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(resultSet.getInt("id_usuario"));
                usuario.setNombre(resultSet.getString("nombre"));
                usuario.setApellido(resultSet.getString("apellido"));
                usuario.setDni(resultSet.getString("dni"));
                usuario.setEmail(resultSet.getString("email"));
                return Optional.of(usuario);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            System.out.println("Error buscar usuario: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Usuario> obtenerUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id_usuario,nombre,apellido,dni,email FROM usuarios";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setDni(rs.getString("dni"));
                u.setEmail(rs.getString("email"));
                usuarios.add(u);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener usuarios: " + e.getMessage());
        }
        return usuarios;
    }

    @Override
    public Optional<Usuario> buscarPorEmailByStream(String email) {
        List<Usuario> coleccion = obtenerUsuarios();
        return coleccion.stream()
                .filter(u->u.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public void guardarDatosActualizados(int idUsuario, Usuario usuarioEditable) {
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, email = ? WHERE id_usuario = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, usuarioEditable.getNombre());
            pstmt.setString(2, usuarioEditable.getApellido());
            pstmt.setString(3, usuarioEditable.getEmail());
            pstmt.setInt(4, idUsuario);
            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Datos actualizados con éxito.");
            } else {
                System.out.println("No se pudo actualizar los datos. Usuario no encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar datos del usuario: " + e.getMessage());
        }
    }


}




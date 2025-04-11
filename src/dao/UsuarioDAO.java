package org.example.banco.dao;

import org.example.banco.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioDAO {
    void guardar (Usuario usuario);
    Optional<Usuario> buscarPorDni(String dni);
    Optional<Usuario> buscarPorId(int id);
    List<Usuario> obtenerUsuarios();
    Optional<Usuario> buscarPorEmailByStream(String email);
    void guardarDatosActualizados(int idUsuarioActual, Usuario usuarioEditable);
}

package org.example.banco.dao;

import org.example.banco.model.Credencial;

import java.util.Optional;

public interface CredentialDAO {
    void guardar(Credencial credencial);
    Optional<Credencial> buscarPorUsuarioName(String usuarioName);
    Optional<Credencial> buscarPorLoginYPassword(String login, String password);
    Optional<Credencial> buscarPorIdUsuario(int idUsuario);
}

package org.example.banco.service;

import org.example.banco.dao.CredentialDAO;
import org.example.banco.dao.UsuarioDAO;
import org.example.banco.model.Credencial;
import org.example.banco.model.Usuario;

import java.util.Optional;

public class LoginService {
    private CredentialDAO credencialDAO;
    private UsuarioDAO usuarioDAO;

    public LoginService(CredentialDAO credencialDAO, UsuarioDAO usuarioDAO) {
        this.credencialDAO = credencialDAO;
        this.usuarioDAO = usuarioDAO;
    }

    public Optional<Usuario> login(String login, String password){
        Optional<Credencial> credencial = credencialDAO.buscarPorLoginYPassword(login,password);
        if(credencial.isPresent()){
            int idUsuario = credencial.get().getIdUsuario();
            return usuarioDAO.buscarPorId(idUsuario);
        }
        return Optional.empty();
    }
}

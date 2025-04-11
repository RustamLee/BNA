package org.example.banco.service;

import org.example.banco.dao.CredentialDAO;
import org.example.banco.dao.CuentaDAO;
import org.example.banco.dao.UsuarioDAO;
import org.example.banco.enums.Permiso;
import org.example.banco.enums.TipoCuenta;
import org.example.banco.model.Credencial;
import org.example.banco.model.Cuenta;
import org.example.banco.model.Usuario;

import java.util.Optional;
import java.util.UUID;

public class RegistroService {
    private final UsuarioDAO usuarioDAO;
    private final CuentaDAO cuentaDAO;
    private final CredentialDAO credentialDAO;


    public RegistroService(UsuarioDAO usuarioDAO, CuentaDAO cuentaDAO, CredentialDAO credentialDAO) {
        this.usuarioDAO = usuarioDAO;
        this.cuentaDAO = cuentaDAO;
        this.credentialDAO = credentialDAO;
    }

    // nombre, apellido, dni, email
    public void registrarUsuario(String nombre, String apellido, String dni, String email) {
        Optional<Usuario> usuario = usuarioDAO.buscarPorDni(dni);
        if (usuario.isPresent()) {
            System.out.println("Usuario con dni " + dni + " ya existe!");
            return;
        }
        Usuario newUsuario = new Usuario(nombre, apellido, dni, email);
        usuarioDAO.guardar(newUsuario);

        String login = generarLogin(nombre,apellido);
        String password = generarPassword();
        Credencial newCredencial = new Credencial(newUsuario.getId(),login, password, Permiso.CLIENTE);
        credentialDAO.guardar(newCredencial);

        Cuenta newCuenta = new Cuenta(newUsuario.getId(),TipoCuenta.CAJA_AHORRO, 0.0);
        cuentaDAO.guardar(newCuenta);
    }

    private String generarLogin(String nombre, String apellido){
        return (nombre.charAt(0)+apellido).toLowerCase();
    }

    private String generarPassword(){
        return UUID.randomUUID().toString().substring(0,4);
    }


}

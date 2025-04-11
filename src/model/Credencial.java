package org.example.banco.model;

import org.example.banco.enums.Permiso;

public class Credencial {
    private int id;
    private int idUsuario;
    private String username;
    private String password;
    private Permiso permiso;

    public Credencial(int idUsuario, String username, String password, Permiso permiso) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.password = password;
        this.permiso = permiso;
    }

    public Credencial(){};

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Permiso getPermiso() {
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }
}

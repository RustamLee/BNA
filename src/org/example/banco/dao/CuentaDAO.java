package org.example.banco.dao;

import org.example.banco.model.Cuenta;

import java.util.List;

public interface CuentaDAO {
    void guardar (Cuenta cuenta);
    List<Cuenta> obtenerCuentasPorUsuarioId(int usuarioId);
    boolean cajaDeAhorroYaExiste(int idUsuario);
}

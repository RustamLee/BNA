package org.example.banco.dao.impl;

import org.example.banco.dao.CuentaDAO;
import org.example.banco.enums.TipoCuenta;
import org.example.banco.model.Cuenta;
import org.example.banco.model.Usuario;
import org.example.banco.util.ConexionSQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CuentaDAOImpl implements CuentaDAO {

    Connection connection;

    public CuentaDAOImpl() {
        connection = ConexionSQL.conectar();
    }

    @Override
    public void guardar(Cuenta cuenta) {
        // id_usuario, tipo, saldo
        String sql = "INSERT INTO cuentas(id_usuario,tipo,saldo) VALUES(?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, cuenta.getIdUsuario());
            pstmt.setString(2, cuenta.getTipo().toString());
            pstmt.setDouble(3, cuenta.getSaldo());
            pstmt.executeUpdate();
            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) {
                int idGenerado = keys.getInt(1);
                cuenta.setId(idGenerado);
            }
        } catch (SQLException e) {
            System.out.println("Error insertando cuenta: " + e.getMessage());
        }
    }

    @Override
    public List<Cuenta> obtenerCuentasPorUsuarioId(int usuarioId) {
        String sql = "SELECT id_usuario,tipo,saldo FROM cuentas WHERE id_usuario=?";
        List<Cuenta> listaCuentas = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, usuarioId);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Cuenta cuenta = new Cuenta();
                cuenta.setIdUsuario(resultSet.getInt("id_usuario"));
                cuenta.setTipo(TipoCuenta.valueOf(resultSet.getString("tipo")));
                cuenta.setSaldo(resultSet.getDouble("saldo"));
                listaCuentas.add(cuenta);
            }
        } catch (SQLException e) {
            System.out.println("Error buscar cuentas: " + e.getMessage());
        }
        return listaCuentas;
    }

    @Override
    public boolean cajaDeAhorroYaExiste(int idUsuario) {
        String sql = "SELECT COUNT(*) FROM cuentas WHERE id_usuario=? AND tipo='CAJA_AHORRO'";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1,idUsuario);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next() && resultSet.getInt(1)>0;
        } catch (SQLException e){
            System.out.println("Error de chequear si caja de ahorro existe: " + e.getMessage());
            return true;
        }
    }

}

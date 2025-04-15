package org.example.banco.model;

import org.example.banco.enums.TipoCuenta;

import java.time.LocalDateTime;

public class Cuenta {
    private int id;
    private int idUsuario;
    private TipoCuenta tipo;
    private double saldo;
    private LocalDateTime fechaCreacion;

    public Cuenta() {
        this.fechaCreacion = LocalDateTime.now();
    }

    public Cuenta(int idUsuario, TipoCuenta tipo, double saldo) {
        this.idUsuario = idUsuario;
        this.tipo = tipo;
        this.saldo = saldo;
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

    public TipoCuenta getTipo() {
        return tipo;
    }

    public void setTipo(TipoCuenta tipo) {
        this.tipo = tipo;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}

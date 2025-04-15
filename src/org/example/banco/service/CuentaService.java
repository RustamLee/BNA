package org.example.banco.service;

import org.example.banco.dao.CuentaDAO;
import org.example.banco.enums.TipoCuenta;
import org.example.banco.model.Cuenta;

public class CuentaService {
    private final CuentaDAO cuentaDAO;

    public CuentaService(CuentaDAO cuentaDAO) {
        this.cuentaDAO = cuentaDAO;
    }

    public void crearCuentaCorriente(int idUsuario){
        Cuenta newCuenta = new Cuenta(idUsuario, TipoCuenta.CUENTA_CORRIENTE, 0.0);
        cuentaDAO.guardar(newCuenta);
    }
}

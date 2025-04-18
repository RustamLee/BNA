package org.example.banco.service;
/*package org.example.banco.org.example.banco.service;*/
import org.example.banco.dao.CredentialDAO;
import org.example.banco.dao.UsuarioDAO;
import org.example.banco.enums.Permiso;
import org.example.banco.model.Credencial;
import org.example.banco.model.Usuario;

import java.util.List;
import java.util.Optional;

public class UsuarioService {
    private UsuarioDAO usuarioDAO;
    private CredentialDAO credencialDAO;

    public UsuarioService(UsuarioDAO usuarioDAO, CredentialDAO credencialDAO) {
        this.usuarioDAO = usuarioDAO;
        this.credencialDAO = credencialDAO;
    }

    public List<Usuario> listarUsuarios(){
        return usuarioDAO.obtenerUsuarios();
    }

    public void actualizarDatos(int idUsuarioActual, Usuario usuarioEditable) {
        Optional<Credencial> credOpt = credencialDAO.buscarPorIdUsuario(idUsuarioActual);

        if (credOpt.isEmpty()) {
            System.out.println("Credencial no encontrada");
            return;
        }
        Permiso permiso = credOpt.get().getPermiso();

        // cliente
        if (permiso == Permiso.CLIENTE && idUsuarioActual != usuarioEditable.getId()) {
            System.out.println("CLIENTE, no dude en contactarnos");
            return;
        }

        // gestor
        if (permiso == Permiso.GESTOR) {
            Optional<Credencial> editableCred = credencialDAO.buscarPorIdUsuario(usuarioEditable.getId());
            if (editableCred.isEmpty() || editableCred.get().getPermiso() != Permiso.CLIENTE) {
                System.out.println("GESTOR solo puede editar clientes");
                return;
            }
        }
        // admin
        usuarioDAO.guardarDatosActualizados(usuarioEditable.getId(), usuarioEditable);
    }

    public Optional<Usuario> buscarPorDni(String dni) {
        return usuarioDAO.buscarPorDni(dni);
    }

    public void eliminarUsuarioPorId(int idUsuarioActual, Usuario usuarioEditable){
        Optional<Credencial> credOpt = credencialDAO.buscarPorIdUsuario(idUsuarioActual);
        int idUsuarioEditable = usuarioEditable.getId();

        if (credOpt.isEmpty()) {
            System.out.println("Credencial no encontrada");
            return;
        }
        Permiso permiso = credOpt.get().getPermiso();

        // gestor
        if (permiso == Permiso.GESTOR) {
            Optional<Credencial> editableCred = credencialDAO.buscarPorIdUsuario(idUsuarioEditable);
            if (editableCred.isEmpty() || editableCred.get().getPermiso() != Permiso.CLIENTE) {
                System.out.println("GESTOR puede eliminar solo clientes");
                return;
            }
        }
        // admin
        usuarioDAO.eliminarUsuario(idUsuarioEditable);
    }
}

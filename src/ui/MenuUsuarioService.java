package org.example.banco.ui;
import org.example.banco.model.Usuario;
import org.example.banco.service.UsuarioService;

import java.util.Optional;
import java.util.Scanner;

public class MenuUsuarioService {

    private final UsuarioService usuarioService;
    private final Scanner scanner;

    public MenuUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        this.scanner = new Scanner(System.in);
    }


    public void mostrarMenuActualizarDatos(int idUsuarioActual) {
        System.out.println("=== Modificacion los datos del usuario ===");

        System.out.print("Ingresa el DNI del usuario que deseas cambiar: ");
        String dni = scanner.nextLine();

        Optional<Usuario> usuarioOpt = usuarioService.buscarPorDni(dni);

        if (usuarioOpt.isEmpty()) {
            System.out.println("Usuario con este DNI no encontrado! ");
            return;
        }

        Usuario usuarioEditable = usuarioOpt.get();
        System.out.println("Los datos actual:");
        System.out.println("Nombre: " + usuarioEditable.getNombre());
        System.out.println("Apellido: " + usuarioEditable.getApellido());
        System.out.println("Email: " + usuarioEditable.getEmail());

        System.out.print("Ingrese nuevo nombre: ");
        String nuevoNombre = scanner.nextLine();

        System.out.print("Ingrese nuevo apellido: ");
        String nuevoApellido = scanner.nextLine();

        System.out.print("Ingrese nuevo email: ");
        String nuevoEmail = scanner.nextLine();

        usuarioEditable.setNombre(nuevoNombre);
        usuarioEditable.setApellido(nuevoApellido);
        usuarioEditable.setEmail(nuevoEmail);

        usuarioService.actualizarDatos(idUsuarioActual, usuarioEditable);
    }


}

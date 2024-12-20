package Modelo.Controlador;


import Modelo.DAO.DAOGenerico;
import Modelo.DTO.Usuario;

import java.time.LocalDate;
import java.util.List;

public class GestionUsuario {

    private DAOGenerico<Usuario, Integer> daoUsuario;

    public GestionUsuario() {
        this.daoUsuario = new DAOGenerico<>(Usuario.class);
    }

    public void verUsuarios() {
        List<Usuario> usuarios = daoUsuario.findAll();
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
        } else {
            for (Usuario usuario : usuarios) {
                System.out.println("ID: " + usuario.getId() +
                        ", Nombre: " + usuario.getNombre() +
                        ", Email: " + usuario.getEmail() +
                        ", Tipo: " + usuario.getTipo());
            }
        }
    }

    public Usuario buscarUsuarioPorEmailYPassword(String email, String password) {
        List<Usuario> usuarios = daoUsuario.getEm()
                .createQuery("SELECT u FROM Usuario u WHERE u.email = :email AND u.password = :password", Usuario.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .getResultList();

        // Si la lista está vacía, no hay usuario con ese email y password
        return usuarios.isEmpty() ? null : usuarios.get(0);
    }

    public void registrarUsuario(String dni, String nombre, String email, String password, String tipo) {
        Usuario usuario = new Usuario();
        usuario.setDni(dni);
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setTipo(tipo);
        usuario.setPenalizacionHasta(null);
        daoUsuario.add(usuario);
    }

    public boolean tienePenalizacionActiva(int usuarioId) {
        Usuario usuario = daoUsuario.find(usuarioId);
        return usuario != null && usuario.getPenalizacionHasta() != null &&
                usuario.getPenalizacionHasta().isAfter(LocalDate.now());
    }
}

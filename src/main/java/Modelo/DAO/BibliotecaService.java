package Modelo.DAO;

import Modelo.DAO.DAOLibro;
import Modelo.DAO.DAOUsuario;
import Modelo.DTO.Libro;
import Modelo.DTO.Usuario;

import java.util.List;

public class BibliotecaService {
    private DAOLibro daoLibro = new DAOLibro();
    private DAOUsuario daoUsuario = new DAOUsuario();

    public void registrarLibro(String isbn, String titulo, String autor) {
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        daoLibro.add(libro);
    }

    public List<Libro> listarLibros() {
        return daoLibro.findAll();
    }

    public Usuario buscarUsuarioPorEmailYPassword(String email, String password) {
        try {
            List<Usuario> usuarios = daoUsuario.getEm()
                    .createQuery("SELECT u FROM Usuario u WHERE u.email = :email AND u.password = :password", Usuario.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getResultList();
            return usuarios.isEmpty() ? null : usuarios.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

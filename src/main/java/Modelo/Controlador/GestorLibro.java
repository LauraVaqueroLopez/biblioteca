package Modelo.Controlador;

import Modelo.DAO.DAOGenerico;
import Modelo.DTO.Libro;

public class GestorLibro {

    private DAOGenerico<Libro, String> daoLibro;

    public GestorLibro() {
        this.daoLibro = new DAOGenerico<>(Libro.class);
    }

    public void registrarLibro(String isbn, String titulo, String autor) {
        if (daoLibro.find(isbn) != null) {
            throw new IllegalArgumentException("El libro con ISBN " + isbn + " ya existe.");
        }
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        daoLibro.add(libro);
    }

    public void listarLibros() {
        daoLibro.findAll().forEach(libro ->
                System.out.println(libro.getIsbn() + " - " + libro.getTitulo() + " por " + libro.getAutor()));
    }

    public void eliminarLibro(String isbn) {
        Libro libro = daoLibro.find(isbn);
        if (libro != null) {
            daoLibro.delete(libro);
            System.out.println("Libro eliminado correctamente.");
        } else {
            System.out.println("El libro con ISBN " + isbn + " no existe.");
        }
    }
}

package Modelo.Controlador;

import Modelo.DAO.DAOGenerico;
import Modelo.DTO.Ejemplar;
import Modelo.DTO.Libro;

import java.util.List;

public class GestorEjemplar {

    private DAOGenerico<Ejemplar, Integer> daoEjemplar;
    private DAOGenerico<Libro, String> daoLibro;

    public GestorEjemplar() {
        this.daoEjemplar = new DAOGenerico<>(Ejemplar.class);
        this.daoLibro = new DAOGenerico<>(Libro.class);
    }

    public void registrarEjemplar(String isbn, String estado) {
        Libro libro = daoLibro.find(isbn);
        if (libro == null) {
            throw new IllegalArgumentException("No se encontró el libro con ISBN: " + isbn);
        }

        Ejemplar ejemplar = new Ejemplar();
        ejemplar.setIsbn(libro);
        ejemplar.setEstado(estado);
        daoEjemplar.add(ejemplar);
    }

    public long calcularStockDisponible(String isbn) {
        List<Ejemplar> ejemplares = daoEjemplar.findAll();
        return ejemplares.stream()
                .filter(e -> e.getIsbn().getIsbn().equals(isbn) && e.getEstado().equals("Disponible"))
                .count();
    }
}

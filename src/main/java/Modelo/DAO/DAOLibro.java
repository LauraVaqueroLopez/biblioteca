package Modelo.DAO;

import Modelo.DTO.Libro;

public class DAOLibro extends DAOGenerico<Libro, String> {
    public DAOLibro() {
        super(Libro.class);
    }
}


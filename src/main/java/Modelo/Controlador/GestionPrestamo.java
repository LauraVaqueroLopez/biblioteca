package Modelo.Controlador;


import Modelo.DAO.DAOGenerico;
import Modelo.DTO.Ejemplar;
import Modelo.DTO.Prestamo;
import Modelo.DTO.Usuario;

import java.time.LocalDate;
import java.util.List;

public class GestionPrestamo {

    private DAOGenerico<Prestamo, Integer> daoPrestamo;
    private DAOGenerico<Ejemplar, Integer> daoEjemplar;

    public GestionPrestamo() {
        this.daoPrestamo = new DAOGenerico<>(Prestamo.class);
        this.daoEjemplar = new DAOGenerico<>(Ejemplar.class);
    }

    // Método para solicitar un préstamo
    public void solicitarPrestamo(Usuario usuario, String isbn) {
        // Validar si el usuario tiene una penalización activa
        if (usuario.getPenalizacionHasta() != null && usuario.getPenalizacionHasta().isAfter(LocalDate.now())) {
            System.out.println("No puedes realizar nuevos préstamos debido a una penalización activa.");
            return;
        }

        // Verificar si el usuario tiene menos de 3 préstamos activos
        if (usuario.getPrestamos().size() >= 3) {
            System.out.println("No puedes tener más de 3 préstamos activos.");
            return;
        }

        // Obtener el libro por ISBN
        List<Ejemplar> ejemplaresDisponibles = daoEjemplar.getEm()
                .createQuery("SELECT e FROM Ejemplar e WHERE e.isbn = :isbn AND e.estado = 'Disponible'", Ejemplar.class)
                .setParameter("isbn", isbn)
                .getResultList();

        if (ejemplaresDisponibles.isEmpty()) {
            System.out.println("No hay ejemplares disponibles para el libro con ISBN: " + isbn);
            return;
        }

        // Tomamos el primer ejemplar disponible
        Ejemplar ejemplar = ejemplaresDisponibles.get(0);

        // Crear el préstamo
        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setEjemplar(ejemplar);
        prestamo.setFechaInicio(LocalDate.now());
        prestamo.setFechaDevolucion(LocalDate.now().plusDays(15)); // Fecha de devolución a 15 días

        // Guardamos el préstamo
        daoPrestamo.add(prestamo);

        // Cambiar el estado del ejemplar a 'Prestado'
        ejemplar.setEstado("Prestado");
        daoEjemplar.update(ejemplar);

        // Mostrar mensaje de éxito
        System.out.println("Préstamo realizado exitosamente. Fecha de devolución: " + prestamo.getFechaDevolucion());
    }

    public void verPrestamos() {
        List<Prestamo> prestamos = daoPrestamo.findAll();
        if (prestamos.isEmpty()) {
            System.out.println("No hay préstamos registrados.");
        } else {
            for (Prestamo prestamo : prestamos) {
                System.out.println("ID: " + prestamo.getId() +
                        ", Usuario: " + prestamo.getUsuario().getNombre() +
                        ", ISBN: " + prestamo.getEjemplar().getIsbn() +
                        ", Fecha Inicio: " + prestamo.getFechaInicio() +
                        ", Fecha Devolución: " + prestamo.getFechaDevolucion());
            }
        }
    }

    // Método para ver los préstamos de un usuario específico
    public void verPrestamosPorUsuario(String email) {
        List<Prestamo> prestamos = daoPrestamo.findByEmail(email); // Supuesto método en el DAO que busca por email
        if (prestamos.isEmpty()) {
            System.out.println("El usuario con el email " + email + " no tiene préstamos.");
        } else {
            for (Prestamo prestamo : prestamos) {
                System.out.println("ID: " + prestamo.getId() +
                        ", ISBN: " + prestamo.getEjemplar().getIsbn() +
                        ", Fecha Inicio: " + prestamo.getFechaInicio() +
                        ", Fecha Devolución: " + prestamo.getFechaDevolucion());
            }
        }
    }

    // Método para devolver un libro
    public void devolverLibro(int idPrestamo) {
        Prestamo prestamo = daoPrestamo.find(idPrestamo);
        if (prestamo != null) {
            prestamo.getEjemplar().setEstado("Disponible"); // Actualiza el estado del ejemplar a 'Disponible'
            prestamo.setFechaDevolucion(java.time.LocalDate.now()); // Establece la fecha de devolución actual
            daoPrestamo.update(prestamo);
            System.out.println("Libro devuelto correctamente.");
        } else {
            System.out.println("El préstamo con ID " + idPrestamo + " no existe.");
        }
    }
}

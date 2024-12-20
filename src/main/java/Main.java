import Modelo.Controlador.GestorLibro;
import Modelo.Controlador.GestionUsuario;
import Modelo.Controlador.GestionPrestamo;
import Modelo.DTO.Usuario;

import java.util.Scanner;

public class Main {

    private static GestionUsuario gestionUsuario = new GestionUsuario();
    private static GestionPrestamo gestorPrestamo = new GestionPrestamo();
    private static GestorLibro gestorLibro = new GestorLibro();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        mostrarMenu();
    }

    private static void mostrarMenu() {
        while (true) {
            System.out.println("\n===== MENÚ PRINCIPAL =====");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Registrar usuario");
            System.out.println("3. Salir");
            System.out.print("Selecciona una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();  // Limpiar el buffer del scanner

            switch (opcion) {
                case 1:
                    iniciarSesion();
                    break;
                case 2:
                    registrarUsuario();
                    break;
                case 3:
                    System.out.println("¡Hasta luego!");
                    return;
                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }
        }
    }

    private static void iniciarSesion() {
        System.out.print("Introduce tu email: ");
        String email = scanner.nextLine();
        System.out.print("Introduce tu contraseña: ");
        String password = scanner.nextLine();

        Usuario usuario = gestionUsuario.buscarUsuarioPorEmailYPassword(email, password);

        if (usuario != null) {
            System.out.println("¡Bienvenido, " + usuario.getNombre() + "!");
            if (usuario.getTipo().equals("administrador")) {
                mostrarMenuAdministrador(usuario);
            } else {
                mostrarMenuUsuario(usuario);
            }
        } else {
            System.out.println("Credenciales incorrectas. Intenta de nuevo.");
        }
    }

    private static void mostrarMenuAdministrador(Usuario usuario) {
        while (true) {
            System.out.println("\n===== MENÚ ADMINISTRADOR =====");
            System.out.println("1. Gestionar libros");
            System.out.println("2. Gestionar préstamos");
            System.out.println("3. Ver usuarios");
            System.out.println("4. Cerrar sesión");
            System.out.print("Selecciona una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    gestionarLibros();
                    break;
                case 2:
                    gestionarPrestamos();
                    break;
                case 3:
                    verUsuarios();
                    break;
                case 4:
                    System.out.println("¡Hasta luego!");
                    return;
                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }
        }
    }

    private static void mostrarMenuUsuario(Usuario usuario) {
        while (true) {
            System.out.println("\n===== MENÚ USUARIO =====");
            System.out.println("1. Ver mis préstamos");
            System.out.println("2. Solicitar un préstamo");
            System.out.println("3. Cerrar sesión");
            System.out.print("Selecciona una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    verMisPrestamos(usuario);
                    break;
                case 2:
                    solicitarPrestamo(usuario);
                    break;
                case 3:
                    System.out.println("¡Hasta luego!");
                    return;
                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }
        }
    }

    private static void gestionarLibros() {
        while (true) {
            System.out.println("\n===== GESTIONAR LIBROS =====");
            System.out.println("1. Registrar un nuevo libro");
            System.out.println("2. Listar todos los libros");
            System.out.println("3. Eliminar un libro");
            System.out.println("4. Volver");
            System.out.print("Selecciona una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    registrarLibro();
                    break;
                case 2:
                    listarLibros();
                    break;
                case 3:
                    eliminarLibro();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }
        }
    }

    private static void registrarLibro() {
        System.out.print("Introduce el ISBN del libro: ");
        String isbn = scanner.nextLine();
        System.out.print("Introduce el título del libro: ");
        String titulo = scanner.nextLine();
        System.out.print("Introduce el autor del libro: ");
        String autor = scanner.nextLine();

        gestorLibro.registrarLibro(isbn, titulo, autor);
        System.out.println("Libro registrado exitosamente.");
    }

    private static void listarLibros() {
        gestorLibro.listarLibros();
    }

    private static void eliminarLibro() {
        System.out.print("Introduce el ISBN del libro a eliminar: ");
        String isbn = scanner.nextLine();

        gestorLibro.eliminarLibro(isbn);
        System.out.println("Libro eliminado exitosamente.");
    }

    private static void gestionarPrestamos() {
        while (true) {
            System.out.println("\n===== GESTIONAR PRÉSTAMOS =====");
            System.out.println("1. Ver todos los préstamos");
            System.out.println("2. Ver préstamos por usuario");
            System.out.println("3. Devolver un libro");
            System.out.println("4. Volver");
            System.out.print("Selecciona una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    verTodosLosPrestamos();
                    break;
                case 2:
                    verPrestamosPorUsuario();
                    break;
                case 3:
                    devolverLibro();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }
        }
    }

    private static void verTodosLosPrestamos() {
        gestorPrestamo.verPrestamos();
    }

    private static void verPrestamosPorUsuario() {
        System.out.print("Introduce el email del usuario: ");
        String email = scanner.nextLine();

        gestorPrestamo.verPrestamosPorUsuario(email);
    }

    private static void devolverLibro() {
        System.out.print("Introduce el ID del préstamo a devolver: ");
        int idPrestamo = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        gestorPrestamo.devolverLibro(idPrestamo);
    }

    private static void verUsuarios() {
        System.out.println("\n===== VER USUARIOS =====");
        gestionUsuario.verUsuarios();
    }

    private static void verMisPrestamos(Usuario usuario) {
        // Aquí puedes agregar la lógica para que el usuario vea sus préstamos
        System.out.println("Mostrando los préstamos de " + usuario.getNombre());
    }

    private static void solicitarPrestamo(Usuario usuario) {
        if (gestionUsuario.tienePenalizacionActiva(usuario.getId())) {
            System.out.println("No puedes realizar nuevos préstamos debido a una penalización activa.");
            return;
        }

        // Verifica si el usuario tiene menos de 3 préstamos activos
        if (usuario.getPrestamos().size() >= 3) {
            System.out.println("No puedes tener más de 3 préstamos activos.");
            return;
        }

        System.out.print("Introduce el ISBN del libro que deseas solicitar: ");
        String isbn = scanner.nextLine();

        // Lógica para realizar un préstamo (asegurarse de que el ejemplar esté disponible)
        gestorPrestamo.solicitarPrestamo(usuario, isbn);
    }

    private static void registrarUsuario() {
        System.out.print("Introduce el DNI: ");
        String dni = scanner.nextLine();
        System.out.print("Introduce el nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Introduce el email: ");
        String email = scanner.nextLine();
        System.out.print("Introduce la contraseña: ");
        String password = scanner.nextLine();
        System.out.print("Introduce el tipo (normal/administrador): ");
        String tipo = scanner.nextLine();

        gestionUsuario.registrarUsuario(dni, nombre, email, password, tipo);
        System.out.println("Usuario registrado exitosamente.");
    }
}

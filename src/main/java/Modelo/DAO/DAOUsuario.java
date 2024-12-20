package Modelo.DAO;

import Modelo.DTO.Usuario;

public class DAOUsuario extends DAOGenerico<Usuario, Integer> {
    public DAOUsuario() {
        super(Usuario.class);
    }
}

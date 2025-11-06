package org.generation.muebleria.service.interfaces;
import org.generation.muebleria.model.Usuarios;
//import org.generation.muebleria.model.Rol;

import java.util.List;
import java.util.Optional;

public interface IUsuariosService {
    List<Usuarios> getAllUsuariosActivos();
    Optional<Usuarios> getUsuarioById(Long id);
    Optional<Usuarios> getUsuarioByCorreo(String correo);
    //List<Usuarios> getUsuariosByRol(Rol rol);
    Usuarios crearUsuario(Usuarios usuario);
    Usuarios updateUsuarioById(Long id, Usuarios usuarioActualizado);
    void deleteUsuarioById(Long id);

}

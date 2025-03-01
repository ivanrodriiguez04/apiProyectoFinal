package Api.proyectoFinalDWSDIW.servicios;

import Api.proyectoFinalDWSDIW.daos.UsuarioDao;
import Api.proyectoFinalDWSDIW.repositorios.AdministradorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AdministradorServicio {

    @Autowired
    private AdministradorRepositorio administradorRepositorio;

    public List<UsuarioDao> obtenerTodosLosUsuarios() {
        return administradorRepositorio.findAll();
    }

    public Optional<UsuarioDao> obtenerUsuarioPorEmail(String email) {
        return administradorRepositorio.findByEmailUsuario(email);
    }

    public boolean eliminarUsuario(Long id) {
        if (administradorRepositorio.existsById(id)) {
            administradorRepositorio.deleteById(id);
            System.out.println("Usuario con ID " + id + " eliminado correctamente.");
            return true;
        } else {
            System.err.println("Usuario con ID " + id + " no encontrado.");
            return false;
        }
    }
}

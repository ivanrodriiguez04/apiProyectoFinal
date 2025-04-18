package Api.proyectoFinalDWSDIW.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Api.proyectoFinalDWSDIW.daos.SucursalDao;

@Repository
public interface SucursalRepositorio extends JpaRepository<SucursalDao, Long> {
    List<SucursalDao> findAll();

}

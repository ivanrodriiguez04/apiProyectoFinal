package Api.proyectoFinalDWSDIW.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Api.proyectoFinalDWSDIW.daos.TransferenciaDao;

@Repository
public interface TransferenciaRepositorio extends JpaRepository<TransferenciaDao, Long>{

}

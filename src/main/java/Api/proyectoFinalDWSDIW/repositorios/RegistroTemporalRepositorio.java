package Api.proyectoFinalDWSDIW.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Api.proyectoFinalDWSDIW.daos.RegistroTemporalDao;

/**
 * Repositorio para la entidad RegistroTemporal.
 * Permite gestionar registros temporales en la base de datos.
 * 
 * @author irodhan - 06/03/2025
 */
@Repository
public interface RegistroTemporalRepositorio extends JpaRepository<RegistroTemporalDao, Long> {
    /**
     * Busca un registro temporal por su token.
     * 
     * @param token Token de registro.
     * @return Un Optional que contiene el registro si se encuentra.
     */
    Optional<RegistroTemporalDao> findByToken(String token);
}

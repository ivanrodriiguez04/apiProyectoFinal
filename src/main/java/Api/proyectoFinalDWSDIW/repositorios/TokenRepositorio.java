package Api.proyectoFinalDWSDIW.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import Api.proyectoFinalDWSDIW.daos.TokenDao;
import Api.proyectoFinalDWSDIW.daos.UsuarioDao;

@Repository
public interface TokenRepositorio extends JpaRepository<TokenDao, Long> {
    Optional<TokenDao> findByToken(String token);
    Optional<TokenDao> findByUsuario(UsuarioDao usuario); // Nuevo método
    @Modifying
    @Transactional
    @Query("DELETE FROM TokenDao t WHERE t.token = :token")
    void deleteByToken(@Param("token") String token);
}

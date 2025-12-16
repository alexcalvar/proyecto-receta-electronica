package es.uvigo.dagss.recetas.daos;

import es.uvigo.dagss.recetas.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioDAO extends JpaRepository<Usuario, Long> {
    // HU-C1: Login de usuarios (buscar por login exacto)
    Optional<Usuario> findByLogin(String login);
}
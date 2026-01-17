package es.uvigo.dagss.recetas.daos;

import es.uvigo.dagss.recetas.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioDAO extends JpaRepository<Usuario, Long> {
    // buscar por login exacto
    Usuario findByLoginContaining(String login);
}